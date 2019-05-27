package pers.huangyuhui.sms.servlet;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pers.huangyuhui.sms.util.CreateVerifiCodeImage;

/**
 * @ClassName: VerificationCodeServlet
 * @Description: 操控验证码
 * @author: HuangYuhui
 * @date: May 6, 2019 6:49:44 PM
 *
 */
@WebServlet("/OutVerifiCodeServlet")
public class OutVerifiCodeServlet extends HttpServlet {

	private String verifiCode;// 验证码
	private BufferedImage verifiCodeImage;// 验证码图片
	private static final long serialVersionUID = 1L;

	public OutVerifiCodeServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String method = request.getParameter("method");
		if ("loginVerifiCode".equals(method)) {
			generateLoginVerifiCode(request, response);
			return;
		}
	}

	/**
	 * @Title: generateLoginVerifiCode
	 * @Description: 生成验证码图片
	 * @param: request
	 * @param: response
	 * @return: void
	 */
	protected void generateLoginVerifiCode(HttpServletRequest request, HttpServletResponse response) {

		// 获取验证码信息
		verifiCodeImage = CreateVerifiCodeImage.getVerifiCodeImage();
		verifiCode = String.valueOf(CreateVerifiCodeImage.getVerifiCode());

		// 将验证码图片输出到登录页面
		try {
			ImageIO.write(verifiCodeImage, "JPEG", response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 存储验证码Session
		request.getSession().setAttribute("verifiCode", verifiCode);
	}

}
