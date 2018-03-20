import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest { //Confirm the correct construction of the player class.

    Player player = new Player("10", "Mr.Test", 0, 1);

    @Test
    public void getPlayerID() {
        assertEquals("10", player.getPlayerID());
    }

    @Test
    public void getPlayerName() {
        assertEquals("Mr.Test", player.getPlayerName());
    }

    @Test
    public void getOverallScore() {
        assertEquals(0, player.getOverallScore());
    }

    @Test
    public void setOverallScore() {
        player.setOverallScore(10);
        assertEquals(10, player.getOverallScore());
    }

    @Test
    public void getLane() {
        assertEquals(1, player.getLane());
    }
}