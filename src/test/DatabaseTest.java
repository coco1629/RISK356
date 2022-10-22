import group.riskgame.Application.JDBC.User;
import group.riskgame.Application.JDBC.UserDao;

public class DatabaseTest {
    public static void main(String[] args) throws Exception {
        UserDao dao = new UserDao();
        User user = new User();
        dao.findUser();
    }

}
