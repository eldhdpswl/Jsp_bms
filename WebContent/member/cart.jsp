<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="setting.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>  

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
				<h2>도서목록</h2>
				<hr><br>
				
				<table style="width:1000px" align="center">
					<tr>
						<th colspan="10" align="center" style="height:25px">
							도서목록(책권수 : ${cnt}) &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<a href="mycartlist.mem">내 장바구니 보기</a>
							
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
									<br>
									<img src="<%=request.getServletContext().getContextPath()%>/host/images/${dto.kimg}" width="100%" height="900%">
								</td>
								
								<td>
									<a href="cartContent.mem?bookNum=${dto.bookNum}">${dto.bookName}</a>
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
								<a href="cart.mem">[◀◀]</a>
								<a href="cart.mem?pageNum=${startPage - pageBlock}">[◀]</a>
							</c:if>
							
							<c:forEach var="i" begin="${startPage}" end="${endPage}">
								<c:if test="${i == currentPage}">
									<span><b>[${i}]</b></span>
								
								</c:if>
								
								<c:if test="${i != currentPage}">
									<a href="cart.mem?pageNum=${i}">[${i}]</a>
								</c:if>
							</c:forEach>
							
							<!-- 다음블록[▶] / 끝[▶▶] -->
							<c:if test="${pageCount > endPage}">
								<a href="cart.mem?pageNum=${startPage + pageBlock}">[▶]</a>
								<a href="cart.mem?pageNum=${pageCount}">[▶▶]</a>
							</c:if>
							
							
							
						
						</c:if>
					</th>
				</table>
				
				
				
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