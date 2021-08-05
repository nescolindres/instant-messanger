package com.colindres.git.first;
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Server extends JFrame{

	private JTextField userText; 			//The box where the user types his message
	private JTextArea chatWindow; 			//The area where the messages appear during conversation
	private ObjectOutputStream output;		//The connection from the server to the client. This is the output stream (i.e connection)
	private ObjectInputStream input;		// The connection from the client to the server.
	private ServerSocket server;			//Used for port and backlog (i.e queue)
	private Socket connection; 				//The initial connection to start the server. 
	
	
	// Constructor that sets up the GUI
	public Server() {
		super("Nes Instant Messenger"); //Window's name
		userText = new JTextField();
		userText.setEditable(false);  //Sets the user's input text box to a grey non editable box. Prevents user to send input when no one is connected to avoid errors
		userText.addActionListener(
				new ActionListener() {		//Waiting for the user to add text to the text box
					public void actionPerformed(ActionEvent event) {	
						sendMessage(event.getActionCommand());		//TODO: implement sendMessage() sends the message to the chat area
						userText.setText("");		// resets the user text box to empty once they send their message
					}
				}
		);
		add(userText, BorderLayout.NORTH); 	//adding the userText to the chat window, and its location is north (might switch to south)
		chatWindow = new JTextArea();	//simply creating a new chat object
		add(new JScrollPane(chatWindow));
		setSize(300,150);		//adds the dimensions of the chat window
		setVisible(true);		// setting the visibility to true
	}
	
	// set up and run the server
	public void startRunning() {
		try {
			server = new ServerSocket(6789, 100);  //ServerSocket takes in two parameters. the first is the port number(dummy port used in place) and the second is the backlog.
			while(true) {
				try {		//this is where we connect and have a conversation TODO: implement waitForConnection(), setupStreams(), whileChatting().
					waitForConnection(); //When we start the server, it is going to wait for someone to connect with.
					setupStreams();		//Once a user connects, its going to setup the input and output streams from the server to the user
					whileChatting();	//Once the streams are connected, this method will append the conversations to the chatbox and update the chatwindow without creating a new GUI everytime
				}catch(EOFException eofException) { //EOFExeption signals end of stream. When the user disconnects. It will catch this exception when we end conversation
					showMessage("\n Server ended the conncetion!");  //TODO: implement showMessage()
				}finally {
					closeAll(); 
				}
			}
		}catch(IOException ioException) {
			ioException.printStackTrace();
		}
	}
	
	//waiting for connection, the n display connection information
	private void waitForConnection() throws IOException{
		showMessage("Waiting for someone to connect.... \n");
		connection = server.accept(); //listens for a connection and accepts it once a request has been made.
		showMessage("Now connected to " + connection.getInetAddress().getHostName()); //Shows who you are connected to with IP address and Host Name

	}
	
	//get stream to send and recieve data
	private void setupStreams() throws IOException {
		output = new ObjectOutputStream(connection.getOutputStream()); // creating the pathway that allows us to connect to another computer. We are connecting to whatever Socket the connection var created.
		output.flush(); //flushes the stream. In case there is any data that is left over in the stream we flush it out to avoid any potential errors and data leaks. 
		input = new ObjectInputStream(connection.getInputStream()); // creating pathway to retrieve information from another computer.
		showMessage("\n Streams are now setup! \n");
	}
	
	// during the chat conversation
	private void whileChatting() throws IOException {
		String message = " You are now connected!";
		sendMessage(message);
		ableToType(true); //We set the intial setEditable to false in our default constructor. Now since we are connected we need to change it back to true. TODO: implement ableToType()
		do {
						// have conversation
			try {
				message = (String) input.readObject(); //we are reading the input from their stream socket and saving it as message. 
				showMessage("\n" + message);
			}catch(ClassNotFoundException classNotFoundException) {
				showMessage("\n Message not readable");
			}
		}while(!message.equals("CLIENT - END"));
	}
	
	//close streams and sockets after you are done chattign
	private void closeAll() {
		showMessage("\n Closing connections... \n");
		ableToType(false);
		try {
			output.close();
			input.close();
			connection.close();
		}catch(IOException ioException) {
			ioException.printStackTrace();
		}
	}
	
	//send a message to client 
	private void sendMessage(String message) {
		try {
			output.writeObject("SERVER - " + message); //this creates an object and sends the message from the server
			output.flush();
			showMessage("\nSERVER -" + message);
		}catch(IOException ioException) {
			chatWindow.append("\n Cant send message");
		}
	}
	
	//updates chatWindow
	private void showMessage(final String text) {
		SwingUtilities.invokeLater(		//allows you to create a thread without making a new GUI every time. updates the text inside chat window
				new Runnable() {
					public void run() {
						chatWindow.append(text); //appending text at the end of the chat window
					}
				}
			);
	}
	
	//let the user type stuff into their box
	private void ableToType(final boolean tof) {
		SwingUtilities.invokeLater(		
				new Runnable() {
					public void run() {
						userText.setEditable(tof); //set the text box to true which allows user to type
					}
				}
			);
	}
	
}
