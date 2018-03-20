import java.util.Arrays;

public class Order {

	private String orderID;
	private String[] items;
	private String[] prices;


	//Get and set infrastructure that can be used to process the order in a genuine use case.
    public String[] getItems(){return this.items;}
    public String[] getPrices(){return this.prices;}

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