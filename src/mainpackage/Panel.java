package mainpackage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import minesweeper.Minesweeper;
import snake.Snake;

public class Panel extends JPanel implements KeyListener, ActionListener {

	JFrame frame = new JFrame();
	static int choosevalue = 0;
	Timer timer = new Timer(50,this);
	public Panel() {
		this.setFocusable(true);
		this.addKeyListener(this);
		timer.start();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(new Color(241,200,97));
		
		g.setColor(Color.red);
		g.fillRect(100,100,200,50);
		g.fillRect(100,200,200,50);
		g.fillRect(100,300,200,50);
		g.fillRect(100,400,200,50);
		g.setColor(Color.white);
		g.drawString("A.扫雷", 185, 125);
		g.drawString("B.贪吃蛇", 175, 225);
		g.drawString("C.待续", 185, 325);
		g.drawString("D.待续", 185, 425);
		g.drawString("选择游戏模式", 160, 35);

	}
	//选择游戏
		public static void chooseModel() {

		}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyCode==KeyEvent.VK_A) {
			choosevalue = 1;
			}
		else if(keyCode == KeyEvent.VK_B) {
			choosevalue = 2;
		}
		else if(keyCode == KeyEvent.VK_C) {
			choosevalue = 3;
		}
		else if(keyCode == KeyEvent.VK_C) {
			choosevalue = 4;
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(choosevalue == 1) {
			frame.setVisible(false);
			new Minesweeper();
			timer.stop();
			return;
		}
		else if(choosevalue == 2) {
			JFrame frame=new JFrame();
			frame.setBounds(10, 10, 912, 732);
			frame.setResizable(false);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.add(new Snake());
			frame.setVisible(true);
			timer.stop();
			return;
		}
		timer.start();
	}
}
