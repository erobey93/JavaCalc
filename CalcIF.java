//Emily Robey
//04/14/23
//CST 238 Lab 2

package JavaCalc;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

//Creates a main JPanel interactive calculator 
//displays numbers and functions for calculations]
//contains a CalcScreen to display user input and results 
//intended to be used in conjunction with NumberButton, CalcScreen and JavaCalc (or some other JFrame) 
public class CalcIF extends JPanel {

	private static final long serialVersionUID = 1L;
	protected GridBagLayout m_layout;
	protected CalcScreen m_screen;
	protected JButton[] m_Numbers;
	protected JButton[] m_FunctionButtons;
	// protected double m_savedValue = 0;
	protected String m_numStringSaved = "";
	protected String m_currValue = "";
	protected String m_prevValue = "";
	protected char m_lastOp = '=';
	protected int cWidth;
	protected int rHeight;
	protected Color defaultButtonColor; 
	protected Color defaultBackground; 
	// two dimensional arrays to set constraints for number and function buttons
	// [0] = gridx; [1] = gridy, [2] = gridwidth, [3] = gridheight
	private int[][] numConstraints = new int[][] { { 0, 5, 2, 1 }, { 0, 4, 1, 1 }, { 1, 4, 1, 1 }, { 2, 4, 1, 1 },
			{ 0, 3, 1, 1 }, { 1, 3, 1, 1 }, { 2, 3, 1, 1 }, { 0, 2, 1, 1 }, { 1, 2, 1, 1 }, { 2, 2, 1, 1 } };
	private int[][] functionConstraints = new int[][] { { 2, 5, 1, 1 }, { 3, 4, 1, 2 }, { 3, 3, 1, 1 }, { 3, 2, 1, 1 },
			{ 0, 1, 1, 1 }, { 3, 1, 1, 1 }, { 2, 1, 1, 1 }, { 1, 1, 1, 1 } };

	public CalcIF() {
		Dimension maxSize = new Dimension(1000, 1000);
		m_screen = new CalcScreen(10);

		setPreferredSize(new Dimension(320, 505)); //TODO can I use this to shrink/grow calc as screen grows? 

		// set GridBagConstraints object
		GridBagConstraints c = new GridBagConstraints();

		// set layout variables
		m_layout = new GridBagLayout();
		setLayout(m_layout);

		cWidth = 80;
		rHeight = 80;
		setButtonColumnWidth(80);
		setButtonRowHeight(80);

		// create number button array
		m_Numbers = new NumberButton[10];
		
		// set number buttons
		// add constraints using numConstraints
		// add to screen
		for (int i = 0; i < m_Numbers.length; i++) {

			m_Numbers[i] = new NumberButton(i, m_screen);
			m_Numbers[i].addActionListener(new NumberListener(i));
			m_Numbers[i].setMaximumSize(maxSize);
			m_Numbers[i].setPreferredSize(maxSize);
			c.gridx = numConstraints[i][0];
			c.gridy = numConstraints[i][1];
			c.gridwidth = numConstraints[i][2];
			c.gridheight = numConstraints[i][3];
			c.fill = GridBagConstraints.BOTH;
			c.insets = new Insets(2, 2, 2, 2);

			add(m_Numbers[i], c);

		}
		//save default button color for later use 
		defaultButtonColor = m_Numbers[0].getBackground();
		
		// create function button array
		m_FunctionButtons = new JButton[8];

		// set function buttons
		// set action listeners and max size for each
		m_FunctionButtons[0] = new JButton(".");
		m_FunctionButtons[0].addActionListener(new FunctionListener('.'));
		m_FunctionButtons[1] = new JButton("=");
		m_FunctionButtons[1].addActionListener(new FunctionListener('='));
		m_FunctionButtons[2] = new JButton("+");
		m_FunctionButtons[2].addActionListener(new FunctionListener('+'));
		m_FunctionButtons[3] = new JButton("-");
		m_FunctionButtons[3].addActionListener(new FunctionListener('-'));
		m_FunctionButtons[4] = new JButton("C");
		m_FunctionButtons[4].addActionListener(new FunctionListener('c'));
		m_FunctionButtons[5] = new JButton("*");
		m_FunctionButtons[5].addActionListener(new FunctionListener('*'));
		m_FunctionButtons[6] = new JButton("/");
		m_FunctionButtons[6].addActionListener(new FunctionListener('/'));
		m_FunctionButtons[7] = new JButton("+/-");
		m_FunctionButtons[7].addActionListener(new FunctionListener('s'));

		// add function button constraints
		// add to screen
		for (int i = 0; i < m_FunctionButtons.length; i++) {

			c.gridx = functionConstraints[i][0];
			c.gridy = functionConstraints[i][1];
			c.gridwidth = functionConstraints[i][2];
			c.gridheight = functionConstraints[i][3];
			c.fill = GridBagConstraints.BOTH;
			c.insets = new Insets(2, 2, 2, 2);
			m_FunctionButtons[i].setMaximumSize(maxSize);

			add(m_FunctionButtons[i], c);
		}

		// add text box at top of screen
		c.gridy = 0;
		c.gridx = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 5;
		c.anchor = GridBagConstraints.PAGE_START;
		c.insets = new Insets(10, 5, 0, 5);
		add(m_screen, c);

	}

	// re-sets button row sizes
	// used to allow for appropriate shrink/growing when frame is adjusted
	public void setButtonRowHeight(int rowHeight) {

		rHeight = rowHeight;
		// FIXME make data member ?
		m_layout.rowHeights = new int[] { rHeight, rHeight, rHeight, rHeight, rHeight, rHeight };

	}

	// resets button column sizes
	// used to allow for appropriate shrink/growing when frame is adjusted
	public void setButtonColumnWidth(int columnWidth) {
		cWidth = columnWidth;
		// FIXME make data member ?
		m_layout.columnWidths = new int[] { cWidth, cWidth, cWidth, cWidth };

	}

	public int getButtonWidth() {
		return cWidth;

	}
	
	//public void setPanelSize

	public int getButtonHeight() {
		return rHeight;
	}

	// action listener for number buttons
	protected class NumberListener implements ActionListener {
		int i;

		NumberListener(int index) {
			i = index;
			m_screen.setText(""); // get rid of leading zero
		}

		public void actionPerformed(ActionEvent e) {
			// if there is no operation waiting to be performed
			// save values to a string
			if (m_lastOp != 0) {
				appendToOutput(m_Numbers[i].getText());
			}
		}
	}

	// ActionListener for function buttons
	// interfaces with clearAll, selectOperators, calc and updateOutput
	protected class FunctionListener implements ActionListener {

		protected char function;

		FunctionListener(char uFunc) {
			function = uFunc;
		}

		public void actionPerformed(ActionEvent e) {

			switch (function) {
			case 'c':
				clearAll();
				selectOperator(function);

				break;
			case '=':
				// run operation
				calc();

				break;
			case '+':
				// add
				selectOperator(function);

				break;
			case '-':
				// subtract
				selectOperator(function);

				break;
			case '*':
				// multiply
				selectOperator(function);

				break;
			case '/':
				// divide
				selectOperator(function);

				break;
			case '.':
				// add decimal
				appendToOutput(".");
				break;

			case 's':
				if (m_currValue != "") {
					if (m_currValue.charAt(0) != '-') {
						m_currValue = "-" + m_currValue;
					} else {
						m_currValue = m_currValue.substring(1);
					}
				}
				break;

			}
			// add most recent value/calculation to the screen
			updateOutput();

		}
	}

	// removes one value off of the text on screen
	public void delete() {
		if (m_currValue.length() > 0) {
			m_currValue = m_currValue.substring(0, m_currValue.length() - 1);
		}
	}

	// update the numbers outputted to the screen
	public void updateOutput() {
		m_screen.setValue(m_currValue);
		m_currValue += m_numStringSaved;
	}

	// takes most recent number from screen and adds to currNum
	// in turn concatenates screen values to make 1 "number"
	public void appendToOutput(String num) {

		// Prevents adding more than one dot on the output
		if (num.equals(".") && m_currValue.contains(".")) {
			return;
		}
		m_currValue += num;
	}

	// if no operator resets operator
	// if no prevValue calls calc w/appropriate operator, sets appropriate variables
	public void selectOperator(char newOperator) {
		// if no number is entered yet
		if (m_currValue.isEmpty()) {
			m_lastOp = newOperator;
			return;
		}
		if (!m_prevValue.isEmpty()) {
			calc();
		}
		m_lastOp = newOperator;
		m_prevValue = m_currValue;
		m_currValue = "";
	}

	// clears the screen
	// resets all variables
	public void clearAll() {
		m_screen.setValue(0);
		m_currValue = "";
		m_prevValue = "";
		m_numStringSaved = "";
		m_lastOp = 0;
	}

	// FIXME want to be sure this works with operators and with numbers
	// clears screen
	// doesn't reset all variables except for curr
	public void clearScreen() {
		m_screen.setValue(0);
		m_currValue = "";
	}

	// algorithms for all operations involving calculations
	// sets currValue, resets prevValue and operator
	public void calc() {

		// check that there is a currValue
		if (m_currValue.length() < 1 || m_prevValue.length() < 1) {
			return;
		}
		// cast number strings back to doubles
		double result = 0.0;
		double num1 = Double.parseDouble(m_prevValue);
		double num2 = Double.parseDouble(m_currValue);

		switch (m_lastOp) {
		case '*':
			result = num1 * num2;
			break;
		case '+':
			result = num1 + num2;
			break;
		case '-':
			result = num1 - num2;
			break;
		case '/':

			/*
			 * //FIXME come back to this exception - currently messing up dividing 
		/*	 */
		/*	try {
				if (Math.abs(num1 = 1 / num2) < Double.POSITIVE_INFINITY)
					throw new ArithmeticException("Not finite");
			} catch (ArithmeticException e) {
				System.out.print(e);
			}*/
			result = num1 / num2;
			break;

		default:
			break;
		}
		// cast result back to string
		m_currValue = String.valueOf(result);
		m_lastOp = 0;
		m_prevValue = "";

		// check if int or double
		updateIntegerNum();

		updateOutput();
	}

	// checks if trailing number is a zero
	// removes decimal if zero
	public void updateIntegerNum() {
		if (m_currValue.length() > 0) {
			try {
				String integerPart = m_currValue.split("\\.")[0];
				String decimalPart = m_currValue.split("\\.")[1];

				if (decimalPart.equals("0")) {
					m_currValue = integerPart;
				}
			} catch (Exception e1) { // FIXME need to add out of range error
				System.out.print("Out of range error");
			}
		}
	}

	// FIXME - not complete
	// function for "kids mode" skin
	// adds colors and labels to numbers and functions
	// changes the screen pointer to a hand
	// TODO - make button cursor a fun image
	// add sound to buttons
	public void setKidsModeSkin() // change to sestKidsSkin
	{
		 
		m_screen.addMouseListener(new MouseListener() {
			@Override
			public void mouseEntered(MouseEvent e) {
				m_screen.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				m_screen.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		Color aColor = new Color(255, 105, 180);
		setBackground(Color.cyan);
		//set number button colors and labels 
		m_Numbers[0].setBackground(Color.ORANGE); 
		//m_Numbers[0].setForeground(aColor);
		m_Numbers[0].setBorder(BorderFactory.createTitledBorder("Zero"));
		
		m_Numbers[1].setBackground(Color.YELLOW);
		m_Numbers[1].setBorder(BorderFactory.createTitledBorder("One"));
		//m_Numbers[1].setForeground(Color.cyan);

		m_Numbers[2].setBackground(Color.PINK);
		m_Numbers[2].setBorder(BorderFactory.createTitledBorder("Two"));
		//m_Numbers[2].setForeground(aColor);

		m_Numbers[3].setBackground(Color.YELLOW);
		m_Numbers[3].setBorder(BorderFactory.createTitledBorder("Three"));
		//m_Numbers[3].setForeground(Color.cyan);

		m_Numbers[4].setBackground(Color.PINK);
		m_Numbers[4].setBorder(BorderFactory.createTitledBorder("Four"));
		//m_Numbers[4].setForeground(aColor);

		m_Numbers[5].setBackground(Color.ORANGE);
		m_Numbers[5].setBorder(BorderFactory.createTitledBorder("Five"));
		//m_Numbers[5].setForeground(Color.cyan);

		m_Numbers[6].setBackground(Color.YELLOW);
		m_Numbers[6].setBorder(BorderFactory.createTitledBorder("Six"));
		//m_Numbers[0].setForeground(aColor);

		m_Numbers[7].setBorder(BorderFactory.createTitledBorder("Seven"));
		m_Numbers[7].setBackground(Color.YELLOW);
		//m_Numbers[7].setForeground(Color.cyan);

		m_Numbers[8].setBorder(BorderFactory.createTitledBorder("Eight"));
		m_Numbers[8].setBackground(Color.YELLOW);
		//m_Numbers[0].setForeground(Color.cyan);

		m_Numbers[9].setBorder(BorderFactory.createTitledBorder("Nine"));
		m_Numbers[9].setBackground(Color.YELLOW);
		//m_Numbers[9].setForeground(aColor);
		
		//set function button color and labels 
		m_FunctionButtons[0].setBorder(BorderFactory.createTitledBorder("Decimal"));
		m_FunctionButtons[0].setBackground(Color.YELLOW);
		//m_FunctionButtons[0].setForeground(aColor);
		
		m_FunctionButtons[1].setBorder(BorderFactory.createTitledBorder(""));
		m_FunctionButtons[1].setBackground(Color.ORANGE);
		//m_FunctionButtons[1].setForeground(Color.cyan);
		
		m_FunctionButtons[2].setBorder(BorderFactory.createTitledBorder(""));
		m_FunctionButtons[2].setBackground(Color.PINK);
		//m_FunctionButtons[2].setForeground(aColor);
		
		m_FunctionButtons[3].setBorder(BorderFactory.createTitledBorder(""));
		m_FunctionButtons[3].setBackground(Color.ORANGE);
		//m_FunctionButtons[3].setForeground(Color.cyan);
		
		m_FunctionButtons[4].setBorder(BorderFactory.createTitledBorder(""));
		m_FunctionButtons[4].setBackground(Color.YELLOW);
		//m_FunctionButtons[4].setForeground(aColor);
		
		m_FunctionButtons[5].setBorder(BorderFactory.createTitledBorder(""));
		m_FunctionButtons[5].setBackground(Color.PINK);
		//m_FunctionButtons[5].setForeground(Color.cyan);
		
		m_FunctionButtons[6].setBorder(BorderFactory.createTitledBorder(""));
		m_FunctionButtons[6].setBackground(Color.ORANGE);
		//m_FunctionButtons[6].setForeground(aColor);
		
		m_FunctionButtons[7].setBorder(BorderFactory.createTitledBorder(""));
		m_FunctionButtons[7].setBackground(Color.YELLOW);
		//m_FunctionButtons[7].setForeground(Color.cyan);
	
		//add audio 
		/*
		 * String soundName = "yourSound.wav"; AudioInputStream audioInputStream =
		 * AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile()); Clip
		 * clip = AudioSystem.getClip(); clip.open(audioInputStream); clip.start();
		 * //Color color=new Color(Color.cyan);
		 */
		/*
		 * custom images on buttons ImageIcon icon = new
		 * ImageIcon("pathOfImageHere.png"); JButton button = new JButton(icon);
		 * 
		 * button.setOpaque(false); button.setContentAreaFilled(false);
		 * button.setBorderPainted(false); button.setFocusPainted(false);
		 */
		
		// add cursor image - java premade, not original
		/*
		 * m_screen.addMouseMotionListener(new MouseMotionListener() {
		 * 
		 * @Override public void mouseMoved(MouseEvent e) { final int x = e.getX();
		 * final int y = e.getY(); // only display a hand if the cursor is over the
		 * items final Rectangle cellBounds = list.getCellBounds(0,
		 * list.getModel().getSize() - 1); if (cellBounds != null &&
		 * cellBounds.contains(x, y)) { list.setCursor(new Cursor(Cursor.HAND_CURSOR));
		 * } else { list.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); } }
		 * 
		 * @Override public void mouseDragged(MouseEvent e) { } });
		 */
	}

	public void setDefaultMode() {
		
		for (int i = 0; i < m_FunctionButtons.length; i++) {

			m_FunctionButtons[i].setBackground(defaultButtonColor);
			//m_Numbers[i].setBorder(null);

		}
		for (int i = 0; i < m_Numbers.length; i++) {

			m_Numbers[i].setBackground(defaultButtonColor);
			//m_Numbers[i].setBorder(null);

		}
		//reset background 
		setBackground(defaultBackground); 
	}
}
