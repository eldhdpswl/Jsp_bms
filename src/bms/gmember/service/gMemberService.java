package bms.gmember.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface gMemberService {
	//�ߺ�Ȯ��
	public void confirmId(HttpServletRequest req, HttpServletResponse res);
	
	//ȸ������ó��
	public void inputPro(HttpServletRequest req, HttpServletResponse res);
	
	//�α��� ó��
	public void loginPro(HttpServletRequest req, HttpServletResponse res);
	
	//ȸ��Ż��ó��
	public void deletePro(HttpServletRequest req, HttpServletResponse res);
	
	//ȸ���������� ���� �� ������
	public void modifyView(HttpServletRequest req, HttpServletResponse res);
	
	//ȸ������ ����
	public void modifyPro(HttpServletRequest req, HttpServletResponse res);
	
}
