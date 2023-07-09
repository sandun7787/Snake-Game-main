import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener {
	
	private static final int SCREEN_WIDTH = 600;
	private static final int SCREEN_HEIGHT = 600;
	private static final int UNIT_SIZE = 25;
	private static final int GAME_UNITS = ((SCREEN_HEIGHT * SCREEN_WIDTH) / UNIT_SIZE);
	private static final int DELAY = 75;
	private final int X[] = new int[GAME_UNITS];
	private final int Y[] = new int[GAME_UNITS];
	int bodyParts = 6;
	int appleEaten;
	int appleX;
	int appleY;
	char direction = 'R'; // R for Right
	boolean running = false;
	Timer timer;
	Random random;
	
	// Create a Constructor
	public GamePanel() {
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}
	
	public void startGame() {
		newApple();
		running = true;
		timer = new Timer(DELAY, this);
		timer.start();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	public void draw(Graphics g) {
		if (running) {
			/* for (int i = 0; i < (SCREEN_HEIGHT / UNIT_SIZE) ; i++) {
				g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
				g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
			} */
			
			// Draw an Apple
			g.setColor(Color.red);
			g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
			
			// Draw an Snake
			// Iterate body parts of Snake
			for (int i = 0; i < bodyParts; i++) {
				if (i == 0) {
					g.setColor(Color.green);
					g.fillRect(X[i], Y[i], UNIT_SIZE, UNIT_SIZE);
				} else {
					g.setColor(new Color(45, 180, 0)); // RGB Color
					g.fillRect(X[i], Y[i], UNIT_SIZE, UNIT_SIZE);
				}
			}
			
			g.setColor(Color.red);
			g.setFont(new Font("Ink Free", Font.BOLD, 40));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString(("Score: " + appleEaten), ((SCREEN_WIDTH - metrics.stringWidth("Score: " + appleEaten)) / 2), g.getFont().getSize());
			
		} else {
			gameOver(g);
		}
		
	}
	
	public void newApple() {
		appleX = random.nextInt((int)(SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
		appleY = random.nextInt((int)(SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
	}

	public void move() {
		for (int i = bodyParts; i > 0; i--) {
			X[i] = X[i - 1];
			Y[i] = Y[i - 1];
		}
		
		switch (direction) {
		case 'U':
			Y[0] = Y[0] - UNIT_SIZE;
			break;
			
		case 'D':
			Y[0] = Y[0] + UNIT_SIZE;
			break;
			
		case 'L':
			X[0] = X[0] - UNIT_SIZE;
			break;
			
		case 'R':
			X[0] = X[0] + UNIT_SIZE;
			break;
		}
	}
	
	public void checkApple() {
		if ((X[0] == appleX) && (Y[0] == appleY)) {
			bodyParts++;
			appleEaten++;
			newApple();
		}
	}
	
	public void checkCollisions() {
		// Checks if head collides with body
		for (int i = bodyParts; i > 0; i--) {
			if ((X[0] == X[i]) && (Y[0] == Y[i])) {
				running = false;
			}
		}
		
		// Check if head touches border
		if ((X[0] < 0) || (X[0] > SCREEN_WIDTH) || (Y[0] < 0) || (Y[0] > SCREEN_HEIGHT)) {
			running = false;
		}
		
		if (!running)
			timer.stop();
	}
	
	public void gameOver(Graphics g) {
		// Game over text
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 75));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Game Over", ((SCREEN_WIDTH - metrics1.stringWidth("Game Over")) / 2), (SCREEN_HEIGHT / 2));
		
		// Score
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 40));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString(("Score: " + appleEaten), ((SCREEN_WIDTH - metrics2.stringWidth("Score: " + appleEaten)) / 2), g.getFont().getSize());
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (running) {
			move();
			checkApple();
			checkCollisions();
		}
		repaint();
		
	}
	
	// Inner Class
	public class MyKeyAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if (direction != 'R')
					direction = 'L';
				break;
				
			case KeyEvent.VK_RIGHT:
				if (direction != 'L')
					direction = 'R';
				break;
				
			case KeyEvent.VK_UP:
				if (direction != 'D')
					direction = 'U';
				break;
				
			case KeyEvent.VK_DOWN:
				if (direction != 'U')
					direction = 'D';
				break;
			
			}
		}
	}

}
