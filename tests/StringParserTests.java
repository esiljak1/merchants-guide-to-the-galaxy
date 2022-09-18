import com.esiljak.exceptions.IllegalKeyFormatException;
import com.esiljak.exceptions.IllegalQueryException;
import com.esiljak.exceptions.IllegalSellingItemFormatException;
import com.esiljak.helpers.StringParser;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StringParserTests {
    @Test
    void keyValuePairTest() throws Exception{
        List<String> list =  StringParser.getKeyValuePair("ajnc is I");

        assertEquals("ajnc", list.get(0).trim());
        assertEquals("I", list.get(1).trim());
    }

    @Test
    void illegalKeyValuePairTest(){
        assertThrows(IllegalKeyFormatException.class, () -> {
            StringParser.getKeyValuePair("ajnc is ");
        });

        assertThrows(IllegalKeyFormatException.class, () -> {
            StringParser.getKeyValuePair("is I");
        });
    }
    @Test
    void itemPriceTest() throws Exception{
        assertEquals(22f, StringParser.getItemPrice("ajnc Gold is 22 Credits"));
        assertEquals(82.3f, StringParser.getItemPrice("glob glob Silver is 82.3 Credits"));
        assertEquals(-10f, StringParser.getItemPrice("glob Dirt is -10 Credits"));
    }

    @Test
    void illegalItemPriceTest(){
        assertThrows(IllegalSellingItemFormatException.class, () -> {
            StringParser.getItemPrice("glob glob Silver 82.3 Credits");
        });

        assertThrows(IllegalSellingItemFormatException.class, () -> {
            StringParser.getItemPrice("glob glob Silver is Credits");
        });

        assertThrows(IllegalSellingItemFormatException.class, () -> {
            StringParser.getItemPrice("glob glob Silver is eighty Credits");
        });

        assertThrows(IllegalSellingItemFormatException.class, () -> {
            StringParser.getItemPrice("glob glob Silver is really 82.3 Credits");
        });
    }

    @Test
    void quantityQueryNumberCodeTest() throws Exception{
        List<String> list = StringParser.getNumberCodeForQuantityQuery("how much is glob blob ajnc");

        assertEquals(3, list.size());
        assertEquals("glob", list.get(0).trim());
        assertEquals("blob", list.get(1).trim());
        assertEquals("ajnc", list.get(2).trim());
    }

    @Test
    void illegalQuantityQueryNumberCodeTest(){
        assertThrows(IllegalQueryException.class, () -> {
            StringParser.getNumberCodeForQuantityQuery("how many is glob blob ajnc");
        });

        assertThrows(IllegalQueryException.class, () -> {
            StringParser.getNumberCodeForQuantityQuery("how much is");
        });

        assertThrows(IllegalQueryException.class, () -> {
            StringParser.getNumberCodeForQuantityQuery("hwo much is blob");
        });
    }

    @Test
    void quantityWithItemTest() throws Exception{
        List<String> list = StringParser.getQuantityWithItem("ajnc blob Iron is 24 Credits");

        assertEquals(3, list.size());
        assertEquals("ajnc", list.get(0).trim());
        assertEquals("blob", list.get(1).trim());
        assertEquals("Iron", list.get(2).trim());
    }

    @Test
    void illegalQuantityWithItemTest(){
        assertThrows(IllegalSellingItemFormatException.class, () -> {
            StringParser.getQuantityWithItem("ajnc blob Iron");
        });

        assertThrows(IllegalSellingItemFormatException.class, () -> {
            StringParser.getQuantityWithItem("ajnc blob Iron is");
        });

        assertThrows(IllegalSellingItemFormatException.class, () -> {
            StringParser.getQuantityWithItem(" is 24 Credits");
        });

        assertThrows(IllegalSellingItemFormatException.class, () -> {
            StringParser.getQuantityWithItem("Iron is 24 Credits");
        });
    }

    @Test
    void quantityForPriceQueryTest() throws Exception{
        List<String> list = StringParser.getQuantityWithItemForPriceQuery("how many Credits is ajnc blob glob Gold");

        assertEquals(4, list.size());
        assertEquals("ajnc", list.get(0).trim());
        assertEquals("blob", list.get(1).trim());
        assertEquals("glob", list.get(2).trim());
        assertEquals("Gold", list.get(3).trim());
    }

    @Test
    void illegalQuantityForPriceQueryTest(){
        assertThrows(IllegalQueryException.class, () -> {
           StringParser.getQuantityWithItemForPriceQuery("how much Credits is ajnc Gold");
        });

        assertThrows(IllegalQueryException.class, () -> {
            StringParser.getQuantityWithItemForPriceQuery("how many Credits is");
        });

        assertThrows(IllegalQueryException.class, () -> {
            StringParser.getQuantityWithItemForPriceQuery("hwo many Credits is Gold");
        });
    }
}
