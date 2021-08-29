<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>



<!-- <link href='http://fonts.googleapis.com/css?family=Source+Sans+Pro:200,300,400,600' rel='stylesheet' type='text/css' /> -->
<link href="/BMS_project/style/hoststyle.css" rel="stylesheet" type="text/css" media="screen" />

<html>

<body>
<div id="wrapper">
	<div id="header-wrapper">
		<div id="header">
			<!-- <div id="logo">
				<h1><a href="#">BMS</a></h1>
				<p>Design by <a href="http://templated.co" rel="nofollow">TEMPLATED</a></p>
				<p>BMS_PROJECT</p>
			</div>  -->
			<%@ include file ="hostlogo.jsp" %> 
		</div>
	</div>
	<!-- end #header -->
	<div id="menu">
		<!-- <ul>
			<li class="current_page_item"><a href="#">Home</a></li>
			<li><a href="main.do">Home</a></li>
			<li><a href="login.do">로그인</a></li>
			<li><a href="#">재고관리</a></li>
			<li><a href="#">주문관리</a></li>
			<li><a href="#">결산</a></li>
			<li><a href="#">게시판</a></li>
		</ul> -->
		<%@ include file="host_stockMenu.jsp" %> 
	</div>
	<!-- end #menu -->
	<div id="page">
		<div id="page-bgtop">
			<div id="page-bgbtm">
				
				<!-- 게시판 시작부분 -->	
				<div width="1100px" align="center">
					
					<h2><center>책 추가</center></h2>
					<hr><br>
					
					<form action="host_stockaddPro.host" method="post" enctype="multipart/form-data"​ name="bookaddform" onsubmit="return bookaddCheck();" >  
						
						<table align="center">
							<tr>
								<th><label for="kimg">책이미지 추가</label></th>
								<td>
									<input type="file" name="kimg" accept="image/*">
								</td>
							</tr>
							
							
							<tr>
								<th>책이름</th>
								<td>
									<input class="input" type="text" name="bookName" maxlength="100">
								</td>
							</tr>
						
							<tr>
								<th>저자</th>
								<td>
									<input class="input" type="text" name="author" maxlength="100">
								</td>
							</tr>
							
							<tr>
								<th>출판사</th>
								<td>
									<input class="input" type="text" name="publisher" maxlength="100" style="width:270px">
								</td>
							</tr>
							
							<tr>
								<th>내용</th>
								<td>
									<textarea class="input" rows="10" cols="40" name="content" style="width:270px"></textarea>
								</td>
							</tr>
							
							<tr>
								<th>가격</th>
								<td>
									<input class="input" type="text" name="price" maxlength="100" style="width:270px">
								</td>
							</tr>
							
							<tr>
								<th>국내/국외</th>
								<td>
									<select  name="bookforeign">
										<option value="국내">국내</option>
										<option value="국외">국외</option>
									</select>
								</td>
							</tr>
							
							<tr>
								<th>권수</th>
								<td>
									<input type="text" name="count">
								</td>
							</tr>
							<tr>
								<th colspan="2">
									<input class="inputButton" type="submit" value="책추가">
									<input class="inputButton" type="reset" value="취소">
									<input class="inputButton" type="button" value="목록보기" 
											onclick="window.location='host_stockList.host'">
								</th>
							
							</tr>
							
						
						</table>
					
					</form>
					
					
				</div>
				
				<!-- end #sidebar -->
				<div style="clear: both;">&nbsp;</div>
			</div>
		</div>
	</div>
	<!-- end #page --> 
</div>
<div id="footer">
	<%@ include file ="hostMain_footer.jsp" %>
</div>
<!-- end #footer -->
</body>
</html>