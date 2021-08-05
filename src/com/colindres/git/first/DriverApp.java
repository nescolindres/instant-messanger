package com.colindres.git.first;
import javax.swing.JFrame;
public class DriverApp {

	public static void main(String[] args) {
		Server server = new Server();
		server.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		server.startRunning();
	}

}
