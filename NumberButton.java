//Emily Robey
//04/14/23
//CST 238 Lab 2
package JavaCalc;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

//subclass of JButton
//instantiates buttons intended for use as number keys
//takes in int values and returns strings 
//sets max and min sizes 
public class NumberButton extends JButton {
	private static final long serialVersionUID = 1L;

	public NumberButton(int value, CalcScreen screen) {
		m_value = Integer.toString(value);
		m_screen = screen;
		addActionListener(new action());
		setText(m_value);
		Dimension maxSize = new Dimension(1000, 1000);
		setMaximumSize(maxSize);
		setMinimumSize(new Dimension (1,1)); 
	}
	public NumberButton(String value, CalcScreen screen) {
		m_value = value;
		m_screen = screen;
		addActionListener(new action());
		setText(m_value);
		Dimension maxSize = new Dimension(1000, 1000);
		setMaximumSize(maxSize); 
		
	}

	protected String m_value;
	protected CalcScreen m_screen;

	protected class action implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			m_screen.addDigit(m_value);
		}
	}
}
