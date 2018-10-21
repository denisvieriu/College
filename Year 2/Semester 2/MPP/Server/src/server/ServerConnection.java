package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class ServerConnection extends Thread {

    private Socket socket;
    Server server;
    private DataInputStream din;
    private DataOutputStream dout;
    private boolean shouldRun = true;

    public ServerConnection(Socket socket, Server server) {
        // superclass for the thread
        super("ServerConnectionThread");
        this.socket = socket;
        this.server = server;
    }

    private void sendStringToClient(String text) {
        try {
            dout.writeUTF(text);

            // Make sure all data from that buffer is cleared, and ready to go
            dout.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendStringToAllClients(String text) {
        List<ServerConnection> connections = server.getConnections();
        for (int index = 0; index < connections.size(); index++) {
            ServerConnection sc = connections.get(index);
            sc.sendStringToClient(text);
        }

    }

    @Override
    public void run() {
        try {
            din = new DataInputStream(socket.getInputStream());
            dout = new DataOutputStream(socket.getOutputStream());

            while (shouldRun) {
                // Sleep the current thread
                while (din.available() == 0) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                System.out.println("Received data!");
                String textIn = din.readUTF();
                sendStringToAllClients(textIn);
            }

            din.close();
            dout.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
