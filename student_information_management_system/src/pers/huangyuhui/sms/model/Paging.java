/**  
 * GitHub address: https://yubuntu0109.github.io/
 * @Title: PageUtil.java   
 * @Package pers.huangyuhui.sms.model   
 * @Description: 工具
 * @author: Huang Yuhui     
 * @date: May 11, 2019 4:17:16 PM   
 * @version 1.0
 *
 */
package pers.huangyuhui.sms.model;

/**
 * @ClassName: PageUtil
 * @Description: 分页操作
 * @author: HuangYuhui
 * @date: May 11, 2019 4:17:16 PM
 * 
 */
public class Paging {

	private int currentPage;// 当前页
	private int pageStart;// 起始页
	private int pageSize;// 每页显示数量

	public Paging(int currentPage, int pageSize) {

		// 初始化起始页,例如pageStart->pageSize: 0->10/10->10/20->10 ···
		pageStart = (currentPage - 1) * pageSize;

		this.currentPage = currentPage;
		this.pageSize = pageSize;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageStart() {
		return pageStart;
	}

	public void setPageStart(int pageStart) {
		this.pageStart = pageStart;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
