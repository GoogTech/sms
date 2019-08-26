## :mortar_board::pencil: Student Information Management System

### 项目概述

#### 项目阶段介绍  (:speech_balloon: pause update)
- *第一阶段：+信息管理功能 :white_check_mark:*
- *第二阶段：+成绩管理功能 :x:*

#### 用户权限介绍
- *管理员 : 具有所有管理模块的权限*
- *教师 : 具有学生管理信息模块的所有权限,但在教师信息管理模块中只具有查询并修改个人信息的权限*
- *学生 : 只具有查询并修改个人信息的权限*

*设置权限的核心示例代码如下 :*
```java
// 用户权限设置: 如果当前用户类型为教师,则将其权限设置为仅能查询个人信息
if (userType == 3) {
    TeacherInfo currentTeacherInfo = (TeacherInfo) request.getSession().getAttribute("userInfo");
	teacherInfo.setId(currentTeacherInfo.getId());
}

// 获取分页后的教师列表信息
List<TeacherInfo> teacherList = teacherDao.getTeacherList(teacherInfo, new Paging(currentPage, pageSize));
```


### 系统截屏
- *登录页面*

![](https://raw.githubusercontent.com/YUbuntu0109/Student-Information-Management-System/master/demonstration_picture/Student_Information_Management_System01-LoginInterface.PNG)

- *系统主页*

![](https://raw.githubusercontent.com/YUbuntu0109/Student-Information-Management-System/master/demonstration_picture/Student_Information_Management_System01-MainInterface.PNG)

- *学生信息管理页面*

![](https://raw.githubusercontent.com/YUbuntu0109/Student-Information-Management-System/master/demonstration_picture/Student_Information_Management_System01-StudentInfoInterface.PNG)


### 项目结构
```
│
└─student_information_management_system
    │       
    │
    ├─build
    │  └─classes
    │      │  databaseConfig.properties
    │                                                                                       
    │
    ├─database
    │      SMS.sql
    │
    ├─src
    │  │  databaseConfig.properties
    │  │
    │  └─pers
    │      └─huangyuhui
    │          └─sms
    │              ├─dao
    │              │      AdminDao.java
    │              │      BasicDao.java
    │              │      ClazzDao.java
    │              │      StudentDao.java
    │              │      TeacherDao.java
    │              │
    │              ├─filter
    │              │      LoginFilter.java
    │              │
    │              ├─model
    │              │      AdminInfo.java
    │              │      ClazzInfo.java
    │              │      Paging.java
    │              │      StudentInfo.java
    │              │      TeacherInfo.java
    │              │
    │              ├─servlet
    │              │      ClazzManagementServlet.java
    │              │      LoginServlet.java
    │              │      OutVerifiCodeServlet.java
    │              │      PersonalManagementServlet.java
    │              │      PhotoServlet.java
    │              │      StuManagementServlet.java
    │              │      SysMainInterfaceServlet.java
    │              │      TeacherManagementServlet.java
    │              │
    │              └─util
    │                      CreateVerifiCodeImage.java
    │                      DbConfig.java
    │                      DbUtil.java
    │                      StringUtil.java
    │
    └─WebContent
        │  index.jsp
        │  refresh.jsp
        │
        ├─easyui
        │  │
        │  ├─css       
        │  │
        │  ├─js
        │  │     
        │  └─themes
        │      
        │
        ├─h-ui
        │  │
        │  ├─css
        │  │      
        │  ├─images
        │  │
        │  ├─js
        │  │       
        │  ├─lib
        │  │
        │  └─skin
        │     
        │
        ├─META-INF
        │      MANIFEST.MF
        │
        ├─resource
        │  └─image
        │          default_portrait.jpg
        │
        └─WEB-INF
            │  web.xml
            │
            ├─lib
            │      commons-beanutils-1.8.3.jar
            │      commons-collections-3.2.1.jar
            │      commons-fileupload-1.2.1.jar
            │      commons-io-1.4.jar
            │      commons-lang-2.5.jar
            │      commons-logging-1.1.1.jar
            │      ezmorph-1.0.6.jar
            │      FilelLoad.jar
            │      json-lib-2.3-jdk15.jar
            │      jsonplugin-0.34.jar
            │      jstl.jar
            │      mysql-connector-java-8.0.11.jar
            │      standard.jar
            │
            └─view
                │  login.jsp
                │
                ├─class
                │      classList.jsp
                │
                ├─error
                │      404.jsp
                │      500.jsp
                │
                ├─management
                │      personalView.jsp
                │
                ├─student
                │      studentList.jsp
                │
                ├─system
                │      main.jsp
                │      welcome.jsp
                │
                └─teacher
                        teacherList.jsp
```

#### 项目文件说明-数据库文件
```
SMS.sql
```

#### 项目文件说明-数据库配置信息
```
databaseConfig.properties
```

#### 项目文件说明-`H-ui 前端框架`
```
h-ui/
```

#### 项目文件说明-`EasyUI 前端框架`
```
easyui/
```


### 数据库ER图

![](https://raw.githubusercontent.com/YUbuntu0109/Student-Information-Management-System/master/demonstration_picture/sms_er.png)


  
:clock8: *`2019-8-26`回首阅读该项目源码,发现该项目中的代码让我感觉非常臃肿,其可扩张性也很差哟 ! 这毕竟是我第一个`Java web`项目,所以请原谅吧嘿嘿~ 为了让你写出更加优美的代码及更加具有可扩张性的项目,这里我给出了一个简单的参考案例 ：https://github.com/YUbuntu0109/springboot-beginner/tree/refactor-190823 , 及一个可供你参考与学习的小项目 : https://github.com/YUbuntu0109/springboot-shiro*



*:books:更多有趣项目及详细学习笔记请前往我的个人博客哟（づ￣3￣）づ╭❤～ : https://yubuntu0109.github.io/*

*👩‍💻学习笔记已全部开源 : https://github.com/YUbuntu0109/YUbuntu0109.github.io*
 
*:coffee: Look forward to your contribution, if you need any help, please contact me~ QQ : 3083968068*
