<@override name="title">登录页面</@override>
<style type="text/css">

</style>
<@override name="style">
	.am-panel-hd {
		margin-bottom: -1px;
	}

	#content {
		margin-top: 20px;
	}
	
	.loginInfo {
		margin-bottom: 15px;
	}
	
	.labelText {
		padding-top: 5px;
	}
</@override>
<@override name="content">
<#include "../head.ftl"/>

<div class="am-g" id="content">
	<div class="am-u-sm-7 am-u-sm-centered">

		<div class="am-panel am-panel-default">
			<h3 class="am-panel-hd am-text-center">登录信息</h3>
			<div class="am-panel-bd">
				<form action="#" method="POST" id="myForm" data-am-validator>
					<div class="am-g loginInfo">
						<div class="am-u-sm-7 am-u-sm-centered am-form-group ">
							<label for="userAccount"
								class="am-u-sm-4 am-form-label am-text-right labelText">通行账号:</label>
							<div class="am-u-sm-8">
								<input type="text" class=" am-form-field" id="userAccount"
									name="userAccount" required />
							</div>
						</div>
					</div>
					<div class="am-g loginInfo">
						<div class="am-u-sm-7 am-u-sm-centered am-form-group loginInfo">
							<label for="userPassword"
								class="am-u-sm-4 am-form-label am-text-right labelText">密码:</label>
							<div class="am-u-sm-8">
								<input type="password" class="am-form-field" id="userPassword"
									name="userPassword" required />
								<div id="bigDropdownBox" style="width:120px;font-size:14px;">
									<div class="am-dropdown" id="bigDropdown" >
										<div class="am-dropdown-content" >大写已经打开!</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="am-g loginInfo">
						<div class="am-u-sm-7 am-u-sm-centered am-form-group loginInfo">
							<label for="verificationCode"
								class="am-u-sm-4 am-form-label am-text-right labelText">验证码:</label>
							<div class="am-u-sm-8">
								<div class="am-g">
									<div class="am-u-sm-6"><input type="text" class="am-form-field" id="verificationCode" name="verificationCode"  required></div>
									<div class="am-u-sm-6"><img  id="verificationCodeImg" src="${rc.contextPath}/get/validateCode.htm"/></div>
								</div>
							</div>
						</div>
					</div>
				</form>
				<div class="am-g">
					<div class="am-u-sm-5 am-text-center am-u-sm-centered">
						<button type="button" id="loginBtn" class="am-btn am-btn-primary "
							>登录</button>&nbsp;&nbsp;&nbsp;
						<button type="button" id="registerBtn" class="am-btn am-btn-default">注册</button>&nbsp;&nbsp;&nbsp;
						<button type="button" id="forgetBtn" class="am-btn am-btn-warning">忘记密码</button>
					</div>
				</div>
			</div>
		</div>

	</div>
</div>
<!--找回密码模态-->
<div class="am-modal am-modal-prompt" tabindex="-1"  id="forgetModal">
	<div class="am-modal-dialog">
		<div class="am-modal-hd">帐户信息</div>
		<div class="am-modal-bd">
			<form class="am-form am-form-horizontal " id="forgetFrom" data-am-validator >
				<div class="am-g am-form-group">
					<label for="userName" class="am-u-sm-4 am-form-label am-text-right">您的帐号:</label>
					<div class="am-u-sm-8">
						<input type="text" class="am-form-field" id="forgetUserAccount" name="forgetUserAccount" placeholder="请输入您的账号" required/>
					</div>
				</div>
				<div class="am-g am-form-group">
					<label for="userName" class="am-u-sm-4 am-form-label am-text-right">邮箱验证码:</label>
					<div class="am-u-sm-4">
						<input type="text" class="am-form-field" id="forgetUserEmailCode" name="forgetUserEmailCode" placeholder="邮件验证码" required/>
					</div>
					<div class="am-u-sm-4">
						<button type="button" class="am-btn am-btn-sm am-btn-secondary" id="sentEmailCode" disabled="disabled" >发送验证码</button>
					</div>
				</div>
				<div class="am-g am-form-group">
					<label for="forgetUserPassword" class="am-u-sm-4 am-form-label am-text-right">新建密码:</label>
					<div class="am-u-sm-8">
						<input type="password" minlength="6" class="am-form-field" id="forgetUserPassword" name="forgetUserPassword" placeholder="不得小于6位" />
					</div>
				</div>
				<div class="am-g am-form-group">
					<label for="#forgetRePassword" class="am-u-sm-4 am-form-label am-text-right">确认密码:</label>
					<div class="am-u-sm-8">
						<input type="password" class="am-form-field" id="forgetRePassword" name="forgetRePassword" data-equal-to="#forgetUserPassword" placeholder="与上面密码一致" />
					</div>
				</div>
			</form>
			<button type="button" class="am-btn am-btn-sm am-btn-default" id="cancelforgetBtn"  >取消</button>
			<button type="button" class="am-btn am-btn-sm am-btn-primary" id="submitforgetBtn" disabled="disabled"  >提交</button>
		</div>
	</div>
</div>
<#include "../foot.ftl"/>
</@override> 
<@override name="javascript">
	var time = 15;
	$(function() {
		$('#bigDropdown').dropdown({justify: '#bigDropdownBox'});
		checkWaitEmail();
	});
	
	// 表单提交验证
	$("#loginBtn").click(function() {
		if($('#myForm').validator('isFormValid')){
			submit();
		}
	});
	// 注册按钮
	$("#registerBtn").click(function() {
		document.location.href = "${rc.contextPath}/user/register.htm";
	});
	// 刷新验证码
	$("#verificationCodeImg").on("click",function() {
		$(this).attr("src", "${rc.contextPath}/get/validateCode.htm?" + new Date())
	});
	// 提交表单
	var  submit = function() {
		var formVals = {};
		formVals.userAccount = $("#userAccount").val();
		formVals.userPassword = $("#userPassword").val();
		formVals.verificationCode = $("#verificationCode").val();
		$.ajax({
			type: "post",
			url: "${rc.contextPath}/userDo/doLogin.htm",
			data: formVals,
			traditional:true,
			success: function(msg) {
				if(msg=="01"){
					alertBox("warning","验证码错误！");
				}else if(msg=="02"){
					alertBox("warning","密码或账号错误！");
				}else if(msg=="user"){
					document.location.href = "${rc.contextPath}/user/loginSuccess.htm";
				}else if(msg=="admin"){
					document.location.href = "${rc.contextPath}/admin/adminLoginSuccess.htm";
				}
				// 清空验证码
				$("#verificationCode").focus(); 
				$("#verificationCodeImg").attr("src", "${rc.contextPath}/get/validateCode.htm?" + new Date());
				$("#verificationCode").val("");
			}, error: function (XMLHttpRequest, textStatus, errorThrown) {
				console.log(errorThrown);
			}
		});
	}
	/*// 检测大小写
	$("#userPassword").on("click",function() {
		$('#userPassword').removeAttr("data-am-popover");
	});*/
	// 检测大小写
	$("#userPassword").on("keypress",function(e){
		var password = $(this).val();
		var lastChar = password.charAt(password.length - 1);
		if( lastChar >= "A" && lastChar<="Z") {
			$("#bigDropdown").dropdown('open');
		}else{
			$("#bigDropdown").dropdown('close');
		}
	});
	
	// "忘记密码"按钮
	$("#forgetBtn").on('click',function() {
		// 清空表单验证
		$("#forgetFrom")[0].reset();
		$("#forgetFrom").validator("destroy");
		$("#forgetFrom").validator();
		
		// 打开模态窗口
		$('#forgetModal').modal({
				relatedTarget: this,
		});
	
	});
	//"忘记密码"取消按钮
	$("#cancelforgetBtn").on('click',function(){
		$('#forgetModal').modal('close');
	});
	// 忘记密码  帐号失去焦点时
	$("#forgetUserAccount").on("blur",function(){
		var account = $("#forgetUserAccount").val();
		if(account!=""){
			checkEmail();
		}
	
	});
	// 得到email
	var checkEmail = function(){
		var formVals = {};
		formVals.userAccount=$("#forgetUserAccount").val();
		$.ajax({
			type: "post",
			url: "${rc.contextPath}/adminerDo/checkUserEmail.htm",
			data: formVals,
			traditional:true,
			success: function(msg) {
				if(msg=="01"){
					$("#submitforgetBtn").attr("disabled",false);
					$("#sentEmailCode").attr("disabled",false);
				}else if(msg=="02"){
					$("#submitforgetBtn").attr("disabled",true);
					$("#sentEmailCode").attr("disabled",true);
					alertBox("warning","账号邮件不存在！");
				}
			}, error: function (XMLHttpRequest, textStatus, errorThrown) {
				alertBox("error","发生错误！");
				console.log(errorThrown);
			}
		});
	}
	// 忘记密码提交按钮
	$("#submitforgetBtn").on("click",function(){
		if($("#forgetFrom").validator("isFormValid")){
			submitForgetFrom();
		}
	});
	// 忘记密码提交按钮
	var submitForgetFrom = function(){
		var formVals = {};
		formVals.userPassword = $("#forgetUserPassword").val();
		formVals.emailCode = $("#forgetUserEmailCode").val();  
		$.ajax({
			type: "post",
			url: "${rc.contextPath}/adminerDo/setNewPassword.htm",
			data: formVals,
			traditional:true,
			success: function(msg) {
				if(msg=="01"){
					alertBox("warning","邮件验证码错误！");
				}else if(msg=="02"){
					alertBox("success","修改成功！");
					$('#forgetModal').modal('close');
				}else if(msg=="03"){
					alertBox("warning","修改账号不存在！");
				}else if(msg=="04"){
					alertBox("warning","您未发送验证码！");
				}else{
					alertBox("error","发生错误！");
				}
			}, error: function (XMLHttpRequest, textStatus, errorThrown) {
				alertBox("error","发生错误！");
				console.log(errorThrown);
			}
		});
	}
	// 发送邮件
	$("#sentEmailCode").on("click",function(){
		wiatEmail();
		/*checkWaitEmail();
		// 按钮等待
		setWaitEmail("yes");*/
		
		//发送邮件
		sentEmailCodeWay();
	});
	// 发送邮件方法
	var sentEmailCodeWay = function(){
		var formVals = {};
		formVals.userAccount=$("#forgetUserAccount").val();
		$.ajax({
			type: "post",
			url: "${rc.contextPath}/adminerDo/sentEmail.htm",
			data: formVals,
			traditional:true,
			success: function(msg) {
				if(msg=="01"){
					alertBox("warning","发送失败！");
				}else if(msg=="02"){
					alertBox("success","发送成功,请查收！");
				}else if(msg=="03"){
					alertBox("warning","账号邮箱不存在！");
				}else{
					alertBox("error","发生错误！");
				}
			}, error: function (XMLHttpRequest, textStatus, errorThrown) {
				alertBox("error","发生错误！");
				console.log(errorThrown);
			}
		});
	
	}
	
	
	
	// 检查等待Flag
	var checkWaitEmail = function(){
		var waitEmailFlag = "${waitEmailFlag!'yes'}";
		if(waitEmailFlag!="no"){
			wiatEmail();
		}
	}
	// 设置等待flag
	var setWaitEmail = function(waitEmailFlag){
		$.ajax({
			type: "post",
			url: "${rc.contextPath}/adminerDo/setWaitEmail.htm",
			data: {waitEmailFlag:waitEmailFlag},
			traditional:true,
			async: false,
			success: function(msg) {
			}, error: function (XMLHttpRequest, textStatus, errorThrown) {
				alertBox("error","发生错误！");
				console.log(errorThrown);
			}
		});
	}
	// 禁用按钮
	var wiatEmail = function()
	{
		if(time=='undefined')
			time = 60;
			$("#sentEmailCode").html(time+"秒后可发送");
			$("#sentEmailCode").attr("disabled",true);
			time = time-1;
		if(time>=0)
		{
			setTimeout("wiatEmail("+time+")",1000);
		}else{
			// setWaitEmail("no");
			$("#sentEmailCode").html("发送验证码");
			// checkEmail();
			
			
			time = 15;
			$("#sentEmailCode").attr("disabled",false);
		}
	}
	
</@override>
<@extends name="../base.ftl" />
