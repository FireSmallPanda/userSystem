<@override name="title">登录成功</@override>
<@override name="style">
	.am-g{ margin-top:15px;}
	#headImage{cursor:pointer;}
	#uploadImageFile{ margin-top:20px; margin-bottom:0px; }
</@override>
<@override name="content">

<#include "../head.ftl"/>


<div class="am-g" >
	<div class="am-u-sm-7 am-u-sm-centered">
		<div class="am-panel am-panel-default">
			<div class="am-panel-bd am-text-center">
				<img id="headImage" class="am-img-thumbnail am-circle" src="${adminHead!'xx'}" width="100px" height="100px" >
			</div>
			<div class="am-panel-bd am-text-center">
				您好管理员！【${admininfo.userName!''}】 您的ID是:【${admininfo.userId!''}】 
				<a href="${rc.contextPath}/adminerDo/doShowAllUser.htm">【查看员工列表】</a>
				<a href="${rc.contextPath}/adminerDo/doShowAllProject.htm">【管理项目】</a>
				<a href="${rc.contextPath}/adminerDo/logOff.htm">【注销】</a>
			</div>
		</div>
	</div>
</div>
<!--图片模态-->
<div class="am-modal am-modal-prompt" tabindex="-1"  id="headImageModal">
	<div class="am-modal-dialog">
		<div class="am-modal-hd">头像信息</div>
		<div class="am-modal-bd">
			<img src="${adminHead!'xx'}" width="400px" height="400px" >
			<div class="am-form-group am-form-file" id="uploadImageFile" >
				<div id="imageName"></div>
				<button type="button" id="uploadHeadImageBtn" class="am-btn am-btn-primary am-btn-sm">
					<i class="am-icon-cloud-upload"></i> 更改头像
				</button>
				<form action="${rc.contextPath}/upload/usersImage.htm" class="am-form am-form-horizontal " id="headImageForm" method="POST" enctype="multipart/form-data" >
					<input id="usersImageFile" name="usersImageFile" type="file" multiple>
					<input type="hidden" value="adminer" name="uploadType"  readonly="readonly" />
					<input type="hidden" value="1" name="imageType"  readonly="readonly" />
				</form>
			</div>
		</div>
		<div class="am-modal-bd">
			<button type="button" class="am-btn am-btn-sm am-btn-default" id="closeHeadImageBtn"  >关闭</button>
			<button type="button" class="am-btn am-btn-sm am-btn-primary" id="submitHeadImageBtn"  >提交</button>
		</div>
	</div>
</div>

<#include "../foot.ftl"/>
</@override> 
<@override name="javascript">
	// "用户头像"点击
	$("#headImage").on('click',function() {
		// 打开模态窗口
		$('#headImageModal').modal({
			relatedTarget: this,
		});
	
	});
	
	$("#closeHeadImageBtn").on('click',function(){
		$("#headImageModal").modal('close');
	});
	
	$('#usersImageFile').on('change', function() {
		var fileNames = '';
		
		$.each(this.files, function() {
			if(this.name.indexOf(".jpg")>1||this.name.indexOf(".png")>1){// 只可上传jpg和png否则清空表单
				fileNames += '<span class="am-badge">将替换为：' + this.name + '</span> ';
			}else{
				$("#headImageForm")[0].reset();
				alertBox("warning","上传格式为JPG或PNG");
			}
		});
		$('#imageName').html(fileNames);
	});
	// 图片模态提交按钮
	$("#submitHeadImageBtn").on("click",function(){
		var fileName = $("#usersImageFile").val(); 

		if(fileName==''){// 判断图片是否上传
			alertBox("warning","请上传图片后再提交!");
		}else{
			$("#headImageForm").submit();
		}
	});
</@override>
<@extends name="../base.ftl" />


