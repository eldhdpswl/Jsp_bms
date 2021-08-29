package bms.guest.persistence;

import java.util.ArrayList;

import bms.guest.vo.cartAddVO;
import bms.guest.vo.guestCartVO;


public interface guestCartDAO {
	
	//å���� ���ϱ�
	public int getArticleCnt();
	
	//å��� ��� ��ȸ
	public ArrayList<guestCartVO> getArticleList(int start, int end);
	
	//��������
	public guestCartVO getArticle(int bookNum);
	
	//��ٱ��� cart���̺� �߰�
	public int insertCart(cartAddVO cartdto); 
	
	//��ٱ��� å ���� ���ϱ�
	public int getCartArticleCnt();
	
	//��ٱ��� ��� ��ȸ
	public ArrayList<guestCartVO> getCartArticleList(int cnt, String memId);
	
	//���Ŵ����� �ѱ涧 ��ٱ��� ��� ��ȸ�ϱ�
	public ArrayList<guestCartVO> getCartInfo(String[] cartNum);
	
/*	//���̳� �� ��������
	public int pay_seq();*/
	
	//�����ͼ� ��������
	public int payNum(String memid);
	
	//pay���̺� �߰�
	public String insertpay(ArrayList<guestCartVO> list);
	
	//cart���̺� �ִ� �������� delete
	public int deletecart(ArrayList<guestCartVO> delist);
	
	//pay�����ҷ�����
	public ArrayList<guestCartVO> getPayList(String memId,int cnt);
	
	//���Ÿ�� å ���� ���ϱ�
	public int getPayArticleCnt();
	
	//��ٱ��� ��� ��ȸ
	public ArrayList<guestCartVO> getPayArticleList(int cnt, String memId);
	
	/*//���Ÿ�Ͽ��� �����ϱ��Ҷ� pay���ִ� ��Ϻҷ�����
	public ArrayList<guestCartVO> getPaymentList();*/
	
	//���Ÿ�Ͽ��� �����ϱ��Ҷ� step update�� �κ�
	public int updatepay(guestCartVO paydto);
	
	public void bookCountUpdate(ArrayList<guestCartVO> dtos);
	
	//ȯ���ϱ�update
	public int refund(int payNum);
	
	public int insertdirectpay(int bookNum, int bookcount, String id);
	
	public int directpayupdate(int bookNum, int bookcount);
	
	public int selectBCount(int bookNum,int bookcount);
	
	//��������Ҷ� ���� ���� update
	public int paycancel(int bookNum, int bookcount);
	
	//��������ҋ� pay���� list����
	public int paylistdelete(int payNum);
}
