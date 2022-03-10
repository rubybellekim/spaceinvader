import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Alien extends Sprite {
	
	private boolean visible, moving;
	private int direction;
	private JLabel AlienLabel;
	private ImageIcon AlienImage;

	public boolean getVisible() { return visible; }
	public boolean getMoving() { return moving; }
	
	public void setVisible(boolean visible) { this.visible = visible; }
	public void setAlienLabel(JLabel temp) { this.AlienLabel = temp; }
	
	public Alien(int type) {
		super(16, 16, "alien_" + type + ".png");
		this.visible = true;
		this.moving = false;
		this.direction = 1;
		this.AlienLabel = new JLabel();
		this.AlienImage = new ImageIcon( getClass().getResource(this.getFilename() ) );
		this.AlienLabel.setIcon(this.AlienImage);
		this.AlienLabel.setSize(this.getWidth(), this.getHeight());
	}
	
	
	public void addToScreen(JFrame frame) {
		this.AlienLabel.setLocation(this.x, this.y);
		frame.add(this.AlienLabel);
		this.AlienLabel.setVisible(this.getVisible());
	}
	
	public void hide() { this.visible = false; this.AlienLabel.setVisible(false); }
	public void show() { this.visible = true; this.AlienLabel.setVisible(true); }
	
	public void Display() {
		System.out.println("X,Y / vis: " + this.x + "," + this.y + " / " + this.visible);
	}
	
	private boolean switched_direction = false;
	
	public void makeMove() {
		int ax = this.x;
		int ay = this.y;

		if ( switched_direction == false && (ax >= (GameProperties.SCREEN_WIDTH - this.width - 10) || ax <= 10)) {
			this.direction *= -1;
			switched_direction = true;
			ay += GameProperties.CHARACTER_STEP * 2;
			if ( ay >= GameProperties.SCREEN_HEIGHT) {
				this.moving = false;
			}
		} else {
			ax += GameProperties.CHARACTER_STEP * this.direction;
			switched_direction = false;
		}
		
		this.setX(ax);
		this.setY(ay);
		this.AlienLabel.setLocation(this.x, this.y);
	}
	
//	private void detectBulletCollision() {
//		if (this.r.intersects( myBullet.getRectangle() ) ) {
//			System.out.println("V");
//			AlienLabel.setIcon( new ImageIcon ( getClass().getResource("die.png")));
//		}
//	}

}


