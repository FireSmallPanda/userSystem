<@override name="title">显示员工列表</@override>
<@override name="style">
	#content{ margin-top:15px;}
</@override>
<@override name="content">

<#include "../head.ftl"/>
<div class="am-g" id="content" >
	<div class="am-u-sm-10 am-u-sm-centered">
		<div class="am-panel am-panel-default">
			<div class="am-panel-bd am-text-center">
				<table class="am-table am-table-bordered am-table-centered" >
					<tr>
						<td colspan="5" >
							<div class="am-g">
								<div class="am-u-sm-9">
									<form action="${rc.contextPath}/adminerDo/doSearchUser.htm" method="post" class="am-form am-form-horizontal "  id="searchUserFrom" >
										<div class="am-u-sm-6">
											<label for="userIdSearch" class="am-u-sm-4 am-form-label am-text-right">用户ID:</label>
											<div class="am-u-sm-8">
												<input type="number"  class="am-form-field am-input-sm" id="userIdSearch" name="userIdSearch" value="${searchUser.userId!''}" />
											</div>
										</div>
										<div class="am-u-sm-6">
											<label for="userNameSearch" class="am-u-sm-4 am-form-label am-text-right">用户姓名:</label>
											<div class="am-u-sm-8">
												<input type="text" class="am-form-field am-input-sm" id="userNameSearch" name="userNameSearch" value="${searchUser.userName!''}" />
											</div>
										</div>
										<div class="am-u-sm-6">
											<label for="userAccountSearch" class="am-u-sm-4 am-form-label am-text-right">用户邮箱:</label>
											<div class="am-u-sm-8">
												<input type="text"  class="am-form-field am-input-sm" id="userEmailSearch" name="userEmailSearch" value="${searchUser.userEmail!''}" />
											</div>
										</div>
										<div class="am-u-sm-6">
											<label for="userStatusSearch" class="am-u-sm-4 am-form-label am-text-right">用户状态:</label>
											<div class="am-u-sm-8">
												<@ui.select system="100001" group="100" name="userStatusSearch" value="${searchUser.userStatus!''}" />
											</div>
										</div>
									</form>
								</div>
								<div class="am-u-sm-3">
									<div class="am-vertical-align am-text-left" style="height: 75px; ">
										<button type="button" id="searchUserBtn" class="am-btn am-btn-secondary am-vertical-align-middle am-btn-xl " style="height: 50px; width:80px; " >搜索</button>
										<button type="button" id="showAllBtn" class="am-btn am-btn-default am-vertical-align-middle am-btn-xl " style="height: 50px; width:120px; " >显示全部</button>
									</div>
									
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<th>员工编号</th>
						<th>员工姓名</th>
						<th>员工电子邮件</th>
						<th>员工状态</th>
						<th>操作</th>
					</tr>
					<#if userList??>
						<#assign time=0 />
						<#list userList as user>
							<#assign time=time+50*1 />
							<tr data-am-scrollspy="{animation: 'fade', delay: '${time}'}">
								<td>${user.userId}</td>
								<#if user.userStatus == 1 >
									<td>${user.userName}</td>
								<#else>
									<td style="color:red;">${user.userName}</td>
								</#if>
								<td>${user.userEmail}</td>
								<td><@ui.dict system="100001" group="100" value="${user.userStatus}" /></td>
								<td><a href="javascript:openUpdateUserModal('${user.userId}','${user.userName}','${user.userAccount}','${user.userStatus}','${user.userEmail}');" >修改</a></td>
							</tr>
						</#list>
					</#if>
					<tr>
						<td colspan="5">
							<div class="am-fl am-btn-group">
								<div class="am-btn-group">
									<button class="am-btn am-btn-fn " onclick="javascript:searchByPage('previous');" >上一页</button>
					 				<input name="" type="number" class="am-btn am-btn-default"  size="4" value=${pageNo!'1'} id="pageNo" readonly />						
									<button class="am-btn am-btn-fn " onclick="javascript:searchByPage('next');">下一页</button>
								</div>
								总共页数为:<span id="totalPage">${totalPageCount}</span>
							</div>
							<button type="button" id="delUserBtn" class="am-btn am-btn-danger am-fr">删除</button>
							<button type="button" id="addUserBtn" class="am-btn am-btn-success am-fr">添加</button>
							<button type="button" id="downloadListBtn" class="am-btn am-btn-default am-fr">下载当前表格</button>
						</td>
					</tr>
				</table>
				<div class="am-g">
					<div class="am-u-sm-5 am-u-sm-centered am-text-center">
						<button type="button" id="backBtn" class="am-btn am-btn-default">返回</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<!--模态窗口-->
<!--添加用户模态-->
<div class="am-modal am-modal-prompt" tabindex="-1"  id="addUserModal">
	<div class="am-modal-dialog">
		<div class="am-modal-hd">添加员工信息</div>
		<div class="am-modal-bd">
			<form class="am-form am-form-horizontal " id="addUserFrom" data-am-validator >
				<div class="am-g am-form-group">
					<label for="userName" class="am-u-sm-4 am-form-label am-text-right">用户姓名:</label>
					<div class="am-u-sm-8">
						<input type="text" class="am-form-field" id="userName" name="userName" placeholder="请输入真实姓名" required/>
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
						<input type="password" minlength="6" class="am-form-field" id="rePassword" name="rePassword" placeholder="两次输入必须一致" data-equal-to="#userPassword" required/>
					</div>
				</div>
				<div class="am-g am-form-group">
					<label for="userEmail" class="am-u-sm-4 am-form-label am-text-right">电子邮箱:</label>
					<div class="am-u-sm-8">
						<input type="text" class="am-form-field js-pattern-email" id="userEmail" name="userEmail" placeholder="请输入电子邮箱" data-foolish-msg="请输入正确电子邮箱！" required/>
					</div>
				</div>
			</form>
			<button type="button" class="am-btn am-btn-sm am-btn-default" id="cancelAddUserBtn"  >取消</button>
			<button type="button" class="am-btn am-btn-sm am-btn-primary" id="submitAdduserBtn"  >提交</button>
		</div>
	</div>
</div>
<!--删除员工窗口-->
<div class="am-modal am-modal-prompt" tabindex="-1"  id="delUserModal">
	<div class="am-modal-dialog">
		<div class="am-modal-hd">删除员工信息</div>
		<div class="am-modal-bd">
			<form class="am-form am-form-horizontal " id="delUserFrom" data-am-validator >
				<div class="am-g am-form-group">
					<label for="userIdDel" class="am-u-sm-4 am-form-label am-text-right">用户ID:</label>
					<div class="am-u-sm-8">
						<input type="text"  class="am-form-field" id="userIdDel" name="userIdDel" placeholder="删除的用户ID" pattern="^\d{1,8}$" required/>
					</div>
				</div>
			</form>
			<button type="button" class="am-btn am-btn-sm am-btn-default" id="cancelDelUserBtn"  >取消</button>
			<button type="button" class="am-btn am-btn-sm am-btn-primary" id="submitDeluserBtn"  >提交</button>
		</div>
	</div>
</div>
<!--修改员工窗口-->
<div class="am-modal am-modal-prompt" tabindex="-1"  id="updateUserModal">
	<div class="am-modal-dialog">
		<div class="am-modal-hd">修改员工信息</div>
		<div class="am-modal-bd">
			<form class="am-form am-form-horizontal " id="updateUserFrom" data-am-validator >
				<div class="am-g">
					<div  class="am-u-sm-2 am-text-right">ID:</div>
					<div id="userIdUpdate" class="am-u-sm-4">
						xx
					</div>
					<div  class="am-u-sm-2 am-text-right">账号:</div>
					<div id="userAccountUpdate" class="am-u-sm-4">
						xx
					</div>
				</div>
				<div class="am-g am-form-group">
					<label for="userNameUpdate" class="am-u-sm-4 am-form-label am-text-right">用户姓名:</label>
					<div class="am-u-sm-8">
						<input type="text" class="am-form-field" id="userNameUpdate" name="userNameUpdate" placeholder="请输入修改的用户姓名" required/>
					</div>
				</div>
				<div class="am-g am-form-group">
					<label for="oldPasswordUpdate" class="am-u-sm-4 am-form-label am-text-right">原始密码:</label>
					<div class="am-u-sm-8">
						<input type="password" class="am-form-field" id="oldPasswordUpdate" name="oldPasswordUpdate" placeholder="若不修改无需输入" />
					</div>
				</div>
				<div class="am-g am-form-group">
					<label for="userPasswordUpdate" class="am-u-sm-4 am-form-label am-text-right">新建密码:</label>
					<div class="am-u-sm-8">
						<input type="password" minlength="6" class="am-form-field" id="userPasswordUpdate" name="userPasswordUpdate" placeholder="若不修改无需输入" disabled="disabled" />
					</div>
				</div>
				<div class="am-g am-form-group">
					<label for="rePasswordUpdate" class="am-u-sm-4 am-form-label am-text-right">确认密码:</label>
					<div class="am-u-sm-8">
						<input type="password" class="am-form-field" id="rePasswordUpdate" name="rePasswordUpdate" data-equal-to="#userPasswordUpdate" placeholder="若不修改无需输入" disabled="disabled" />
					</div>
				</div>
				<div class="am-g am-form-group">
					<label for="userEmailUpdate" class="am-u-sm-4 am-form-label am-text-right">电子邮箱:</label>
					<div class="am-u-sm-8">
						<input type="text" class="am-form-field js-pattern-email" id="userEmailUpdate" name="userEmailUpdate" placeholder="请输入电子邮箱" data-foolish-msg="请输入正确电子邮箱！"/>
					</div>
				</div>
				<div class="am-g am-form-group">
					<label for="userStatusUpdate" minlength="6" class="am-u-sm-4 am-form-label am-text-right">可用状态:</label>
					<div class="am-u-sm-8">
						<@ui.select system="100001" group="100" name="userStatusUpdate" />
					</div>
				</div>
			</form>
			<button type="button" class="am-btn am-btn-sm am-btn-default" id="cancelUpdateUserBtn"  >取消</button>
			<button type="button" class="am-btn am-btn-sm am-btn-primary" id="submitUpdateuserBtn"  >提交</button>
		</div>
	</div>
</div>
<#include "../foot.ftl"/>
</@override> 
<@override name="javascript">
	
	// 返回上一页页面按钮
	$("#backBtn").on('click',function() {
		window.location.href="${rc.contextPath}/admin/adminLoginSuccess.htm"
	});
	
	if ($.AMUI && $.AMUI.validator) {
		$.AMUI.validator.patterns.email =/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
	}
	// 用户信息“添加”按钮
	$("#addUserBtn").on('click',function() {
		// 清空表单验证
		$("#addUserFrom")[0].reset();
		$("#addUserFrom").validator("destroy");
		$("#addUserFrom").validator();
		
		// 打开模态窗口
		$('#addUserModal').modal({
				relatedTarget: this,
		});
	
	});
	// 添加用户信息模态"取消"按钮 
	$("#cancelAddUserBtn").on('click',function() {
		$('#addUserModal').modal('close');
	});
	//  添加用户信息模态"提交"按钮 
	$("#submitAdduserBtn").on('click',function() {
		if($("#addUserFrom").validator("isFormValid")&&checkAddUserInfo()) {
				submitAddUserInfo();
		}
	});
	
	// 用户添加资料验证
	var checkAddUserInfo = function() {
		// 用户姓名验证
		var regUserNameC =  /^[\u4e00-\u9fa5]{2,5}$/; // 中文2-5 
		var regUserNameE = /^[A-Z][A-Z,a-z ]{2,20}$/; // 英文3-20
		var userName = $("#userName").val();
		if(regUserNameC.test(userName)||regUserNameE.test(userName)){
			return true;
		}else{
			alertBox("error","用户姓名填写错误(中文姓名或英文姓名)！");
			return false;
		}
	}
	// 提交添加的用户信息
	var submitAddUserInfo = function() {
		var formVals = {};
		formVals.userName = $("#userName").val();
		formVals.userAccount = $("#userAccount").val();
		formVals.userPassword = $("#userPassword").val();
		formVals.userEmail =  $("#userEmail").val();
		$.ajax({
			type: "post",
			url: "${rc.contextPath}/adminerDo/doAddUser.htm",
			data: formVals,
			traditional:true,
			success: function(msg) {
				if(msg=="success"){
					alertBox("success","添加成功！");
					$('#addUserModal').modal('close');
				}
				if(msg=="error"){
					alertBox("warning","添加失败！");
				}
				if(msg=="nologin"){
					alertBox("error","您的登录已失效！");
				}
			}, error: function (XMLHttpRequest, textStatus, errorThrown) {
				alertBox("error","发生错误！");
				console.log(errorThrown);
			}
		});
	}
	// 用户信息“删除”按钮
	$("#delUserBtn").on('click',function() {
		// 清空表单验证
		$("#delUserFrom")[0].reset();
		$("#delUserFrom").validator("destroy");
		$("#delUserFrom").validator();
		
		// 打开模态窗口
		$('#delUserModal').modal({
				relatedTarget: this,
		});
	
	});
	// 删除用户信息模态"取消"按钮 
	$("#cancelDelUserBtn").on('click',function() {
		$('#delUserModal').modal('close');
	});
	//  删除用户信息模态"提交"按钮 
	$("#submitDeluserBtn").on('click',function() {
		if($("#delUserFrom").validator("isFormValid")) {
				submitDelUserInfo();
		}
	});
	// 提交删除的用户信息
	var submitDelUserInfo = function() {
		var formVals = {};
		formVals.userId = $("#userIdDel").val();
		
		$.ajax({
			type: "post",
			url: "${rc.contextPath}/adminerDo/doDelUser.htm",
			data: formVals,
			traditional:true,
			success: function(msg) {
				if(msg=="success"){
					alertBox("success","删除成功！");
					$('#delUserModal').modal('close');
				}
				if(msg=="error"){
					alertBox("warning","删除失败！");
				}
				if(msg=="nologin"){
					alertBox("error","您的登录已失效！");
				}
			}, error: function (XMLHttpRequest, textStatus, errorThrown) {
				alertBox("error","发生错误！");
				console.log(errorThrown);
			}
		});
	}
	// 打开修改用户信息模态窗口
	var openUpdateUserModal = function(userId,userName,userAccount,userStatus,userEmail){
		// 清空表单验证
		$("#updateUserFrom")[0].reset();
		$("#updateUserFrom").validator("destroy");
		$("#updateUserFrom").validator();
		// 禁用表单
		$("#userPasswordUpdate").attr("disabled",true);
		$("#rePasswordUpdate").attr("disabled",true);
		// 写入用户信息
		$("#userIdUpdate").html(userId);
		$("#userAccountUpdate").html(userAccount);
		$("#userNameUpdate").val(userName);
		$("#userEmailUpdate").val(userEmail);
		$("[name=userStatusUpdate]").val(userStatus);
		$("[name=userStatusUpdate]").find("option[value='"+userStatus+"']").attr("selected",true);  
		// 打开模态窗口
		$('#updateUserModal').modal({
				relatedTarget: this,
		});
	
	}
	// 修改用户信息模态窗口密码控制
	$("#oldPasswordUpdate").on('change',function() {
		if($("#oldPasswordUpdate").val()!=""){
			$("#userPasswordUpdate").attr("disabled",false);
			$("#rePasswordUpdate").attr("disabled",false);
		}else{
			$("#userPasswordUpdate").attr("disabled",true);
			$("#rePasswordUpdate").attr("disabled",true);
		}
	
	});
	// 修改用户信息模态"取消"按钮 
	$("#cancelUpdateUserBtn").on('click',function() {
		$('#updateUserModal').modal('close');
	});
	//  修改用户信息模态"提交"按钮 
	$("#submitUpdateuserBtn").on('click',function() {
		if($("#updateUserFrom").validator("isFormValid")&&checkUpdateUserInfo()){
			submitUpdateUserInfo();
		
		}
	});
	var submitUpdateUserInfo = function() {
		var formVals = {};
		formVals.userId=$("#userIdUpdate").html();
		formVals.userName=$("#userNameUpdate").val();
		formVals.userAccount=$("#userAccountUpdate").html();
		formVals.userPassword=$("#userPasswordUpdate").val();
		formVals.userStatus=$("[name=userStatusUpdate]").val();
		formVals.oldPassword=$("#oldPasswordUpdate").val();
		formVals.userEmail=$("#userEmailUpdate").val();
		$.ajax({
			type: "post",
			url: "${rc.contextPath}/adminerDo/doUpdateUser.htm",
			data: formVals,
			traditional:true,
			success: function(msg) {
				if(msg=="success"){
					alertBox("success","更改成功！");
					$('#updateUserModal').modal('close');
				}
				if(msg=="passwordError"){
					alertBox("warning","原始密码错误或帐户已经禁用！");
				}
				if(msg=="error"){
					alertBox("warning","更改失败！");
				}
				if(msg=="nologin"){
					alertBox("error","您的登录已失效！");
				}
			}, error: function (XMLHttpRequest, textStatus, errorThrown) {
				alertBox("error","发生错误！");
				console.log(errorThrown);
			}
		});
	}
	// 用户更改资料验证
	var checkUpdateUserInfo = function() {
		// 用户姓名验证
		var regUserNameC =  /^[\u4e00-\u9fa5]{2,5}$/; // 中文2-5 
		var regUserNameE = /^[A-Z][A-Z,a-z ]{2,20}$/; // 英文3-20
		var userName = $("#userNameUpdate").val();
		if(regUserNameC.test(userName)||regUserNameE.test(userName)){
			return true;
		}else{
			alertBox("error","用户姓名填写错误(中文姓名或英文姓名)！");
			return false;
		}
	}
	// 搜索按钮按下提交搜索内容
	$("#searchUserBtn").on("click", function(){
		$("#searchUserFrom").submit();
		
	});
	
	// 根据页面搜索
	var searchByPage = function(pageFlag){
	
		var totalPage=$("#totalPage").html();
		var pageNo = $("#pageNo").val();
		
		
		if(pageFlag=="next"){
			pageNo=pageNo*1+1;
		}else if(pageFlag=="previous"){
			pageNo=pageNo*1-1;
		}
		
		if(pageNo>totalPage){
			alertBox("warning","没有下一页了！");
		}else if(pageNo<=0){
			alertBox("warning","没有上一页了！");
		}else{
			window.location.href="${rc.contextPath}/adminerDo/doSearchUserByPage.htm?pageNo="+pageNo;
		}
	}
	// "显示全部" 按钮
	$("#showAllBtn").on("click",function(){
		window.location.href="${rc.contextPath}/adminerDo/doShowAllUser.htm";
		
	});
	// "下载当前表格"按钮
	$("#downloadListBtn").on("click",function(){
		var pageNo=$("#pageNo").val();
		window.open("${rc.contextPath}/adminerDo/doDownloadUserList.htm?pageNo="+pageNo);
	});
	
</@override>
<@extends name="../base.ftl" />


