package com.chuonye.syso.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * 系统配置
 * 
 * @author chuonye@foxmail.com
 */
@Entity
@Table(name = "so_option", uniqueConstraints = {@UniqueConstraint(columnNames="name")})
public class Option implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "name")
    private String  name;

    @Lob
    @Column(name = "value")
    private String  value = "";

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "{" + name + "=" + value + "}";
    }
}