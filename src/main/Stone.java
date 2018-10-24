package main;

import javax.swing.*;
import java.awt.*;

public class Stone extends JButton
{
    private int position;
    private final String color;
    private final int numberInArray;
    private final Icon white = new ImageIcon(Stone.class.getResource("resource/whiteStone.png"));
    private final Icon red = new ImageIcon(Stone.class.getResource("resource/redStone.png"));
    
    public Stone(int position, String color, int numberInArray)
    {
        this.position = position;
        this.numberInArray = numberInArray;
        this.color = color;
        if(color.equals("white")) this.setIcon(white);
        else this.setIcon(red);
    }
    
    public int getNumberInArray()
    {
        return numberInArray;
    }
    
    public int getPosition()
    {
        return position;
    }
    
    public String getColor()
    {
        return color;
    }
    
    public Icon getRed()
    {
        return red;
    }
    
    public Icon getWhite()
    {
        return white;
    }
}
