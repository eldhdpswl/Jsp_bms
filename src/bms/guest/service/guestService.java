package bms.guest.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface guestService {
	
	//책목록
	public void stockList(HttpServletRequest req, HttpServletResponse res);
	
	//상세페이지
	public void stockcontent(HttpServletRequest req, HttpServletResponse res);
	
	//장바구니 책담기 처리페이지
	public void cartTakePro(HttpServletRequest req, HttpServletResponse res);
	
	//내 장바구니 목록
	public void mycartlist(HttpServletRequest req, HttpServletResponse res);
	
	//checkbox로 체크할때 구매단 
	public void pay(HttpServletRequest req, HttpServletResponse res);
	
	//구매목록 리스트 불러오기
	public void paylist(HttpServletRequest req, HttpServletResponse res);
	
	//구매목록에서 결재하기 눌렀을때
	public void payPro(HttpServletRequest req, HttpServletResponse res);
	
	//환불하기
	public void payNum(HttpServletRequest req, HttpServletResponse res);
	
	public void directpay(HttpServletRequest req, HttpServletResponse res);

	public void selectBCount(HttpServletRequest req, HttpServletResponse res);
	
	public void paycancel(HttpServletRequest req, HttpServletResponse res);
	
	
	
}
