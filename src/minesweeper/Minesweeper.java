package minesweeper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Minesweeper implements ActionListener {
	JFrame frame = new JFrame();
	ImageIcon headerIcon = new ImageIcon("res/pic/minesweeper/header.png");
	ImageIcon guessIcon = new ImageIcon("res/pic/minesweeper/guess.png");
	ImageIcon boomIcon = new ImageIcon("res/pic/minesweeper/bomb.png");
	ImageIcon failIcon = new ImageIcon("res/pic/minesweeper/fail.png");
	ImageIcon winIcon = new ImageIcon("res/pic/minesweeper/win.png");
	ImageIcon win_flag =new ImageIcon("res/pic/minesweeper/win_flag.png");
	

	//数据结构
	

	int mine = 5 ;//雷的总数
	int level = 0 ;
	int ROW = 20;
	int COL = 20;
	int [] [] data = new int[ROW][COL];
	JButton[][] btns = new JButton[ROW][COL];
	int windowx = ROW * 30;
	int windowy = COL * 30 + 100;
	int MINECODE = -1;//表示雷
	int unopened = ROW*COL ;
	int opened = 0 ;
	int times = 0 ;//用时
	
	JButton headerBtn = new JButton(headerIcon);
	JLabel stayOpen = new JLabel("待开："+unopened);
	JLabel alreadyOpen = new JLabel("已开："+opened);
	JLabel useTime = new JLabel("用时："+times+"s");
	JLabel gameLevel = new JLabel("关卡："+(level+1));
	Timer timer = new Timer(1000,this);
	
	
	public Minesweeper() {
		frame.setSize(windowx, windowy);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		setHeader();
		
		addmine();
		
		setButtons();
		
		timer.start();
		frame.setVisible(true);
	}

	private void addmine() {
		Random rand = new Random();
		for(int i = 0;i < mine;) {
			int r = rand.nextInt(ROW);
			int c = rand.nextInt(COL);
			if(data[r][c]!=MINECODE) {
				data[r][c] = MINECODE ;
				i++;
			}
		}
		
		//计算周边雷数量
		for(int i = 0;i < ROW;i++) {
			for(int j = 0;j < COL;j++) {
				if(data[i][j]==MINECODE) continue;
				int tempCount = 0;
				if(i > 0 && j > 0 && data[i-1][j-1] == MINECODE) tempCount++;
				if(i > 0 && data[i-1][j] == MINECODE) tempCount++;
				if(i > 0 && j < 19 && data[i-1][j+1] == MINECODE) tempCount++;
				if(j > 0 && data[i][j-1] == MINECODE) tempCount++;
				if(j < 19 && data[i][j+1] == MINECODE) tempCount++;
				if(i < 19 && j > 0 && data[i+1][j-1] == MINECODE) tempCount++;
				if(i < 19 && data[i+1][j] == MINECODE) tempCount++;
				if(i < 19 && j < 19 && data[i+1][j+1] == MINECODE) tempCount++;
				data[i][j] = tempCount;
			}
		}
		
	}

	private void setButtons() {
		Container con = new Container();
		con.setLayout(new GridLayout(ROW,COL));
		
		for(int i = 0;i < ROW ;i++) {
			for(int j = 0; j < COL;j++) {
				JButton btn = new JButton(guessIcon);
				btn.setOpaque(true);
				btn.setBackground(new Color(244,183,113));
				btn.addActionListener(this);
				btn.setMargin(new Insets(0,0,0,0));
				con.add(btn);
				btns[i][j] = btn ;
			}
		}
		frame.add(con,BorderLayout.CENTER);
	}

	private void setHeader() {
		JPanel panel = new JPanel(new GridBagLayout());
		
		GridBagConstraints c1 = new GridBagConstraints(0,0,4,1,1.0,1.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(0,0,0,0),0,0);
		panel.add(headerBtn,c1);
		headerBtn.addActionListener(this);
		
		stayOpen.setOpaque(true);
		stayOpen.setBackground(Color.white);
		stayOpen.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		
		alreadyOpen.setOpaque(true);
		alreadyOpen.setBackground(Color.white);
		alreadyOpen.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		
		useTime.setOpaque(true);
		useTime.setBackground(Color.white);
		useTime.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		
		gameLevel.setOpaque(true);
		gameLevel.setBackground(Color.white);
		gameLevel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		
		headerBtn.setOpaque(true);
		headerBtn.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		headerBtn.setBackground(Color.white);
		
		GridBagConstraints c2 = new GridBagConstraints(0,1,1,1,1.0,1.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(0,0,0,0),0,0);
		panel.add(stayOpen,c2);
		GridBagConstraints c3 = new GridBagConstraints(1,1,1,1,1.0,1.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(0,0,0,0),0,0);
		panel.add(alreadyOpen,c3);
		GridBagConstraints c4 = new GridBagConstraints(2,1,1,1,1.0,1.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(0,0,0,0),0,0);
		panel.add(useTime,c4);
		GridBagConstraints c5 = new GridBagConstraints(3,1,1,1,1.0,1.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(0,0,0,0),0,0);
		panel.add(gameLevel,c5);
		
		frame.add(panel,BorderLayout.NORTH);
		
		
	}

/*	public static void main(String[] args) {
		new Minesweeper();

	}*/

	public void actionPerformed(ActionEvent e) {
		if(e.getSource()instanceof Timer) {
			times++;
			useTime.setText("用时："+times+"s");
			timer.start();
			return;
		}
		
		JButton btn = (JButton)e.getSource();
		
		if(btn.equals(headerBtn)) {
			restart();
			return;
		}
		for(int i = 0;i < ROW;i++) {
			for(int j = 0;j < COL ;j++) {
				if(btn.equals(btns[i][j])) {
					if(data[i][j] == MINECODE) {
						lose();
					}
					else {
					openCell(i,j);
					checkWin();
					
					}
					return;
					
				}
			}
		}
	}
//重新开始：数据清零，按钮恢复，重启时钟
	private void restart() {
		for(int i = 0;i < ROW;i++) {
			for(int j = 0;j < COL;j++) {
				data[i][j] = 0;
				btns[i][j].setBackground(new Color(244,183,113));
				btns[i][j].setEnabled(true);
				btns[i][j].setText("");
				btns[i][j].setIcon(guessIcon);
			}
		}
		//状态栏恢复
		unopened = ROW * COL;
		opened = 0;
		times = 0 ;
		level = 0 ;
		stayOpen.setText("待开："+unopened);
		alreadyOpen.setText("已开："+opened);
		useTime.setText("用时："+times+"s");
		gameLevel.setText("关卡："+(level+1));
		
		//重新启动
		headerBtn.setIcon(headerIcon);
		mine = 5;
		addmine();
		timer.start();
		
	}

//胜利
	private void checkWin() {
		int count = 0;
		for(int i = 0;i < ROW;i++) {
			for(int j = 0;j < COL;j++) {
				if(btns[i][j].isEnabled())count++;
			}
		}
		if(count == mine) {
			timer.stop();
			for(int i = 0;i < ROW;i++) {
				for(int j = 0;j < COL;j++) {
					if(btns[i][j].isEnabled()) {
						btns[i][j].setIcon(win_flag);
					}
				}
			}
			headerBtn.setIcon(winIcon);
			JOptionPane.showMessageDialog(frame, "你赢啦，Yeah！\n点击确定按钮进入下一关！\n你也可以点击上方按钮重新开始！","You Win!",JOptionPane.PLAIN_MESSAGE);
			nextLevel();
		}
	}
private void nextLevel() {
	level++;
	for(int i = 0;i < ROW;i++) {
		for(int j = 0;j < COL;j++) {
			data[i][j] = 0;
			btns[i][j].setBackground(new Color(244,183,113));
			btns[i][j].setEnabled(true);
			btns[i][j].setText("");
			btns[i][j].setIcon(guessIcon);
			
		}
		
	}
	//状态栏恢复
	unopened = ROW * COL;
	opened = 0;
	times = 0 ;
	stayOpen.setText("待开："+unopened);
	alreadyOpen.setText("已开："+opened);
	useTime.setText("用时："+times+"s");
	gameLevel.setText("关卡："+(level+1));
	
	//重新启动
	headerBtn.setIcon(headerIcon);
	Random rand = new Random();
	int temp = rand.nextInt(4)+2;
	mine = mine + temp;//每关随机增加二到五颗雷
	addmine();
	timer.start();
	
}

//失败
	private void lose() {
		timer.stop();
		headerBtn.setIcon(failIcon);
		for(int i = 0;i < ROW;i++) {
			for(int j = 0;j < COL ;j++) {
				if(btns[i][j].isEnabled()) {
					JButton btn = btns[i][j];
					if(data[i][j] == MINECODE) {
						btn.setEnabled(false);
						btn.setIcon(boomIcon);
						btn.setDisabledIcon(boomIcon);
						
					}
					else {
						btn.setIcon(null);
						btn.setEnabled(false);
						btn.setOpaque(true);
						btn.setBackground(new Color(255,147,88));
						btn.setText(data[i][j]+"");
					}
				}
			}
		}
		JOptionPane.showMessageDialog(frame, "游戏失败了！\n点击确定按钮重新开始游戏！","暴雷啦！",JOptionPane.PLAIN_MESSAGE);
		restart();
	}

	private void openCell(int i, int j) {
		JButton btn = btns[i][j];
		if(!btn.isEnabled()) return;
		
		btn.setIcon(null);
		btn.setEnabled(false);
		btn.setOpaque(true);
		btn.setBackground(Color.green);
		btn.setText(data[i][j]+"");
		addOpenCount();
		
		if(data[i][j] == 0 ) {
			if(i > 0 && j > 0 && data[i-1][j-1] == 0) openCell(i-1,j-1);
			if(i > 0 && data[i-1][j] == 0) openCell(i-1,j);
			if(i > 0 && j < 19 && data[i-1][j+1] == 0) openCell(i-1,j+1);
			if(j > 0 && data[i][j-1] == 0) openCell(i,j-1);
			if(j < 19 && data[i][j+1] == 0) openCell(i,j+1);
			if(i < 19 && j > 0 && data[i+1][j-1] == 0) openCell(i+1,j-1);
			if(i < 19 && data[i+1][j] == 0) openCell(i+1,j);
			if(i < 19 && j < 19 && data[i+1][j+1] == 0) openCell(i+1,j+1);
		}
	}

	private void addOpenCount() {
		opened++;
		unopened--;
		stayOpen.setText("待开："+unopened);
		alreadyOpen.setText("已开："+opened);
		
	}
	
}
