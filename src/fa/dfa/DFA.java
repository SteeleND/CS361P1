package fa.dfa;

import fa.FAInterface;
import fa.State;

import java.util.*;
import java.util.stream.Collectors;

public class DFA implements FAInterface, DFAInterface {

    private DFAState startState;
    private Map<String, DFAState> states = new LinkedHashMap<>();
    private Set<DFAState> finalStates = new LinkedHashSet<>();

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
            if (transition == 'e') continue;
            
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Q = { ");
        Set<Character> alphabet = getABC();

        for (DFAState state : states.values()) {
            sb.append(state).append(" ");
        }

        // sigma
        sb.append("}\nSigma = { ");
        for (Character c : alphabet) {
            sb.append(c).append(" ");
        }

        //Delta addition
        sb.append("}\ndelta =\n");

        int width = states.values().stream()
                .map(DFAState::toString)
                .mapToInt(String::length)
                .max()
                .orElse(1) + 1;

        sb.append(padLeft("", width));
        for (Character c : alphabet) {
            sb.append(padLeft(c, width));
        }
        sb.append("\n");

        for (DFAState state : states.values()) {
            sb.append(padLeft(state, width));

            for (Character c : alphabet) {
                sb.append(padLeft(state.getTransition(c), width));
            }

            sb.append("\n");
        }

        sb.append("q0 = ").append(startState).append("\n");
        sb.append("F = { ");

        for (DFAState state : finalStates) {
            sb.append(state).append(" ");
        }

        sb.append("}\n");

        return sb.toString();
    }

    private static String padLeft(Object o, int n) {
        return String.format("%1$" + n + "s", o);
    }
}
