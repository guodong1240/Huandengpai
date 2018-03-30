package com.hzj.hpai.bean;

/**
 * @author rendy 分享的数据
 */
public class OpenInfoBean {
    private String openId;
    private String userFace;
    private String sex;
    private String name;
    private String email;
    private String phone;
    private String sourse;
    private String location;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSourse() {
        return sourse;
    }

    public void setSourse(String sourse) {
        this.sourse = sourse;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUserFace() {
        return userFace;
    }

    public void setUserFace(String userFace) {
        this.userFace = userFace;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmailOrPhone() {
        return emailOrPhone;
    }

    public void setEmailOrPhone(String emailOrPhone) {
        this.emailOrPhone = emailOrPhone;
    }

    private String emailOrPhone;

    @Override
    public String toString() {
        return "OpenInfoBean{" +
                "openId='" + openId + '\'' +
                ", userFace='" + userFace + '\'' +
                ", sex='" + sex + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", sourse='" + sourse + '\'' +
                ", location='" + location + '\'' +
                ", password='" + password + '\'' +
                ", emailOrPhone='" + emailOrPhone + '\'' +
                '}';
    }
}
