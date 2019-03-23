<@override name="title">显示项目列表</@override>
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
						<td colspan="6" >
							<div class="am-g">
								<div class="am-u-sm-9">
									<form action="${rc.contextPath}/adminerDo/doSearchProject.htm" method="post" class="am-form am-form-horizontal "  id="searchProjectsFrom" >
										<div class="am-u-sm-6">
											<label for="projectsIdSearch" class="am-u-sm-4 am-form-label am-text-right">项目编号:</label>
											<div class="am-u-sm-8">
												<input type="number"  class="am-form-field am-input-sm" id="projectIdSearch" name="projectsIdSearch" value="${searchProject.projectsId!''}" />
											</div>
										</div>
										<div class="am-u-sm-6">
											<label for="projectsNameSearch" class="am-u-sm-4 am-form-label am-text-right">项目名字:</label>
											<div class="am-u-sm-8">
												<input type="text" class="am-form-field am-input-sm" id="projectsNameSearch" name="projectsNameSearch" value="${searchProject.projectsName!''}" />
											</div>
										</div>
										<div class="am-u-sm-6">
											<label for="projectsTimeSearch" class="am-u-sm-4 am-form-label am-text-right">项目时间:</label>
											<div class="am-u-sm-8">
												<@ui.select system="100001" group="101" name="projectsTimeSearch" value="${projectsTimeSearch!''}" />
											</div>
										</div>
										<div class="am-u-sm-6">
											<label for="projectsStatusSearch" class="am-u-sm-4 am-form-label am-text-right">项目状态:</label>
											<div class="am-u-sm-8">
												<@ui.select system="100001" group="100" name="projectsStatusSearch" value="${searchProject.projectsStatus!''}" />
											</div>
										
										</div>
									</form>
								</div>
								<div class="am-u-sm-3" >
									<div class="am-vertical-align am-text-left" style="height: 75px; ">
										<button type="button" id="searchProjectsBtn" class="am-btn am-btn-secondary am-vertical-align-middle am-btn-xl " style="height: 50px; width:80px; " >搜索</button>
										<button type="button" id="showProjectsBtn" class="am-btn am-btn-default am-vertical-align-middle am-btn-xl " style="height: 50px; width:120px; " >显示全部</button>
									</div>
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<th>项目编号</th>
						<th>项目名字</th>
						<th>开始时间</th>
						<th>结束时间</th>
						<th>状 态</th>
						<th>操 作</th>
					</tr>
					<#if projectList??>
						<#assign time=0 />
						<#list projectList as project>
						<#assign time=time+50*1 />
						<tr data-am-scrollspy="{animation: 'fade', delay: '${time}'}">
							<td>${project.projectsId}</td>
							<#if project.projectsStatus == 1 >
									<td>${project.projectsName}</td>
							<#else>
								<td style="color:red;">${project.projectsName}</td>
							</#if>
							<td>${project.projectsStart?string("yyyy-MM-dd")}</td>
							<td>${project.projectsEnd?string("yyyy-MM-dd")}</td>
							<td><@ui.dict system="100001" group="100" value="${project.projectsStatus}" /></td>
							<td><a href="javascript:openUpdateProjectModal('${project.projectsId}','${project.projectsName}','${project.projectsStart?string("yyyy-MM-dd")}','${project.projectsEnd?string("yyyy-MM-dd")}','${project.projectsStatus}');">修改</a></td>
						</tr>	
						</#list>
					</#if>
					<tr>
						<td colspan="6" >
							<div class="am-fl am-btn-group">
								<div class="am-btn-group">
									<button class="am-btn am-btn-fn " onclick="javascript:searchByPage('previous');" >上一页</button>
					 				<input name="" type="number" class="am-btn am-btn-default"  size="4" value=${pageNo!'1'} id="pageNo" readonly />						
									<button class="am-btn am-btn-fn " onclick="javascript:searchByPage('next');">下一页</button>
								</div>
								总共页数为:<span id="totalPage">${totalPageCount}</span>
							</div>
							<button type="button" id="delProjectBtn" class="am-btn am-btn-danger am-fr">删除</button>
							<button type="button" id="addProjectBtn" class="am-btn am-btn-success am-fr">添加</button>
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
<!--添加项目模态-->
<div class="am-modal am-modal-prompt" tabindex="-1"  id="addProjectModal">
	<div class="am-modal-dialog">
		<div class="am-modal-hd">添加项目信息</div>
		<div class="am-modal-bd">
			<form class="am-form am-form-horizontal " id="addProjectFrom" data-am-validator >
				<div class="am-g am-form-group">
					<label for="projectName" class="am-u-sm-4 am-form-label am-text-right">项目名字:</label>
					<div class="am-u-sm-8">
						<input type="text" class="am-form-field" id="projectsName" name="projectsName" placeholder="请输入项目名字" required/>
					</div>
				</div>
				<div class="am-g am-form-group">
					<label for="projectStart" class="am-u-sm-4 am-form-label am-text-right">开始日期:</label>
					<div class="am-u-sm-8">
						<div class="am-input-group am-datepicker-date" data-am-datepicker="{format: 'yyyy-mm-dd'}">
							<input type="text" class="am-form-field" placeholder="yyyy-mm-dd" id="projectsStart" name="projectsStart" readonly required>
							<span class="am-input-group-btn am-datepicker-add-on">
								<button class="am-btn am-btn-default" type="button"><span class="am-icon-calendar"></span></button>
							</span>
						</div>
					</div>
				</div>
				<div class="am-g am-form-group">
					<label for="projectEnd" class="am-u-sm-4 am-form-label am-text-right">结束日期:</label>
					<div class="am-u-sm-8">
						<div class="am-input-group am-datepicker-date" data-am-datepicker="{format: 'yyyy-mm-dd'}">
							<input type="text" class="am-form-field" placeholder="yyyy-mm-dd" id="projectsEnd" name="projectsEnd" readonly required>
							<span class="am-input-group-btn am-datepicker-add-on">
								<button class="am-btn am-btn-default" type="button"><span class="am-icon-calendar"></span></button>
							</span>
						</div>
					</div>
				</div>
				
			</form>
			<button type="button" class="am-btn am-btn-sm am-btn-default" id="cancelAddProjectBtn"  >取消</button>
			<button type="button" class="am-btn am-btn-sm am-btn-primary" id="submitAddProjectBtn"  >提交</button>
		</div>
	</div>
</div>
<!--删除项目窗口-->
<div class="am-modal am-modal-prompt" tabindex="-1"  id="delProjectModal">
	<div class="am-modal-dialog">
		<div class="am-modal-hd">删除项目信息</div>
		<div class="am-modal-bd">
			<form class="am-form am-form-horizontal " id="delProjectFrom" data-am-validator >
				<div class="am-g am-form-group">
					<label for="projectIdDel" class="am-u-sm-4 am-form-label am-text-right">项目ID:</label>
					<div class="am-u-sm-8">
						<input type="text"  class="am-form-field" id="projectIdDel" name="projectIdDel" placeholder="删除的项目ID" pattern="^\d{1,8}$" required/>
					</div>
				</div>
			</form>
			<button type="button" class="am-btn am-btn-sm am-btn-default" id="cancelDelProjectBtn"  >取消</button>
			<button type="button" class="am-btn am-btn-sm am-btn-primary" id="submitDelProjectBtn"  >提交</button>
		</div>
	</div>
</div>
<!--修改项目窗口-->
<div class="am-modal am-modal-prompt" tabindex="-1"  id="updateProjectModal">
	<div class="am-modal-dialog">
		<div class="am-modal-hd">修改项目信息</div>
		<div class="am-modal-bd">
			<form class="am-form am-form-horizontal " id="updateProjectFrom" data-am-validator >
				<div class="am-g">
					<div  class="am-u-sm-4 am-text-right"><strong>项目ID:</strong></div>
					<div id="projectsIdUpdate" class="am-u-sm-8">
						xx
					</div>
				</div>
				<div class="am-g am-form-group">
					<label for="projectName" class="am-u-sm-4 am-form-label am-text-right">项目名字:</label>
					<div class="am-u-sm-8">
						<input type="text" class="am-form-field" id="projectsNameUpdate" name="projectsNameUpdate" placeholder="请输入项目名字" required/>
					</div>
				</div>
				<div class="am-g am-form-group">
					<label for="projectStartUpdate" class="am-u-sm-4 am-form-label am-text-right">开始日期:</label>
					<div class="am-u-sm-8">
						<div class="am-input-group am-datepicker-date" data-am-datepicker="{format: 'yyyy-mm-dd'}">
							<input type="text" class="am-form-field" placeholder="yyyy-mm-dd" id="projectsStartUpdate" name="projectsStartUpdate"  readonly required>
							<span class="am-input-group-btn am-datepicker-add-on">
								<button class="am-btn am-btn-default" type="button"><span class="am-icon-calendar"></span></button>
							</span>
						</div>
					</div>
				</div>
				<div class="am-g am-form-group">
					<label for="projectEndUpdate" class="am-u-sm-4 am-form-label am-text-right">结束日期:</label>
					<div class="am-u-sm-8">
						<div class="am-input-group am-datepicker-date" data-am-datepicker="{format: 'yyyy-mm-dd'}">
							<input type="text" class="am-form-field" placeholder="yyyy-mm-dd" id="projectsEndUpdate" name="projectsEndUpdate"  readonly required>
							<span class="am-input-group-btn am-datepicker-add-on">
								<button class="am-btn am-btn-default" type="button"><span class="am-icon-calendar"></span></button>
							</span>
						</div>
					</div>
				</div>
				<div class="am-g am-form-group">
					<label for="projectStatusUpdate" class="am-u-sm-4 am-form-label am-text-right">可用状态:</label>
					<div class="am-u-sm-8">
						<@ui.select system="100001" group="100" name="projectsStatusUpdate" />
					</div>
				</div>
				
			</form>
			<button type="button" class="am-btn am-btn-sm am-btn-default" id="cancelUpdateProjectBtn"  >取消</button>
			<button type="button" class="am-btn am-btn-sm am-btn-primary" id="submitUpdateProjectBtn"  >提交</button>
		</div>
	</div>
</div>
<#include "../foot.ftl"/>
</@override> 
<@override name="javascript">
	$(function(){
		
	});
	// 正则
	if ($.AMUI && $.AMUI.validator) {
		$.AMUI.validator.patterns.num = /^[0-9.-]+$/;
	}
	// 返回上一页页面按钮
	$("#backBtn").on('click',function() {
		window.location.href="${rc.contextPath}/admin/adminLoginSuccess.htm"
	});
	// 添加项目
	// 项目信息“添加”按钮
	$("#addProjectBtn").on('click',function() {
		// 清空表单验证
		$("#addProjectFrom")[0].reset();
		$("#addProjectFrom").validator("destroy");
		$("#addProjectFrom").validator();
		// 打开模态窗口
		$('#addProjectModal').modal({
				relatedTarget: this,
		});
	
	});
	// 添加"提交"按钮
	$("#submitAddProjectBtn").on("click",function() {
		// 验证表单
		if($("#addProjectFrom").validator("isFormValid")) {
			
			var formVals = {};
			formVals.projectsName = $("#projectsName").val();
			formVals.start = $("#projectsStart").val();
			formVals.end = $("#projectsEnd").val();
			$.ajax({
				type: "post",
				url: "${rc.contextPath}/adminerDo/doAddProject.htm",
				data: formVals,
				traditional:true,
				success: function(msg) {
					if(msg=="01"){
						alertBox("error","您的登录已失效！");
					}else if(msg=="02"){
						alertBox("success","添加成功！");
						$('#addProjectModal').modal('close');
					}else if(msg=="03"){
						alertBox("warning","添加失败！");
					}else if(msg=="04"){
						alertBox("warning","结束日期不得大于开始日期!");
					}
				}, error: function (XMLHttpRequest, textStatus, errorThrown) {
					alertBox("error","发生错误！");
					console.log(errorThrown);
				}
			});
		
		}
	});
	// 取消添加用户
	$("#cancelAddProjectBtn").on("click",function() {
		$('#addProjectModal').modal('close');
	});
	// 删除
	// 项目信息“删除”按钮
	$("#delProjectBtn").on('click',function() {
		// 清空表单验证
		$("#delProjectFrom")[0].reset();
		$("#delProjectFrom").validator("destroy");
		$("#delProjectFrom").validator();
		
		// 打开模态窗口
		$('#delProjectModal').modal({
				relatedTarget: this,
		});
	
	});
	// 删除项目信息模态"取消"按钮 
	$("#cancelDelProjectBtn").on('click',function() {
		$('#delProjectModal').modal('close');
	});
	//  删除项目信息模态"提交"按钮 
	$("#submitDelProjectBtn").on('click',function() {
		if($("#delProjectFrom").validator("isFormValid")) {
			var formVals = {};
			formVals.projectsId = $("#projectIdDel").val();
			$.ajax({
				type: "post",
				url: "${rc.contextPath}/adminerDo/doDelProject.htm",
				data: formVals,
				traditional:true,
				success: function(msg) {
					if(msg=="01"){
						alertBox("error","您的登录已失效！");
					}else if(msg=="02"){
						alertBox("success","删除成功！");
						$('#delProjectModal').modal('close');
					}else if(msg=="03"){
						alertBox("warning","删除失败！");
					}
				}, error: function (XMLHttpRequest, textStatus, errorThrown) {
					alertBox("error","发生错误！");
					console.log(errorThrown);
				}
			});
		}
	});
	
	// 打开更新窗口
	var openUpdateProjectModal = function(projectsId,projectsName,projectsStart,projectsEnd,projectsStatus) {
		// 清空表单验证
		$("#updateProjectFrom")[0].reset();
		$("#updateProjectFrom").validator("destroy");
		$("#updateProjectFrom").validator();
		
		// 写入项目信息
		$("#projectsIdUpdate").html(projectsId);
		$("#projectsNameUpdate").val(projectsName);
		$("#projectsStartUpdate").datepicker('setValue', projectsStart);
		$("#projectsEndUpdate").datepicker('setValue', projectsEnd);
		$("[name=projectsStatusUpdate]").val(projectsStatus);
		$("[name=projectsStatusUpdate]").find("option[value='"+projectsStatus+"']").attr("selected",true);
		// 打开模态窗口
		$('#updateProjectModal').modal({
				relatedTarget: this,
		});
		
	}
	// 修改项目信息模态"取消"按钮 
	$("#cancelUpdateProjectBtn").on('click',function() {
		$('#updateProjectModal').modal('close');
	});
	//修改项目信息模态"提交"按钮
	$("#submitUpdateProjectBtn").on("click",function() {
		// 验证表单
		if($("#updateProjectFrom").validator("isFormValid")) {
			
			var formVals = {};
			formVals.projectsId = $("#projectsIdUpdate").html();
			formVals.projectsName = $("#projectsNameUpdate").val();
			formVals.start = $("#projectsStartUpdate").val();
			formVals.end = $("#projectsEndUpdate").val();
			formVals.projectsStatus = $("[name=projectsStatusUpdate]").val();
			$.ajax({
				type: "post",
				url: "${rc.contextPath}/adminerDo/doUpdateProject.htm",
				data: formVals,
				traditional:true,
				success: function(msg) {
					if(msg=="01"){
						alertBox("error","您的登录已失效！");
					}else if(msg=="02"){
						alertBox("success","更改成功！");
						$('#updateProjectModal').modal('close');
					}else if(msg=="03"){
						alertBox("warning","更改失败！");
					}else if(msg=="04"){
						alertBox("warning","结束日期不得大于开始日期");
					}
				}, error: function (XMLHttpRequest, textStatus, errorThrown) {
					alertBox("error","发生错误！");
					console.log(errorThrown);
				}
			});
		}
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
			window.location.href="${rc.contextPath}/adminerDo/doSearchProjectByPage.htm?pageNo="+pageNo;
		}
	}
	// "搜索"按钮点击时
	$("#searchProjectsBtn").on("click",function(){
		$("#searchProjectsFrom").submit();
	});
	// "显示全部"按钮点击时
	$("#showProjectsBtn").on("click",function(){
		window.location.href="${rc.contextPath}/adminerDo/doShowAllProject.htm";
	});
	// "下载当前表格"按钮
	$("#downloadListBtn").on("click",function(){
		var pageNo=$("#pageNo").val();
		window.open("${rc.contextPath}/adminerDo/doDownloadProjectsList.htm?pageNo="+pageNo);
	});
	
</@override>
<@extends name="../base.ftl" />


