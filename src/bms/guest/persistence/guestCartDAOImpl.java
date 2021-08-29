package bms.guest.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import bms.guest.vo.cartAddVO;
import bms.guest.vo.guestCartVO;
import bms.host.persistence.hostBookDAOImpl;
import bms.host.vo.hostBookVO;


public class guestCartDAOImpl implements guestCartDAO {
	
	//���ؼ�Ǯ ��ü ����
	DataSource datasource;
	
	//�̱��� ������� ��ü�� ����
	private static guestCartDAOImpl instance = new guestCartDAOImpl();
	
	public static guestCartDAOImpl getInstance() {
		return instance;
	}
	
	//���ؼ�Ǯ ���
	public guestCartDAOImpl() {
		try {
			Context context = new InitialContext();
			datasource = (DataSource) context.lookup("java:comp/env/jdbc/Oracle11g_bms");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//å���� ���ϱ�
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
	public ArrayList<guestCartVO> getArticleList(int start, int end) {
		
		ArrayList<guestCartVO> dtos = null;
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
				dtos = new ArrayList<guestCartVO>(end - start + 1);
				
				do {
					//2. �����ٱ��� ����
					guestCartVO dto = new guestCartVO();
					
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
	public guestCartVO getArticle(int bookNum) {
		System.out.println("getArticle" );
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		guestCartVO dto = null;
		
		try {
			conn = datasource.getConnection();
			String sql="SELECT * FROM book WHERE bookNum=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bookNum);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				//1.�����ٱ��ϻ���
				dto = new guestCartVO();
				
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
	
	//��ٱ��� cart���̺� �߰� //////////////////�����ؾߵ� �κ�
	@Override
	public int insertCart(cartAddVO cartdto) {
		int cnt = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = null;
		
		try {
			conn = datasource.getConnection();
			sql = "SELECT * FROM cart WHERE bookNum=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, cartdto.getBookNum());
			
			rs = pstmt.executeQuery();
			
			//��ٱ������̺� �����Ͱ� ������
			if(rs.next()) {
				int cartcount = rs.getInt("cartcount");
				pstmt.close();
				sql= "UPDATE cart SET cartcount = ? "
						+"WHERE bookNum=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, cartdto.getCartcount()+cartcount);
				pstmt.setInt(2, cartdto.getBookNum());
				
				pstmt.executeUpdate();
				
				cnt = pstmt.executeUpdate();
			
			//��ٱ������̺� �����Ͱ� ������
			}else {
				pstmt.close();
				sql = "INSERT INTO cart(cartNum, id, bookNum, cartcount) "
						+"VALUES ( cart_seq.NEXTVAL, ?, ?, ?)";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, cartdto.getId());
				pstmt.setInt(2, cartdto.getBookNum());
				pstmt.setInt(3, cartdto.getCartcount());
				
				cnt = pstmt.executeUpdate();
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
	
	//��ٱ��� å ���� ���ϱ�
	@Override
	public int getCartArticleCnt() {
		
		int cnt = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			String sql = "SELECT COUNT(*) FROM cart";
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
	
	//��ٱ��� ��� ��ȸ
	@Override
	public ArrayList<guestCartVO> getCartArticleList(int cnt, String memId) {
		ArrayList<guestCartVO> dtos = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			String sql = "SELECT " + 
						 "c.cartNum AS cartNum, " +
						 "c.bookNum AS bookNum, " + 
						 "b.kimg AS kimg, " + 
						 "b.bookname AS bookname, " + 
						 "b.price AS price, " + 
						 "c.cartcount AS cartcount " + 
						 "FROM cart c JOIN book b " + 
						 "ON c.bookNum=b.bookNum "+
						 "WHERE id=?";
						 //3. �Ѱܹ��� start���� end������ rowNum�� ��ȸ�Ѵ�. 
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memId);			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dtos = new ArrayList<guestCartVO>(cnt);
				
				do {
					//2. �����ٱ��� ����
					guestCartVO dto = new guestCartVO();
					
					//3. �Խñ� 1���� �о rs�� �����ٱ���(dto)�� ��ƶ�
					dto.setCartNum(rs.getInt("cartNum"));
					dto.setBookNum(rs.getInt("bookNum"));
					dto.setKimg(rs.getString("kimg"));
					dto.setBookName(rs.getString("bookname"));
					dto.setPrice(rs.getInt("price"));
					dto.setBookcount(rs.getInt("cartcount"));
					
					dto.setTotal(rs.getInt("cartcount"), rs.getInt("price"));
					
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
	
	//���Ŵ����� �ѱ涧 ��ٱ��� ��� ��ȸ�ϱ�
	@Override
	public ArrayList<guestCartVO> getCartInfo(String[] cartNum) {
		
		ArrayList<guestCartVO> dtos = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		guestCartVO dto = null;
		
		try {
			conn = datasource.getConnection();
			String sql="SELECT * FROM cart WHERE cartNum=?";
			pstmt = conn.prepareStatement(sql);
			dtos= new ArrayList<guestCartVO>(cartNum.length);
			
			for(int i=0; i<cartNum.length; i++) { //for�� ��������� �迭�� Ǯ�����ؼ� ���
				int cart1 = Integer.parseInt(cartNum[i]);
				pstmt.setInt(1, cart1);	
				System.out.println("cart1" + cart1);
				rs = pstmt.executeQuery();
			
				if(rs.next()) {
					dto = new guestCartVO();
					
					dto.setBookcount(rs.getInt("cartcount"));
					System.out.println(rs.getInt("cartcount"));
					
					dto.setCartNum(rs.getInt("cartNum"));
					System.out.println(rs.getInt("cartNum"));
					
					dto.setId(rs.getString("id"));
					dto.setBookNum(rs.getInt("bookNum"));
					System.out.println(rs.getInt("bookNum"));
					
					dtos.add(dto);
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
		
		return dtos;
	}

/*	//pay_seq.NEXTVAL ���ڰ�������
	@Override
	public int pay_seq() {
		int pay_seq=0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			String sql="SELECT pay_seq.NEXTVAL as pay_num FROM dual";
			pstmt = conn.prepareStatement(sql);	
				rs = pstmt.executeQuery();
				if(rs.next()) {
					rs.getInt("pay_num");
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
		
		return pay_seq;
	}*/
	
	

	@Override
	public String insertpay(ArrayList<guestCartVO> list) { //serviceImpl���� insertpay(dtos)���� dtos��
		Connection conn = null;							// ���⼭ insertpay(ArrayList<guestCartVO> list)���� list�� ����(������ �����̱⶧���� �̸��� �ٸ��� �����Ҽ� �ִ�.)
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String id="";
		
		try {
			conn = datasource.getConnection();
			String sql="INSERT INTO pay(payNum, bookNum, bookcount, id, step) VALUES(pay_seq.nextval,?,?,?,1)";
			pstmt = conn.prepareStatement(sql);
			
			
			for(int i=0; i<list.size(); i++) { //for�� ��������� �迭�� Ǯ�����ؼ� ���
				
				id = list.get(i).getId();
				int bookcount = list.get(i).getBookcount();
				int bookNum = list.get(i).getBookNum();
				
				System.out.println("id" + id + "bookcount" + bookcount + "bookNum" + bookNum);
				
				pstmt.setInt(1, bookNum);
				pstmt.setInt(2, bookcount);
				pstmt.setString(3, id);
				
				pstmt.executeUpdate();
				
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
		
		return id;
	}
	

	//cart���̺� �ִ� �������� delete
	@Override
	public int deletecart(ArrayList<guestCartVO> delist) {
		
		System.out.println("deletecart");
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		int deleteCnt=0;
		
		try {
			conn = datasource.getConnection();
			String sql="DELETE FROM cart WHERE cartNum=?";
			pstmt = conn.prepareStatement(sql);
			
			for(int i=0; i<delist.size(); i++) { //for�� ��������� �迭�� Ǯ�����ؼ� ���
				
				int cartNum = delist.get(i).getCartNum();
				
				pstmt.setInt(1, cartNum);
				
				deleteCnt = pstmt.executeUpdate();
			
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
		
		return deleteCnt;
	}
	
	//pay�����ҷ�����
	@Override
	public ArrayList<guestCartVO> getPayList(String memId,int cnt) {
		
		ArrayList<guestCartVO> dtos = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		guestCartVO dto = null;
		
		try {
			conn = datasource.getConnection();
			
			String sql = "SELECT " + 
						 "p.payNum AS payNum, "
						 +"p.bookNum AS bookNum,p.step as step, "
						 + "p.id as id, " + 
						 "b.kimg AS img, " + 
						 "b.bookName AS bookName, " + 
						 "b.price AS price, " + 
						 "p.bookcount AS bookcount " + 
						 "FROM book b JOIN pay p " + 
						 "ON b.bookNum = p.bookNum"
						 + " WHERE p.id=?";
			pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, memId);
				
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
					dto.setStep(rs.getInt("step"));
					dto.setBookcount(rs.getInt("bookcount"));
					dto.setTotal(rs.getInt("price"), rs.getInt("bookcount"));
					dto.setPayNum(rs.getInt("payNum"));
					
					dtos.add(dto);
					}while(rs.next());
				}
				
			System.out.println("---------------======");
			
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
	
	//�����ͼ� ��������
	@Override
	public int payNum(String memid) {

		int cnt = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			String sql = "SELECT COUNT(paynum) as count FROM pay WHERE id=? and step!=4";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memid);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				cnt = rs.getInt("count");
				System.out.println("================="+cnt);
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
	
	//���Ÿ�� å ���� ���ϱ�
	@Override
	public int getPayArticleCnt() {
		int cnt = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			String sql = "SELECT COUNT(*) FROM pay";
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
	
	//��ٱ��� ��� ��ȸ
	@Override
	public ArrayList<guestCartVO> getPayArticleList(int cnt, String memId) {
		
		ArrayList<guestCartVO> dtos = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			String sql = "SELECT " + 
						 "c.cartNum AS cartNum, " +
						 "c.bookNum AS bookNum, " + 
						 "b.kimg AS kimg, " + 
						 "b.bookname AS bookname, " + 
						 "b.price AS price, " + 
						 "c.cartcount AS cartcount " + 
						 "FROM cart c JOIN book b " + 
						 "ON c.bookNum=b.bookNum "+
						 "WHERE id=?";
						 //3. �Ѱܹ��� start���� end������ rowNum�� ��ȸ�Ѵ�. 
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memId);			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dtos = new ArrayList<guestCartVO>(cnt);
				
				do {
					//2. �����ٱ��� ����
					guestCartVO dto = new guestCartVO();
					
					//3. �Խñ� 1���� �о rs�� �����ٱ���(dto)�� ��ƶ�
					dto.setCartNum(rs.getInt("cartNum"));
					dto.setBookNum(rs.getInt("bookNum"));
					dto.setKimg(rs.getString("kimg"));
					dto.setBookName(rs.getString("bookname"));
					dto.setPrice(rs.getInt("price"));
					dto.setBookcount(rs.getInt("cartcount"));
					
					dto.setTotal(rs.getInt("cartcount"), rs.getInt("price"));
					
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
	
	//���Ÿ�Ͽ��� �����ϱ��Ҷ� step update�� �κ�
	@Override
	public int updatepay(guestCartVO paydto) {
		int cnt = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = null;
		
		try {
			conn = datasource.getConnection();
			sql = "SELECT * FROM pay WHERE payNum=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, paydto.getPayNum());
			System.out.println("PayNum:"+paydto.getPayNum());
			rs = pstmt.executeQuery();
			
			//��ٱ������̺� �����Ͱ� ������
			if(rs.next()) {
				pstmt.close();
				sql= "UPDATE pay SET step = 2 WHERE payNum=?";
						
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, paydto.getPayNum());
				
				pstmt.executeUpdate();
				
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
	public void bookCountUpdate(ArrayList<guestCartVO> dtos) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			for(int i=0;i<dtos.size();i++) {
				int num=dtos.get(i).getBookNum();
				
				int count=dtos.get(i).getBookcount();
				
				conn = datasource.getConnection();
				String sql = "UPDATE book SET bookcount = bookcount-? WHERE booknum=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, count);
				pstmt.setInt(2, num);
				pstmt.executeUpdate();
				pstmt.close();
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
		
		
		
	}

	@Override
	public int refund(int payNum) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int cnt=0;
		try {
			conn = datasource.getConnection();
			String sql = "UPDATE pay SET step=3 WHERE payNum=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, payNum);
			cnt=pstmt.executeUpdate();
			
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

	
	@Override
	public int insertdirectpay(int bookNum, int bookcount, String id) {
		Connection conn = null;							// ���⼭ insertpay(ArrayList<guestCartVO> list)���� list�� ����(������ �����̱⶧���� �̸��� �ٸ��� �����Ҽ� �ִ�.)
		PreparedStatement pstmt = null;
		int cnt=0;
		
		
		try {
			conn = datasource.getConnection();
			String sql="INSERT INTO pay(payNum, bookNum, bookcount, id, step) VALUES(pay_seq.nextval,?,?,?,1)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, bookNum);
			pstmt.setInt(2, bookcount);
			pstmt.setString(3, id);
			
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

	@Override
	public int directpayupdate(int bookNum, int bookcount) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int cnt=0;
		try {
			conn = datasource.getConnection();
			String sql = "UPDATE book SET bookcount=bookcount-? WHERE bookNum=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, bookcount);
			pstmt.setInt(2, bookNum);
			cnt=pstmt.executeUpdate();
			
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

	@Override
	public int selectBCount(int bookNum, int bookcount) {
		int cnt = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = datasource.getConnection();
			String sql = "SELECT * FROM book where bookNum=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bookNum);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				cnt = rs.getInt("bookcount");
				System.out.println("---------------"+bookcount);
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
	
	//�������
	@Override
	public int paycancel(int bookNum, int bookcount) {
		System.out.println("asdasdas");
		Connection conn = null;
		PreparedStatement pstmt = null;
		int cnt=0;
		try {
			conn = datasource.getConnection();
			String sql = "UPDATE book SET bookcount=bookcount+? WHERE bookNum=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, bookcount);
			pstmt.setInt(2, bookNum);
			cnt=pstmt.executeUpdate();
			
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

	@Override
	public int paylistdelete(int payNum) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		int deleteCnt=0;
		
		try {
			conn = datasource.getConnection();
			String sql="DELETE FROM pay WHERE payNum=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, payNum);
			deleteCnt = pstmt.executeUpdate();
			
			
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
		
		return deleteCnt;
	}
	
	
	
	
	
	//���Ÿ�Ͽ��� �����ϱ��Ҷ� pay���ִ� ��Ϻҷ�����
	/*@Override
	public ArrayList<guestCartVO> getPaymentList() {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = null;
		
		try {
			conn = datasource.getConnection();
			sql = "SELECT * FROM pay";
			pstmt = conn.prepareStatement(sql);
			
			
			
			rs = pstmt.executeQuery();
			
			//��ٱ������̺� �����Ͱ� ������
			if(rs.next()) {
				
				
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
		
		
		return ;
	}*/
	
	
	
	
	
	
	
	
	
	
	
	
	
}
