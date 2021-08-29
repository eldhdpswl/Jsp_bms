package bms.gboard.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bms.gboard.service.gBoardService;
import bms.gboard.service.gBoardServiceImpl;






@WebServlet("*.bo")
public class gBFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public gBFrontController() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		actionDo(req, res);
	}

	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		actionDo(req, res);
	}
	
	protected void actionDo(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException{
		//한글 안깨지게 처리..안하면 DB에 한글이 깨져서 insert
		req.setCharacterEncoding("UTF-8");
		
		String viewPage = null;
		String uri = req.getRequestURI();
		String contextPath = req.getContextPath();
		String url = uri.substring(contextPath.length());
		
		//글목록
		if(url.equals("/boardList.bo")) {
			System.out.println("/boardList.bo");
			
			gBoardService service = new gBoardServiceImpl();
			service.boardList(req, res);
			
			viewPage = "/board/boardList.jsp";
		
		}
		
		//상세페이지
		else if(url.equals("/b_contentForm.bo")) {
			System.out.println("/b_contentForm.bo");
			
			gBoardService service = new gBoardServiceImpl();
			service.b_contentForm(req, res);
			
			viewPage = "/board/b_contentForm.jsp";
			
		}
		
		//상세페이지안에서 글수정페이지 
		//글수정폼 페이지
		else if(url.equals("/b_modifyForm.bo")) {
			System.out.println("/b_modifyForm.bo");
			
			int num = Integer.parseInt(req.getParameter("num"));
			int pageNum = Integer.parseInt(req.getParameter("pageNum"));
			
			req.setAttribute("num", num);
			req.setAttribute("pageNum", pageNum);
			
			gBoardService service = new gBoardServiceImpl();
			
			viewPage = "/board/b_modifyForm.jsp";
			
		}
		
		//글수정 상세페이지
		else if(url.equals("/b_modifyView.bo")) {
			System.out.println("/b_modifyView.bo");
			
			gBoardService service = new gBoardServiceImpl();
			service.b_modifyView(req, res);
			
			
			viewPage = "/board/b_modifyView.jsp";
			
		}
		
		//글수정 처리페이지
		else if(url.equals("/b_modifyPro.bo")) {
			System.out.println("/b_modifyPro.bo");
			
			gBoardService service = new gBoardServiceImpl();
			service.b_modifyPro(req, res);
			
			viewPage = "/board/b_modifyPro.jsp";
		}
		
		//글쓰기폼 페이지(답글쓰기)
		else if(url.equals("/b_writeForm.bo")) {
			System.out.println("/b_writeForm.bo");
			
			//제목글(답변글이 아닌 경우)
			int num = 0;
			int ref = 1;	//그룹화 아이디
			int ref_step = 0;  // 글순서(행)
			int ref_level = 0; // 글레벨(들여쓰기)
			
			//답변글
			//contentForm.jsp에서 get방식으로 넘긴 값 num, ref, ref_step, ref_level
			if(req.getParameter("num") != null) {
				num = Integer.parseInt(req.getParameter("num"));
				ref = Integer.parseInt(req.getParameter("ref"));
				ref_step = Integer.parseInt(req.getParameter("ref_step"));
				ref_level = Integer.parseInt(req.getParameter("ref_level"));
			}
			
			req.setAttribute("num", num);
			req.setAttribute("ref", ref);
			req.setAttribute("ref_step", ref_step);
			req.setAttribute("ref_level", ref_level);
			
			viewPage = "/board/b_writeForm.jsp";
			
		}
		
		//글쓰기 처리페이지
		else if(url.equals("/b_writePro.bo")) {
			System.out.println("/b_writePro.bo");
			
			gBoardService service = new gBoardServiceImpl();
			service.b_writePro(req, res);

			viewPage="/board/b_writePro.jsp";
		}
		
		//글삭제폼 페이지
		else if(url.equals("/b_deleteForm.bo")) {
			System.out.println("/b_deleteForm.bo");
			
			//contentForm.jsp에서 삭제버튼 클릭했을때  deleteForm.jsp?num=${dto.num}&pageNum=${pageNum}
			
			int num = Integer.parseInt(req.getParameter("num"));
			int pageNum = Integer.parseInt(req.getParameter("pageNum"));
			
			req.setAttribute("num", num);
			req.setAttribute("pageNum", pageNum);
			
			
			viewPage="/board/b_deleteForm.jsp";
			
		}
		
		//글삭제 처리페이지
		else if(url.equals("/b_deletePro.bo")) {
			System.out.println("/b_deletePro.bo");
			
			gBoardService service = new gBoardServiceImpl();
			service.b_deletePro(req, res);
			
			viewPage = "/board/b_deletePro.jsp";
		
		}
		
		RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);  //viewPage정보를 forward하기위해 쓴다.
		dispatcher.forward(req, res);
		
	}

	
	
	
	
	
	
}
