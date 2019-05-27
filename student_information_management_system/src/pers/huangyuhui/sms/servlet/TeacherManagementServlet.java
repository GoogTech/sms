package pers.huangyuhui.sms.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import pers.huangyuhui.sms.dao.TeacherDao;
import pers.huangyuhui.sms.model.Paging;
import pers.huangyuhui.sms.model.TeacherInfo;

@WebServlet("/TeacherManagementServlet")
public class TeacherManagementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static TeacherDao teacherDao = new TeacherDao();

	public TeacherManagementServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 后期需为全站设计字符编码过滤器哟 ~
		response.setCharacterEncoding("utf-8");

		String method = request.getParameter("method");
		if ("toTeacherListView".equals(method)) {
			teacherListView(request, response);
		} else if ("addTeacher".equals(method)) {
			addTeacher(request, response);
		} else if ("getTeacherList".equals(method)) {
			getTeacherList(request, response);
		} else if ("editTeacher".equals(method)) {
			editTeacher(request, response);
		} else if ("deleteTeacher".equals(method)) {
			deleteTeacher(request, response);
		}
	}

	/**
	 * @Title: deleteTeacher
	 * @Description: 删除教师信息
	 * @param: request
	 * @param: response
	 * @return: void
	 */
	private void deleteTeacher(HttpServletRequest request, HttpServletResponse response) {

		// 获取待删除的教师id
		String[] idArray = request.getParameterValues("ids[]");

		// 拼接教师id
		String idStr = "";
		for (String id : idArray) {
			idStr += id + ",";
		}
		idStr = idStr.substring(0, idStr.length() - 1);

		// 删除数据
		if (teacherDao.deleteTeacher(idStr)) {
			try {
				response.getWriter().write("success");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @Title: editTeacher
	 * @Description: 修改教师信息
	 * @param: request
	 * @param: response
	 * @return: void
	 */
	private void editTeacher(HttpServletRequest request, HttpServletResponse response) {

		// 获取修改信息
		String name = request.getParameter("name");
		String sex = request.getParameter("sex");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		Integer id = Integer.valueOf(request.getParameter("id"));
		Integer classID = Integer.valueOf(request.getParameter("clazzid"));

		// 存储
		TeacherInfo teacherInfo = new TeacherInfo();
		teacherInfo.setId(id);
		teacherInfo.setName(name);
		teacherInfo.setSex(sex);
		teacherInfo.setEmail(email);
		teacherInfo.setMobile(phone);
		teacherInfo.setClassID(classID);

		// 更新数据
		if (teacherDao.editTeacherInfo(teacherInfo)) {
			try {
				response.getWriter().write("success");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @Title: getTeacherList
	 * @Description: 获取教师列表信息
	 * @param: request
	 * @param: response
	 * @return: void
	 */
	private void getTeacherList(HttpServletRequest request, HttpServletResponse response) {

		// ( 用户权限设置 )获取当前登录的用户类型
		Integer userType = Integer.valueOf(request.getSession().getAttribute("userType").toString());

		// 三目算法防止无page..属性的页面发生响应出错
		int currentPage = request.getParameter("page") == null ? 1 : Integer.valueOf(request.getParameter("page"));
		int pageSize = request.getParameter("rows") == null ? 999 : Integer.valueOf(request.getParameter("rows"));

		// 获取用户查询条件
		String name = request.getParameter("teacherName");
		int classID = request.getParameter("clazzid") == null ? 0 : Integer.valueOf(request.getParameter("clazzid"));

		// 存储查询条件
		TeacherInfo teacherInfo = new TeacherInfo();
		teacherInfo.setId(0);
		teacherInfo.setName(name);
		teacherInfo.setClassID(classID);

		// 用户权限设置: 如果当前用户类型为教师,则将其权限设置为仅能查询个人信息
		if (userType == 3) {
			TeacherInfo currentTeacherInfo = (TeacherInfo) request.getSession().getAttribute("userInfo");// 获取当前登录的教师信息
			teacherInfo.setId(currentTeacherInfo.getId());
		}

		// 获取分页后的教师列表信息
		List<TeacherInfo> teacherList = teacherDao.getTeacherList(teacherInfo, new Paging(currentPage, pageSize));

		// 获取教师信息列表的条数
		int totalNum = teacherDao.getTeacherListNum(teacherInfo);

		// 将List转化为JSON数据
		Map<String, Object> datas = new HashMap<String, Object>();
		datas.put("total", totalNum);
		datas.put("rows", teacherList);
		try {
			// 在页面中显示教师列表信息
			response.getWriter().write(JSONObject.fromObject(datas).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Title: addTeacher
	 * @Description: 添加教师信息
	 * @param: request
	 * @param: response
	 * @return: void
	 */
	private void addTeacher(HttpServletRequest request, HttpServletResponse response) {

		// 获取待添加的教师信息
		int classID = request.getParameter("clazzid") == null ? 0 : Integer.valueOf(request.getParameter("clazzid"));
		String tno = request.getParameter("tno");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String sex = request.getParameter("sex");
		String email = request.getParameter("email");
		String mobile = request.getParameter("phone");

		// 存储
		TeacherInfo teacherInfo = new TeacherInfo();
		teacherInfo.setClassID(classID);
		teacherInfo.setTno(tno);
		teacherInfo.setName(name);
		teacherInfo.setPassword(password);
		teacherInfo.setSex(sex);
		teacherInfo.setEmail(email);
		teacherInfo.setMobile(mobile);

		// 添加数据
		if (teacherDao.addTeacher(teacherInfo)) {
			try {
				response.getWriter().write("success");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @Title: teacherListView
	 * @Description: 将请求转发到教师信息页面
	 * @param: request
	 * @param: response
	 * @return: void
	 */
	private void teacherListView(HttpServletRequest request, HttpServletResponse response) {

		try {
			request.getRequestDispatcher("/WEB-INF/view/teacher/teacherList.jsp").forward(request, response);
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
