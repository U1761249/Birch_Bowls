import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayersTest {

    Players players = new Players();

    @Before //Create the players arraylist for testing before each test.
    public void setup(){

        players.addPlayer(new Player("10","Mr.Test", 0, 1));
        players.addPlayer(new Player("11","Mr.Test(1)", 0, 1));
    }


    @Test
    public void addPlayer() {
        players.addPlayer(new Player("12", "Adam",0, 1));

        Player t1 = new Player("10","Mr.Test", 0, 1);
        Player t2 = new Player("11","Mr.Test(1)", 0, 1);
        Player t3 = new Player("12","Adam", 0, 1);

        Players testValues = new Players();
        testValues.addPlayer(t1);
        testValues.addPlayer(t2);
        testValues.addPlayer(t3);

        for (int i = 0; i < players.getSize(); i++){
            Player current = (players.players.get(i));
            String currentName = current.getPlayerName();
            Player test = (testValues.players.get(i));
            String testName = test.getPlayerName();

            assertEquals(testName, currentName);
        }
    }

    @Test
    public void getSize() {
        assertEquals(2, players.getSize());
    }
}