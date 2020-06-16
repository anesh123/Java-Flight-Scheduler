package Scheduler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection
{
    private static final String URL = "jdbc:derby://localhost:1527/FlightSchedulerDBAneshPavp5603";
    private static final String USERNAME = "java";
    private static final String PASSWORD = "java";

    private static Connection connection;

    public static Connection getConnection() {
        try
        {
            if( connection == null)
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
            System.exit(1);
        }
        return connection;
    }
}

  