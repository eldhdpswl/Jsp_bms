/**
 * 
 */
//회원가입에 관한 메시지
var msg_id = "아이디를 입력하세요.";
var msg_pwd = "패스워드를 입력하세요.";
var msg_repwd = "비밀번호를 한번 더 입력하세요.";
var msg_pwdChk = "비밀번호가 다릅니다.";
var msg_name = "이름을 입력하세요.";
var msg_birth = "주민번호를 입력하세요.";
var msg_email = "이메일을 입력하세요.";
var msg_emailChk = "이메일 형식에 일치하지 않습니다.";
var msg_confirmId = "중복확인을 해주세요.";

var insertError = "회원가입에 실패했습니다. \n확인 후 다시 시도하세요.";
var deleteError = "회원탈퇴에 실패했습니다. \n확인 후 다시 시도하세요.";
var updateError = "회원정보 수정을 실패했습니다. \n확인 후 다시 시도하세요.";
var passwdError = "입력하신 비밀번호가 일치하지 않습니다. \n확인 후 다시 시도하세요.";

//장바구니에 관한 메시지
var insertcartAddError = "장바구니담기에 실패했습니다. \n확인 후 다시 시도하세요.";
var insertcartAddSuccess = "축하합니다! 장바구니담기에 성공했습니다.";

var cartcountError = "장바구니에 담을 권수를 입력하세요.";

//구매단가지전의 메시지
var beforepayError = "다시 확인해주세요.";
var beforepaySuccess = "구매해주세요!";


//장바구니에서 결재하기버튼 누르기전에 체크값이 없을떄 
var cartlistcheck = "결재할 정보가 없습니다. 다시 확인해주세요!";

//결재하기버튼눌렀을때
var paysuccess = "결재가 완료되었습니다!!!";
var payfail = "결재가 확인되지않았습니다. 다시확인해주세요.";


//에러메시지
function errorAlert(errorMsg){
	alert(errorMsg);
	window.history.back();//이전 페이지로 이동	
}

//성공메시지
function successAlert(successMsg){
	alert(successMsg);
	return false;
}
function paylistcheck(){
	 
}
//장바구니에서 결재하기, 삭제하기할때 체크한 값이 없을떄
/*function paylistcheck(){
    var isSeasonChk = false;
    var arr_season =document.getElementsByName("cartNum");
    for(var i=0;i<arr_season.length;i++){
    	if(arr_season[i].checked==true){
    		isSeasonChk=true;
    		break;
    	}
    }
    if(!isSeasobChk){
    	alert("결재할 정보가 없습니다! 다시확인해주세요!");
    	return false;
    }
    
    
    
	if(!document.mycartlist.cartNum[].checked){
		alert("결재할 정보가 없습니다! 다시확인해주세요!");
		return false;
	}
	
}*/

/*function cartdelete(){
	if(!document.mycartlist.cartNum.value){
		alert("결재할 정보가 없습니다! 다시확인해주세요!");
		return false;
	}
}*/


//메인페이지
function mainFocus(){
	document.mainform.id.focus();
}

//메인화면 버튼 클릭시
function mainCheck(){
	if(!document.mainform.id.value){
		alert(msg_id);
		document.mainform.id.focus();
		return false;
	}else if(!document.mainform.pwd.value){
		alert(msg_pwd);
		document.mainform.pwd.focus();
		return false;
	}
	
}

//회원가입 페이지
function inputFocus(){
	document.inputform.id.focus();
}

//회원가입 버튼 클릭시
function inputCheck(){
	if(!document.inputform.id.value){
		alert(msg_id);
		document.inputform.id.focus();
		return false;  //return false를 안하면 계속해서 밑으로 내려가서 실행된다.
		
	}else if(!document.inputform.pwd.value){
		alert(msg_pwd);
		document.inputform.pwd.focus();
		return false;
		
	}else if(!document.inputform.repwd.value){
		alert(msg_repwd);
		document.inputform.repwd.focus();
		return false;
		
	}else if(document.inputform.pwd.value != document.inputform.repwd.value){
		alert(msg_pwdChk);
		document.inputform.pwdChk.focus();
		return false;
		
	
		
	}else if(!document.inputform.name.value){
		alert(msg_name);
		document.inputform.name.focus();
		return false;
		
	}else if(!document.inputform.jumin1.value){
		alert(msg_birth);
		document.inputform.jumin1.focus();
		return false;
		
	}else if(!document.inputform.jumin2.value){
		alert(msg_birth);
		document.inputform.jumin2.focus();
		return false;
		
	//이메일 중요	
	}else if(!document.inputform.email1.value){
		alert(msg_email);
		document.inputform.email1.focus();
		return false;
	
	//직접입력인 경우 - email2
	}else if(document.inputform.email3.value==0 && !document.inputform.email2.value ){
		alert(msg_email);
		document.inputform.email2.focus();
		return false;
	
	
	//중복확인 버튼을 클릭하지 않는 경우
	//체크전제조건 : inputForm.jsp의 form안에 <input type="hidden" name="hiddenId" value="0">
	//hiddenId : 중복확인 버튼 클릭 여부 체크(0:클릭안함, 1:클릭함) 
		
	}else if(document.inputform.hiddenId.value == "0"){
		alert(msg_confirmId);
		document.inputform.dupChk.focus();
		return false;
	}
}

//중복확인
function confirmId() {
	if (!document.inputform.id.value) {
		alert(msg_id);
		document.inputform.id.focus();
		return false;
	}
	
	var url = "confirmId.mem?id=" + document.inputform.id.value;
	window.open(url, "confirm", "menubar=no, width=500, height=300"); // url은
																		// 위의
}

//중복확인 버튼 클릭시
function confirmIdFocus(){
	document.confirmform.id.focus();
}

//중복확인창에서 id입력 여부
function confirmIdCheck(){
	if(!document.confirmform.id.value){
		alert(msg_id);
		document.confirmform.id.focus();
		return false;
	}
}

function setId(id){
	opener.document.inputform.id.value=id;
	opener.document.inputform.hiddenId.value=1;
	self.close();
}

function nextJumin1() {
	if (document.inputform.jumin1.value.length >= 6) {
		document.inputform.jumin2.focus();
	}

}

function nextJumin2() {
	if (document.inputform.jumin2.value.length >= 7) {
		document.inputform.hp1.focus();
	}

}

function nextHp1() {
	if (document.inputform.hp1.value.length >= 3) {
		document.inputform.hp2.focus();
	}

}

function nextHp2() {
	if (document.inputform.hp2.value.length >= 4) {
		document.inputform.hp3.focus();	
	}

}

//hp3해야됨
function nextHp3(){
	if(document.inputform.hp3.value.length >= 4){
		document.inputform.email1.focus();
	}
}

//회원탈퇴, 회원정보수정
function passwdFocus(){
	document.passwdform.pwd.focus();
}


function passwdCheck(){
	if(!document.passwdform.pwd.value){
		alert(msg_pwd);
		document.passwdform.pwd.focus();
		return false;
	}
}

function modifyFocus(){
	document.modifyform.pwd.focus();
}

function modifyCheck(){
	
	//비밀번호 입력값이 없을때
	if(!document.modifyform.pwd.value){
		alert(msg_pwd);
		document.modifyform.pwd.focus();
		return false;
	
	//비밀번호 확인값이 없을때
	}else if(!document.modifyform.repwd.value){
		alert(msg_repwd);
		document.modifyform.repwd.focus();
		return false;
	
	//비밀번호 불일치시	
	}else if(document.modifyform.pwd.value != document.modifyform.repwd.value){
		alert(msg_pwdChk);
		document.modifyform.repwd.focus();
		return false;
	
	//이름 미입력시
	}else if(!document.modifyform.name.value){
		alert(msg_name);
		document.modifyform.name.focus();
		return false;
		
	//이메일 입력값이 없을때 
	}else if(!document.modifyform.email1.value){
		alert(msg_email);
		document.modifyform.email1.focus();
		return false;
	
		//이메일 입력값이 없을때 	
	}else if(!document.modifyform.email2.value){
		alert(msg_email);
		document.modifyform.email2.focus();
		return false;
	}
}

function directpayCheck(){
	if(!document.cartcontent.cartcount.value){
		alert(cartcountError);
		return false;
	}
	var bookNum=document.cartcontent.bookNum.value;
	var bookcount=document.cartcontent.cartcount.value;
	
	window.location="directpay.mem?bookNum="+bookNum+"&bookcount="+bookcount;
}

//cart 장바구니담기로 넘길때
//window.location='cartTakePro.mem?bookNum=${dto.bookNum}&pageNum=${pageNum}&id=${memId}'
function cartAdd(){
	if(!document.cartcontent.cartcount.value){
		alert(cartcountError);
		return false;
	}
	
	var bookNum = document.cartcontent.bookNum.value;
	var pageNum = document.cartcontent.pageNum.value;
	var id = document.cartcontent.id.value;
	var cartcount = document.cartcontent.cartcount.value;
	window.location="cartTakePro.mem?bookNum="+bookNum+"&pageNum="+pageNum+"&id="+id+"&cartcount="+cartcount;
}

/////////////////////////////////////중요부분
function pay(){
	
	/*<input type="hidden" name="id" value="${memId}">
	<input type="hidden" name="bookNum" value="${dto.bookNum}">
	<input type="hidden" name="kimg" value="${dto.kimg}">
	<input type="hidden" name="bookName" value="${dto.bookName}">
	<input type="hidden" name="price" value="${dto.price}">
	<input type="hidden" name="total" value="${dto.total}">*/
	
	var mycartcount = document.mycartlist.mycartcount.value;
	var id = document.mycartlist.id.value;
	var bookNum = document.mycartlist.bookNum.value;
	var kimg = document.mycartlist.kimg.value;
	var bookName = document.mycartlist.bookName.value;
	var price = document.mycartlist.price.value;
	var total = document.mycartlist.total.value;
	var cartNum = document.mycartlist.cartNum;
	var sum;   /*중요부분*/
	
	if(cartNum.length==undefined){ /*배열이 아닐때 */
		window.location="pay.mem?mycartcount="+mycartcount+"&id="+id+"&bookNum="+bookNum+
		"&kimg="+kimg+"&bookName="+bookName+"&price="+price+"&total="+total+"&cartNum="+cartNum.value;
	}else { /*배열일때  */
		for(var i=0; i<cartNum.length; i++){
			if(document.mycartlist.cartNum[i].checked){
				sum += (i==0 ? cartNum[i].value : "?" + cartNum[i].value); ////삼항연산자 ==> i가 0일때 앞부분, 아닐때는 뒷부분
				window.location="pay.mem?mycartcount="+mycartcount+"&id="+id+"&bookNum="+bookNum+
				"&kimg="+kimg+"&bookName="+bookName+"&price="+price+"&total="+total+"&cartNum="+sum;
			}
		}
	}
	
	
	
	
}




