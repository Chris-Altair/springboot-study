package com.study.domain;

import java.io.Serializable;

public class DataNode implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer code;
    private String msg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
