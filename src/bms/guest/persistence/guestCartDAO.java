package bms.guest.persistence;

import java.util.ArrayList;

import bms.guest.vo.cartAddVO;
import bms.guest.vo.guestCartVO;


public interface guestCartDAO {
	
	//책갯수 구하기
	public int getArticleCnt();
	
	//책목록 목록 조회
	public ArrayList<guestCartVO> getArticleList(int start, int end);
	
	//상세페이지
	public guestCartVO getArticle(int bookNum);
	
	//장바구니 cart테이블에 추가
	public int insertCart(cartAddVO cartdto); 
	
	//장바구니 책 갯수 구하기
	public int getCartArticleCnt();
	
	//장바구니 목록 조회
	public ArrayList<guestCartVO> getCartArticleList(int cnt, String memId);
	
	//구매단으로 넘길때 장바구니 목록 조회하기
	public ArrayList<guestCartVO> getCartInfo(String[] cartNum);
	
/*	//페이넘 수 가져오기
	public int pay_seq();*/
	
	//데이터수 가져오기
	public int payNum(String memid);
	
	//pay테이블에 추가
	public String insertpay(ArrayList<guestCartVO> list);
	
	//cart테이블에 있는 정보들을 delete
	public int deletecart(ArrayList<guestCartVO> delist);
	
	//pay정보불러오기
	public ArrayList<guestCartVO> getPayList(String memId,int cnt);
	
	//구매목록 책 갯수 구하기
	public int getPayArticleCnt();
	
	//장바구니 목록 조회
	public ArrayList<guestCartVO> getPayArticleList(int cnt, String memId);
	
	/*//구매목록에서 결재하기할때 pay에있는 목록불러오기
	public ArrayList<guestCartVO> getPaymentList();*/
	
	//구매목록에서 결재하기할때 step update할 부분
	public int updatepay(guestCartVO paydto);
	
	public void bookCountUpdate(ArrayList<guestCartVO> dtos);
	
	//환불하기update
	public int refund(int payNum);
	
	public int insertdirectpay(int bookNum, int bookcount, String id);
	
	public int directpayupdate(int bookNum, int bookcount);
	
	public int selectBCount(int bookNum,int bookcount);
	
	//구매취소할때 도서 수량 update
	public int paycancel(int bookNum, int bookcount);
	
	//구매취소할떄 pay에서 list삭제
	public int paylistdelete(int payNum);
}
