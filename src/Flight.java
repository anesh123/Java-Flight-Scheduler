package Scheduler;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Flight {

    private static PreparedStatement addFlight;
    private static PreparedStatement getFlight;
    private static PreparedStatement getSeats;
    private static PreparedStatement cancelFlight;
    private static PreparedStatement namesOfPassangersBooked;
    private static PreparedStatement namesOfDatesBooked;
    private static PreparedStatement namesOfWaitList;
    private static PreparedStatement deleteBookings;
    private static PreparedStatement deleteWaitList;
    private static Connection connection = DBConnection.getConnection();

    public Flight() {
        try {
            getFlight = connection.prepareStatement("SELECT * FROM FLIGHTS");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        }
    }

    public static List< String> getFlight() {
        List<String> results = null;
        ResultSet resultSet = null;

        try {
            getFlight = connection.prepareStatement("SELECT * FROM FLIGHTS");
            resultSet = getFlight.executeQuery();
            results = new ArrayList< String>();

            while (resultSet.next()) {
                results.add(resultSet.getString("NAME").toString());
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } 
        return results;
    }
    
    public static int getSeats(String flight) {
        ResultSet resultSet = null;
        int seats = 0;

        try {
           
            getSeats = connection.prepareStatement("SELECT * FROM FLIGHTS where NAME = ?");
            getSeats.setString(1, flight);
            resultSet = getSeats.executeQuery();

            resultSet.next();
            seats=resultSet.getInt("SEATS");
            
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } 
        return seats;
    }
    public static int addFlight (String flightName,int numseats)
    {
        int result = 0;

        try
        {
            addFlight = connection.prepareStatement("INSERT INTO Flights (name, seats) VALUES (?,?)");
            addFlight.setString(1, flightName);
            addFlight.setInt(2, numseats);
            
                    
            result = addFlight.executeUpdate();
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
            
        }
        
        return result;
    }
public static int deleteFlight(String flightName)
{
    int results = 0;
        
        try
        {
            PreparedStatement preparedStmt = connection.prepareStatement("DELETE from Flights where Name = ? "); 
            preparedStmt.setString(1, flightName);   
            results = preparedStmt.executeUpdate();
        }
       
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return results;
}
    public static int RemoveFlight(String flightName)
    {
        int result = 0;
        try
        {
            cancelFlight = connection.prepareStatement("DELETE FROM FLIGHTS WHERE NAME = ?");
            cancelFlight.setString(1, flightName);
            result = cancelFlight.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return result;
    }
    public static List<String> getNamesOfPassangersBooked(String flight)
    {
        List<String> bookedNames = null;
        ResultSet resultSet = null; 
        try
        {
            bookedNames = new ArrayList< String>();
            namesOfPassangersBooked = connection.prepareStatement("SELECT Customer FROM BOOKING WHERE FLIGHT = ?");
            namesOfPassangersBooked.setString(1, flight); 
            resultSet = namesOfPassangersBooked.executeQuery(); 
            while (resultSet.next())
            {
                bookedNames.add(resultSet.getString("Customer"));
            }
        }
        catch (SQLException sqlException)
        {
           sqlException.printStackTrace();
        }
       return bookedNames;
    }
    
     public static List<String> getDatesOfPassangersBooked(String flight)
    {
        List<String> bookedDates = null;
        ResultSet resultSet = null; 
        try
        {
            bookedDates = new ArrayList< String>();
            namesOfDatesBooked = connection.prepareStatement("SELECT DATE FROM BOOKING WHERE FLIGHT = ?");
            namesOfDatesBooked.setString(1, flight); 
            resultSet = namesOfDatesBooked.executeQuery(); 
            while (resultSet.next())
            {
                bookedDates.add(resultSet.getString("DATE"));
            }
        }
        catch (SQLException sqlException)
        {
           sqlException.printStackTrace();
        }
       return bookedDates;
    }
       public static List<String> getNamesOfPassangersWaitListed(String flight)
    {
        List<String> waitListNames = null;
        ResultSet resultSet = null; 
        try
        {
            waitListNames = new ArrayList< String>();
            namesOfWaitList.setString(1, flight); 
            resultSet = namesOfWaitList.executeQuery(); 
            while (resultSet.next())
            {
                waitListNames.add(resultSet.getString("Customer"));
            }
        }
        catch (SQLException sqlException)
        {
           sqlException.printStackTrace();
        }
       return waitListNames;
    }
       public static int RemoveBookingsOfDeletedFlight(String flightName)
    {
        int result = 0;
        try
        {
            deleteBookings = connection.prepareStatement("DELETE FROM BOOKING WHERE FLIGHT = ?");
            deleteBookings.setString(1, flightName);
            result = deleteBookings.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return result;
    }
       public static int RemoveWaitListOfDeletedFlight(String flightName)
    {
        int result = 0;
        try
        {
            deleteWaitList = connection.prepareStatement("DELETE FROM WAITLIST WHERE FLIGHT = ?");
            deleteWaitList.setString(1, flightName);
            result = deleteWaitList.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return result;
    }
}


