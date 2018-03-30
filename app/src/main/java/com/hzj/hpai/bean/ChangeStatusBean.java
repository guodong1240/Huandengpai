package com.hzj.hpai.bean;

/**
 * Created by zx on 2017/1/4.
 */

public class ChangeStatusBean extends  BaseBean {

    private String title;
    private String summary;
    private String categoryValue;
    private String voiceStyle;

    public ChangeStatusBean() {
    }

    public ChangeStatusBean(String title, String summary, String categoryValue, String voiceStyle) {
        this.title = title;
        this.summary = summary;
        this.categoryValue = categoryValue;
        this.voiceStyle = voiceStyle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCategoryValue() {
        return categoryValue;
    }

    public void setCategoryValue(String categoryValue) {
        this.categoryValue = categoryValue;
    }

    public String getVoiceStyle() {
        return voiceStyle;
    }

    public void setVoiceStyle(String voiceStyle) {
        this.voiceStyle = voiceStyle;
    }
}
