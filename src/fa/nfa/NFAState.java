package fa.nfa;

import fa.State;

import java.util.*;

/**
 * Represents a single state for a non-deterministic finite automata.
 *
 * @author Nathan Regner, Nathan Steele
 */
public class NFAState extends State {

    private Map<Character, Set<NFAState>> transitions = new HashMap<>();

    /**
     * Creates a new NFA state with the provided name.
     *
     * @param name the name of the state
     */
    public NFAState(String name) {
        this.name = name;
    }

    /**
     * Adds a transition to another {@link NFAState}
     *
     * @param to     the other state
     * @param onSymb the symbol to transition on
     */
    public void addTransition(NFAState to, char onSymb) {
        transitions.compute(onSymb, (t, states) -> {
            states = states == null ? new LinkedHashSet<>() : states;
            states.add(to);
            return states;
        });
    }

    /**
     * @param onSymb the symbol to transition on
     * @return the set of available states that can be transitioned to
     */
    public Set<NFAState> getTransition(char onSymb) {
        return transitions.getOrDefault(onSymb, new LinkedHashSet<>());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NFAState nfaState = (NFAState) o;
        return Objects.equals(name, nfaState.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}
