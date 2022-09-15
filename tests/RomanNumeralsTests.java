import com.esiljak.exceptions.IllegalRomanNumeralException;
import com.esiljak.models.RomanNumeral;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RomanNumeralsTests {
    @Test
    void basicDigitTest() throws Exception{
        RomanNumeral romanNumeral = new RomanNumeral("I");
        assertEquals(1, romanNumeral.getValue(), "I should have the value 1");

        romanNumeral.setNumeral("V");
        assertEquals(5, romanNumeral.getValue(), "V should have the value 5");

        romanNumeral.setNumeral("X");
        assertEquals(10, romanNumeral.getValue(), "X should have the value 10");

        romanNumeral.setNumeral("L");
        assertEquals(50, romanNumeral.getValue(), "L should have the value 50");

        romanNumeral.setNumeral("C");
        assertEquals(100, romanNumeral.getValue(), "C should have the value 100");

        romanNumeral.setNumeral("D");
        assertEquals(500, romanNumeral.getValue(), "D should have the value 500");

        romanNumeral.setNumeral("M");
        assertEquals(1000, romanNumeral.getValue(), "M should have the value 1000");
    }

    @Test
    void numberParseTest() throws Exception {
        assertEquals(101, new RomanNumeral("CI").getValue());
        assertEquals(24, new RomanNumeral("XXIV").getValue());
        assertEquals(49, new RomanNumeral("XLIX").getValue());
        assertEquals(508, new RomanNumeral("DVIII").getValue());
        assertEquals(999, new RomanNumeral("CMXCIX").getValue());
        assertEquals(1101, new RomanNumeral("MCI").getValue());
    }

    @Test
    void wrongFormatsTest(){
        assertThrows(IllegalRomanNumeralException.class, () -> {
           new RomanNumeral("IC");
        }, "I as a prefix only before V and X");

        assertThrows(IllegalRomanNumeralException.class, () -> {
            new RomanNumeral("XCX");
        }, "If you have a subtractive number after it can come the value smaller than the prefix");

        assertThrows(IllegalRomanNumeralException.class, () -> {
           new RomanNumeral("MMXXT");
        }, "Illegal character T");

        assertThrows(IllegalRomanNumeralException.class, () -> {
           new RomanNumeral("XXXXI");
        }, "Illegal number of repeated characters");

        assertThrows(IllegalRomanNumeralException.class, () -> {
           new RomanNumeral("LXL");
        }, "Illegal number of repeated characters");
    }
}
