package com.rom.util.ValidateCode;

import java.awt.Color; 
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;
import javax.imageio.ImageIO;

public class ValidateCode {
	/**
	 * 
	 */
	// 验证码图片的宽度。
	private int width = 350;
	// 验证码图片的高度。
	private int height = 100;
	// 验证码字符个数
	private int codeCount = 4;

	private int x = 0;
	// 字体高度
	private int fontHeight;
	private int codeY;

	char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
			'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
			'Y', 'Z', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

	/**
	 * 初始化验证图片属性
	 */
	public void init() {
		// 宽度
		String strWidth = "350";
		// 高度
		String strHeight = "100";
		// 字符个数
		String strCodeCount = "4";

		// 将配置的信息转换成数值
		try {
			if (strWidth != null && strWidth.length() != 0) {
				width = Integer.parseInt(strWidth);
			}
			if (strHeight != null && strHeight.length() != 0) {
				height = Integer.parseInt(strHeight);
			}
			if (strCodeCount != null && strCodeCount.length() != 0) {
				codeCount = Integer.parseInt(strCodeCount);
			}
		} catch (NumberFormatException e) {
		}

		x = width / (codeCount + 1);
		fontHeight = height - 2;
		codeY = height - 4;

	}

	protected void service() {
		try {
			// 定义图像buffer
			BufferedImage buffImg = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = buffImg.createGraphics();

			// 创建一个随机数生成器类
			Random random = new Random();

			// 将图像填充为白色
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, width, height);

			// 创建字体，字体的大小应该根据图片的高度来定。
			Font font = new Font("Fixedsys", Font.PLAIN, fontHeight);
			// 设置字体。
			g.setFont(font);

			// 画边框。
			g.setColor(Color.BLACK);
			g.drawRect(0, 0, width - 1, height - 1);

			// 随机产生160条干扰线，使图象中的认证码不易被其它程序探测到。
			g.setColor(Color.BLACK);
			for (int i = 0; i < 100; i++) {
				int x = random.nextInt(width);
				int y = random.nextInt(height);
				int xl = random.nextInt(22);
				int yl = random.nextInt(22);
				g.drawLine(x, y, x + xl, y + yl);
			}

			// randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
			StringBuffer randomCode = new StringBuffer();
			int red = 0, green = 0, blue = 0;

			// 随机产生codeCount数字的验证码。
			for (int i = 0; i < codeCount; i++) {
				// 得到随机产生的验证码数字。
				String strRand = String
						.valueOf(codeSequence[random.nextInt(34)]);
				// 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
				red = random.nextInt(255);
				green = random.nextInt(255);
				blue = random.nextInt(255);

				// 用随机产生的颜色将验证码绘制到图像中。
				g.setColor(new Color(red, green, blue));
				g.drawString(strRand, (i + 1) * x, codeY);

				// 将产生的四个随机数组合在一起。
				randomCode.append(strRand);
				System.out.println(strRand);
			}
			// 将四位数字的验证码保存到Session中。

			ImageIO.write(buffImg, "jpeg", new File("d://aa.jpeg"));

			// 将图像输出到Servlet输出流中。
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}