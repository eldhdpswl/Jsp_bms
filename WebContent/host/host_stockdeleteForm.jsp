<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ include file="hostSetting.jsp"%> 

<html>
<body>
	<h2><center>책 삭제</center></h2>
	<form action="host_stockdeletePro.host" method="post" name="deleteform">
		<input type="hidden" name="bookNum" value="${bookNum}">
		<input type="hidden" name="pageNum" value="${pageNum}">
		
		<table align="center">
			<tr>
				<th colspan="2">정말로 삭제하시겠습니까?</th>
			</tr>
			
			<tr>
				<th>이미지</th>	
				<td>${dto.kimg}</td>
			</tr>
			
			<tr>
				<th>책이름</th>	
				<td>${dto.bookName}</td>
			</tr>
			<tr>
				<th>저자</th>	
				<td>${dto.author}</td>
			</tr>
			<tr>
				<th>출판사</th>	
				<td>${dto.publisher}</td>
			</tr>
			<tr>
				<th>내용</th>	
				<td>......</td>
			</tr>
			<tr>
				<th>가격</th>	
				<td>${dto.price}</td>
			</tr>
			<tr>
				<th>권수</th>	
				<td>${dto.bookcount}</td>
			</tr>
			<tr>
				<th>등록일</th>	
				<td>${dto.reg_date}</td>
			</tr>
			
			<tr>
				<th colspan="2">
					<input class="inputButton" type="submit" value="책삭제">
					<input class="inputButton" type="reset" value="삭제취소"
							onclick="window.history.back();">
				</th>
			
			</tr>
			
		</table>
		
	</form>	
	
</body>
</html>