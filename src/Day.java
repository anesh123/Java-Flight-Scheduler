package Scheduler;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Day {
    
    private static PreparedStatement addDate;
    private static PreparedStatement getDate;
    private static Connection connection = DBConnection.getConnection();

    public Day() {
        try {
            getDate = connection.prepareStatement("SELECT * FROM DATE");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        }
    }

    public static List< String> getDate() {
        List<String> results = null;
        ResultSet resultSet = null;

        try {
            getDate = connection.prepareStatement("SELECT * FROM DATE");
            resultSet = getDate.executeQuery();
            results = new ArrayList< String>();

            while (resultSet.next()) {
                results.add(resultSet.getDate("DATE").toString());
            }
        } 
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } 
        return results;
    }
    public static int addDate (Date dates)
    {
        int result = 0;

        try
        {
            addDate = connection.prepareStatement("INSERT INTO Date (date) VALUES (?)");
            addDate.setDate(1, dates);
            
                    
            result = addDate .executeUpdate();
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
            
        }
        
        return result;
    }

}
