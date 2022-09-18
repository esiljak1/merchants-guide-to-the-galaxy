import com.esiljak.exceptions.IllegalQueryException;
import com.esiljak.exceptions.IllegalSellingItemFormatException;
import com.esiljak.models.IntergalacticConversion;
import com.esiljak.models.PriceQuery;
import com.esiljak.models.QuantityQuery;
import com.esiljak.models.Query;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QueryTests {
    private static IntergalacticConversion conversion;
    private Query query;

    private static void addEntries() throws Exception{
        conversion.addEntry("clob is I");
        conversion.addEntry("blob is V");
        conversion.addEntry("slob is X");
        conversion.addEntry("fes is L");
    }

    private static void addSellingItems() throws Exception{
        conversion.addSellingItem("blob clob Silver is 1200 Credits");
        conversion.addSellingItem("slob clob Rock is 121 Credits");
        conversion.addSellingItem("slob fes Dirt is 40 Credits");
        conversion.addSellingItem("slob Calcium is 15000 Credits");
    }

    @BeforeAll
    static void initializeConversion() throws Exception{
        conversion = new IntergalacticConversion();
        addEntries();
        addSellingItems();
    }

    @Test
    void constructorTest(){
        query = new PriceQuery(conversion);
        assertNotNull(query);

        query = new QuantityQuery(conversion);
        assertNotNull(query);
    }

    @Test
    void quantityQuestionTest() throws Exception{
        query = new QuantityQuery(conversion);

        assertDoesNotThrow(() -> {
            query.queryAnswer("how much is fes");
        });

        assertEquals("slob fes is 40", query.queryAnswer("how much is slob fes"));
        assertEquals("fes slob clob clob is 62", query.queryAnswer("how much is fes slob clob clob"));
    }

    @Test
    void priceQuestionTest() throws Exception{
        query = new PriceQuery(conversion);

        assertDoesNotThrow(() -> {
            query.queryAnswer("how many Credits is slob Dirt");
        });

        assertEquals("clob clob Calcium is 3000.00 Credits", query.queryAnswer("how many credits is clob clob Calcium"));
        assertEquals("slob blob Rock is 165.00 Credits", query.queryAnswer("how many credits is slob blob Rock"));
    }

    @Test
    void illegalQuantityQueryTest(){
        query = new QuantityQuery(conversion);

        assertThrows(IllegalQueryException.class, () -> {
            query.queryAnswer("how many Credits is slob Dirt");
        });

        assertThrows(IllegalQueryException.class, () -> {
            query.queryAnswer("how much is  ");
        });

        assertThrows(IllegalSellingItemFormatException.class, () -> {
            query.queryAnswer("how much is cener");
        });

        assertThrows(IllegalQueryException.class, () -> {
            query.queryAnswer("hwo much is blob");
        });

        assertThrows(IllegalQueryException.class, () -> {
            query.queryAnswer("what is the meaning of life");
        });
    }

    @Test
    void illegalPriceQueryTest(){
        query = new PriceQuery(conversion);

        assertThrows(IllegalQueryException.class, () -> {
            query.queryAnswer("how much is blob");
        });

        assertThrows(IllegalQueryException.class, () -> {
            query.queryAnswer("how many Credits is  ");
        });

        assertThrows(IllegalQueryException.class, () -> {
            query.queryAnswer("how many is blob Silver");
        });

        assertThrows(IllegalSellingItemFormatException.class, () -> {
            query.queryAnswer("how many Credits is clob");
        });

        assertThrows(IllegalQueryException.class, () -> {
            query.queryAnswer("how much Credits is clob Iron");
        });
    }
}
