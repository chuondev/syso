<#setting locale="en_US">
<?xml version="1.0" encoding="utf-8"?>
<rss xmlns:atom="http://www.w3.org/2005/Atom" version="2.0">
  <channel>
    <title>${options.siteTitle}</title>
    <link>${options.siteUrl}</link>
    <description>${options.siteDesc}</description>
    <atom:link href="${options.siteUrl}/rss" rel="self"/>
    <language>zh-cn</language>
    
  <#if posts?? && posts?size gt 0 > 
  <#list posts as post>
    <item>
      <title><![CDATA[${post.title}]]></title>
      <link>${options.siteUrl}/archives/${post.slug}.html</link>
      <description><![CDATA[${post.summary!}]]></description>
      <pubDate>${post.createTime?string("EEE, dd MMM yyyy HH:mm:ss Z")}</pubDate>
    </item>
  </#list>
  </#if>
  
  </channel>
</rss>