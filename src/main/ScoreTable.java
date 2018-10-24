package main;

import javax.swing.*;
import java.awt.*;

public class ScoreTable extends JFrame 
{
    JLabel[] scoreFields;
    JPanel panel;
    int x = Toolkit.getDefaultToolkit().getScreenSize().width;
    int y = Toolkit.getDefaultToolkit().getScreenSize().height;
    
    public ScoreTable()
    {
       this.setTitle("Score Table");
       this.setBounds((x-300)/2, (y-600)/2, 400, 400);
       initComponents();
       showScores();
       this.setDefaultCloseOperation(2);
    }
    
    private void initComponents()
    {
        scoreFields = new JLabel[10];
        
        int x = 20;
        
        for(int i = 0; i < scoreFields.length; i++)
        {
            scoreFields[i] = new JLabel();
            scoreFields[i].setBounds(20, x, 150, 30);
            x += 30; 
        }
        
        panel = new JPanel();
        panel.setLayout(null);
        
        for(int i = 0; i < scoreFields.length; i++)
        {
            panel.add(scoreFields[i]);
        }
        
        this.add(panel);
        
    }
    
    private void showScores()
    {
        mysqlConnector connection = new mysqlConnector();
        String[] scores = connection.getData();
        
        for(int i = 0; i < scoreFields.length; i++)
        {
            scoreFields[i].setText(scores[i]);
        }
    }
}
