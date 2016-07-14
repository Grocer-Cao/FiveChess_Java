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

	//获取屏幕分辨率信息
	int width = Toolkit.getDefaultToolkit().getScreenSize().width;
	int heigth = Toolkit.getDefaultToolkit().getScreenSize().height;
	
	BufferedImage bgImage = null;
	
	//构造方法
	public FiveChessFrame() {
		// TODO Auto-generated constructor stub
		//设置标题
		this.setTitle("五子棋");
		//设置大小
		this.setSize(500, 500);
		//设置窗体出现位置
		this.setLocation((width-500)/2, (heigth-500)/2);
		//设置窗体大小不可改变
		this.setResizable(false);
		//设置关闭方式
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//增加鼠标监听
		this.addMouseListener(this);
		
		
		//显示窗体
		this.setVisible(true);
	}
	
	
	
	@Override
	public void paint(Graphics g) {
		//读入背景图片
		try {
			bgImage = ImageIO.read(new File("E:/java/workspace/JSwing/background.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//显示背景图片
		g.drawImage(bgImage, 3, 28, this);
		
		//游戏提示信息
		g.setFont(new Font("黑体",Font.BOLD,30));
		g.drawString("游戏信息：轮到黑方", 20, 66);
		g.setFont(new Font("宋体", Font.PLAIN, 20));
		g.drawString("黑方时间：无限制", 30, 478);
		g.drawString("白方时间：无限制", 250, 478);
		
		//绘制棋盘
		int i = 0;
		for(;i<19;i++){
			g.drawLine(13, 78+i*20, 373, 78+i*20);
			g.drawLine(13+i*20, 78, 13+i*20, 438);
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
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("当前坐标："+e.getX()+" "+e.getY());

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
