package bms.host.vo;

import java.sql.Timestamp;

public class hostBookVO {
	
	private int bookNum;
	private String kimg;
	private String bookName;
	private String author;
	private String publisher;
	private	String content;
	private int price;
	private	String bookforeign;
	private int bookcount;
	private Timestamp reg_date;
	
	public String getKimg() {
		return kimg;
	}
	public void setKimg(String kimg) {
		this.kimg = kimg;
	}
	
	public int getBookNum() {
		return bookNum;
	}
	public void setBookNum(int bookNum) {
		this.bookNum = bookNum;
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
	
	
	public Timestamp getReg_date() {
		return reg_date;
	}
	public void setReg_date(Timestamp reg_date) {
		this.reg_date = reg_date;
	}
	
	
}
