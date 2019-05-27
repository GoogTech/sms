/**  
 * GitHub address: https://yubuntu0109.github.io/
 * @Title: DbConfig.java   
 * @Package pers.huangyuhui.sms.util   
 * @Description: 数据库配置
 * @author: Huang Yuhui     
 * @date: May 10, 2019 1:29:20 PM   
 * @version 1.0
 *
 */
package pers.huangyuhui.sms.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @ClassName: DbConfig
 * @Description: 初始化数据库连接信息
 * @author: HuangYuhui
 * @date: May 10, 2019 1:29:20 PM
 * 
 */
public class DbConfig {

	private static Properties properties;
	// 读取数据库配置文件
	private static InputStream inputStream = DbConfig.class.getResourceAsStream("/databaseConfig.properties");

	private DbConfig() {
	}

	static {
		try {
			properties = new Properties();
			properties.load(inputStream);
			properties.getProperty("Url");
			properties.getProperty("UserName");
			properties.getProperty("UserPassword");
			properties.getProperty("DriverName");

		} catch (FileNotFoundException e) {
			System.err.println("error : not found the specified configuration file: databaseConfig.properties");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @Title: getProperties
	 * @Description: 获取数据库配置
	 * @return: Properties
	 */
	public static Properties getProperties() {
		return properties;
	}

}
