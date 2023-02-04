import java.sql.*;

public class InsertCustomer 
{
    public static void main(String [] args)
    {
        // database URL
        final String DATABASE_URL = "jdbc:mysql://localhost/Project";
        Connection connection = null ;
        PreparedStatement pstat = null;
        String FirstName = "Mark";
        String LastName = "Power";
        int Age = 20;
        int i=0;
        try    
        {       // establish connection to database
            connection = DriverManager.getConnection(DATABASE_URL, "root", "" );
            // create Prepared Statement for inserting data into table
            pstat = connection.prepareStatement("INSERT INTO customers (FirstName, LastName, Age) VALUES (?,?,?)");
            pstat.setString (1, FirstName);
            pstat.setString (2, LastName);
            pstat.setInt(3, Age);
            // insert data into table
            i = pstat .executeUpdate();
            System.out. println ( i + " record successfully added to the table .");
        
        }    
        
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace () ;
        }
            
        finally 
        {
            try 
            {
                pstat.close () ;
                connection.close () ;
            }
            catch (Exception exception)
            {
                exception . printStackTrace () ;
            }
        }
            
    }
}
