package utility;

import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.text.NumberFormatter;

public class Formatter extends NumberFormatter{
	private static final long serialVersionUID = 9089086654371696163L;

	public Formatter(int maximum)
	{
		super(NumberFormat.getInstance());
		setValueClass(Integer.class);
		setMinimum(1);
		setMaximum(maximum);
		setAllowsInvalid(false);
		setCommitsOnValidEdit(true);
	}
	
	public Formatter(int min, int max) {
		super(NumberFormat.getInstance());
		setValueClass(Integer.class);
		setMinimum(min);
		setMaximum(max);
		setAllowsInvalid(false);
		setCommitsOnValidEdit(true);
	}
	
	public Object stringToValue(String string) throws ParseException {
        if (string == null || string.length() == 0) {
            return null;
        }
        return super.stringToValue(string);
	}
	
}
