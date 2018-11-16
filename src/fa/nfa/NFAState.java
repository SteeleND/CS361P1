package fa.nfa;

import fa.State;

import java.util.*;

public class NFAState extends State {

    private Map<Character, Set<NFAState>> transitions = new HashMap<>();

    public NFAState(String name) {
        this.name = name;
    }

    public void addTransition(NFAState to, char onSymb) {
        transitions.compute(onSymb, (t, states) -> {
            states = states == null ? new LinkedHashSet<>() : states;
            states.add(to);
            return states;
        });
    }

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
