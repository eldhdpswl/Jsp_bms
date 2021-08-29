package bms.hmember.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bms.gboard.service.gBoardService;
import bms.gboard.service.gBoardServiceImpl;


@WebServlet("*.host")
public class hMFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public hMFrontController() {
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
		
		//�ѱ� �ȱ����� ó��..���ϸ� DB�� �ѱ��� ������ insert
		req.setCharacterEncoding("UTF-8");
		
		String viewPage = null;
		String uri = req.getRequestURI();
		String contextPath = req.getContextPath();
		String url = uri.substring(contextPath.length());
		
		//host ����������
		if(url.equals("/hostMain.host")) {
			System.out.println("/hostMain.host");
			
			viewPage = "/host/hostMain.jsp";
			
		}
		
		//host �α׾ƿ�
		else if(url.equals("/logout.host")) {
			System.out.println("/logout.host");
			
			req.getSession().setAttribute("memId", null);
			req.setAttribute("cnt", 2);
			
			viewPage ="/member/main.jsp";
		}
		
		//host �Խ���
		else if(url.equals("/hostBoardList.host")) {
			System.out.println("/hostBoardList.host");
			
			gBoardService service = new gBoardServiceImpl();
			service.boardList(req, res);
			
			
			viewPage = "/host/hostBoardList.jsp";
			
		}
		
		//host ��������
		else if(url.equals("/hostContentForm.host")) {
			System.out.println("/hostContentForm.host");
		
			gBoardService service = new gBoardServiceImpl();
			service.boardList(req, res);
			
			viewPage = "/host/hostContentForm.jsp";
		}
		
		//host ������
		else if(url.equals("/host_stockMain.host")) {
			System.out.println("/host_stockMain.host");
			
			viewPage = "/host/host_stockMain.jsp";
		}
		
		//host å ���
		else if(url.equals("/host_stockList.host")) {
			System.out.println("/host_stockList.host");
			
			viewPage = "/host/host_stockList.jsp";
		}
		
		//host å �߰�
		else if(url.equals("/host_stockaddForm.host")) {
			System.out.println("/host_stockaddForm.host");
			
			
			viewPage = "/host/host_stockaddForm.jsp";
		}
		
		//host å �߰� ó��������
		else if(url.equals("/host_stockaddPro.host")) {
			System.out.println("/host_stockaddPro.host");
			
			
			viewPage = "/host/host_stockaddPro.jsp";
		}
		
		
		//host å ����
		
		//host å ����
		
		
		RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
		dispatcher.forward(req, res);
	}

}
