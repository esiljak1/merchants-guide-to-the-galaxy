import com.esiljak.exceptions.IllegalRomanNumeralException;
import com.esiljak.helpers.NumberParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NumberParserTests {
    @Test
    void digitTest(){
        assertDoesNotThrow(() -> {
            NumberParser.checkValidDigit("I");
            NumberParser.checkValidDigit("V");
            NumberParser.checkValidDigit("X");
            NumberParser.checkValidDigit("L");
            NumberParser.checkValidDigit("C");
            NumberParser.checkValidDigit("D");
            NumberParser.checkValidDigit("M");
        });
    }

    @Test
    void invalidDigitTest(){
        assertThrows(IllegalRomanNumeralException.class, () -> {
            NumberParser.checkValidDigit("i");
        });

        assertThrows(IllegalRomanNumeralException.class, () -> {
            NumberParser.checkValidDigit("k");
        });

        assertThrows(IllegalRomanNumeralException.class, () -> {
            NumberParser.checkValidDigit("IX");
        });

        assertThrows(IllegalRomanNumeralException.class, () -> {
            NumberParser.checkValidDigit("|");
        });

        assertThrows(IllegalRomanNumeralException.class, () -> {
            NumberParser.checkValidDigit(".");
        });
    }

    @Test
    void valueTest() throws Exception{
        assertEquals(58, NumberParser.parseRomanNumeral("LVIII"));
        assertEquals(999, NumberParser.parseRomanNumeral("CMXCIX"));
        assertEquals(15, NumberParser.parseRomanNumeral("XV"));
        assertEquals(84, NumberParser.parseRomanNumeral("LXXXIV"));
        assertEquals(321, NumberParser.parseRomanNumeral("CCCXXI"));
    }

    @Test
    void illegalValueTest(){
        assertThrows(IllegalRomanNumeralException.class, () -> {
           NumberParser.parseRomanNumeral("XXXX");
        });

        assertThrows(IllegalRomanNumeralException.class, () -> {
            NumberParser.parseRomanNumeral("CC|");
        });

        assertThrows(IllegalRomanNumeralException.class, () -> {
            NumberParser.parseRomanNumeral("IC");
        });

        assertThrows(IllegalRomanNumeralException.class, () -> {
            NumberParser.parseRomanNumeral("LT");
        });
    }
}
