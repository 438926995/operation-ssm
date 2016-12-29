供应链金融运营后台
========
JDK
      JDK1.8u60 

开发IDE
      Eclipse Mars 

版本控制
      SVN 

后台框架
       MVC框架: Spring MVC、Spring 4.1.7
       ORM; Spring Jdbc  4.1.7 -> Mybatis 3.3.0 
       安全框架：Spring Security 4.0.2
       日志记录：SLF4J + Logback 

DB
       MySQL 5.6 

缓存
       Redis

事务
       暂使用@Transaction注解

构建工具
       Apache Maven 3.3.3

前端库
       jQuery 2.1.4 + Bootstrap 3.3.5 + AdminLTE 2.3.0 

图标字体库
	   Font Awesome 4.4 https://github.com/FortAwesome/Font-Awesome
	   
开源图标集合
	   ionicons 2.0.1 http://ionicons.com/

表单美化插件	   
	   iCheck http://plugins.jquery.com/icheck/ （登录页面的RememberMe复选框）

颜色选择器   
	   Colorpicker: let you add color picker to text field or to any other element.  

双日历日期区间选择插件	   
	   daterangepicker https://github.com/dangrossman/bootstrap-daterangepicker 
	   
JS 插件
      弹出框： remodal 
      前端验证： parsley http://parsleyjs.org/
      日期处理： moment.js http://momentjs.com/

TODO:
1. spring security:
   1) remember me
   2) csrf / xss 
   3) session-management (concurrency-control)
   4) 限制登录次数，防暴力破解
   5) security包发生异常，没有捕捉到。跳转到了空白页。
   6) 异地登录
   
2. 替换验证码库  
3. 富文本框
4. js模块化

   