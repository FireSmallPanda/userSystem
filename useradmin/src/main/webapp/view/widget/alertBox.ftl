<#macro alertBox
	type
>
<#assign typeShow="" />
<#switch type>
	<#case 'error'>
		<#assign typeShow="错误" />
		<div class="am-modal am-modal-alert" style="color:red;" tabindex="-1" id="error-alertBox" >
	<#break>
	<#case 'success'>
		<#assign typeShow="成功" />
		<div class="am-modal am-modal-alert" style="color:green;" tabindex="-1" id="success-alertBox" >
	<#break>
	<#case 'warning'>
		<#assign typeShow="警告" />
		<div class="am-modal am-modal-alert" style="color:orange;" tabindex="-1" id="warning-alertBox"  >
	<#break>
	<#default>
		<#assign typeShow="信息" />
		<div class="am-modal am-modal-alert" tabindex="-1" id="msg-alertBox" >
</#switch>


	<div class="am-modal-dialog" style="border:1px solid #CCC;">
		<div class="am-modal-hd">${typeShow}</div>
		<div class="am-modal-bd">
			<span class="alertBoxText"></span>
		</div>
		<div class="am-modal-footer">
			<span class="am-modal-btn">确定</span>
		</div>
	</div>
</div>
</#macro>