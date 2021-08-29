CREATE TABLE bms_board(
    num         NUMBER(5),              --�۹�ȣ      --num�� ref�� ���ƾߵ�
    writer      VARCHAR2(20) NOT NULL,  --�ۼ���
    pwd         VARCHAR2(10) NOT NULL,  --��й�ȣ
    subject     VARCHAR2(50) NOT NULL,  --������
    content     VARCHAR2(500),          --�۳���
    readCnt     NUMBER(5)    DEFAULT 0, --��ȸ��
    ref         NUMBER(5)    DEFAULT 0, --�亯�� �׷�ȭ ���̵�  --�亯 �۹�ȣ�μ� ���۹�ȣ�� ��ġ�ؾ� �Ѵ�.
    ref_step    NUMBER(5)    DEFAULT 0, --�亯�� �׷� ����
    ref_level   NUMBER(5)    DEFAULT 0, --�亯�� �׷� ����
    reg_date    TIMESTAMP    DEFAULT sysdate,   --�ۼ���
    
    CONSTRAINT bms_board_num_pk PRIMARY KEY(num)   
);