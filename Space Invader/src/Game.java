import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Game extends JFrame implements ActionListener, KeyListener, Runnable, SpaceClientInterface {
	// game ID
	private static final long serialVersionUID = 308723318977051841L;
	
	//storage classes
	private Player myPlayer;
	private Player secondPlayer;
	private ArrayList<Alien> enemies;
	private Bullet myBullet;
//	private ArrayList<Bullet> bullets;
//	bullets = new ArrayList<Bullet>();

	//graphic labels
	private JLabel BulletLabel;
	private ImageIcon BulletImage;
	
	//start, stop button
	private JButton startButton;
	
	//texts
	private String score = "Score: ";
	private String over = "Game Over";
	
	//graphic containers
	private Container content;
	
	private SpaceClient client;
	private Thread gameThread;
	private boolean stopGame = false;
	
	public static void main(String[] args) {
		Game myGame = new Game();
		myGame.setVisible(true);
	}
	
	//GUI setting
	public Game() {
		super("Space Invader");
		setSize(GameProperties.SCREEN_WIDTH + 15, GameProperties.SCREEN_HEIGHT);
		System.out.println("width=" + getSize().width);
		
		//content
		content = getContentPane();
		content.setBackground(Color.black);

		setLayout(null);
		
		//-----------------------------
		//player
		myPlayer = createPlayer(1);
		
		
		//-----------------------------
		//alien arrays
		int[] alienTypes = {1, 1, 2, 2, 3, 2, 2, 1, 1,
							1, 1, 2, 2, 3, 2, 2, 1, 1};
		enemies = new ArrayList<Alien>();
		
		for (int i = 0 ; i < 18; i++) {
			//(int)Math.floor(Math.random()*3+1));
			Alien newAlien = new Alien(alienTypes[i]);
			
			newAlien.addToScreen(this);
			newAlien.setVisible(false);
			
			enemies.add(newAlien);
		}
		
		
		//-----------------------------
		//buttons
		startButton = new JButton("Start");
		startButton.setSize(GameProperties.SCREEN_WIDTH, 30);
		startButton.setLocation(0, 0);
		add(startButton);
		startButton.addActionListener(this);
		startButton.setFocusable(false);

		startButton.setFont(new Font("GravityRegular5", Font.BOLD, 7));
		startButton.setBackground(new Color(0,51,204));
		startButton.setForeground(Color.WHITE);
		startButton.setFocusPainted(false);
		startButton.setBorderPainted(false);
		
		content.addKeyListener(this);
		content.setFocusable(true);

		//Close program
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		// Init client conenction
		client = new SpaceClient(this);
	}
	
	public Player createPlayer(int type) {
		Player newPlayer = new Player(type);
		
		newPlayer.addToScreen(this);
		newPlayer.Display();
		
		return newPlayer;
	}
	
	public void startGame() {
		myPlayer.setX(150);
		myPlayer.setY(330);
		
		
		myBullet = new Bullet();
		BulletLabel = new JLabel();
		BulletImage = new ImageIcon( getClass().getResource(myBullet.getFilename() ) );
		BulletLabel.setIcon(this.BulletImage);
		BulletLabel.setSize(this.getWidth(), this.getHeight());
		
		myBullet.setX(GameProperties.SCREEN_WIDTH / 2);
		myBullet.setY(GameProperties.SCREEN_HEIGHT);
		myBullet.addToScreen(this);
		
		
		//------------------------
		//alien
		int posX = 30;
		int posY = 35;
		
		// TODO: remove remaining enemies from screen
		// TODO: create new enemies
		
		for (int i = 0 ; i < enemies.size(); i++) {
			if (i == 9) {
				posX = 30;
				posY = 80;
			}
			
			Alien curAlien = enemies.get(i);
			
			curAlien.setX(posX);
			curAlien.setY(posY);
			curAlien.setVisible(true);
			
			posX += curAlien.getWidth() + 5;
		}
		
		gameThread = new Thread(this, "Game Thread");
		gameThread.start();
		
		startButton.setText("Stop");
	}
	
	//stop game
	public void stopGame() {
		if (null != gameThread) {
			stopGame = true;
			try {
				gameThread.join();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			stopGame = false;
			gameThread = null;
			
			startButton.setText("Start");
		}
	}
	
	//Alien auto movement
	@Override
	public void run() {
		while(false == stopGame) {
			// move enemies
			// move every 200ms
			// if 200ms passed
			for (int i = 0 ; i < enemies.size(); i++) {
				enemies.get(i).makeMove();
			}
			
			// move bullets
			//	move every 50ms
			// TODO
			
			// detect collision
			// TODO
			// for every alien check detection with every bullet
			// foreach (alien)
			//		foreach(bullet)
			//			if (bullet->detectCollision(alien))
			//				alien->kill()
			//				enemies->remove(alien)
			//		if (alien->detectCollision(player))
			//			player->kill()
			// TODO
			
			// check end of the game condition
			//	if (enemies->size() == 0 || 
			// 		(aliens reached end of the screen) ||
			//		(player dead) )
			//		-> end game
			// TODO
			
			try {
				Thread.sleep(200);
			} catch (Exception e) {
			}
		}
	}

	
	//key events
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
		
	@Override
	public void keyPressed(KeyEvent e) {
		// spaceship movement
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			myPlayer.moveLeft();
			client.sendPlayerMoveLeft();
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			myPlayer.moveRight();
			client.sendPlayerMoveRight();
		}
		
		// space key for bullet
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			int bx = myPlayer.getX();
			int by = myBullet.getY();
			
			myBullet.setVisible(true);
			myBullet.moveBullet();
			myBullet.setX(bx);
		}
	}
		

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	//Alien movement
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == startButton) {
			if (null != gameThread) {
				stopGame();
				client.sendGameEnd();
			} else {
				startGame();
				client.sendGameStart();
			}
		}
	}

	@Override
	public void onServerCommand(String cmd) {
		System.out.println("game command: " + cmd);
		
		if (cmd.equals("NEW_PLAYER")) {
			secondPlayer = createPlayer(2);
		} else if (null != secondPlayer) {
			if (cmd.equals("PLAYER_LEFT")) {
				secondPlayer.moveLeft();
			} else if (cmd.equals("PLAYER_RIGHT")) {
				secondPlayer.moveRight();
			} else if (cmd.equals("PLAYER_QUIT")) {
				secondPlayer.removeFromScreen(this);
			} else if (cmd.equals("GAME_START")) {
				startGame();
			} else if (cmd.equals("GAME_END")) {
				stopGame();
			}
		}
	}

}

	
	
	
	
