package JDBC;

import org.junit.Test;

import java.sql.Connection;
import JDBC.DBUtil;
import static org.junit.Assert.*;

public class DBUtilTest {
    DBUtil dbUtil = new DBUtil();
    @Test
    public void testGetConnection(){
        Connection connection = dbUtil.getConn();
        assertNotNull(connection);
    }

}