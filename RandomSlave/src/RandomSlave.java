import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class RandomSlave {
    public static void main(String[] args) {
        try(Socket socket = new Socket()) {
            InetSocketAddress isa = new InetSocketAddress(args[0], 6000);
            socket.connect(isa);
            System.out.println("Success connect to server : " + socket.getRemoteSocketAddress());

            try(Socket server = socket;
                DataInputStream dis = new DataInputStream(server.getInputStream());
                DataOutputStream dos = new DataOutputStream(server.getOutputStream());) {

                Connection con = dbConnect(args[0]);

                CopyNumber copyNumber = new CopyNumber(con);
                dos.writeInt(copyNumber.getLastId());

                while(true) {
                    try {
                        copyNumber.insertData(dis.readUTF());
                    } catch (IOException e) {
                        System.out.println("Error receive data.");
                        if (con != null) try { con.close(); } catch (SQLException ex) {}
                        break;
                    }
                }

            } catch(IOException e) {
                System.out.println("Error execute pool IOException : " + e.getMessage());
            } catch(SQLException e) {
                System.out.println("Error execute pool SQLException : " + e.getMessage());
            } catch(Exception e) {
                System.out.println("Error execute pool Other Exception : " + e.getMessage());
            } finally {
                System.out.println("Disconnect the client : " + socket.getRemoteSocketAddress());
            }

        } catch (Throwable e) {
            System.out.println("Error connecting to server : " + e.getMessage());
        }
    }

    /**
     * DB Connect
     *
     * @return con
     */
    public static Connection dbConnect(String ip) {
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
