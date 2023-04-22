//Emily Robey
//04/14/23
//CST 238 Lab 2
package JavaCalc;

import java.awt.Font;
import javax.swing.JTextField;

//subclass of JTextField
//sets width, alignment and starting values for calculator screen 
//allows for setting and resetting of values on screen 
public class CalcScreen extends JTextField {
	private static final long serialVersionUID = 1L;

	public CalcScreen(int width) {
		super(width);
		setFont(new Font(Font.MONOSPACED, Font.PLAIN, 24));
		setHorizontalAlignment(JTextField.RIGHT);
		setEditable(false);
		setText("0");
	}

	public void addDigit(String digit) {
		String text;
		if (m_resetOnPress)
			text = "0";
		else
			text = getText();
		m_resetOnPress = false;
		setText(text + digit);
	}

	public double getValue() {
		return new Double(getText());
	}

	public void setValue(double value) {
		if (value == 0)
			setText("0");
		else {
			int iValue = (int) value;
			if (iValue == value)
				setText(Integer.toString(iValue));
			else
				setText(Double.toString(value));
		}
	}
	
	public void setValue(String value) {
		if (value == "0")
			setText("0");
		else
		    setText(value);
	}

	public void setReset() {
		m_resetOnPress = true;
	}

	protected boolean m_resetOnPress = false;
}
