package bms.gmember.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import bms.gmember.vo.gMemberVO;



public class gMemberDAOImpl implements gMemberDAO{
	//���ؼ� ��ü�� ����
	DataSource datasource;
	
	//�̱��� ��� : ��ü�� 1���� �����ϰڴ�.(private ��ü ����)
	private static gMemberDAOImpl instance = new gMemberDAOImpl();
	
	public static gMemberDAOImpl getInstance() {
		return instance;
	}
	
	//������
	public gMemberDAOImpl() {
		try {
			//���ؼ���(DBCP) : Servers > context.xml
			Context context = new InitialContext();
			datasource = (DataSource)context.lookup("java:comp/env/jdbc/Oracle11g_bms");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//�ߺ�Ȯ��üũ
	@Override
	public int idCheck(String strId) {
		int cnt = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection(); //���ؼ� Ǯ�� �����ض�
			String sql="SELECT * FROM bms_member WHERE id=?";
			pstmt = conn.prepareStatement(sql); //?�� �ش��ϴ°��� ����
			pstmt.setString(1, strId);
			
			rs = pstmt.executeQuery();
			
			//�Է¹��� id�� DB�� �����ϸ� cnt=1, �� id�ߺ� /�������������� cnt=0;
			if(rs.next()) {
				cnt=1;
			}
			System.out.println("dao--->" + cnt);
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return cnt;
	}
	
	//ȸ������
	@Override
	public int insertMember(gMemberVO vo) {
		int cnt = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = datasource.getConnection();
			String sql = "INSERT INTO bms_member(id, pwd, name, jumin1, jumin2, hp, email, reg_date) values(?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			
			//getter�� �̿��ؼ� vo�ٱ��Ͽ��� ������ set�ߴ�
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPwd());
			pstmt.setString(3, vo.getName());
			pstmt.setString(4, vo.getJumin1());
			pstmt.setString(5, vo.getJumin2());
			pstmt.setString(6, vo.getHp());
			pstmt.setString(7, vo.getEmail());
			pstmt.setTimestamp(8, vo.getReg_date());
			
			cnt = pstmt.executeUpdate();
			
			if(cnt==1) {
				System.out.println("Insert ����");
				
			}else {
				System.out.println("Insert ����");
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return cnt;
	}
	
	//üũ
	@Override
	public int check(String strId, String strPwd) {
		int cnt = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			
			String sql = "SELECT * FROM bms_member WHERE id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, strId);
			
			rs = pstmt.executeQuery(); //������ ���ڵ带 �������ִ�.
			
			if(rs.next()) {
				//�н����尡 ��ġ�ϸ� cnt==1
				if(strPwd.equals(rs.getString("pwd"))) {
					cnt=1;
				}else {
					//�н����尡 ��ġ���� ������ cnt= -1
					cnt=-1;
				}
			//�α����� id�� �ش��ϴ�  �����Ͱ� ������ cnt=0;	
			}else {
				cnt=0;
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return cnt;
	}
	
	//ȸ��Ż��ó��
	@Override
	public int memberDelete(String strId) {
		int cnt=0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = datasource.getConnection();
			
			String sql = "DELETE FROM bms_member WHERE id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, strId);
			
			cnt = pstmt.executeUpdate();
			/*
			if(cnt==1) {
				System.out.println("Delete ����");
			}else {
				System.out.println("Delete ����");
			}*/
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return cnt;
	}
	
	//ȸ������ �������� - ���̵�� ��ġ�� ȸ������ ��������(��������ȭ�� ����)
	@Override
	public gMemberVO getMemberInfo(String strId) {
		//1. �ٱ��� ����
		gMemberVO vo = new gMemberVO();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			String sql = "SELECT * FROM mvc_member WHERE id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, strId);
			rs = pstmt.executeQuery();
			
			//2. ResultSet�� id�� �ش��ϴ� 1����� ȸ�������� �����ϸ�
			if(rs.next()) {
				//2-1. ResultSet�� �о �ٱ��Ͽ� ���´�.
				vo.setId(rs.getString("id")); //vo.setId(id�÷��� �ش��ϴ� ��)
				vo.setPwd(rs.getString("pwd"));
				vo.setName(rs.getString("name"));
				vo.setJumin1(rs.getString("jumin1"));
				vo.setJumin2(rs.getString("jumin2"));
				vo.setHp(rs.getString("hp"));
				vo.setEmail(rs.getString("email"));
				vo.setReg_date(rs.getTimestamp("reg_date"));
				
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return vo;
	}

	@Override
	public int updateMember(gMemberVO vo) {
		int cnt=0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = datasource.getConnection();
			
			String sql = "UPDATE bms_member SET pwd=?, name=?, hp=?, email=? WHERE id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getPwd());
			pstmt.setString(2, vo.getName());
			pstmt.setString(3, vo.getHp());
			pstmt.setString(4, vo.getEmail());
			pstmt.setString(5, vo.getId());
			
			cnt = pstmt.executeUpdate();
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return cnt;
		
	}

}
