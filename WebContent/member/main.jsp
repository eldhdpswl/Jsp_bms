<%@page import="bms.guest.vo.guestCartVO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="setting.jsp" %>

<!-- <link href='http://fonts.googleapis.com/css?family=Source+Sans+Pro:200,300,400,600' rel='stylesheet' type='text/css' /> -->
<link href="/BMS_project/style/style.css" rel="stylesheet" type="text/css" media="screen" />

<html>
<head>
<style type="text/css">
	ul li{
		list-style:none;
	}
</style>
</head>
<body>

<div id="wrapper">
	<div id="header-wrapper">
		<div id="header">
			<!-- <div id="logo">
				<h1><a href="#">BMS</a></h1>
				<p>Design by <a href="http://templated.co" rel="nofollow">TEMPLATED</a></p>
				<p>BMS_PROJECT</p>
			</div> -->
			<%@ include file ="logo.jsp" %>
		</div>
	</div>
	<!-- end #header -->
	<div id="menu">
		<!-- <ul>
			<li class="current_page_item"><a href="#">Home</a></li>
			<li><a href="main.do">Home</a></li>
			<li><a href="login.do">Login</a></li>
			<li><a href="#">Book</a></li>
			<li><a href="#">장바구니</a></li>
			<li><a href="#">마이페이지</a></li>
			<li><a href="#">게시판</a></li>
		</ul> -->
		<%-- <%@ include file="main_menu.jsp" %> --%>
		<c:if test="${sessionScope.memId == null}">
			<%@ include file="main_menu.jsp" %>
		</c:if>
		
		<c:if test="${sessionScope.memId != null}">
			<%@ include file="main_menu_connect.jsp" %>
		</c:if>
		
		<%-- <c:if test="${sessionScope.memId == host}">
			<%@ include file="hostMain_menu.jsp" %>
		
		</c:if> --%>
	</div>
	<!-- end #menu -->
	<div id="page">
		<div id="page-bgtop">
			<div id="page-bgbtm">
				<div id="content" >
					<div class="post">
						<table>
							<h2 class="title"><a href="#">신권소개</a></h2>
							<c:if test="${cnt > 0}">
						
						
						<%
						ArrayList<guestCartVO> dtos=(ArrayList<guestCartVO>)request.getAttribute("dtos");
						if(dtos.size()>3){
							
							%>
							<tr>
							<%
								for(int i=0;i<3;i++){
								%>
								<td>
									<ul>
										<li><img src="<%=request.getServletContext().getContextPath()%>/host/images/<%=dtos.get(i).getKimg()%>" width="100%" height="150px"></li>
										<li><a href="cartContent.mem?bookNum=<%=dtos.get(i).getBookNum()%>"><%=dtos.get(i).getBookName()%></a></li>
										<li><%=dtos.get(i).getAuthor()%></li>
									</ul>
								</td>							
								<%
								}
							%>
							</tr>
							<%
							%>
							<tr>
							<%
								for(int i=3;i<6;i++){
								%>
								<td>
									<ul>
										<li><img src="<%=request.getServletContext().getContextPath()%>/host/images/<%=dtos.get(i).getKimg()%>" width="100%" height="150px"></li>
										<li><a href="cartContent.mem?bookNum=<%=dtos.get(i).getBookNum()%>"><%=dtos.get(i).getBookName()%></a></li>
										<li><%=dtos.get(i).getAuthor()%></li>
									</ul>
								</td>							
								<%
								}
							%>
							</tr>
							<%
							%>
							<tr>
							<%
								for(int i=5;i<8;i++){
								%>
								<td>
									<ul>
										<li><img src="<%=request.getServletContext().getContextPath()%>/host/images/<%=dtos.get(i).getKimg()%>" width="100%" height="150px"></li>
										<li><a href="cartContent.mem?bookNum=<%=dtos.get(i).getBookNum()%>"><%=dtos.get(i).getBookName()%></a></li>
										<li><%=dtos.get(i).getAuthor()%></li>
									</ul>
								</td>							
								<%
								}
							%>
							</tr>
							<%
						}
						%>
					
						
					
						
					</c:if>
					
					<!-- 게시글 없으면 -->
					<c:if test="${cnt == 0}">
						<tr>
							<td colspan="10" align="center">
								게시글이 없습니다. 글을 작성해주세요.!!
							</td>
						</tr>
					</c:if>
						</table>
					</div>
					
					<!-- <div style="clear: both;">&nbsp;</div>  -->
				</div>
				<!-- end #content -->
				<div id="sidebar">
					<ul>
						<div>
							<c:if test="${sessionScope.memId == null}">
							
							</c:if>
							<c:if test="${sessionScope.memId != null}">
								<span>${sessionScope.memId}</span>님 안녕하세요!!
							</c:if>
						</div>
						<li>
							<h2>도서관련검색</h2>
							<p><input class="search" type="text"><button type="submit" name="검색">검색</button></p>
						</li>
						<!-- <li>
							<h2>Categories</h2>
							<ul>
								<li><a href="#">Aliquam libero</a></li>
								<li><a href="#">Consectetuer adipiscing elit</a></li>
								<li><a href="#">Metus aliquam pellentesque</a></li>
								<li><a href="#">Suspendisse iaculis mauris</a></li>
								<li><a href="#">Urnanet non molestie semper</a></li>
								<li><a href="#">Proin gravida orci porttitor</a></li>
							</ul>
						</li>
						<li>
							<h2>Blogroll</h2>
							<ul>
								<li><a href="#">Aliquam libero</a></li>
								<li><a href="#">Consectetuer adipiscing elit</a></li>
								<li><a href="#">Metus aliquam pellentesque</a></li>
								<li><a href="#">Suspendisse iaculis mauris</a></li>
								<li><a href="#">Urnanet non molestie semper</a></li>
								<li><a href="#">Proin gravida orci porttitor</a></li>
							</ul>
						</li>
						<li>
							<h2>Archives</h2>
							<ul>
								<li><a href="#">Aliquam libero</a></li>
								<li><a href="#">Consectetuer adipiscing elit</a></li>
								<li><a href="#">Metus aliquam pellentesque</a></li>
								<li><a href="#">Suspendisse iaculis mauris</a></li>
								<li><a href="#">Urnanet non molestie semper</a></li>
								<li><a href="#">Proin gravida orci porttitor</a></li>
							</ul>
						</li> -->
						
					</ul>
				</div>
				<!-- end #sidebar -->
				<div style="clear: both;">&nbsp;</div>
			</div>
		</div>
	</div>
	<!-- end #page --> 
</div>
<div id="footer">
	<%@ include file ="main_footer.jsp" %>
</div>
<!-- end #footer -->
</body>
</html>