import java.util.Arrays;

public class Order {

	private String orderID;
	private String[] items;
	private String[] prices;

	public String getOrderID() { return orderID; }

	public void setOrderID(String orderID) { this.orderID = orderID; }

	public String[] getItems() { return items; }

	public void setItems(String[] items) { this.items = items; }

	public String[] getPrices() { return prices; }

	public void setPrices(String[] prices) { this.prices = prices; }

    @Override
    public String toString() {
        return "Order{" +
                "orderID='" + orderID + '\'' +
                ", items=" + Arrays.toString(items) +
                ", prices=" + Arrays.toString(prices) +
                '}';
    }

    public Order(String orderID, String[] items, String[] prices) {
        this.orderID = orderID;
        this.items = items;
        this.prices = prices;
    }
}