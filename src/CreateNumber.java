import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Random;
import java.util.TimerTask;

public class CreateNumber extends TimerTask {
    Connection con = null;
    PreparedStatement pstmt = null;

    public CreateNumber(Connection con) throws SQLException {
        this.con = con;
        this.pstmt = con.prepareStatement("INSERT INTO random_master(random_number, created_date) VALUES(?, ?)");
    }

    @Override
    public void run()  {
        Random number = new Random();
        Timestamp time = new Timestamp(new java.util.Date().getTime());

        try {
            pstmt.setInt(1, number.nextInt());
            pstmt.setTimestamp(2, time);
            pstmt.executeUpdate();
            System.out.println("Success insert random number : " + time);

        } catch(SQLException e) {
            System.out.println("Error inserting random number : " + e.getMessage());
        }
    }
}
