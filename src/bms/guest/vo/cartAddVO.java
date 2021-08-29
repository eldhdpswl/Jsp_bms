package bms.guest.vo;

public class cartAddVO {
	/*cartNum     NUMBER(5),
    id          VARCHAR2(20),
    bookNum     NUMBER(5),
    bookcount   NUMBER(5),*/
	
	private int cartNum;
	private String id;
	private int bookNum;
	private int cartcount;
	
	public int getCartNum() {
		return cartNum;
	}
	public void setCartNum(int cartNum) {
		this.cartNum = cartNum;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public int getBookNum() {
		return bookNum;
	}
	public void setBookNum(int bookNum) {
		this.bookNum = bookNum;
	}
	
	public int getCartcount() {
		return cartcount;
	}
	public void setCartcount(int cartcount) {
		this.cartcount = cartcount;
	}
	
	
	
	
	
}
