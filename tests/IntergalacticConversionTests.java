import com.esiljak.exceptions.DuplicatedConversionKey;
import com.esiljak.exceptions.DuplicatedConversionValue;
import com.esiljak.exceptions.IllegalRomanNumeralException;
import com.esiljak.models.IntergalacticConversion;
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

    @Test
    void constructorTest(){
        IntergalacticConversion conversion = new IntergalacticConversion();

        assertTrue(conversion.getEntries().isEmpty(), "If no value is passed in constructor there should be no entries");
    }

    @Test
    void constructorWithParameterTest(){
        IntergalacticConversion conversion = new IntergalacticConversion(map);

        assertEquals(3, conversion.getEntries().size(), "Entries not properly set through constructor");
    }

    @Test
    void setterTest(){
        IntergalacticConversion conversion = new IntergalacticConversion();
        conversion.setEntries(map);

        assertEquals("I", conversion.getEntries().get("name1"), "Entries not properly set through setter");
    }

    @Test
    void addingPairsTest(){
        IntergalacticConversion conversion = new IntergalacticConversion();
        conversion.addEntry("entry1", "X");
        conversion.addEntry("entry2", "I");

        assertEquals("X", conversion.getEntries().get("entry1"), "Entry not properly added through addEntry");
        assertEquals("I", conversion.getEntries().get("entry2"), "Entry not properly added through addEntry");
    }

    @Test
    void duplicatingKeysTest(){
        assertThrows(DuplicatedConversionKey.class, () -> {
            IntergalacticConversion conversion = new IntergalacticConversion();
            conversion.addEntry("entry1", "X");
            conversion.addEntry("entry1", "I");
        }, "Cannot have multiple entries with the same key");

        assertThrows(DuplicatedConversionKey.class, () -> {
            IntergalacticConversion conversion = new IntergalacticConversion();
            conversion.addEntry("entry1", "X");
            conversion.addEntry("Entry1", "I");
        }, "Keys are case insensitive");

        assertThrows(DuplicatedConversionValue.class, () -> {
            IntergalacticConversion conversion = new IntergalacticConversion();
            conversion.addEntry("entry1", "X");
            conversion.addEntry("entry2", "X");
        }, "Cannot have multiple entries with the same value");
    }

    @Test
    void invalidValueTest(){
        assertThrows(IllegalRomanNumeralException.class, () -> {
           IntergalacticConversion conversion = new IntergalacticConversion();
           conversion.addEntry("entry", "T");
        });

        assertThrows(IllegalRomanNumeralException.class, () -> {
            IntergalacticConversion conversion = new IntergalacticConversion();
            conversion.addEntry("entry", "i");
        });

        assertThrows(IllegalRomanNumeralException.class, () -> {
            IntergalacticConversion conversion = new IntergalacticConversion();
            conversion.addEntry("entry", "XX");
        });
    }
}
