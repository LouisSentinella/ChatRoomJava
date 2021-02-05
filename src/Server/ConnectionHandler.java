package Server;

import Server.EchoServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectionHandler implements Runnable{

    private final Socket clientSocket;
    private InputStreamReader clientCharStream;
    private BufferedReader clientIn;
    public PrintWriter clientOut;
    private User user;

    public ConnectionHandler(Socket clientSocketInput, User user) {

        this.user = user;
        clientSocket = clientSocketInput;
        System.out.println("Received new connection from: " + clientSocket.getInetAddress());



        try {
            this.clientCharStream = new InputStreamReader(this.clientSocket.getInputStream());
            this.clientIn = new BufferedReader(this.clientCharStream);
            this.clientOut = new PrintWriter(this.clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        try {
            boolean first = true;
            while (true) {
                if (this.clientIn.ready()){
                    String userInput = this.clientIn.readLine();
                    if(first) {
                        this.user.username = userInput;
                        first = false;
                    } else {
                        System.out.println(this.user.username + ": " + userInput);
                        for (User client : EchoServer.clientList) {
                                new PrintWriter(client.socket.getOutputStream(), true).println(this.user.username + ": " + userInput);
                            }
                        }
                    }
                }

            } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

}
