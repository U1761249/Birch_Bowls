import org.junit.Test;

import static org.junit.Assert.*;

public class OrderTest {

    String[] items = {"Test1","Test2", "Test3"};
    String[] prices = {"£Test1", "£Test2", "£Test3"};

    Order order = new Order("100",items,prices);

    @Test
    public void addOrderTest() { //Test the order that was added to Orders.
        Orders orders = new Orders();
        orders.addOrder(order);

        Order test = orders.orders.get(0);
        String[] itemTest = test.getItems();
        String[] priceTest = test.getPrices();

        assertEquals(items, itemTest);
        assertEquals(prices, priceTest);
    }
}