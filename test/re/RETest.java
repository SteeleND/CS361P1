package re;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

class RETest {
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
        String actual = captureOutput(() -> REDriver.main(new String[]{"./tests/" + testCase + ".txt"}));
        String expected = new String(Files.readAllBytes(Paths.get("./tests/", testCase + "_expected.txt")), Charset.defaultCharset());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void p3tc1() throws Exception {
        testDriver("p3tc1");
    }

    @Test
    void p3tc2() throws Exception {
        testDriver("p3tc2");
    }

    @Test
    void p3tc3() throws Exception {
        testDriver("p3tc3");
    }
}