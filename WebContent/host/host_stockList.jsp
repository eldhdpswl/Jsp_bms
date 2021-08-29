<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="hostSetting.jsp" %>

<!-- <link href='http://fonts.googleapis.com/css?family=Source+Sans+Pro:200,300,400,600' rel='stylesheet' type='text/css' /> -->
<link href="/BMS_project/style/hoststyle.css" rel="stylesheet" type="text/css" media="screen" />
<link href="/BMS_project/style/boardstyle.css" rel="stylesheet" type="text/css" media="screen" /> 

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
				<div width="1000px" align="center">
					
					<h2><center>도서목록</center></h2>
					<hr>
					
					<table style="width:1000px" align="center">
						<tr>
							<th colspan="10" align="center" style="height:25px">
								글목록(글갯수 : ${cnt}) &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<a href="host_stockaddForm.host">책추가</a>
								
							</th>
						</tr>
						
						<tr>
							<th style="width:5%">책번호</th>
							<th style="width:10%">표지</th>
							<th style="width:10%">책이름</th>
							<th style="width:10%">저자</th>
							<th style="width:10%">출판사</th>
							<th style="width:20%">내용</th>
							<th style="width:10%">가격</th>
							<th style="width:10%">국내/국외</th>
							<th style="width:7%">권수</th>
							<th style="width:10%">등록일</th>
						
						</tr>
					
						<!-- 게시글 있으면 -->
						<c:if test="${cnt > 0}">
							<c:forEach var="dto" items="${dtos}">
								<tr>
									<td align="center">
										${dto.bookNum}
									</td>
									
									<td>
										<%-- <c:choose>
											<c:when test="${dto.kimg != null}">
												<img src="<%=request.getServletContext().getContextPath()%>/host/images/${dto.kimg}" width="100%" height="100%">
											</c:when>
										</c:choose>   --%>
										<img src="<%=request.getServletContext().getContextPath()%>/host/images/${dto.kimg}" width="100%" height="100%">
									</td>
									
									<td>
										<a href="host_stockcontent.host?bookNum=${dto.bookNum}&pageNum=${pageNum}&number=${number+1}">${dto.bookName}</a>
										<%-- ${dto.bookName} --%>
									</td>
									
									<td align="center">
										${dto.author}					
									</td>
									<td align="center">
										${dto.publisher}					
									</td>
									<td align="center">
										${dto.content}					
									</td>
									<td align="center">
										${dto.price}					
									</td>
									
									<td align="center">
										${dto.bookforeign}					
									</td>
									<td align="center">
										${dto.bookcount}					
									</td>
									
									
									<td align="center">
										<fmt:formatDate type="both" pattern="yyyy-MM-dd HH:mm" value="${dto.reg_date}"/>
										
									</td>
									
								</tr>
							
							</c:forEach>
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
					
					<!-- 페이지컨트롤 -->
					<table style="width:1000px" align="center">
						<th align="center">
							<c:if test="${cnt > 0}">
								<!-- 처음[◀◀] / 이전블록[◀] 특수문자 : ㅁ한자키 -->
								<c:if test="${startPage > pageBlock}">
									<a href="host_stockList.host">[◀◀]</a>
									<a href="host_stockList.host?pageNum=${startPage - pageBlock}">[◀]</a>
								</c:if>
								
								<c:forEach var="i" begin="${startPage}" end="${endPage}">
									<c:if test="${i == currentPage}">
										<span><b>[${i}]</b></span>
									
									</c:if>
									
									<c:if test="${i != currentPage}">
										<a href="host_stockList.host?pageNum=${i}">[${i}]</a>
									</c:if>
								</c:forEach>
								
								<!-- 다음블록[▶] / 끝[▶▶] -->
								<c:if test="${pageCount > endPage}">
									<a href="host_stockList.host?pageNum=${startPage + pageBlock}">[▶]</a>
									<a href="host_stockList.host?pageNum=${pageCount}">[▶▶]</a>
								</c:if>
								
								
								
							
							</c:if>
						</th>
					</table>
					
					
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