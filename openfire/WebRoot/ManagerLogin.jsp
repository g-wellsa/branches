<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'ManagerLogin.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

    <link href="CSS/manager.css" rel="stylesheet" type="text/css">
    <style type="text/css">
<!--
.style1 {font-size: 14px}
-->
    </style>
    <script type="text/javascript">
       function managersubmit()
       {
          document.managerfrmlogin.submit();
       }
    </script>
  </head>
  
  <body>
   <div id="managerfrontrightnum2buttom">
    <div id="managerfrontrightnum2">
   
   <table width="273" height="207" border="0" cellpadding="0" cellspacing="0">
       <tr>
         <td rowspan="2" width="77" height="260"><img src="image/forumindex/login_01.gif" width="77" height="260"></td>
         <td rowspan="2" width="74" height="260"><img src="image/forumindex/login_02.gif" width="74" height="260"></td>
         <td rowspan="2" width="85" height="260"><img src="image/forumindex/login_03.gif" width="85" height="260"></td>
         <td width="354" height="86"><img src="image/forumindex/login_04.gif" width="354" height="86"></td>
       </tr>
       <tr>
         <td align="center" width="354" height="174" valign="middle" style="background-image:url(image/forumindex/login_05.gif) ">
		  <form action="servlet/ManagerLoginServlet" method="post" name="managerfrmlogin">
		 <table width="258" height="115" border="0" cellpadding="0" cellspacing="0">
           <tr align="center">
             <td colspan="3"><span class="style1">☆淘宝网后台登陆☆</span></td>
           </tr>
           <tr>
             <td width="74" align="center"><span  style="font-size: 12px;">用户名：</span></td>
             <td colspan="2"><input type="text" name="adminname"  style=" BORDER-BOTTOM: #679FC2 1px solid; BORDER-LEFT: #ffffff 1px solid; BORDER-RIGHT: #ffffff 1px solid; BORDER-TOP: #ffffff 1px solid; FONT-SIZE: 9pt"></td>
           </tr>
           <tr>
             <td align="center"><span  style="font-size: 12px;">密&nbsp;&nbsp;码：</span></td>
             <td colspan="2"><input type="password" name="adminpassword"  style=" BORDER-BOTTOM: #679FC2 1px solid; BORDER-LEFT: #ffffff 1px solid; BORDER-RIGHT: #ffffff 1px solid; BORDER-TOP: #ffffff 1px solid; FONT-SIZE: 9pt"></td>
           </tr>
           <tr>
             <td align="center"><span style="font-size: 12px;">验证码：</span></td>
             <td width="75"><input type="text" size=8 name="adminyanzhengma"  style=" BORDER-BOTTOM: #679FC2 1px solid; BORDER-LEFT: #ffffff 1px solid; BORDER-RIGHT: #ffffff 1px solid; BORDER-TOP: #ffffff 1px solid; FONT-SIZE: 9pt"></td>
           <td width="109"><a href="" onclick="javascript:window.history.go(0);" title="看不清,换一张?"><img src="http://localhost:8080/taobao/servlet/GetRandomImageServlet" border="0"></a></td>
           </tr>
		   <tr><td colspan="3" height="9"></td>
		   </tr>
           <tr>
             <td height="30"></td>
           <td height="30" colspan="2">
			 <img src="image/forumindex/button_dl.gif" style="CURSOR: hand" onclick="javascript:managersubmit()">&nbsp;&nbsp;&nbsp;&nbsp;
             <img style="CURSOR: hand" onClick=reset() src="image/forumindex/button_cx.gif"></td>
           </tr>
         </table>
		  </form></td>
       </tr>
     </table>
	 </div>
   </div>
  </body>
</html>
