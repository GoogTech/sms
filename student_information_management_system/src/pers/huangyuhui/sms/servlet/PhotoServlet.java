package pers.huangyuhui.sms.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;

import com.lizhou.exception.FileFormatException;
import com.lizhou.exception.NullFileException;
import com.lizhou.exception.ProtocolException;
import com.lizhou.exception.SizeException;
import com.lizhou.fileload.FileUpload;

//啧啧啧~ 自定义jar包呢 !

import pers.huangyuhui.sms.dao.StudentDao;
import pers.huangyuhui.sms.dao.TeacherDao;
import pers.huangyuhui.sms.model.StudentInfo;
import pers.huangyuhui.sms.model.TeacherInfo;

/**
 * @ClassName: PhotoServlet
 * @Description: 操控图片
 * @author: HuangYuhui
 * @date: May 13, 2019 4:47:17 PM
 *
 */
@WebServlet("/PhotoServlet")
public class PhotoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static StudentDao studentDao = new StudentDao();
	private static TeacherDao teacherDao = new TeacherDao();

	public PhotoServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		/*
		 * 后期需要为全站设计字符过滤器 ~
		 */
		response.setCharacterEncoding("utf-8");

		String method = request.getParameter("method");
		if ("getPhoto".equals(method)) {
			getPhoto(request, response);
		} else if ("setPhoto".equals(method)) {
			uploadPhoto(request, response);
		}
	}

	/**
	 * @Title: setPhoto
	 * @Description: 上传用户头像( 点击上传按钮时触发 )
	 * @param: request
	 * @param: response
	 * @return: void
	 * @throws IOException
	 */
	private void uploadPhoto(HttpServletRequest request, HttpServletResponse response) throws IOException {

		// 获取登录用户的类型
		int sid = request.getParameter("sid") == null ? 0 : Integer.parseInt(request.getParameter("sid"));// 学生id
		int tid = request.getParameter("tid") == null ? 0 : Integer.parseInt(request.getParameter("tid"));// 教师id

		// 设置上传图片格式及大小
		FileUpload fileUpload = new FileUpload(request);
		fileUpload.setFileFormat("jpg");
		fileUpload.setFileFormat("png");
		fileUpload.setFileFormat("gif");
		fileUpload.setFileFormat("jpeg");
		fileUpload.setFileSize(2048);// 2M

		try {
			// 读取图片
			InputStream uploadInputStream = fileUpload.getUploadInputStream();
			// 学生
			if (sid != 0) {
				StudentInfo studentInfo = new StudentInfo();
				studentInfo.setId(sid);
				studentInfo.setPhoto(uploadInputStream);
				// 上传头像
				if (studentDao.setStudentPhoto(studentInfo)) {
					response.getWriter().write("<div id='message'>头像上传成功啦 ~</div>");
				} else {
					response.getWriter().write("<div id='message'>头像上传失败 !</div>");
				}
			}
			// 教师
			if (tid != 0) {
				TeacherInfo teacherInfo = new TeacherInfo();
				teacherInfo.setId(tid);
				teacherInfo.setPhoto(uploadInputStream);
				if (teacherDao.setTeacherPhoto(teacherInfo)) {
					response.getWriter().write("<div id='message'>头像上传成功啦 ~</div>");
				} else {
					response.getWriter().write("<div id='message'>头像上传失败 !</div>");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			response.getWriter().write("<div id='message'>图片获取失败 !</div>");
		} catch (ProtocolException e) {
			response.getWriter().write("<div id='message'>上传协议错误啦 !</div>");
			e.printStackTrace();
		} catch (NullFileException e) {
			response.getWriter().write("<div id='message'>请选择指定的图片哟 ~</div>");
			e.printStackTrace();
		} catch (SizeException e) {
			response.getWriter().write("<div id='message'>头像大小不能超过2M哟 !</div>");
			e.printStackTrace();
		} catch (FileFormatException e) {
			response.getWriter()
					.write("<div id='message'>别闹! 请上传图片呀~ 如格式为: " + fileUpload.getFileFormat() + " 的文件哟 ~</div>");
			e.printStackTrace();
		} catch (FileUploadException e) {
			response.getWriter().write("<div id='message'>图片上传失败 !</div>");
			e.printStackTrace();
		}

	}

	/**
	 * @Title: getPhoto
	 * @Description: 获取图片并显示在页面
	 * @param: request
	 * @param: response
	 * @return: void
	 */
	private void getPhoto(HttpServletRequest request, HttpServletResponse response) {

		// 获取登录用户的类型
		int sid = request.getParameter("sid") == null ? 0 : Integer.parseInt(request.getParameter("sid"));// 学生id
		int tid = request.getParameter("tid") == null ? 0 : Integer.parseInt(request.getParameter("tid"));// 教师id

		/*
		 * 根据用户类型读取头像,并将其显示到页面
		 */
		if (sid != 0) {
			StudentInfo studentInfo = new StudentInfo();
			studentInfo = studentDao.getStudentInfoById(sid);
			if (studentInfo != null) {
				try (InputStream photoInputStream = studentInfo.getPhoto()) {

					if (photoInputStream != null) {
						byte[] b = new byte[photoInputStream.available()];
						photoInputStream.read(b);
						response.getOutputStream().write(b, 0, b.length);// 在页面中显示头像
						return;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		if (tid != 0) {
			TeacherInfo teacherInfo = new TeacherInfo();
			teacherInfo = teacherDao.getTeacherInfoById(tid);
			if (teacherInfo != null) {
				try (InputStream photoInputStream = teacherInfo.getPhoto()) {

					if (photoInputStream != null) {
						byte[] b = new byte[photoInputStream.available()];
						photoInputStream.read(b);
						response.getOutputStream().write(b, 0, b.length);
						return;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		/*
		 * 默认情况( 无头像 ): 读取默认头像并将其显示到页面
		 */
		String path = request.getSession().getServletContext().getRealPath("");

		File file = new File(path + "resource\\image\\default_portrait.jpg");

		try (FileInputStream fileInputStream = new FileInputStream(file)) {
			byte[] b = new byte[fileInputStream.available()];
			fileInputStream.read(b);

			response.getOutputStream().write(b, 0, b.length);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
