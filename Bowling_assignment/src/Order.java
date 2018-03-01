public class Order {

	private String orderID;
	private String[] items;
	private float[] prices;

	public String getOrderID() { return orderID; }

	public void setOrderID(String orderID) { this.orderID = orderID; }

	public String[] getItems() { return items; }

	public void setItems(String[] items) { this.items = items; }

	public float[] getPrices() { return prices; }

	public void setPrices(float[] prices) { this.prices = prices; }
}