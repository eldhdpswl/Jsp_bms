CREATE TABLE bms_board(
    num         NUMBER(5),              --글번호      --num과 ref는 같아야됨
    writer      VARCHAR2(20) NOT NULL,  --작성자
    pwd         VARCHAR2(10) NOT NULL,  --비밀번호
    subject     VARCHAR2(50) NOT NULL,  --글제목
    content     VARCHAR2(500),          --글내용
    readCnt     NUMBER(5)    DEFAULT 0, --조회수
    ref         NUMBER(5)    DEFAULT 0, --답변글 그룹화 아이디  --답변 글번호로서 원글번호와 일치해야 한다.
    ref_step    NUMBER(5)    DEFAULT 0, --답변글 그룹 스텝
    ref_level   NUMBER(5)    DEFAULT 0, --답변글 그룹 레벨
    reg_date    TIMESTAMP    DEFAULT sysdate,   --작성일
    
    CONSTRAINT bms_board_num_pk PRIMARY KEY(num)   
);