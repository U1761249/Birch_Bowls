import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class OrdersTest {

    Orders orders = new Orders();

    @Before
    public void setupOrders() {
        String[] items = {"Test","Test2"};
        String[] prices = {"£Test","£Test"};
        orders.addOrder(new Order("100",items,prices));
        String[] items2 = {"Test3","Test4"};
        String[] prices2 = {"£Test","£Test"};
        orders.addOrder(new Order("101",items2,prices2));
    }

    @Test
    public void getSizeTest() {
        assertEquals(2, orders.getSize());
    }

    @Test
    public void addOrderTest() {
        String[] items = {"Test5","Test6"};
        String[] prices = {"£Test","£Test"};
        orders.addOrder(new Order("102",items,prices));
        assertEquals(3, orders.getSize());
    }
}