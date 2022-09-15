import com.esiljak.models.RomanNumerals;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RomanNumeralsTests {
    @Test
    void basicValueTest(){
        assertEquals(RomanNumerals.I.getValue(), 1, "I should have a value of 1");
        assertEquals(RomanNumerals.V.getValue(), 5, "V should have a value of 5");
        assertEquals(RomanNumerals.X.getValue(), 10, "X should have a value of 10");
        assertEquals(RomanNumerals.L.getValue(), 50, "L should have a value of 50");
        assertEquals(RomanNumerals.C.getValue(), 100, "C should have a value of 100");
        assertEquals(RomanNumerals.D.getValue(), 500, "D should have a value of 500");
        assertEquals(RomanNumerals.M.getValue(), 1000, "M should have a value of 1000");
    }

    @Test
    void basicToStringTest(){
        assertEquals(RomanNumerals.I.toString(), "I");
        assertEquals(RomanNumerals.V.toString(), "V");
        assertEquals(RomanNumerals.X.toString(), "X");
        assertEquals(RomanNumerals.L.toString(), "L");
        assertEquals(RomanNumerals.C.toString(), "C");
        assertEquals(RomanNumerals.D.toString(), "D");
        assertEquals(RomanNumerals.M.toString(), "M");
    }
}
