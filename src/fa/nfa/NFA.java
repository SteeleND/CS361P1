package fa.nfa;

import fa.FAInterface;
import fa.State;
import fa.dfa.DFA;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents a non-deterministic finite automata.
 *
 * @author Nathan Regner, Nathan Steele
 */
public class NFA implements FAInterface, NFAInterface {

    static final char EMPTY_STRING = 'e';

    private NFAState startState;
    private Map<String, NFAState> states = new LinkedHashMap<>();
    private Set<NFAState> finalStates = new LinkedHashSet<>();
    private Set<Character> alphabet = new LinkedHashSet<>();

    /**
     * Return the {@link NFAState} associated with the provided name.
     *
     * @throws IllegalArgumentException if the state is not defined
     */
    private NFAState findState(String name) {
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

        startState = states.computeIfAbsent(name, NFAState::new);
    }

    @Override
    public void addState(String name) {
        states.computeIfAbsent(name, NFAState::new);
    }

    @Override
    public void addFinalState(String name) {
        NFAState state = states.computeIfAbsent(name, NFAState::new);
        finalStates.add(state);
    }

    @Override
    public void addTransition(String fromState, char onSymb, String toState) {
        NFAState from = findState(fromState);
        NFAState to = findState(toState);

        from.addTransition(to, onSymb);

        if (onSymb != EMPTY_STRING) {
            alphabet.add(onSymb);
        }
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
        return alphabet;
    }

    /**
     * Converts this NFA to an equivalent DFA using a breadth-first search.
     *
     * @return equivalent DFA
     */
    @Override
    public DFA getDFA() {
        DFA dfa = new DFA();

        // compute new start state
        Set<NFAState> start = eClosure(startState);
        String startName = name(start);
        if (isFinal(start)) {
            dfa.addFinalState(startName);
        }
        dfa.addStartState(startName);

        // track nodes that we need to visit
        Queue<Set<NFAState>> queue = new ArrayDeque<>();
        queue.add(start);

        // track nodes we have already visited
        HashSet<String> visited = new HashSet<>();

        while (!queue.isEmpty()) {
            Set<NFAState> from = queue.remove();
            String fromName = name(from);
            visited.add(fromName);

            for (Character transition : alphabet) {
                Set<NFAState> to = union(from, transition);
                String toName = name(to);

                if (!visited.contains(toName)) {
                    // mark as final if any of the individual NFA states are final
                    if (isFinal(to)) {
                        dfa.addFinalState(toName);
                    } else {
                        dfa.addState(toName);
                    }

                    queue.add(to);
                    visited.add(toName);
                }

                dfa.addTransition(fromName, transition, toName);
            }
        }

        return dfa;
    }

    /**
     * @return true if the new DFA state should be marked as final
     */
    private boolean isFinal(Set<NFAState> state) {
        return state.stream().anyMatch(finalStates::contains);
    }

    /**
     * Returns a unique name for a set of NFA states. Used in resulting DFA.
     */
    private String name(Set<NFAState> states) {
        return states.stream()
                .map(State::getName)
                .collect(Collectors.joining(", ", "[", "]"));
    }

    /**
     * Returns the union of all reachable states for a given transition.
     */
    private Set<NFAState> union(Set<NFAState> states, char onSymb) {
        return states.stream()
                .map(s -> reachable(onSymb, s))
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<NFAState> getToState(NFAState from, char onSymb) {
        return from.getTransition(onSymb);
    }

    @Override
    public Set<NFAState> eClosure(NFAState s) {
        return closure(s);
    }

    /**
     * Finds the set of states reachable from the provided state, via empty transitions.
     */
    public static Set<NFAState> closure(NFAState from) {
        return closure(from, new HashSet<>());
    }

    private static Set<NFAState> closure(NFAState from, Set<NFAState> acc) {
        acc.add(from);

        for (NFAState to : from.getTransition(EMPTY_STRING)) {
            if (!acc.contains(to)) {
                closure(to, acc);
            }
        }

        return acc;
    }

    /**
     * Finds the set of all states that can be reached consuming 'symbol' exactly once.
     * e* -> symbol -> e*
     */
    public static Set<NFAState> reachable(char symbol, NFAState from) {
        return reachable(symbol, from, new HashSet<>(), new HashSet<>());
    }

    private static Set<NFAState> reachable(char symbol, NFAState from, Set<NFAState> visited, Set<NFAState> acc) {
        visited.add(from);

        // find direct transitions, optionally followed by empty transitions
        for (NFAState to : from.getTransition(symbol)) {
            acc.addAll(closure(to));
        }

        // then explore indirect transitions
        for (NFAState other : from.getTransition(EMPTY_STRING)) {
            if (!visited.contains(other)) {
                reachable(symbol, other, visited, acc);
            }
        }

        return acc;
    }
}
