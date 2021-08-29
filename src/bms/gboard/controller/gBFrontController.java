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
		//�ѱ� �ȱ����� ó��..���ϸ� DB�� �ѱ��� ������ insert
		req.setCharacterEncoding("UTF-8");
		
		String viewPage = null;
		String uri = req.getRequestURI();
		String contextPath = req.getContextPath();
		String url = uri.substring(contextPath.length());
		
		//�۸��
		if(url.equals("/boardList.bo")) {
			System.out.println("/boardList.bo");
			
			gBoardService service = new gBoardServiceImpl();
			service.boardList(req, res);
			
			viewPage = "/board/boardList.jsp";
		
		}
		
		//��������
		else if(url.equals("/b_contentForm.bo")) {
			System.out.println("/b_contentForm.bo");
			
			gBoardService service = new gBoardServiceImpl();
			service.b_contentForm(req, res);
			
			viewPage = "/board/b_contentForm.jsp";
			
		}
		
		//���������ȿ��� �ۼ��������� 
		//�ۼ����� ������
		else if(url.equals("/b_modifyForm.bo")) {
			System.out.println("/b_modifyForm.bo");
			
			int num = Integer.parseInt(req.getParameter("num"));
			int pageNum = Integer.parseInt(req.getParameter("pageNum"));
			
			req.setAttribute("num", num);
			req.setAttribute("pageNum", pageNum);
			
			gBoardService service = new gBoardServiceImpl();
			
			viewPage = "/board/b_modifyForm.jsp";
			
		}
		
		//�ۼ��� ��������
		else if(url.equals("/b_modifyView.bo")) {
			System.out.println("/b_modifyView.bo");
			
			gBoardService service = new gBoardServiceImpl();
			service.b_modifyView(req, res);
			
			
			viewPage = "/board/b_modifyView.jsp";
			
		}
		
		//�ۼ��� ó��������
		else if(url.equals("/b_modifyPro.bo")) {
			System.out.println("/b_modifyPro.bo");
			
			gBoardService service = new gBoardServiceImpl();
			service.b_modifyPro(req, res);
			
			viewPage = "/board/b_modifyPro.jsp";
		}
		
		//�۾����� ������(��۾���)
		else if(url.equals("/b_writeForm.bo")) {
			System.out.println("/b_writeForm.bo");
			
			//�����(�亯���� �ƴ� ���)
			int num = 0;
			int ref = 1;	//�׷�ȭ ���̵�
			int ref_step = 0;  // �ۼ���(��)
			int ref_level = 0; // �۷���(�鿩����)
			
			//�亯��
			//contentForm.jsp���� get������� �ѱ� �� num, ref, ref_step, ref_level
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
		
		//�۾��� ó��������
		else if(url.equals("/b_writePro.bo")) {
			System.out.println("/b_writePro.bo");
			
			gBoardService service = new gBoardServiceImpl();
			service.b_writePro(req, res);

			viewPage="/board/b_writePro.jsp";
		}
		
		//�ۻ����� ������
		else if(url.equals("/b_deleteForm.bo")) {
			System.out.println("/b_deleteForm.bo");
			
			//contentForm.jsp���� ������ư Ŭ��������  deleteForm.jsp?num=${dto.num}&pageNum=${pageNum}
			
			int num = Integer.parseInt(req.getParameter("num"));
			int pageNum = Integer.parseInt(req.getParameter("pageNum"));
			
			req.setAttribute("num", num);
			req.setAttribute("pageNum", pageNum);
			
			
			viewPage="/board/b_deleteForm.jsp";
			
		}
		
		//�ۻ��� ó��������
		else if(url.equals("/b_deletePro.bo")) {
			System.out.println("/b_deletePro.bo");
			
			gBoardService service = new gBoardServiceImpl();
			service.b_deletePro(req, res);
			
			viewPage = "/board/b_deletePro.jsp";
		
		}
		
		RequestDispatcher dispatcher = req.getRequestDispatcher(viewPage);  //viewPage������ forward�ϱ����� ����.
		dispatcher.forward(req, res);
		
	}

	
	
	
	
	
	
}
