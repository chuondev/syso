<?xml version="1.0" encoding="utf-8"?>
<urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">
<#if posts?? && posts?size gt 0 > 
<#list posts as post>
  <url>
    <loc>${options.siteUrl}/archives/${post.slug}.html</loc>
    <lastmod>${post.updateTime?iso_local}</lastmod>
    <changefreq>weekly</changefreq>
  </url>
</#list>
</#if>
</urlset>