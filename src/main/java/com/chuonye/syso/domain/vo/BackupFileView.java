package com.chuonye.syso.domain.vo;

/**
 * 备份文件基本信息，供模板使用
 * 
 * @author chuonye@foxmail.com
 */
public class BackupFileView implements Comparable<BackupFileView> {
    
    private String name;
    private Long   size;
    private Long   createTime;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getSize() {
        return size;
    }
    public void setSize(Long size) {
        this.size = size;
    }
    public Long getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
    
    @Override
    public int compareTo(BackupFileView o) {
        if (createTime > o.getCreateTime()) return -1;
        if (createTime < o.getCreateTime()) return 1;
        return 0;
    }
}
