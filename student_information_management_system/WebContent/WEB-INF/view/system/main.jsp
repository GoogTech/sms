<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>学生信息管理系统 | 系统主页面</title>
	<!-- 引入CSS -->
    <link rel="stylesheet" type="text/css" href="easyui/css/default.css" />
    <link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css" />
    <link rel="stylesheet" type="text/css" href="easyui/themes/icon.css" />
    <!-- 引入JS -->
    <script type="text/javascript" src="easyui/jquery.min.js"></script>
    <script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src='easyui/js/outlook2.js'> </script>
    <script type="text/javascript">
    <!-- 菜单栏 -->
	 var _menus = {"menus":[
						{"menuid":"2","icon":"","menuname":"学生信息管理",
							"menus":[
									{"menuid":"21","menuname":"学生列表","icon":"icon-user-student","url":"StuManagementServlet?method=toStudentListView"},
								]
						},
						<%-- 通过JSTL设置用户权限: 仅管理员和教师可以查看教师列表信息 	--%>
						<c:if test="${userType == 1 || userType == 3}">
						{"menuid":"3","icon":"","menuname":"教师信息管理",
							"menus":[
									{"menuid":"31","menuname":"教师列表","icon":"icon-user-teacher","url":"TeacherManagementServlet?method=toTeacherListView"},
								]
						},
						</c:if>
						<%-- 通过JSTL设置用户权限:  仅管理员和教师可以查看班级列表信息	 --%>
						<c:if test="${userType == 1 || userType == 3}">
						{"menuid":"4","icon":"","menuname":"班级信息管理",
							"menus":[
									{"menuid":"42","menuname":"班级列表","icon":"icon-house","url":"ClazzManagementServlet?method=toClassListView"}
								]
						},
						</c:if>
						{"menuid":"5","icon":"","menuname":"系统用户管理",
							"menus":[
							        {"menuid":"51","menuname":"修改密码","icon":"icon-set","url":"PersonalManagementServlet?method=toPersonalView"},
								]
						}
				]};
    </script>
</head>

<body class="easyui-layout" style="overflow-y: hidden"  scroll="no">
	<noscript>
		<div style=" position:absolute; z-index:100000; height:2046px;top:0px;left:0px; width:100%; background:white; text-align:center;">
		    <img src="images/noscript.gif" alt='抱歉 ! 请开启脚本支持哟 !' />
		</div>
	</noscript>
    <div region="north" split="true" border="false" style="overflow: hidden; height: 30px;
        background: url(images/layout-browser-hd-bg.gif) #7f99be repeat-x center 50%;
        line-height: 20px;color: #fff; font-family: Verdana, 微软雅黑,黑体">
        <span style="float:right; padding-right:20px;" class="head">
        	<span style="color:blue; font-weight:bold;" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-man',plain:true">
        		<c:choose>
        			<c:when test="${userType==1 }">管理员  :  </c:when>
        			<c:when test="${userType==2 }">学生  :  </c:when>
        			<c:when test="${userType==3 }">教师  :  </c:when>
        		</c:choose>
        	</span>
        	<%-- ${userInfo.name}: 从Session中获取登录用户的用户名	--%>
        	<span style="color:red; font-weight:bold;">${userInfo.name}&nbsp;</span>
        		您好哟 ~&nbsp;&nbsp;&nbsp;
        	<a href="LoginServlet?method=loginOut" id="loginOut"  href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-back',plain:true">
        		
        		[安全退出]
        	</a>
        </span>
        <span style="padding-left:10px; font-size: 18px; ">学生信息管理系统</span>
    </div>
    <div region="south" split="true" style="height: 30px; background: #D2E0F2; ">
        <div class="footer">Copyright @ 2019 黄宇辉. All rights reserved | 本人博客网站 : https://yubuntu0109.github.io</div>
    </div>
    <div region="west" hide="true" split="true" title="[ 导航菜单  ]" style="width:180px;" id="west">
	<div id="nav" class="easyui-accordion" fit="true" border="false">
		<!--  导航菜单内容 -->
	</div>
	
    </div>
    <div id="mainPanle" region="center" style="background: #eee; overflow-y:hidden">
        <div id="tabs" class="easyui-tabs"  fit="true" border="false" >
        	<!-- 引入欢迎页面资源 -->
			<jsp:include page="/WEB-INF/view/system/welcome.jsp" />
		</div>
    </div>
	
	<!-- 刷新页面 -->
	<iframe width=0 height=0 src="refresh.jsp"/>
	
</body>
</html>