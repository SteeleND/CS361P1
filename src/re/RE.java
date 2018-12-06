package re;

import fa.nfa.NFA;
import java.util.LinkedHashSet;
import java.util.Set;
import fa.State;
import fa.nfa.NFAState;

/**
* Create an NFA from a Regular expression
* 
* @author Nathan Steele and Nathan Regnal
*
* Modeled after example provided at http://matt.might.net/articles/parsing-regex-with-recursive-descent/
*/
public class RE implements REInterface {

    String regEx = "";
    int stateCount = 1;
    public RE(String regEx) {
        this.regEx = regEx;
    }

    public NFA getNFA() {
        return regex();
    }


   /**
    * Returns the char at 0
    */
    private char peek(){
        return regEx.charAt(0);
    }

    /**
    * Returns the next input
    * @return
    */
    private char next(){
        char c = peek();
        consume(c);
        return c;        
    }

    /**
    * Consumes the next char in the string
    * @param nextInput
    *
    */
    private void consume(char nextInput){
        if(peek() == nextInput){
            this.regEx = this.regEx.substring(1);
        }
        else{
            throw new RuntimeException("Received this: " + peek() + "\n" + "Expected this: " + nextInput);
        }
    }

    /**
    * Checks to verify that there is still more in the string to consume
    * @return true or false depending on if there is more string to consume
    */
    private boolean more(){
        return regEx.length() > 0;
    }
    /**
    * Checks for and handles |/'or'
    *
    * @return
    */
    private NFA regex() {
        NFA term = term(); 
        if(more() && peek() == '|'){
            consume('|');  //nextInput monster
            NFA regex = regex();
            return choice(term, regex);
        }
        else{
            return term;
        }
    }

    /**
    * Looks for more and verifies theres more states
    * @return factor 
    */
    private NFA term(){
        NFA factor = new NFA();
        while (more() && peek() != ')' && peek() != '|'){
            NFA nFactor = factor();
            if(factor.getStates().isEmpty()){
                factor = nFactor;
            }
            else{
                factor = concat(factor, nFactor);
            }
        }
        return factor;
    }

    /**
    * Calls base() and then checks for *
    * @return
    */
    private NFA factor(){
        NFA base = base();
        while (more() && peek() == '*'){
            consume('*');
            base = star(base);
        }
        return base;
    }

    /**
    * Checks and handles open parentisis
    * @return
    */
    private NFA base(){
        switch (peek()){
        case '(':
            consume('(');
            NFA reg = regex();  
            consume(')');
            return reg;
        default:
            return symbol(next());
        }
    }

    /**
    * Returns choice of NFA
    * @param nfa1
    * @param nfa2
    * @return
    */
    private NFA choice(NFA nfa1, NFA nfa2){
        NFA nfa = new NFA();

        // Create a new start and final state
        String neStart = "" + stateCount++;
        nfa.addStartState(nStart);
        String neFinal = "" + stateCount++;
        nfa.addFinalState(nFinal);

        // add states to both nfa's
        nfa.addNFAStates(nfa1.getStates());
        nfa.addNFAStates(nfa2.getStates());

        // initialize NFA 1
        for(State finalState : nfa1.getFinalStates()){
            NFAState ourFinal = (NFAState) finalState;
            ourFinal.setNonFinal();
            nfa.addTransition(ourFinal.getName(), 'e', neFinal);
        }

        // initialize NFA 2 
        for(State finalState : nfa2.getFinalStates()){
            NFAState ourFinal = (NFAState) finalState;
            ourFinal.setNonFinal();
            nfa.addTransition(ourFinal.getName(), 'e', neFinal);
        }
	// old nfa getter
        String oldNFA1 = nfa1.getStartState().getName(); 
        // new nfa getter
	String oldNFA2 = nfa2.getStartState().getName();

        // add empty transitions from new states to old versions
        nfa.addTransition(neStart, 'e', oldNFA1);
        nfa.addTransition(neStart, 'e', oldNFA2);

        //add the alphabet
        nfa.addAbc(nfa1.getABC());
        nfa.addAbc(nfa2.getABC());        

        return nfa;
    }

    /**
    * Returns a concatenation of NFA
    * @param nfa1
    * @param nfa2
    * @return
    */
    private NFA concat(NFA nfa1, NFA nfa2){

        String secondStartState = nfa2.getStartState().getName();
        Set<State> nfa1Finals = nfa1.getFinalStates();
        nfa1.addNFAStates(nfa2.getStates());

        //remove nfa1 final, and add empty tranisition to get to  nfa2
        for(State finalState : nfa1Finals){
            NFAState afinal = (NFAState) finalState;
            afinal.setNonFinal();
            nfa1.addTransition(afinal.getName(), 'e', secondStartState);
        }

        //add the alphabet of nfa2
        nfa1.addAbc(nfa2.getABC());

        return nfa1;
    }

    /**
      * Returns nested NFA that processes astriks *
      * @param ourNFA
      * @return
    */
    private NFA star(NFA nfa){
        NFA newNFA = new NFA();

        //create a new start
        String newStart = "" + stateCount++;
        newNFA.addStartState(newStart);

        //create a  new final and add states
        String newFinal = "" + stateCount++;
        newNFA.addFinalState(newFinal);
        newNFA.addNFAStates(nfa.getStates());

        //loop through 
        for(State finalState : nfa.getFinalStates()){
            NFAState ourFinal = (NFAState) finalState;
            // make all states not-final
            ourFinal.setNonFinal(); 
	    // manage and add empty states transition
            newNFA.addTransition(ourFinal.getName(), 'e', newFinal);
            newNFA.addTransition(ourFinal.getName(), 'e', nfa.getStartState().getName());
        }


        // Putting it all together, adding everything to new NFA
	// Create a transition from new NFA to old NFA start, then set the new Final state
        newNFA.addTransition(newStart, 'e', nfa.getStartState().getName()); 
        newNFA.addTransition(newStart, 'e', newFinal);       
        // Setup and add the alphabet
        newNFA.addAbc(nfa.getABC());
        return newNFA;
    }

    /**
    * Returns NFA that accepts input character
    * @param inputChar
    * @return
    */
    private NFA symbol(char inputChar){
        NFA newNFA = new NFA();

        // Start state and final state initalization and tranistions.
        String newStart = "" + stateCount++;
        newNFA.addStartState(newStart);
        String newFinal = "" + stateCount++;
        newNFA.addFinalState(newFinal);

        newNFA.addTransition(newStart, inputChar, newFinal);

        Set<Character> alph = new LinkedHashSet<Character>();
        alph.add(inputChar);
        newNFA.addAbc(alph);
        return newNFA;
    }
}

