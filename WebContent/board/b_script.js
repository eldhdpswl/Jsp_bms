/**
 * 
 */
var msg_writer = "작성자를 입력하세요.!!";
var msg_pwd = "비밀번호를 입력하세요.!!"
var msg_subject = "제목을 입력하세요!!";

var pwdError = "비밀번호가 일치하지 않습니다.\n확인후 다시 시도하세요!!";
var updateError = "글 수정에 실패했습니다.\n확인후 다시 시도하세요!!";
var deleteError = "글 삭제에 실패했습니다.\n확인후 다시 시도하세요!!";
var insertError = "글 작성에 실패했습니다.\n확인후 다시 시도하세요!!";

function errorAlert(msg){
	alert(msg);
	window.history.back();
}

//게시글 수정, 삭제
function pwdFocus(){
	document.pwdform.pwd.focus();
}

function pwdCheck(){
	if(!document.pwdform.pwd.value){
		alert(msg_pwd);
		document.pwdform.pwd.focus();
		return false;
	}
}

//글수정
function modifyFocus(){
	document.modifyform.subject.focus();
}

function modifyCheck(){
	if(!document.modifyform.subject.value){
		alert(msg_subject);
		document.modifyform.subject.focus();
		return false;
	}else if(!document.modifyform.pwd.value){
		alert(msg_pwd);
		document.modifyform.pwd.focus();
		return false;
	}
}

//글작성
function writeFocus(){
	document.writeform.writer.focus();
}


function writeCheck(){
	if(!document.writeform.writer.value){
		alert(msg_writer);
		document.writeform.writer.focus();
		return false;
	
	}else if(!document.writeform.pwd.value){
		alert(msg_pwd);
		document.writeform.pwd.focus();
		return false;
	
	}else if(!document.writeform.subject.value){
		alert(msg_subject);
		document.writeform.subject.focus();
		return false;
	}
	
}