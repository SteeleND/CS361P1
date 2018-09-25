package fa.dfa;

import fa.FAInterface;
import fa.State;

import java.util.Set;

/**
 * TODO: Implement
 */
public class DFA implements FAInterface, DFAInterface {

    @Override
    public void addStartState(String name) {

    }

    @Override
    public void addState(String name) {

    }

    @Override
    public void addFinalState(String name) {

    }

    @Override
    public void addTransition(String fromState, char onSymb, String toState) {

    }

    @Override
    public Set<? extends State> getStates() {
        return null;
    }

    @Override
    public Set<? extends State> getFinalStates() {
        return null;
    }

    @Override
    public State getStartState() {
        return null;
    }

    @Override
    public Set<Character> getABC() {
        return null;
    }

    @Override
    public boolean accepts(String s) {
        return false;
    }

    @Override
    public DFAState getToState(DFAState from, char onSymb) {
        return null;
    }
}
