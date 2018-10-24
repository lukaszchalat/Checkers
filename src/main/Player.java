package main;

public class Player 
{
    private final String name;
    private String color;
    
    public Player(String name, String color)
    {
        this.name = name;
        this.color = color;
    }
    
    public String getColor()
    {
        return color;
    }
    
    public String getName()
    {
        return name;
    }
    
    public boolean makeAMove(Field startField, Field destinationField, Field[] checkersBoard, String lastMove)
    {
        int position = startField.getNumber();
        int goal = destinationField.getNumber();
        int distance = goal - position;
        int difference = 0;
        int redStones = 0;
        int whiteStones = 0;
        
        if(startField.getStoneOnField() instanceof King)
        {
            if(distance == -9 && lastMove.equals("none") || distance == 9 && lastMove.equals("none") || distance == 7 && lastMove.equals("none") || distance == -7 && lastMove.equals("none")) return true;
            else if((distance % 7) != 0 && (distance % 9) != 0 && (distance & -7) != 0 && (distance % -9) != 0) return false;
            else if(((distance % 7) == 0 && distance < -9) || ((distance % 7) == 0 && distance > 9) || ((distance % 9) == 0 && distance < -9) || ((distance % 9) == 0 && distance > 9))
            {
                if(distance > 0 && distance % 7 == 0) difference = 7;
                if(distance > 0 && distance % 9 == 0) difference = 9;
                if(distance < 0 && distance % -7 == 0) difference = -7;
                if(distance < 0 && distance % -9 == 0) difference = -9;
                if(distance > 0)
                {
                    for(int i = position + difference; i <= goal; i = i + difference)
                    {
                        if(checkersBoard[i].getOccupied() == true && checkersBoard[i].getStoneOnField().getColor().equals("white")) whiteStones++;
                        if(checkersBoard[i].getOccupied() == true && checkersBoard[i].getStoneOnField().getColor().equals("red")) redStones++;
                    }
                }
                if(distance < 0)
                {
                    for(int i = position + difference; i >= goal; i = i + difference)
                    {
                        if(checkersBoard[i].getOccupied() == true && checkersBoard[i].getStoneOnField().getColor().equals("white")) whiteStones++;
                        if(checkersBoard[i].getOccupied() == true && checkersBoard[i].getStoneOnField().getColor().equals("red")) redStones++;
                    }
                }
                
                if(checkersBoard[position].getStoneOnField().getColor().equals("white") && whiteStones == 0 && redStones == 0 || checkersBoard[position].getStoneOnField().getColor().equals("white") && whiteStones == 0 && redStones == 1) return true;
                if(checkersBoard[position].getStoneOnField().getColor().equals("red") && whiteStones == 0 && redStones == 0 || checkersBoard[position].getStoneOnField().getColor().equals("red") && whiteStones == 1 && redStones == 0) return true;
              
            }
        }
        else
        {
            if(distance == -7 && color.equals("white") && lastMove.equals("none") || distance == -9 && color.equals("white") && lastMove.equals("none")) 
            {
                return true;
            }
            if(distance == 7 && color.equals("red") && lastMove.equals("none") || distance == 9 && color.equals("red") && lastMove.equals("none"))
            {
                return true;
            }
            if(color.equals("white") && distance == -18 && checkersBoard[position-9].getOccupied() == true && checkersBoard[position-9].getStoneOnField().getColor().equals("red"))
            {
                return true;
            }
            if(color.equals("white") && distance == -14 && checkersBoard[position-7].getOccupied() == true && checkersBoard[position-7].getStoneOnField().getColor().equals("red"))
            {
                return true;
            }
            if(color.equals("white") && distance == 18 && checkersBoard[position+9].getOccupied() == true && checkersBoard[position+9].getStoneOnField().getColor().equals("red"))
            {
                return true;
            }    
            if(color.equals("white") && distance == 14 && checkersBoard[position+7].getOccupied() == true && checkersBoard[position+7].getStoneOnField().getColor().equals("red"))
            {
                return true;
            }    
            if(color.equals("red") && distance == -18 && checkersBoard[position-9].getOccupied() == true && checkersBoard[position-9].getStoneOnField().getColor().equals("white"))
            {
                return true;
            }
            if(color.equals("red") && distance == -14 && checkersBoard[position-7].getOccupied() == true && checkersBoard[position-7].getStoneOnField().getColor().equals("white"))
            {
                return true;
            }
            if(color.equals("red") && distance == 18 && checkersBoard[position+9].getOccupied() == true && checkersBoard[position+9].getStoneOnField().getColor().equals("white"))
            {
                return true;
            }    
            if(color.equals("red") && distance == 14 && checkersBoard[position+7].getOccupied() == true && checkersBoard[position+7].getStoneOnField().getColor().equals("white"))
            {
                return true;
            }   
        }
       
        return false;
    }
}
