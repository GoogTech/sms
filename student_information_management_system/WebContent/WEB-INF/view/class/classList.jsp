<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
	<title>班级信息管理</title>
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
	        title:'班级列表', 
	        iconCls:'icon-more',//图标 
	        border: true, 
	        collapsible: false,//是否可折叠 
	        fit: true,//自动大小 
	        method: "post",
	        url:"ClazzManagementServlet?method=getClassList&t="+new Date().getTime(),
	        idField:'id', 
	        singleSelect: true,//是否单选 
	        pagination: true,//分页控件 
	        rownumbers: true,//行号 
	        sortName: 'id',
	        sortOrder: 'DESC', 
	        remoteSort: false,
	        columns: [[  
				{field:'chk',checkbox: true,width:50},//单选框
 		        {field:'id',title:'ID',width:50, sortable: true},
 		        {field:'name',title:'班级名称',width:200},
 		        {field:'introduce',title:'班级介绍',width:600, 
 		        },
	 		]], 
	        toolbar: "#toolbar"
	    }); 
		
	    //设置分页控件 
	    var p = $('#dataList').datagrid('getPager'); 
	    $(p).pagination({ 
	        pageSize: 10,//每页显示的记录条数,默认为10 
	        pageList: [10,20,30,50,100],//可以设置每页记录条数的列表 
	        beforePageText: '第',//页数文本框前显示的汉字
	        afterPageText: '页    共 {pages} 页', 
	        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录', 
	    });
	    
	    //设置工具类按钮
	    $("#add").click(function(){
	    	$("#addDialog").dialog("open");
	    });
	    
	    //删除
	    $("#delete").click(function(){
	    	var selectRow = $("#dataList").datagrid("getSelected");
        	if(selectRow == null){
            	$.messager.alert("消息提醒", "请先选择想要删除的班级哟 (o°ω°o) !", "warning");
            } else{
            	var classid = selectRow.id;
            	$.messager.confirm("消息提醒", "仅删除该班级( 不包含其中学生与教师信息哟 ! ) 确认继续嘛亲 ?", function(r){
            		if(r){
            			$.ajax({
							type: "post",
							url: "ClazzManagementServlet?method=deleteClass",
							data: {classid: classid},//班级ID
							success: function(msg){
								if(msg == "success"){
									$.messager.alert("消息提醒","删除成功啦 ! ヾ(◍°∇°◍)ﾉﾞ ~","info");
									//刷新表格 
									$("#dataList").datagrid("reload");
								} else{
									$.messager.alert("消息提醒","删除失败 (ŎдŎ；) !","warning");
									return;
								}
							}
						});
            		}
            	});
            }
	    });	    
	  	
	  	//设置添加班级信息窗口
	    $("#addDialog").dialog({
	    	title: "添加班级",
	    	width: 500,
	    	height: 400,
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
					iconCls:'icon-add',
					handler:function(){
						var validate = $("#addForm").form("validate");
						if(!validate){
							$.messager.alert("消息提醒","请检查你输入的班级信息哟亲 (o°ω°o) !","warning");
							return;
						} else{
							$.ajax({
								type: "post",
								url: "ClazzManagementServlet?method=addClass",
								data: $("#addForm").serialize(),
								success: function(msg){
									if(msg == "success"){
										$.messager.alert("消息提醒","添加成功 ! ヾ(◍°∇°◍)ﾉﾞ ~","info");
										//关闭窗口
										$("#addDialog").dialog("close");
										//清空原表格数据
										$("#add_name").textbox('setValue', "");
										$("#add_Introduce").textbox('setValue',"");
										//重新刷新页面数据
							  			$('#dataList').datagrid("reload");
									} else{
										$.messager.alert("消息提醒","添加失败  (ŎдŎ；) !","warning");
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
						$("#add_name").textbox('setValue', "");
						$("#add_Introduce").textbox("setValue","");
					}
				},
			]
	    });

	  	
	    //设置编辑班级信息窗口
	    $("#editDialog").dialog({
	    	title: "修改班级信息",
	    	width: 500,
	    	height: 400,
	    	iconCls: "icon-add",
	    	modal: true,
	    	collapsible: false,
	    	minimizable: false,
	    	maximizable: false,
	    	draggable: true,
	    	closed: true,
	    	buttons: [
	    		{
					text:'确定修改',
					plain: true,
					iconCls:'icon-add',
					handler:function(){
						var validate = $("#editForm").form("validate");
						if(!validate){
							$.messager.alert("消息提醒","请检查你输入的班级信息哟亲 (o°ω°o) !","warning");
							return;
						} else{
							//var gradeid = $("#add_gradeList").combobox("getValue");
							$.ajax({
								type: "post",
								url: "ClazzManagementServlet?method=editClass",
								data: $("#editForm").serialize(),
								success: function(msg){
									if(msg == "success"){
										$.messager.alert("消息提醒","修改成功 ! ヾ(◍°∇°◍)ﾉﾞ ~","info");
										//关闭窗口
										$("#editDialog").dialog("close");
										//清空原表格数据
										$("#edit_name").textbox('setValue', "");
										$("#edit_Introduce").textbox('setValue',"");//功能失效 ! ?
										//重新刷新页面数据
							  			$('#dataList').datagrid("reload");
										
									} else{
										$.messager.alert("消息提醒","修改失败  (ŎдŎ；) !","warning");
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
						$("#edit_name").textbox('setValue', "");
						$("#edit_Introduce").textbox('setValue',"");
					}
				},
			],
			
			//打开编辑窗口前的初始化工作
			onBeforeOpen: function(){
				var selectRow = $("#dataList").datagrid("getSelected");
				//设置被修该信息的班级ID
				$("#edit_id").val(selectRow.id);
				//设置文本框中的初始化值
				$("#edit_name").textbox('setValue', selectRow.name);
				$("#edit_Introduce").textbox('setValue', selectRow.introduce);			
			}
	 
	    });
	  	
	  	//搜索按钮监听事件
	  	$("#search").click(function(){
	  		$('#dataList').datagrid('load',{
	  			className: $('#className').val()  
	  		});
	  	});
	  	
	    //修改按钮监听事件
	  	$("#modify").click(function(){ 
	  		var selectRow = $("#dataList").datagrid("getSelected");
        	if(selectRow == null){
            	$.messager.alert("消息提醒", "请先选择想要修改的班级哟 (o°ω°o) !", "warning");
            	return;
            } 
        	$("#editDialog").dialog("open");//打开编辑窗口
	  	});
	  
	});
	</script>
</head>
<body>
	<!-- 数据列表 -->
	<table id="dataList" cellspacing="0" cellpadding="0"> </table> 
	
	<!-- 工具栏 -->
	<div id="toolbar">
		<div style="float: left;"><a id="add" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">添加</a></div>
			<div style="float: left;" class="datagrid-btn-separator"></div>
		<div style="float: left; margin-right: 10px;"><a id="modify" href="javascript:;"  class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">修改</a></div>
			<div style="float: left;" class="datagrid-btn-separator"></div>
		<div style="float: left; margin-right: 10px;"><a id="delete" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-some-delete',plain:true">删除</a></div>
			<div style="float: left;" class="datagrid-btn-separator"></div>
		<div style="margin: 0 10px 0 15px">班级名称  <input id="className" class="easyui-textbox" name="className" />
			<!-- 搜索按钮 -->
			<a id="search" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true">搜索</a>
		</div>
	</div>
	
	<!-- 添加信息窗口 -->
	<div id="addDialog" style="padding: 10px">  
    	<form id="addForm" method="post">
	    	<table cellpadding="8" >
	    		<tr>
	    			<td>班级名称</td>
	    			<td><input id="add_name" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="name"  data-options="required:true, missingMessage:'不能为空呦 (✪ω✪) ~'" /></td>
	    		</tr>
	    		<tr>
	    			<td>班级介绍</td>
	    			<td><input id="add_Introduce" style="width: 200px; height: 50px;" class="easyui-textbox" type="text" name="introduce" data-options="multiline:true,required:true, missingMessage:'不能为空呦 (✪ω✪) ~'" ></input></td>
	    		</tr>
	    	</table>
	    </form>
	</div>
	
	<!-- 编辑信息窗口 -->
	<div id="editDialog" style="padding: 10px">  
    	<form id="editForm" method="post">
    		<!-- 指定被修改信息的班级ID -->
    		<input type="hidden" id="edit_id" name="id"/>
	    	<table cellpadding="8" >
	    		<tr>
	    			<td>班级名称</td>
	    			<td><input id="edit_name" style="width: 200px; height: 30px;" class="easyui-textbox" type="text" name="name"  data-options="required:true, missingMessage:'不能为空呦 (✪ω✪) ~'" /></td>
	    		</tr>
	    		<tr>
	    			<td>班级介绍</td>
	    			<td><input id="edit_Introduce" style="width: 200px; height: 50px;" class="easyui-textbox" type="text" name="introduce"  data-options="multiline:true,required:true, missingMessage:'不能为空呦 (✪ω✪) ~'"></input></td>
	    		</tr>
	    	</table>
	    </form>
	</div>
	
</body>
</html>