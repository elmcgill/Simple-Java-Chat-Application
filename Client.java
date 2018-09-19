import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	
	private static Socket client = null;
	private static PrintWriter out = null;
	private static Scanner userIn = null;
	private static Scanner serverIn = null;
	private static String host = "localhost";
	private static int port = 3190;
	private static String name = "";
	private static serverHandler sh;
	
	Client() {
		
		try {
			client = new Socket(host,port);
			System.out.println("You have connected to the server " + host + "with port number " + port);
			System.out.println("Please enter a username: ");
			userIn = new Scanner(System.in);
			name = userIn.nextLine();
			sh = new serverHandler(this, client);
			new Thread(sh).start();
			
			while(true) {
				userIn = new Scanner(System.in);
				String message = userIn.nextLine();
				out.println(name + " > " + message);
			}
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		Client client = new Client();
	}
}

class serverHandler implements Runnable{
	
	Client client;
	Scanner serverIn;
	
	serverHandler(Client client, Socket s){
		try {
		this.client = client;
		serverIn = new Scanner(s.getInputStream());
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void run() {
		while(true) {
			String server = serverIn.nextLine();
			System.out.println(server);
		}
		
	}
	
	
	
}
