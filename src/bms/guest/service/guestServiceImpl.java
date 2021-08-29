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

	// å���
	@Override
	public void stockList(HttpServletRequest req, HttpServletResponse res) {

		int pageSize = 9; // �� �������� ����� �� ����
		int pageBlock = 3; // �� ���� ������ ����

		int cnt = 0; // �� ����
		int start = 0; // ���� ������ �۽��۹�ȣ
		int end = 0; // ���� ������ �۸�������ȣ
		int number = 0; // ����� �۹�ȣ
		String pageNum = null; // ��������ȣ
		int currentPage = 0; // ����������

		int pageCount = 0; // ������ ����
		int startPage = 0; // ����������
		int endPage = 0; // ������ ������

		guestCartDAO dao = guestCartDAOImpl.getInstance();

		cnt = 0;
		cnt = dao.getArticleCnt();

		req.setAttribute("cnt", cnt);

		System.out.println("cnt : " + cnt);

		pageNum = req.getParameter("pageNum");

		if (pageNum == null) {
			pageNum = "1"; // ù������ 1�������� ����
		}

		currentPage = (Integer.parseInt(pageNum)); // ����������

		pageCount = (cnt / pageSize) + (cnt % pageSize > 0 ? 1 : 0);

		start = (currentPage - 1) * pageSize + 1; // ���������� ���۹�ȣ

		end = start + pageSize - 1; // ���������� ����ȣ

		System.out.println("start : " + start);
		System.out.println("end : " + end);

		if (end > cnt)
			end = cnt;

		number = cnt - (currentPage - 1) * pageSize; // ����� �۹�ȣ... �ֽű�(ū ������)�� 1page

		System.out.println("number : " + number);
		System.out.println("cnt : " + cnt);
		System.out.println("currentPage : " + currentPage);
		System.out.println("pageSize : " + pageSize);

		if (cnt > 0) {
			// �Խñ� ��� ��ȸ
			ArrayList<guestCartVO> dtos = dao.getArticleList(start, end);

			/*
			 * System.out.println("ù��°�ε��� : "+dtos.get(1));
			 * System.out.println("�ι�°�ε��� : "+dtos.get(2));
			 * System.out.println("����°�ε��� : "+dtos.get(3));
			 */

			req.setAttribute("dtos", dtos); // ū�ٱ��� : �Խñ۸�� cf)�����ٱ��� : �Խñ� 1��

		}

		startPage = (currentPage / pageBlock) * pageBlock + 1; // (5/3) * 3 + 1 = 4
		if (currentPage % pageBlock == 0)
			startPage -= pageBlock; // (5%3) == 0

		endPage = startPage + pageBlock - 1; // 4 + 3 - 1 = 6
		if (endPage > pageCount)
			endPage = pageCount;

		req.setAttribute("cnt", cnt); // å��
		req.setAttribute("number", number); // �۹�ȣ
		req.setAttribute("pageNum", pageNum); // ��������ȣ

		if (cnt > 0) {
			req.setAttribute("startPage", startPage); // ����������
			req.setAttribute("endPage", endPage);// ������������
			req.setAttribute("pageBlock", pageBlock);// ����� ������ ����
			req.setAttribute("pageCount", pageCount);// ������ ����
			req.setAttribute("currentPage", currentPage);// ���� ������

		}

	}

	// ��������
	@Override
	public void stockcontent(HttpServletRequest req, HttpServletResponse res) {

		int bookNum = Integer.parseInt(req.getParameter("bookNum")); // ���� �ѱ涧�� ������ Ű�� ���� �ѱ�� �޴´�.


		// dao ����(�̱���, ������)
		guestCartDAO dao = guestCartDAOImpl.getInstance();

		// �������� ��������..1��
		guestCartVO dto = dao.getArticle(bookNum);

		// jsp�� ���� �ѱ��. dto, pageNum, number

		req.setAttribute("dto", dto);
 // db�۹�ȣ�� �̴� �ѹ����� �۹�ȣ

	}

	// ��ٱ��� å��� ó��������
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

	// �� ��ٱ��� ���
	@Override
	public void mycartlist(HttpServletRequest req, HttpServletResponse res) {

		String memId = (String) req.getSession().getAttribute("memId");

		guestCartDAO dao = guestCartDAOImpl.getInstance();

		int cnt = dao.getCartArticleCnt();

		ArrayList<guestCartVO> dtos = dao.getCartArticleList(cnt, memId);

		req.setAttribute("cnt", cnt);
		req.setAttribute("dtos", dtos);

	}

	// checkbox�� üũ�Ҷ� ���Ŵ�
	@Override
	public void pay(HttpServletRequest req, HttpServletResponse res) {
		
		
		String[] cartNum = req.getParameterValues("cartNum");
		
		guestCartDAO dao = guestCartDAOImpl.getInstance();
		ArrayList<guestCartVO> dtos = dao.getCartInfo(cartNum);
		
		String memid= dao.insertpay(dtos);
		// ��� ������Ʈ
		dao.bookCountUpdate(dtos);
		
		//���̿� �ִ� �ش� ���̵��� ������ ���� ������?? ����Ʈ�� �Ѹ� ��̸���Ʈ ������� ����
		int cnt=dao.payNum(memid);
		
	
			int deleteCnt = dao.deletecart(dtos);
			if(deleteCnt != 0) {
				ArrayList<guestCartVO> payselect = dao.getPayList(memid,cnt);	
				req.setAttribute("dtos", payselect);
			}
		
		req.setAttribute("cnt", cnt);
		
		
	}
	
	//���Ÿ�� ����Ʈ �ҷ�����
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

	//���Ÿ�Ͽ��� �����ϱ� ��������
	@Override
	public void payPro(HttpServletRequest req, HttpServletResponse res) {
		System.out.println("payPro�����������");
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
	
	//ȯ���ϱ�
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
		//1. pay�� �����ֱ�
		int cnt = dao.insertdirectpay(bookNum, bookcount, id);
		
		//2. book���� �����
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
	
	//�������
	@Override
	public void paycancel(HttpServletRequest req, HttpServletResponse res) {
		int bookNum = Integer.parseInt(req.getParameter("bookNum"));
		int bookcount = Integer.parseInt(req.getParameter("bookcount"));
		int payNum = Integer.parseInt(req.getParameter("payNum"));
		
		System.out.println("bookNum : "+ bookNum);
		System.out.println("bookcount : " + bookcount);
		
		guestCartDAO dao = guestCartDAOImpl.getInstance();
		//1.��������ҋ� ������Ʈ
		int paycancelcnt = dao.paycancel(bookNum, bookcount);
		
		//2. ��������ҋ� pay�κ� delete
		int paydeletecnt= dao.paylistdelete(payNum);
		
		int Dcnt=0;
		
		
		if(paycancelcnt !=0 &&paydeletecnt !=0) {
			Dcnt=1;
		}
		
		req.setAttribute("Dcnt", Dcnt);
		
	}

	

}
