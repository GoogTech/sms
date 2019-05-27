/**  
 * GitHub address: https://yubuntu0109.github.io/
 * @Title: CreateVerifiCodeImage.java   
 * @Package pers.huangyuhui.sms.servlet   
 * @Description: 验证码
 * @author: Huang Yuhui     
 * @date: May 6, 2019 7:01:00 PM   
 * @version 1.0
 *
 */
package pers.huangyuhui.sms.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @ClassName: CreateVerifiCodeImage
 * @Description: 绘制验证码图片
 * @author: HuangYuhui
 * @date: May 6, 2019 7:01:00 PM
 * 
 */
public class CreateVerifiCodeImage {

	private static int WIDTH = 85;
	private static int HEIGHT = 29;
	private static int FONT_SIZE = 18;
	private static char[] verifiCode;
	private static BufferedImage verifiCodeImage;

	/**
	 * @Title: createImage
	 * @Description: 获取验证码图片
	 * @return: void
	 */
	public static BufferedImage getVerifiCodeImage() {
		verifiCodeImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_BGR);// create a image
		Graphics graphics = verifiCodeImage.getGraphics();

		verifiCode = generateCheckCode();
		drawBackground(graphics);
		drawRands(graphics, verifiCode);

		graphics.dispose();

		return verifiCodeImage;
	}

	/**
	 * @Title: getVerifiCode
	 * @Description: 获取验证码
	 * @return: char[]
	 */
	public static char[] getVerifiCode() {
		return verifiCode;
	}

	/**
	 * @Title: generateCheckCode
	 * @Description: 随机生成验证码
	 * @return: char[]
	 */
	private static char[] generateCheckCode() {
		String chars = "0123456789abcdefghijklmnopqrstuvwxyz" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		char[] rands = new char[4];
		for (int i = 0; i < 4; i++) {
			int rand = (int) (Math.random() * (10 + 26 * 2));
			rands[i] = chars.charAt(rand);
		}
		return rands;
	}

	/**
	 * @Title: drawRands
	 * @Description: 绘制验证码
	 * @return: void
	 */
	private static void drawRands(Graphics g, char[] rands) {
		g.setFont(new Font("Console", Font.BOLD, FONT_SIZE));

		for (int i = 0; i < rands.length; i++) {

			g.setColor(getRandomColor());
			g.drawString("" + rands[i], i * FONT_SIZE + 10, 22);
		}
	}

	/**
	 * @Title: drawBackground
	 * @Description: 绘制图片背景
	 * @return: void
	 */
	private static void drawBackground(Graphics g) {

		g.setColor(Color.white);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		// 绘制验证码干扰点
		for (int i = 0; i < 200; i++) {
			int x = (int) (Math.random() * WIDTH);
			int y = (int) (Math.random() * HEIGHT);
			g.setColor(getRandomColor());
			g.drawOval(x, y, 1, 1);

		}
	}

	/**
	 * @Title: getRandomColor
	 * @Description: 获取随机颜色
	 * @return: Color
	 */
	private static Color getRandomColor() {
		Random ran = new Random();
		return new Color(ran.nextInt(220), ran.nextInt(220), ran.nextInt(220));
	}
}
