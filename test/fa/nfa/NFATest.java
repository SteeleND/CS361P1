package fa.nfa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

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
    void emptyTransition() throws Exception {
        testDriver("empty");
    }

}