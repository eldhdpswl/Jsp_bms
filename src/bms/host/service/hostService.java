package bms.host.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface hostService {
	
	//å�߰� ó��������
	public void host_stockaddPro(HttpServletRequest req, HttpServletResponse res);
	
	//å���
	public void host_stockList(HttpServletRequest req, HttpServletResponse res);
	
	//��������
	public void bookcontentForm(HttpServletRequest req, HttpServletResponse res);
	
	//å���� ��������
	public void bookmodifyView(HttpServletRequest req, HttpServletResponse res);
	
	//å ���� ó��������
	public void bookmodifyPro(HttpServletRequest req, HttpServletResponse res);
	
	//å ���� ��������
	public void bookdeleteForm(HttpServletRequest req, HttpServletResponse res);
	
	//å ���� ó��������
	public void bookdeletePro(HttpServletRequest req, HttpServletResponse res);
	
	//�ֹ���û ó�� ������
	public void orderlist(HttpServletRequest req, HttpServletResponse res);
	
	public void delivery(HttpServletRequest req, HttpServletResponse res);
	
	public void hostrefund(HttpServletRequest req, HttpServletResponse res);
	
	public void finaltotal(HttpServletRequest req, HttpServletResponse res);
	
	
}
