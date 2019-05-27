/**  
 * GitHub address: https://yubuntu0109.github.io/
 * @Title: StudentInfo.java   
 * @Package pers.huangyuhui.sms.model   
 * @Description: 实体表
 * @author: Huang Yuhui     
 * @date: May 13, 2019 11:30:41 AM   
 * @version 1.0
 *
 */
package pers.huangyuhui.sms.model;

import java.io.InputStream;

/**
 * @ClassName: StudentInfo
 * @Description: 学生信息
 * @author: HuangYuhui
 * @date: May 13, 2019 11:30:41 AM
 * 
 */
public class StudentInfo {

	private Integer classID;
	private Integer id;
	private String sno;
	private String name;
	private String password;
	private String sex = "男";// default
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

	public String getSno() {
		return sno;
	}

	public void setSno(String sno) {
		this.sno = sno;
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
