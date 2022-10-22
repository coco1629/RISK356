package group.riskgame.Application.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


public class UserDao {
    DBUtil db = new DBUtil();



    /**
     * Insert user's information
     */
    public void insertUser(User user) throws Exception {
        Connection conn = db.getConn();
        PreparedStatement pstm;
        // Insert sentence
        String sql_insert = "insert into users values(?,?)";
        pstm = conn.prepareStatement(sql_insert);
        pstm.setString(1, user.getName());
        pstm.setString(2, user.getPassword());

        int row = pstm.executeUpdate();
        if (row > 0) {
            System.out.println("Success to add " + row + "line has been changed.");
        } else {
            System.out.println("Fail to add");
        }
        db.closeConn(null, pstm, conn);
    }

    /**
     * Modify user's information.
     */
    public void updateUser(int id, User user) throws Exception {
        Connection conn = db.getConn();
        PreparedStatement pstm;
        String sql_update = "update users set password=? where id=?";
        pstm = conn.prepareStatement(sql_update);
        pstm.setString(1, user.getPassword());
        pstm.setInt(2, id);

        int row = pstm.executeUpdate();
        if (row > 0) {
            System.out.println("Succeed to modify " + row + "line has been modified.");
        } else {
            System.out.println("Failed to modify.");
        }
        db.closeConn(null, pstm, conn);
    }

    /**
     * Delete
     */
    public void deleteUser(int id) throws Exception {
        Connection conn = db.getConn();
        PreparedStatement pstm;
        String sql_delete = "delete from users where id=?";
        pstm = conn.prepareStatement(sql_delete);

        pstm.setInt(1, id);

        int row = pstm.executeUpdate();
        if (row > 0) {
            System.out.println("Succeed to delete, " + row + "line has been modified.");
        } else {
            System.out.println("Fail to delete");
        }
        db.closeConn(null, pstm, conn);
    }

    /**
     * Search all.
     */
    public void findUser() throws Exception {
        Connection conn = db.getConn();
        PreparedStatement pstm;
        ResultSet res;

        String sql_find = "select * from users ";

        pstm = conn.prepareStatement(sql_find);

        res = pstm.executeQuery();
        System.out.println("Loading...");
        System.out.println("id\tname\tpassword");
        while (res.next()) {
            System.out.println(res.getInt(1) + "\t" +
                    res.getString(2) + "\t" + res.getString(3));
        }


        db.closeConn(res, pstm, conn);
    }

    /**
     * Search
     */
    public boolean findUserbyid(String name) throws Exception {
        Connection conn = db.getConn();
        PreparedStatement pstm;
        ResultSet res;

        String sql_find = "select * from users where name = ?";

        pstm = conn.prepareStatement(sql_find);

        pstm.setString(1, name);

        res = pstm.executeQuery();
        boolean isExist = res.next();
        db.closeConn(res, pstm, conn);
        return isExist;
    }

    /**
     * Calculate.
     */
    public void countUser() throws Exception {
        Connection conn = db.getConn();
        PreparedStatement pstm;
        ResultSet res;

        String sql_count = "select count(*) from users";

        pstm = conn.prepareStatement(sql_count);
        res = pstm.executeQuery();
        int count = 0;
        if (res.next()) {
            count = res.getInt(1);
            System.out.println("The number is" + count );
        } else {
            System.out.println("No information");
        }

        db.closeConn(res, pstm, conn);
    }

    public boolean checkLogin(String name, String password) throws Exception {
        Connection conn = db.getConn();
        PreparedStatement pstm;
        ResultSet res;

        String sql_find = "select * from users where name = ?";
        pstm = conn.prepareStatement(sql_find);
        pstm.setString(1,name);

        res = pstm.executeQuery();

        boolean isLogin = false;

        if (res.next()) {
            String passwords = res.getString(3);
            if(password.equals(passwords))
                isLogin = true;
        } else {
            System.out.println("Fail to find.");
        }

        db.closeConn(res, pstm, conn);
        return isLogin;
    }
    Connection conn = db.getConn();
    PreparedStatement pstm;

    public ArrayList<String> getUsers() throws Exception {
        Connection conn = db.getConn();
        ArrayList<String> users = new ArrayList<>();
        PreparedStatement pstm;
        ResultSet res;

        String sql_find = "select * from users ";

        pstm = conn.prepareStatement(sql_find);

        res = pstm.executeQuery();
        System.out.println("Loading...");
        System.out.println("id\tname\tpassword");
        while (res.next()) {
            users.add(res.getString(2));
        }
        db.closeConn(res, pstm, conn);
        return users;
    }



}
