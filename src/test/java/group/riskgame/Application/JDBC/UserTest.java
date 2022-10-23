package group.riskgame.Application.JDBC;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {
    User user;
    @Before
    public void setUp() throws Exception {
        user = new User("Name","password");

    }

    @Test
    public void getId() {
        user.setId(10);
        assertEquals(user.getId(),10);
    }

    @Test
    public void getName() {
        assertEquals(user.getName(),"Name");
        user.setName("Change");
        assertEquals(user.getName(),"Change");
    }

}