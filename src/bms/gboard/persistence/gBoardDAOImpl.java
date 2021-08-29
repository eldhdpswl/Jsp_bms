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
	
	//컨넥션풀 객체 보관
	DataSource datasource;
	
	//싱글톤 방식으로 객체를 생성
	private static gBoardDAOImpl instance = new gBoardDAOImpl();
	
	public static gBoardDAOImpl getInstance() {
		return instance;
	}
	
	//생성자
	public gBoardDAOImpl() {
		try {
			//컨넥션을(DBCP) : Servers > context.xml
			Context context = new InitialContext();
			datasource = (DataSource)context.lookup("java:comp/env/jdbc/Oracle11g_bms");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//글갯수 구하기
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
	
	//게시글 목록 조회
	@Override
	public ArrayList<gBoardVO> getArticleList(int start, int end) {
		ArrayList<gBoardVO> dtos = null;  //큰바구니
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
					     				"ORDER BY ref DESC, ref_step ASC" +  //1. 최신글부턴 SELECT한다.
					     			 ")" + //2. 최신글부터 SELECT한 레코드에 rowNum을 추가한다.(삭제데이터 제외한 실제데이터를 최신글부터 넘버링한다.)
					     	 ") " + 
					     "WHERE rNUM >= ? AND rNUM <= ?"; //3. 넘겨받은 start값과 end값으로 rowNum을 조회한다. 
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				//1. 큰바구니 생성(dtos)
				dtos = new ArrayList<gBoardVO>(end - start + 1);
				
				do {
					//2. 작은바구니 생성
					gBoardVO dto = new gBoardVO();
					
					//3. 게시글 1건을 읽어서 rs를 작은바구니(dto)에 담아라
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
					
					//4. 큰 바구니(ArrayList dtos)에 작은 바구니(dto, 게시글 1건씩)를 담는다.
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
		
		//5. 큰바구니(ArrayLsit)를 리턴한다.
		return dtos;
		
	}
	
	//상세페이지
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
				
				//1.작은바구니생성
				dto = new gBoardVO();
				
				//2. 게시글1건을 읽어서 rs를 작은 바구니 (dto)에 담는다.
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
	
	//조회수 증가
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
	
	//비밀번호 확인(게시글 수정, 게시글 삭제)
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
	
	//게시글 수정
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
	
	//게시글 작성
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
				sql = "SELECT MAX(num) FROM bms_board";  //최신글부터 가져온다.(최신글부터 뿌려주므로 게시글작성시 글번호는 최신글번호이어야 한다.)
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				
				//첫글이 아닌 경우
				if(rs.next()) {
					ref = rs.getInt(1) + 1;
					System.out.println("//첫글이 아닌 경우");
					
				//첫글인 경우	
				}else {
					ref = 1;
					System.out.println("//첫글인 경우");
				}
				
				ref_step = 0;
				ref_level = 0;
			
			//답변글인 경우	
			}else {
				//삽입할 글보다 아래쪽 글들을 update
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
	
	//게시글 삭제
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
			
			//넘겨받은 num에 해당하는 키값이 존재하면
			if(rs.next()) {
				int ref = rs.getInt("ref");
				int ref_step = rs.getInt("ref_step");
				int ref_level = rs.getInt("ref_level"); 
				
				// 답글이 존재하는지 여부
				sql = "SELECT * FROM bms_board WHERE ref=? AND ref_step=?+1 AND ref_level > ?";
				pstmt.close();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, ref);
				pstmt.setInt(2, ref_step);
				pstmt.setInt(3, ref_level);
				
				rs.close();
				rs = pstmt.executeQuery();
				
				// 답글이 있는 경우 삭제하지 않겠다.
				if(rs.next()) {
					cnt = -1;
					
				// 답글이 없는 경우 삭제(제목글, 답글없는 답글)
				} else {
					//1)삭제글보다 아래에 있는 글들을 step-1을 해서 1줄씩 올린다.
					//같은 ref, 같은 level을 가진 답글중 답글이 없는 경우 삭제
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
