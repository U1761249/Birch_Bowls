import java.util.ArrayList;
import java.util.Arrays;

public class Food extends Refreshments{


    public Food(String productName, String productPrice) {
        super(productName, productPrice);
    }

    public Food (){}


    //Define items on the food menu and their prices.
    public Food FoodMenu(int i){
        Food f1 = new Food("Hot Dog", "£3.30");
        Food f2 = new Food("Beef Burger", "£4.20");
        Food f3 = new Food("Cheese Burger", "£4.40");
        Food f4 = new Food("Veggie Burger", "£4.00");
        Food f5 = new Food("Nachos for 1", "£2.20");
        Food f6 = new Food("Nachos to Share", "£4.10");
        Food f7 = new Food("Ice Cream", "£2.00");



        ArrayList<Food> foodList = new ArrayList<>(Arrays.asList( f1, f2, f3, f4, f5, f6, f7 ));

        return foodList.get(i);

    }

}
