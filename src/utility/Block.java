package utility;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class Block extends JPanel{

	private static final long serialVersionUID = -4627353772145679363L;
	private int processID;
	
	public Block(int processID) {
		this.processID = processID;
		setLayout(new MigLayout("fillx"));
		setBorder(BorderFactory.createLineBorder(Color.GRAY));
		setBackground(new Color(230, 230, 230));
		
		add(new Label(new Font("Arial", Font.BOLD, 10), processID + ""), "align center, gapLeft 15px, gapRight 15px, gapTop 5px, gapBottom 5px");
		setSize(100, 100);
		setVisible(true);
	}
	
	public int getProcessID() {
		return processID;
	}
	
}
