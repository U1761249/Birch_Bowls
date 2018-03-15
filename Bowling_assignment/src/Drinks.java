import java.util.ArrayList;
import java.util.Arrays;


public class Drinks extends Refreshments {

    public Drinks(String productName, String productPrice) {
        super(productName, productPrice);
    }

    public Drinks (){}


    //Define items on the drinks menu and their prices.
    public Drinks DrinksMenu(int i){
        Drinks d1 = new Drinks("Tea", "£2.30");
        Drinks d2 = new Drinks("Coffee", "£2.40");
        Drinks d3 = new Drinks("Coke", "£2.50");
        Drinks d4 = new Drinks("Pepsi", "£2.10");
        Drinks d5 = new Drinks("Lemonade", "£1.95");
        Drinks d6 = new Drinks("Orange Juice", "£2.10");
        Drinks d7 = new Drinks("Jug of Water", "£1.50");

        ArrayList<Drinks> drinkList = new ArrayList<>(Arrays.asList(d1, d2, d3, d4, d5, d6, d7));

        return drinkList.get(i);

    }

}
