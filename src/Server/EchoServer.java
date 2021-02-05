package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class EchoServer {

	private ServerSocket mySocket;
	public static ArrayList<User> clientList;
	
	public EchoServer() {
		this.clientList = new ArrayList<User>();
		try {
			mySocket = new ServerSocket(14002);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void go() {
		System.out.println("Server listening...");
		try {
			while (true) {
				// Accept a connection from a client
				this.clientList.add(new User(mySocket.accept()));

			}

			
		} catch (IOException e) {
			try {
				mySocket.close();
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		}
		finally {
			try {
				mySocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		EchoServer myEchoServer = new EchoServer();
		myEchoServer.go();
	}

}
