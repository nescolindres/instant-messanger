package clientProgram;
import javax.swing.JFrame;

public class ClientApp {

	public static void main(String[] args) {
	Client client;
	client = new Client("127.0.0.1"); //local host is being used as the IP address
	client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	client.startRunning();
	}

}
