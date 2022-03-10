import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Bullet extends Sprite implements Runnable {
	
	// bullet(array,movement), collision detection, game end logic, destroying alien
	
	private boolean visible, moving;
	private Thread b;
	private JLabel BulletLabel;
	private ImageIcon BulletImage;

	public boolean getVisible() { return visible; }
	public boolean getMoving() { return moving; }

	public void setVisible(boolean visible) { this.visible = visible; }
	public void setMoving(boolean moving) { this.moving = moving; }

	public void setBulletLabel(JLabel temp) { this.BulletLabel = temp; }
	
	public Bullet() {
		super(16, 16, "bullet_2.png");
		this.visible = true;
		this.moving = false;
		this.BulletLabel = new JLabel();
		this.BulletImage = new ImageIcon( getClass().getResource(this.getFilename() ) );
		this.BulletLabel.setIcon(this.BulletImage);
		this.BulletLabel.setSize(this.getWidth(), this.getHeight());
	}
	
	public void addToScreen(JFrame frame) {
		this.BulletLabel.setLocation(this.x, this.y);
		frame.add(this.BulletLabel);
		this.BulletLabel.setVisible(this.getVisible());
	}
	
	public void hide() { this.visible = false; }
	public void show() { this.visible = true; }
	
	public void Display() {
		System.out.println("X,Y / vis: " + this.x + "," + this.y + " / " + this.visible);
	}
	
	public void moveBullet() {
		b = new Thread(this, "Bullet Thread");
		b.start();
	}
	
	@Override
	public void run() {
		this.moving = true;
//		this.visible = true;
		System.out.println("run");
		
		while (moving) {
			int bx = this.x;
			int by = this.y;
			
			bx += 0;
			by += GameProperties.CHARACTER_STEP * -2;
			
			this.setX(bx);
			this.setY(by);
			
			BulletLabel.setLocation(this.x, this.y);
//			this.detectCollision();
			
			try {
				Thread.sleep(200);
			} catch (Exception e) {
			}
		
	}
  }
	
	public boolean detectCollision(Alien currentAlien) {	
		return this.r.intersects(currentAlien.getRectangle());
	}
	

}
