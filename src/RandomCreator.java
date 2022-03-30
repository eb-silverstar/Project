import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Timer;

public class RandomCreator {
    public static void main(String[] args) {
        try {
            // 0.1초 주기로 랜덤 수 생성하여 DB Insert
            Timer timer = new Timer();
            timer.schedule(new CreateNumber(dbConnect()), 0, 100);

        } catch(SQLException e) {
            System.out.println("Error preparing database : " + e.getMessage());
        }
    }

    /**
     * DB Connect
     *
     * @return con
     */
    public static Connection dbConnect() {
        String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:xe";
        String id = "system";
        String password = "admin";

        Connection con = null;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(jdbcUrl, id, password);
            System.out.println("Success connecting database.");

        } catch(ClassNotFoundException e) {
            System.out.println("Can not find JDBC Driver : " + e.getMessage());
        } catch(SQLException e) {
            System.out.println("Can not connect database : " + e.getMessage());
        }

        return con;
    }
}
