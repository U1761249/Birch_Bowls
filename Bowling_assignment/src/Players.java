import java.util.ArrayList;

class Players {

    public ArrayList<Player> getPlayers() { return players; }

    public void setPlayers(ArrayList<Player> players) { this.players = players; }

    public void addPlayer(Player person){ players.add(person); }

    public int getSize(){return players.size();}

    ArrayList<Player> players = new ArrayList<>();

}
