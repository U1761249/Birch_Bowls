import java.util.ArrayList;
import java.util.Arrays;

public class Scoresheet {

    ArrayList<String> scores = new ArrayList<>(Arrays.asList("0","0","0","0","0","0","0","0","0","0"));

    public ArrayList<String> getScores() { return scores; }

    public void setScores(ArrayList<String> scores) { this.scores = scores; }

    public String getFrameScore(int frame){return scores.get(frame);}

    @Override
    public String toString() {
        return "scores=" + scores + '}';
    }
}

