package fa.nfa;

import com.google.common.collect.Sets;
import fa.State;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;

import static fa.nfa.NFA.EMPTY_STRING;

class NFATest {

    /**
     * Like {@link Runnable}, but handles checked exceptions
     */
    @FunctionalInterface
    private interface Action {
        void call() throws Exception;
    }

    private String captureOutput(Action a) throws Exception {
        PrintStream old = System.out;

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PrintStream ps = new PrintStream(baos);
            System.setOut(ps);

            a.call();
            System.out.flush();

            return baos.toString();
        } finally {
            System.setOut(old);
        }
    }

    private void testDriver(String testCase) throws Exception {
        String actual = captureOutput(() -> NFADriver.main(new String[]{"./tests/" + testCase + ".txt"}));
        String expected = new String(Files.readAllBytes(Paths.get("./tests/", testCase + "_expected.txt")), Charset.defaultCharset());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void p2tc0() throws Exception {
        testDriver("p2tc0");
    }

    @Test
    void p2tc1() throws Exception {
        testDriver("p2tc1");
    }

    @Test
    void p2tc2() throws Exception {
        testDriver("p2tc2");
    }

    @Test
    void p2tc3() throws Exception {
        testDriver("p2tc3");
    }

    @Test
    void p2tc4() throws Exception {
        testDriver("p2tc4");
    }

    @Test
    void p2tc5() throws Exception {
        testDriver("p2tc5");
    }

    // Example 5 - NFA to DFA
    @Test
    void p2tc6() throws Exception {
        testDriver("p2tc6");
    }

    // Example 4 - NFA to DFA
    @Test
    void p2tc7() throws Exception {
        testDriver("p2tc7");
    }

    @Test
    void closure() {
        NFAState q0 = new NFAState("q0");
        NFAState q1 = new NFAState("q1");
        NFAState q2 = new NFAState("q2");
        NFAState q3 = new NFAState("q3");

        q0.addTransition(q1, EMPTY_STRING);
        q1.addTransition(q2, 'a');
        q2.addTransition(q3, EMPTY_STRING);
        q3.addTransition(q3, EMPTY_STRING);

        Set<String> expected = Sets.newHashSet("q0", "q1");
        Set<String> actual = NFA.closure(q0)
                .stream()
                .map(State::getName)
                .collect(Collectors.toSet());

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void reachable() {
        NFAState q0 = new NFAState("q0");
        NFAState q1 = new NFAState("q1");
        NFAState q2 = new NFAState("q2");
        NFAState q3 = new NFAState("q3");

        q0.addTransition(q1, EMPTY_STRING);
        q1.addTransition(q2, 'a');
        q2.addTransition(q3, EMPTY_STRING);
        q3.addTransition(q1, EMPTY_STRING); // loop back to c

        Set<String> expected = Sets.newHashSet("q2", "q3", "q1");
        Set<String> actual = NFA.reachable('a', q0)
                .stream()
                .map(State::getName)
                .collect(Collectors.toSet());

        Assertions.assertEquals(expected, actual);
    }
}