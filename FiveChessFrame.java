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
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

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

	// 标志位，用于判断是否重新开始
	int restart = 1;

	// 标志位，用于判断是否已经落满子
	int count = 0;

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
		if (restart == 1) {
			// 初始化数组
			allChess = getEmptyIntArray(allChess);
			count = 0;

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
			} else if (flag == 2) {
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

			restart = 0;
		} else {
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

	}

	private int[][] getEmptyIntArray(int[][] intarray) {
		// TODO Auto-generated method stub
		for (int i = 0; i < intarray.length; i++) {
			for (int j = 0; j < intarray.length; j++) {
				intarray[i][j] = 0;
			}
		}
		return intarray;
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

					this.repaint();

					// 判断游戏是否结束
					if (this.check(m, n)) {
						JOptionPane.showMessageDialog(this, "游戏结束:黑方胜利！");
						// 游戏重新开始
						restart = 1;
						this.repaint();
						return;
					} else
						flag = 2;

				} else if (flag == 2) {
					allChess[m][n] = 2;

					this.repaint();

					// 判断游戏是否结束
					if (this.check(m, n)) {
						System.out.println("游戏结束");
						JOptionPane.showMessageDialog(this, "游戏结束:白方胜利！");
						// 游戏重新开始
						restart = 1;
						this.repaint();
						return;
					} else{
						flag = 1;
					}
				}
				//判断是否棋盘已经落满子
				if (++count == 19*19) {
					JOptionPane.showMessageDialog(this, "双方平局");
					restart = 1;
					this.repaint();
				}

			}

			// this.repaint();
		}
	}

	private boolean check(int m, int n) {
		// TODO Auto-generated method stub
		int M = 1, N = 1, L = 1, R = 1;
		int tp_m = m, tp_n = n;

		// 判断落子左方有多少个相同颜色的子
		while (tp_m > 0 && allChess[tp_m - 1][n] == flag) {
			M++;
			tp_m--;

			if (M == 5)
				return true;

		}
		tp_m = m;

		// 判断落子右方有多少个相同颜色的子
		while (tp_m < 18 && allChess[tp_m + 1][n] == flag) {
			M++;
			tp_m++;

			if (M == 5)
				return true;

		}
		tp_m = m;

		// 判断落子上方有多少个相同颜色的子
		while (tp_n > 0 && allChess[m][tp_n - 1] == flag) {
			N++;
			tp_n--;

			if (N == 5)
				return true;

		}
		tp_n = n;

		// 判断落子下方有多少个相同颜色的子
		while (tp_n < 18 && allChess[m][tp_n + 1] == flag) {
			N++;
			tp_n++;

			if (N == 5)
				return true;

		}
		tp_n = n;

		// 判断落子左上方有多少个相同颜色的子
		while (tp_m > 0 && tp_n > 0 && allChess[tp_m - 1][tp_n - 1] == flag) {
			L++;
			tp_m--;
			tp_n--;

			if (L == 5)
				return true;

		}
		tp_m = m;
		tp_n = n;

		// 判断落子右下方有多少个相同颜色的子
		while (tp_m < 18 && tp_n < 18 && allChess[tp_m + 1][tp_n + 1] == flag) {
			L++;
			tp_m++;
			tp_n++;

			if (L == 5)
				return true;

		}
		tp_m = m;
		tp_n = n;

		// 判断落子左下方有多少个相同颜色的子
		while (tp_m > 0 && tp_n < 18 && allChess[tp_m - 1][tp_n + 1] == flag) {
			R++;
			tp_m--;
			tp_n++;

			if (R == 5)
				return true;

		}
		tp_m = m;
		tp_n = n;

		// 判断落子右上方有多少个相同颜色的子
		while (tp_m < 18 && tp_n > 0 && allChess[tp_m + 1][tp_n - 1] == flag) {
			R++;
			tp_m++;
			tp_n--;

			if (R == 5)
				return true;

		}
		tp_m = m;
		tp_n = n;

		return false;
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
