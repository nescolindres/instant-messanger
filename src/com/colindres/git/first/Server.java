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
	
}
