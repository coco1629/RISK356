package Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connection {
    public static void main(String[] args) {
        java.sql.Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
//            注册驱动
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test1?&useSSL=false&serverTimezone=UTC","root","#Cancy0605");
            stmt = conn.createStatement();
//            String insertsql1 = "insert into RISK values('198198', 'tutu', 'lq520')";
//            stmt.executeUpdate(insertsql1);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(rs != null){
                try{
                    rs.close();
                }catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(stmt !=null){
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(conn != null){
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
