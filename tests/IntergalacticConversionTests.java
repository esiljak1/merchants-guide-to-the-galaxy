import com.esiljak.exceptions.*;
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

    private void addConversionEntries() throws Exception{
        conversion.addEntry("clob is I");
        conversion.addEntry("blob is V");
        conversion.addEntry("slob is X");
    }

    @BeforeEach
    void initialize(){
        conversion = new IntergalacticConversion();
    }

    @Test
    void constructorTest(){
        assertTrue(conversion.getEntries().isEmpty(), "If no value is passed in constructor there should be no entries");
    }

    @Test
    void constructorWithParameterTest() throws Exception{
        conversion = new IntergalacticConversion(map);

        assertEquals(3, conversion.getEntries().size(), "Entries not properly set through constructor");
    }

    @Test
    void setterTest() throws Exception{
        conversion.setEntries(map);

        assertEquals("I", conversion.getEntries().get("name1"), "Entries not properly set through setter");
    }

    @Test
    void addingPairsTest() throws Exception {
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

    @Test
    void invalidKeyFormatTest(){
        assertThrows(IllegalKeyFormatException.class, () -> {
           conversion.addEntry("illegal key", "X");
        });

        assertThrows(IllegalKeyFormatException.class, () -> {
            conversion.addEntry("", "X");
        });

        assertThrows(IllegalKeyFormatException.class, () -> {
            conversion.addEntry("   ", "X");
        });

        assertThrows(IllegalKeyFormatException.class, () -> {
            conversion.addEntry("   key  ", "X");
        });

        assertThrows(IllegalKeyFormatException.class, () -> {
            conversion.addEntry(null, "X");
        });
    }

    @Test
    void basicParseEntryTest() throws Exception{
        conversion.addEntry("blob is I");

        assertEquals("I", conversion.getEntries().get("blob"), "Entry not parsed correctly");
    }

    @Test
    void additionalParseEntryTest() throws Exception{
        conversion.addEntry("blob is I");
        conversion.addEntry("rod is V");
        conversion.addEntry("lak is X");

        assertEquals(3, conversion.getEntries().size(), "Entries not added");
        assertEquals("V", conversion.getEntries().get("rod"), "Entry not added correctly");
        assertEquals("X", conversion.getEntries().get("lak"), "Entry not added correctly");
    }

    @Test
    void parseEntryExceptionTest(){
        assertThrows(DuplicatedConversionKeyException.class, () -> {
            conversion.addEntry("blob is I");
            conversion.addEntry("blob is V");
        }, "Cannot have multiple entries with the same key");

        assertThrows(DuplicatedConversionValueException.class, () -> {
            conversion.addEntry("flob is I");
            conversion.addEntry("clob is V");
        }, "Cannot have multiple entries with the same value");

        assertThrows(IllegalRomanNumeralException.class, () -> {
            conversion.addEntry("clob is i");
        });

        assertThrows(IllegalRomanNumeralException.class, () -> {
            conversion.addEntry("clob is T");
        });
    }

    @Test
    void illegalKeyFormatTest(){
        assertThrows(IllegalKeyFormatException.class, () -> {
            conversion.addEntry("clob k is I");
        });

        assertThrows(IllegalKeyFormatException.class, () -> {
            conversion.addEntry("is I");
        });

        assertThrows(IllegalKeyFormatException.class, () -> {
            conversion.addEntry("   is I");
        });
    }

    @Test
    void addSellingItemTest() throws Exception{
        addConversionEntries();
        conversion.addSellingItem("clob clob Iron is 50 Credits");
        assertEquals(25f, conversion.getSellingItem("Iron").getPrice());
    }

    @Test
    void additionalSellingItemTest() throws Exception{
        addConversionEntries();
        conversion.addSellingItem("clob blob Gold is 80 Credits");
        conversion.addSellingItem("blob clob clob clob Dirt is 1600 Credits");
        conversion.addSellingItem("slob Silver is 10 Credits");

        assertEquals(20f, conversion.getSellingItem("Gold").getPrice());
        assertEquals(200f, conversion.getSellingItem("Dirt").getPrice());
        assertEquals(1f, conversion.getSellingItem("Silver").getPrice());
    }

    @Test
    void invalidQuantityTest() throws Exception {
        addConversionEntries();

        assertThrows(IllegalRomanNumeralException.class, () -> {
           conversion.addSellingItem("blob slob Silver is 90 Credits");
        });

        assertThrows(IllegalRomanNumeralException.class, () -> {
            conversion.addSellingItem("blob blob Silver is 90 Credits");
        });

        assertThrows(IllegalRomanNumeralException.class, () -> {
            conversion.addSellingItem("clob clob clob clob Silver is 90 Credits");
        });

        assertThrows(IllegalSellingItemFormatException.class, () -> {
            conversion.addSellingItem("unknown Silver is 90 Credits");
        });
    }

    @Test
    void invalidSellingItemTest() throws Exception {
        addConversionEntries();

        assertThrows(IllegalSellingItemFormatException.class, () -> {
            conversion.addSellingItem("blob clob clob is 90 Credits");
        });

        assertThrows(IllegalSellingItemFormatException.class, () -> {
            conversion.addSellingItem("Silver is 90 Credits");
        });

        assertThrows(IllegalSellingItemFormatException.class, () -> {
            conversion.addSellingItem("   Silver is 90 Credits");
        });

        assertThrows(IllegalSellingItemFormatException.class, () -> {
            conversion.addSellingItem("blob is 90 Credits");
        });
    }

    @Test
    void invalidSellingItemFormatTest() throws Exception {
        addConversionEntries();

        assertThrows(IllegalPriceException.class, () -> {
            conversion.addSellingItem("blob Silver is -90 Credits");
        });

        assertThrows(IllegalSellingItemFormatException.class, () -> {
            conversion.addSellingItem("blob Silver is 90");
        });
    }
}
