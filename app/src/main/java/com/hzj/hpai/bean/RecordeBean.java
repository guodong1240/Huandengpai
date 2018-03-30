package com.hzj.hpai.bean;

/**
 * Created by zx on 2017/1/5.
 */

public class RecordeBean extends BaseBean {
    private String title;
    private String node_id;
    private String image_url;
    private String voice_url;

    private String share_url;
    private String PHP;
    private String field_ppti_image_file;
    private String field_pptf_summary;



    public RecordeBean() {
    }

    public RecordeBean(String title, String node_id, String image_url, String voice_url) {
        this.title = title;
        this.node_id = node_id;
        this.image_url = image_url;
        this.voice_url = voice_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNode_id() {
        return node_id;
    }

    public void setNode_id(String node_id) {
        this.node_id = node_id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getVoice_url() {
        return voice_url;
    }

    public void setVoice_url(String voice_url) {
        this.voice_url = voice_url;
    }


    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public String getPHP() {
        return PHP;
    }

    public void setPHP(String PHP) {
        this.PHP = PHP;
    }

    public String getField_ppti_image_file() {
        return field_ppti_image_file;
    }

    public void setField_ppti_image_file(String field_ppti_image_file) {
        this.field_ppti_image_file = field_ppti_image_file;
    }

    public String getField_pptf_summary() {
        return field_pptf_summary;
    }

    public void setField_pptf_summary(String field_pptf_summary) {
        this.field_pptf_summary = field_pptf_summary;
    }

    @Override
    public String toString() {
        return "RecordeBean{" +
                "title='" + title + '\'' +
                ", node_id='" + node_id + '\'' +
                ", image_url='" + image_url + '\'' +
                ", voice_url='" + voice_url + '\'' +
                '}';
    }
}
