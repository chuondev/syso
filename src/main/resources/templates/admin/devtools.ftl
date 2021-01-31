<#include "_macros.ftl">
<@header title="开发者工具" />
<div class="container-fluid">
        <!-- Page title -->
    <div class="row">
        <div class="col-12">
            <div class="page-title-box">
                <div class="page-title-right">
                    <ol class="breadcrumb m-0">
                        <li class="breadcrumb-item"><a href="/admin">首页</a></li>
                        <li class="breadcrumb-item">系统</li>
                        <li class="breadcrumb-item active">开发者工具</li>
                    </ol>
                </div>
                <h4 class="page-title">开发者工具</h4>
            </div>
        </div>
    </div> <!-- / .row -->

    <div class="row">

        <div class="col-12">
            <div class="card">
                <div class="card-body">
                    <div class="row">
                        <div class="col-4">
                            <div class="mb-3 fs-16 text-dark">服务器</div>
                            <ul class="list-group">
                              <#include "_osmetrics.ftl">
                            </ul>
                        </div>
                        <div class="col-8">
                            <div class="mb-3 fs-16 text-dark">JVM 虚拟机</div>
                            <div class="row">
                              <div class="col-xl-5 col-md-12">
                                <ul class="list-group list-group-flush">
                                  <li class="list-group-item">
                                    <div class="row">
                                        <div class="col-5">Heap</div>
                                        <div class="col-7"><@common.sizeWord size="${jvmetrics.heapUsed}"?number/>/<@common.sizeWord size="${jvmetrics.heapMax}"?number/></div>
                                    </div>
                                  </li>
                                  <li class="list-group-item">
                                    <div class="row">
                                        <div class="col-5">Eden Space</div>
                                        <div class="col-7"><@common.sizeWord size="${jvmetrics.EdenUsed}"?number/>/<@common.sizeWord size="${jvmetrics.EdenMax}"?number/></div>
                                    </div>
                                  </li>
                                  <li class="list-group-item">
                                    <div class="row">
                                        <div class="col-5">Survivor</div>
                                        <div class="col-7"><@common.sizeWord size="${jvmetrics.SurvivorUsed}"?number/>/<@common.sizeWord size="${jvmetrics.SurvivorMax}"?number/></div>
                                    </div>
                                  </li>
                                  <li class="list-group-item">
                                    <div class="row">
                                        <div class="col-5">Old Gen</div>
                                        <div class="col-7"><@common.sizeWord size="${jvmetrics.OldUsed}"?number/>/<@common.sizeWord size="${jvmetrics.OldMax}"?number/></div>
                                    </div>
                                  </li>
                                   <li class="list-group-item">
                                    <div class="row">
                                        <div class="col-5">GC 次数</div>
                                        <div class="col-7">${jvmetrics.gcPause}</div>
                                    </div>
                                  </li>
                                </ul>
                              </div>
                              
                              <div class="col-xl-7 col-md-12">
                                <ul class="list-group list-group-flush">
                                  <li class="list-group-item">
                                    <div class="row">
                                        <div class="col-5">Non-Heap</div>
                                        <div class="col-7"><@common.sizeWord size="${jvmetrics.nonheapUsed}"?number/>/<@common.sizeWord size="${jvmetrics.nonheapMax}"?number/></div>
                                    </div>
                                  </li>
                                  <li class="list-group-item">
                                    <div class="row">
                                        <div class="col-5">Metaspace</div>
                                        <div class="col-7"><@common.sizeWord size="${jvmetrics.MetaspaceUsed}"?number/>/<@common.sizeWord size="${jvmetrics.MetaspaceMax}"?number/></div>
                                    </div>
                                  </li>
                                  <li class="list-group-item">
                                    <div class="row">
                                        <div class="col-5">CodeCache</div>
                                        <div class="col-7"><@common.sizeWord size="${jvmetrics.CodeCacheUsed}"?number/>/<@common.sizeWord size="${jvmetrics.CodeCacheMax}"?number/></div>
                                    </div>
                                  </li>
                                  <li class="list-group-item">
                                    <div class="row">
                                        <div class="col-6">CompressedClassSpace</div>
                                        <div class="col-6"><@common.sizeWord size="${jvmetrics.CompressedClassSpaceUsed}"?number/>/<@common.sizeWord size="${jvmetrics.CompressedClassSpaceMax}"?number/></div>
                                    </div>
                                  </li>
                                </ul>
                              </div>
                            </div><!-- end .row-->
                            
                        </div>
                    </div>
                </div> <!-- end card-body-->
            </div> <!-- end card-->
        </div> <!-- end col-->


        <div class="col-12">
            <div class="card">
                <div class="card-body">
                    <div class="mb-3 fs-16 text-dark">运行环境</div>
                    <hr class="m-0">
                    <ul class="list-group list-group-flush">
                      <li class="list-group-item">
                        <div class="row">
                            <div class="col-2">应用版本</div>
                            <div class="col-10">${env.appVersion}</div>
                        </div>
                      </li>
                      <li class="list-group-item">
                        <div class="row">
                            <div class="col-2">启动时间</div>
                            <div class="col-10">${env.appStartTime?number_to_datetime}</div>
                        </div>
                      </li>
                      <li class="list-group-item">
                        <div class="row">
                            <div class="col-2">运行时间</div>
                            <div class="col-10">${env.appUptime}</div>
                        </div>
                      </li>
                      <li class="list-group-item">
                        <div class="row">
                            <div class="col-2">操作系统</div>
                            <div class="col-10">${env.osName}</div>
                        </div>
                      </li>
                      <li class="list-group-item">
                        <div class="row">
                            <div class="col-2">系统架构</div>
                            <div class="col-10">${env.osArch}</div>
                        </div>
                      </li>
                      <li class="list-group-item">
                        <div class="row">
                            <div class="col-2">服务器IP地址</div>
                            <div class="col-10">${env.address}</div>
                        </div>
                      </li>
                      <li class="list-group-item">
                        <div class="row">
                            <div class="col-2">JVM</div>
                            <div class="col-10">${env.jvm}</div>
                        </div>
                      </li>
                      <li class="list-group-item">
                        <div class="row">
                            <div class="col-2">Java</div>
                            <div class="col-10">${env.javaVersion}</div>
                        </div>
                      </li>
                      <li class="list-group-item">
                        <div class="row">
                            <div class="col-2">Java Home</div>
                            <div class="col-10">${env.javaHome}</div>
                        </div>
                      </li>
                      <li class="list-group-item">
                        <div class="row">
                            <div class="col-2">Java Tmpdir</div>
                            <div class="col-10">${env.javaTmpdir}</div>
                        </div>
                      </li>
                    </ul>
                </div> <!-- end card-body-->
            </div> <!-- end card-->
        </div> <!-- end col-->

    </div><!-- / .row -->

</div><!-- / .container-fluid -->

<@footer />