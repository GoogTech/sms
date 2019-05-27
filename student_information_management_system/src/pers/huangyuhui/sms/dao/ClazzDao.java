/**  
 * GitHub address: https://yubuntu0109.github.io/
 * @Title: ClazzDao.java   
 * @Package pers.huangyuhui.sms.dao   
 * @Description: 数据库操作
 * @author: Huang Yuhui     
 * @date: May 10, 2019 PM   
 * @version 1.0
 *
 */
package pers.huangyuhui.sms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pers.huangyuhui.sms.model.ClazzInfo;
import pers.huangyuhui.sms.model.Paging;
import pers.huangyuhui.sms.util.StringUtil;

/**
 * @ClassName: ClassDao
 * @Description: 操控班级信息
 * @author: HuangYuhui
 * @date: May 11, 2019 2:04:38 PM
 * 
 */
public class ClazzDao extends BasicDao {

	/**
	 * @Title: getClassList
	 * @Description: 获取班级列表
	 * @param: clazz
	 * @param: paging
	 * @return: List<ClazzInfo>
	 */
	public List<ClazzInfo> getClassList(ClazzInfo clazz, Paging paging) {

		List<ClazzInfo> list = new ArrayList<ClazzInfo>();
		String sql = "select id,name,introduce from classInfo ";

		if (!StringUtil.isEmpty(clazz.getName())) {
			sql += "where name like '%" + clazz.getName() + "%' ";
		}
		// 分页查询
		sql += " limit " + paging.getPageStart() + "," + paging.getPageSize();

		try (ResultSet resultSet = query(sql)) {
			while (resultSet.next()) {
				ClazzInfo clazzInfo = new ClazzInfo();
				clazzInfo.setId(resultSet.getInt("id"));
				clazzInfo.setName(resultSet.getString("name"));
				clazzInfo.setIntroduce(resultSet.getString("introduce"));
				list.add(clazzInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;

	}

	/**
	 * @Title: getClassListNum
	 * @Description: 获取班级列表的条数
	 * @param: clazz
	 * @return: int
	 */
	public int getClassListNum(ClazzInfo clazz) {

		int num = 0;
		String sql = "select count(*) as num from classInfo ";

		if (!StringUtil.isEmpty(clazz.getName())) {
			sql += "where name like '%" + clazz.getName() + "%' ";
		}
		try (ResultSet resultSet = query(sql)) {
			while (resultSet.next()) {
				num = resultSet.getInt("num");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return num;
	}

	/**
	 * @Title: addClass
	 * @Description: 添加班级
	 * @param: clazzInfo
	 * @return: boolean
	 */
	public boolean addClass(ClazzInfo clazzInfo) {

		String sql = null;
		if (!StringUtil.isEmpty(clazzInfo.toString())) {
			sql = "insert into classInfo(name,introduce) values('" + clazzInfo.getName() + "','"
					+ clazzInfo.getIntroduce() + "')";
		}

		return update(sql);
	}

	/**
	 * @Title: deleteClass
	 * @Description: 删除班级
	 * @param: clazzInfo
	 * @return: boolean
	 */
	public boolean deleteClass(int classId) {

		String sql = "delete from classInfo where id = " + classId;
		return update(sql);
	}

	/**
	 * @Title: editClassInfo
	 * @Description: 更新班级信息
	 * @param: clazzInfo
	 * @return: boolean
	 */
	public boolean editClassInfo(ClazzInfo clazzInfo) {

		// 为防止异常:Data truncation: Truncated incorrect DOUBLE value,使用 ' , ' 代替 ' and ' !
		String sql = "update classInfo set name='" + clazzInfo.getName() + "' ,  introduce='" + clazzInfo.getIntroduce()
				+ "' where id= " + clazzInfo.getId();
		return update(sql);
	}

}
