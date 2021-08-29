package bms.gmember.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bms.gboard.service.gBoardService;
import bms.gboard.service.gBoardServiceImpl;
import bms.gmember.service.gMemberServiceImpl;
import bms.guest.service.guestService;
import bms.guest.service.guestServiceImpl;


@WebServlet("*.mem")
public class gMFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public gMFrontController() {
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
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		String viewPage = null;
		String uri = req.getRequestURI();
		String contextPath = req.getContextPath();
		String url = uri.substring(contextPath.length());
		
		//첫 메인페이지
		if(url.equals("/main.mem")) {
			System.out.println("/main.mem");
			
			guestService service = new guestServiceImpl();
			service.stockList(req, res);
			
			viewPage = "/member/main.jsp";
		}
		
		//로그인 페이지
		else if(url.equals("/login.mem")) {
			System.out.println("/login.mem");
			
			viewPage = "/member/login.jsp";
		}
		
		//회원가입 페이지
		else if(url.equals("/inputForm.mem")) {
			System.out.println("/inputForm.mem");
			
			viewPage = "/member/inputForm.jsp";
		}
		
		//중복확인 페이지
		else if(url.equals("/confirmId.mem")) {
			System.out.println("/confirmId.mem");
			
			gMemberServiceImpl service = new gMemberServiceImpl();
			service.confirmId(req, res);
			
			viewPage = "/member/confirmId.jsp";
		}
		
		//회원가입 처리 페이지
		else if(url.equals("/inputPro.mem")) {
			System.out.println("/inputPro.mem");
			
			gMemberServiceImpl service = new gMemberServiceImpl();
			service.inputPro(req, res);
			
			viewPage = "/member/inputPro.jsp";
		}
		
		//회원가입 성공 : mainSuccess.mem
		else if(url.equals("/mainSuccess.mem")) {
			System.out.println("/mainSuccess.mem");
			
			int cnt = Integer.parseInt(req.getParameter("cnt"));
			req.setAttribute("cnt", cnt);
			
			viewPage = "/member/login.jsp";
		}
		
		//로그인 처리 페이지	
		else if(url.equals("/loginPro.mem")) {
			System.out.println("/loginPro.mem");
			
			gMemberServiceImpl service = new gMemberServiceImpl();
			service.loginPro(req, res);
			
			if(req.getSession().getAttribute("memId").equals("host")) {
				viewPage="/host/hostMain.jsp";
			}else {
				guestService service1 = new guestServiceImpl();
				service1.stockList(req, res);
				
				viewPage= "/member/main.jsp";
				
			}
			
		}
		
		//로그아웃
		else if(url.equals("/logout.mem")) {
			System.out.println("/logout.mem");
			
			//세션을 삭제..memId대소문자 구분
			req.getSession().setAttribute("memId", null);
			req.setAttribute("cnt", 2);
			
			guestService service1 = new guestServiceImpl();
			service1.stockList(req, res);
			
			viewPage ="/member/login.jsp";
		}
		
		//회원탈퇴	
		else if(url.equals("/deleteForm.mem")) {
			System.out.println("/deleteForm.mem");
			
			
			viewPage="/member/deleteForm.jsp";
		}
		
		//회원탈퇴처리페이지	
		else if(url.equals("/deletePro.mem")) {
			System.out.println("/deletePro.mem");
			
			gMemberServiceImpl service = new gMemberServiceImpl();
			service.deletePro(req, res);
			
			viewPage="/member/deletePro.jsp";
		}
		
		//회원정보 수정폼페이지
		else if(url.equals("/modifyForm.mem")) {
			System.out.println("/modifyForm.mem");
			
			viewPage = "/member/modifyForm.jsp";
		}
		
		//회원정보수정 정보페이지
		else if(url.equals("/modifyView.mem")) {
			System.out.println("/modifyView.mem");
			
			gMemberServiceImpl service = new gMemberServiceImpl();
			service.modifyView(req, res);
			
			viewPage = "/member/modifyView.jsp";
		}
		
		//회원정보 수정 처리페이지
		else if(url.equals("/modifyPro.mem")) {
			System.out.println("/modifyPro.mem");
			
			gMemberServiceImpl service = new gMemberServiceImpl();
			service.modifyPro(req, res);
			
			viewPage = "/member/modifyPro.jsp";
		}
		
		//cart 책목록불러오기
		else if(url.equals("/cart.mem")) {
			System.out.println("/cart.mem");
			
			guestService service = new guestServiceImpl();
			service.stockList(req, res);
			
			viewPage="/member/cart.jsp";
		}
		
		//cart 책목록에서  상세페이지
		else if(url.equals("/cartContent.mem")) {
			System.out.println("/cartContent.mem");
			
			guestService service = new guestServiceImpl();
			service.stockcontent(req, res);
			
			
			viewPage = "/member/cartContent.jsp";
		}
		
		//cart 장바구니에 책담기 처리 페이지
		else if(url.equals("/cartTakePro.mem")) {
			System.out.println("/cartTakePro.mem");	
			
			/*int pageNum = Integer.parseInt(req.getParameter("pageNum"));*/
			int bookNum = Integer.parseInt(req.getParameter("bookNum"));
			String id = req.getParameter("id");
			int cartcount = Integer.parseInt(req.getParameter("cartcount"));
			
			/*req.setAttribute("pageNum", pageNum);*/
			req.setAttribute("bookNum", bookNum);
			req.setAttribute("id", id);
			req.setAttribute("cartcount", cartcount);
			
			guestService service = new guestServiceImpl();
			service.cartTakePro(req, res);
			
			viewPage = "/member/cartTakePro.jsp";
		}
		
		//내장바구니 목록
		else if(url.equals("/mycartlist.mem")) {
			System.out.println("/mycartlist.mem");
			
			guestService service = new guestServiceImpl();
			service.mycartlist(req, res);
			
			viewPage="/member/mycartlist.jsp";
		}
		
		//구매단부분
		else if(url.equals("/pay.mem")) {
			System.out.println("/pay.mem");
			String[] cartNum = req.getParameterValues("cartNum");
			
			if(cartNum==null) {
				req.setAttribute("error", 1);
				guestService service = new guestServiceImpl();
				service.mycartlist(req, res);
				viewPage="/member/mycartlist.jsp";
			}else {
			guestService service = new guestServiceImpl();
			service.pay(req, res);
			viewPage="/member/pay.jsp";
			}
			
			
		}
		
		//구매단 목록 불러오기
		else if(url.equals("/paylist.mem")) {
			System.out.println("/paylist.mem");
			
			guestService service = new guestServiceImpl();
			service.paylist(req, res);
			
			
			
			viewPage="/member/pay.jsp";
		}
		
		//구매단에서 결재할떄
		else if(url.equals("/payPro.mem")) {
			System.out.println("/payPro.mem");
			
			String id = req.getParameter("id");
			System.out.println("id " + id);
			int payNum = Integer.parseInt(req.getParameter("payNum"));
			System.out.println("payNum " + payNum);
			int bookNum = Integer.parseInt(req.getParameter("bookNum"));
			System.out.println("bookNum " + bookNum);
			int bookcount = Integer.parseInt(req.getParameter("bookcount"));
			System.out.println("bookcount " + bookcount);
			
			req.setAttribute("payNum", payNum);
			req.setAttribute("bookNum", bookNum);
			req.setAttribute("bookcount", bookcount);
			
			guestService service = new guestServiceImpl();
			service.payPro(req, res);
			
			viewPage="/member/payPro.jsp";
		}
		
		//환불하기
		else if(url.equals("/refund.mem")) {
			System.out.println("/refund.mem");
			
			guestService service = new guestServiceImpl();
			service.payNum(req, res);
			
			service.paylist(req, res);
			
			viewPage="/member/pay.jsp";
		}
		
		//바로구매
		else if(url.equals("/directpay.mem")) {
			guestService service = new guestServiceImpl();
			
			service.selectBCount(req, res);
			int BCnt=(int)req.getAttribute("BCnt");
			if(BCnt==1) {
				
			}else {
				service.directpay(req, res);//구매
			}
			req.setAttribute("BCnt", BCnt);
			
			service.stockcontent(req, res);	
			viewPage = "/member/cartContent.jsp";
		}
		
		else if(url.equals("/memberboardList.mem")) {
			System.out.println("/memberboardList.mem");
			
			gBoardService service = new gBoardServiceImpl();
			service.boardList(req, res);
			
			viewPage="/member/memberboardList.jsp";
		}
		
		//구매취소
		else if(url.equals("/paycancel.mem")) {
			System.out.println("/paycancel.mem");
			
			guestService service = new guestServiceImpl();
			service.paycancel(req,res);
			
			service.paylist(req, res);
			
			
			viewPage="/member/pay.jsp";
		}
		
		
		RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
		dispatcher.forward(req, res);
		
		
	}

}
