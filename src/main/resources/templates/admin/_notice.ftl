<#if notice??>
<div class="notice ${(notice.code == 0)?then('notice-error','notice-success')} fade" onclick="this.remove();">${notice.msg}</div>
</#if>