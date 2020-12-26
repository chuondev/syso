
<#-- 大小单位转换 -->
<#macro sizeWord size=0>
<#compress>
  <#if size gte 1073741824>${(size/1073741824)?string("##.#")} GB
  <#elseif size gte 1048576>${(size/1048576)?string("##.#")} MB
  <#elseif size gte 1024>${(size/1024)?string("##.#")} KB
  <#else>${size} B
  </#if>
</#compress>
</#macro>

<#-- 格式化时间 -->
<#macro timeline time=.now>
<#compress>
  <#assign ct = (.now?long-time?long)/1000>
  <#if ct gte 31104000>${(ct/31104000)?int} 年前
  <#elseif ct gte 2592000>${(ct/2592000)?int} 个月前
  <#elseif ct gte 86400>${(ct/86400)?int} 天前
  <#elseif ct gte 3600>${(ct/3600)?int} 小时前
  <#elseif ct gte 60>${(ct/60)?int} 分钟前
  <#elseif ct gt 1>${ct?int} 秒前
  <#else>刚刚
  </#if>
</#compress>
</#macro>
