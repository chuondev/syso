<#list posts as post>
<li class="">
    <a class="list-post-title" href="/archives/${post.slug}.html">${post.title}</a>
    <div class="list-post-meta">
        <span>${post.createTime}</span><span>阅读 ${post.views}</span>
    </div>
</li>
</#list>
