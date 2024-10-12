let index = {
	init: function(){
		$("#btn-save").on("click", ()=>{
			this.save();
		});
	},
	
	save: function(){
		//alert("user의 save 함수 호출됨");
		let data = {
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};
		console.log(data);
		
		// ajax 통신을 이용해 3개의 데이터를 json으로 변경하여 insert 요청
		/*
		$.ajax({
			// 회원가입 수행 요청
			type: "POST",
			url: "/blog/api/user",
			data: JSON.stringify(data),	// http body 데이터
			contentType: "application/json; charset=utf-8", 	// body 데이터가 어떤 타입인지
			dataType: "json"	// 응답
		}).done(function(resp){
			// 회원가입 성공
			alert("회원가입이 완료되었습니다.");
			location.href="/blog";
		}).fail(function(error){
			// 회원가입 실패
			alert(JSON.stringify(error));
		});	
		*/
	}
}

index.init();