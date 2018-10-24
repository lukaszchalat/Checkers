package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Main extends JFrame
{
    private final JLabel turn = new JLabel();
    private final JLabel describeGame = new JLabel();
    private final JLabel numberOfRed = new JLabel();
    private final JLabel numberOfWhite = new JLabel();
    private final JLabel playerOneScore = new JLabel();
    private final JLabel playerTwoScore = new JLabel();
    private final JLabel strike = new JLabel();
    private JButton reset;
    private int numberOfStrikes;
    private int score1;
    private int score2;
    private Field[] checkersBoard;
    private Field startField;
    private Field destinationField;
    private Stone[] stones;
    private int whiteStones = 0;
    private int redStones = 0;
    private Panel panel;
    private Mover listener;
    private final Player player1 = new Player("Mr. White", "white");
    private final Player player2 = new Player("Mr. Red", "red");
    private Player player = player1;
    private String lastMove = "none";
    private int x = Toolkit.getDefaultToolkit().getScreenSize().width;
    private int y = Toolkit.getDefaultToolkit().getScreenSize().height;
    
    public Main()
    {
        super("Checkers");
        this.setBounds((x-900)/2, (y-710)/2, 900, 710);
        // new ScoreTable().setVisible(true);
        initComponents();
        this.setDefaultCloseOperation(3);
        this.setResizable(false);
    }     
    
    private void initComponents()
    {
        initCheckersBoard();
        setStonesOnCheckersBoard();
        addCheckersBoardToFrame();
        addListenerToBlackFields();
        addLabelsToFrame();
      
    }
    
    private void addLabelsToFrame()
    {
        Surround surround = new Surround();
        JLabel[] labels = surround.getSurround();
        
        for(JLabel a: labels)
        {
            panel.add(a);
        }
    }
    
    private void initCheckersBoard()
    {
        checkersBoard = new Field[64];
        int line = 0;
        int number = 8;
        int index = 0;
        
        String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H"};
        
        for(int i = 0; i < checkersBoard.length; i++)
        {
            if(i%8 == 0 && i != 0) { line++; number--; index = 0; }
            String name = letters[index]+number;
            
            if(line%2 == 0)
            {
                if(i%2 == 0) 
                {
                    checkersBoard[i] = new Field(i, name, "white", false);
                }
                else
                {
                    checkersBoard[i] = new Field(i, name, "black", false);
                }
            }
            else
            {
                if(i%2 == 0) 
                {
                    checkersBoard[i] = new Field(i, name, "black", false);
                }
                else
                {
                    checkersBoard[i] = new Field(i, name, "white", false);
                }
            }
            
            index++;
        }
    }
    
    private void addListenerToBlackFields()
    {
        listener = new Mover();
        reset.addMouseListener(listener);
        for(Field a: checkersBoard)
        {
            if(a.getColor().equals("black")) a.addMouseListener(listener);
        }
    }
    
    private void setStonesOnCheckersBoard()
    {
        
        stones = new Stone[24];
        
        int index = 0;
        
        for(int i = 0; i < checkersBoard.length; i++)
        {
            if(i > 0 && i < 24)
            {
                if(checkersBoard[i].getColor().equals("black"))
                {
                   stones[index] = new Stone(checkersBoard[i].getNumber(), "red", index);
                   checkersBoard[i].setIcon(stones[index].getRed());
                   checkersBoard[i].setStoneOnField(stones[index]);
                   checkersBoard[i].setOccupied(true);
                   index++;
                }
                
            }
            if(i > 39 && i < 63)
            {
                if(checkersBoard[i].getColor().equals("black"))
                {
                   stones[index] = new Stone(checkersBoard[i].getNumber(), "white", index);
                   checkersBoard[i].setIcon(stones[index].getWhite());
                   checkersBoard[i].setStoneOnField(stones[index]);
                   checkersBoard[i].setOccupied(true);
                   index++;
                }
                
            }
            
        }
    }
    
    private void addCheckersBoardToFrame()
    {
        panel = new Panel();
        panel.setLayout(null);
        turn.setBounds(700, 50, 200, 50);
        numberOfRed.setBounds(700, 100, 200, 50);
        numberOfWhite.setBounds(700, 150, 200, 50);
        describeGame.setBounds(700, 200, 200, 50);
        strike.setBounds(700, 250, 200, 50);
        playerOneScore.setBounds(700, 300, 200, 50);
        playerTwoScore.setBounds(700, 350, 200, 50);
        turn.setText("Turn: "+player.getName());
        numberOfRed.setText("Number of red stones: 12");
        numberOfWhite.setText("Number of white stones: 12");
        describeGame.setText("Please make a move !");
        strike.setText("No strike");
        playerOneScore.setText(player1.getName()+" score: "+score1);
        playerTwoScore.setText(player2.getName()+" score: "+score2);
        reset = new JButton("Reset");
        reset.setBounds(700, 400, 80, 30);
        panel.add(turn);
        panel.add(numberOfRed);
        panel.add(numberOfWhite);
        panel.add(describeGame);
        panel.add(strike);
        panel.add(playerOneScore);
        panel.add(playerTwoScore);
        panel.add(reset);
        
        int index = 0;
        
        for(int i = 20; i <= 580; i = i + 80)
        {
            for(int j = 20; j <= 580; j = j + 80)
            {
                checkersBoard[index].setLocation(j, i);
                panel.add(checkersBoard[index]);
                index++;
            }
        }
        
        this.add(panel);
    }
    
    private void addToScore(int points)
    {
        if(player == player1)
        {
            score1 += points;
            playerOneScore.setText(player1.getName()+" score: "+score1);
        }
        else
        {
            score2 += points;
            playerTwoScore.setText(player2.getName()+" score: "+score2);
        }
    }
    
    public static void main(String[] args) 
    {
       new Main().setVisible(true);
    }
    
    public class Mover implements MouseListener
    {
        @Override
        public void mouseClicked(MouseEvent e) 
        {
            for(Field a: checkersBoard)
            {
                
                if(e.getSource() == a && a.getOccupied() == true && a.getStoneOnField().getColor().equals(player.getColor()) && lastMove.equals("none"))
                {
                    startField = a;
                    strike.setText("No strike");
                }
                else if(e.getSource() == a && a.getOccupied() == false)
                {
                    destinationField = a;
                    
                    if(startField != null && destinationField != null)
                    {
                        if(player.makeAMove(startField, destinationField, checkersBoard, lastMove) == true)
                        {
                            describeGame.setText("The "+player.getColor()+" stone from "+startField.getName()+" on "+destinationField.getName());
                            
                            executeTheMove(startField.getNumber(), destinationField.getNumber(), startField.getStoneOnField());
                            
                            if(lastMove.equals("simpleMove"))
                            {
                                changePlayer();
                                startField = null;
                                lastMove = "none";
                            }
                            if(lastMove.equals("jump"))
                            {
                                if(nextJump(destinationField) == true)
                                {
                                    startField = destinationField;
                                    whiteStones = 0;
                                    redStones = 0;
                                }
                                else
                                {
                                    changePlayer();
                                    startField = null;
                                    lastMove = "none";
                                    whiteStones = 0;
                                    redStones = 0;
                                }
                            }
                        }
                        else describeGame.setText("This move is not allowed !");
                    }
                }
            }
            
            if(e.getSource() == reset) resetTheGame();
        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {
            
            for(Field a: checkersBoard)
            {
                if(e.getSource() == a && a.getOccupied() == false) a.setActive();
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            
            for(Field a: checkersBoard)
            {
                if(e.getSource() == a && a.getOccupied() == false) a.setDefaultIcon();
            }
        }
        
        
    }
    
    private void executeTheMove(int a, int b, Stone stone)
    {
        checkersBoard[b].setOccupied(true);
        checkersBoard[b].setIcon(checkersBoard[a].getIcon());
        checkersBoard[b].setStoneOnField(checkersBoard[a].getStoneOnField());
        
        checkersBoard[a].setOccupied(false);
        checkersBoard[a].setDefaultIcon();
        checkersBoard[a].setStoneOnField(null);
        
        lastMove = "simpleMove";
        strike.setText("No strike");
        
        int distance = b - a;
        int difference = 0;
        
        if(stone instanceof King)
        {
            if((b - a) < -9 || (b - a) > 9)
            {
                if(distance > 0 && distance % 7 == 0) difference = 7;
                if(distance > 0 && distance % 9 == 0) difference = 9;
                if(distance < 0 && distance % -7 == 0) difference = -7;
                if(distance < 0 && distance % -9 == 0) difference = -9;
                
                if(distance > 0)
                {
                    for(int i = a + difference; i <= b - difference; i = i + difference)
                    {
                        if(checkersBoard[i].getOccupied() == true && checkersBoard[i].getStoneOnField().getColor().equals("white") || checkersBoard[i].getOccupied() == true && checkersBoard[i].getStoneOnField().getColor().equals("red"))
                        {
                            System.out.println(i);
                            checkersBoard[i].setOccupied(false);
                            checkersBoard[i].setDefaultIcon();
                            stones[checkersBoard[i].getStoneOnField().getNumberInArray()] = null;
                            checkersBoard[i].setStoneOnField(null);
                            lastMove = "jump";
                            redStones = 0;
                            whiteStones = 0;
                        }
                    }
                }
                if(distance < 0)
                {
                    for(int i = a + difference; i >= b - difference; i = i + difference)
                    {
                        if(checkersBoard[i].getOccupied() == true && checkersBoard[i].getStoneOnField().getColor().equals("white") || checkersBoard[i].getOccupied() == true && checkersBoard[i].getStoneOnField().getColor().equals("red"))
                        {
                            System.out.println(i);
                            checkersBoard[i].setOccupied(false);
                            checkersBoard[i].setDefaultIcon();
                            stones[checkersBoard[i].getStoneOnField().getNumberInArray()] = null;
                            checkersBoard[i].setStoneOnField(null);
                            lastMove = "jump";
                            redStones = 0;
                            whiteStones = 0;
                        }
                    }
                }
                
            }
            
            numberOfStrikes++;
            addToScore(numberOfStrikes*50);
            strike.setText(numberOfStrikes+" x strike in row ! (+"+numberOfStrikes*50+")");
        }
        else
        {    
            if((b - a) < -9 || (b - a) > 9)
            {
                int index = ((b - a) / 2) + a;
                checkersBoard[index].setOccupied(false);
                checkersBoard[index].setDefaultIcon();
                stones[checkersBoard[index].getStoneOnField().getNumberInArray()] = null;
                checkersBoard[index].setStoneOnField(null);
                lastMove = "jump";
                redStones = 0;
                whiteStones = 0;
                numberOfStrikes++;
                addToScore(numberOfStrikes*50);
                strike.setText(numberOfStrikes+" x strike in row ! (+"+numberOfStrikes*50+")");
            }
        
            if(!(checkersBoard[b].getStoneOnField() instanceof King) && b >= 0 && b <= 7 || !(checkersBoard[b].getStoneOnField() instanceof King) && b >= 56 && b <= 63) upgradeToTheKing(b);
        }
        
    }
    
    private void upgradeToTheKing(int index)
    {
        addToScore(100);
        
        if(checkersBoard[index].getStoneOnField().getColor().equals("white") && index >= 0 && index <= 7)
        {
            changePlayer();
            int numberInArray = checkersBoard[index].getStoneOnField().getNumberInArray();
            King king = new King(index, "white", numberInArray);
            checkersBoard[index].setStoneOnField(king);
            checkersBoard[index].setIcon(king.getWhite());
        }
        if(checkersBoard[index].getStoneOnField().getColor().equals("red") && index >= 56 && index <= 63)
        {
            changePlayer();
            int numberInArray = checkersBoard[index].getStoneOnField().getNumberInArray();
            King king = new King(index, "red", numberInArray);
            checkersBoard[index].setStoneOnField(king);
            checkersBoard[index].setIcon(king.getRed());
        }
    }
    
    private void changePlayer()
    {
        if(player == player1) player = player2;
        else player = player1;
        startField = null;
        numberOfStrikes = 0;
        lastMove = "none";
        checkVictory();
        turn.setText("Turn: "+player.getName());
    }
    
    private boolean nextJump(Field destinationField)
    {
        int index = destinationField.getNumber();
        
        if(destinationField.getStoneOnField() instanceof King)
        {
            if(index < 16)
            {
                int[] numbers = {7, 9};
            
                for(int i = 0; i < numbers.length; i++)
                {
                    int buffer = index+numbers[i];
                
                    while(1>0)
                    {
                        if(buffer >= 1 && buffer <= 7 || buffer >= 56 && buffer <= 63 || buffer % 8 == 0 || buffer % 8 == 7) break;
                    
                        if(checkersBoard[buffer].getOccupied() == true && checkersBoard[buffer].getStoneOnField().getColor().equals("white")) whiteStones++;
                        if(checkersBoard[buffer].getOccupied() == true && checkersBoard[buffer].getStoneOnField().getColor().equals("red")) redStones++;
                      
                        if(destinationField.getStoneOnField().getColor().equals("white") && redStones == 1 && checkersBoard[buffer+numbers[i]].getOccupied() == false && checkersBoard[buffer+numbers[i]].getColor().equals("black")) return true;
                        if(destinationField.getStoneOnField().getColor().equals("red") && whiteStones == 1 && checkersBoard[buffer+numbers[i]].getOccupied() == false && checkersBoard[buffer+numbers[i]].getColor().equals("black")) return true;

                        buffer += numbers[i];
                    }
                    whiteStones = 0;
                    redStones = 0;
                }
            }
            if(index >= 16 && index <= 47)
            {
                int[] numbers = {7, 9, -7, -9};
            
                for(int i = 0; i < numbers.length; i++)
                {
                    int buffer = index+numbers[i];
                
                    while(1>0)
                    {
                        if(buffer >= 1 && buffer <= 7 || buffer >= 56 && buffer <= 63 || buffer % 8 == 0 || buffer % 8 == 7) break;
                        
                        if(checkersBoard[buffer].getOccupied() == true && checkersBoard[buffer].getStoneOnField().getColor().equals("white")) whiteStones++;
                        if(checkersBoard[buffer].getOccupied() == true && checkersBoard[buffer].getStoneOnField().getColor().equals("red")) redStones++;
                   
                        if(destinationField.getStoneOnField().getColor().equals("white") && redStones == 1 && checkersBoard[buffer+numbers[i]].getOccupied() == false && checkersBoard[buffer+numbers[i]].getColor().equals("black")) return true;
                        if(destinationField.getStoneOnField().getColor().equals("red") && whiteStones == 1 && checkersBoard[buffer+numbers[i]].getOccupied() == false && checkersBoard[buffer+numbers[i]].getColor().equals("black")) return true;
                        
                        buffer += numbers[i];
                    }
                    System.out.println(numbers[i]);
                    whiteStones = 0;
                    redStones = 0;
                }
            }
            if(index > 47)
            {
                int[] numbers = {-7, -9};
            
                for(int i = 0; i < numbers.length; i++)
                {
                    int buffer = index+numbers[i];
                
                    while(1>0)
                    {
                        if(buffer >= 1 && buffer <= 7 || buffer >= 56 && buffer <= 63 || buffer % 8 == 0 || buffer % 8 == 7) break;
                    
                        if(checkersBoard[buffer].getOccupied() == true && checkersBoard[buffer].getStoneOnField().getColor().equals("white")) whiteStones++;
                        if(checkersBoard[buffer].getOccupied() == true && checkersBoard[buffer].getStoneOnField().getColor().equals("red")) redStones++;
                       
                        if(destinationField.getStoneOnField().getColor().equals("white") && redStones == 1 && checkersBoard[buffer+numbers[i]].getOccupied() == false && checkersBoard[buffer+numbers[i]].getColor().equals("black")) return true;
                        if(destinationField.getStoneOnField().getColor().equals("red") && whiteStones == 1 && checkersBoard[buffer+numbers[i]].getOccupied() == false && checkersBoard[buffer+numbers[i]].getColor().equals("black")) return true;

                        buffer += numbers[i];
                    }
                    whiteStones = 0;
                    redStones = 0;
                }
            }
        }
        else
        {
            if(index < 16)
            {
                if(checkersBoard[index].getStoneOnField().getColor().equals("white") && checkersBoard[index+7].getOccupied() == true && checkersBoard[index+7].getStoneOnField().getColor().equals("red") && checkersBoard[index+14].getColor().equals("black") && checkersBoard[index+14].getOccupied() == false) return true;
                if(checkersBoard[index].getStoneOnField().getColor().equals("white") && checkersBoard[index+9].getOccupied() == true && checkersBoard[index+9].getStoneOnField().getColor().equals("red") && checkersBoard[index+18].getColor().equals("black") && checkersBoard[index+18].getOccupied() == false) return true;
            
                if(checkersBoard[index].getStoneOnField().getColor().equals("red") && checkersBoard[index+7].getOccupied() == true && checkersBoard[index+7].getStoneOnField().getColor().equals("white") && checkersBoard[index+14].getColor().equals("black") && checkersBoard[index+14].getOccupied() == false) return true;
                if(checkersBoard[index].getStoneOnField().getColor().equals("red") && checkersBoard[index+9].getOccupied() == true && checkersBoard[index+9].getStoneOnField().getColor().equals("white") && checkersBoard[index+18].getColor().equals("black") && checkersBoard[index+18].getOccupied() == false) return true;
            }   
        
            if(index >= 16 && index <= 47)
            {
                if(checkersBoard[index].getStoneOnField().getColor().equals("white") && checkersBoard[index+7].getOccupied() == true && checkersBoard[index+7].getStoneOnField().getColor().equals("red") && checkersBoard[index+14].getColor().equals("black") && checkersBoard[index+14].getOccupied() == false) return true;
                if((index + 18) <= 63 && checkersBoard[index].getStoneOnField().getColor().equals("white") && checkersBoard[index+9].getOccupied() == true && checkersBoard[index+9].getStoneOnField().getColor().equals("red") && checkersBoard[index+18].getColor().equals("black") && checkersBoard[index+18].getOccupied() == false) return true;
                if(checkersBoard[index].getStoneOnField().getColor().equals("white") && checkersBoard[index-7].getOccupied() == true && checkersBoard[index-7].getStoneOnField().getColor().equals("red") && checkersBoard[index-14].getColor().equals("black") && checkersBoard[index-14].getOccupied() == false) return true;
                if((index - 18) >= 0 && checkersBoard[index].getStoneOnField().getColor().equals("white") && checkersBoard[index-9].getOccupied() == true && checkersBoard[index-9].getStoneOnField().getColor().equals("red") && checkersBoard[index-18].getColor().equals("black") && checkersBoard[index-18].getOccupied() == false) return true;
            
                if(checkersBoard[index].getStoneOnField().getColor().equals("red") && checkersBoard[index+7].getOccupied() == true && checkersBoard[index+7].getStoneOnField().getColor().equals("white") && checkersBoard[index+14].getColor().equals("black") && checkersBoard[index+14].getOccupied() == false) return true;
                if((index + 18) <= 63 && checkersBoard[index].getStoneOnField().getColor().equals("red") && checkersBoard[index+9].getOccupied() == true && checkersBoard[index+9].getStoneOnField().getColor().equals("white") && checkersBoard[index+18].getColor().equals("black") && checkersBoard[index+18].getOccupied() == false) return true;
                if(checkersBoard[index].getStoneOnField().getColor().equals("red") && checkersBoard[index-7].getOccupied() == true && checkersBoard[index-7].getStoneOnField().getColor().equals("white") && checkersBoard[index-14].getColor().equals("black") && checkersBoard[index-14].getOccupied() == false) return true;
                if((index - 18) >= 0 && checkersBoard[index].getStoneOnField().getColor().equals("red") && checkersBoard[index-9].getOccupied() == true && checkersBoard[index-9].getStoneOnField().getColor().equals("white") && checkersBoard[index-18].getColor().equals("black") && checkersBoard[index-18].getOccupied() == false) return true;
            }
        
            if(index > 47)
            {
                if(checkersBoard[index].getStoneOnField().getColor().equals("white") && checkersBoard[index-7].getOccupied() == true && checkersBoard[index-7].getStoneOnField().getColor().equals("red") && checkersBoard[index-14].getColor().equals("black") && checkersBoard[index-14].getOccupied() == false) return true;
                if(checkersBoard[index].getStoneOnField().getColor().equals("white") && checkersBoard[index-9].getOccupied() == true && checkersBoard[index-9].getStoneOnField().getColor().equals("red") && checkersBoard[index-18].getColor().equals("black") && checkersBoard[index-18].getOccupied() == false) return true;
            
                if(checkersBoard[index].getStoneOnField().getColor().equals("red") && checkersBoard[index-7].getOccupied() == true && checkersBoard[index-7].getStoneOnField().getColor().equals("white") && checkersBoard[index-14].getColor().equals("black") && checkersBoard[index-14].getOccupied() == false) return true;
                if(checkersBoard[index].getStoneOnField().getColor().equals("red") && checkersBoard[index-9].getOccupied() == true && checkersBoard[index-9].getStoneOnField().getColor().equals("white") && checkersBoard[index-18].getColor().equals("black") && checkersBoard[index-18].getOccupied() == false) return true;
            }
        }
        return false;
    }
    
    private void checkVictory()
    {
        int red = 0;
        int white = 0;
        
        for(Stone stone: stones)
        {
            if(stone != null && stone.getColor().equals("red")) red++;
            if(stone != null && stone.getColor().equals("white")) white++;
        }
        
        numberOfRed.setText("Number of red stones: "+red);
        numberOfWhite.setText("Number of white stones: "+white);
        
        if(red == 0) { JOptionPane.showMessageDialog(null, "White player wins !"); disebleAllFields(); updateTheScoreTable("Mr. White", score1); showScoreTable(); resetTheGame();}
        if(white == 0) { JOptionPane.showMessageDialog(null, "Red player wins !"); disebleAllFields(); updateTheScoreTable("Mr. Red", score2); showScoreTable(); resetTheGame();}
    }
    
    private void disebleAllFields()
    {
        for(Field a: checkersBoard)
        {
            a.removeMouseListener(listener);
        }
    }
    
    private void resetTheGame()
    {
        for(Field a: checkersBoard)
        {
            a.removeMouseListener(listener);
            a.setDefaultIcon();
            a.setOccupied(false);
            a.setStoneOnField(null);
        }
        
        setStonesOnCheckersBoard();
        addListenerToBlackFields();
        player = player1;
        numberOfStrikes = 0;
        score1 = 0;
        score2 = 0;
        turn.setText("Turn: "+player.getName());
        numberOfRed.setText("Number of red stones: 12");
        numberOfWhite.setText("Number of white stones: 12");
        describeGame.setText("Please make a move !");
        strike.setText("No strike");
        playerOneScore.setText(player1.getName()+" score: "+score1);
        playerTwoScore.setText(player2.getName()+" score: "+score2);
    }
    
    private void updateTheScoreTable(String name, int score)
    {
        mysqlConnector connection = new mysqlConnector();
        connection.insertData(name, score);
    }
    
    public void showScoreTable()
    {
        // new ScoreTable().setVisible(true);
    }
    
}

