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
import pers.huangyuhui.sms.dao.StudentDao;
import pers.huangyuhui.sms.model.Paging;
import pers.huangyuhui.sms.model.StudentInfo;

/**
 * @ClassName: StuManagementServlet
 * @Description: 操控学生信息
 * @author: HuangYuhui
 * @date: May 11, 2019 12:31:36 PM
 *
 */
@WebServlet("/StuManagementServlet")
public class StuManagementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static StudentDao studentDao = new StudentDao();

	public StuManagementServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 后期需为全站设计字符编码过滤器哟 ~
		response.setCharacterEncoding("utf-8");

		String method = request.getParameter("method");
		if ("toStudentListView".equals(method)) {
			studentListView(request, response);
		} else if ("addStudent".equals(method)) {
			addStudent(request, response);
		} else if ("getStudentList".equals(method)) {
			getStudentList(request, response);
		} else if ("editStudent".equals(method)) {
			editStudent(request, response);
		} else if ("deleteStudent".equals(method)) {
			deleteStudent(request, response);
		}
	}

	/**
	 * @Title: deleteStudent
	 * @Description: 删除学生信息
	 * @param: request
	 * @param: response
	 * @return: void
	 */
	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) {

		// 获取要删除的学生id
		String[] idArray = request.getParameterValues("ids[]");

		// 拼接学生id
		String idStr = "";
		for (String id : idArray) {
			idStr += id + ",";
		}
		idStr = idStr.substring(0, idStr.length() - 1);

		// 删除数据
		if (studentDao.deleteStudent(idStr)) {
			try {
				response.getWriter().write("success");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * @Title: editStudent
	 * @Description: 修改学生信息
	 * @param: request
	 * @param: response
	 * @return: void
	 */
	private void editStudent(HttpServletRequest request, HttpServletResponse response) {

		// 获取修改信息
		String name = request.getParameter("name");
		String sex = request.getParameter("sex");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		Integer id = Integer.valueOf(request.getParameter("id"));
		Integer classID = Integer.valueOf(request.getParameter("clazzid"));

		// 存储
		StudentInfo studentInfo = new StudentInfo();
		studentInfo.setId(id);
		studentInfo.setName(name);
		studentInfo.setSex(sex);
		studentInfo.setEmail(email);
		studentInfo.setMobile(phone);
		studentInfo.setClassID(classID);

		// 更新数据
		if (studentDao.editStudentInfo(studentInfo)) {
			try {
				response.getWriter().write("success");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * @Title: getStudentList
	 * @Description: 获取班级列表信息
	 * @param: request
	 * @param: response
	 * @return: void
	 */
	private void getStudentList(HttpServletRequest request, HttpServletResponse response) {

		// ( 用户权限设置 )获取当前登录的用户类型
		Integer userType = Integer.valueOf(request.getSession().getAttribute("userType").toString());

		// 三目运算防止无page..属性的页面发生响应出错
		int currentPage = request.getParameter("page") == null ? 1 : Integer.valueOf(request.getParameter("page"));
		int pageSize = request.getParameter("rows") == null ? 999 : Integer.valueOf(request.getParameter("rows"));

		// 获取用户查询条件
		String name = request.getParameter("studentName");
		int classID = request.getParameter("clazzid") == null ? 0 : Integer.valueOf(request.getParameter("clazzid"));

		// 存储查询条件
		StudentInfo studentInfo = new StudentInfo();
		studentInfo.setId(0);
		studentInfo.setName(name);
		studentInfo.setClassID(classID);

		// 用户权限设置: 如果当前用户类型为学生,则将其权限设置为仅能查询个人信息
		if (userType == 2) {
			StudentInfo currentStudentInfo = (StudentInfo) request.getSession().getAttribute("userInfo");// 获取当前登录的学生信息
			studentInfo.setId(currentStudentInfo.getId());
		}

		// 获取分页后的学生列表信息
		List<StudentInfo> studentList = studentDao.getStudentList(studentInfo, new Paging(currentPage, pageSize));

		// 获取学生信息列表的条数
		int totalNum = studentDao.getStudentListNum(studentInfo);

		// 将List转化为JSON数据
		Map<String, Object> datas = new HashMap<String, Object>();
		datas.put("total", totalNum);
		datas.put("rows", studentList);
		try {
			// 在页面中显示学生列表信息
			response.getWriter().write(JSONObject.fromObject(datas).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Title: studentListView
	 * @Description: 将请求转发到学生信息页面
	 * @param: request
	 * @param: response
	 * @return: void
	 */
	private void studentListView(HttpServletRequest request, HttpServletResponse response) {

		try {
			request.getRequestDispatcher("/WEB-INF/view/student/studentList.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Title: studentList
	 * @Description: 添加学生信息
	 * @param: request
	 * @param: response
	 * @return: void
	 */
	private void addStudent(HttpServletRequest request, HttpServletResponse response) {

		// 获取添加的学生信息
		int classID = request.getParameter("clazzid") == null ? 0 : Integer.valueOf(request.getParameter("clazzid"));
		String sno = request.getParameter("sno");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String sex = request.getParameter("sex");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");

		// 存储
		StudentInfo studentInfo = new StudentInfo();
		studentInfo.setClassID(classID);
		studentInfo.setSno(sno);
		studentInfo.setName(name);
		studentInfo.setPassword(password);
		studentInfo.setSex(sex);
		studentInfo.setEmail(email);
		studentInfo.setMobile(phone);

		// 添加数据
		if (studentDao.addStudent(studentInfo)) {
			try {
				response.getWriter().write("success");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
