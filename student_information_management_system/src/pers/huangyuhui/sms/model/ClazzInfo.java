/**  
 * GitHub address: https://yubuntu0109.github.io/
 * @Title: ClassInfo.java   
 * @Package pers.huangyuhui.sms.model   
 * @Description: 实体表
 * @author: Huang Yuhui     
 * @date: May 11, 2019 1:44:43 PM   
 * @version 1.0
 *
 */
package pers.huangyuhui.sms.model;

/**
 * @ClassName: ClassInfo
 * @Description: 班级信息
 * @author: HuangYuhui
 * @date: May 11, 2019 1:44:43 PM
 * 
 */
public class ClazzInfo {

	private Integer id;
	private String name;
	private String introduce;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

}
