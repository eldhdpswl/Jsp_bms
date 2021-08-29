CREATE TABLE book(
   	bookNum         NUMBER(5),              --글번호      --num과 ref는 같아야됨
    kimg            VARCHAR2(100) NOT NULL,
	bookName        VARCHAR2(20) NOT NULL,  --작성자
    author          VARCHAR2(20) NOT NULL,  --비밀번호
    publisher       VARCHAR2(20) NOT NULL,  --글제목
    content         VARCHAR2(500),          --글내용
    price           NUMBER(5)    NOT NULL, --조회수
    bookforeign      VARCHAR2(20) NOT NULL,
    bookcount       NUMBER(5)    NOT NULL,
    reg_date        TIMESTAMP    DEFAULT sysdate,
    
    CONSTRAINT book_bookNum_pk PRIMARY KEY(bookNum)
);


<th style="width:5%">책번호</th>
							<th style="width:10%">책이름</th>
							<th style="width:10%">저자</th>
							<th style="width:10%">출판사</th>
							<th style="width:25%">내용</th>
							<th style="width:10%">가격</th>
							<th style="width:10%">국내/국외</th>
							<th style="width:5%">권수</th>
							<th style="width:10%">등록일</th>

