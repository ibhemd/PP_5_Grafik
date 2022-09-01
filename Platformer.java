import java.awt.*;
import java.io.IOException;
import java.io.Serial;
import javax.swing.JFrame;

public class Platformer extends JFrame {
	@Serial
	private static final long serialVersionUID = 5736902251450559962L;

	public Platformer() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("HelloWorld");
		this.setBounds(0, 0, 1000, 350);
		this.setVisible(true);
	}

	@Override
	public void paint(Graphics g) {

		Graphics2D g2d = (Graphics2D) g; // cast to Graphics2D

		try {
			Level.drawLevelImage(g);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
