import javax.swing.JFrame;

public class GameFrame extends JFrame{
	
	// Create a constructor
	public GameFrame() {
		
		// 'this' keyword in a constructor refers to the current instance of the class
		this.add(new GamePanel());
		this.setTitle("Snake Game");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		
	}
}
