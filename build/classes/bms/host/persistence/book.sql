CREATE TABLE book(
   	bookNum         NUMBER(5),              --�۹�ȣ      --num�� ref�� ���ƾߵ�
    kimg            VARCHAR2(100) NOT NULL,
	bookName        VARCHAR2(20) NOT NULL,  --�ۼ���
    author          VARCHAR2(20) NOT NULL,  --��й�ȣ
    publisher       VARCHAR2(20) NOT NULL,  --������
    content         VARCHAR2(500),          --�۳���
    price           NUMBER(5)    NOT NULL, --��ȸ��
    bookforeign      VARCHAR2(20) NOT NULL,
    bookcount       NUMBER(5)    NOT NULL,
    reg_date        TIMESTAMP    DEFAULT sysdate,
    
    CONSTRAINT book_bookNum_pk PRIMARY KEY(bookNum)
);


<th style="width:5%">å��ȣ</th>
							<th style="width:10%">å�̸�</th>
							<th style="width:10%">����</th>
							<th style="width:10%">���ǻ�</th>
							<th style="width:25%">����</th>
							<th style="width:10%">����</th>
							<th style="width:10%">����/����</th>
							<th style="width:5%">�Ǽ�</th>
							<th style="width:10%">�����</th>

