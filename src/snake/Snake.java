package snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Snake extends JPanel implements KeyListener, ActionListener{
	ImageIcon title=new ImageIcon("res/pic/snake/title.png");
	ImageIcon body=new ImageIcon("res/pic/snake/body.png");
	ImageIcon up=new ImageIcon("res/pic/snake/up.png");
	ImageIcon down=new ImageIcon("res/pic/snake/down.png");
	ImageIcon right=new ImageIcon("res/pic/snake/right.png");
	ImageIcon left=new ImageIcon("res/pic/snake/left.png");
	ImageIcon food=new ImageIcon("res/pic/snake/food.png");
	
	int len= 3 ;
	int score = 0 ;
	int[] snakex=new int[750];
	int[] snakey=new int[750];
	String fx="R";//方向R右L左U上D下
	
	boolean isStarted = false;
	boolean isFailed = false;
	int speed = 200 ;
	Timer timer = new Timer(speed,this);
	int Foodx;
	int Foody;
	Random rand = new Random();
	
	public Snake() {
		initSnake();
		this.setFocusable(true);
		this.addKeyListener(this);
		timer.start();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(Color.WHITE);
		title.paintIcon(this, g, 25, 11);
		
		g.fillRect(25,75,850,600);
		g.setColor(Color.WHITE);
		g.drawString("长度："+len, 750, 25);
		g.drawString("分数："+score,750,40);
		g.drawString("速度："+(200-speed+2)*5+"光年/小时",750,55);


		switch (fx) {
			case "R":
				right.paintIcon(this, g, snakex[0], snakey[0]);
				break;
			case "L":
				left.paintIcon(this, g, snakex[0], snakey[0]);
				break;
			case "D":
				down.paintIcon(this, g, snakex[0], snakey[0]);
				break;
			case "U":
				up.paintIcon(this, g, snakex[0], snakey[0]);
				break;
		}
		for(int i=1;i<len;i++) {
			body.paintIcon(this, g, snakex[i], snakey[i]);
		}
		 food.paintIcon(this, g, Foodx, Foody);
		if(!isStarted){
			g.setColor(Color.WHITE);
			g.setFont(new Font("stxingka",Font.BOLD,40));
			g.drawString("按下空格键开始游戏!", 300, 400);
		}
		if(isFailed){
			g.setColor(Color.WHITE);
			g.setFont(new Font("stxingka",Font.BOLD,40));
			g.drawString("失败了，别灰心。按下空格键重新开始游戏!", 50, 400);
		}

	}
	
	public void initSnake() {
		len = 3 ;
		score = 0 ;
		speed = 200 ;
		snakex[0] = 100;
		snakey[0] = 100;
		snakex[1] = 75;
		snakey[1] = 100;
		snakex[2] = 50;
		snakey[2] = 100;
		Foodx=25+25*rand.nextInt(34);
		Foody=75+25*rand.nextInt(23);
		fx ="R";
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		int keyCode = e.getKeyCode();
		if(keyCode==KeyEvent.VK_SPACE) {
			if(isFailed) {
				isFailed = false;
				initSnake();
			}
			else {
				isStarted = !isStarted;
			}
			repaint();
		}
		else if(keyCode==KeyEvent.VK_LEFT&&snakey[0]!=snakey[1]) {
			fx = "L";
		}
		else if(keyCode==KeyEvent.VK_RIGHT&&snakey[0]!=snakey[1]) {
			fx = "R";
		}
		else if(keyCode==KeyEvent.VK_UP&&snakex[0]!=snakex[1]) {
			fx = "U";
		}
		else if(keyCode==KeyEvent.VK_DOWN&&snakex[0]!=snakex[1]) {
			fx = "D";
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(isStarted && !isFailed) {
			for(int i=len-1;i>0;i--) {
				snakex[i]=snakex[i-1];
				snakey[i]=snakey[i-1];
			}
			switch (fx) {
				case "R":
					snakex[0] = snakex[0] + 25;
					if (snakex[0] > 850) {
						isFailed = true;
						timer.stop();
						speed = 200;
						timer = new Timer(speed, this);
					}
					break;
				case "L":
					snakex[0] = snakex[0] - 25;
					if (snakex[0] < 25) {
						isFailed = true;
						timer.stop();
						speed = 200;
						timer = new Timer(speed, this);
					}
					break;
				case "U":
					snakey[0] = snakey[0] - 25;
					if (snakey[0] < 75) {
						isFailed = true;
						timer.stop();
						speed = 200;
						timer = new Timer(speed, this);
					}
					break;
				case "D":
					snakey[0] = snakey[0] + 25;
					if (snakey[0] > 650) {
						isFailed = true;
						timer.stop();
						speed = 200;
						timer = new Timer(speed, this);
					}
					break;
			}
			if (snakex[0]==Foodx&&snakey[0]==Foody) {
				len++;
				timer.stop();
				speed=speed - 2;
				timer = new Timer(speed,this);

				score = score + 5;
				Foodx=25+25*rand.nextInt(34);
				Foody=75+25*rand.nextInt(23);
				for(int i=len-1;i>0;i--) {
					if(Foodx==snakex[i]&&Foody==snakey[i]) {
						Foodx=25+25*rand.nextInt(34);
						Foody=75+25*rand.nextInt(23);
					}
				}
			}
			for(int i=1;i<len;i++) {
				if(snakex[i]==snakex[0]&&snakey[i]==snakey[0]) {
					isFailed=true;
					timer.stop();
					speed = 200 ;
					timer = new Timer(speed,this);
				}
			}
			repaint();
		}
		timer.start();
	}

}