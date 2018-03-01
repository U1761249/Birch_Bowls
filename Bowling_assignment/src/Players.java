import java.util.ArrayList;

class Players {

    public ArrayList<Player> getPlayers() { return players; }

    public void setPlayers(ArrayList<Player> players) { this.players = players; }

    public void addPlayer(Player person){ players.add(person); }

    ArrayList<Player> players = new ArrayList<>();

}
