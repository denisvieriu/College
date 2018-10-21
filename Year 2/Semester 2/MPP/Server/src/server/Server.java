package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private ServerSocket ss;
    private List<ServerConnection> connectionList = new ArrayList<ServerConnection>();
    private boolean shouldRun = true;

    public static void main(String[] args) {

        System.out.println("HEY" + LINE_SEPARATOR + "WTF");
            //new Server();
    }

    private Server() {
        try {
            ss = new ServerSocket(3333);
            while (shouldRun) {

                Socket s = ss.accept();

                // Pass the socket and a reference to itself
                ServerConnection sc = new ServerConnection(s, this);
                sc.start();
                connectionList.add(sc);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<ServerConnection> getConnections() {
        return connectionList;
    }
}
