import JDBC.User;
import JDBC.UserDao;

public class DatabaseTest {
    public static void main(String[] args) throws Exception {
        UserDao dao = new UserDao();
        User user = new User();
        dao.findUser();
    }

}
