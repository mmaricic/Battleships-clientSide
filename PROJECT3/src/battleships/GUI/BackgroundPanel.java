package battleships.GUI;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class BackgroundPanel extends JPanel {

	private BufferedImage image;

	public BackgroundPanel(InputStream inputStream) {
		setOpaque(true);
		try {

			image = ImageIO.read(inputStream);
		} catch (IOException ioe) {
		}
	}


	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
	}
}
