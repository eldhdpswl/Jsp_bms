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
	
	//컨넥션풀 객체 보관
	DataSource datasource;
	
	//싱글톤 방식으로 객체를 생성
	private static guestCartDAOImpl instance = new guestCartDAOImpl();
	
	public static guestCartDAOImpl getInstance() {
		return instance;
	}
	
	//컨넥션풀 사용
	public guestCartDAOImpl() {
		try {
			Context context = new InitialContext();
			datasource = (DataSource) context.lookup("java:comp/env/jdbc/Oracle11g_bms");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//책갯수 구하기
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
	
	//책목록 목록 조회
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
				     			 ")" + //2. 최신글부터 SELECT한 레코드에 rowNum을 추가한다.(삭제데이터 제외한 실제데이터를 최신글부터 넘버링한다.)
				     	 ") " + 
				     "WHERE rNUM >= ? AND rNUM <= ?"; //3. 넘겨받은 start값과 end값으로 rowNum을 조회한다. 
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
		
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dtos = new ArrayList<guestCartVO>(end - start + 1);
				
				do {
					//2. 작은바구니 생성
					guestCartVO dto = new guestCartVO();
					
					//3. 게시글 1건을 읽어서 rs를 작은바구니(dto)에 담아라
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
	
	//상세페이지
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
				//1.작은바구니생성
				dto = new guestCartVO();
				
				//2. 게시글1건을 읽어서 rs를 작은 바구니 (dto)에 담는다.
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
	
	//장바구니 cart테이블에 추가 //////////////////수정해야될 부분
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
			
			//장바구니테이블에 데이터가 있을때
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
			
			//장바구니테이블에 데이터가 없을때
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
	
	//장바구니 책 갯수 구하기
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
	
	//장바구니 목록 조회
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
						 //3. 넘겨받은 start값과 end값으로 rowNum을 조회한다. 
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memId);			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dtos = new ArrayList<guestCartVO>(cnt);
				
				do {
					//2. 작은바구니 생성
					guestCartVO dto = new guestCartVO();
					
					//3. 게시글 1건을 읽어서 rs를 작은바구니(dto)에 담아라
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
	
	//구매단으로 넘길때 장바구니 목록 조회하기
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
			
			for(int i=0; i<cartNum.length; i++) { //for문 사용이유는 배열을 풀기위해서 사용
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

/*	//pay_seq.NEXTVAL 숫자가져오기
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
	public String insertpay(ArrayList<guestCartVO> list) { //serviceImpl에서 insertpay(dtos)에서 dtos는
		Connection conn = null;							// 여기서 insertpay(ArrayList<guestCartVO> list)에서 list와 같다(어차피 변수이기때문에 이름만 다를뿐 같게할수 있다.)
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String id="";
		
		try {
			conn = datasource.getConnection();
			String sql="INSERT INTO pay(payNum, bookNum, bookcount, id, step) VALUES(pay_seq.nextval,?,?,?,1)";
			pstmt = conn.prepareStatement(sql);
			
			
			for(int i=0; i<list.size(); i++) { //for문 사용이유는 배열을 풀기위해서 사용
				
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
	

	//cart테이블에 있는 정보들을 delete
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
			
			for(int i=0; i<delist.size(); i++) { //for문 사용이유는 배열을 풀기위해서 사용
				
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
	
	//pay정보불러오기
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
	
	//데이터수 가져오기
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
	
	//구매목록 책 갯수 구하기
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
	
	//장바구니 목록 조회
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
						 //3. 넘겨받은 start값과 end값으로 rowNum을 조회한다. 
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memId);			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dtos = new ArrayList<guestCartVO>(cnt);
				
				do {
					//2. 작은바구니 생성
					guestCartVO dto = new guestCartVO();
					
					//3. 게시글 1건을 읽어서 rs를 작은바구니(dto)에 담아라
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
	
	//구매목록에서 결재하기할때 step update할 부분
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
			
			//장바구니테이블에 데이터가 있을때
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
		Connection conn = null;							// 여기서 insertpay(ArrayList<guestCartVO> list)에서 list와 같다(어차피 변수이기때문에 이름만 다를뿐 같게할수 있다.)
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
	
	//구매취소
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
	
	
	
	
	
	//구매목록에서 결재하기할때 pay에있는 목록불러오기
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
			
			//장바구니테이블에 데이터가 있을때
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
