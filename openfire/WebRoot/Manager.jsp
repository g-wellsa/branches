<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	<base href="<%=basePath%>">
	<title>My JSP 'Manager.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="CSS/manager.css" rel="stylesheet" type="text/css">
	<script language="javascript">
function show(obj,maxg,obj2){
  if(obj.style.pixelHeight<maxg){
    obj.style.pixelHeight+=maxg/10;
	obj.filters.alpha.opacity+=20;
	obj2.background="image/manager/title_hide.gif";
    if(obj.style.pixelHeight==maxg/10)
	  obj.style.display='block';
	myObj=obj;
	mymaxg=maxg;
	myObj2=obj2;
	setTimeout('show(myObj,mymaxg,myObj2)','5');
  }
}
function hide(obj,maxg,obj2){
  if(obj.style.pixelHeight>0){
    if(obj.style.pixelHeight==maxg/5)
	  obj.style.display='none';
    obj.style.pixelHeight-=maxg/5;
	obj.filters.alpha.opacity-=10;
	obj2.background="image/manager/title_show.gif";
	myObj=obj;
	mymaxg=maxg
	myObj2=obj2;
	setTimeout('hide(myObj,mymaxg,myObj2)','5');
  }
  else if(whichContinue) whichContinue.click();
}

function chang(obj,maxg,obj2){
  if(obj.style.pixelHeight){
    hide(obj,maxg,obj2);
	nopen='';
	whichcontinue='';
  }else if(nopen){
	  whichContinue=obj2;
      nopen.click();
	}else{
	  show(obj,maxg,obj2);
	  nopen=obj2;
	  whichContinue='';
	}
}
    </script>
	<STYLE type=text/css>
BODY {
	 MARGIN: 0px; FONT: 9pt 宋体
}
TABLE {
	BORDER-RIGHT: 0px; BORDER-TOP: 0px; BORDER-LEFT: 0px; BORDER-BOTTOM: 0px
}
TD {
	FONT: 12px 宋体
}
img {
	BORDER-RIGHT: 0px; BORDER-TOP: 0px; VERTICAL-ALIGN: bottom; BORDER-LEFT: 0px; BORDER-BOTTOM: 0px
}
A {
	FONT: 12px 宋体; COLOR: #215dc6; TEXT-DECORATION: none
}
A:hover {
	COLOR: #428eff
}
.sec_menu {
	OVERFLOW: hidden;
}
.list_title {
	
}
.list_title SPAN {
	FONT-WEIGHT: bold;
	LEFT: 8px;
	COLOR: #215dc6;
	POSITION: relative;
	TOP: 2px;
	visibility: visible;
}
.list_title2 {
	
}
.list_title2 SPAN {
	FONT-WEIGHT: bold; LEFT: 8px; COLOR: #428eff; POSITION: relative; TOP: 2px
}
.style1 {
	font-size: 14pt;
	font-weight: bold;
	color: #467ACB;
}
.style2 {
	font-size: 15px;
	font-weight: bold;
}
.style3 {font-size: 14px}
.style4 {font-size: 13px}
.style6 {font-size: 14pt}
    .style9 {font-size: 12pt}
    .style10 {
	font-size: 12px;
	color: #467ACB;
}
    .style13 {font-size: 12pt; color: #467ACB; }
    </STYLE>
	<script language="JavaScript" type="text/JavaScript">
<!--
function MM_reloadPage(init) {  //reloads the window if Nav4 resized
  if (init==true) with (navigator) {if ((appName=="Netscape")&&(parseInt(appVersion)==4)) {
    document.MM_pgW=innerWidth; document.MM_pgH=innerHeight; onresize=MM_reloadPage; }}
  else if (innerWidth!=document.MM_pgW || innerHeight!=document.MM_pgH) location.reload();
}
MM_reloadPage(true);
//-->
    </script>
    <script type="text/javascript">
    /*function loadlogin()
    {
    window.open('ChangePwd.jsp','changpassword','width=450,height=300');
    }*/
    </script>
	</head>

	<body>
		<div id="buttomdiv">
			<div id="managerhead">
			<div id="taobaoheadpic"><img src="image/Head/2.bmp" width="240" height="31"></div>
			</div>
			<div align="left">
			  <div id="managertop">&nbsp;&nbsp;&nbsp;<span class="style6"><a href="">[淘宝网后台管理]</a>   <a href="">商品管理</a> <span class="style10">&gt;&gt;</span>  <a href="">用户管理</a> <span class="style10">&gt;&gt;</span>  <a href="">论坛管理</a> <span class="style10">&gt;&gt;</span>  <a href="">订单管理</a> <span class="style10">&gt;&gt;</span> <a href="">类别管理</a> </span><%
 	String username = (String) session.getAttribute("loginusername");
 	if (username == null) {
 		out.print("你好，");
 	} else {
 %> <%=username%>管理员, <%
 }
 %>欢迎您来淘宝后台管理！</div>
		  </div>
			<div id="managerfront">
				<div id="managerfrontleft">
	
      <table cellSpacing="0" cellPadding="0" width=158 align=center>
        
        <tr style="CURSOR: hand">
          <td vAlign=bottom height=31><img height=30 
            src="image/manager/title.gif" width=158></td>
        </tr></table>
      <table cellSpacing="0" cellPadding="0" width=158 align=center>
        
        <tr style="CURSOR: hand">
            <td class=list_title id=list1 
          onmouseover="this.typename='list_title2';" 
          onclick=chang(menu1,50,list1); 
          onmouseout="this.typename='list_title';" 
          background="image/manager/title_show.gif" 
            height=25><span>淘宝网管理</span> </td>
        </tr>
        <tr>
            <td align="center" valign="middle"> 
              <div class=sec_menu id=menu1 
            style="DISPLAY: none; FILTER: alpha(Opacity=0); width: 158px; height: 0px">
                <table  cellSpacing="0" 
            cellPadding="0" width=152 align="center"  background="image/manager/bg.gif" style="padding-left:5px">
                  
                    <tr> 
                      <td height=25>
                       <%
 	              if (username == null) {
 	                out.print("更改初始信息");
 	               } else {%>
 	               <a href="ManagerSystem.jsp" target="main">更改初始信息</a>
 	              <%}%>
                      </td>
                    </tr>
           
                    <tr> 
                      <td height=25>
                      <%
 	              if (username == null) {
 	                out.print("查看服务器信息");
 	               } else {%>
 	               <a href="ManagerSystem.jsp" target="main">查看服务器信息</a>
 	              <%}%>
                      
                      </td>
                    </tr>
                  
                  
                </table>
              </div></td></tr></table>


<table cellSpacing="0" cellPadding="0" width=158 align=center>
        
        <tr style="CURSOR: hand">
            <td class=list_title id=list2 
          onmouseover="this.typename='list_title2';" 
          onclick=chang(menu2,160,list2); 
          onmouseout="this.typename='list_title';" 
          background="image/manager/title_show.gif" 
            height=25><span>论坛信息管理</span> </td>
      </tr>
        <tr>
            <td align="center"> 
              <div class=sec_menu id=menu2 
            style="DISPLAY: none; FILTER: alpha(Opacity=0); width: 158px; height: 0px">
                <table  cellSpacing=2 
            cellPadding="0" width=152 align="center"  background="image/manager/bg.gif" style="padding-left:5px">
                  <tr> 
                    <td height=25>
                    <%
 	              if (username == null) {
 	                out.print("主贴管理");
 	               } else {%>
                    <a href="MainMasterManager.jsp" target="main">主贴管理</a>
                    <%} %>
                    </td>
                  </tr>
                  
                    <tr> 
                      <td height=25>
                      <%
 	              if (username == null) {
 	                out.print("从贴管理");
 	               } else {%>
                      <a href="FollowMasterManager.jsp" target="main">从贴管理</a>
                      <%} %>
                      </td>
                    </tr>
                   
					<tr> 
                      <td height=25>
                      <%
 	              if (username == null) {
 	                out.print("主贴操纵");
 	               } else {%>
                      <a href="MainMasterControl.jsp" target="main">主贴操纵</a>
                      <%} %>
                      </td>
                    </tr>
					<tr> 
                      <td height=25>
                      <%
 	              if (username == null) {
 	                out.print("从贴操纵");
 	               } else {%>
                      <a href="FollowMasterManager.jsp" target="main">从贴操纵</a>
                      <%} %>
                      </td>
                    </tr>
					<tr> 
                      <td height=25>
                      <%
 	              if (username == null) {
 	                out.print("模块管理");
 	               } else {%>
                      <a href="ModeManager.jsp" target="main">模块管理</a>
                      <%} %>
                      </td>
                    </tr>
					<tr> 
                      <td height=25>
                      <%
 	              if (username == null) {
 	                out.print("其他管理");
 	               } else {%>
                      <a href="ModeManager.jsp" target="main">其他管理</a>
                      <%} %>
                      </td>
                    </tr>
                  
                </table>
              </div></td></tr></table>
       <table cellSpacing="0" cellPadding="0" width=158 align=center>
         
           <tr style="CURSOR: hand">
             <td class=list_title id=list10 
          onmouseover="this.typename='list_title2';" 
          onclick=chang(menu10,40,list10); 
          onmouseout="this.typename='list_title';" 
          background="image/manager/title_show.gif" 
            height=25><span>用户信息管理</span> </td>
           </tr>
           <tr>
             <td valign="middle">
               <div class=sec_menu id=menu10
            style="DISPLAY: none; FILTER: alpha(Opacity=0); width: 158px; height: 0px">
                 <table width=152 align=center
            cellPadding="0"  cellSpacing=2  background="image/manager/bg.gif" style="padding-left:5px">                   
                     <tr>
                       <td height=40>
                       <%
 	              if (username == null) {
 	                out.print("用户管理");
 	               } else {%>
                       <a href="ManagerUser.jsp" target="main">用户管理</a>
                       <%} %>
                       </td>
                     </tr>
                 </table>
             </div></td>
           </tr>
         
       </table>
<table cellSpacing="0" cellPadding="0" width=158 align=center>
         
           <tr style="CURSOR: hand">
             <td class=list_title id=list4 
          onmouseover="this.typename='list_title2';" 
          onclick=chang(menu4,50,list4); 
          onmouseout="this.typename='list_title';" 
          background="image/manager/title_show.gif" 
            height=25><span>商品信息管理</span> </td>
           </tr>
           <tr>
             <td valign="middle">
               <div class=sec_menu id=menu4 
            style="DISPLAY: none; FILTER: alpha(Opacity=0); width: 158px; height: 0px">
                 <table  cellSpacing=2 
            cellPadding="0" width=152 align="center" background="image/manager/bg.gif" style="padding-left:5px">
                    <tr>
                       <td height=25>
                       <%
 	              if (username == null) {
 	                out.print("商品操纵");
 	               } else {%>
                       <a href="Shopcontrol.jsp" target="main">商品操纵</a>
                       <%} %>
                       </td>
                     </tr>
                     <tr> 
                      <td height=25>
                      <%
 	              if (username == null) {
 	                out.print("商品管理");
 	               } else {%>
                      <a href="ShopManager.jsp" target="main">商品管理</a>
                      <%} %>
                      </td>
                    </tr>    
                   
                 </table>
             </div></td>
           </tr>
     </table>
       <script language="javascript">
		  var nopen="";
		  var whichContinue="";
      </script>
      <table width=158 align=center cellPadding="0" cellSpacing="0">
        
          <tr style="CURSOR: hand"> 
            <td class=list_title id=list7 
          onmouseover="this.typename='list_title2';" 
          onclick=chang(menu7,80,list7); 
          onmouseout="this.typename='list_title';" 
          background="image/manager/title_show.gif" 
            height=25><span>订单信息管理</span> </td>
          </tr>
          <tr> 
            <td valign="middle"> <div class=sec_menu id=menu7 
            style="DISPLAY: none;  FILTER: alpha(Opacity=0); width: 158px; height: 0px"> 
                <table width=152 align=center 
            cellPadding="0"  cellSpacing=2  background="image/manager/bg.gif" style="padding-left:5px">

                    <tr> 
                      <td height=25>
                      <%
 	              if (username == null) {
 	                out.print("订单管理");
 	               } else {%>
                      <a href="servlet/OrderManager1Servlet" target="main">订单管理</a>
                      <%} %>
                      </td>
                    </tr>
                    <tr>
                      <td height=25>
                      <%
 	              if (username == null) {
 	                out.print("已确认订单");
 	               } else {%>
                      <a href="Orderok.jsp" target="main">已确认订单</a>
                      <%} %>
                      </td>
                    </tr>
                    <tr>
                      <td height=25>
                      <%
 	              if (username == null) {
 	                out.print("定单查询");
 	               } else {%>
                      <a href="ManagerSelectOrder.jsp" target="main">定单查询</a>
                      <%} %>
                      </td>
                    </tr>
                  
                </table>
              </div></td>
          </tr>
        
      </table>

   
       

      <table cellSpacing="0" cellPadding="0" width=152 align=center>
        
          <tr style="CURSOR: hand"> 
            <td width="158" 
            height=25 
          background="image/manager/title_show.gif" class=list_title id=list57 
          onclick=chang(menu57,30,list57); 
          onmouseover="this.typename='list_title2';" 
          onmouseout="this.typename='list_title';"><span>商品类别管理</span> </td>
          </tr>
          <tr> 
            <td valign="middle"> <div class=sec_menu id=menu57 
            style="DISPLAY: none; FILTER: alpha(Opacity=0); width: 158px; height: 0px"> 
                <table width=152 align=center 
            cellPadding="0"  cellSpacing="0" background="image/manager/bg.gif">
                  
                    <tr> 
                      <td height="30" style="padding-left:5px">
                      <%
 	              if (username == null) {
 	                out.print("类别维护");
 	               } else {%>
                      <a href="TypeManager.jsp" target="main">类别维护</a>
                      <%} %>
                      </td>
                    </tr>
                  
                </table>
              </div></td>
          </tr>
	     <tr>
      <td height="40" valign="middle">              <div class=sec_menu id=menu8
            style="FILTER: alpha(Opacity=100); width: 158px; height:40px"><img src="image/manager/bottom.gif" width="158" height="30"></div></td></tr></table>

				</div>
			  <div id="managerfrontright">
			  
				   <iframe frameborder="0"  name="main" scrolling="no" src="ManagerLogin.jsp" style="HEIGHT: 100%; VISIBILITY: inherit; WIDTH: 100%; Z-INDEX: 1"></iframe>
			  </div>
			</div>
			<div id="managerbuttom">
			    <UL>
		  <LI><A href="">关于淘宝</A> </LI>
		  <LI><A href="">合作伙伴</A> </LI>
		  <LI><A href="">帮助中心</A></LI>
		  <LI><A href="">诚征英才</A> </LI>
		  <LI><A href="">联系我们</A> </LI>
		  <LI><A href="">网站地图</A> </LI>
		  <LI><A href="">热门商品</A> </LI>
		  <LI><A href="">热门品牌</A> </LI>
		  <LI><A href="">版权说明 </A></LI>
		  <LI><A href="">淘客推广</A> </LI>
  </UL>
<DIV id="managerbuttomfont">全球阿里巴巴 - 阿里巴巴网络：<A href="">中国站</A> 
    <A href="">国际站</A>
    <A href="">日文站</A> | <A 
href="">淘宝站</A> | <A 
href="">支付宝</A> | <A 
href="">中国雅虎</A> | <A 
href="">雅虎口碑网</A> | <A 
href="">阿里软件</A> | <A 
href="">阿里妈妈</A> </DIV>

<DIV id="managerbuttomfont2">Copyright 2003-2008, 版权所有 TAOBAO.COM  </DIV>
			</div>
		</div>
	</body>
</html>
