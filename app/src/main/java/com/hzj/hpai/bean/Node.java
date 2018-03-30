package com.hzj.hpai.bean;

/**
 * Created by zx on 2016/12/28.
 */

public class Node extends BaseBean {
    private String nid;
    private String title;
    private String pptstatus;
    private int pptstatuskey;
    private String postdate;
    private String coverimage;
    private String filename;
    private int istransform;
    private String url;
    private String qrUrl;

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPptstatus() {
        return pptstatus;
    }

    public void setPptstatus(String pptstatus) {
        this.pptstatus = pptstatus;
    }

    public int getPptstatuskey() {
        return pptstatuskey;
    }

    public void setPptstatuskey(int pptstatuskey) {
        this.pptstatuskey = pptstatuskey;
    }

    public String getPostdate() {
        return postdate;
    }

    public void setPostdate(String postdate) {
        this.postdate = postdate;
    }

    public String getCoverimage() {
        return coverimage;
    }

    public void setCoverimage(String coverimage) {
        this.coverimage = coverimage;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getIstransform() {
        return istransform;
    }

    public void setIstransform(int istransform) {
        this.istransform = istransform;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getQrUrl() {
        return qrUrl;
    }

    public void setQrUrl(String qrUrl) {
        this.qrUrl = qrUrl;
    }

    @Override
    public String toString() {
        return "Node{" +
                "nid='" + nid + '\'' +
                ", title='" + title + '\'' +
                ", pptstatus='" + pptstatus + '\'' +
                ", pptstatuskey=" + pptstatuskey +
                ", postdate='" + postdate + '\'' +
                ", coverimage='" + coverimage + '\'' +
                ", filename='" + filename + '\'' +
                ", istransform=" + istransform +
                ", url='" + url + '\'' +
                ", qrUrl='" + qrUrl + '\'' +
                '}';
    }
}
