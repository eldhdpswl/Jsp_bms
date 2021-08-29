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
	//컨넥션 객체를 보관
	DataSource datasource;
	
	//싱글톤 방식 : 객체를 1번만 생성하겠다.(private 객체 생성)
	private static gMemberDAOImpl instance = new gMemberDAOImpl();
	
	public static gMemberDAOImpl getInstance() {
		return instance;
	}
	
	//생성자
	public gMemberDAOImpl() {
		try {
			//컨넥션을(DBCP) : Servers > context.xml
			Context context = new InitialContext();
			datasource = (DataSource)context.lookup("java:comp/env/jdbc/Oracle11g_bms");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//중복확인체크
	@Override
	public int idCheck(String strId) {
		int cnt = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection(); //컨넥션 풀에 접근해라
			String sql="SELECT * FROM bms_member WHERE id=?";
			pstmt = conn.prepareStatement(sql); //?에 해당하는것을 설정
			pstmt.setString(1, strId);
			
			rs = pstmt.executeQuery();
			
			//입력받은 id가 DB에 존재하면 cnt=1, 즉 id중복 /존재하지않으면 cnt=0;
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
	
	//회원가입
	@Override
	public int insertMember(gMemberVO vo) {
		int cnt = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = datasource.getConnection();
			String sql = "INSERT INTO bms_member(id, pwd, name, jumin1, jumin2, hp, email, reg_date) values(?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			
			//getter를 이용해서 vo바구니에서 꺼내서 set했다
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
				System.out.println("Insert 성공");
				
			}else {
				System.out.println("Insert 실패");
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
	
	//체크
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
			
			rs = pstmt.executeQuery(); //한행의 레코드를 가지고있다.
			
			if(rs.next()) {
				//패스워드가 일치하면 cnt==1
				if(strPwd.equals(rs.getString("pwd"))) {
					cnt=1;
				}else {
					//패스워드가 일치하지 않으면 cnt= -1
					cnt=-1;
				}
			//로그인한 id에 해당하는  데이터가 없으면 cnt=0;	
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
	
	//회원탈퇴처리
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
				System.out.println("Delete 성공");
			}else {
				System.out.println("Delete 실패");
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
	
	//회원정보 상세페이지 - 아이디와 일치한 회원정보 가져오기(정보수정화면 제공)
	@Override
	public gMemberVO getMemberInfo(String strId) {
		//1. 바구니 생성
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
			
			//2. ResultSet에 id에 해당하는 1사람의 회원정보가 존재하면
			if(rs.next()) {
				//2-1. ResultSet을 읽어서 바구니에 감는다.
				vo.setId(rs.getString("id")); //vo.setId(id컬럼에 해당하는 값)
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
