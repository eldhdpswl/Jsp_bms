<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="setting.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>  

<script src="${project}script.js"></script>
<!-- <link href='http://fonts.googleapis.com/css?family=Source+Sans+Pro:200,300,400,600' rel='stylesheet' type='text/css' /> --> 
<link href="/BMS_project/style/style.css" rel="stylesheet" type="text/css" media="screen" />
<link href="/BMS_project/style/login.css" rel="stylesheet" type="text/css" media="screen" />

<html>
<body onload="mainFocus();">
<div id="wrapper">
	<div id="header-wrapper">
		<div id="header">
			<%@ include file ="logo.jsp" %>
		</div>
	</div>
	<!-- end #header -->
	<div id="menu">
		<c:if test="${sessionScope.memId == null}">
			<%@ include file="main_menu.jsp" %>
		</c:if>
		<c:if test="${sessionScope.memId != null}">
			<%@ include file="main_menu_connect.jsp" %>
		</c:if>
	</div>
	<!-- end #menu -->
	<div id="page">
		<div id="page-bgtop">
			<div id="page-bgbtm">
				<div width="1100px" align="center">    
					<h2>로그인</h2>
					<hr><br>
					<div>
						<c:if test="${sessionScope.memId == null}">
							<form action="loginPro.mem" method="post" name="mainform" onsubmit="return mainCheck();">
								<table>
									<tr>
										<th colspan="2">
											<%-- <%
												if(cnt == -1){
													out.println("비밀번호가 다릅니다. 다시확인하세요"); /* 로그인 실패 */
												}else if(cnt == 0){
													out.println("존재하지않는 아이디입니다. 다시확인하세요."); /* 비가입 */
												}else if(cnt == 1){
													out.println("회원가입을 축하합니다. 로그인하세요!!"); /* 회원가입에 성공한 경우 */
												}else if(cnt == 2){
													out.println("첫방문을 환영합니다."); /* 처음 방문한 경우 */
												}
											
											%> --%>
											<c:choose>
												<c:when test="${cnt == -1}">
													비밀번호가 다릅니다. 다시확인하세요
												</c:when>
												<c:when test="${cnt == 0}">
													존재하지않는 아이디입니다. 다시확인하세요.
												</c:when>
												<c:when test="${cnt == 1}">
													회원가입을 축하합니다. 로그인하세요!!
												</c:when>
												<c:otherwise>
													asdasdasdasdasdas첫방문을 환영합니다.	
												</c:otherwise>
											</c:choose>
											
											
											
										</th>
									</tr>
									
									<tr>
										<th>아이디</th>
										<td><input class="input" type="text" name="id" maxlength="20"></td><!-- maxlength는 db크기만큼 -->
									</tr>
									
									<tr>
										<th>비밀번호</th>
										<td><input class="input" type="password" name="pwd" maxlength="10"></td>
									</tr>
									
									<tr>
										<th colspan="2">
											<input class="inputButton" type="submit" value="로그인">
											<input class="inputButton" type="reset" value="취소">
											<input class="inputButton" type="button" value="회원가입"
													onclick="window.location='inputForm.mem'">
										</th>
									
									</tr>
									
								</table>
							</form>
						</c:if>	
					<%-- <%
						} else{ // 로그인 성공, 로그아웃, 회원탈퇴, 회원정보수정 
							
					%> --%>
					
						<c:if test="${sessionScope.memId != null}">
							<table>
								<tr>
									<td align="center" style="width:300px;">
										<%-- <span>${memId}</span>님 안녕하세요!! --%>
										 <span>${sessionScope.memId}</span>님 안녕하세요!!
									</td>	
								</tr>
								<tr>
									<th>
										<input class="inputButton" type="button" value="정보수정"
											onclick="window.location='modifyForm.mem'">
										<input class="inputButton" type="button" value="회원탈퇴"
											onclick="window.location='deleteForm.mem'">
										<input class="inputButton" type="button" value="로그아웃"
											onclick="window.location='logout.mem'">	
											
									</th>
								
								</tr>
								
							</table>	
						</c:if>
						
						<%-- <c:if test="${sessionScope.memId == host}">
							<table>
								<tr>
									<td align="center" style="width:300px;">
										<span>${memId}</span>님 안녕하세요!!
										 <span>${sessionScope.memId}</span>님 안녕하세요!!
									</td>	
								</tr>
								<tr>
									<th>
										<input class="inputButton" type="button" value="정보수정"
											onclick="window.location='modifyForm.mem'">
										<input class="inputButton" type="button" value="회원탈퇴"
											onclick="window.location='deleteForm.mem'">
										<input class="inputButton" type="button" value="로그아웃"
											onclick="window.location='logout.mem'">	
									</th>
								</tr>
							</table>
						</c:if> --%>
					</div>
									
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