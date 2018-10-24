package main;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Field extends JButton
{
    private final int number;
    private final String name;
    private final String color;
    private boolean occupied;
    private Stone stoneOnField;
    Icon whiteIcon = new ImageIcon(Field.class.getResource("resource/white.png"));
    Icon blackIcon = new ImageIcon(Field.class.getResource("resource/black.png"));
    Icon currentField = new ImageIcon(Field.class.getResource("resource/currentField.png"));
            
    public Field(int number, String name, String color, boolean occupied)
    {
        this.number = number;
        this.name = name;
        this.color = color;
        if(color.equals("white")) this.setIcon(whiteIcon);
        else this.setIcon(blackIcon);
        this.occupied = occupied;
        this.setSize(80, 80);
    }
    
    public int getNumber()
    {
        return number;
    }
    
    public String getName()
    {
        return name;
    }
    
    public String getColor()
    {
        return color;
    }
    
    public boolean getOccupied()
    {
        return occupied;
    }
    
    public Stone getStoneOnField()
    {
        return stoneOnField;
    }
    
    public void setOccupied(boolean occupied)
    {
        this.occupied = occupied;
    }
    
    public void setStoneOnField(Stone stone)
    {
        this.stoneOnField = stone;
    }
    
    public void setActive()
    {
        if(this.occupied == false) this.setIcon(currentField);
    }
    
    public void setDefaultIcon()
    {
        if(this.getColor().equals("white")) this.setIcon(whiteIcon);
        else this.setIcon(blackIcon);
    }
}
