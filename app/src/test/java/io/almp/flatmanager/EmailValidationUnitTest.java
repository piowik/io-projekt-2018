package io.almp.flatmanager;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class EmailValidationUnitTest {
    @Test
    public void proper_email_isCorrect() {
        boolean result = Utils.isValidEmail("piotr@gmail.com");
        assertTrue(result);
    }
    @Test
    public void empty_isIncorrect() {
        boolean result = Utils.isValidEmail("");
        assertFalse(result);
    }
    @Test
    public void incorrect_one_isIncorrect() {
        boolean result = Utils.isValidEmail("abc.pl");
        assertFalse(result);
    }
    @Test
    public void incorrect_two_isIncorrect() {
        boolean result = Utils.isValidEmail("@abc.pl");
        assertFalse(result);
    }
    @Test
    public void incorrect_three_isIncorrect() {
        boolean result = Utils.isValidEmail("piotr@abc@pl");
        assertFalse(result);
    }
}