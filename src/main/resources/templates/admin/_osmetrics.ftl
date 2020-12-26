<#include "_macros.ftl">
<li class="list-group-item border-0 pl-0">
    <div class="row">
        <div class="col-3">CPU</div>
        <div class="col-9">
            <div class="progress" style="height: 5px;">
                <div class="progress-bar <@alertbg x="${osmetrics.cpuUsage}"/>" role="progressbar" style="width: ${osmetrics.cpuUsage?string.percent};"></div>
            </div>
            <div class="small">${osmetrics.cpuCount} core/${osmetrics.cpuUsage?string.percent}</div>
        </div>
    </div>
</li>

<li class="list-group-item border-0 pl-0">
    <div class="row">
        <div class="col-3">内存</div>
        <div class="col-9">
            <div class="progress" style="height: 5px;">
                <div class="progress-bar <@alertbg x="${osmetrics.memoryUsed}" y="${osmetrics.memoryTotal}"/>" role="progressbar" style="width: ${(osmetrics.memoryUsed/osmetrics.memoryTotal)?string.percent};"></div>
            </div>
            <div class="small"><@common.sizeWord size="${osmetrics.memoryUsed}"?number />/<@common.sizeWord size="${osmetrics.memoryTotal}"?number /></div>
        </div>
    </div>
</li>

<li class="list-group-item border-0 pl-0">
    <div class="row">
        <div class="col-3">交换内存</div>
        <div class="col-9">
            <div class="progress" style="height: 5px;">
                <div class="progress-bar <@alertbg x="${osmetrics.swapUsed}" y="${osmetrics.swapTotal}"/>" role="progressbar" style="width: ${(osmetrics.swapUsed/osmetrics.swapTotal)?string.percent};"></div>
            </div>
            <div class="small"><@common.sizeWord size="${osmetrics.swapUsed}"?number />/<@common.sizeWord size="${osmetrics.swapTotal}"?number /></div>
        </div>
    </div>
</li>

<li class="list-group-item border-0 pl-0">
    <div class="row">
        <div class="col-3">磁盘</div>
        <div class="col-9">
            <div class="progress" style="height: 5px;">
                <div class="progress-bar <@alertbg x="${osmetrics.diskSpaceUsed}" y="${osmetrics.diskSpaceTotal}"/>" role="progressbar" style="width: ${(osmetrics.diskSpaceUsed/osmetrics.diskSpaceTotal)?string.percent};"></div>
            </div>
            <div class="small"><@common.sizeWord size="${osmetrics.diskSpaceUsed}"?number />/<@common.sizeWord size="${osmetrics.diskSpaceTotal}"?number /></div>
        </div>
    </div>
</li>
