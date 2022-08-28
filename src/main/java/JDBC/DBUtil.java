package JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class DBUtil {
    //Loading JDBC。
    private String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    //Connect the server to the database.
    private String dbURL = "jdbc:sqlserver://risk123.database.windows.net:1433; DatabaseName=mySampleDatabase";
    private String userName = "local";
    private String password = "!@#123QWE";

    public Connection getConn() {
        Connection dbConn = null;
        try {
            Class.forName(driverName);
            dbConn = DriverManager.getConnection(dbURL, userName, password);
//            System.out.println("SQL server connected！");
        } catch (Exception e) {
//            System.out.println("SQL server fail to connect？？？？？");
            e.printStackTrace();
        }

        return  dbConn;
    }

    public void closeConn(ResultSet rs, PreparedStatement pstm, Connection conn) throws Exception {
        if (rs != null) {
            rs.close();
        }
        if (pstm != null) {
            pstm.close();
        }
        if (conn != null) {
            conn.close();
        }
    }
}
