<#macro dict
	system group value
>
<#assign list=dict_cache(system, group) />
<#if list?exists>
	<#if value?index_of(',')!=-1><!--判断是否多项翻译-->
		<#list value?split(",") as node>
			<#assign flag="1" />
			<#list list as item>
				<#if flag == "1">
					<#if node == item.dictKey ><!--若等于列表中的值则显示-->
						${item.dictContentCN!''}
						<#assign flag="" />
					</#if>
				</#if>
			</#list>
		</#list>
	<#else>
		<#list list as item>
			<#if value == item.dictKey ><!--若等于列表中的值则显示-->
				${item.dictContentCN!''}
			</#if>
		</#list>
	</#if>
	
</#if>	

</#macro>