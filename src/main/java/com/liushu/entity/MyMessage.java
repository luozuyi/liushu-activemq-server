package com.liushu.entity;

import java.io.Serializable;

public class MyMessage implements Serializable{
    private static final long serialVersionUID = 1L;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 书流id
     */
    private String bookFlowId;
    /**
     * 流书用户的id
     */
    private String bookFlowUserId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBookFlowId() {
        return bookFlowId;
    }

    public void setBookFlowId(String bookFlowId) {
        this.bookFlowId = bookFlowId;
    }

    public String getBookFlowUserId() {
        return bookFlowUserId;
    }

    public void setBookFlowUserId(String bookFlowUserId) {
        this.bookFlowUserId = bookFlowUserId;
    }
}
