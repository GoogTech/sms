/**  
 * GitHub address: https://yubuntu0109.github.io/
 * @Title: Admin.java   
 * @Package pers.huangyuhui.sms.model   
 * @Description: 实体表
 * @author: Huang Yuhui     
 * @date: May 10, 2019 2:11:44 PM   
 * @version 1.0 
 *
 */
package pers.huangyuhui.sms.model;

/**
 * @ClassName: Admin
 * @Description: 管理员信息
 * @author: HuangYuhui
 * @date: May 10, 2019 2:11:44 PM
 * 
 */
public class AdminInfo {

	private int status;
	private String id;
	private String name;
	private String password;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Admin [status=" + status + ", id=" + id + ", name=" + name + ", password=" + password + "]";
	}

}
