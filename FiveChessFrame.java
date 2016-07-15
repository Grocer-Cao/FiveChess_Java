package org.liky.game.frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class FiveChessFrame extends JFrame implements MouseListener {

	// 获取屏幕分辨率信息
	int width = Toolkit.getDefaultToolkit().getScreenSize().width;
	int heigth = Toolkit.getDefaultToolkit().getScreenSize().height;

	// 用于缓存背景图片的BufferedImage变量
	BufferedImage bgImage = null;

	// 用于保存棋子的一个二维数组
	// 其中内容若为 0---无棋子; 1---黑子; 2---白子
	int[][] allChess = new int[19][19];

	// 标志位，用于判断当前落子是黑子还是白子，初始置为黑子
	int flag = 1;

	// 构造方法
	public FiveChessFrame() {
		// TODO Auto-generated constructor stub
		// 设置标题
		this.setTitle("五子棋");
		// 设置大小
		this.setSize(500, 500);
		// 设置窗体出现位置
		this.setLocation((width - 500) / 2, (heigth - 500) / 2);
		// 设置窗体大小不可改变
		this.setResizable(false);
		// 设置关闭方式
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 增加鼠标监听
		this.addMouseListener(this);

		// 显示窗体
		this.setVisible(true);
	}

	@Override
	public void paint(Graphics g) {
		// 读入背景图片
		try {
			bgImage = ImageIO.read(new File("E:/java/workspace/JSwing/background.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 显示背景图片
		g.drawImage(bgImage, 3, 28, this);

		// 游戏提示信息
		g.setFont(new Font("黑体", Font.BOLD, 30));

		if (flag == 1) {
			g.drawString("游戏信息：轮到黑方", 20, 66);
		}
		else if(flag == 2){
			g.drawString("游戏信息：轮到白方", 20, 66);
		}
		g.setFont(new Font("宋体", Font.PLAIN, 20));
		g.drawString("黑方时间：无限制", 30, 478);
		g.drawString("白方时间：无限制", 250, 478);

		// 绘制棋盘
		int i = 0;
		for (; i < 19; i++) {
			g.drawLine(13, 78 + i * 20, 373, 78 + i * 20);
			g.drawLine(13 + i * 20, 78, 13 + i * 20, 438);
		}
		g.fillOval(70, 135, 7, 7);
		g.fillOval(70, 255, 7, 7);
		g.fillOval(70, 375, 7, 7);
		g.fillOval(190, 135, 7, 7);
		g.fillOval(190, 255, 7, 7);
		g.fillOval(190, 375, 7, 7);
		g.fillOval(310, 135, 7, 7);
		g.fillOval(310, 255, 7, 7);
		g.fillOval(310, 375, 7, 7);

		// 绘制棋子
		for (int p = 0; p < 19; p++) {
			for (int q = 0; q < 19; q++) {
				if (allChess[p][q] == 1) {
					// 黑子
					g.setColor(Color.BLACK);
					g.fillOval(p * 20 + 6, q * 20 + 71, 14, 14);
				} else if (allChess[p][q] == 2) {
					// 白子
					g.setColor(Color.BLACK);
					g.fillOval(p * 20 + 6, q * 20 + 71, 14, 14);
					g.setColor(Color.WHITE);
					g.fillOval(p * 20 + 7, q * 20 + 72, 12, 12);
				}
			}
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		int x = e.getX();
		int y = e.getY();
		// System.out.println("当前坐标：" + x + " " + y);

		if (x >= 13 && x <= 373 && y >= 78 && y <= 438) {
			// 为了保证尽可能接近用户点击的坐标
			int m = (x - 3) / 20;
			int n = (y - 68) / 20;
			// System.out.println("在棋盘内：m="+m+" n="+n);

			// 双方交替下棋flag: 1---黑方下, 2---白方下
			if (allChess[m][n] == 0) {
				if (flag == 1) {
					allChess[m][n] = 1;
					flag = 2;
				} else if (flag == 2) {
					allChess[m][n] = 2;
					flag = 1;
				}

			}

			this.repaint();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
