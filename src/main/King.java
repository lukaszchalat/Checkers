package main;

import javax.swing.*;
import java.awt.*;

public class King extends Stone
{
    private final Icon white = new ImageIcon(King.class.getResource("resource/whiteKing.png"));
    private final Icon red = new ImageIcon(King.class.getResource("resource/redKing.png"));
    
    public King(int position, String color, int numberInArray)
    {
        super(position, color, numberInArray);
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
