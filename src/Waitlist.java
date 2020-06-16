package Scheduler;
import static Scheduler.Waitlist.waitlistByFlightDate;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

public class Waitlist {
    private static PreparedStatement addWaitlist;
    private static PreparedStatement waitlistByFlightDate;
    private static PreparedStatement selectWaitlist;
    private static PreparedStatement getFlightSeats;
    private static PreparedStatement selectStatus;
    private static Connection connection = DBConnection.getConnection();

    
    public static ArrayList< String > waitlistByDay(Date date)
    {
        ArrayList< String > results = null;
        ResultSet resultSet = null;
        
        try
        {
            selectWaitlist=connection.prepareStatement("SELECT CUSTOMER, FLIGHT FROM BOOKING where DATE = ?");
            selectWaitlist.setDate(1, date);
            
            resultSet = selectWaitlist.executeQuery();
            results = new ArrayList< String >();
            
            while (resultSet.next())
            {
                results.add(resultSet.getString("CUSTOMER"));
                results.add(resultSet.getString("FLIGHT"));
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
    
    public static int addWaitlist (String customerName, Date flightDate, String flightType, Timestamp customerPosition)
    {
        int result = 0;

        try
        {
            addWaitlist = connection.prepareStatement("INSERT INTO WAITLIST (customer,date, flight,position) VALUES (?,?,?,?)");
            addWaitlist.setString(1, customerName);
            addWaitlist.setDate(2, flightDate);
            addWaitlist.setString(3, flightType);
            addWaitlist.setTimestamp(4, customerPosition);
            
                    
            result = addWaitlist.executeUpdate();
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
            
        }
        
        return result;
    }
    
    public static int getBookCount(String flight, Date date )
    {
        int results = 0;
        ResultSet resultSet = null;
        
        try
        {
            getFlightSeats = connection.prepareStatement("select count(flight) from bookings where flight = ? and date = ?"); 
            getFlightSeats.setString(1, flight); getFlightSeats.setDate(2, date); 
            resultSet = getFlightSeats.executeQuery(); 
            resultSet.next(); 
            results = resultSet.getInt(1);
            

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
public static DefaultTableModel settingJTable (Date theDay) {

    String[][] toAdd;
    ArrayList<String> customerName = new ArrayList<>();
    ArrayList<String> flightType = new ArrayList<>();

        String query = "select customer, flight from waitlist where date = ? ";
        PreparedStatement preparedStmt;
        try {
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setDate(1,theDay);
            ResultSet result = preparedStmt.executeQuery();
            while(result.next()) {
        try {
            customerName.add(result.getString("customer"));
            flightType.add(result.getString("flight"));
        } catch (SQLException ex) {
            Logger.getLogger(Waitlist.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        } catch (SQLException ex) {
            Logger.getLogger(Waitlist.class.getName()).log(Level.SEVERE, null, ex);
        }

        toAdd = new String[customerName.size()] [2];
        for (int i = 0; i < customerName.size(); i++) {
            toAdd[i] [0] = customerName.get(i);
            toAdd[i] [1] = flightType.get(i);
        }
        String [] colNames = new String[] {"Customer Name", "Flight Name"};
        return new DefaultTableModel(toAdd,colNames);
    
    
}
public static int cancelWaitlist(String customer, Date date)
{
    int results = 0;
        ResultSet resultSet = null;
        
        try
        {
            PreparedStatement preparedStmt = connection.prepareStatement("DELETE from waitlist where CUSTOMER = ? and DATE = ?"); 
            preparedStmt.setString(1, customer); preparedStmt.setDate(2, date); 
            results = preparedStmt.executeUpdate();
            

        }
       
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return results;
}
public static String waitlistByFlightDate(String customerName, Date dates)
{ 
    String results = null;
    ResultSet resultSet = null;
    try
        {
            selectStatus=connection.prepareStatement("SELECT customer FROM Waitlist where flight = ? and DATE = ?");
            selectStatus.setString(1, customerName);
            selectStatus.setDate(2, dates);
            resultSet = selectStatus.executeQuery();
            
            
            if (resultSet.next())
            {
                results =resultSet.getString("CUSTOMER");
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
    }
