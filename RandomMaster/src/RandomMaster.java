import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RandomMaster {
    public static void main(String[] args) {
        try(ServerSocket server = new ServerSocket()) {
            InetSocketAddress isa = new InetSocketAddress(6000);
            server.bind(isa);
            System.out.println("Waiting the client.");

            ExecutorService pool = Executors.newCachedThreadPool();

            while(true) {
                Socket socket = server.accept();
                System.out.println("Enter the client : " + socket.getRemoteSocketAddress());

                pool.execute(() -> {
                    System.out.println("Assign the thread pool.");

                    try (Socket client = socket;
                         DataInputStream dis = new DataInputStream(client.getInputStream());
                         DataOutputStream dos = new DataOutputStream(client.getOutputStream());) {

                        Connection con = dbConnect();

                        while(true) {
                            int id = dis.readInt();

                            Timer timer = new Timer();
                            timer.schedule(new GetNumber(con, id, (callback) -> {
                                try {
                                    if(!callback.isEmpty()) {
                                        dos.writeUTF(callback);
                                        System.out.println("Success send to client : ");
                                        System.out.println(callback);
                                    } else {
                                        System.out.println("Empty data.");
                                    }
                                } catch (IOException e) {
                                    System.out.println("Error send to client : " + e.getMessage());
                                }

                                if (client.isClosed() || !client.isConnected()) {
                                    System.out.println("Disconnect the client.");
                                    timer.cancel();
                                    if (con != null) try { con.close(); } catch (SQLException ex) {}
                                }
                            }), 0, 1000);
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
                });
            }
        } catch(IOException e) {
            System.out.println("Error handle socket IOException : " + e.getMessage());
        } catch(Exception e) {
            System.out.println("Error handle socket Other Exception : " + e.getMessage());
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
