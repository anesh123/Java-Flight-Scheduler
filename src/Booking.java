package Scheduler;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

public class Booking {
    private static PreparedStatement addBooking;
    private static PreparedStatement getBooking;
    private static PreparedStatement selectStatus;
    private static PreparedStatement getFlightSeats;
    private static Connection connection = DBConnection.getConnection();

    
    public static ArrayList< String > statusByDayAndFlight(String flight, Date date)
    {
        ArrayList< String > results = null;
        ResultSet resultSet = null;
        
        try
        {
            selectStatus=connection.prepareStatement("SELECT CUSTOMER FROM BOOKING where FLIGHT = ? and DATE = ?");
            selectStatus.setString(1, flight);
            selectStatus.setDate(2, date);
            resultSet = selectStatus.executeQuery();
            results = new ArrayList< String >();
            
            while (resultSet.next())
            {
                results.add(resultSet.getString("CUSTOMER"));
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
    
    public static int addBooking (String customerName, Date flightDate, String flightType)
    {
        int result = 0;

        try
        {
            addBooking = connection.prepareStatement("INSERT INTO BOOKING (customer,date, flight) VALUES (?,?,?)");
            addBooking.setString(1, customerName);
            addBooking.setDate(2, flightDate);
            addBooking.setString(3, flightType);
            
                    
            result = addBooking.executeUpdate();
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
            getFlightSeats = connection.prepareStatement("select count(flight) from booking where flight = ? and date = ?"); 
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
    


public static DefaultTableModel settingJTable (Date theDay, String theFlight) {

    String[][] toAdd;
    ArrayList<String> customerName = new ArrayList<>();

        String query = "select customer from booking where date = ? and flight = ?";
        PreparedStatement preparedStmt;
        try {
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setDate(1,theDay);
            preparedStmt.setString(2,theFlight);
            ResultSet result = preparedStmt.executeQuery();
            while(result.next()) {
        try {
            customerName.add(result.getString("customer"));
        } catch (SQLException ex) {
            Logger.getLogger(Booking.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        } catch (SQLException ex) {
            Logger.getLogger(Booking.class.getName()).log(Level.SEVERE, null, ex);
        }

        toAdd = new String[customerName.size()] [1];
        for (int i = 0; i < customerName.size(); i++) {
            toAdd[i] [0] = customerName.get(i);
        }
        String [] colNames = new String[] {"Customer Name"};
        return new DefaultTableModel(toAdd,colNames);
    
    
}
public static int cancelBooking(String customer, Date date)
{
    int results = 0;
        ResultSet resultSet = null;
        
        try
        {
            PreparedStatement preparedStmt = connection.prepareStatement("DELETE from booking where CUSTOMER = ? and DATE = ?"); 
            preparedStmt.setString(1, customer); preparedStmt.setDate(2, date); 
            //resultSet = cancelBooking.executeQuery(); 
            //resultSet.next(); 
            results = preparedStmt.executeUpdate();
            

        }
       
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return results;

}
public static int getBooking(String customerName, Date dates) {
        ResultSet resultSet = null;
        int seats = 0;

        try {
           
            getBooking = connection.prepareStatement("SELECT * FROM Booking where (customer,Date) =(?,?)");
            getBooking.setString(1, customerName); 
            getBooking.setDate(2, dates);
            resultSet = getBooking.executeQuery();

            resultSet.next();
            seats=resultSet.getInt("Flight");
            
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } 
        return seats;
    }

    public static int bookByFlightDate(Date flightDate, String flightType, String customerName, Timestamp position) {
     int result = 0;

        try
        {
            addBooking = connection.prepareStatement("INSERT INTO BOOKING (customer,date, flight, position) VALUES (?,?,?,?)");
            addBooking.setString(1, customerName);
            addBooking.setDate(2, flightDate);
            addBooking.setString(3, flightType);
            addBooking.setTimestamp(4, position);
            
                    
            result = addBooking.executeUpdate();
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
            
        }
        
        return result;
    }

    public static String getBookingByCustDate(String cancel_Customer, Date cancel_Date) {
        String  results = null;
        ResultSet resultSet = null;
        
        try
        {
            selectStatus=connection.prepareStatement("SELECT Flight FROM BOOKING where Customer = ? and DATE = ?");
            selectStatus.setString(1, cancel_Customer);
            selectStatus.setDate(2, cancel_Date);
            resultSet = selectStatus.executeQuery();
            
            
            if (resultSet.next())
            {
                results= resultSet.getString("flight");
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


