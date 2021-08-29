package bms.host.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface hostService {
	
	//책추가 처리페이지
	public void host_stockaddPro(HttpServletRequest req, HttpServletResponse res);
	
	//책목록
	public void host_stockList(HttpServletRequest req, HttpServletResponse res);
	
	//상세페이지
	public void bookcontentForm(HttpServletRequest req, HttpServletResponse res);
	
	//책수정 상세페이지
	public void bookmodifyView(HttpServletRequest req, HttpServletResponse res);
	
	//책 수정 처리페이지
	public void bookmodifyPro(HttpServletRequest req, HttpServletResponse res);
	
	//책 삭제 폼페이지
	public void bookdeleteForm(HttpServletRequest req, HttpServletResponse res);
	
	//책 삭제 처리페이지
	public void bookdeletePro(HttpServletRequest req, HttpServletResponse res);
	
	//주문요청 처리 페이지
	public void orderlist(HttpServletRequest req, HttpServletResponse res);
	
	public void delivery(HttpServletRequest req, HttpServletResponse res);
	
	public void hostrefund(HttpServletRequest req, HttpServletResponse res);
	
	public void finaltotal(HttpServletRequest req, HttpServletResponse res);
	
	
}
