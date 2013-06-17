package com.threeH.MyExhibition.entities;

import java.io.Serializable;

/**
 * 展会实体类
 */
public class Exhibition implements Serializable {
    /**
     * 展会标识(icon字段)
     */
    private String exKey;
    /**
     * 展会名称
     */
    private String name;
    /**
     * 展会日期
     */
    private String date;
    /**
     * 展会地址
     */
    private String address;
    /**
     * 主办单位
     */
    private String organizer;
    /**
     * 展会创建时间戳
     */
    private Long createdAt;

    public String getExKey() {
        return exKey;
    }

    public void setExKey(String exKey) {
        this.exKey = exKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }
}
