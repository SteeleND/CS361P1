package re;

import fa.nfa.NFA;

public class RE implements REInterface {
    private final String regEx;

    public RE(String regEx) {
        this.regEx = regEx;
    }

    @Override
    public NFA getNFA() {
        NFA nfa = new NFA();
        // TODO
        return nfa;
    }
}
