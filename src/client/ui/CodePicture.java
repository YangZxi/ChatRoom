package client.ui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class CodePicture {

//	private static final char[] chars = ("0123456789abcdefghjklmnopqrstuvwxyzABCDEFGHIJKHLMNOPQRSTUVWXYZ").toCharArray();
	private static final char[] code = new char[4];
	private static final int SIZE = 4;
	private static final int WIDTH = 160;
	private static final int HEIGHT = 55;
	private static final int FONT_SIZE = 30;
	
	// 生成随机验证码图片
	public static Object[] createCodeImages() {
		createCode();
		// 存储产生的随机数
		StringBuffer sb = new StringBuffer();
		// 存储验证码图片
		BufferedImage img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_BGR);
		// 获得画笔
		Graphics g = img.getGraphics();
		g.setColor(new Color(51, 51, 51));	// 背景颜色
		g.fillRect(0, 0, WIDTH, HEIGHT);	// 填充背景
		// 画随机数
		Random r = new Random();
		for (int i = 0;i < code.length;i++) {
			// 随机字符的索引
//			int n = r.nextInt(code.length);
			// 设置画笔颜色
			g.setColor(getRandomColor());
			// 设置字体
			g.setFont(new Font("黑体", Font.BOLD, FONT_SIZE));
			g.drawString(code[i]+"", WIDTH/SIZE*i+10, 40);
			sb.append(code[i]);
		}
		// 绘制干扰点
		for (int i = 0;i < 5;i++) {
			g.setColor(getRandomColor());
			g.drawLine(r.nextInt(WIDTH), r.nextInt(HEIGHT), r.nextInt(WIDTH), r.nextInt(HEIGHT));
		}
		
		return new Object[] {sb.toString(),img};
	}
	
	// 随机颜色
	public static Color getRandomColor() {
		Random r = new Random();
		Color color = new Color(r.nextInt(256),r.nextInt(256),r.nextInt(256));
		return color;
	}
	
	// 随机生成验证码
	public static void createCode() {
		char singleCode = 0;
		for (int i = 0; i < code.length; i++) {
			int noSuiJi = (int) (Math.random() * 3);
			if (noSuiJi == 0) {
				singleCode = (char) (Math.random() * 9 + 48);			//ASCII随机生成0-9
			} else if (noSuiJi == 1) {
				singleCode = (char) (Math.random() * 25 + 65);			//ASCII随机生成A-Z
			} else {
				singleCode = (char) (Math.random() * 25 + 97);			//ASCII随机生成A-Z
			}
			code[i] = singleCode;        // 将随机的字符存在数组里
		}
//		String noEnd = new String(code);        // 将数组转为字符串
//		return noEnd;
	}
	
//	public static void main(String[] args) {
//		JFrame jf = new JFrame("验证码");
//		JPanel jp = new JPanel();
//		JLabel jl = new JLabel("");
//		Object[] object= createCodeImages();
//		String codeEnd = object[0].toString();
//		jl.setIcon(new ImageIcon((BufferedImage) object[1]));
//		jf.setSize(350, 150);
//		jp.add(jl);
//		jf.add(jp);
//		jf.setLocationRelativeTo(null);
//		jf.setVisible(true);
//	}
	
}
