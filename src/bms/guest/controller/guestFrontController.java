package bms.guest.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bms.guest.service.guestService;
import bms.guest.service.guestServiceImpl;



@WebServlet("*.gst")
public class guestFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public guestFrontController() {
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
		
		//한글 안깨지게 처리..안하면 DB에 한글이 깨져서 insert
		req.setCharacterEncoding("UTF-8");
		
		String viewPage = null;
		String uri = req.getRequestURI();
		String contextPath = req.getContextPath();
		String url = uri.substring(contextPath.length());
		
		//cart 책목록불러오기
		if(url.equals("/cart.gst")) {
			System.out.println("/cart.gst");
			
			guestService service = new guestServiceImpl();
			service.stockList(req, res);
			
			viewPage="/member/cart.jsp";
		}
		
		//cart 책목록에서  상세페이지
		else if(url.equals("/cartContent.gst")) {
			System.out.println("/cartContent.gst");
			
			guestService service = new guestServiceImpl();
			service.stockcontent(req, res);
			
			viewPage = "/member/cartContent.jsp";
		}
		
		
		
		
		
		
		
		
	}

}
