package fa.dfa;

import fa.FAInterface;
import fa.State;

import java.util.*;
import java.util.stream.Collectors;

public class DFA implements FAInterface, DFAInterface {

    private DFAState startState;
    private Map<String, DFAState> states = new HashMap<>();
    private Set<State> finalStates = new HashSet<>();

    /**
     * Return the {@link DFAState} associated with the provided name.
     *
     * @throws IllegalArgumentException if the state is not defined
     */
    private DFAState findState(String name) {
        if (!states.containsKey(name)) {
            throw new IllegalArgumentException("Invalid state: " + name);
        }

        return states.get(name);
    }

    @Override
    public void addStartState(String name) {
        if (startState != null) {
            throw new IllegalStateException("Cannot define more than one start state");
        }

        startState = states.computeIfAbsent(name, DFAState::new);
    }

    @Override
    public void addState(String name) {
        states.computeIfAbsent(name, DFAState::new);
    }

    @Override
    public void addFinalState(String name) {
        DFAState state = states.computeIfAbsent(name, DFAState::new);
        finalStates.add(state);
    }

    @Override
    public void addTransition(String fromState, char onSymb, String toState) {
        DFAState from = findState(fromState);
        DFAState to = findState(toState);

        from.addATransition(to, onSymb);
    }

    @Override
    public Set<? extends State> getStates() {
        return new HashSet<>(states.values());
    }

    @Override
    public Set<? extends State> getFinalStates() {
        return finalStates;
    }

    @Override
    public State getStartState() {
        return startState;
    }

    @Override
    public Set<Character> getABC() {
        return states.values().stream()
                .map(DFAState::getTransitions)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean accepts(String s) {
        DFAState state = startState;

        for (char transition : s.toCharArray()) {
            // if state is null, we either:
            // a.) got a character that is not part of the alphabet
            // b.) or the machine is missing transitions
            if (state == null) return false;
            state = state.getTransition(transition);
        }

        return finalStates.contains(state);
    }

    @Override
    public DFAState getToState(DFAState from, char onSymb) {
        return findState(from.getName()).getTransition(onSymb);
    }

    public String toString()
    {
        Iterator<DFAState> Q = states.iterator();
        Iterator <Character> sig = alphabet.iterator();

        Iterator<DFAState> F = finalStates.iterator();

        String output = "Q = { ";
        //Instantiating string, and then looping threw different cases to add
        while(Q.hasNext())
        {
            output += Q.next() + " ";
        }

        //Sigma value addition
        output += "}\n Sigma = { ";
        while(sig.hasNext())
        {
            output += sig.next() + " ";
        }

        //Delta addition
        output += "}\n delta = \n\t";
        sig = alphabet.iterator();

        while(sig.hasNext())
        {
            output += "\t" + sig.next() + "\t";
        }
        output += "\n";
        Q = states.iterator();
        while(Q.hasNext())
        {
            sig = alphabet.iterator();
            DFAState currentState = Q.next();
            output += "\t" + currentState + "\t";
            while(sig.hasNext())
            {
                output += currentState.getTransition(sig.next()) + "\t";
            }

            output += "\n";


        }
        output += "q0 = " + startState + "\n";
        output += "F = { ";

        while(F.hasNext())
        {
            output += F.next() + " ";
        }

        output += "}\n";

        return output;
    }
}
