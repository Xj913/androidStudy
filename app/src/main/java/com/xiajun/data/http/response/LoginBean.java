package com.xiajun.data.http.response;


import com.xiajun.entity.UserInfo;

import java.io.Serializable;

public class LoginBean implements Serializable {
    public String token;
    public UserInfo friend;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
