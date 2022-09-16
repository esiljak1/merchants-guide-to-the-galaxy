import com.esiljak.exceptions.DuplicatedConversionKeyException;
import com.esiljak.exceptions.DuplicatedConversionValueException;
import com.esiljak.exceptions.IllegalRomanNumeralException;
import com.esiljak.models.IntergalacticConversion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class IntergalacticConversionTests {
    private final Map<String, String> map = Stream.of(new String[][]{
            { "name1", "I" },
            { "name2", "V" },
            { "name3", "X" }
    }).collect(Collectors.toMap((item) -> item[0], (item) -> item[1]));
    private IntergalacticConversion conversion;

    @BeforeEach
    void initialize(){
        conversion = new IntergalacticConversion();
    }

    @Test
    void constructorTest(){
        assertTrue(conversion.getEntries().isEmpty(), "If no value is passed in constructor there should be no entries");
    }

    @Test
    void constructorWithParameterTest(){
        conversion = new IntergalacticConversion(map);

        assertEquals(3, conversion.getEntries().size(), "Entries not properly set through constructor");
    }

    @Test
    void setterTest(){
        conversion.setEntries(map);

        assertEquals("I", conversion.getEntries().get("name1"), "Entries not properly set through setter");
    }

    @Test
    void addingPairsTest() throws DuplicatedConversionValueException, DuplicatedConversionKeyException, IllegalRomanNumeralException {
        conversion.addEntry("entry1", "X");
        conversion.addEntry("entry2", "I");

        assertEquals("X", conversion.getEntries().get("entry1"), "Entry not properly added through addEntry");
        assertEquals("I", conversion.getEntries().get("entry2"), "Entry not properly added through addEntry");
    }

    @Test
    void duplicatingKeysTest(){
        assertThrows(DuplicatedConversionKeyException.class, () -> {
            conversion.addEntry("entry1", "X");
            conversion.addEntry("entry1", "I");
        }, "Cannot have multiple entries with the same key");

        assertThrows(DuplicatedConversionValueException.class, () -> {
            conversion.addEntry("entry2", "X");
            conversion.addEntry("entry3", "X");
        }, "Cannot have multiple entries with the same value");
    }

    @Test
    void invalidValueTest(){
        assertThrows(IllegalRomanNumeralException.class, () -> {
           conversion.addEntry("entry", "T");
        });

        assertThrows(IllegalRomanNumeralException.class, () -> {
            conversion.addEntry("entry", "i");
        });

        assertThrows(IllegalRomanNumeralException.class, () -> {
            conversion.addEntry("entry", "XX");
        });
    }
}
