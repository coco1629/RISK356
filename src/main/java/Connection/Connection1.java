package Connection;

import java.sql.Connection;
import java.sql.*;

public class Connection1 {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String connectionString =
                "jdbc:sqlserver://risk123.database.windows.net:1433;"
                        + "database=mySampleDatabase;"
                        + "user=local@risk123;"
                        + "password=!@#123QWE;"
                        + "encrypt=true;"
                        + "trustServerCertificate=true;"
                        + "hostNameInCertificate=*.database.windows.net;"
                        + "loginTimeout=30;";

        // Declare the JDBC objects.
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        PreparedStatement prepsInsertPerson = null;
        PreparedStatement prepsUpdateAge = null;

        try {
            // INSERT two rows into the table.
            // ...
            // TRANSACTION and commit for an UPDATE.
            // ...
            // SELECT rows from the table.
            // ...
            connection = DriverManager.getConnection(connectionString);
            System.out.println("Successful");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            // Close the connections after the data has been handled.
            if (prepsInsertPerson != null) try { prepsInsertPerson.close(); } catch(Exception e) {}
            if (prepsUpdateAge != null) try { prepsUpdateAge.close(); } catch(Exception e) {}
            if (resultSet != null) try { resultSet.close(); } catch(Exception e) {}
            if (statement != null) try { statement.close(); } catch(Exception e) {}
            if (connection != null) try { connection.close(); } catch(Exception e) {}
        }
    }
}