package group.riskgame.Application.JDBC;

import org.junit.Before;
import org.junit.Test;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.Assert.*;

public class DBUtilTest {

    @Before
    public void setUp() throws Exception {
        String sql = "select * from peopel";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
    }

    @Test
    public void getConn() {
    }

    @Test
    public void closeConn() {
    }
}