package bms.guest.vo;

import java.sql.Timestamp;

public class guestCartVO {
	private int payNum;
	
	private int cartNum;
	private int bookNum;
	private String kimg;
	private String bookName;
	private String author;
	private String publisher;
	private	String content;
	private int price;
	private	String bookforeign;
	private int bookcount;
	private int total;
	private String id;
	private int step;
	
	public int getStep() {
		return step;
	}
	public void setStep(int step) {
		this.step = step;
	}
	public int getPayNum() {
		return payNum;
	}
	public void setPayNum(int payNum) {
		this.payNum = payNum;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public int getCartNum() {
		return cartNum;
	}
	public void setCartNum(int cartNum) {
		this.cartNum = cartNum;
	}
	
	
	public int getBookNum() {
		return bookNum;
	}
	public void setBookNum(int bookNum) {
		this.bookNum = bookNum;
	}
	
	public String getKimg() {
		return kimg;
	}
	public void setKimg(String kimg) {
		this.kimg = kimg;
	}
	
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	public String getBookforeign() {
		return bookforeign;
	}
	public void setBookforeign(String bookforeign) {
		this.bookforeign = bookforeign;
	}
	
	public int getBookcount() {
		return bookcount;
	}
	public void setBookcount(int bookcount) {
		this.bookcount = bookcount;
	}
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int bookcount, int price) {
		this.total = bookcount * price;
	}
	
	
	
	
}
