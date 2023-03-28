import java.sql.*;

public class InsertCustomer 
{
    public static void main(String [] args)
    {
        // database URL
        final String DATABASE_URL = "jdbc:mysql://localhost/customerdb";
        Connection connection = null ;
        PreparedStatement pstat = null;
        String firstname = "Mark";
        String surname = "Power";
        int dob = 2003;
        int i=0;
        try    
        {       // establish connection to database
            connection = DriverManager.getConnection(DATABASE_URL, "root", "" );
            // create Prepared Statement for inserting data into table
            pstat = connection.prepareStatement("INSERT INTO customer (firstName, surname, dob) VALUES (?,?,?)");
            pstat.setString (1, firstname);
            pstat.setString (2, surname);
            pstat.setInt(3, dob);
            // insert data into table
            i = pstat .executeUpdate();
            System.out. println ( i + " record successfully added to the table .");
        
        }    
        
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
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
