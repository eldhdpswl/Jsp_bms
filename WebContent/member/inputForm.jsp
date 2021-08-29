<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="setting.jsp" %>

<script src="${project}script.js"></script>

<link href='http://fonts.googleapis.com/css?family=Source+Sans+Pro:200,300,400,600' rel='stylesheet' type='text/css' />
<link href="/BMS_project/style/style.css" rel="stylesheet" type="text/css" media="screen" />
<!-- <link href="../style/memberjoin.css" rel="stylesheet" type="text/css" media="screen" /> -->
<link href="/BMS_project/style/login.css" rel="stylesheet" type="text/css" media="screen" />


<html>
<body onload="inputFocus();">
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
				<h2>회원가입</h2>
				<hr><br>
				<form action="inputPro.mem" method="post" name="inputform" onsubmit="return inputCheck();">
		
					<!-- hiddenId : 중복확인 버튼 클릭 여부 체크(0:클릭안함, 1:클릭함) -->
					<input type="hidden" name="hiddenId" value="0">
					<table>
						<tr>
							<th colspan="2">회원정보를 입력하세요!</th>			
						</tr>
						
						<tr>
							<th> * 아이디</th>
							<td>
								<input class="input" type="text" name="id" maxlength="20">
								<input class="inputButton" type="button" name="dupChk" type="button" value="중복확인" onclick="confirmId();">
							</td>
						</tr>
						
						<tr>
							<th> * 비밀번호</th>
							<td>
								<input class="input" type="password" name="pwd" maxlength="10">
							</td>	
						</tr>
						
						<tr>
							<th> * 비밀번호 확인</th>
							<td>
								<input class="input" type="password" name="repwd" maxlength="10">
							</td>
						</tr>
						
						<tr>
							<th> * 이름</th>
							<td>
								<input class="input" type="text" name="name" maxlengh="20">
							</td>
						
						</tr>
						
						<tr>
							<th> * 주민번호</th>
							<td>
								<input class="input" type="text" name="jumin1" maxlengh="6"
								 		style="width:50px" onkeyup="nextJumin1();"> <!-- onkeyup은 자리를 다채우면 자동으로 옆으로 넘어가라  -->
								-
								<input class="input" type="text" name="jumin2" maxlength="7"
										style="width:60px" onkeyup="nextJumin2();">
							</td>
						</tr>
						
						<tr>
							<th>핸드폰 번호</th>
							<td>
								<input class="input" type="text" name="hp1" maxlength="3"
										style="width:30px" onkeyup="nextHp1();">
								-
								<input class="input" type="text" name="hp2" maxlength="4"
										style="width:40px" onkeyup="nextHp2();">
								-
								<input class="input" type="text" name="hp3" maxlength="4"
										style="width:40px" onkeyup="nextHp3();">				
							</td>
						</tr>
						
						<tr>
							<th> * 이메일</th>
							<td>
								<input class="input" type="text" name="email1" maxlength="10"
										style="width:65px">
								@
								<input class="input" type="text" name="email2" maxlength="20"
										style="width:65px">
								<select class="input" name="email3">
									<option value="0">직접입력</option>
									<option value="gmail.com">구글</option>
									<option value="daum.net">다음</option>
									<option value="nate.com">네이트</option>
								</select>				
								
							</td>
						</tr>
						
						<tr>
							<th colspan="2">
								<input class="inputButton" type="submit" value="회원가입">
								<input class="inputButton" type="reset" value="재작성">
								<input class="inputButton" type="button" value="가입취소"
										onclick="window.location='main.do'">
							</th>
							
						</tr>
						
					</table>
				
				</form>
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