import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
    private int max = 10;
    private int turn = 1;
    private int score = 0;
    private int currentFrame = 1;   //Current frame in play, starts at frame 1 and ends at frame 10.
    private int currentPlayer = 0;  //Players index in the table, starts at index 0.
    private boolean hasThird = false;

    private MainProg() {
        allPlayers = new DefaultListModel<>();
        playerList.setModel(allPlayers);
        playerTable.setModel(model);

        addNewButton.addActionListener(e -> {
            if (playerCount < 8) {
                String name = JOptionPane.showInputDialog("Enter a player Name: ");
                if (name == "") {
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
                for (int i = 0; i < playerCount; i++) {
                    String playerID = "" + laneNo.getText() + i;
                    String name = allPlayers.getElementAt(i);
                    int finalScore = 0;
                    int lane = Integer.parseInt(laneNo.getText());
                    players.addPlayer(new Player(playerID, name, finalScore, lane));
                }
                newGame.setVisible(false);
                runGame.setVisible(true);
                MainPanel.revalidate();
                MainPanel.repaint();
                GameSetup();
                playerTable.setSelectionBackground(Color.CYAN);
                playerTable.setRowSelectionInterval(0,0);

            }
        });

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
    }

    private int BowlBall(int max){
        Random rand = new Random();
        return( rand.nextInt(max) + 1);
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
        if (currentFrame == 10) {
            Frame10(score1, score2, storeScore, player);
        } else {
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
            JOptionPane.showMessageDialog(null, overallScore);
        }
    }
    private void StrikeHandle(Player player, int score1, int score2){
        String current = player.getScore().getScores().get(currentFrame);
        String lastframe1 = player.getScore().getScores().get(currentFrame-1);
        String lastframe2 = player.getScore().getScores().get(currentFrame-2);
        if (lastframe1.contains("X")&&lastframe2.contains("X")){
            int overall = player.getOverallScore();
            player.setOverallScore(overall + 10 + score1);
        }else if ( !current.contains("X") && lastframe1.contains("X")){
            int overall = player.getOverallScore();
            player.setOverallScore(overall  + score1 + score2);
        }
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


    private void Frame10(int score1, int score2, String storeScore ,Player player){

    }

    private void Bowl10(){
        if (turn == 1){
            score = BowlBall(10);
            if (score == 10){ turn ++; hasThird = true;}  // Strike}
        }

        if (turn == 2){
            int score2 = BowlBall(max - score);
            if (score + score2 == 10){turn ++; hasThird = true;}  // Spare}
        }

        if (turn == 3 && hasThird){
            int score3 = BowlBall(10);
            turn = 0;
            nextPlayer();
        }
        else {turn = 0; nextPlayer();}
    }

    private void nextPlayer(){
        currentPlayer ++;
        int pc = playerTable.getRowCount();
        pc --;
        if (currentPlayer > pc){
            currentPlayer = 0;
            if (currentFrame < 11){
                currentFrame++;
            }
        }
        playerTable.setSelectionBackground(Color.CYAN);
        try {
            playerTable.setRowSelectionInterval(currentPlayer, currentPlayer);
        } catch (Exception e) {
            currentPlayer = 0;
            playerTable.setRowSelectionInterval(currentPlayer, currentPlayer);
        }

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainProg");
        frame.setContentPane(new MainProg().MainPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}






