<@override name="title">登录成功</@override>
<@override name="style">
	.am-g{ margin-top:15px;}
</@override>
<@override name="content">

<#include "../head.ftl"/>
<div class="am-g" >
	<div class="am-u-sm-7 am-u-sm-centered">
		<div class="am-panel am-panel-default">
			<div class="am-panel-bd am-text-center">
				发生错误：${msg!''}
			</div>
		</div>
	</div>
</div>		
<#include "../foot.ftl"/>
</@override> 
<@override name="javascript">
	
</@override>
<@extends name="../base.ftl" />


