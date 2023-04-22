//Emily Robey
//04/14/23
//CST 238 Lab 2

package JavaCalc;
import javax.swing.*;

//instantiates a JFrame that - currently static
//displays a CalcIF calculator
//with a calcMenu 
public class JavaCalc {
	
	static Boolean start = true;

	public static void main(String[] args) {
		JFrame myFrame = new JFrame();
		CalcIF calc = new CalcIF();
		myFrame.setSize(400, 650);
		myFrame.setTitle("Calculator");
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.add(calc);
		myFrame.pack();
		myFrame.setResizable(false);
		myFrame.setJMenuBar(new CalcMenu(calc)); //set menu to my menu class
		myFrame.setVisible(true);
		
	}

}