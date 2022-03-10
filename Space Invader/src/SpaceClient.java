import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

interface SpaceClientInterface {                   
	void onServerCommand(String cmd);
}

//processing routine on server for both side
public class SpaceClient implements Runnable {
	final static int CLIENT_PORT = 5656;
	final static int SERVER_PORT = 5556;

	private Socket s;
	private Scanner in;
	private Thread clientThread;
	private boolean stopClient = false;
	private SpaceClientInterface mainGame;

	//connect to the server
	public SpaceClient (SpaceClientInterface game)  {
		mainGame = game;
		try {
			this.s = new Socket("localhost", SERVER_PORT);
			clientThread = new Thread(this, "SpaceClient Thread");
			clientThread.start();
		
			sendCommand("CONNECTED");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//client disconnection
	public void disconnect() throws IOException {
		stopClient = true;
		this.s.close();
		try {
			clientThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		clientThread = null;
	}
	
	//send command
	public void sendCommand(String cmd) {
		if (this.s.isConnected()) {
			try {
				OutputStream outstream = this.s.getOutputStream();
				PrintWriter out = new PrintWriter(outstream);
		
				System.out.println("Sending: " + cmd);
				out.println(cmd + "\n");
				out.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void sendGameStart() {
		sendCommand("GAME_START");
	}
	
	public void sendGameEnd() {
		sendCommand("GAME_END");
	}
	
	public void sendPlayerMoveLeft() {
		sendCommand("PLAYER_LEFT");
	}
	
	public void sendPlayerMoveRight() {
		sendCommand("PLAYER_RIGHT");
	}
	
	// listen for server commands
	public void run() {
		System.out.println("Started client thread");
		
		while(false == stopClient) {
			try {
				in = new Scanner(s.getInputStream());
				processRequest( );
			} catch (IOException e){
				e.printStackTrace();
			} finally {
				try {
					s.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	//processing server commands
	public void processRequest () throws IOException {
		//if next request is empty then return
		while(true) {
			if(!in.hasNext( )){
				return;
			}
			String command = in.next();
			
			executeCommand(command);
		}
	}
	
	// send server commands to the Game
	public void executeCommand(String command) throws IOException{
		System.out.println("received: " + command);
		
		if ( false == command.equals("HELLO")) {
			mainGame.onServerCommand(command);
		}
	}
}
