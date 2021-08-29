<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="setting.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>  

<script src="${project}script.js"></script>

<!-- <link href='http://fonts.googleapis.com/css?family=Source+Sans+Pro:200,300,400,600' rel='stylesheet' type='text/css' /> -->
<link href="/BMS_project/style/style.css" rel="stylesheet" type="text/css" media="screen" />
<!-- <link href="../style/memberjoin.css" rel="stylesheet" type="text/css" media="screen" /> -->
<link href="/BMS_project/style/login.css" rel="stylesheet" type="text/css" media="screen" />


<html>
<body onload="modifyFocus();">
<div id="wrapper">
	<div id="header-wrapper">
		<div id="header">
			<%@ include file ="logo.jsp" %>
		</div>
	</div>
	<!-- end #header -->
	<div id="menu">
		<%@ include file="main_menu.jsp" %>
	</div>
	<!-- end #menu -->
	<div id="page">
		<div id="page-bgtop">
			<div id="page-bgbtm">
				<div width="1100px" align="center">
				<!-- 타이틀바 아래 회원정보수정jsp -->
				<h2>회원정보 수정</h2>
				<hr><br>
				<c:if test="${selectCnt == 1}">
					<form action="modifyPro.mem" method="post" name="modifyform"
							onsubmit="return modifyCheck();">
						<table>
							<tr>
								<th>아이디</th>
								<%-- <td><%=vo.getId()%></td> --%>
								<td>${vo.getId()}</td> <%-- ${vo.id}도 가능 --%>
								
							</tr>
							
							<tr>
								<th>* 비밀번호</th>
								<td>
									<input class="input" type="password" name="pwd" maxlength="10" value="${vo.getPwd()}" >
								</td>
							</tr>
							
							<tr>
								<th>* 비밀번호 확인</th>
								<td>
									<input class="input" type="password" name="repwd" maxlength="10" value="${vo.getPwd()}">
								</td>
							</tr>
							
							<tr>
								<th>* 이름</th>
								<td>
									<input class="input" type="text" name="name" maxlength="20" value="${vo.getName()}">
								</td>
							</tr>
							
							<tr>
								<th>* 주민번호</th>
								<td>${vo.getJumin1()}- *******</td>
							</tr>
							
							<tr>
								<th>핸드폰 번호</th>
								<td>
									<%-- <%
										//hp 데이터가 null이면 hp 형식에 맞게 입력받도록 input을 만든다.
										if(vo.getHp() == null){
									%>	 --%>
										<c:if test="${vo.getHp() == null}">
											<input class="input" type="text" name="hp1" maxlength="3" style="width:30px">
											-
											<input class="input" type="text" name="hp2" maxlength="4" style="width:40px">
											-
											<input class="input" type="text" name="hp3" maxlength="4" style="width:40px">
										</c:if>								
									<%-- <% 		
										}else{ //테이블 : '010-xxxx-xxxx'/입력하면 : 쪼개져있다(hp1-hp2-hp3)
											String hp = vo.getHp();
											String[] hpArr = hp.split("-");
									%> --%>
										<c:if test="${vo.getHp() != null}">
											<c:set var="hpArr" value="${fn:split(vo.getHp(), '-')}"/>
											<input class="input" type="text" name="hp1" maxlength="3" style="width:30px" value="${hpArr[0]}">
											-
											<input class="input" type="text" name="hp2" maxlength="4" style="width:40px" value="${hpArr[1]}">
											-
											<input class="input" type="text" name="hp3" maxlength="4" style="width:40px" value="${hpArr[2]}">	
										</c:if>		
									<%-- <% 
										}
									%> --%>
								</td>
							</tr>
							
							<tr>
								<th>* 이메일</th>
								<td>
									<%-- <%
										String email = vo.getEmail();
										System.out.println(email);
										String[] emailArr = email.split("@");
										
									%> --%>
									<c:set var="emailArr" value="${fn:split(vo.getEmail(), '@')}"/>
									<input class="input" type="text" name="email1" maxlength="10" style="width:100px" value="${emailArr[0]}">
									@
									<input class="input" type="text" name="email2" maxlength="20" style="width:100px" value="${emailArr[1]}">
								
								</td>
							</tr>
							
							<tr>
								<th> 가입일자</th>
								<td>  <fmt:formatDate type="both" pattern="yyyy-MM-dd HH:mm" value="${vo.getReg_date()}"/></td>
								
							</tr>
							
							<tr>
								<th colspan="2">
									<input class="inputButton" type="submit" value="수정">
									<input class="inputButton" type="reset" value="취소">
									<input class="inputButton" type="button" value="수정취소"
											onclick="window.history.go(-2);">	<!-- 2페이지 이전(=main.jsp)으로 이동 -->				
								</th>
							
							</tr>
							
						</table>
						
					</form>  
				</c:if>
					  	
			<%-- <%    	
			    }else{
			%>  --%>
				<c:if test="${selectCnt != 1}">  	
			    	<script type="text/javascript">
			    		errorAlert(passwdError);
			    	</script>
			    </c:if>	
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