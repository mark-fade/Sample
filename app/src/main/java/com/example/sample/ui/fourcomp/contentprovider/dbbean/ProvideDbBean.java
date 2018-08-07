package com.example.sample.ui.fourcomp.contentprovider.dbbean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/*****************************
 * @作者：chenk
 * @描述：
 ******************************/

@Entity
public class ProvideDbBean {

    @Id
    private Long id;

    private String text;

    @Generated(hash = 10512115)
    public ProvideDbBean(Long id, String text) {
        this.id = id;
        this.text = text;
    }

    @Generated(hash = 346178231)
    public ProvideDbBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
