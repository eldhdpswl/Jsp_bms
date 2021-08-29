package bms.gboard.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface gBoardService {
	
	//글목록
	public void boardList(HttpServletRequest req, HttpServletResponse res);
	
	//상세페이지
	public void b_contentForm(HttpServletRequest req, HttpServletResponse res);
	
	//글수정 상세페이지
	public void b_modifyView(HttpServletRequest req, HttpServletResponse res);
	
	//글수정 처리페이지
	public void b_modifyPro(HttpServletRequest req, HttpServletResponse res);
	
	//글작성 처리페이지
	public void b_writePro(HttpServletRequest req, HttpServletResponse res);
	
	//글삭제 처리페이지
	public void b_deletePro(HttpServletRequest req, HttpServletResponse res);
	
	
}
