package com.hzj.hpai.bean;

/**
 * Created by zx on 2017/2/28.
 */

public class UserBean extends BaseBean {
    private String phone;
    private String sex;
    private String name;
    private String mail;

    public String getEmail() {
        return mail;
    }

    public void setEmail(String email) {
        this.mail = email;
    }
}

/**
 * {"status":200,
 * "data":{"uid":"844","
 * name":"15135196965",
 * "mail":"_15135196965@pptology.com",
 * "signature":"",
 * "created":"1457932651",
 * "login":"0",
 * "status":"1",
 * "roles":{"2":"authenticated user","6":"learning_user"},
 * "field_id_wx_openid":[]}}
 */
