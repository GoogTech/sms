/**  
 * GitHub address: https://yubuntu0109.github.io/
 * @Title: BasicDao.java   
 * @Package pers.huangyuhui.sms.dao   
 * @Description: 操控数据库信息的通用功能
 * @author: Huang Yuhui     
 * @date: May 10, 2019 2:32:54 PM   
 * @version 1.0
 *
 */
package pers.huangyuhui.sms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import pers.huangyuhui.sms.util.DbUtil;

/**
 * @ClassName: BasicDao
 * @Description: 基本数据库操作
 * @author: HuangYuhui
 * @date: May 10, 2019 2:32:54 PM
 * 
 */
public class BasicDao {

	private static Connection connection = DbUtil.getConnection();

	/**
	 * @Title: query
	 * @Description: 查询数据
	 * @param: sql
	 * @return: ResultSet
	 */
	public static ResultSet query(String sql) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			return preparedStatement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @Title: update
	 * @Description: 判断是否已更新数据库信息
	 * @param: sql
	 * @return: boolean
	 */
	public boolean update(String sql) {

		try {
			return connection.prepareStatement(sql).executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @Title: releaseConnection
	 * @Description: 释放数据库连接资源
	 * @return: void
	 */
//	public static void releaseConnection() {
//		DbUtil.freeResource(null, null, connection);
//	}

}
