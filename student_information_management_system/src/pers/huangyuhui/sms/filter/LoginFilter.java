package pers.huangyuhui.sms.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: LoginFilter
 * @Description: 拦截用户未登录状态下的操作
 * @author: HuangYuhui
 * @date: May 10, 2019 8:58:58 PM
 *
 */
@WebFilter(filterName = "/LoginFilter", urlPatterns = { "/SysMainInterfaceServlet" })
public class LoginFilter implements Filter {

	public LoginFilter() {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;

		// 判断用户是否已经登录
		Object user = httpServletRequest.getSession().getAttribute("userInfo");
		if (user == null) {
			// httpServletRequest.getRequestDispatcher("/view/login.jsp").forward(httpServletRequest,httpServletResponse);
			httpServletResponse.sendRedirect("index.jsp");
			return;
		}

		chain.doFilter(request, response);

	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
