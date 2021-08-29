package bms.gboard.persistence;

import java.util.ArrayList;

import bms.gboard.vo.gBoardVO;


public interface gBoardDAO {
	
	//�۰��� ���ϱ�
	public int getArticleCnt();
	
	//�Խñ� ��� ��ȸ
	public ArrayList<gBoardVO> getArticleList(int start, int end);
	
	//��������
	public gBoardVO getArticle(int num);
	
	//��ȸ�� ����
	public void addReadCnt(int num);
	
	//��й�ȣ Ȯ��(�Խñ� ����, �Խñ� ����)
	public int pwdCheck(int num, String strPwd);
	
	//�Խñ� ����
	public int updateBoard(gBoardVO dto);
	
	//�Խñ� �ۼ�
	public int insertBoard(gBoardVO dto);
	
	//�Խñ� ����
	public int deleteBoard(int num);
	
	
	
	
}
