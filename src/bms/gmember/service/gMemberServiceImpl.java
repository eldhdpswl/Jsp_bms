package bms.gmember.service;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bms.gmember.persistence.gMemberDAO;
import bms.gmember.persistence.gMemberDAOImpl;
import bms.gmember.vo.gMemberVO;



public class gMemberServiceImpl implements gMemberService{
	//중복확인처리
	@Override //(1단계, 2단계는 MFrontController)
	public void confirmId(HttpServletRequest req, HttpServletResponse res) {
		// 3단계. 화면으로부터 입력받은 값을 받아온다.
		String strId = req.getParameter("id");
		//4단계. 다형성 적용, 싱글톤 방식으로 객체 생성 
		gMemberDAO dao = gMemberDAOImpl.getInstance();
		
		//5단계. 중복된 id가 있는지 확인
		int cnt = dao.idCheck(strId);
		
		//6단계. request나 session에 처리 결과를 저장
		req.setAttribute("cnt", cnt);
		req.setAttribute("id", strId);
		
	}
	
	//회원가입처리
	@Override
	public void inputPro(HttpServletRequest req, HttpServletResponse res) {
		//vo 바구니 생성
		gMemberVO vo = new gMemberVO();
		
		//2단계. dto 바구니에 담는다(화면에서 넘어온 값을).
		vo.setId(req.getParameter("id"));
		vo.setPwd(req.getParameter("pwd"));
		vo.setName(req.getParameter("name"));
		vo.setJumin1(req.getParameter("jumin1"));
		vo.setJumin2(req.getParameter("jumin2"));
		
		//hp
		String hp="";
		String hp1 = req.getParameter("hp1");
		String hp2 = req.getParameter("hp2"); 
		String hp3 = req.getParameter("hp3");
		
		//필수입력항목이 아니므로 null 체크없이 무조건 insert하면 nullpointer Exception발생
		if(!hp1.equals("") && !hp2.equals("") && !hp3.equals("")) {
			hp = hp1 + "-" + hp2 + "-" + hp3; 
			
		}
		vo.setHp(hp);  //필수입력 항목이 하나므로 null이면 공백 set, 아니면 위값 설정
		
		//email
		String email1 = req.getParameter("email1");
		String email2 = req.getParameter("email2");
		String email3 = req.getParameter("email3");
		
		String email = "";
		
		if(email3.equals("0")) {
			//직접입력일때
			email = email1 + "@" + email2; 
			
		}else {
			//선택입력일때
			email = email1 + "@" + email3;
		}
		
		vo.setEmail(email);
		
		//req_date
		vo.setReg_date(new Timestamp(System.currentTimeMillis()));
		
		//3단계. 다형성적용, 싱글톤 방식으로 객체생성
		gMemberDAO dao = gMemberDAOImpl.getInstance();
		
		//4단계. 모델을 사용하여 요청한 기능을 수행한다.
		//4단계. DAO에서 vo 바구니를 dao 파라미터에 넘겨서 해당SQL문 호출
		int cnt = dao.insertMember(vo);
		
		//5단계. request나 session에 처리결과를 저장하면 jsp에서 받는다.
		req.setAttribute("cnt", cnt);
	}
	
	//로그인 처리
	@Override
	public void loginPro(HttpServletRequest req, HttpServletResponse res) {
		// 1단계. 화면으로부터 id, pwd 받아온다.
		String strId = req.getParameter("id");
		String strPwd = req.getParameter("pwd");
		
		// 2-1단계. dao 객체생성(싱글톤, 다형성 적용)
		gMemberDAO dao = gMemberDAOImpl.getInstance();
		
		// 2-2단계. 모델을 사용하여 요청한 기능을 수행한다. 
		int cnt = dao.check(strId, strPwd); //두개의 값을 넘겨서 select문
		
		if(cnt==1) {
			//memId대소문자 구분
			req.getSession().setAttribute("memId", strId);
		}
		
		req.setAttribute("cnt", cnt);
		
	}
	
	//탈퇴처리 페이지
	@Override
	public void deletePro(HttpServletRequest req, HttpServletResponse res) {
		// 1단계. 화면으로 아이디, 패스워드 값을 받아온다.
		String strId = (String)req.getSession().getAttribute("memId");
		String strPwd = req.getParameter("pwd");
		
		// 2-1단계. dao 객체생성(싱글톤, 다형성 적용)
		gMemberDAO dao = gMemberDAOImpl.getInstance();
		
		//2-2단계. 모델을 사용하여 요청한 기능을 수행한다. 
		/*
		 * 아이디와 패스워드가 일치하면 (selectCnt == 1) 입력한 회원정보를 읽어온다.
		 * 패스워드가 일치하지 않으면 (selectCnt == -1) | 아이디가 존재하지 않으면(selectCnt == 0).
		 * 
		 */
		
		int selectCnt = dao.check(strId, strPwd);
		int deleteCnt = 0;
		
		//아이디와 패스워드가 일치하면 회원탈퇴
		if(selectCnt == 1) {
			deleteCnt = dao.memberDelete(strId);
		}
		
		
		/*System.out.println("selectCnt -->" + selectCnt);
		System.out.println("deleteCnt -->" + deleteCnt);*/
		
		// 3단계. request나 session에 처리 결과를 저장하면 jsp에서 받는다.
		req.setAttribute("selectCnt", selectCnt);
		req.setAttribute("deleteCnt", deleteCnt);
		
	}
	
	//회원정보수정 상세페이지
	@Override
	public void modifyView(HttpServletRequest req, HttpServletResponse res) {
		// 1단계. 화면으로부터 id, pwd 받아온다.
		String strId = (String) req.getSession().getAttribute("memId");
		String strPwd = (String) req.getParameter("pwd");
		
		// 2-1단계. dao 객체생성(싱글톤, 다형성 적용)
		gMemberDAO dao = gMemberDAOImpl.getInstance();
		
		//2-2단계. 모델을 사용하여 요청한 기능을 수행한다. 
		int selectCnt = dao.check(strId, strPwd);
		
		//아이디와 패스워드가 일치하면, 수정하기 위해서, 입력한 정보를 읽어온다.
		if(selectCnt == 1) {
			gMemberVO vo = dao.getMemberInfo(strId);
			req.setAttribute("vo", vo);
		}
		
		// 3단계. request나 session에 처리 결과를 저장하면 jsp에서 받는다.
		req.setAttribute("selectCnt", selectCnt);
		
	}
	
	
	@Override
	public void modifyPro(HttpServletRequest req, HttpServletResponse res) {
		//1단계. 모델을 이용해서 요청한 기능을 수행한다.
		//vo 바구니 생성
		gMemberVO vo = new gMemberVO(); 		
				
		//2단계. dto 바구니에 담는다(화면에서 넘어온 값을).
		//id
		String id = (String) req.getSession().getAttribute("memId");		
		vo.setId(id);
		vo.setPwd(req.getParameter("pwd"));
		
		//name
		String name = req.getParameter("name");
		vo.setName(name);
		
		//hp
		String hp ="";
		String hp1 = req.getParameter("hp1");
		String hp2 = req.getParameter("hp2");
		String hp3 = req.getParameter("hp3");
		
		//필수입력항목이 아니므로 null 체크없이 무조건 insert하면 nullpointer Exception발생
		if(!hp1.equals("") && !hp2.equals("") && !hp3.equals("")) {
			hp = hp1 + "-" + hp2 + "-" + hp3;
			
		}
		vo.setHp(hp);
		
		//email
		String email="";		
		String email1 = req.getParameter("email1");
		String email2 = req.getParameter("email2");
		email = email1 + "@" + email2;
		vo.setEmail(email);
		
		//3단계. 다형성적용, 싱글톤 방식으로 객체생성
		gMemberDAO dao = gMemberDAOImpl.getInstance();
		
		//4단계. 모델을 사용하여 요청한 기능을 수행한다.
		//4단계. DAO에서 vo 바구니를 dao 파라미터에 넘겨서 해당SQL문 호출		
		int cnt = dao.updateMember(vo);		
		
		//5단계. request나 session에 처리결과를 저장하면 jsp에서 받는다.
		req.setAttribute("cnt", cnt);
		
		System.out.println("cnt : " + cnt);

	}
	

}
