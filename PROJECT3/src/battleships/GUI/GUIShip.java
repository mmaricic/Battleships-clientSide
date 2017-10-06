package battleships.GUI;

import java.awt.*;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class GUIShip extends JButton {

	private int SegmentNum;


	public GUIShip(int n) {
		SegmentNum = n;
		setMinimumSize(new Dimension(n * 25, 25));
		setMaximumSize(new Dimension(n * 25, 25));
		setPreferredSize(new Dimension(n * 25, 25));
		setBorder(BorderFactory.createLineBorder(Color.black, 1));
	}


	public int getSegNum() {
		return SegmentNum;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		Stroke oldStroke = g2.getStroke();
		g2.setStroke(new BasicStroke(2));

		for (int i = 1; i <= SegmentNum; i++) {
			g.setColor(Color.black);
			g.drawRect(getHorizontalAlignment() + (getWidth() / SegmentNum * (i - 1)), getVerticalAlignment(),
					getWidth() / SegmentNum * i, getHeight());
			g.setColor(new Color(0, 153, 76));
			g.fillRect(getHorizontalAlignment() + (getWidth() / SegmentNum * (i - 1)), getVerticalAlignment(),
					getWidth() / SegmentNum * i, getHeight());
		}

		g2.setStroke(oldStroke);
	}
}
