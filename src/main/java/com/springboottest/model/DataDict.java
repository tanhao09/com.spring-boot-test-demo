package com.springboottest.model;

/**
 * Created by ymind on 2018/4/17.
 */
public class DataDict {
    private Integer id;
    private String typeName;
    private String typeCode;
    private String ddkey;
    private String ddvalue;
    private Boolean isShow;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getDdkey() {
        return ddkey;
    }

    public void setDdkey(String ddkey) {
        this.ddkey = ddkey;
    }

    public String getDdvalue() {
        return ddvalue;
    }

    public void setDdvalue(String ddvalue) {
        this.ddvalue = ddvalue;
    }

    public Boolean getShow() {
        return isShow;
    }

    public void setShow(Boolean show) {
        isShow = show;
    }
}

