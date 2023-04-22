package JavaCalc;
//Emily Robey
//04/14/23
//CST 238 Lab 2

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

//The CalcMenu Class currently  populates 1 menu option - Change Mode
//ChangeMode - has Kids and Default modes TODO add scientific and programmer modes
//MenuClass currently contains SkinListener (subclass of ActionListener)
public class CalcMenu extends JMenuBar {

	private static final long serialVersionUID = 1L;
	protected CalcIF calc;
	protected SkinListener skinEvent;

	protected JMenu menu;
	protected JMenuItem menuItem;

	// MenuClass default ctor
	// instantiates 3 menu objects - File, Font, Style
	// adds menu items with labels and action listeners to appropriate menus
	CalcMenu(CalcIF myCalc) {
		calc = myCalc;
		// create menus
		menu = new JMenu("Change Mode");
		// add menus
		add(menu);
		// create menu items and add to menu
		// Change skin/mode
		menuItem = new JMenuItem("Kids Mode");
		menuItem.addActionListener(new SkinListener("Kids Mode"));
		menu.add(menuItem);

		menuItem = new JMenuItem("Default Mode");
		menuItem.addActionListener(new SkinListener("Default"));
		menu.add(menuItem);

	}

	// skin action listener
	// sets appropriate skin, or mode 
	protected class SkinListener implements ActionListener {
		// data members
		String skinName;

		// default ctor
		SkinListener(String e) {
			skinName = e;
		}

		// action to perform
		public void actionPerformed(ActionEvent e) {
			if (skinName == "Kids Mode") {
				calc.setKidsModeSkin();
			} else if (skinName == "Default") {
				calc.setDefaultMode();
			}

		}
	}

}
