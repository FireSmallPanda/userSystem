<header data-am-widget="header" class="am-header am-header-default" >
	<div class="am-header-left am-header-nav">
		<a href="${rc.contextPath}/user/login.htm" class="" id="headHome"> <span class="am-header-nav-title">
				首页 </span> <i class="am-header-icon am-icon-home"></i>
		</a>
	</div>

	<h1 class="am-header-title">小熊猫的帐户管理系统</h1>

	<div class="am-header-right am-header-nav">
		 <span class="am-header-nav-title" style="cursor: pointer" id="view" >
		意见投递</span> <i class="am-header-icon am-icon-bars"></i>
		
	</div>
	<div id="viewContent" class="am-offcanvas">
		<div class="am-offcanvas-bar am-offcanvas-bar-flip">
			<div class="am-offcanvas-content">
				<form action="#" method="POST" id="viewForm" data-am-validator >
					您的联系方式:
					<input type="text" class=" am-form-field" id="consumerViewContactWay" name="consumerViewContactWay" placeholder="可以是QQ、电子邮箱或电话" required />
					意见或介意内容:
					<textarea id="consumerViewContext" name="consumerViewContext" rows="5" cols="31" placeholder="请写出错误页面和错误信息哦，我们会尽快处理并与您联系。" required ></textarea>
					<div class="am-g">
						<div class="am-u-sm-10 am-text-center am-u-sm-centered">
							<button type="button" id="viewSubmitBtn" class="am-btn am-btn-primary "
								>提交</button>&nbsp;&nbsp;&nbsp;
							<button type="button" id="viewCancelBtn" class="am-btn am-btn-default">返回</button>
							
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</header>
<script type="text/javascript">
	$(function(){
	});
	// 打开意见投递
	$("#view").on("click",function(){
		$("#viewContent").offCanvas('open');
	});
	// 关闭意见投递
	$("#viewCancelBtn").on("click",function(){
		$("#viewContent").offCanvas('close');
	});
	// 关闭意见投递
	$("#viewSubmitBtn").on("click",function(){
		if($('#viewForm').validator('isFormValid')){
			submitView();
		}
	});
	// 提交
	var submitView = function() {
		var formVals = {};
		formVals.consumerViewContactWay = $("#consumerViewContactWay").val();
		formVals.consumerViewContext = $("#consumerViewContext").val();
		$.ajax({
			type: "post",
			url: "${rc.contextPath}/userDo/doAddConsumerView.htm",
			data: formVals,
			traditional:true,
			success: function(msg) {
				if(msg=="01"){
					alertBox("success","意见提交成功！");
					//清空表单和关闭侧边栏
					$("#viewForm")[0].reset();
					$("#viewContent").offCanvas('close');
				}else if(msg=="02"){
					alertBox("warning","意见提交失败！");
					
				}
				
			}, error: function (XMLHttpRequest, textStatus, errorThrown) {
				console.log(errorThrown);
				alertBox("error","发生错误");
			}
		});
	
	}
	
</script>