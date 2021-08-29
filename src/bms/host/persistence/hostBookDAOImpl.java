package bms.host.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import bms.guest.vo.guestCartVO;
import bms.host.vo.hostBookVO;
import bms.host.vo.totalVO;


public class hostBookDAOImpl implements hostBookDAO{
	
	//���ؼ�Ǯ ��ü ����
	DataSource datasource;
	
	//�̱��� ������� ��ü�� ����
	private static hostBookDAOImpl instance = new hostBookDAOImpl();
	
	public static hostBookDAOImpl getInstance() {
		return instance;
	}
	
	//���ؼ�Ǯ ���
	public hostBookDAOImpl() {
		try {
			Context context = new InitialContext();
			datasource = (DataSource) context.lookup("java:comp/env/jdbc/Oracle11g_bms");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//�Խñ� �ۼ�
	@Override
	public int insertBook(hostBookVO dto) {
		int cnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = null;
		
		try {
			conn = datasource.getConnection();
			sql = "INSERT INTO book(bookNum, kimg, bookName, author, publisher, content, price, bookforeign, bookcount, reg_date) "
					+"VALUES ( book_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getKimg());
			pstmt.setString(2, dto.getBookName());
			pstmt.setString(3, dto.getAuthor());
			pstmt.setString(4, dto.getPublisher());
			pstmt.setString(5, dto.getContent());
			pstmt.setInt(6, dto.getPrice());
			pstmt.setString(7, dto.getBookforeign());
			pstmt.setInt(8, dto.getBookcount());
			pstmt.setTimestamp(9, dto.getReg_date());
			
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
	
	//�۰��� ���ϱ�
	@Override
	public int getArticleCnt() {
		int cnt = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			String sql = "SELECT COUNT(*) FROM book";
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
	
	//å��� ��� ��ȸ
	@Override
	public ArrayList<hostBookVO> getArticleList(int start, int end) {
		System.out.println(" ���������� å��� getArticleList--> ");
		ArrayList<hostBookVO> dtos = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			String sql = "SELECT * " + 
					     "FROM(SELECT bookNum, kimg, bookName, author, publisher, content, price, \r\n" + 
					    			 "bookforeign, bookcount, reg_date, rownum rNum\r\n" + 
					     		"FROM (" + 
					     				"SELECT * FROM book " + 
					     			 ")" + //2. �ֽűۺ��� SELECT�� ���ڵ忡 rowNum�� �߰��Ѵ�.(���������� ������ ���������͸� �ֽűۺ��� �ѹ����Ѵ�.)
					     	 ") " + 
					     "WHERE rNUM >= ? AND rNUM <= ?"; //3. �Ѱܹ��� start���� end������ rowNum�� ��ȸ�Ѵ�. 
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dtos = new ArrayList<hostBookVO>(end - start + 1);
				
				do {
					//2. �����ٱ��� ����
					hostBookVO dto = new hostBookVO();
					
					//3. �Խñ� 1���� �о rs�� �����ٱ���(dto)�� ��ƶ�
					dto.setBookNum(rs.getInt("bookNum"));
					dto.setKimg(rs.getString("kimg"));
					dto.setBookName(rs.getString("bookName"));
					dto.setAuthor(rs.getString("author"));
					dto.setPublisher(rs.getString("publisher"));
					dto.setContent(rs.getString("content"));
					dto.setPrice(rs.getInt("price"));
					dto.setBookcount(rs.getInt("bookcount"));
					dto.setBookforeign(rs.getString("bookforeign"));
					dto.setReg_date(rs.getTimestamp("reg_date"));
					
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
		
		return dtos;
	}

	//��������	
	@Override
	public hostBookVO getArticle(int bookNum) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		hostBookVO dto = null;
		
		try {
			conn = datasource.getConnection();
			String sql="SELECT * FROM book WHERE bookNum=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bookNum);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				//1.�����ٱ��ϻ���
				dto = new hostBookVO();
				
				//2. �Խñ�1���� �о rs�� ���� �ٱ��� (dto)�� ��´�.
				dto.setBookNum(rs.getInt("bookNum"));
				dto.setKimg(rs.getString("kimg"));
				dto.setBookName(rs.getString("bookName"));
				dto.setAuthor(rs.getString("author"));
				dto.setPublisher(rs.getString("publisher"));
				dto.setContent(rs.getString("content"));
				dto.setPrice(rs.getInt("price"));
				dto.setBookforeign(rs.getString("bookforeign"));
				dto.setBookcount(rs.getInt("bookcount"));
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
	
	//��й�ȣ Ȯ��(�Խñ� ����, �Խñ� ����)
	@Override
	public int pwdCheck(int bookNum, String strPwd) {
		
		return 0;
	}
	
	//å���� ����(�ؾߵɺκ�)
	@Override
	public int updateBook(hostBookVO dto) {
		int cnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = datasource.getConnection();
			String sql= "UPDATE book set book=? , content=?, pwd=? WHERE num=?";
			/*pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getSubject());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getPwd());
			pstmt.setInt(4, dto.getNum());*/
			
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
	
	//å ����ó��
	@Override
	public int deleteBook(int bookNum) {
		int cnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			conn = datasource.getConnection();
			sql= "SELECT * FROM book WHERE bookNum=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bookNum);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				pstmt.close();
				sql="DELETE FROM book WHERE bookNum=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, bookNum);
				
				cnt = pstmt.executeUpdate();
				
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

	@Override
	public ArrayList<guestCartVO> getorderlist(int cnt) {
		
		ArrayList<guestCartVO> dtos = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		guestCartVO dto = null;
		
		try {
			conn = datasource.getConnection();
			
			String sql = "SELECT  " + 
					"    p.paynum as paynum, " + 
					"    p.id as memid, " + 
					"    p.bookNum AS bookNum,p.step as step, " + 
					"    b.kimg AS img, " + 
					"     b.bookName AS bookName, " + 
					"     b.price AS price, " + 
					"     p.bookcount AS bookcount  " + 
					"     FROM book b JOIN pay p  " + 
					"     ON b.bookNum = p.bookNum";
			
			pstmt = conn.prepareStatement(sql);

				rs = pstmt.executeQuery();
				
				
				if(rs.next()) {
					dtos=new ArrayList<guestCartVO>(cnt);
					
					do {
					dto=new guestCartVO();
					dto.setBookNum(rs.getInt("bookNum"));
					System.out.println(rs.getInt("bookNum"));
					dto.setKimg(rs.getString("img"));
					dto.setBookName(rs.getString("bookName"));
					System.out.println(rs.getString("bookName"));
					dto.setPrice(rs.getInt("price"));
					dto.setBookcount(rs.getInt("bookcount"));
					dto.setStep(rs.getInt("step"));
					dto.setTotal(rs.getInt("price"), rs.getInt("bookcount"));
					dto.setPayNum(rs.getInt("payNum"));
					
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
		
		return dtos;
	}

	@Override
	public int payNum() {
		int cnt = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			String sql = "SELECT COUNT(paynum) as count FROM pay";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				cnt = rs.getInt("count");
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
	
	
	@Override
	public String selectforeign(String name) {
		String foreign = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		System.out.println("======="+name);
		try {
			conn = datasource.getConnection();
			sql= "SELECT * FROM book WHERE bookname=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				foreign = rs.getString("bookforeign");
				
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
		
		return foreign;
	}

	@Override
	public void stepUpdate(int payNum) {
		System.out.println("payNum==========="+payNum);
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = datasource.getConnection();
			String sql= "UPDATE pay set step=2 WHERE payNum=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, payNum);
			
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

	@Override
	public void total(String bookforeign, int price, int count) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			if(bookforeign.equals("����")) {
				conn = datasource.getConnection();
				String sql= "UPDATE total set bookforeignIn=bookforeignIn+? ,total=total+? WHERE num=1";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, count);
				pstmt.setInt(2, price);
			}else {
				conn = datasource.getConnection();
				String sql= "UPDATE total set bookforeignOut=bookforeignOut+? ,total=total+? WHERE num=1";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, count);
				pstmt.setInt(2, price);
			}
			
			
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

	@Override
	public void hostrefundbook(int bookNum, int bookcount) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = datasource.getConnection();
			String sql= "UPDATE book set bookcount = bookcount + ? WHERE bookNum=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bookcount);
			pstmt.setInt(2, bookNum);
			
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

	@Override
	public void totalupdate(int price) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = datasource.getConnection();
			String sql= "UPDATE total set total = total - ? WHERE num=1";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, price);
			
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

	@Override
	public void updatestep(int payNum) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = datasource.getConnection();
			String sql= "UPDATE pay set step = 4 WHERE payNum=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, payNum);
			
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

	@Override
	public String foreigncheck(int bookNum) {
		
		String foreign = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			conn = datasource.getConnection();
			sql= "SELECT * FROM book WHERE bookNum=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bookNum);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				foreign = rs.getString("bookforeign");
				
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
		
		return foreign;
		
	}

	@Override
	public void totalforeign(String foreign, int bookcount) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			if(foreign.equals("����")) {
				conn = datasource.getConnection();
				String sql= "UPDATE total set bookforeignIn = bookforeignIn - ? WHERE num=1";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, bookcount);
				
			}else {
				conn = datasource.getConnection();
				String sql= "UPDATE total set bookforeignOut = bookforeignOut - ? WHERE num=1";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, bookcount);
			}
			
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

	@Override
	public totalVO selecttotal() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		totalVO dto= new totalVO();
		
		try {
			conn = datasource.getConnection();
			sql= "SELECT * FROM total WHERE num=1";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto.setNum(rs.getInt("num"));
				dto.setBookforeignIn(rs.getInt("bookforeignIn"));
				dto.setBookforeignOut(rs.getInt("bookforeignOut"));
				dto.setTotal(rs.getInt("total"));
				
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
	
	

}
