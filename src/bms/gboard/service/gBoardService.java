package bms.gboard.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface gBoardService {
	
	//�۸��
	public void boardList(HttpServletRequest req, HttpServletResponse res);
	
	//��������
	public void b_contentForm(HttpServletRequest req, HttpServletResponse res);
	
	//�ۼ��� ��������
	public void b_modifyView(HttpServletRequest req, HttpServletResponse res);
	
	//�ۼ��� ó��������
	public void b_modifyPro(HttpServletRequest req, HttpServletResponse res);
	
	//���ۼ� ó��������
	public void b_writePro(HttpServletRequest req, HttpServletResponse res);
	
	//�ۻ��� ó��������
	public void b_deletePro(HttpServletRequest req, HttpServletResponse res);
	
	
}
