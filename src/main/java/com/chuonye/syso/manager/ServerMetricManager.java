package com.chuonye.syso.manager;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.boot.actuate.metrics.MetricsEndpoint.MetricResponse;
import org.springframework.stereotype.Component;

import com.chuonye.syso.utils.HostUtil;
import com.chuonye.syso.utils.Tools;

/**
 * 获取系统监控指标
 *  
 * @author chuonye@foxmail.com
 */
@Component
public class ServerMetricManager {
    
    @Value("${app.version}")
    private String appVersion;
    
    private MetricsEndpoint metrics;
    private HealthEndpoint  health;
    
    @Autowired
    public ServerMetricManager(MetricsEndpoint metrics, HealthEndpoint health) {
        this.metrics = metrics;
        this.health = health;
    }
    
    //getMetic("jvm.memory.max")
    //getMetic("jvm.memory.max", "area:heap")
    private Double getMetic(String name, String... tags) {
        MetricResponse metric = metrics.metric(name, tags.length > 0 ? Arrays.asList(tags) : null);
        if (Objects.nonNull(metric)) {
            List<MetricsEndpoint.Sample> measurements = metric.getMeasurements();
            if (Objects.nonNull(measurements) && !measurements.isEmpty()) {
                MetricsEndpoint.Sample defaultSample = measurements.get(0);
                return defaultSample.getValue();
            }
        }
        return -1D;
    }
    
    private Double getMeticTry(String name, String... tags) {
        Double val = -1D;
        for (String tag:tags) {
            val = getMetic(name, tag);
            if (val > 0) break;
        }
        
        return val;
    }
    
    @SuppressWarnings("restriction")
    public HashMap<String, Object> loadOSMetrics() {
        HashMap<String, Object> metrics = new HashMap<>(8);
        
        metrics.put("cpuCount", getMetic("system.cpu.count").intValue());
        metrics.put("cpuUsage", getMetic("system.cpu.usage"));
        
        Health healthInfo = (Health) health.healthForPath("diskSpace");
        Long total = (Long) healthInfo.getDetails().get("total");
        Long free = (Long) healthInfo.getDetails().get("free");
        
        metrics.put("diskSpaceUsed", total-free);
        metrics.put("diskSpaceTotal", total);
        
        OperatingSystemMXBean osmxb = ManagementFactory.getOperatingSystemMXBean();
        if (osmxb instanceof com.sun.management.OperatingSystemMXBean) {
            com.sun.management.OperatingSystemMXBean nativeOsmxb  = (com.sun.management.OperatingSystemMXBean) osmxb;
            
            total = nativeOsmxb.getTotalPhysicalMemorySize();
            free = nativeOsmxb.getFreePhysicalMemorySize();
            metrics.put("memoryUsed", total-free);
            metrics.put("memoryTotal", total);
            
            
            total = nativeOsmxb.getTotalSwapSpaceSize();
            free = nativeOsmxb.getFreeSwapSpaceSize();
            metrics.put("swapUsed", total-free);
            metrics.put("swapTotal", total);
        }
        
        return metrics;
    }
    
    
    public HashMap<String, Object> loadJVMetrics() {
        HashMap<String, Object> metrics = new HashMap<>(17);
        
        metrics.put("heapMax",  getMetic("jvm.memory.max", "area:heap").longValue());
        metrics.put("heapUsed", getMetic("jvm.memory.used", "area:heap").longValue());
        
        metrics.put("nonheapMax",  getMetic("jvm.memory.max", "area:nonheap").longValue());
        metrics.put("nonheapUsed", getMetic("jvm.memory.used", "area:nonheap").longValue());
        
        // gc
        metrics.put("gcPause",  getMetic("jvm.gc.pause").intValue());
        
        // heap
        metrics.put("EdenMax",     getMeticTry("jvm.memory.max", "id:Eden Space", "id:PS Eden Space", "id:Par Eden Space").longValue());
        metrics.put("SurvivorMax", getMeticTry("jvm.memory.max", "id:Survivor Space", "id:PS Survivor Space", "id:Par Survivor Space").longValue());
        metrics.put("OldMax",      getMeticTry("jvm.memory.max", "id:Tenured Gen", "id:PS Old Gen", "id:CMS Old Gen").longValue());
        
        metrics.put("EdenUsed",     getMeticTry("jvm.memory.used", "id:Eden Space", "id:PS Eden Space", "id:Par Eden Space").longValue());
        metrics.put("SurvivorUsed", getMeticTry("jvm.memory.used", "id:Survivor Space", "id:PS Survivor Space", "id:Par Survivor Space").longValue());
        metrics.put("OldUsed",      getMeticTry("jvm.memory.used", "id:Tenured Gen", "id:PS Old Gen", "id:CMS Old Gen").longValue());
        
        //non-heap
        metrics.put("MetaspaceMax",  getMetic("jvm.memory.max", "id:Metaspace").longValue());
        metrics.put("CodeCacheMax",  getMetic("jvm.memory.max", "id:Code Cache").longValue());
        metrics.put("CompressedClassSpaceMax",  getMetic("jvm.memory.max", "id:Compressed Class Space").longValue());
        
        metrics.put("MetaspaceUsed",  getMetic("jvm.memory.used", "id:Metaspace").longValue());
        metrics.put("CodeCacheUsed",  getMetic("jvm.memory.used", "id:Code Cache").longValue());
        metrics.put("CompressedClassSpaceUsed",  getMetic("jvm.memory.used", "id:Compressed Class Space").longValue());
        
        return metrics;
    }
    
    public HashMap<String, Object> loadEnvInfo() {
        HashMap<String, Object> metrics = new HashMap<>(9);
        
        metrics.put("appVersion", appVersion);
        metrics.put("appStartTime", getMetic("process.start.time").longValue() * 1000);
        metrics.put("appUptime",  Tools.timeToDhms(getMetic("process.uptime").longValue()));
        
        metrics.put("osName", systemProps("os.name"));
        metrics.put("osArch", systemProps("os.arch"));
        metrics.put("address", HostUtil.getLocalHostAddress().toString());
        
        metrics.put("jvm", systemProps("java.vm.name") + " (" + systemProps("java.vm.version") + "，" + systemProps("java.vm.info") + ")");
        metrics.put("javaVersion", systemProps("java.version"));
        metrics.put("javaHome", systemProps("java.home"));
        metrics.put("javaTmpdir", systemProps("java.io.tmpdir"));
        
        return metrics;
    }
    
    private String systemProps(String key) {
        return System.getProperty(key);
    }
}
