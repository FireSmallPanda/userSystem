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
	#vld-tooltip {
		position: absolute;
		z-index: 1000;
		padding: 5px 10px;
		background: #F37B1D;
		min-width: 0px;
		color: #fff;
		transition: all 0.15s;
		box-shadow: 0 0 5px rgba(0,0,0,.15);
		display: none;
	}
	#vld-tooltip:before {
		position: absolute;
		top: -8px;
		left: 50%;
		width: 0;
		height: 0;
		margin-left: -8px;
		content: "";
		border-width: 0 8px 8px;
		border-color: transparent transparent #F37B1D;
		border-style: none inset solid;
	}
</@override>
<@override name="content">
<#include "../head.ftl"/>

<div class="am-g" id="content">
	<div class="am-u-sm-10 am-u-sm-centered">
		<div class="am-panel am-panel-default">
			<h3 class="am-panel-hd am-text-center">注册信息</h3>
			<div class="am-panel-bd">
				<div class="am-g">
					<div class="am-u-sm-5 am-text-center am-u-sm-centered">
						<form class="am-form am-form-horizontal " id="addUserFrom" data-am-validator >
							<div class="am-g am-form-group">
								<label for="userName" class="am-u-sm-4 am-form-label am-text-right">用户姓名:</label>
								<div class="am-u-sm-8">
									<input type="text" class="am-form-field js-pattern-regUserName tip" id="userName" name="userName" placeholder="请输入真实姓名" data-foolish-msg="名字必须是纯中文(2-5位)或纯英文(3-20位)！" required/>
								</div>
							</div>
							<div class="am-g am-form-group">
								<label for="userAccount" class="am-u-sm-4 am-form-label am-text-right">用户帐户:</label>
								<div class="am-u-sm-8">
									<input type="text" minlength="3" class="am-form-field" id="userAccount" name="userAccount" placeholder="帐户不得小于3位"  required/>
								</div>
							</div>
							<div class="am-g am-form-group">
								<label for="userPassword" class="am-u-sm-4 am-form-label am-text-right">用户密码:</label>
								<div class="am-u-sm-8">
									<input type="password" minlength="6" class="am-form-field" id="userPassword" name="userPassword" placeholder="密码必须不得小于6位" required/>
								</div>
							</div>
							<div class="am-g am-form-group">
								<label for="rePassword" class="am-u-sm-4 am-form-label am-text-right">确认密码:</label>
								<div class="am-u-sm-8">
									<input type="password" minlength="6" class="am-form-field tip" id="rePassword" name="rePassword" placeholder="两次输入必须一致" data-equal-to="#userPassword" data-foolish-msg="两次输入密码必须一致！" required/>
								</div>
							</div>
							<div class="am-g am-form-group">
								<label for="userEmail" class="am-u-sm-4 am-form-label am-text-right">电子邮箱:</label>
								<div class="am-u-sm-8">
									<input type="text" class="am-form-field js-pattern-email tip" id="userEmail" name="userEmail" placeholder="请输入电子邮箱" data-foolish-msg="请输入正确电子邮箱！" required/>
								</div>
							</div>
						</form>
						<button type="button" class="am-btn am-btn-sm am-btn-default" id="backAddUserBtn"  >返回</button>
						<button type="button" class="am-btn am-btn-sm am-btn-primary" id="submitAdduserBtn"  >提交</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<#include "../foot.ftl"/>
</@override> 
<@override name="javascript">
	$(function(){
		tipInfo();
	});
	
	if ($.AMUI && $.AMUI.validator) {
		$.AMUI.validator.patterns.regUserName = /^(([\u4e00-\u9fa5]{2,5})||([A-Z][A-Z,a-z ]{2,20}))$/; // 中文2-5 
		$.AMUI.validator.patterns.email =/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
	}
	
	// 提交按钮
	$("#submitAdduserBtn").on("click",function(){
		if($("#addUserFrom").validator("isFormValid")) { // 验证表单
				$("#submitAdduserBtn").button('loading');
				submit();
				$("#submitAdduserBtn").button('reset');
		} 
	});
	// 返回按钮
	$("#backAddUserBtn").on("click",function(){
		document.location.href = "${rc.contextPath}/user/login.htm";
	});
	var submit = function() {
		var formVals = {};
		formVals.userName = $("#userName").val();
		formVals.userAccount = $("#userAccount").val();
		formVals.userPassword = $("#userPassword").val();
		formVals.userEmail =  $("#userEmail").val();
		$.ajax({
			type: "post",
			url: "${rc.contextPath}/userDo/doRegister.htm",
			data: formVals,
			contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			success: function(msg) {
				if(msg==""){
					alert("恭喜！注册成功！");
					document.location.href = "${rc.contextPath}/user/login.htm";
				}else{
					switch (msg)
					{
						case '01':
							alertBox("warning","注册失败！");
						break;
						case '02':
							alertBox("warning","该帐户已经存在！");
						break;
						default:
							alertBox("error","未知错误，请联系管理员！");
					
					}
				}
			}, error: function (XMLHttpRequest, textStatus, errorThrown) {
				$("#submitAdduserBtn").button('reset');
			}
		});
	}
	// 表单报错
	var tipInfo = function(){
		var $form = $('#addUserFrom');
		var $tooltip = $('<div id="vld-tooltip">提示信息！</div>');
		$tooltip.appendTo(document.body);
		$form.validator();
		var validator = $form.data('amui.validator');
		$form.on('focusin focusout', '.am-form-error input', function(e) {
			if (e.type === 'focusin' && $(this).hasClass("tip")) {
				var $this = $(this);
				var offset = $this.offset();
				var msg = $this.data('foolishMsg') || validator.getValidationMessage($this.data('validity'));
				$tooltip.text(msg).show().css({
					left: offset.left + 10,
					top: offset.top + $(this).outerHeight() + 10
				});
			} else {
				$tooltip.hide();
			}
		});
		// 表单失去焦点隐藏提示框
		$("input").on("blur",function(){
			$tooltip.hide();
		});
	}
</@override>
<@extends name="../base.ftl" />
