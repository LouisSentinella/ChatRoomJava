package Client;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class EchoClient {

	private Socket serverSocket;
	
	public EchoClient(String address) {
		try {
			serverSocket = new Socket(address, 14002);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void go() {
		try {
			// Set up the ability to read user input from keyboard

			// Set up the ability to send the data to the server
			PrintWriter serverOut = new PrintWriter(serverSocket.getOutputStream(), true);
			
			// Set up the ability to read the data from the server
			BufferedReader serverIn = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));

			//new Thread(new messageInputHandler(serverOut)).start();

			JFrame f= new JFrame("Chat Room");

			JPanel textPanel = new JPanel();
			textPanel.setBounds(0, 0, 400, 300);
			JPanel inputPanel = new JPanel();
			inputPanel.setBounds(0,330, 400,70);



			JTextField t1 = new JTextField(31);
			JTextArea t2 = new JTextArea(16,31);
			DefaultCaret caret = (DefaultCaret) t2.getCaret();
			caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
			t2.setEditable(false);
			JScrollPane scroll = new JScrollPane(t2, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);


			final boolean[] firstTime = {true};
			final String[] username = {null};
			t1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

					if (firstTime[0]){
						username[0] = t1.getText();
						t2.append(username[0] + " has joined the chat!\n");
						System.out.println(username[0] + " has joined the chat!");
						t1.setText("");
						firstTime[0] = false;
					} else {
						String userInputString = t1.getText();
						t2.append(username[0] + ": " + userInputString + "\n");
						serverOut.println(username[0] + ": " + userInputString);
						//scroll.getVerticalScrollBar().setValue(scroll.getVerticalScrollBar().getMaximum());
						t1.setText("");
					}
				}
			});

			t1.setBounds(0,330, 400,70);
			scroll.setBounds(0, 0, 400, 300);

			textPanel.add(scroll);
			inputPanel.add(t1);
			f.setSize(400,400);

			f.add(textPanel);
			f.add(inputPanel);
			f.setLayout(null);
			f.setVisible(true);

			t2.append("What is your name? \n");


			while(true) {

				String serverResponse = serverIn.readLine();
				t2.append(serverResponse + "\n");
			}			
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		EchoClient myEchoClient = new EchoClient("redacted");
		myEchoClient.go();
	}
}
