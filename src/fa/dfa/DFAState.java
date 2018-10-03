package fa.dfa;

import fa.State;

import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

/*
 *  Starting instantiating a few simple things, state storage (Hashmap) State constructor, and transitions
 * 
 */
public class DFAState extends State{

/*
 * Instantiating the Hashmap that will represent the states
 */
	private HashMap<Character, DFAState> transition;
/*
 * State constructor
 * @param name - Name for the state
 */

	public DFAState(String name) {
		transition = new HashMap<Character, DFAState>();
		this.name = name;
	
	}
/*
 * transitions constructor
 * @param toState - state name for transition
 * @parma c - consumed input
 */
	public void addATransition(DFAState toState, char c) {
		transition.put(c, toState);

	
	}
/*
 * transitions possible or not
 * @param c - char to be consumed if possible
 * @returns - the statename that you can go to. 
 */
	public DFAState getTransition(char c) {
	if(transition.containsKey(c))
		return transition.get(c);
	else
		return null;
	}

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
