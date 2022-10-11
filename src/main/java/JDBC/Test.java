package JDBC;


public class Test {
    public static void main(String[] args) throws Exception {
        UserDao dao = new UserDao();
        User user = new User();

        dao.findUser();
    }

}
