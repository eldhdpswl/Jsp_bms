<%@page import="bms.guest.vo.guestCartVO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="hostSetting.jsp" %>


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
		<%@ include file="hostMain_menu.jsp" %> 
	</div>
	<!-- end #menu -->
	<div id="page">
		<div id="page-bgtop">
			<div id="page-bgbtm">
				<div width="1100px" align="center">
				<h2>주문상품 목록</h2>
				<hr><br>
				<form action="payPro.mem" name="mycartlist">
					<table style="width:1000px" align="center">
					
						<tr>
							<th colspan="10" align="center" style="height:25px">
								<a href="cart.mem">도서목록보기</a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								도서목록(책권수 : ${cnt}) &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<a href="mycartlist.mem">내 장바구니 목록보기</a>
								
							</th>
						</tr>
						
						<tr>
							<th style="width:5%">책번호</th>
							<th style="width:10%">표지</th>
							<th style="width:10%">책이름</th>
							<th style="width:10%">판매가</th>
							<th style="width:7%">수량</th>
							<th style="width:10%">합계</th>
							<th style="width:5%">상태</th>
							<th style="width:10%">선택</th>
							
							
						</tr>
						
						<!-- 게시글 있으면 -->
						<c:if test="${(cnt*1) > 0}">
						<%ArrayList<guestCartVO> dtos=(ArrayList<guestCartVO>) request.getAttribute("dtos");
							
							
							for(int i=0;i<dtos.size();i++){
								String name=dtos.get(i).getBookName();
								int payNum=dtos.get(i).getPayNum();
								int count=dtos.get(i).getBookcount();
								int price=dtos.get(i).getPrice();
								int bookNum=dtos.get(i).getBookNum();
						%>
								<tr>
									<td align="center">
										<%=dtos.get(i).getBookNum()%>
									</td>
									
									<td>
										<br>
										<img src="<%=request.getServletContext().getContextPath()%>/host/images/<%=dtos.get(i).getKimg()%>" width="100%" height="150px;">
									</td>
									
									<td>
										<%-- <a href="cartContent.mem?bookNum=${dto.bookNum}&pageNum=${pageNum}&number=${number+1}">${dto.bookName}</a> --%>
										<%=dtos.get(i).getBookName()%>
									</td>
									
									<td align="center">
										<%=dtos.get(i).getPrice()%>

									</td>
									
								    <td align="center">
								    	
										<input type="text" name="bookcount" value="<%=dtos.get(i).getBookcount()%>">권		
										<input type="hidden" name="payNum" value="${dto.payNum}">
										
										<input type="hidden" name="id" value="${memId}">
									</td>
									<td align="center">
									<%=dtos.get(i).getTotal()%>
										
										
									</td>
									<td align="center">
										<c:if test="<%=dtos.get(i).getStep()==1%>">
											주문
										</c:if>
										<c:if test="<%=dtos.get(i).getStep()==2%>">
											배송완료
										</c:if>
										<c:if test="<%=dtos.get(i).getStep()==3%>">
											환불
										</c:if>
										<c:if test="<%=dtos.get(i).getStep()==4%>">
											환불완료
										</c:if>
										
									</td>
									
									
									<td align="center">
										<c:if test="<%=dtos.get(i).getStep()==1%>">
											<input class="inputButton" type="button" onclick="window.location='delivery.host?name=<%=name%>&payNum=<%=payNum%>&count=<%=count%>&price=<%=price%>'" name="pay" value="배송하기"><br>
										</c:if>
										<c:if test="<%=dtos.get(i).getStep()==2%>">
											
										</c:if>
										<c:if test="<%=dtos.get(i).getStep()==3%>">
											<input class="inputButton" type="button" onclick="window.location='hostrefund.host?bookNum=<%=bookNum%>&bookcount=<%=count%>&price=<%=price%>&payNum=<%=payNum%>'" name="pay" value="환불하기"><br>
										</c:if>
										<c:if test="<%=dtos.get(i).getStep()==4%>">
											<input class="inputButton" type="button" onclick="window.location=''" name="pay" value="삭제"><br>
										</c:if>
									</td>
								</tr>
								<tr>
									
								</tr>
						
						
						<%
							}
						%>
								
							
							
						</c:if>
						
						<!-- 게시글 없으면 -->
						
						<c:if test="${(cnt*1) == 0}">
							<tr>
								<td colspan="10" align="center">
									구매목록이 없습니다..!!
								</td>
							</tr>
						</c:if>
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
	<%@ include file ="hostMain_footer.jsp" %>
</div>
<!-- end #footer -->
</body>
</html>