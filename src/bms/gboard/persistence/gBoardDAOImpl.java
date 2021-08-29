package bms.gboard.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import bms.gboard.vo.gBoardVO;




public class gBoardDAOImpl implements gBoardDAO{
	
	//���ؼ�Ǯ ��ü ����
	DataSource datasource;
	
	//�̱��� ������� ��ü�� ����
	private static gBoardDAOImpl instance = new gBoardDAOImpl();
	
	public static gBoardDAOImpl getInstance() {
		return instance;
	}
	
	//������
	public gBoardDAOImpl() {
		try {
			//���ؼ���(DBCP) : Servers > context.xml
			Context context = new InitialContext();
			datasource = (DataSource)context.lookup("java:comp/env/jdbc/Oracle11g_bms");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//�۰��� ���ϱ�
	@Override
	public int getArticleCnt() {
		int cnt = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			String sql="SELECT COUNT(*) FROM bms_board";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				cnt = rs.getInt(1);
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
	
	//�Խñ� ��� ��ȸ
	@Override
	public ArrayList<gBoardVO> getArticleList(int start, int end) {
		ArrayList<gBoardVO> dtos = null;  //ū�ٱ���
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			String sql = "SELECT * " + 
					     "FROM(SELECT num, writer, pwd, subject, content, readCnt, \r\n" + 
					    			 "ref, ref_step, ref_level, reg_date, rownum rNum\r\n" + 
					     		"FROM (" + 
					     				"SELECT * FROM bms_board " + 
					     				"ORDER BY ref DESC, ref_step ASC" +  //1. �ֽűۺ��� SELECT�Ѵ�.
					     			 ")" + //2. �ֽűۺ��� SELECT�� ���ڵ忡 rowNum�� �߰��Ѵ�.(���������� ������ ���������͸� �ֽűۺ��� �ѹ����Ѵ�.)
					     	 ") " + 
					     "WHERE rNUM >= ? AND rNUM <= ?"; //3. �Ѱܹ��� start���� end������ rowNum�� ��ȸ�Ѵ�. 
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				//1. ū�ٱ��� ����(dtos)
				dtos = new ArrayList<gBoardVO>(end - start + 1);
				
				do {
					//2. �����ٱ��� ����
					gBoardVO dto = new gBoardVO();
					
					//3. �Խñ� 1���� �о rs�� �����ٱ���(dto)�� ��ƶ�
					dto.setNum(rs.getInt("num"));
					dto.setWriter(rs.getString("writer"));
					dto.setPwd(rs.getString("pwd"));
					dto.setSubject(rs.getString("subject"));
					dto.setContent(rs.getString("content"));
					dto.setReadCnt(rs.getInt("readCnt"));
					dto.setRef(rs.getInt("ref"));
					dto.setRef_step(rs.getInt("ref_step"));
					dto.setRef_level(rs.getInt("ref_level"));
					dto.setReg_date(rs.getTimestamp("reg_date"));
					
					//4. ū �ٱ���(ArrayList dtos)�� ���� �ٱ���(dto, �Խñ� 1�Ǿ�)�� ��´�.
					dtos.add(dto);
				}while(rs.next());
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
		
		//5. ū�ٱ���(ArrayLsit)�� �����Ѵ�.
		return dtos;
		
	}
	
	//��������
	@Override
	public gBoardVO getArticle(int num) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		gBoardVO dto = null;
		
		try {
			conn = datasource.getConnection();
			String sql = "SELECT * FROM bms_board WHERE num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				//1.�����ٱ��ϻ���
				dto = new gBoardVO();
				
				//2. �Խñ�1���� �о rs�� ���� �ٱ��� (dto)�� ��´�.
				dto.setNum(rs.getInt("num"));
				dto.setWriter(rs.getString("writer"));
				dto.setPwd(rs.getString("pwd"));
				dto.setSubject(rs.getString("subject"));
				dto.setContent(rs.getString("content"));
				dto.setReadCnt(rs.getInt("readCnt"));
				dto.setRef(rs.getInt("ref"));
				dto.setRef_step(rs.getInt("ref_step"));
				dto.setRef_level(rs.getInt("ref_level"));
				dto.setReg_date(rs.getTimestamp("reg_date"));
				
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
		
		return dto;
	}
	
	//��ȸ�� ����
	@Override
	public void addReadCnt(int num) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = datasource.getConnection();
			String sql = "UPDATE bms_board SET readCnt = readCnt + 1 WHERE num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			pstmt.executeUpdate();
			
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
		
	}
	
	//��й�ȣ Ȯ��(�Խñ� ����, �Խñ� ����)
	@Override
	public int pwdCheck(int num, String strPwd) {
		int selectCnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			String sql = "SELECT * FROM bms_board WHERE num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				if(strPwd.equals(rs.getString("pwd"))) {
					selectCnt = 1;
				}else {
					selectCnt = 0; 
				}
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
		
		return selectCnt;
	}
	
	//�Խñ� ����
	@Override
	public int updateBoard(gBoardVO dto) {
		int cnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = datasource.getConnection();
			String sql= "UPDATE bms_board set subject=? , content=?, pwd=? WHERE num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getSubject());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getPwd());
			pstmt.setInt(4, dto.getNum());
			
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
	
	//�Խñ� �ۼ�
	@Override
	public int insertBoard(gBoardVO dto) {
		int cnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			conn = datasource.getConnection();
			
			int num = dto.getNum();
			int ref = dto.getRef();
			int ref_step = dto.getRef_step();
			int ref_level = dto.getRef_level();
			
			if(num == 0) {
				sql = "SELECT MAX(num) FROM bms_board";  //�ֽűۺ��� �����´�.(�ֽűۺ��� �ѷ��ֹǷ� �Խñ��ۼ��� �۹�ȣ�� �ֽű۹�ȣ�̾�� �Ѵ�.)
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				
				//ù���� �ƴ� ���
				if(rs.next()) {
					ref = rs.getInt(1) + 1;
					System.out.println("//ù���� �ƴ� ���");
					
				//ù���� ���	
				}else {
					ref = 1;
					System.out.println("//ù���� ���");
				}
				
				ref_step = 0;
				ref_level = 0;
			
			//�亯���� ���	
			}else {
				//������ �ۺ��� �Ʒ��� �۵��� update
				sql = "UPDATE bms_board SET ref_step = ref_step+1 WHERE ref=? AND ref_step > ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, ref);
				pstmt.setInt(2, ref_step);
				
				pstmt.executeQuery();
				
				ref_step++;
				ref_level++;
			}
			
			sql = "INSERT INTO bms_board(num, writer, pwd, subject, content, readCnt, ref, ref_step, ref_level, reg_date) "
					+"VALUES (board_seq.NEXTVAL, ?, ?, ?, ?, 0, ?, ?, ?, ?)";
			pstmt.close();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getWriter());
			pstmt.setString(2, dto.getPwd());
			pstmt.setString(3, dto.getSubject());
			pstmt.setString(4, dto.getContent());
			
			pstmt.setInt(5, ref);
			pstmt.setInt(6, ref_step);
			pstmt.setInt(7, ref_level);
			
			pstmt.setTimestamp(8, dto.getReg_date());
			
			cnt = pstmt.executeUpdate();
			
			
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
	
	//�Խñ� ����
	@Override
	public int deleteBoard(int num) {
		int cnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			conn = datasource.getConnection();
			sql= "SELECT * FROM bms_board WHERE num=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			rs = pstmt.executeQuery();
			
			//�Ѱܹ��� num�� �ش��ϴ� Ű���� �����ϸ�
			if(rs.next()) {
				int ref = rs.getInt("ref");
				int ref_step = rs.getInt("ref_step");
				int ref_level = rs.getInt("ref_level"); 
				
				// ����� �����ϴ��� ����
				sql = "SELECT * FROM bms_board WHERE ref=? AND ref_step=?+1 AND ref_level > ?";
				pstmt.close();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, ref);
				pstmt.setInt(2, ref_step);
				pstmt.setInt(3, ref_level);
				
				rs.close();
				rs = pstmt.executeQuery();
				
				// ����� �ִ� ��� �������� �ʰڴ�.
				if(rs.next()) {
					cnt = -1;
					
				// ����� ���� ��� ����(�����, ��۾��� ���)
				} else {
					//1)�����ۺ��� �Ʒ��� �ִ� �۵��� step-1�� �ؼ� 1�پ� �ø���.
					//���� ref, ���� level�� ���� ����� ����� ���� ��� ����
					sql = "UPDATE bms_board SET ref_step=ref_step-1 WHERE ref=? AND ref_step > ?";
					pstmt.close();
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, ref);
					pstmt.setInt(2, ref_step);
					
					pstmt.executeQuery();
					
					//2)
					sql = "DELETE FROM bms_board WHERE num=?";
					pstmt.close();
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, num);
					
					cnt = pstmt.executeUpdate();
					
				}
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
	

	
	
}
