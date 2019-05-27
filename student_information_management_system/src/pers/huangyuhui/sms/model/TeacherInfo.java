/**  
 * GitHub address: https://yubuntu0109.github.io/
 * @Title: TeacherInfo.java   
 * @Package pers.huangyuhui.sms.model   
 * @Description: 实体表
 * @author: Huang Yuhui     
 * @date: May 19, 2019 7:12:39 PM   
 * @version 1.0
 *
 */
package pers.huangyuhui.sms.model;

import java.io.InputStream;

/**
 * @ClassName: TeacherInfo
 * @Description: 教师信息
 * @author: HuangYuhui
 * @date: May 19, 2019 7:12:39 PM
 * 
 */
public class TeacherInfo {

	private Integer classID;
	private Integer id;
	private String tno;
	private String name;
	private String password;
	private String sex;
	private String email;
	private String mobile;
	private InputStream photo;

	public Integer getClassID() {
		return classID;
	}

	public void setClassID(Integer classID) {
		this.classID = classID;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTno() {
		return tno;
	}

	public void setTno(String tno) {
		this.tno = tno;
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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public InputStream getPhoto() {
		return photo;
	}

	public void setPhoto(InputStream photo) {
		this.photo = photo;
	}

}
