package bms.host.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bms.gboard.service.gBoardService;
import bms.gboard.service.gBoardServiceImpl;
import bms.guest.service.guestService;
import bms.guest.service.guestServiceImpl;
import bms.host.service.hostService;
import bms.host.service.hostServiceImpl;


@WebServlet("*.host")
public class hostFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public hostFrontController() {
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
			
			guestService service1 = new guestServiceImpl();
			service1.stockList(req, res);
			
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
			
			hostService service = new hostServiceImpl();
			service.host_stockList(req, res);
			
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
			
			hostService service = new hostServiceImpl();
			service.host_stockaddPro(req, res);
			
			viewPage = "/host/host_stockaddPro.jsp";
		}
		
		//host å ��������
		else if(url.equals("/host_stockcontent.host")) {
			System.out.println("/host_stockcontent.host");
			
			hostService service = new hostServiceImpl();
			service.bookcontentForm(req, res);
			
			viewPage = "/host/host_stockcontent.jsp";
		}
		
		
		//host å ����
		else if(url.equals("/host_stockmodify.host")) {
			System.out.println("/host_stockmodify.host");
			
			int bookNum = Integer.parseInt(req.getParameter("bookNum"));
			int pageNum = Integer.parseInt(req.getParameter("pageNum"));
			
			req.setAttribute("bookNum", bookNum);
			req.setAttribute("pageNum", pageNum);
			
			hostService service = new hostServiceImpl();
			
			viewPage = "/host/host_stockmodifyForm.jsp";
		}
		
		//host å ���� ��������
		else if(url.equals("/host_stockmodifyView.host")) {
			System.out.println("/host_stockmodifyView.host");
			
			hostService service = new hostServiceImpl();
			service.bookmodifyView(req, res);
			
			viewPage= "/host/host_stockmodifyView.jsp";
		}
		
		//host å ���� ó��������
		else if(url.equals("/host_stockmodifyPro.host")) {
			System.out.println("/host_stockmodifyPro.host");
			
			hostService service = new hostServiceImpl();
			service.bookmodifyPro(req, res);
			
			viewPage="/host/host_stockmodifyPro.jsp";
		}
		
		
		//host å ������ ������
		else if(url.equals("/host_stockdeleteForm.host")) {
			System.out.println("/host_stockdeleteForm.host");
			
			//contentForm.jsp���� ������ư Ŭ��������  deleteForm.jsp?num=${dto.num}&pageNum=${pageNum}
			
			int bookNum = Integer.parseInt(req.getParameter("bookNum"));
			int pageNum = Integer.parseInt(req.getParameter("pageNum"));
			
			req.setAttribute("bookNum", bookNum);
			req.setAttribute("pageNum", pageNum);
			
			hostService service = new hostServiceImpl();
			service.bookdeleteForm(req, res);
			
			viewPage="/host/host_stockdeleteForm.jsp";
			
		}
		
		//host å���� ó��������
		else if(url.equals("/host_stockdeletePro.host")) {
			System.out.println("/host_stockdeletePro.host");
			
			hostService service = new hostServiceImpl();
			service.bookdeletePro(req, res);
			
			viewPage = "/host/host_stockdeletePro.jsp";
			
		}
		
		else if(url.equals("/orderlist.host")) {
			System.out.println("/orderlist.host");
			hostService service = new hostServiceImpl();
			
			service.orderlist(req, res);
			
			viewPage = "/host/orderlist.jsp";
		}
		else if(url.equals("/delivery.host")) {
			System.out.println("/delivery.host");
			hostService service = new hostServiceImpl();
			
			service.delivery(req, res);
			
			service.orderlist(req, res);
			viewPage = "/host/orderlist.jsp";
		}
		
		else if(url.equals("/hostrefund.host")) {
			System.out.println("/hostrefund.host");
			
			hostService service = new hostServiceImpl();
			service.hostrefund(req, res);
			
			
			service.orderlist(req, res);
			viewPage = "/host/orderlist.jsp";
		}
		
		else if(url.equals("/finaltotal.host")) {
			System.out.println("/finaltotal.host");
			
			hostService service = new hostServiceImpl();
			service.finaltotal(req, res);
			
			
			viewPage="/host/finaltotal.jsp";
		}
		
		
		RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);
		dispatcher.forward(req, res);
	}

}
