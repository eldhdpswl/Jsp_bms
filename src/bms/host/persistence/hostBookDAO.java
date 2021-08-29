package bms.host.persistence;

import java.util.ArrayList;

import bms.guest.vo.guestCartVO;
import bms.host.vo.hostBookVO;
import bms.host.vo.totalVO;



public interface hostBookDAO {
	
	//게시글 작성, 책이미지 추가
	public int insertBook(hostBookVO dto);
	
	//글갯수 구하기
	public int getArticleCnt();
	
	//책목록 목록 조회
	public ArrayList<hostBookVO> getArticleList(int start, int end);
	
	//상세페이지
	public hostBookVO getArticle(int bookNum);
	
	//비밀번호 확인(게시글 수정, 게시글 삭제)
	public int pwdCheck(int bookNum, String strPwd);
	
	//책수정 수정
	public int updateBook(hostBookVO dto);
	
	//책 삭제
	public int deleteBook(int bookNum);
	
	public ArrayList<guestCartVO> getorderlist(int cnt);
	
	public int payNum();
	
	public String selectforeign(String name);
	
	public void stepUpdate(int payNum);
	
	public void total(String bookforeign, int price, int count);/*bookforeign, price, count*/
	
	public void hostrefundbook(int bookNum, int bookcount);
	
	public void totalupdate(int price);
	
	public void updatestep(int payNum);
	
	public String foreigncheck(int bookNum);
	
	public void totalforeign(String foreign, int bookcount);
	
	public totalVO selecttotal();
	
}
