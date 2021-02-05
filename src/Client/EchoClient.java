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
import java.nio.Buffer;

public class EchoClient {

	private static Socket serverSocket;
	private String address;
	private static PrintWriter serverOut;
	private static BufferedReader serverIn;
	
	public EchoClient() {

	}
	
	public void go() {

		try {
			// Set up the ability to read user input from keyboard

			// Set up the ability to send the data to the server


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


			final int[] firstTime = {0};
			t1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

					if (firstTime[0] == 0){
						String userInputString = t1.getText();
						System.out.println(userInputString);
						t1.setText("");
						try {
							EchoClient.serverSocket = new Socket(userInputString, 14002);
							EchoClient.serverOut = new PrintWriter(EchoClient.serverSocket.getOutputStream(), true);

							EchoClient.serverIn = new BufferedReader(new InputStreamReader(EchoClient.serverSocket.getInputStream()));
						} catch (IOException error) {
							error.printStackTrace();
						}
						t2.append(userInputString);

						t2.append("\nWhat is your name? \n");
						firstTime[0] = 1;

					}
					else if (firstTime[0] == 1){
						String userInputString = t1.getText();
						t2.append("Welcome to the chat, " + userInputString + "!\n");
						EchoClient.serverOut.println(userInputString);
						t1.setText("");
						firstTime[0] = 2;
					} else {
						String userInputString = t1.getText();
						EchoClient.serverOut.println(userInputString);
						//scroll.getVerticalScrollBar().setValue(scroll.getVerticalScrollBar().getMaximum());
						t1.setText("");
					}
				}
			});

			t2.append("What ip would you like to connect to? \n");
			t1.setBounds(0,330, 400,70);
			scroll.setBounds(0, 0, 400, 300);

			textPanel.add(scroll);
			inputPanel.add(t1);
			f.setSize(400,400);

			f.add(textPanel);
			f.add(inputPanel);
			f.setLayout(null);
			f.setVisible(true);




			while(true) {
					try {
						String serverResponse = EchoClient.serverIn.readLine();
						t2.append(serverResponse + "\n");
					} catch (Exception e){
						continue;
					}



			}			
		} catch (Exception e) {
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
	
	public static void main(String[] args) throws IOException {

		EchoClient myEchoClient = new EchoClient();
		myEchoClient.go();
	}
}
