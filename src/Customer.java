package Scheduler;
import Scheduler.DBConnection;
import Scheduler.Flight;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

public class Customer {
    private static PreparedStatement addCustomer;
    private static PreparedStatement getCustomer;
    private static PreparedStatement selectAllCustomer;
    private static Connection connection = DBConnection.getConnection();


    
    public static ArrayList< String > getAllCustomer()
    {
        ArrayList< String > results = null;
        ResultSet resultSet = null;
        
        try
        {
            selectAllCustomer=connection.prepareStatement("SELECT * FROM Customer");
            resultSet = selectAllCustomer.executeQuery();
            results = new ArrayList< String >();
            
            while (resultSet.next())
            {
                results.add(resultSet.getString("NAME"));
            }
        }
       
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        finally
        {
            try
            {
                resultSet.close();
            }
            catch (SQLException sqlException)
            {
                sqlException.printStackTrace();
            }
        }
        return results;
    }
    
    public static int addCustomer (String customerName)
    {
        int result = 0;

        try
        {
            addCustomer = connection.prepareStatement("INSERT INTO Customer (name) VALUES (?)");
            addCustomer.setString(1, customerName);
            
                    
            result = addCustomer.executeUpdate();
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
            
        }
        
        return result;
    }
public static DefaultTableModel settingJTable (String customerName) throws SQLException {

    String[][] toAdd;
    ArrayList<String> flightWaitlist = new ArrayList<>();
    ArrayList<String> flightBook = new ArrayList<>();
    ArrayList<String> dateBook = new ArrayList<>();
    ArrayList<String> dateWait = new ArrayList<>();

    String query = "select flight from booking where customer = ?";
    PreparedStatement preparedStmt;
    preparedStmt = connection.prepareStatement(query);
    preparedStmt.setString(1,customerName);
    ResultSet result = preparedStmt.executeQuery();
    while(result.next()) {
        flightBook.add(result.getString("flight"));
    }
    
    query = "select date from booking where customer = ?";
    preparedStmt = connection.prepareStatement(query);
    preparedStmt.setString(1,customerName);
    result = preparedStmt.executeQuery();
    while(result.next()) {
        dateBook.add(result.getString("date"));
    }
    
    query = "select date from waitlist where customer = ?";
    preparedStmt = connection.prepareStatement(query);
    preparedStmt.setString(1,customerName);
    result = preparedStmt.executeQuery();
    while(result.next()) {
        dateWait.add(result.getString("date"));
    }
    
    query = "select flight from waitlist where customer = ? ";
    preparedStmt = connection.prepareStatement(query);
    preparedStmt.setString(1,customerName);
    result = preparedStmt.executeQuery();
    while(result.next()) {
        flightWaitlist.add(result.getString("flight"));
    }
        
    toAdd = new String[flightBook.size() + flightWaitlist.size()] [3];
    for (int i = 0; i < flightBook.size(); i++) {
        toAdd[i] [0] = flightBook.get(i);
        toAdd[i] [1] = "Booked";
        toAdd[i] [2] = dateBook.get(i);
    }
        
    for (int i = 0; i < flightWaitlist.size(); i++) {
        toAdd[i] [0] = flightWaitlist.get(i);
        toAdd[i] [1] = "Waitlisted";
        toAdd[i] [2] = dateWait.get(i);
    }
    String [] colNames = new String[] {"Flight Name", "Status","Flight Date"};
    return new DefaultTableModel(toAdd,colNames);
    
    
}
    
}



