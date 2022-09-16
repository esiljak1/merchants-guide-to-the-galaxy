import com.esiljak.exceptions.IllegalItemNameException;
import com.esiljak.exceptions.IllegalPriceException;
import com.esiljak.exceptions.IllegalQuantityException;
import com.esiljak.models.SellingItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SellingItemTests {
    private final String ITEM_NAME = "name";
    private final float PRICE = 12f;
    private final int QUANTITY = 3;
    private SellingItem item;

    @BeforeEach
    void initialize() throws Exception{
        item = new SellingItem(ITEM_NAME, PRICE, QUANTITY);
    }

    @Test
    void constructorTest(){
        assertEquals(ITEM_NAME, item.getName(), "Name not properly set through constructor");
        assertEquals(PRICE / QUANTITY, item.getPrice(), "Price not properly set through constructor");
    }

    @Test
    void noQuantityConstructorTest() throws Exception{
        item = new SellingItem(ITEM_NAME, PRICE);

        assertEquals(PRICE, item.getPrice(), "Price not properly set through constructor");
    }

    @Test
    void setterTest() throws Exception{
        item.setName(ITEM_NAME + "2");
        assertEquals(ITEM_NAME + "2", item.getName(), "Name not properly set through setter");

        item.setPrice(PRICE * 2);
        assertEquals(PRICE * 2, item.getPrice(), "Price not properly set through setter");
    }

    @Test
    void negativePriceTest(){
        assertThrows(IllegalPriceException.class, () -> {
           new SellingItem(ITEM_NAME, -1f);
        }, "Price cannot be negative - constructor");

        assertThrows(IllegalPriceException.class, () -> {
           item = new SellingItem(ITEM_NAME, PRICE, QUANTITY);
           item.setPrice(-1f);
        }, "Price cannot be negative - setter");

        assertDoesNotThrow(() -> {
            item = new SellingItem(ITEM_NAME, 0, QUANTITY);
            item.setPrice(0);
        }, "Price can be set to 0");
    }

    @Test
    void emptyNameTest(){
        assertThrows(IllegalItemNameException.class, () -> {
            new SellingItem("", PRICE);
        }, "Name cannot be empty - constructor");

        assertThrows(IllegalItemNameException.class, () -> {
           SellingItem item = new SellingItem(ITEM_NAME, PRICE);
           item.setName("   ");
        }, "Name cannot be empty - setter");

        assertThrows(IllegalItemNameException.class, () -> {
            new SellingItem(null, PRICE);
        }, "Name cannot be null - constructor");

        assertThrows(IllegalItemNameException.class, () -> {
            SellingItem item = new SellingItem(ITEM_NAME, PRICE);
            item.setName(null);
        }, "Name cannot be null - setter");
    }

    @Test
    void nonPositiveQuantityTest(){
        assertThrows(IllegalQuantityException.class, () -> {
           new SellingItem(ITEM_NAME, PRICE, 0);
        }, "Quantity cannot be 0 - constructor");

        assertThrows(IllegalQuantityException.class, () -> {
            new SellingItem(ITEM_NAME, PRICE, -1);
        }, "Quantity cannot be negative - constructor");
    }

    @Test
    void priceForQuantityTest() throws Exception{
        item = new SellingItem(ITEM_NAME, PRICE);

        assertEquals(PRICE * QUANTITY, item.getPrice(QUANTITY), "Price not generated correctly for quantity");
        assertEquals(PRICE * (QUANTITY + 2), item.getPrice(QUANTITY + 2), "Price not generated correctly for quantity");
        assertEquals(PRICE, item.getPrice(1), "Price not generated correctly for quantity");

        assertEquals(0, item.getPrice(0), "Price for quantity 0 and below has to be 0");
        assertEquals(0, item.getPrice(-1), "Price for quantity 0 and below has to be 0");
    }
}
