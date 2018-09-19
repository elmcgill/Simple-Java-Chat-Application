import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server {
	
	private static ServerSocket server = null;
	private static Socket client = null;
	private static ArrayList<clientHandler> clientList = new ArrayList<clientHandler>();
	private static int port = 3190;
	
	public static void main(String[] args) {
		
		System.out.println("Server started on port " + port);
		System.out.println("Waiting for clients to connect");
		
		try {
			server = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println("Could not connect to server");
		}
		
		while(true) {
			try {
				client = server.accept();
				//System.out.println("A new client connected to server");
				clientHandler ch = new clientHandler(client, clientList);
				Thread thread = new Thread(ch);
				thread.start();
				clientList.add(ch);
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}

class clientHandler implements Runnable{
	
	private Scanner in = null;
	private PrintWriter out = null;
	private Socket client = null;
	private ArrayList<clientHandler> clientList;
	
	public clientHandler(Socket client, ArrayList<clientHandler> clientList) {
		this.client = client;
		this.clientList = clientList;
	}
	
	@Override
	public void run() {
		
		try {
			in = new Scanner(client.getInputStream());
			out = new PrintWriter(new BufferedOutputStream(client.getOutputStream()));
			while (true) {
				String message = in.nextLine();
				for (int i = 0; i < clientList.size(); i++) {
					out = new PrintWriter(clientList.get(i).client.getOutputStream());
					out.println(message);
				}
				System.out.println(message);
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
