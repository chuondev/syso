<@compress single_line = true>
<#include "_layouts.ftl">
<@header title="${post.title}"/>
<!-- Main Content-->
<div class="container-fluid syso-main">
    <div class="row">
        <div class="col-xl-9 col-lg-9">
            <div class="card">
                <div class="post-view">
                    <div class="post-view-title">
                        <h1>${post.title}</h1>
                        <ul class="nav small dot-list mt-1">
                            <li><span class="text-muted">${post.createTime}</span></li>
                            <li><span class="text-muted">阅读 ${post.views}</span></li>
                        </ul>
                    </div>
</@compress>
                    <div id="archive-content" class="syso-markdown mb-4">${post.renderedContent}</div>
<@compress single_line = true>
                    <div class="mb-3"><strong>最后更新时间：${post.updateTime}</strong></div>
                  
                    <div class="copy-text">
                        <div>本文由 <a href="https://www.chuonye.com/">小创</a> 创作，采用 <a href="https://creativecommons.org/licenses/by/4.0/" target="_blank" rel="external nofollow">知识共享署名4.0</a> 国际许可协议进行许可。</div>
                        <div>本站文章除注明转载/出处外，均为本站原创或翻译，转载请务必署名。</div>
                    </div>

                    <div class="mt-4">
                        <div class="text-muted mb-1">
                            <span>上一篇: </span><#if prevPost??><a href="/archives/${prevPost.slug}.html">${prevPost.title}</a><#else>没有了</#if>
                        </div>
                        <div class="text-muted ">
                            <span>下一篇: </span><#if nextPost??><a href="/archives/${nextPost.slug}.html">${nextPost.title}</a><#else>没有了</#if>
                        </div>
                    </div>
                </div>

            </div> <!-- end card-->
        </div> <!-- end col-->

        <div class="col-xl-3 col-lg-3">
            <div class="post-catalog">
                <div class="catalog-title">目录</div>
                <div class="catalog-body">
                    <ul class="catalog-list"></ul>
                </div>
            </div>
        </div> <!-- end col-->
        
    </div><!-- / .row -->
</div><!-- / .container-fluid -->

<@footer>
<script type="text/javascript">
    $(document).ready(function() {
        "use strict";
        function checkLocation() {
            if (window.scrollY > 90) {
                var width = $('.post-catalog').width();
                $('.post-catalog').addClass('catalog-sticky').width(width);
            } else {
                $('.post-catalog').removeClass('catalog-sticky').width("");
            }
        }
        checkLocation();
        $(window).scroll(function() {
            checkLocation();
        });

        var tocArr = [], subArr = [];
        var parent = 0, child = 0, expect = 0;
        $("h2,h3", $('#archive-content')).each(function(idx, el) {
            if (this.localName === 'h2') {
                if (expect == 1) {
                    parent += 1;
                } else if (expect == 2) {
                    tocArr[parent].c = subArr;
                    subArr = [];
                    parent += 1;
                }

                tocArr[parent] = {};
                tocArr[parent].p = this;
                expect = 1;
                child = 0;
            } else if (this.localName === 'h3') {
                subArr[child++] = this;
                expect = 2;
            }
        });
        if (subArr.length > 0) {
            tocArr[parent].c = subArr;
        }
        
        var toc = '';
        for(var i=0;i<tocArr.length;i++){
            toc += '<li class="item d1">';
            toc += '<a href="#' + tocArr[i].p.id + '">' + tocArr[i].p.innerText + '</a>';
            if (undefined != tocArr[i].c && tocArr[i].c.length > 0) {
                toc += '<ul class="sub-list">';
                for(var j=0;j<tocArr[i].c.length;j++){
                    toc += '<li class="item d2">';
                    toc += '<a href="#' + tocArr[i].c[j].id + '">' + tocArr[i].c[j].innerText + '</a>';
                    toc += '</li>';
                }
                toc += '</ul>';
            }
            toc += '</li>';
        }
        $('.catalog-list').html(toc);

        $('.catalog-list .item').click(function (e) {
            $('.catalog-list .item').removeClass('active');
            $(this).addClass('active');
            e.stopPropagation();
        });

    });
</script>
</@footer>
</@compress>