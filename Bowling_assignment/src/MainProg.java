import com.sun.deploy.util.StringUtils;
import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class MainProg {
    private DefaultListModel<String> allPlayers;
    private int playerCount = 0;
    private JPanel newGame;
    private JPanel setupNorth;
    private JPanel setupSouth;
    private JPanel setupCentre;
    private JList playerList;
    private JLabel mainName;
    private JButton clearAllButton;
    private JButton removeSelectedButton;
    private JButton addNewButton;
    private JButton STARTButton;
    private JTextField laneNo;
    private JPanel MainPanel;
    private JPanel runGame;
    private JPanel runNorth;
    private JPanel runSouth;
    private JPanel runCentre;
    private String TABLE_HEADINGS[] = new String[] {"Name", "Frame 1", "Frame 2", "Frame 3", "Frame 4", "Frame 5", "Frame 6", "Frame 7", "Frame 8", "Frame 9", "Frame 10", "Overall"};
    private Players players = new Players();
    private DefaultTableModel model = new DefaultTableModel();
    private JTable playerTable;
    private JButton orderButton;
    private JButton bowlButton;
    private JButton endButton;
    private JPanel centreNorth;
    private JTextField lastHits;
    private int max = 10;
    private int turn = 1;
    private int score = 0;
    private int currentFrame = 1;   //Current frame in play, starts at frame 1 and ends at frame 10.
    private int currentPlayer = 0;  //Players index in the table, starts at index 0.
    private boolean hasThird = false;
    int score1;
    int score2;
    int score3;

    private MainProg() {
        allPlayers = new DefaultListModel<>();
        playerList.setModel(allPlayers);
        playerTable.setModel(model);

        addNewButton.addActionListener(e -> {
            if (playerCount < 8) {
                String name = JOptionPane.showInputDialog("Enter a player Name: ");
                if (name.length() == 0) {
                    JOptionPane.showMessageDialog(null, "Enter a name.");
                } else {
                    allPlayers.addElement(name);
                    playerCount++;
                }
            } else {
                JOptionPane.showMessageDialog(null, "You already have a full lane.");
            }
        });

        removeSelectedButton.addActionListener(e -> {
            int selected = playerList.getSelectedIndex();
            if (selected == -1) {
                JOptionPane.showMessageDialog(null, "Nothing selected.");
            } else {
                allPlayers.removeElementAt(selected);
                playerCount--;
            }
        });

        clearAllButton.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(null, "This will remove all players. Continue?");
            if (choice == JOptionPane.YES_OPTION) {
                allPlayers.clear();
                playerCount = 0;
            }
        });


        STARTButton.addActionListener(e -> {

            if (laneNo.getText().length() != 1) {
                JOptionPane.showMessageDialog(null, "Enter a lane.");
            } else {
                if (playerCount >= 2) {
                    int lane = 0;
                    try {
                        lane = Integer.parseInt(laneNo.getText());
                    } catch (NumberFormatException e1) {
                        JOptionPane.showMessageDialog(null, "Lane number MUST BE A NUMBER");
                        lane = -1;
                    }
                    if (lane != -1) {
                        for (int i = 0; i < playerCount; i++) {
                            String playerID = "" + laneNo.getText() + i;
                            String name = allPlayers.getElementAt(i);
                            int finalScore = 0;
                            players.addPlayer(new Player(playerID, name, finalScore, lane));
                        }

                        newGame.setVisible(false);
                        runGame.setVisible(true);
                        MainPanel.revalidate();
                        MainPanel.repaint();
                        GameSetup();
                        playerTable.setSelectionBackground(Color.CYAN);
                        playerTable.setRowSelectionInterval(0, 0);

                    } else {
                        JOptionPane.showMessageDialog(null, "Must include 2 or more players.");
                    }
                }}});

        laneNo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                String c = laneNo.getText();
                if (c.length() > 0) {
                    laneNo.setText("" + c.substring(0, c.length() - 1));
                }
            }
        });

        bowlButton.addActionListener(e -> {
            if (currentFrame != 10){
            if (turn == 1) {
                score = BowlBall(max);
                lastHits.setText("Last hit: " + score);
                if (score == 10) {
                    JOptionPane.showMessageDialog(null, "STRIKE!");
                    Strike();
                    nextPlayer();
                } else {
                    turn++;
                }
            } else {
                max = max - score;
                int score2 = BowlBall(max);
                lastHits.setText("Last hit: " + score2);
                if (score + score2 == 10) {
                    JOptionPane.showMessageDialog(null, "SPARE!");
                    Spare(score, score2);
                } else {
                    int total = score + score2;
                    JOptionPane.showMessageDialog(null, "Hit " + total + " of 10.");
                    UpdateScore(score, score2);
                }
                score = 0;
                turn = 1;
                max = 10;
                nextPlayer();
            }}
            else{Bowl10();}
        });
        endButton.addActionListener(e -> {
            newGame.setVisible(true);
            runGame.setVisible(false);
            allPlayers.clear();
            centreNorth.setVisible(false);
            setupSouth.setVisible(false);

            Display();
            String first = allPlayers.get(0);
            String winner = first.substring(6);
            mainName.setSize(20,20);
            mainName.setText("Complete! Well done " + winner + "!");
        });
    }

    private void Display() {

        while (players.getSize() != 0) {
            Player player = players.players.get(0);
            int overall = player.getOverallScore();
            String toStore;
            if (overall < 100){toStore = "0" +player.getOverallScore() + "   "; }
            else{toStore = "" + player.getOverallScore() + "   ";}
            toStore = toStore + player.getPlayerName();
            allPlayers.addElement(toStore);
            players.players.remove(0);
        }

        Sort();
    }

    private void Sort() {
        int toSort = allPlayers.size()-1;
        for (int i = 0; i < allPlayers.size(); i++){
            for (int j = 0; j < toSort; j++){
                String sLine1 = allPlayers.get(j);
                String sLine2 = allPlayers.get(j+1);
                int line1 = Integer.parseInt(sLine1.substring(0,3));
                int line2 = Integer.parseInt(sLine2.substring(0,3));

                if (line1 < line2){
                    allPlayers.setElementAt(sLine1, j+1);
                    allPlayers.setElementAt(sLine2, j+0);

                }

            }
            toSort--;
        }
    }

    private int BowlBall(int max){
        Random rand = new Random();
        return( rand.nextInt(max+1));
    }

    private void GameSetup(){
        for (String title : TABLE_HEADINGS){
            model.addColumn(title);
        }
        players.players.forEach(player -> PlayerSetup(player));
    }

    private void Strike(){
        Player player = players.players.get(currentPlayer);
        String storeScore = "    X";
        ScoreHandler(10, 0, storeScore, player);
    }

    private void Spare(int score1, int score2){
        Player player = players.players.get(currentPlayer);
        String storeScore = "" + score1 + "  | " + "/";
        ScoreHandler(score1, score2, storeScore, player);
    }

    private void UpdateScore(int score1, int score2) {
        Player player = players.players.get(currentPlayer);
        String storeScore = "" + score1 + "  | " + score2;
        ScoreHandler(score1, score2, storeScore, player);
    }
    private void ScoreHandler(int score1, int score2, String storeScore ,Player player) {

            if (currentFrame != 1) {
                String lastScore = player.getScore().getScores().get(currentFrame - 1);
                if (lastScore.contains("/")) {
                    score1 = score1 * 2;
                }
                if (lastScore.contains("X")) {
                    StrikeHandle(player, score1, score2);
                }

            }
            int overallScore = player.getOverallScore();
            player.setOverallScore(overallScore + score1 + score2);
            overallScore = player.getOverallScore();
            player.getScore().getScores().set(currentFrame, storeScore);
            model.setValueAt(storeScore, currentPlayer, currentFrame);
            String overallString = "" + overallScore;
            model.setValueAt(overallString, currentPlayer, playerTable.getColumnCount() - 1);
        }

    private void StrikeHandle(Player player, int score1, int score2){
        String current = player.getScore().getScores().get(currentFrame);
        String lastframe1 = player.getScore().getScores().get(currentFrame-1);
        String lastframe2 = player.getScore().getScores().get(currentFrame-2);
        int overall = player.getOverallScore();

        if (lastframe1.contains("X")&&lastframe2.contains("X")){
            player.setOverallScore(overall + 10 + score1);

        }else if ( !current.contains("X") && lastframe1.contains("X")){
            player.setOverallScore(overall  + score1 + score2);
        }
    }
    private void ScoreHandle2(Player player, int score1, int score2){
        int throw1 = score1;
        int throw2 = score2;
        int overall = player.getOverallScore();
        String frame9 = player.getScore().getScores().get(currentFrame-1);

        if (frame9.contains("X")){overall = overall + throw1 + throw2;}
        if (frame9.contains("/")){overall = overall + throw1;}

        player.setOverallScore(overall);

    }



    private String[] SetRowData(Player player){
        String name = player.getPlayerName();
        String overall = "" + player.getOverallScore();
        String frame0 = player.getScore().getScores().get(0);
        String frame1 = player.getScore().getScores().get(1);
        String frame2 = player.getScore().getScores().get(2);
        String frame3 = player.getScore().getScores().get(3);
        String frame4 = player.getScore().getScores().get(4);
        String frame5 = player.getScore().getScores().get(5);
        String frame6 = player.getScore().getScores().get(6);
        String frame7 = player.getScore().getScores().get(7);
        String frame8 = player.getScore().getScores().get(8);
        String frame9 = player.getScore().getScores().get(9);

        String data[] = {name, frame0, frame1, frame2, frame3, frame4, frame5, frame6, frame7, frame8, frame9, overall};
        return data;
    }

    private void PlayerSetup(Player player){
        String[] data = SetRowData(player);
        model.addRow(data);

    }


    private void Frame10(int score1, int score2, int score3 ,Player player){
        player.setOverallScore( player.getOverallScore() + score1 + score2 + score3);
        ScoreHandle2(player, score1, score2);

        String stScore1 = "" + score1;
        String stScore2 = "" + score2;
        String stScore3 = "" + score3;

        if (score1 == 10){stScore1 = "X";} // was a Strike
        if (score1 + score2 == 10){stScore2 = "/";} //was a Spare
        if (score2 == 10){stScore2 = "X";} //was a Strike
        if (score3 == 10){stScore3 = "X";} //was a Strike
        if (hasThird == false){stScore3 = "-";}

        String storeScore = stScore1 + "  | " + stScore2 + "  | " + stScore3;

        model.setValueAt(storeScore, currentPlayer, currentFrame);
        model.setValueAt(player.getOverallScore(), currentPlayer, playerTable.getColumnCount() - 1);
    }

    private void Bowl10(){
        Player player = players.players.get(currentPlayer);

        if (turn == 1){
            max = 10;
            score1 = BowlBall(max);
            lastHits.setText("Last hit: " + score1);
            if (score1 == 10){ hasThird = true;
            JOptionPane.showMessageDialog(null,"STRIKE!");}  // Strike}
            else {max = 10 - score1;}
        }

        else if (turn == 2){
            score2 = BowlBall(max);
            lastHits.setText("Last hit: " + score2);
            if (score1 + score2 == 10){hasThird = true; max = 10;
            JOptionPane.showMessageDialog(null, "SPARE!");}// Spare
            else{max = 10 - score2;}
            if (hasThird == false){
                JOptionPane.showMessageDialog(null, "Hit " + (score1+score2) + " of 10.");
                    Frame10(score1, score2, 0, player);
                    turn = 0;
                    nextPlayer();}
        }

       else if (turn == 3 && hasThird){
            score3 = BowlBall(max);
            lastHits.setText("Last hit: " + score3);
            turn = 0;
            Frame10(score1, score2, score3, player);
            nextPlayer();
        }

        turn ++;
    }

    private void nextPlayer(){

        score = 0;
        score1 = 0;
        score2 = 0;
        score3 = 0;
        max = 10;
        hasThird = false;

            currentPlayer++;
            int pc = playerTable.getRowCount();
            pc--;

            if (currentPlayer > pc) {
                currentPlayer = 0;
                currentFrame++;
                if (currentFrame == 11){bowlButton.setVisible(false);
                endButton.setVisible(true);}

            }
            playerTable.setSelectionBackground(Color.CYAN);
            playerTable.setRowSelectionInterval(currentPlayer, currentPlayer);

        }


    public static void main(String[] args) {
        JFrame frame = new JFrame("MainProg");
        frame.setContentPane(new MainProg().MainPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}






