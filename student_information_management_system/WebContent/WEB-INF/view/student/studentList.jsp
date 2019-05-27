<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<title>学生信息管理</title>
	<!-- 引入CSS -->
	<link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="easyui/css/demo.css">
	<!-- 引入JS -->
	<script type="text/javascript" src="easyui/jquery.min.js"></script>
	<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="easyui/js/validateExtends.js"></script>
	<script type="text/javascript">
	
	$(function() {	
		//datagrid初始化 
	    $('#dataList').datagrid({ 
	        title:'学生列表', 
	        iconCls:'icon-more',//图标 
	        border: true, 
	        collapsible:false,//是否可折叠 
	        fit: true,//自动大小 
	        method: "post",
	        url:"StuManagementServlet?method=getStudentList&t="+new Date().getTime(),
	        idField:'id', 
	        singleSelect:false,//是否单选 
	        pagination:true,//分页控件 
	        rownumbers:true,//行号 
	        sortName:'id',
	        sortOrder:'DESC', 
	        remoteSort: false,
	        //field : Java Bean(StudentInfo.java)中的字段名哟 ~
	        columns: [[ 
				{field:'chk',checkbox: true,width:50},
 		        {field:'id',title:'ID',width:50, sortable: true},    
 		        {field:'sno',title:'学号',width:200, sortable: true},    
 		        {field:'name',title:'姓名',width:200},
 		        {field:'sex',title:'性别',width:100},
 		        {field:'mobile',title:'电话',width:150},
 		        {field:'email',title:'邮箱',width:150},
 		        {field:'classID',title:'班级',width:150, 
 		        	//根据班级下拉框中的班级ID来初始化学生列表中的班级名称
 		        	formatter: function(value,row,index){
 						if (row.classID){//classID:学生列表信息中的班级ID属性
 							var clazzList = $("#clazzList").combobox("getData");//获取班级下拉框中班级的列表信息
 							for(var i=0;i<clazzList.length;i++){
								 if(row.classID==clazzList[i].id){
									 return clazzList[i].name;
								 }								 
 							} 
 							return row.classID;
 						} else {
 							return 'not found ~';
 						}
 					}
				},
	 		]], 
	        toolbar: "#toolbar",
	        
	     	//在发送加载数据的请求前先初始化班级下拉框(目的:根据班级下拉框中的班级ID来初始化学生列表中的班级名称)
	        onBeforeLoad : function(){
	        	//防止班级名称下拉框中值在每次点击搜索按钮后被刷掉 ~
	        	try{
	        		$("#clazzList").combobox("getData")
	        	}catch(err){
	        		myOnBeforeLoad();	        			        		
	        	}
	        }
	        
	    }); 
		
	    //设置分页控件 
	    var p = $('#dataList').datagrid('getPager'); 
	    $(p).pagination({ 
	        pageSize: 10,//每页显示的记录条数，默认为10 
	        pageList: [10,20,30,50,100],//可以设置每页记录条数的列表 
	        beforePageText: '第',//页数文本框前显示的汉字 
	        afterPageText: '页    共 {pages} 页', 
	        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录', 
	    }); 
	    
	    //设置工具类按钮
	    $("#add").click(function(){
	    	$("#addDialog").dialog("open");
	    });
	    
	    //修改
	    $("#edit").click(function(){
	    	var selectRows = $("#dataList").datagrid("getSelections");
        	if(selectRows.length != 1){
            	$.messager.alert("消息提醒", "请单条选择你想要修改的数据哟 !", "warning");
            } else{
		    	$("#editDialog").dialog("open");
            }
	    });
	    
	    //删除
	    $("#delete").click(function(){
	    	var selectRows = $("#dataList").datagrid("getSelections");
        	var selectLength = selectRows.length;
        	if(selectLength == 0){
            	$.messager.alert("消息提醒", "请选择你想要删除的数据 !", "warning");
            } else{
            	var numbers = [];
            	$(selectRows).each(function(i, row){
            		numbers[i] = row.sno;
            	});
            	var ids = [];
            	$(selectRows).each(function(i, row){
            		ids[i] = row.id;
            	});
            	$.messager.confirm("消息提醒", "仅删除与学生相关的所有数据! 确认继续 ?", function(r){
            		if(r){
            			$.ajax({
							type: "post",
							url: "StuManagementServlet?method=deleteStudent",
							data: {sns: numbers, ids: ids},//sns: 学生学号, ids: 学生ID
							success: function(msg){
								if(msg == "success"){
									$.messager.alert("消息提醒","删除成功!","info");
									//刷新表格
									$("#dataList").datagrid("reload");
									$("#dataList").datagrid("uncheckAll");
								} else{
									$.messager.alert("消息提醒","删除失败!","warning");
									return;
								}
							}
						});
            		}
            	});
            }
	    });
	    
		function myOnBeforeLoad(){
	  		//班级下拉框
		  	$("#clazzList").combobox({
		  		width: "150",
		  		height: "25",
		  		valueField: "id",
		  		textField: "name",
		  		multiple: false, //可多选
		  		editable: false, //不可编辑
		  		method: "post",
		  		//使用`&from=combox`解决页面中班级名称下拉框无数据显示问题 !
		  		url: "ClazzManagementServlet?method=getClassList&t="+new Date().getTime()+"&from=combox",
		  		onChange: function(newValue, oldValue){
		  		}
		  	});
		}
	  	
	  	//下拉框通用属性
	  	$("#add_clazzList, #edit_clazzList").combobox({
	  		width: "200",
	  		height: "30",
	  		valueField: "id",
	  		textField: "name",
	  		multiple: false, //可多选
	  		editable: false, //不可编辑
	  		method: "post",
	  	});
	  	
	  	$("#add_clazzList,#edit_clazzList").combobox({
	  		url: "ClazzManagementServlet?method=getClassList&t="+new Date().getTime()+"&from=combox",
	  		onLoadSuccess: function(){
		  		//默认选择第一条数据
				var data = $(this).combobox("getData");
				$(this).combobox("setValue", data[0].id);
	  		}
	  	});
	  	
	  	
	  	//设置添加学生窗口
	    $("#addDialog").dialog({
	    	title: "添加学生",
	    	width: 600,
	    	height: 460,
	    	iconCls: "icon-add",
	    	modal: true,
	    	collapsible: false,
	    	minimizable: false,
	    	maximizable: false,
	    	draggable: true,
	    	closed: true,
	    	buttons: [
	    		{
					text:'添加',
					plain: true,
					iconCls:'icon-user_add',
					handler:function(){
						var validate = $("#addForm").form("validate");
						if(!validate){
							$.messager.alert("消息提醒","请检查你输入的数据哟 !","warning");
							return;
						} else{
							var clazzid = $("#add_clazzList").combobox("getValue");//获取班级的id
							$.ajax({
								type: "post",
								url: "StuManagementServlet?method=addStudent",
								data: $("#addForm").serialize(),
								success: function(msg){
									if(msg == "success"){
										$.messager.alert("消息提醒","添加成功啦 ~","info");
										//关闭窗口
										$("#addDialog").dialog("close");
										//清空原表格数据
										$("#add_sno").textbox('setValue', "");
										$("#add_name").textbox('setValue', "");
										$("#add_password").textbox('setValue', "");
										$("#add_sex").textbox('setValue', "男");
										$("#add_phone").textbox('setValue', "");
										$("#add_email").textbox('setValue', "");
										
										//重新刷新页面数据
										$('#dataList').datagrid("options").queryParams = {clazzid: clazzid};
							  			$('#dataList').datagrid("reload");
							  			setTimeout(function(){
											$("#clazzList").combobox('setValue', clazzid);
										}, 100);
										
									} else{
										$.messager.alert("消息提醒","添加失败 !","warning");
										return;
									}
								}
							});
						}
					}
				},
				{
					text:'重置',
					plain: true,
					iconCls:'icon-reload',
					handler:function(){
						$("#add_sno").textbox('setValue', "");
						$("#add_sex").textbox('setValue', "男");
						$("#add_name").textbox('setValue', "");
						$("#add_password").textbox('setValue', "");
						$("#add_phone").textbox('setValue', "");
						$("#add_email").textbox('setValue', "");
					}
				},
			]
	    });
	  	
	  	//设置编辑学生窗口
	    $("#editDialog").dialog({
	    	title: "修改学生信息",
	    	width: 650,
	    	height: 460,
	    	iconCls: "icon-edit",
	    	modal: true,
	    	collapsible: false,
	    	minimizable: false,
	    	maximizable: false,
	    	draggable: true,
	    	closed: true,
	    	buttons: [
	    		{
					text:'提交',
					plain: true,
					iconCls:'icon-user_add',
					handler:function(){
						var validate = $("#editForm").form("validate");
						var clazzid = $("#edit_clazzList").combobox("getValue");//获取班级id
						if(!validate){
							$.messager.alert("消息提醒","请检查你输入的数据哟 !","warning");
							return;
						} else{
							$.ajax({
								type: "post",
								url: "StuManagementServlet?method=editStudent&t="+new Date().getTime(),
								data: $("#editForm").serialize(),
								success: function(msg){
									if(msg == "success"){
										$.messager.alert("消息提醒","更新成功 ~","info");
										//关闭窗口
										$("#editDialog").dialog("close");
										//刷新表格
										$('#dataList').datagrid("options").queryParams = {clazzid: clazzid};
										$("#dataList").datagrid("reload");
										$("#dataList").datagrid("uncheckAll");
										
							  			setTimeout(function(){
											$("#clazzList").combobox('setValue', clazzid);// attention: clazzid ~
										}, 100);
							  			
									} else{
										$.messager.alert("消息提醒","更新失败 !","warning");
										return;
									}
								}
							});
						}
					}
				},
				{
					text:'重置',
					plain: true,
					iconCls:'icon-reload',
					handler:function(){
						//清空表单
						$("#edit_name").textbox('setValue', "");
						$("#edit_sex").textbox('setValue', "男");
						$("#edit_phone").textbox('setValue', "");
						$("#edit_email").textbox('setValue', "");
					}
				}
			],
			onBeforeOpen: function(){
				var selectRow = $("#dataList").datagrid("getSelected");
				//设置值( selectRow.`学生信息属性名` )
				$("#edit_id").val(selectRow.id);
				$("#edit_sno").textbox('setValue', selectRow.sno);
				$("#edit_name").textbox('setValue', selectRow.name);
				$("#edit_sex").textbox('setValue', selectRow.sex);
				$("#edit_phone").textbox('setValue', selectRow.mobile);
				$("#edit_email").textbox('setValue', selectRow.email);
				$("#edit_photo").attr("src", "PhotoServlet?method=getPhoto&type=2&sid="+selectRow.id);// bug ! o(╥﹏╥)o ~
				$("#edit_photo_id").val(selectRow.id); //o(╥﹏╥)o ~ 不应该是sid嘛 ! 整整花了本宝宝一个早上的时间呀 !
			},
	    
	    });
	  	
	    //学生姓名搜索按钮监听事件
	  	$("#search").click(function(){
	  		$('#dataList').datagrid('load',{
	  			studentName: $('#studentName').val(),//初始化学生姓名
	  			//利用三目算法避免: java.lang.NumberFormatException: For input string: "" ..
	  			clazzid:$("#clazzList").combobox('getValue') == '' ? 0 : $("#clazzList").combobox('getValue')// 初始化班级ID信息
	  		});
	  	});
	   
	});
	
	
	//上传图片按钮事件
	function uploadPhoto(){
		//解决由于:enctype="multipart/form-data" 而导致后台无法获取用户id的问题
		var action = $("#uploadForm").attr('action');
		$("#uploadForm").attr('action',action+'&sid='+$("#edit_photo_id").val());
		
		$("#uploadForm").submit();
		setTimeout(function(){
			var message =  $(window.frames["photo_target"].document).find("#message").text();
			$.messager.alert("消息提醒",message,"info");
			
			//上传成功后立即刷新头像
			$("#edit_photo").attr("src", "PhotoServlet?method=getPhoto&sid="+$("#edit_photo_id").val()); 
		}, 1500)
	}
	
	</script>
</head>
<body>
	<!-- 学生列表 -->
	<table id="dataList" cellspacing="0" cellpadding="0"> </table> 
	
	<!-- 工具栏 -->
	<div id="toolbar">
		<%-- 通过JSTL设置用户操作权限 :  将添加和删除按钮设置为仅管理员或教师可见	 --%>
		<c:if test="${userType==1 || userType== 3 }">
			<div style="float: left;"><a id="add" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">添加</a></div>
				<div style="float: left;" class="datagrid-btn-separator"></div>
			<div style="float: left;"><a id="delete" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-some-delete',plain:true">删除</a></div>
				<div style="float: left;" class="datagrid-btn-separator"></div>
		</c:if>
		<div style="float: left;"><a id="edit" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">修改</a></div>
		<!-- 班级名称下拉框 -->
		<div style="float: left; margin: 0 10px 0 5px" class="datagrid-btn-separator">班级<input id="clazzList" class="easyui-textbox" name="clazz" /></div>
		<!-- 学生姓名搜索框 -->
		<div style="margin-left: 10px;">学生<input id="studentName" class="easyui-textbox" name="studentName" />
			<!-- 搜索按钮 -->
			<a id="search" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">搜索</a>
		</div>
	</div>
	
	<!-- 添加信息窗口 -->
	<div id="addDialog" style="padding: 10px">  
		<div style="float: right; margin: 20px 20px 0 0; width: 200px; border: 1px solid #EBF3FF" id="photo">
	    	<img alt="照片" style="max-width: 200px; max-height: 350px;" title="照片" src="PhotoServlet?method=getPhoto" />
	    </div> 
    	<form id="addForm" method="post">
	    	<table cellpadding="8" >
	    		<tr>
	    			<td>班级:</td>
	    			<td><input id="add_clazzList" style="width: 200px; height: 30px;" class="easyui-textbox" name="clazzid" /></td>
	    		</tr>
	    		<tr>
	    			<td>学号</td>
	    			<td><input id="add_sno"  class="easyui-textbox" style="width: 200px; height: 30px;" type="text" name="sno" data-options="required:true, validType:'repeat', missingMessage:'请输入学号呦(✪ω✪)~'" /></td>
	    		</tr>
	    		<tr>
	    			<td>姓名</td>
	    			<td><input id="add_name" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="name" data-options="required:true, missingMessage:'请填写姓名呦(✪ω✪)~'" /></td>
	    		</tr>
	    		<tr>
	    			<td>密码</td>
	    			<td><input id="add_password" style="width: 200px; height: 30px;" class="easyui-textbox" type="password" name="password" data-options="required:true, missingMessage:'请填写密码呦(✪ω✪)~'" /></td>
	    		</tr>
	    		<tr>
	    			<td>性别</td>
	    			<td><select id="add_sex" class="easyui-combobox" data-options="editable: false, panelHeight: 50, width: 60, height: 30" name="sex"><option value="男">男</option><option value="女">女</option></select></td>
	    		</tr>
	    		<tr>
	    			<td>邮箱</td>
	    			<td><input id="add_email" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="email" validType="email"  data-options="required:true, missingMessage:'请填写邮箱地址哟(✪ω✪)~'"  /></td>
	    		</tr>
	    		<tr>
	    			<td>电话</td>
	    			<td><input id="add_phone" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="phone" validType="mobile"  data-options="required:true, missingMessage:'请填写联系电话哟(✪ω✪)~'" /></td>
	    		</tr>
	    	</table>
	    </form>
	</div>
	
	<!-- 修改信息窗口 -->
	<div id="editDialog" style="padding: 10px">
		<div style="float: right; margin: 20px 20px 0 0; width: 200px; border: 1px solid #EBF3FF">
	    	<img id="edit_photo" alt="照片" style="max-width: 200px; max-height: 400px;" title="照片" src="" />
	    	<!-- 设置上传图片按钮 -->
	    	<form id="uploadForm" method="post" enctype="multipart/form-data" action="PhotoServlet?method=setPhoto" target="photo_target">
	    		<!-- 指定被修改头像的学生ID -->
    			<input type="hidden" id="edit_photo_id" name="sid"/>
		    	<input class="easyui-filebox" name="photo" data-options="prompt:'选择照片'" style="width:200px;">
		    	<input id="uploadBtn" onClick="uploadPhoto()" class="easyui-linkbutton" style="width: 50px; height: 24px;" type="button" value="上传"/>
		    </form>
	    </div>   
    	<form id="editForm" method="post">
    		<!-- 指定被修改信息的学生ID -->
    		<input type="hidden" id="edit_id" name="id"/>
	    	<table cellpadding="8" >
	    		<tr>
	    			<td>学号</td>
	    			<!-- 设置为只读 -->
	    			<td><input id="edit_sno" data-options="readonly: true" class="easyui-textbox" style="width: 200px; height: 30px;" type="text" name="sno" data-options="required:true, validType:'repeat', missingMessage:'请输入学号哟 ~'" /></td>
	    		</tr>
	    		<tr>
	    			<td>姓名</td>
	    			<td><input id="edit_name" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="name" data-options="required:true, missingMessage:'请填写姓名'" /></td>
	    		</tr>
	    		<tr>
	    			<td>性别</td>
	    			<td><select id="edit_sex" class="easyui-combobox" data-options="editable: false, panelHeight: 50, width: 60, height: 30" name="sex"><option value="男">男</option><option value="女">女</option></select></td>
	    		</tr>
	    		<tr>
	    			<td>电话</td>
	    			<td><input id="edit_phone" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="phone" validType="mobile" data-options="required:true, missingMessage:'请填写联系电话'" /></td>
	    		</tr>
	    		<tr>
	    			<td>邮箱</td>
	    			<td><input id="edit_email" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="email" validType="email" data-options="required:true, missingMessage:'请填写邮箱地址'" /></td>
	    		</tr>
	    		<tr>
	    			<td>班级</td>
	    			<td><input id="edit_clazzList" style="width: 200px; height: 30px;" class="easyui-textbox" name="clazzid" data-options="required:true, missingMessage:'请选择所属班级哟 ~'" /></td>
	    		</tr>
	    	</table>
	    </form>
	</div>
	
	<!-- 提交表单处理 -->
	<iframe id="photo_target" name="photo_target"></iframe>    
	
</body>
</html>