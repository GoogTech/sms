package pers.huangyuhui.sms.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pers.huangyuhui.sms.dao.AdminDao;
import pers.huangyuhui.sms.dao.StudentDao;
import pers.huangyuhui.sms.dao.TeacherDao;
import pers.huangyuhui.sms.model.AdminInfo;
import pers.huangyuhui.sms.model.StudentInfo;
import pers.huangyuhui.sms.model.TeacherInfo;

@WebServlet("/PersonalManagementServlet")
public class PersonalManagementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static AdminDao adminDao = new AdminDao();
	private static TeacherDao teacherDao = new TeacherDao();
	private static StudentDao studentDao = new StudentDao();

	public PersonalManagementServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 后期需为全站设计字符编码过滤器哟 ~
		response.setCharacterEncoding("utf-8");

		String method = request.getParameter("method");
		if ("toPersonalView".equals(method)) {
			toPersonalView(request, response);
		} else if ("toEditPasswod".equals(method)) {
			editPasswod(request, response);
			return;
		}
	}

	/**
	 * @Title: editPasswod
	 * @Description: 修改密码
	 * @param: request
	 * @param: response
	 * @return: void
	 * @throws IOException
	 */
	private void editPasswod(HttpServletRequest request, HttpServletResponse response) throws IOException {

		// 获取登录用户的类型
		int userType = Integer.valueOf(request.getSession().getAttribute("userType").toString());
		// 获取密码重置信息
		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("newPassword");

		// 校验
		if (userType == 1) {// 管理员
			AdminInfo adminInfo = new AdminInfo();
			adminInfo = (AdminInfo) request.getSession().getAttribute("userInfo");
			if (!adminInfo.getPassword().equals(oldPassword)) {
				response.getWriter().write("原密码错误哟 !");
				return;
			} else {
				// 修改
				if (adminDao.modifyPassword(adminInfo, newPassword)) {
					response.getWriter().write("success");
				} else {
					response.getWriter()
							.write("error: sorry ~ system error ! please check the database connection or try again !");
				}
			}

		} else if (userType == 2) {// 学生
			StudentInfo studentInfo = new StudentInfo();
			studentInfo = (StudentInfo) request.getSession().getAttribute("userInfo");
			if (!studentInfo.getPassword().equals(oldPassword)) {
				response.getWriter().write("原密码错误哟 !");
				return;
			} else {
				if (studentDao.modifyPassword(studentInfo, newPassword)) {
					response.getWriter().write("success");
				} else {
					response.getWriter()
							.write("error: sorry ~ system error ! please check the database connection or try again !");
				}
			}

		} else if (userType == 3) {// 教师
			TeacherInfo teacherInfo = new TeacherInfo();
			teacherInfo = (TeacherInfo) request.getSession().getAttribute("userInfo");
			if (!teacherInfo.getPassword().equals(oldPassword)) {
				response.getWriter().write("原密码错误哟 !");
			} else {
				if (teacherDao.modifyPassword(teacherInfo, newPassword)) {
					response.getWriter().write("success");
				} else {
					response.getWriter()
							.write("error: sorry ~ system error ! please check the database connection or try again !");
				}
			}
		}
	}

	/**
	 * @Title: toPersonalView
	 * @Description: 将请求转发到修改密码页面
	 * @param: request
	 * @param: response
	 * @return: void
	 */
	private void toPersonalView(HttpServletRequest request, HttpServletResponse response) {

		try {
			request.getRequestDispatcher("/WEB-INF/view/management/personalView.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
