package fa.dfa;

import fa.State;
import java.util.HashMap;
/*
 *  Starting instantiating a few simple things, state storage (Hashmap) State constructor, and transitions
 * 
 */
public class DFAState extends State{

/*
 * Instantiating the Hashmap that will represent the states
 */
	private HashMap<Character, String> transition;
/*
 * State constructor
 * @param name - Name for the state
 */

	public DFAState(String name) {
		transition = new HashMap<Character, String>();
		this.name = name;
	
	}
/*
 * transitions constructor
 * @param toState - state name for transition
 * @parma c - consumed input
 */
	public void addATransition(String toState, char c) {
		transition.put(c, toState);

	
	}
/*
 * transitions possible or not
 * @param c - char to be consumed if possible
 * @returns - the statename that you can go to. 
 */
	public String getTransition(char c) {
	if(transition.containsKey(c))
		return transition.get(c);
	else
		return null;
	}
}
