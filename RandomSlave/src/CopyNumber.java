import java.sql.*;
import java.util.StringTokenizer;

public class CopyNumber {
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    public CopyNumber(Connection con) {
        this.con = con;
    }

    public void insertData(String response) throws SQLException {
        pstmt = con.prepareStatement("MERGE INTO random_slave USING DUAL ON (id = ?) WHEN NOT MATCHED THEN INSERT (id, random_number, created_date) VALUES(?, ?, ?)");

        StringTokenizer row = new StringTokenizer(response, "\n");
        StringTokenizer column = null;

        while (row.hasMoreTokens()) {
            column = new StringTokenizer(row.nextToken(), "_");

            int id = Integer.parseInt(column.nextToken());
            int randomNumber = Integer.parseInt(column.nextToken());
            String createdDate = column.nextToken();

            pstmt.setInt(1, id);
            pstmt.setInt(2, id);
            pstmt.setInt(3, randomNumber);
            pstmt.setTimestamp(4, Timestamp.valueOf(createdDate));
            pstmt.executeUpdate();

            System.out.println("Success insert copy data : " + id + "_" + randomNumber + "_" + createdDate);
        }
    }

    /**
     * 마지막 Data Id 조회
     *
     * @return lastId
     * @throws SQLException
     */
    public int getLastId() throws SQLException {
        int lastId = 0;

        pstmt = con.prepareStatement("SELECT MAX(id) FROM random_slave");
        rs = pstmt.executeQuery();

        if(rs.next()) {
            lastId = rs.getInt(1);
        }

        return lastId;
    }
}
