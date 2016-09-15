package com.riggitt.utils;

import android.util.Base64;

/**
 * Created by josem on 10/09/2016.
 */
public class User {

    private String user;
    private String password;

    public User(String user, String password) {

        this.user = user;
        this.password = password;
    }

    public String getBase64UserPasswordEncodedString(){
        String s = this.user + ":" + this.password;
        String o = Base64.encodeToString(s.getBytes(),Base64.DEFAULT);
        return o;
    }
    public String getBasicBase64UserPasswordEncodedString(){
        return "Basic " + this.getBase64UserPasswordEncodedString();
    }
}
