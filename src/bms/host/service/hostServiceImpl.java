package bms.host.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import bms.guest.persistence.guestCartDAO;
import bms.guest.persistence.guestCartDAOImpl;
import bms.guest.vo.guestCartVO;
import bms.host.persistence.hostBookDAO;
import bms.host.persistence.hostBookDAOImpl;
import bms.host.vo.hostBookVO;
import bms.host.vo.totalVO;


public class hostServiceImpl implements hostService{
	
	//å�߰� ó��������
	@Override
	public void host_stockaddPro(HttpServletRequest req, HttpServletResponse res) {
		MultipartRequest mr = null;
		int maxSize = 10 * 1024 * 1024; // ���ε��� ������ �ִ� ������(10MB)
		String saveDir = req.getRealPath("/host/images/");
		String readDir = "C:\\Dev\\workspace\\BMS_project\\WebContent\\host\\images\\";
		String encType = "UTF-8";
		
		 	
		try {
			mr = new MultipartRequest(req, saveDir, maxSize, encType, new DefaultFileRenamePolicy());
			
			FileInputStream fis = new FileInputStream(saveDir + mr.getFilesystemName("kimg"));
			FileOutputStream fos = new FileOutputStream(readDir + mr.getFilesystemName("kimg"));
			
			int data = 0; 
			while((data = fis.read()) != -1) {
				fos.write(data);
			}
			
			/*System.out.println("fis-->" + fis);
			System.out.println("fos-->" + fos);
			
			System.out.println("kimg-->" + mr.getFilesystemName("kimg"));
			System.out.println("bookName-->" + mr.getParameter("bookName"));
			System.out.println("author -->" + mr.getParameter("author"));
			System.out.println("publisher--> " +mr.getParameter("publisher") );
			System.out.println("content-->" + mr.getParameter("content"));
			System.out.println(Integer.parseInt(mr.getParameter("price")));
			System.out.println(mr.getParameter("bookforeign"));
			System.out.println(Integer.parseInt(mr.getParameter("count")));
			System.out.println(new Timestamp(System.currentTimeMillis()));*/
			
			fis.close();
			fos.close();
			
			//1. ���� �ٱ���(DTO)�� �����.
			hostBookVO dto = new hostBookVO();
			
			
			//2. ȭ�����κ��� �Է¹��� ������ ���� �ٱ���(DTO)�� ��´�.
			dto.setKimg(mr.getFilesystemName("kimg"));
			dto.setBookName(mr.getParameter("bookName"));
			dto.setAuthor(mr.getParameter("author"));
			dto.setPublisher(mr.getParameter("publisher"));
			dto.setContent(mr.getParameter("content"));
			dto.setPrice(Integer.parseInt(mr.getParameter("price")));
			dto.setBookforeign(mr.getParameter("bookforeign"));
			dto.setBookcount(Integer.parseInt(mr.getParameter("count")));
			dto.setReg_date(new Timestamp(System.currentTimeMillis()));
			
			hostBookDAO dao = hostBookDAOImpl.getInstance();
			
			int cnt = dao.insertBook(dto);
			
			System.out.println("å�߰���� --> " + cnt);
			req.setAttribute("cnt", cnt);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	//å���
	@Override
	public void host_stockList(HttpServletRequest req, HttpServletResponse res) {
		
		int pageSize = 10;    //�� �������� ����� �� ����
		int pageBlock = 3;   //�� ���� ������ ����
		
		int cnt=0;			 //�� ����
		int start=0;		 //���� ������ �۽��۹�ȣ
		int end=0;			 //���� ������ �۸�������ȣ
		int number=0;		 //����� �۹�ȣ
		String pageNum = null;  //��������ȣ
		int currentPage = 0;    //����������
		
		int pageCount = 0;		//������ ����
		int startPage = 0; 		//����������
		int endPage=0;  		//������ ������	
		
		//dao ��ü����(�̱���, ������)
		hostBookDAO dao = hostBookDAOImpl.getInstance();
		
		cnt = 0;
		cnt = dao.getArticleCnt();
		
		req.setAttribute("cnt", cnt);
		
		System.out.println("cnt : " + cnt);
		
		pageNum = req.getParameter("pageNum");
		
		if(pageNum == null) {
			pageNum = "1"; //ù������ 1�������� ����
		}
		
		currentPage = (Integer.parseInt(pageNum));   //����������
		
		pageCount = (cnt / pageSize) + (cnt % pageSize > 0 ? 1 : 0);
		
		start = (currentPage - 1) * pageSize +1;   //���������� ���۹�ȣ
		
		end = start + pageSize -1; //���������� ����ȣ
		
		System.out.println("start : " + start);
		System.out.println("end : " + end);
		
		if(end > cnt) end = cnt;
		
		number = cnt - (currentPage - 1) * pageSize; //����� �۹�ȣ... �ֽű�(ū ������)�� 1page 
		
		System.out.println("number : " + number);
		System.out.println("cnt : " + cnt);
		System.out.println("currentPage : " + currentPage);
		System.out.println("pageSize : " + pageSize);
		
		
		if(cnt > 0) {
			//�Խñ� ��� ��ȸ
			ArrayList<hostBookVO> dtos = dao.getArticleList(start, end);
			
			/*System.out.println("ù��°�ε��� : "+dtos.get(1));
			System.out.println("�ι�°�ε��� : "+dtos.get(2));
			System.out.println("����°�ε��� : "+dtos.get(3));*/
			
			
			req.setAttribute("dtos", dtos);  //ū�ٱ��� : �Խñ۸�� cf)�����ٱ��� : �Խñ� 1��
			
		}
		
		startPage = (currentPage / pageBlock) * pageBlock + 1; // (5/3) * 3 + 1 = 4
		if(currentPage % pageBlock == 0) startPage -= pageBlock;  //(5%3) == 0
		
		endPage = startPage + pageBlock -1 ;  // 4 + 3 - 1 = 6
		if(endPage > pageCount) endPage = pageCount;
		
		req.setAttribute("cnt", cnt); //å��
		req.setAttribute("number", number); //�۹�ȣ
		req.setAttribute("pageNum", pageNum); //��������ȣ
		
		if(cnt > 0) {
			req.setAttribute("startPage", startPage);  //����������
			req.setAttribute("endPage", endPage);//������������
			req.setAttribute("pageBlock", pageBlock);//����� ������ ����
			req.setAttribute("pageCount", pageCount);//������ ����
			req.setAttribute("currentPage", currentPage);//���� ������
			
		}
		
	}
	
	//host å ��������
	@Override
	public void bookcontentForm(HttpServletRequest req, HttpServletResponse res) {
		int bookNum = Integer.parseInt(req.getParameter("bookNum")); //���� �ѱ涧�� ������ Ű�� ���� �ѱ�� �޴´�.
		int pageNum = Integer.parseInt(req.getParameter("pageNum"));
		int number = Integer.parseInt(req.getParameter("number"));
		
		//dao ����(�̱���, ������)
		hostBookDAO dao = hostBookDAOImpl.getInstance();
		
		//�������� ��������..1��
		hostBookVO dto = dao.getArticle(bookNum);
		
		//jsp�� ���� �ѱ��. dto, pageNum, number
		req.setAttribute("dto", dto);
		req.setAttribute("pageNum", pageNum);
		req.setAttribute("number", number); //db�۹�ȣ�� �̴� �ѹ����� �۹�ȣ
		
	}
	
	//host å ���� ó��������
	@Override
	public void bookmodifyView(HttpServletRequest req, HttpServletResponse res) {
		int bookNum = Integer.parseInt(req.getParameter("bookNum")); 
		int pageNum = Integer.parseInt(req.getParameter("pageNum")); 
		
		//dao��ü����(�̱���, ������ ����)
		hostBookDAO dao = hostBookDAOImpl.getInstance();
		
		//�������� ��������..1��
		hostBookVO dto = dao.getArticle(bookNum);
		
		req.setAttribute("bookNum", bookNum);
		req.setAttribute("pageNum", pageNum);
		
	}
	
	//å ���� ó��������
	@Override
	public void bookmodifyPro(HttpServletRequest req, HttpServletResponse res) {
		
		MultipartRequest mr = null;
		int maxSize = 10 * 1024 * 1024; // ���ε��� ������ �ִ� ������(10MB)
		String saveDir = req.getRealPath("/host/images/");
		String readDir = "C:\\Dev\\workspace\\BMS_project\\WebContent\\host\\images\\";
		String encType = "UTF-8";
		
		try {
			int bookNum = Integer.parseInt(req.getParameter("bookNum"));
			int pageNum = Integer.parseInt(req.getParameter("pageNum"));
			
			//�ٱ��� ����
			hostBookVO dto = new hostBookVO();
			
			//�ٱ��Ͽ� ȭ�鿡�� �Է¹��� ������ ��´�.
			dto.setBookNum(bookNum);
			dto.setAuthor(mr.getParameter("author"));
			dto.setPublisher(mr.getParameter("publisher"));
			dto.setContent(mr.getParameter("content"));
			dto.setPrice(Integer.parseInt(mr.getParameter("price")));
			dto.setBookforeign(mr.getParameter("bookforeign"));
			dto.setBookcount(Integer.parseInt(mr.getParameter("bookcount")));
			
			//dao��ü����(�̱���, ������ ����)
			hostBookDAO dao = hostBookDAOImpl.getInstance();
			
			//����ó��
			int cnt = dao.updateBook(dto);
			
			req.setAttribute("bookNum", bookNum);
			req.setAttribute("pageNum", pageNum);
			req.setAttribute("cnt", cnt);
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	//å���� ó��������
	@Override
	public void bookdeletePro(HttpServletRequest req, HttpServletResponse res) {
		int bookNum = Integer.parseInt(req.getParameter("bookNum"));
		int pageNum = Integer.parseInt(req.getParameter("pageNum"));
		
		System.out.println("bookNum = " + bookNum);
		
		//dao��ü����(�̱���, ������ ����)
		hostBookDAO dao = hostBookDAOImpl.getInstance();
		
		int deleteCnt = dao.deleteBook(bookNum);
		req.setAttribute("deleteCnt", deleteCnt);
		req.setAttribute("pageNum", pageNum);
		
		System.out.println("deleteCnt = " + deleteCnt);
		
		
	}
	
	//å ���� ��������
	@Override
	public void bookdeleteForm(HttpServletRequest req, HttpServletResponse res) {
		int bookNum = (Integer)req.getAttribute("bookNum"); //���� �ѱ涧�� ������ Ű�� ���� �ѱ�� �޴´�.
		int pageNum = (Integer)req.getAttribute("pageNum");
		/*int number = Integer.parseInt(req.getParameter("number"));*/
		
		//dao ����(�̱���, ������)
		hostBookDAO dao = hostBookDAOImpl.getInstance();
		
		//�������� ��������..1��
		hostBookVO dto = dao.getArticle(bookNum);
		
		//jsp�� ���� �ѱ��. dto, pageNum, number
		req.setAttribute("dto", dto);
		req.setAttribute("pageNum", pageNum);
		
		System.out.println("Ž");
		/*req.setAttribute("number", number);*/ //db�۹�ȣ�� �̴� �ѹ����� �۹�ȣ
		
	}

	@Override
	public void orderlist(HttpServletRequest req, HttpServletResponse res) {

		
		hostBookDAO dao = hostBookDAOImpl.getInstance();
		int cnt = dao.payNum();
		
		ArrayList<guestCartVO> payselect = dao.getorderlist(cnt);

		req.setAttribute("cnt", cnt);
		req.setAttribute("dtos", payselect);
		
	}

	@Override
	public void delivery(HttpServletRequest req, HttpServletResponse res) {
		int payNum=Integer.parseInt(req.getParameter("payNum"));
		String name=req.getParameter("name");
		int count = Integer.parseInt(req.getParameter("count"));
		int price = Integer.parseInt(req.getParameter("price"));
		
		hostBookDAO dao = hostBookDAOImpl.getInstance();
		// �������� �������� ã�ƿ���
		String bookforeign= dao.selectforeign(name);
		
		// step 2���� ����
		dao.stepUpdate(payNum);
		
		// total ���̺� �� �߰�(����/���� å���ż���, �� �ݾ�)
		dao.total(bookforeign, price, count);
		
		
		
	}
	
	@Override
	public void hostrefund(HttpServletRequest req, HttpServletResponse res) {
		int bookNum = Integer.parseInt(req.getParameter("bookNum"));
		int bookcount = Integer.parseInt(req.getParameter("bookcount"));
		int price = Integer.parseInt(req.getParameter("price"));
		int payNum = Integer.parseInt(req.getParameter("payNum"));
		
		// 1. ȯ�ҽ� ������ ������Ű��
		hostBookDAO dao = hostBookDAOImpl.getInstance();
		dao.hostrefundbook(bookNum, bookcount);
		
		
		//----------------------------------------------------------------
		//2. �Ѱ�꿡�� ȯ���� ���ݸ�ŭ ���ֱ�
		dao.totalupdate(price);
		
		//3. step=>4�� �ٲٱ�
		dao.updatestep(payNum);
		
		//4. ȯ���ϴ� å�� �������� /�������� ��������
		String foreign = dao.foreigncheck(bookNum);
		
		//5. total���� foreign�� ������Ʈ ��Ű��
		dao.totalforeign(foreign, bookcount);
		
	}

	@Override
	public void finaltotal(HttpServletRequest req, HttpServletResponse res) {
		hostBookDAO dao = hostBookDAOImpl.getInstance();
		totalVO dto = dao.selecttotal();
		
		req.setAttribute("dto", dto);
		
	}

}
