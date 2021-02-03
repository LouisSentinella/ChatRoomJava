package Client;

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

    public ConnectionHandler(Socket clientSocketInput) {

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
            while (true) {
                if (this.clientIn.ready()){
                    String userInput = this.clientIn.readLine();
                    System.out.println(userInput);
                    for (Socket client : EchoServer.clientList){
                        if (client.equals(this.clientSocket)){
                            continue;
                        } else{
                            new PrintWriter(client.getOutputStream(), true).println(userInput);
                        }
                    }
                }

            }
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }
}
