package bms.gmember.persistence;

import bms.gmember.vo.gMemberVO;

public interface gMemberDAO {
	//�ߺ�Ȯ��üũ
	public int idCheck(String strId);
	
	//ȸ������
	public int insertMember(gMemberVO vo);
	
	//�α��� ó��
	public int check(String strId, String strPwd);
	
	//ȸ��Ż�� ó��
	public int memberDelete(String strId);
	
	//ȸ������ ��������
	public gMemberVO getMemberInfo(String strId);
	
	//ȸ������ ����
	public int updateMember(gMemberVO vo);
	
	
	
	
	
}
