package bms.gboard.persistence;

import java.util.ArrayList;

import bms.gboard.vo.gBoardVO;


public interface gBoardDAO {
	
	//글갯수 구하기
	public int getArticleCnt();
	
	//게시글 목록 조회
	public ArrayList<gBoardVO> getArticleList(int start, int end);
	
	//상세페이지
	public gBoardVO getArticle(int num);
	
	//조회수 증가
	public void addReadCnt(int num);
	
	//비밀번호 확인(게시글 수정, 게시글 삭제)
	public int pwdCheck(int num, String strPwd);
	
	//게시글 수정
	public int updateBoard(gBoardVO dto);
	
	//게시글 작성
	public int insertBoard(gBoardVO dto);
	
	//게시글 삭제
	public int deleteBoard(int num);
	
	
	
	
}
