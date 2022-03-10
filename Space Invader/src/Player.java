import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Player extends Sprite implements KeyListener {
	private boolean visible;
	private JLabel PlayerLabel;
	private ImageIcon PlayerImage;
	
	public Boolean getVisible() { return visible; }
	public void setVisible(Boolean visible) { this.visible = visible; }
	
	public Player(int type) {
		super(16, 16, "spaceship_" + type + ".png");
		
		PlayerLabel = new JLabel();
		PlayerImage = new ImageIcon( getClass().getResource(getFilename()) );
		PlayerLabel.setIcon(PlayerImage);
		PlayerLabel.setSize(getWidth(), getHeight());
		
		setX(150);
		setY(330);
		PlayerLabel.setVisible(getVisible());
		PlayerLabel.setLocation(getX(), getY());
		
		this.visible = true;
	}
	
	public void addToScreen(JFrame frame) {
		this.PlayerLabel.setLocation(this.x, this.y);
		frame.add(this.PlayerLabel);
		this.PlayerLabel.setVisible(this.getVisible());
	}
	
	public void removeFromScreen(JFrame frame) {
		this.PlayerLabel.setVisible(false);
		frame.remove(this.PlayerLabel);		
	}
	
	public void moveLeft() {
		int px = getX();
		int py = getY();
		
		px -= GameProperties.CHARACTER_STEP;
		if (px + getWidth() < 0) {
			px = GameProperties.SCREEN_WIDTH;
		}
		
		setX(px);
		setY(py);
		
		PlayerLabel.setLocation(getX(), getY());
	}
	
	public void moveRight() {
		int px = getX();
		int py = getY();
		
		px += GameProperties.CHARACTER_STEP;
		if (px > GameProperties.SCREEN_WIDTH) {
			px = -1 * getWidth();
		}
		
		setX(px);
		setY(py);
		PlayerLabel.setLocation(getX(), getY());
	}
	

	public void Display() {
		System.out.println("X,Y / vis: " + this.x + "," + this.y + " / " + this.visible);
		visible = true;
		PlayerLabel.setVisible(true);
		PlayerLabel.setLocation(getX(), getY());
	}
	
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


}
