package main;

import javax.swing.*;
import java.awt.*;

public class Surround 
{
    private JLabel[] surround;
    
    public Surround()
    {
        initLabels();
    }
    
    private void initLabels()
    {
        surround = new JLabel[32];
        
        String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H"};
        
        int index = 0;
        int number = 8;
        int x = 0;
        int y = 20;
        
        for(int i = 0; i <= 15; i++)
        {
            surround[i] = new JLabel(Integer.toString(number), JLabel.CENTER);
            surround[i].setOpaque(true);
            surround[i].setBackground(Color.GRAY);
            surround[i].setBounds(x, y, 20, 80);
            y += 80;
            number--;
            
            if(i == 7) { x = 660; y = 20; number = 8; }
        }
        
        x = 20;
        y = 0;
        
        for(int i = 16; i <= 31; i++)
        {
            surround[i] = new JLabel(letters[index], JLabel.CENTER);
            surround[i].setOpaque(true);
            surround[i].setBackground(Color.GRAY);
            surround[i].setBounds(x, y, 80, 20);
            x += 80;
            index++;
            
            if(i == 23) { y = 660; x = 20; index = 0; }
        }
    }
    
    public JLabel[] getSurround()
    {
        return surround;
    }
    
}
