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

public class FiveChessFrame extends JFrame implements MouseListener, Runnable {
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

	// 标志位，用于判断棋盘上已有棋子数
	int count = 0;

	// 用户设置的时间,单位 秒
	int maxTime = 0;
	int blackTime = 0;
	int whiteTime = 0;

	// 创建一个新的线程用于倒计时
	Thread td = new Thread(this);

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
		// 读入背景图片
		try {
			bgImage = ImageIO.read(new File("E:/java/workspace/JSwing/background.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 挂起线程
		td.start();
		td.suspend();
		// 显示窗体
		this.setVisible(true);
		this.repaint();
				
	}

	@Override
	public void paint(Graphics g) {
		

		// 双缓冲技术，防止屏幕闪烁
		/**
		 * 工作原理： 先在内存空间中创建一个BufferedImage的对象，将所有需要绘制的内容绘制于这个BufferedImage中，
		 * 此时并不会再屏幕上有任何的显示，这样就可以防止由于显示过程中多次绘制导致的屏幕闪烁问题。最后通过调用
		 * 该窗口的Graphics对象g，来一次性显示所有内容： g.drawImage(bi, 0, 0, this); 从而保证屏幕不闪烁。
		 * Graphics这个对象相当于窗口中的画笔，通过它来在窗口中绘制文字图案等。
		 */
		BufferedImage bi = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
		Graphics g2 = bi.createGraphics();

		// 显示背景图片
		g2.setColor(Color.BLACK);
		g2.drawImage(bgImage, 3, 28, this);

		// 游戏提示信息
		g2.setFont(new Font("黑体", Font.BOLD, 30));
		if (flag == 1) {
			g2.drawString("游戏信息：轮到黑方", 20, 66);
		} else if (flag == 2) {
			g2.drawString("游戏信息：轮到白方", 20, 66);
		}

		g2.setFont(new Font("宋体", Font.PLAIN, 17));
		if (maxTime == 0) {
			g2.drawString("黑方剩余时间：无限制", 30, 478);
			g2.drawString("白方剩余时间：无限制", 250, 478);
		} else {
			g2.drawString("黑方剩余时间：" + blackTime / 3600 + ":" + (blackTime / 60 - blackTime / 3600 * 60) + ":"
					+ blackTime % 60, 30, 478);
			g2.drawString("白方剩余时间：" + whiteTime / 3600 + ":" + (whiteTime / 60 - whiteTime / 3600 * 60) + ":"
					+ whiteTime % 60, 250, 478);
		}
		// 绘制棋盘
		int i = 0;
		for (; i < 19; i++) {
			g2.drawLine(13, 78 + i * 20, 373, 78 + i * 20);
			g2.drawLine(13 + i * 20, 78, 13 + i * 20, 438);
		}
		g2.fillOval(70, 135, 7, 7);
		g2.fillOval(70, 255, 7, 7);
		g2.fillOval(70, 375, 7, 7);
		g2.fillOval(190, 135, 7, 7);
		g2.fillOval(190, 255, 7, 7);
		g2.fillOval(190, 375, 7, 7);
		g2.fillOval(310, 135, 7, 7);
		g2.fillOval(310, 255, 7, 7);
		g2.fillOval(310, 375, 7, 7);

		// 绘制棋子
		for (int p = 0; p < 19; p++) {
			for (int q = 0; q < 19; q++) {
				if (allChess[p][q] == 1) {
					// 黑子
					g2.setColor(Color.BLACK);
					g2.fillOval(p * 20 + 6, q * 20 + 71, 14, 14);
				} else if (allChess[p][q] == 2) {
					// 白子
					g2.setColor(Color.BLACK);
					g2.fillOval(p * 20 + 6, q * 20 + 71, 14, 14);
					g2.setColor(Color.WHITE);
					g2.fillOval(p * 20 + 7, q * 20 + 72, 12, 12);
				}
			}
		}

		g.drawImage(bi, 0, 0, this);

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
		// 鼠标点击了“棋盘”
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
						this.restart();
						return;
					} else
						flag = 2;

				} else if (flag == 2) {
					allChess[m][n] = 2;

					this.repaint();

					// 判断游戏是否结束
					if (this.check(m, n)) {
						JOptionPane.showMessageDialog(this, "游戏结束:白方胜利！");
						// 游戏重新开始
						this.restart();
						return;
					} else {
						flag = 1;
					}
				}
				// 判断是否棋盘已经落满子
				if (++count == 19 * 19) {
					JOptionPane.showMessageDialog(this, "双方平局");
					// 游戏重新开始
					this.restart();
				}

			}
		}
		// 鼠标点击了“开始游戏”
		else if (x >= 403 && x <= 473 && y >= 79 && y <= 108) {
			// System.out.println("开始游戏");
			if (JOptionPane.showConfirmDialog(this, "确定要重新开始吗？") == 0) {
				// 游戏重新开始
				this.restart();
			}
		}
		// 鼠标点击了“游戏设置”
		else if (x >= 403 && x <= 473 && y >= 129 && y <= 158) {
			// System.out.println("游戏设置");
			String userTime = JOptionPane.showInputDialog("请输入需要设置的时间，单位分钟，可输入范围0~100，0表示无限制");

			try {
				int tempTime = Integer.parseInt(userTime) * 60;
				if (tempTime < 0 || tempTime > 6000) {
					JOptionPane.showMessageDialog(this, "请输入正确范围 0~100");
				} else if (tempTime == 0) {
					if (JOptionPane.showConfirmDialog(this, "设置完成，是否重新开始游戏") == 0) {
						maxTime = tempTime;
						td.suspend();
						restart();
					}
				} else {
					if (JOptionPane.showConfirmDialog(this, "设置完成，是否重新开始游戏") == 0) {
						maxTime = tempTime;
						//恢复线程
						td.resume();
						restart();
					}
				}
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(this, "请输入正确的格式");
			}

		}
		// 鼠标点击了“游戏说明”
		else if (x >= 403 && x <= 473 && y >= 179 && y <= 208) {
			// System.out.println("游戏说明");
			JOptionPane.showMessageDialog(this, "开局先猜子，（一方拿子一方进行猜单双），猜对的一方执黑子，\n" + "黑先、白后，从天元开始相互顺序落子；第二局换先。最先在\n"
					+ "棋盘横向、竖向、斜向形成连续的相同色五个棋子的一方为胜。");
		}
		// 鼠标点击了“认输”
		else if (x >= 403 && x <= 473 && y >= 280 && y <= 308) {
			// System.out.println("认输");
			if (JOptionPane.showConfirmDialog(this, "确定要认输吗？") == 0) {
				JOptionPane.showMessageDialog(this, "游戏结束:" + (flag == 1 ? "白子" : "黑子") + "胜利！");
				// 游戏重新开始
				this.restart();
			}
		}
		// 鼠标点击了“关于”
		else if (x >= 403 && x <= 473 && y >= 330 && y <= 358) {
			// System.out.println("关于");
			JOptionPane.showMessageDialog(this, "五子棋小游戏Java版\n版本1.0");
		}
		// 鼠标点击了“退出”
		else if (x >= 403 && x <= 473 && y >= 380 && y <= 408) {
			//System.out.println("退出");
			if (JOptionPane.showConfirmDialog(this, "确定要退出吗？") == 0) {
				// 关闭游戏
				System.exit(0);
			}			
		}
	}

	private void restart() {
		// TODO Auto-generated method stub
		flag = 1;
		allChess = getEmptyIntArray(allChess);
		count = 0;
		whiteTime = blackTime = maxTime;
		this.repaint();
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

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (maxTime>0) {
			if(flag == 1){
				if(blackTime>0) {
					blackTime--;
				}else {
					JOptionPane.showMessageDialog(this, "黑子时间已到，游戏结束：白子胜利！");
					System.out.println("黑子时间已到，游戏结束：白子胜利！");
					this.restart();
				}
			}
			else{
				if(whiteTime>0) {
					whiteTime--;
				}else {
					JOptionPane.showMessageDialog(this, "白子时间已到，游戏结束：黑子胜利！");
					System.out.println("白子时间已到，游戏结束：黑子胜利！");
					this.restart();
				}
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.repaint();
		}
	}

}
