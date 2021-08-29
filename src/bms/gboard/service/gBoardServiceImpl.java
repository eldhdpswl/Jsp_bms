package bms.gboard.service;

import java.sql.Timestamp;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bms.gboard.persistence.gBoardDAO;
import bms.gboard.persistence.gBoardDAOImpl;
import bms.gboard.vo.gBoardVO;






public class gBoardServiceImpl implements gBoardService{
	
	//글목록
	@Override
	public void boardList(HttpServletRequest req, HttpServletResponse res) {
		
		int pageSize = 10;    //한 페이지당 출력할 글 갯수
		int pageBlock = 3;   //한 블럭당 페이지 갯수
		
		int cnt=0;			 //글 갯수
		int start=0;		 //현재 페이지 글시작번호
		int end=0;			 //현재 페이지 글마지막번호
		int number=0;		 //출력할 글번호
		String pageNum = null;  //페이지번호
		int currentPage = 0;    //현재페이지
		
		int pageCount = 0;		//페이지 갯수
		int startPage = 0; 		//시작페이지
		int endPage=0;  		//마지막 페이지	
		
		
		//dao 객체생성(싱글톤, 다형성)
		gBoardDAO dao = gBoardDAOImpl.getInstance();
		
		//글갯수 구하기
		cnt = 0;
		cnt = dao.getArticleCnt();
		
		req.setAttribute("cnt", cnt);
		
		System.out.println("cnt : " + cnt);
		
		pageNum = req.getParameter("pageNum");
		
		if(pageNum == null) {
			pageNum = "1";  //첫페이지 1페이지로 설정
		}
		
		//현재페이지
		currentPage = (Integer.parseInt(pageNum));  
		
		//페이지 갯수
		//페이지갯수+나머지  //나머지 갯수가 있으면 한페이지를 더 줘야된다.
		pageCount = (cnt / pageSize) + (cnt % pageSize > 0 ? 1 : 0);
		
		//현재페이지에서 -1을 하는 이유는 0부터시작하기때문
		start = (currentPage -1) * pageSize +1;
		
		//현재페이지 끝번호
		end = start + pageSize -1;
		
		if(end > cnt) end=cnt;
		
		number = cnt - (currentPage - 1) * pageSize; //출력할 글번호... 최신글(큰 페이지)가 1page 
		
		System.out.println("number : " + number);
		System.out.println("cnt : " + cnt);
		System.out.println("currentPage : " + currentPage);
		System.out.println("pageSize : " + pageSize);
		
		if(cnt > 0) {
			ArrayList<gBoardVO> dtos = dao.getArticleList(start, end);
			req.setAttribute("dtos", dtos);
		}
		
		startPage = (currentPage/pageBlock) * pageBlock + 1;
		if(currentPage % pageBlock == 0) startPage -= pageBlock;
		
		endPage = startPage + pageBlock -1; 
		if(endPage > pageCount) endPage = pageCount;
		
		req.setAttribute("cnt", cnt); //글갯수
		req.setAttribute("number", number); //글번호
		req.setAttribute("pageNum", pageNum); //페이지번호
		System.out.println("cnt--------"+cnt);
		if(cnt > 0) {
			req.setAttribute("startPage", startPage);  //시작페이지
			req.setAttribute("endPage", endPage);//마지막페이지
			req.setAttribute("pageBlock", pageBlock);//출력할 페이지 갯수
			req.setAttribute("pageCount", pageCount);//페이지 갯수
			req.setAttribute("currentPage", currentPage);//현재 페이지
			
		}
	}
	
	//상세페이지
	@Override
	public void b_contentForm(HttpServletRequest req, HttpServletResponse res) {
		int num = Integer.parseInt(req.getParameter("num"));
		int pageNum = Integer.parseInt(req.getParameter("pageNum"));
		int number = Integer.parseInt(req.getParameter("number"));
		
		//dao 생성(싱글톤, 다형성)
		gBoardDAO dao = gBoardDAOImpl.getInstance();
		
		//상세페이지 가져오기..1건
		gBoardVO dto = dao.getArticle(num);
		
		//조회수 증가
		dao.addReadCnt(num);
		
		//jsp로 값을 넘긴다. dto, pageNum, number
		req.setAttribute("dto", dto);
		req.setAttribute("pageNum", pageNum);
		req.setAttribute("number", number);
		
		
	}
	
	//글수정 상세페이지
	@Override
	public void b_modifyView(HttpServletRequest req, HttpServletResponse res) {
		//넘겨받은 값 가져온다.
		int num = Integer.parseInt(req.getParameter("num")); 
		int pageNum = Integer.parseInt(req.getParameter("pageNum")); 
		String strPwd = req.getParameter("pwd");
		
		//dao객체생성(싱글톤, 다형성 적용)
		gBoardDAO dao = gBoardDAOImpl.getInstance();
		
		//패스워드가 일치하면 selectCnt == 1, 불일치시 selectCnt == 0;
		
		int selectCnt = dao.pwdCheck(num, strPwd);
		
		//패스워드가 일치하면 num과 일치하는 게시글 1건을 읽어온다.
		if(selectCnt == 1) {
			gBoardVO dto = dao.getArticle(num);
			
			req.setAttribute("dto", dto);
		}
		
		//jsp에 값들을 넘긴다.
		req.setAttribute("selectCnt", selectCnt);
		req.setAttribute("num", num);
		req.setAttribute("pageNum", pageNum);

	}
	
	//글수정 처리페이지
	@Override
	public void b_modifyPro(HttpServletRequest req, HttpServletResponse res) {
		
		//화면으로부터 넘겨받은 값들을 받는다.
		int num = Integer.parseInt(req.getParameter("num"));
		int pageNum = Integer.parseInt(req.getParameter("pageNum"));
		
		//바구니 생성
		gBoardVO dto = new gBoardVO();
				
		//바구니에 화면에서 입력받은 값들을 담는다.
		dto.setNum(num);
		dto.setSubject(req.getParameter("subject"));
		dto.setContent(req.getParameter("content"));
		dto.setPwd(req.getParameter("pwd"));		
		
		//dao 객체생성(싱글톤, 다형성)
		gBoardDAO dao = gBoardDAOImpl.getInstance();
		
		//수정처리
		int cnt = dao.updateBoard(dto);
		//jsp에 값들을 넘긴다.
		req.setAttribute("num", num);
		req.setAttribute("pageNum", pageNum);
		req.setAttribute("cnt", cnt);
				
				
				
	}
	
	//글작성 처리페이지
	@Override
	public void b_writePro(HttpServletRequest req, HttpServletResponse res) {
		int num = Integer.parseInt(req.getParameter("num"));
		int ref = Integer.parseInt(req.getParameter("ref"));
		int ref_step = Integer.parseInt(req.getParameter("ref_step"));
		int ref_level = Integer.parseInt(req.getParameter("ref_level"));
		
		//1. 작은 바구니(DTO)를 만든다.
		gBoardVO dto = new gBoardVO();
		
		//2. 화면으로부터 입력받은 내용을 작은 바구니(DTO)에 담는다.
		dto.setWriter(req.getParameter("writer"));
		dto.setPwd(req.getParameter("pwd"));
		dto.setSubject(req.getParameter("subject"));
		dto.setContent(req.getParameter("content"));
		
		//3. hidden으로부터 넘겨받은 값을(DTO)에 담는다.
		
		dto.setNum(num);
		dto.setRef(ref);
		dto.setRef_step(ref_step);
		dto.setRef_level(ref_level);
		
		dto.setReg_date(new Timestamp(System.currentTimeMillis()));
		
		//4. dao 객체생성(싱글톤, 다형성)
		gBoardDAO dao = gBoardDAOImpl.getInstance();
		
		//5. insertBoard()
		int cnt = dao.insertBoard(dto);
		System.out.println("cnt --> " + cnt);
		//6. jsp에 값들을 넘긴다.
		req.setAttribute("cnt", cnt);
		
	}
	
	//글삭제 처리페이지
	@Override
	public void b_deletePro(HttpServletRequest req, HttpServletResponse res) {
		int num = Integer.parseInt(req.getParameter("num"));
		int pageNum = Integer.parseInt(req.getParameter("pageNum"));
		String strPwd = req.getParameter("pwd");
		
		//dao 객체생성(싱글톤, 다형성)
		gBoardDAO dao = gBoardDAOImpl.getInstance();
	
		//num과 일치할 경우 비밀번호 일치하는지 확인
		int selectCnt = dao.pwdCheck(num, strPwd);
		
		
		/*
		 * deleteCnt = -1 : 답글이 있는 경우 삭제 안함
		 * deleteCnt = 0 : 답글이 없는 경우 삭제 실패
		 * deleteCnt = 1: 답글이 없는 경우 삭제 성공
		 * 
		 */
		
		if(selectCnt == 1) {
			int deleteCnt = dao.deleteBoard(num);
			req.setAttribute("deleteCnt", deleteCnt);
		}
		
		req.setAttribute("selectCnt", selectCnt);
		req.setAttribute("pageNum", pageNum);
		
	}
	
	

}
