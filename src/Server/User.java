package Server;

import java.net.InetAddress;
import java.net.Socket;

public class User {

    private InetAddress address;
    private String authtoken;
    public String username;
    private ConnectionHandler connection;
    public Socket socket;

    public User(Socket socket) {
        this.socket = socket;
        this.address = socket.getInetAddress();
        this.authtoken = randomStringGenerator.generateString();
        this.connection = new ConnectionHandler(socket, this);
        new Thread(this.connection).start();

    }
}
