/**  
 * GitHub address: https://yubuntu0109.github.io/
 * @Title: DbUtil2.java   
 * @Package pers.huangyuhui.sms.util   
 * @Description: 连接数据库
 * @author: Huang Yuhui     
 * @date: May 10, 2019 1:50:45 PM   
 * @version  1.0
 *
 */
package pers.huangyuhui.sms.util;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @ClassName: DbUtil
 * @Description: 操控数据库的工具类
 * @author: HuangYuhui
 * @date: May 10, 2019 1:50:45 PM
 * 
 */
public class DbUtil {

	private static Connection connection;

	// 初始化数据库配置信息
	private static String URL = DbConfig.getProperties().getProperty("Url");
	private static String USER = DbConfig.getProperties().getProperty("UserName");
	private static String PASSWORD = DbConfig.getProperties().getProperty("UserPassword");
	private static String DRIVER_NAME = DbConfig.getProperties().getProperty("DriverName");

	private DbUtil() {

	}

	static {
		try {
			Class.forName(DRIVER_NAME);
		} catch (Exception e) {
			System.err.println("error : fail to initialize the driver of database !\n");
			throw new ExceptionInInitializerError(e);
		}
	}

	/**
	 * @Title: getConnection
	 * @Description: 获取数据库连接
	 * @return: Connection
	 */
	public static Connection getConnection() {
		if (connection == null) {
			try {
				connection = DriverManager.getConnection(URL, USER, PASSWORD);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return connection;
	}

}
