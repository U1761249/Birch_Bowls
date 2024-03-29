import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;

public class MainProg {

    //Source and ID of the colour scheme used.

    //http://colorschemedesigner.com/csd-3.5/
    //4W52Pw0w0w0w0

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
    private String TABLE_HEADINGS[] = new String[]
            {"Name", "Frame 1", "Frame 2", "Frame 3", "Frame 4", "Frame 5",
                    "Frame 6", "Frame 7", "Frame 8", "Frame 9", "Frame 10", "Overall"};
    private Players players = new Players();
    private DefaultTableModel model = new DefaultTableModel();
    private DefaultTableModel foodModel = new DefaultTableModel();
    private DefaultTableModel drinksModel = new DefaultTableModel();
    private DefaultTableModel orderModel = new DefaultTableModel();
    private JTable playerTable;
    private JButton orderButton;
    private JButton bowlButton;
    private JButton endButton;
    private JPanel centreNorth;
    private JLabel lastHits;
    private JPanel setupSouthStart;
    private JPanel setupSouthRepeat;
    private JButton samePlayers;
    private JButton newPlayers;
    private JButton quit;
    private JPanel cafePanel;
    private JPanel cafeNorth;
    private JPanel foodMenu;
    private JButton addFood;
    private JTable foodTable;
    private JPanel drinksMenu;
    private JButton addDrink;
    private JTable drinksTable;
    private JPanel cafeCentre;
    private JPanel cafeCentreButtons;
    private JButton cancelOrderButton;
    private JButton clearOrderButton;
    private JTable playerOrder;
    private JButton removeOrder;
    private JButton placeOrderButton;
    private JLabel orderTotal;
    private JPanel cafeSouth;
    private JLabel currentPlayerDisplay;
    private JScrollPane playerSP;
    private JScrollPane orderSP;
    private JScrollPane drinkSP;
    private JScrollPane foodSP;
    private int max = 10;
    private int turn = 1;
    private int score = 0;
    private int currentFrame = 1;   //Current frame in play, starts at frame 1 and ends at frame 10.
    private int currentPlayer = 0;  //Players index in the table, starts at index 0.
    private boolean hasThird = false;
    int score1;
    int score2;
    int score3;

    public MainProg() {

        allPlayers = new DefaultListModel<>();
        playerList.setModel(allPlayers);
        playerTable.setModel(model);
        foodTable.setModel(foodModel);
        drinksTable.setModel(drinksModel);
        playerOrder.setModel(orderModel);

        addNewButton.addActionListener(e -> {
            addNew();
        });

        removeSelectedButton.addActionListener(e -> {
            removeSelected();
        });

        clearAllButton.addActionListener(e -> {
            clearAll();
        });


        STARTButton.addActionListener(e -> {

            InitializeGame();

        });

        laneNo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                //Check the user typed input, and set it to 1 character in length.
                super.keyTyped(e);
                laneCheck();
            }
        });

        bowlButton.addActionListener(e -> {
            bowlBall();
        });
        playerTable.getSelectionModel().addListSelectionListener(event -> // Prevent the user from selecting a row within the player table.
                playerTable.setRowSelectionInterval(currentPlayer, currentPlayer));

        endButton.addActionListener(e -> {
            endGame();
        });

        quit.addActionListener(e -> {
            System.exit(0);
        });

        samePlayers.addActionListener(e -> {

           newSamePlayers();
        });

        newPlayers.addActionListener(e -> {
            newNewPlayers();

        });

        orderButton.addActionListener(e -> {
            OpenCafe();
        });

        addFood.addActionListener(e -> {
            initFood();
        });

        addDrink.addActionListener(e -> {
            initDrinks();
        });

        clearOrderButton.addActionListener(e -> {
                    clearOrder();
        });

        removeOrder.addActionListener(e -> {
            removeSelectedOrder();
        });

        cancelOrderButton.addActionListener(e -> {
            ResetCafe();
        });

        placeOrderButton.addActionListener(e -> {
            ProcessOrder();
        });

    }

    //Methods for action listeners.
    //Separated to allow the test class to access the functions.

    public void addNew() {
        if (playerCount < 8) { //Don't overfill a lane.
            String name = JOptionPane.showInputDialog("Enter a player Name: ");
            if (name.length() == 0) { //Must enter a name.
                JOptionPane.showMessageDialog(null, "Enter a name.");
            } else {
                int count = 1;
                name = NameCheck(name, count, name.length());
                allPlayers.addElement(name);
                playerCount++;
            }
        } else {
            JOptionPane.showMessageDialog(null, "You already have a full lane.");
        }
    }

    public void removeSelected() {
        int selected = playerList.getSelectedIndex();
        if (selected == -1) {
            JOptionPane.showMessageDialog(null, "Nothing selected.");
        } else {
            allPlayers.removeElementAt(selected);
            playerCount--;
        }
    }

    public void clearAll() {
        int choice = JOptionPane.showConfirmDialog(null, "This will remove all players. Continue?");
        if (choice == JOptionPane.YES_OPTION) {
            allPlayers.clear();
            playerCount = 0;
        }
    }

    public void laneCheck() {
        String c = laneNo.getText();
        if (c.length() > 0) {
            laneNo.setText("" + c.substring(0, c.length() - 1));
        }
    }

    public void bowlBall() {
        if (currentFrame != 10){

            //Functions for normal frame functionality.
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
            }
            else {

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
        //10th frame functionality.
        else{Bowl10();}
    }

    public void endGame() {
        newGame.setVisible(true);
        runGame.setVisible(false);
        allPlayers.clear();
        centreNorth.setVisible(false);
        setupSouthStart.setVisible(false);
        setupSouthRepeat.setVisible(true);

        Display();
        String first = allPlayers.get(0);
        String winner = first.substring(6);
        mainName.setSize(20,20);
        mainName.setText("Complete! Well done " + winner + "!");
    }

    public void newSamePlayers() {
        resetModel();
        playerCount = allPlayers.size();
        boolean doShuffle = true;
        String[] options = {"Shuffle","This order"}; //Allow player order to be shuffled, or in score descending order.
        int shuffle = JOptionPane.showOptionDialog(null,
                "Do you want to play this order, or a random order?",
                "Choose an option",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                "Shuffle");
        if (shuffle == 0){doShuffle = true;}     //Shuffle
        else if(shuffle == 1){doShuffle = false;}      //This order

        resetGame(doShuffle);
    }

    public void newNewPlayers() {
        //Reset initial screen for new players.
        allPlayers.clear();
        playerCount = 0;
        resetModel();
        setupSouthRepeat.setVisible(false);
        setupSouthStart.setVisible(true);
        centreNorth.setVisible(true);
        mainName.setText("Welcome to Birch Bowling");
    }

    public void initFood() {
        int index = foodTable.getSelectedRow();
        if (index > -1) { //Check that an item is selected.
            Object name = foodTable.getValueAt(index, 0);
            Object price = foodTable.getValueAt(index, 1);
            Object[] toStore = {name, price};
            orderModel.addRow(toStore);
            AddToOrderTotal();
        }
    }

    public void initDrinks() {
        int index = drinksTable.getSelectedRow();
        if (index > -1) { //Check that an item is selected.
            Object name = drinksTable.getValueAt(index, 0);
            Object price = drinksModel.getValueAt(index, 1);
            Object[] toStore = {name, price};
            orderModel.addRow(toStore);
            AddToOrderTotal();
        }
    }

    public void clearOrder() {
        int clearConfirm = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to clear the order?");
        if (clearConfirm == 0) {
            //Reset the order to its empty state.
            orderModel.setRowCount(0);
            orderTotal.setText("£0");
        }
    }

    public void removeSelectedOrder() {
        //Remove selected item from order.
        int targetRow = playerOrder.getSelectedRow();
        if (targetRow > -1){
            orderModel.removeRow(targetRow);
            orderTotal.setText("£0");
            OrderTotal();
        }
    }


    //Setting the Player data.

    public String NameCheck(String name, int count, int length) {
        //Adds a number to a name to make it unique, EG Player and Player(1).
        for(int i = 0; i < allPlayers.size(); i++){
            if (allPlayers.contains(name)){
                if (name.length() > length){
                    //If input name has already been altered.
                    name = name.substring(0, name.length() - 3);
                }
            name = name + "(" + count + ")";
            count ++;
            }
        }
        return name;

    }

    public String[] SetRowData(Player player){
        String name = player.getPlayerName();
        String overall = "" + player.getOverallScore();
        String frame0 = player.score().getScores().get(0);
        String frame1 = player.score().getScores().get(1);
        String frame2 = player.score().getScores().get(2);
        String frame3 = player.score().getScores().get(3);
        String frame4 = player.score().getScores().get(4);
        String frame5 = player.score().getScores().get(5);
        String frame6 = player.score().getScores().get(6);
        String frame7 = player.score().getScores().get(7);
        String frame8 = player.score().getScores().get(8);
        String frame9 = player.score().getScores().get(9);

        String data[] = {name, frame0, frame1, frame2, frame3, frame4, frame5, frame6, frame7, frame8, frame9, overall};
        return data;
    }

    public void PlayerSetup(Player player){
        String[] data = SetRowData(player);
        model.addRow(data);

    }

    //Set up the game.

    public void InitializeGame() {
        lastHits.setText("No pins have been struck.");
        endButton.setVisible(false);
        bowlButton.setVisible(true);
        currentFrame = 1;
        currentPlayer = 0;
        JTableHeader header = playerTable.getTableHeader();
        header.setBackground(new Color( 170, 0, 210 ));
        header.setForeground(Color.white);

        if (laneNo.getText().length() != 1) {
            JOptionPane.showMessageDialog(null, "Enter a lane.");
        } else {
            if (playerCount >= 2) {
                int lane;
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
                    String currentPlayerName = (players.players.get(0).getPlayerName());
                    currentPlayerDisplay.setText(currentPlayerName + "'s turn.");
                    playerTable.setRowSelectionInterval(0, 0);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Must include 2 or more players."); }

        }
    }

    public void GameSetup(){
        playerSP.getViewport().setBackground( new Color( 210,95,210 ));
        if (model.getColumnCount() == 0) {
            for (String title : TABLE_HEADINGS){
                //Add a column for each title, if the table hasn't been initialised already (First game only).
                model.addColumn(title);
            }
        }
        //Add each player to the table.
        players.players.forEach(player -> PlayerSetup(player));
    }

    public void resetModel() {
        if (model.getRowCount() > 0) {
            for (int i = model.getRowCount() - 1; i > -1; i--) {
                model.removeRow(i);
            }
        }
    }

    public void resetGame(boolean doShuffle) {
        for (int i = 0; i < playerCount; i++){
            String name = allPlayers.getElementAt(i);
            name = name.substring(6);
            allPlayers.setElementAt(name, i);
        }

        if (doShuffle){
            ArrayList<String> tempList = new ArrayList<>();
            for (int i = 0; i < playerCount; i++){
                tempList.add(allPlayers.getElementAt(i));
            }
            Collections.shuffle(tempList); //Shuffle the order of the players.
            allPlayers.clear();
            for (String name : tempList){allPlayers.addElement(name);}
        }
        InitializeGame();
    }

    //Play the game.

    public int BowlBall(int max){
        Random rand = new Random();
        int score = rand.nextInt(max+1);
        if (score == 0){score = rand.nextInt(max+1);} //Reduce the frequency of misses.
        // More likely to miss the fewer pins there are remaining.
        return( score);
    }

    public void Strike(){
        Player player = players.players.get(currentPlayer);
        String storeScore = "    X";
        ScoreHandler(10, 0, storeScore, player);
    }

    public void Spare(int score1, int score2){
        Player player = players.players.get(currentPlayer);
        String storeScore = "" + score1 + "  | " + "/";
        ScoreHandler(score1, score2, storeScore, player);
    }

    public void UpdateScore(int score1, int score2) {
        Player player = players.players.get(currentPlayer);
        String storeScore = "" + score1 + "  | " + score2;
        ScoreHandler(score1, score2, storeScore, player);
    }

    public void ScoreHandler(int score1, int score2, String storeScore ,Player player) {

            if (currentFrame != 1) { //Prevent Index out of Range exception.
                String lastScore = player.score().getScores().get(currentFrame - 1);
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
            player.score().getScores().set(currentFrame, storeScore);
            model.setValueAt(storeScore, currentPlayer, currentFrame);
            String overallString = "" + overallScore;
            model.setValueAt(overallString, currentPlayer, playerTable.getColumnCount() - 1);
        }

    public void StrikeHandle(Player player, int score1, int score2){
        String current = player.score().getScores().get(currentFrame);
        String lastframe1 = player.score().getScores().get(currentFrame-1);
        String lastframe2 = player.score().getScores().get(currentFrame-2);
        int overall = player.getOverallScore();

        if (lastframe1.contains("X")&&lastframe2.contains("X")){
            player.setOverallScore(overall + 10 + score1);

        }else if ( !current.contains("X") && lastframe1.contains("X")){
            player.setOverallScore(overall  + score1 + score2);
        }
    }

    //Differs from original for last frame functionality.
    public void ScoreHandle2(Player player, int score1, int score2){
        int throw1 = score1;
        int throw2 = score2;
        int overall = player.getOverallScore();
        String frame9 = player.score().getScores().get(currentFrame-1);

        if (frame9.contains("X")){overall = overall + throw1 + throw2;}
        if (frame9.contains("/")){overall = overall + throw1;}

        player.setOverallScore(overall);

    }

    public void Frame10(int score1, int score2, int score3 ,Player player){
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

    public void Bowl10(){
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

       else if (turn == 3 && hasThird){ //Only has third throw if the player got a Strike or a Spare.
            score3 = BowlBall(max);
            lastHits.setText("Last hit: " + score3);
            turn = 0;
            Frame10(score1, score2, score3, player);
            nextPlayer();
        }

        turn ++;
    }

    public void nextPlayer(){

        score = 0;
        score1 = 0;
        score2 = 0;
        score3 = 0;
        max = 10;
        hasThird = false;

            currentPlayer++;
            int pc = playerTable.getRowCount();
            pc--; //One row is for Headers.

            if (currentPlayer > pc) {
                currentPlayer = 0;
                currentFrame++;
                if (currentFrame == 11){bowlButton.setVisible(false);
                endButton.setVisible(true);}

            }
        String currentPlayerName = (players.players.get(currentPlayer).getPlayerName());
        currentPlayerDisplay.setText(currentPlayerName + "'s turn.");
            playerTable.setRowSelectionInterval(currentPlayer, currentPlayer);

        }

    //Display the Winner.

    public void Display() {

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

    public void Sort() { //Bubble sort Players.
        int toSort = allPlayers.size()-1;
        for (int i = 0; i < allPlayers.size(); i++){
            for (int j = 0; j < toSort; j++){
                String sLine1 = allPlayers.get(j);
                String sLine2 = allPlayers.get(j+1);
                //Get the scores for the players.
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

    //Cafe functionality.

    public void OpenCafe() {
        orderTotal.setText("£0");
        orderSP.getViewport().setBackground( new Color( 210,95,210 ));

        if (foodModel.getColumnCount() == 0){
            foodModel.addColumn("Item");
            foodModel.addColumn("Price");
        }
        if (drinksModel.getColumnCount() == 0){
            drinksModel.addColumn("Item");
            drinksModel.addColumn("Price");
        }
        if (orderModel.getColumnCount() == 0){
            orderModel.addColumn("Individual Items");
            orderModel.addColumn("Individual Prices");
        }

        AddMenu();

        cafePanel.setVisible(true);
        runGame.setVisible(false);
    }

    public void ProcessOrder() {
        Player player = players.players.get(currentPlayer);
        String id = player.getPlayerID() + player.orders().getSize();
        int orderSize = playerOrder.getRowCount();
        if (orderSize > 0) {
            String[] items = new String[orderSize];
            String[] prices = new String[orderSize];
            for (int i = 0; i < orderSize; i++){
                items[i] = "" + playerOrder.getValueAt(i, 0);
                prices[i] = "" + playerOrder.getValueAt(i, 1);
            }
            player.orders().addOrder(new Order(id, items, prices)); //Add new order to the players Order array.
            JOptionPane.showMessageDialog(null,
                    "Order number for " + player.getPlayerName() + " is: " + id
                            + ", and will be delivered shortly to lane " + player.getLane() + ".");
            ResetCafe();
        }
        else{JOptionPane.showMessageDialog(null, "You haven't added anything to the order.");}
    }

    public void ResetCafe() {
        //Delete the data from each table. Also resets the selections.
        orderModel.setRowCount(0);
        drinksModel.setRowCount(0);
        foodModel.setRowCount(0);
        cafePanel.setVisible(false);
        runGame.setVisible(true);
    }

    public void OrderTotal() {
        int rowCount = orderModel.getRowCount();
        for (int i = 0; i<rowCount; i++) {
            double overall = Double.parseDouble(orderTotal.getText().substring(1)); //Remove £ from the string.
            double rowPrice = Double.parseDouble(((playerOrder.getValueAt(i, 1).toString()).substring(1)));
            overall = overall + rowPrice;
            String toStore = String.format("%.2f", overall); //Make format to 2 DP.
            orderTotal.setText("£" + toStore);
        }
    }

    public void AddToOrderTotal() {
        int rowCount = orderModel.getRowCount();
        double overall = Double.parseDouble(orderTotal.getText().substring(1)); //Remove £ from the string.
        double rowPrice = Double.parseDouble(((playerOrder.getValueAt(rowCount-1, 1).toString()).substring(1)));
        overall = overall + rowPrice;
        String toStore = String.format("%.2f", overall); //Make format to 2 DP.
        orderTotal.setText("£" + toStore);
    }

    public void AddMenu() {
        drinkSP.getViewport().setBackground( new Color( 170, 0, 210 ));
        JTableHeader header1 = drinksTable.getTableHeader();
        header1.setBackground(new Color( 170, 0, 210));
        header1.setForeground(Color.white);
        foodSP.getViewport().setBackground( new Color( 170, 0, 210 ));
        JTableHeader header2 = foodTable.getTableHeader();
        header2.setBackground(new Color( 170, 0, 210 ));
        header2.setForeground(Color.white);
        JTableHeader oTable = playerOrder.getTableHeader();
        oTable.setBackground(new Color(170, 0, 210));
        oTable.setForeground(Color.white);
        Food food = new Food();
        Drinks drink = new Drinks();
        for (int i = 0; i < 7; i++){
            Food fItem = food.FoodMenu(i);
            Drinks dItem = drink.DrinksMenu(i);

            String[] fData = {fItem.getProductName(),"" + fItem.getProductPrice()};
            String[] dData = {dItem.getProductName(),"" + dItem.getProductPrice()};

            foodModel.addRow(fData);
            drinksModel.addRow(dData);

        }
    }



    public static void main(String[] args) {
        JFrame frame = new JFrame("Birch_Bowling");
        frame.setContentPane(new MainProg().MainPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}






