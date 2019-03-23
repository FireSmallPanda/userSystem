<#macro select
	system group name value="" class="" required="" disabled="" dataAmSelected=""
>
<#assign list = dict_cache(system, group) />
<#if list?exists>
	<select <#if dataAmSelected != "">data-am-selected="${dataAmSelected}"<#else>data-am-selected="{btnWidth: '100%'}"</#if>
		<#if class != "">class="${class}"</#if>
		<#if required != "">required</#if>
		<#if disabled != "">disabled</#if>
		name="${name}" >
		<option value=""></option>
		<#list list as item><!--遍历字典内容-->
			<option value="${item.dictKey!''}" <#if value==item.dictKey >selected</#if> >
					${item.dictContentCN!''}
			</option>
		</#list>
	</select>
</#if>
</#macro>