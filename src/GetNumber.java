import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TimerTask;
import java.util.function.Consumer;

public class GetNumber extends TimerTask {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    Consumer<String> callback;
    int id = 0;

    public GetNumber(Connection con, int id, Consumer<String> callback) throws SQLException {
        this.con = con;
        this.pstmt = con.prepareStatement("SELECT * FROM random_master WHERE id > ? ORDER BY id");
        this.id = id;
        this.callback = callback;
    }

    @Override
    public void run()  {
        String payload = "";

        try {
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            while(rs.next()) {
                payload += rs.getInt(1) + "_" + rs.getInt(2) + "_" + rs.getTimestamp(3) + "\n";
                id = rs.getInt(1);
            }

            callback.accept(payload);

        } catch(SQLException e) {
            System.out.println("Error inserting random number : " + e.getMessage());
        }
    }
}
