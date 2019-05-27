/**  
 * GitHub address: https://yubuntu0109.github.io/
 * @Title: StudentDao.java   
 * @Package pers.huangyuhui.sms.dao   
 * @Description: 数据库操作
 * @author: Huang Yuhui     
 * @date: May 13, 2019 1:36:34 PM   
 * @version 1.0
 *
 */
package pers.huangyuhui.sms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pers.huangyuhui.sms.model.Paging;
import pers.huangyuhui.sms.model.StudentInfo;
import pers.huangyuhui.sms.util.DbUtil;
import pers.huangyuhui.sms.util.StringUtil;

/**
 * @ClassName: StudentDao
 * @Description: 操控学生信息
 * @author: HuangYuhui
 * @date: May 13, 2019 1:36:34 PM
 * 
 */
public class StudentDao extends BasicDao {

	/**
	 * @Title: getUserInfo
	 * @Description: 获取用户信息
	 * @param: name
	 * @param: password
	 * @return: StudentInfo
	 */
	public StudentInfo getUserInfo(String name, String password) {
		String sql = "select id,classID,sno,name,password,sex,email,mobile,photo from user_student where name='" + name
				+ "'  and password='" + password + "'";

		try (ResultSet resultSet = query(sql)) {
			if (resultSet.next()) {
				StudentInfo studentInfo = new StudentInfo();
				studentInfo.setId(resultSet.getInt("id"));
				studentInfo.setClassID(resultSet.getInt("classID"));
				studentInfo.setSno(resultSet.getString("sno"));
				studentInfo.setName(resultSet.getString("name"));
				studentInfo.setPassword(resultSet.getString("password"));
				studentInfo.setSex(resultSet.getString("sex"));
				studentInfo.setEmail(resultSet.getString("email"));
				studentInfo.setMobile(resultSet.getString("mobile"));
				studentInfo.setPhoto(resultSet.getBinaryStream("photo"));

				return studentInfo;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @Title: addStudent
	 * @Description: 添加学生信息
	 * @param: studentInfo
	 * @return: boolean
	 */
	public boolean addStudent(StudentInfo studentInfo) {

		String sql = null;
		if (!StringUtil.isEmpty(studentInfo.toString())) {
			sql = "insert into user_student(classID,sno,name,password,sex,email,mobile) values('"
					+ studentInfo.getClassID() + "','" + studentInfo.getSno() + "','" + studentInfo.getName() + "' , '"
					+ studentInfo.getPassword() + "' , '" + studentInfo.getSex() + "' , '" + studentInfo.getEmail()
					+ "' , '" + studentInfo.getMobile() + "')";
		}

		return update(sql);
	}

	/**
	 * @Title: getStudentList
	 * @Description: 获取学生列表
	 * @param: studentInfo
	 * @param: paging
	 * @param: @return
	 * @return: List<StudentInfo>
	 */
	public List<StudentInfo> getStudentList(StudentInfo studentInfo, Paging paging) {

		List<StudentInfo> list = new ArrayList<StudentInfo>();
		String sql = "select * from user_student ";

		if (!StringUtil.isEmpty(studentInfo.getName())) {
			sql += " and name like '%" + studentInfo.getName() + "%' ";
		}
		if (studentInfo.getClassID() != 0) {
			sql += " and classID = " + studentInfo.getClassID();
		}
		if (studentInfo.getId() != 0) {
			sql += " and id = " + studentInfo.getId();// 约束用户权限
		}
		sql += " limit " + paging.getPageStart() + "," + paging.getPageSize();// 分页查询

		try (ResultSet resultSet = query(sql.replaceFirst("and", "where"))) {
			while (resultSet.next()) {
				StudentInfo studentInfo2 = new StudentInfo();
				studentInfo2.setClassID(resultSet.getInt("classID"));
				studentInfo2.setId(resultSet.getInt("id"));
				studentInfo2.setSno(resultSet.getString("sno"));
				studentInfo2.setName(resultSet.getString("name"));
				studentInfo2.setSex(resultSet.getString("sex"));
				studentInfo2.setEmail(resultSet.getString("email"));
				studentInfo2.setMobile(resultSet.getString("mobile"));
				list.add(studentInfo2);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;

	}

	/**
	 * @Title: getStudentListNum
	 * @Description: 获取学生列表的条数
	 * @param: studentInfo
	 * @return: int
	 */
	public int getStudentListNum(StudentInfo studentInfo) {

		int num = 0;
		String sql = "select count(*) as num from user_student ";

		if (!StringUtil.isEmpty(studentInfo.getName())) {
			sql += "and name like '%" + studentInfo.getName() + "%' ";
		}
		if (studentInfo.getClassID() != 0) {
			sql += "and classID = " + studentInfo.getClassID();
		}
		if (studentInfo.getId() != 0) {
			sql += " and id = " + studentInfo.getId();// 约束用户权限
		}

		try (ResultSet resultSet = query(sql.replaceFirst("and", "where"))) {
			while (resultSet.next()) {
				num = resultSet.getInt("num");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return num;
	}

	/**
	 * @Title: editStudent
	 * @Description: 更新学生信息
	 * @param: studentInfo
	 * @return: boolean
	 */
	public boolean editStudentInfo(StudentInfo studentInfo) {

		// 为防止异常:Data truncation: Truncated incorrect DOUBLE value,使用 ' , ' 代替 ' and ' !
		String sql = "update user_student set name='" + studentInfo.getName() + "' ,  sex='" + studentInfo.getSex()
				+ "' ,  email='" + studentInfo.getEmail() + "' ,  mobile='" + studentInfo.getMobile() + "' ,  classID='"
				+ studentInfo.getClassID() + "' where id= " + studentInfo.getId();
		return update(sql);
	}

	/**
	 * @Title: deleteStudent
	 * @Description: 删除学生
	 * @param: ids
	 * @return: boolean
	 */
	public boolean deleteStudent(String ids) {

		String sql = "delete from user_student where id in ( " + ids + " )";
		return update(sql);
	}

	/**
	 * @Title: getStudentInfoById
	 * @Description: 获取学生信息
	 * @param: id
	 * @return: StudentInfo
	 */
	public StudentInfo getStudentInfoById(int id) {

		String sql = "select * from user_student where id =" + id;
		StudentInfo studentInfo = null;

		try (ResultSet resultSet = query(sql)) {
			if (resultSet.next()) {
				studentInfo = new StudentInfo();
				studentInfo.setId(resultSet.getInt("id"));
				studentInfo.setClassID(resultSet.getInt("classID"));
				studentInfo.setSno(resultSet.getString("sno"));
				studentInfo.setName(resultSet.getString("name"));
				studentInfo.setPassword(resultSet.getString("password"));
				studentInfo.setSex(resultSet.getString("sex"));
				studentInfo.setEmail(resultSet.getString("email"));
				studentInfo.setMobile(resultSet.getString("mobile"));
				studentInfo.setPhoto(resultSet.getBinaryStream("photo"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return studentInfo;
	}

	/**
	 * @Title: setStudentPhoto
	 * @Description: 更新用户头像
	 * @param: studentInfo
	 * @return: boolean
	 */
	public boolean setStudentPhoto(StudentInfo studentInfo) {

		Connection connection = DbUtil.getConnection();
		String sql = "update user_student set photo = ? where id = ?";

		try (PreparedStatement prepareStatement = connection.prepareStatement(sql)) {
			prepareStatement.setBinaryStream(1, studentInfo.getPhoto());
			prepareStatement.setInt(2, studentInfo.getId());
			return prepareStatement.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return update(sql);
	}

	/**
	 * @Title: modifyPassword
	 * @Description: 修改密码
	 * @param: studentInfo
	 * @param: newPassword
	 * @return: boolean
	 */
	public boolean modifyPassword(StudentInfo studentInfo, String newPassword) {
		String sql = "update user_student set password = '" + newPassword + "' where id = '" + studentInfo.getId()
				+ "'";
		return update(sql);
	}

}
