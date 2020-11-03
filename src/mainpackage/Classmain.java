package mainpackage;

import javax.swing.JFrame;
import mainpackage.Panel;

public class Classmain {
	static int choosevalue = 0 ;
	public static void main(String[] args) {
		JFrame frame = new JFrame(); 
		frame.setBounds(10, 10, 400, 600);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new Panel());
		frame.setVisible(true);
	}
}