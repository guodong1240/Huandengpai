package com.hzj.hpai.bean;

/**
 * Created by zx on 2016/12/28.
 */

//{"status":200,
// "data":{"uid":"844",
//          "name":"15210848761",
//             "mail":"_15210848761@pptology.com",
//               "signature":"",
//                  "created":"1482800710",
//                      "login":"1482826365",
//                          "status":"1",
//                              "roles":{"2":"authenticated user","6":"learning_user"},
//                                  "field_id_wx_openid":[]
//              }
//          }
public class ResultBean<T>extends BaseBean {
    public int status;
    public T data;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
