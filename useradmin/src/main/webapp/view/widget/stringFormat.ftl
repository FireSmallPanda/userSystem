<#macro stringFormat
	size value
>
<#if size gt 0 && value?length gt size><!--若长度大于则分割显示-->
	<#assign brs = value?length/size />
	<#if  value?length%size gt 0 ><!--判断是否有余数-->
		<#assign brs = brs+1 />
	</#if>
	<#list 0..brs-1 as i>
		<#if i = 0 ><!--第一次-->
			${value?substring(0,size)}<br/>
		<#elseif i+2 gt brs ><!--最后一次-->
			${value?substring(size*i,value?length)}
		<#else>
			${value?substring(size*i,size*(i+1))}<br/>
		</#if>
	</#list>
<#else><!--若长度不大于则显示原值-->
	${value!''}
</#if>
</#macro>