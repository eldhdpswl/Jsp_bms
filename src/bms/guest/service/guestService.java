package bms.guest.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface guestService {
	
	//å���
	public void stockList(HttpServletRequest req, HttpServletResponse res);
	
	//��������
	public void stockcontent(HttpServletRequest req, HttpServletResponse res);
	
	//��ٱ��� å��� ó��������
	public void cartTakePro(HttpServletRequest req, HttpServletResponse res);
	
	//�� ��ٱ��� ���
	public void mycartlist(HttpServletRequest req, HttpServletResponse res);
	
	//checkbox�� üũ�Ҷ� ���Ŵ� 
	public void pay(HttpServletRequest req, HttpServletResponse res);
	
	//���Ÿ�� ����Ʈ �ҷ�����
	public void paylist(HttpServletRequest req, HttpServletResponse res);
	
	//���Ÿ�Ͽ��� �����ϱ� ��������
	public void payPro(HttpServletRequest req, HttpServletResponse res);
	
	//ȯ���ϱ�
	public void payNum(HttpServletRequest req, HttpServletResponse res);
	
	public void directpay(HttpServletRequest req, HttpServletResponse res);

	public void selectBCount(HttpServletRequest req, HttpServletResponse res);
	
	public void paycancel(HttpServletRequest req, HttpServletResponse res);
	
	
	
}
