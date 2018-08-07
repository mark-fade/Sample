package com.example.sample.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

/*****************************
 * @作者：chenk
 * @描述：
 ******************************/
@Entity
public class TestDbBean {

    @Id
    private Long id;
    private String name;
    @Transient
    private int tempUsageCount; // not persisted
    @Generated(hash = 1194232184)
    public TestDbBean(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    @Generated(hash = 1705223996)
    public TestDbBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

}
