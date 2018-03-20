import org.junit.Test;

import static org.junit.Assert.*;

public class DrinksTest {

    @Test
    public void getName() {
        Drinks d1 = new Drinks("Tea", "£2.30");
        assertEquals("Tea", d1.getProductName());
    }

    @Test
    public void getPrice() {
        Drinks d1 = new Drinks("Tea", "£2.30");
        assertEquals("£2.30", d1.getProductPrice());
    }

}