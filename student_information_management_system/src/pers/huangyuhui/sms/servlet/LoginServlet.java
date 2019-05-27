package pers.huangyuhui.sms.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pers.huangyuhui.sms.dao.AdminDao;
import pers.huangyuhui.sms.dao.StudentDao;
import pers.huangyuhui.sms.dao.TeacherDao;
import pers.huangyuhui.sms.model.AdminInfo;
import pers.huangyuhui.sms.model.StudentInfo;
import pers.huangyuhui.sms.model.TeacherInfo;
import pers.huangyuhui.sms.util.StringUtil;

/**
 * @ClassName: VerifyLoginServlet
 * @Description: 验证用户登录
 * @author: HuangYuhui
 * @date: May 6, 2019 10:19:15 PM
 *
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static AdminDao adminDao = new AdminDao();
	private static TeacherDao teacherDao = new TeacherDao();
	private static StudentDao studentDao = new StudentDao();

	public LoginServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String method = request.getParameter("method");

		if ("login".equals(method)) {
			login(request, response);
		} else if ("loginOut".equals(method)) {
			loginOut(request, response);
			return;
		}
	}

	/**
	 * @Title: login
	 * @Description: 验证用户登录表单信息
	 * @param: request
	 * @param: response
	 * @return: void
	 * @exception IOException
	 */
	private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {

		// 获取用户登录类型
		int userType = Integer.valueOf(request.getParameter("userType"));
		// 获取用户提交的登录表单信息
		String name = request.getParameter("userName");
		String password = request.getParameter("userPassword");
		// 获取验证码信息
		String verifiCode = request.getParameter("verificationCode");
		String verificationCode = request.getSession().getAttribute("verifiCode").toString();

		/*
		 * 验证验证码
		 */
		if (StringUtil.isEmpty(verifiCode) || !verifiCode.equalsIgnoreCase(verificationCode)) {
			response.getWriter().write("vcodeError");// 验证码错误提示
			return;
		}

		/*
		 * 验证用户登录
		 */
		switch (userType) {

		// 管理员身份
		case 1: {
			// 验证用户信息
			AdminInfo adminInfo = new AdminInfo();
			adminInfo = adminDao.getUserInfo(name, password);
			if (adminInfo == null) {
				response.getWriter().write("loginError");
				return;
			}
			// 登录成功: 将用户信息存储到Sesssion
			HttpSession session = request.getSession();
			session.setAttribute("userInfo", adminInfo);
			session.setAttribute("userType", userType);
			// 进入系统主页
			response.getWriter().write("loginSuccess");
			break;
		}
		// 学生身份
		case 2: {
			StudentInfo studentInfo = new StudentInfo();
			studentInfo = studentDao.getUserInfo(name, password);
			if (studentInfo == null) {
				response.getWriter().print("loginError");
				return;
			}
			HttpSession session = request.getSession();
			session.setAttribute("userInfo", studentInfo);
			session.setAttribute("userType", userType);
			response.getWriter().print("loginSuccess");
			break;
		}

		// 教师身份
		case 3: {
			TeacherInfo teacherInfo = new TeacherInfo();
			teacherInfo = teacherDao.getUserInfo(name, password);
			if (teacherInfo == null) {
				response.getWriter().print("loginError");
				return;
			}
			HttpSession session = request.getSession();
			session.setAttribute("userInfo", teacherInfo);
			session.setAttribute("userType", userType);
			response.getWriter().print("loginSuccess");
			break;
		}

		}

	}

	/**
	 * @throws IOException
	 * @Title: loginOut
	 * @Description: 注销用户信息
	 * @param: request
	 * @param: response
	 * @return: void
	 */
	private void loginOut(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().removeAttribute("userInfo");
		request.getSession().removeAttribute("userType");

		try {
			response.sendRedirect("index.jsp");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
