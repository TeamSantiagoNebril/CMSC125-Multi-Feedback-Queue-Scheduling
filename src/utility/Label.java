package utility;

import java.awt.Font;

import javax.swing.JLabel;

public class Label extends JLabel{

	private static final long serialVersionUID = 5672211036372247630L;

	public Label(Font font, String text) {
		setText(text);
		setFont(font);
	}
}
