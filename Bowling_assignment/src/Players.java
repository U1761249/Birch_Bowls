import java.util.ArrayList;

class Players {

    ArrayList<Player> players = new ArrayList<>();

    public void addPlayer(Player person){ players.add(person); }

    public int getSize(){return players.size();}

}
