<@override name="title">登录失败</@override>
<@override name="style">
	.am-g{ margin-top:15px;}
</@override>
<@override name="content">

<#include "../head.ftl"/>
<div class="am-g" >
	<div class="am-u-sm-7 am-u-sm-centered">
		<div class="am-panel am-panel-default">
			<div class="am-panel-bd am-text-center">
				您不是改组成员请返回!
				
				<div class="am-g">
					<div class="am-u-sm-5 am-u-sm-centered am-text-center">
						<button type="button" id="backBtn" class="am-btn am-btn-default">返回</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>		
<#include "../foot.ftl"/>
</@override> 
<@override name="javascript">
	// 返回登录页面按钮
	$("#backBtn").click(function() {
		window.location.href="${rc.contextPath}/user/login.htm"
	});
</@override>
<@extends name="../base.ftl" />