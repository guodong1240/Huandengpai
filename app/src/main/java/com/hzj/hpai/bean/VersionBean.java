package com.hzj.hpai.bean;

/**
 * Created by zx on 2017/1/11.
 */

public class VersionBean extends BaseBean {
    private String version_download_url;
    private String version_notes;
    private String version;

    public String getVersion_download_url() {
        return version_download_url;
    }

    public void setVersion_download_url(String version_download_url) {
        this.version_download_url = version_download_url;
    }

    public String getVersion_notes() {
        return version_notes;
    }

    public void setVersion_notes(String version_notes) {
        this.version_notes = version_notes;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
