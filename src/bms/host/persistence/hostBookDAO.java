package bms.host.persistence;

import java.util.ArrayList;

import bms.guest.vo.guestCartVO;
import bms.host.vo.hostBookVO;
import bms.host.vo.totalVO;



public interface hostBookDAO {
	
	//�Խñ� �ۼ�, å�̹��� �߰�
	public int insertBook(hostBookVO dto);
	
	//�۰��� ���ϱ�
	public int getArticleCnt();
	
	//å��� ��� ��ȸ
	public ArrayList<hostBookVO> getArticleList(int start, int end);
	
	//��������
	public hostBookVO getArticle(int bookNum);
	
	//��й�ȣ Ȯ��(�Խñ� ����, �Խñ� ����)
	public int pwdCheck(int bookNum, String strPwd);
	
	//å���� ����
	public int updateBook(hostBookVO dto);
	
	//å ����
	public int deleteBook(int bookNum);
	
	public ArrayList<guestCartVO> getorderlist(int cnt);
	
	public int payNum();
	
	public String selectforeign(String name);
	
	public void stepUpdate(int payNum);
	
	public void total(String bookforeign, int price, int count);/*bookforeign, price, count*/
	
	public void hostrefundbook(int bookNum, int bookcount);
	
	public void totalupdate(int price);
	
	public void updatestep(int payNum);
	
	public String foreigncheck(int bookNum);
	
	public void totalforeign(String foreign, int bookcount);
	
	public totalVO selecttotal();
	
}
