package fa.dfa;

import fa.State;

import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

/**
 * Starting instantiating a few simple things, state storage (Hashmap) State constructor, and transitions
 */
public class DFAState extends State {

    /**
     * Instantiating the Hashmap that will represent the states
     */
    private HashMap<Character, DFAState> transition;

    /**
     * State constructor
     *
     * @param name Name for the state
     */
    public DFAState(String name) {
        transition = new HashMap<>();
        this.name = name;
    }

    /**
     * Adds a transition to the given state
     *
     * @param toState state name for transition
     * @param c       consumed input
     */
    public void addATransition(DFAState toState, char c) {
        transition.put(c, toState);
    }

    /**
     * Checks if a givne transition is
     *
     * @param c char to be consumed if possible
     * @return the state that you can go to, or null
     */
    public DFAState getTransition(char c) {
        return transition.get(c);
    }

    /**
     * @return the set of all transition characters
     */
    public Set<Character> getTransitions() {
        return transition.keySet();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DFAState dfaState = (DFAState) o;
        return Objects.equals(name, dfaState.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
