package pers.huangyuhui.sms.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: SysMainInterfaceServlet
 * @Description: 操控系统主页面
 * @author: HuangYuhui
 * @date: May 10, 2019 9:24:09 PM
 *
 */
@WebServlet("/SysMainInterfaceServlet")
public class SysMainInterfaceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SysMainInterfaceServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {

		String method = request.getParameter("method");

		if ("toMainView".equals(method)) {
			toMainView(request, response);
		}

	}

	/**
	 * @Title: toMainView
	 * @Description: 经滤器拦截后将请求转发到系统主页面
	 * @param: request
	 * @param: response
	 * @return: void
	 */
	private void toMainView(HttpServletRequest request, HttpServletResponse response) {

		try {
			request.getRequestDispatcher("/WEB-INF/view/system/main.jsp").forward(request, response);
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
