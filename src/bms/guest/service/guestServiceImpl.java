package bms.guest.service;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bms.guest.persistence.guestCartDAO;
import bms.guest.persistence.guestCartDAOImpl;
import bms.guest.vo.cartAddVO;
import bms.guest.vo.guestCartVO;
import bms.host.persistence.hostBookDAO;
import bms.host.persistence.hostBookDAOImpl;
import bms.host.vo.hostBookVO;

public class guestServiceImpl implements guestService {

	// 책목록
	@Override
	public void stockList(HttpServletRequest req, HttpServletResponse res) {

		int pageSize = 9; // 한 페이지당 출력할 글 갯수
		int pageBlock = 3; // 한 블럭당 페이지 갯수

		int cnt = 0; // 글 갯수
		int start = 0; // 현재 페이지 글시작번호
		int end = 0; // 현재 페이지 글마지막번호
		int number = 0; // 출력할 글번호
		String pageNum = null; // 페이지번호
		int currentPage = 0; // 현재페이지

		int pageCount = 0; // 페이지 갯수
		int startPage = 0; // 시작페이지
		int endPage = 0; // 마지막 페이지

		guestCartDAO dao = guestCartDAOImpl.getInstance();

		cnt = 0;
		cnt = dao.getArticleCnt();

		req.setAttribute("cnt", cnt);

		System.out.println("cnt : " + cnt);

		pageNum = req.getParameter("pageNum");

		if (pageNum == null) {
			pageNum = "1"; // 첫페이지 1페이지로 설정
		}

		currentPage = (Integer.parseInt(pageNum)); // 현재페이지

		pageCount = (cnt / pageSize) + (cnt % pageSize > 0 ? 1 : 0);

		start = (currentPage - 1) * pageSize + 1; // 현재페이지 시작번호

		end = start + pageSize - 1; // 현재페이지 끝번호

		System.out.println("start : " + start);
		System.out.println("end : " + end);

		if (end > cnt)
			end = cnt;

		number = cnt - (currentPage - 1) * pageSize; // 출력할 글번호... 최신글(큰 페이지)가 1page

		System.out.println("number : " + number);
		System.out.println("cnt : " + cnt);
		System.out.println("currentPage : " + currentPage);
		System.out.println("pageSize : " + pageSize);

		if (cnt > 0) {
			// 게시글 목록 조회
			ArrayList<guestCartVO> dtos = dao.getArticleList(start, end);

			/*
			 * System.out.println("첫번째인덱스 : "+dtos.get(1));
			 * System.out.println("두번째인덱스 : "+dtos.get(2));
			 * System.out.println("세번째인덱스 : "+dtos.get(3));
			 */

			req.setAttribute("dtos", dtos); // 큰바구니 : 게시글목록 cf)작은바구니 : 게시글 1건

		}

		startPage = (currentPage / pageBlock) * pageBlock + 1; // (5/3) * 3 + 1 = 4
		if (currentPage % pageBlock == 0)
			startPage -= pageBlock; // (5%3) == 0

		endPage = startPage + pageBlock - 1; // 4 + 3 - 1 = 6
		if (endPage > pageCount)
			endPage = pageCount;

		req.setAttribute("cnt", cnt); // 책수
		req.setAttribute("number", number); // 글번호
		req.setAttribute("pageNum", pageNum); // 페이지번호

		if (cnt > 0) {
			req.setAttribute("startPage", startPage); // 시작페이지
			req.setAttribute("endPage", endPage);// 마지막페이지
			req.setAttribute("pageBlock", pageBlock);// 출력할 페이지 갯수
			req.setAttribute("pageCount", pageCount);// 페이지 갯수
			req.setAttribute("currentPage", currentPage);// 현재 페이지

		}

	}

	// 상세페이지
	@Override
	public void stockcontent(HttpServletRequest req, HttpServletResponse res) {

		int bookNum = Integer.parseInt(req.getParameter("bookNum")); // 값을 넘길때는 무조건 키를 통해 넘기고 받는다.


		// dao 생성(싱글톤, 다형성)
		guestCartDAO dao = guestCartDAOImpl.getInstance();

		// 상세페이지 가져오기..1건
		guestCartVO dto = dao.getArticle(bookNum);

		// jsp로 값을 넘긴다. dto, pageNum, number

		req.setAttribute("dto", dto);
 // db글번호가 이닌 넘버링한 글번호

	}

	// 장바구니 책담기 처리페이지
	@Override
	public void cartTakePro(HttpServletRequest req, HttpServletResponse res) {

		int bookNum = (Integer) req.getAttribute("bookNum");
		String id = (String) req.getAttribute("id");
		int cartcount = (Integer) req.getAttribute("cartcount");

		cartAddVO cartdto = new cartAddVO();

		cartdto.setId(id);
		cartdto.setBookNum(bookNum);
		cartdto.setCartcount(cartcount);

		guestCartDAO dao = guestCartDAOImpl.getInstance();

		int cnt = dao.insertCart(cartdto);

		req.setAttribute("cnt", cnt);

	}

	// 내 장바구니 목록
	@Override
	public void mycartlist(HttpServletRequest req, HttpServletResponse res) {

		String memId = (String) req.getSession().getAttribute("memId");

		guestCartDAO dao = guestCartDAOImpl.getInstance();

		int cnt = dao.getCartArticleCnt();

		ArrayList<guestCartVO> dtos = dao.getCartArticleList(cnt, memId);

		req.setAttribute("cnt", cnt);
		req.setAttribute("dtos", dtos);

	}

	// checkbox로 체크할때 구매단
	@Override
	public void pay(HttpServletRequest req, HttpServletResponse res) {
		
		
		String[] cartNum = req.getParameterValues("cartNum");
		
		guestCartDAO dao = guestCartDAOImpl.getInstance();
		ArrayList<guestCartVO> dtos = dao.getCartInfo(cartNum);
		
		String memid= dao.insertpay(dtos);
		// 재고 업데이트
		dao.bookCountUpdate(dtos);
		
		//페이에 있는 해당 아이디의 데이터 숫자 오케이?? 리스트로 뿌릴 어레이리스트 사이즈로 쓰임
		int cnt=dao.payNum(memid);
		
	
			int deleteCnt = dao.deletecart(dtos);
			if(deleteCnt != 0) {
				ArrayList<guestCartVO> payselect = dao.getPayList(memid,cnt);	
				req.setAttribute("dtos", payselect);
			}
		
		req.setAttribute("cnt", cnt);
		
		
	}
	
	//구매목록 리스트 불러오기
	@Override
	public void paylist(HttpServletRequest req, HttpServletResponse res) {
		
		String memId = (String) req.getSession().getAttribute("memId");

		guestCartDAO dao = guestCartDAOImpl.getInstance();

		int cnt = dao.payNum(memId);

		ArrayList<guestCartVO> payselect = dao.getPayList(memId,cnt);

		System.out.println("-----------");
		req.setAttribute("cnt", cnt);
		req.setAttribute("dtos", payselect);
		
	}

	//구매목록에서 결재하기 눌렀을때
	@Override
	public void payPro(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("payPro여기까지왔음");
		int payNum = (Integer)req.getAttribute("payNum");
		int bookNum = (Integer)req.getAttribute("bookNum");
		int bookcount = (Integer)req.getAttribute("bookcount");
		
		guestCartVO paydto = new guestCartVO();
		
		paydto.setPayNum(payNum);
		paydto.setBookNum(bookNum);
		paydto.setBookcount(bookcount);
		
		guestCartDAO dao = guestCartDAOImpl.getInstance();
		
		int cnt = dao.updatepay(paydto);
		
		req.setAttribute("cnt", cnt);
		
	}
	
	//환불하기
	@Override
	public void payNum(HttpServletRequest req, HttpServletResponse res) {
		int payNum = Integer.parseInt(req.getParameter("payNum"));
		
		
		guestCartDAO dao = guestCartDAOImpl.getInstance();
		int cnt = dao.refund(payNum);
		
		req.setAttribute("refundCnt", cnt);
	}

	@Override
	public void directpay(HttpServletRequest req, HttpServletResponse res) {
		int bookNum = Integer.parseInt(req.getParameter("bookNum"));
		int bookcount = Integer.parseInt(req.getParameter("bookcount"));
		String id = (String)req.getSession().getAttribute("memId");
		
		guestCartDAO dao = guestCartDAOImpl.getInstance();
		//1. pay에 정보넣기
		int cnt = dao.insertdirectpay(bookNum, bookcount, id);
		
		//2. book에서 재고빼기
		int Cnt1 = dao.directpayupdate(bookNum, bookcount);
		
		int directbookCnt=0;
		if(cnt!=0&&Cnt1!=0) {
			directbookCnt=1;
		}
		req.setAttribute("directbookCnt",directbookCnt);
	}

	@Override
	public void selectBCount(HttpServletRequest req, HttpServletResponse res) {
		int bookNum=Integer.parseInt(req.getParameter("bookNum"));
		int bookcount=Integer.parseInt(req.getParameter("bookcount"));
		int BCnt=0;
		guestCartDAO dao = guestCartDAOImpl.getInstance();
		
		int BCountCheck=dao.selectBCount(bookNum,bookcount);
		
		if(bookcount>BCountCheck) {
			BCnt=1;
		}
		System.out.println("BCnt----------"+BCnt);
		req.setAttribute("BCnt", BCnt);
	}
	
	//구매취소
	@Override
	public void paycancel(HttpServletRequest req, HttpServletResponse res) {
		int bookNum = Integer.parseInt(req.getParameter("bookNum"));
		int bookcount = Integer.parseInt(req.getParameter("bookcount"));
		int payNum = Integer.parseInt(req.getParameter("payNum"));
		
		System.out.println("bookNum : "+ bookNum);
		System.out.println("bookcount : " + bookcount);
		
		guestCartDAO dao = guestCartDAOImpl.getInstance();
		//1.구매취소할떄 업데이트
		int paycancelcnt = dao.paycancel(bookNum, bookcount);
		
		//2. 구매취소할떄 pay부분 delete
		int paydeletecnt= dao.paylistdelete(payNum);
		
		int Dcnt=0;
		
		
		if(paycancelcnt !=0 &&paydeletecnt !=0) {
			Dcnt=1;
		}
		
		req.setAttribute("Dcnt", Dcnt);
		
	}

	

}
