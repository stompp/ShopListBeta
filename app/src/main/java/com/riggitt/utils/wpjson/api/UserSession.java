package com.riggitt.utils.wpjson.api;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by josem on 23/09/2016.
 */

public class UserSession implements Constants{
    private String cookie;
    private String cookieName;

    public String getCookie(){
        return this.cookie;
    }

    public String getCookieName() {
        return this.cookieName;
    }

    public UserSession(){
        this.cookie = "";
        this.cookieName = "";
    }

    public UserSession(JSONObject o){
        this.cookie = o.optString(COOKIE);
        this.cookieName = o.optString(COOKIE_NAME);
    }

    public String toString(){
        JSONObject o = new JSONObject();
        try {
            o.put(COOKIE,this.cookie);
            o.put(COOKIE_NAME,this.cookieName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return o.toString();

    }
}
