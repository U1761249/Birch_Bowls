import java.util.ArrayList;

public class Orders {

    public ArrayList<Order> getOrders() { return orders; }

    public void setOrders(ArrayList<Order> orders) { this.orders = orders; }

    public void addOrder(Order order){ orders.add(order); }

    public int getSize(){return orders.size();}

    ArrayList<Order> orders = new ArrayList<>();
}
