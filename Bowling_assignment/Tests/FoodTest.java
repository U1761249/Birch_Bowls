import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FoodTest {

    @Test
    public void getName() {
        Food f1 = new Food("Hot Dog", "£3.30");
        assertEquals("Hot Dog", f1.getProductName());
    }

    @Test
    public void getPrice() {
        Food f1 = new Food("Hot Dog", "£3.30");
        assertEquals("£3.30", f1.getProductPrice());
    }
}