package utility;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class RoundEdgedPanel extends JPanel{

	/**
	 * Randomly generated id for this class
	 */
	private static final long serialVersionUID = 1656942567792259555L;
	
	public RoundEdgedPanel(String title) {
		Border border = BorderFactory.createTitledBorder(title);
		setBorder(border);
	}
	
	protected void paintComponent(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(new Color(255, 255, 255, 100));
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
        g2d.setStroke(new BasicStroke());
        super.paintComponent(g);
    }
}
