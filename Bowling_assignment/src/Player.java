public class Player {

	private String playerID;
	private String playerName;
	private Scoresheet score; //composition. Player "Has-a" Scoresheet.
	private int overallScore;
	private int lane;

	public String getPlayerID() { return playerID; }

	public String getPlayerName() { return playerName; }

	public Scoresheet getScore() { return this.score;}

	public int getOverallScore() { return overallScore; }

	public void setOverallScore(int overallScore) { this.overallScore = overallScore; }

	public int getLane() { return lane; }

	public void setLane(int lane) { this.lane = lane; }

	public Player(String playerID, String playerName, int overallScore, int lane) {
		this.playerID = playerID;
		this.playerName = playerName;
		this.score = new Scoresheet();
		this.overallScore = overallScore;
		this.lane = lane;
	}

	@Override
	public String toString() {
		return "playerID='" + playerID + '\'' +
				", playerName='" + playerName + '\'' +
				", score=" + score +
				", overallScore=" + overallScore +
				", lane=" + lane +
                '\n' ;
	}
}
