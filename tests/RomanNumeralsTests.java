import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RomanNumeralsTests {
    @Test
    void basicDigitTest(){
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
    void numberParseTest(){
        assertEquals(101, new RomanNumeral("CI"));
        assertEquals(24, new RomanNumeral("XXIV"));
        assertEquals(49, new RomanNumeral("XLIX"));
        assertEquals(508, new RomanNumeral("DXIII"));
        assertEquals(999, new RomanNumeral("CMXCIX"));
        assertEquals(1101, new RomanNumeral("MCI"));
    }
}
