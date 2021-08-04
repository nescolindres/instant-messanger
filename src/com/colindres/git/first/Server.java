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
}
