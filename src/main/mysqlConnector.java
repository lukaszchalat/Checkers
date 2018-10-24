package main;

import java.sql.*;

public class mysqlConnector 
{
    private Connection con;
    private Statement st;
    private ResultSet rs;
    
    public mysqlConnector()
    {   
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/chess", "root", "");
            st = con.createStatement();
        }
        catch(Exception ex)
        {
            System.out.println("Error: "+ex);
        }
    }
    
    public String[] getData()
    {
        int position = 1;
        String[] scores = new String[10];
        
        try
        {
            String query = "select * from scoretable order by score DESC";
            rs = st.executeQuery(query);
            
            while(rs.next() && position <= 10)
            {
                String name = rs.getString("name");
                int score = rs.getInt("score");
                scores[position-1] = position + ". " + name + "        " + score;
                position++;
            }
        }
        catch(Exception ex)
        {
            System.out.println("Error: "+ex);
        }
        
        return scores;
    }
    
    public void insertData(String name, int score)
    {
        try
        {
            String query = "insert into chess.scoretable VALUES (DEFAULT, '"+name+"', '"+score+"')";
            System.out.println(query);
            st.executeUpdate(query);
        }
        catch(Exception ex)
        {
            System.out.println("Error: "+ex);
        }
        
    }
}