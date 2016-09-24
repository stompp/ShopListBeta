package com.riggitt.utils.wpjson.api;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by josem on 23/09/2016.
 */

public class UserSession implements Constants {
    private String cookie;
    private String cookieName;

    private UserData user;

    public String getCookie() {
        return this.cookie;
    }

    public String getCookieName() {
        return this.cookieName;
    }

    public UserData getUser() {
        return this.user;
    }


    public UserSession(JSONObject o) {

        if (o != null) {
            this.cookie = o.optString(COOKIE);
            this.cookieName = o.optString(COOKIE_NAME);
            this.user = new UserData(o.optJSONObject(USER));
        }
    }

    public UserSession(String s) throws JSONException {
        this(new JSONObject(s));
    }

    public JSONObject toJSONObject() {
        JSONObject o = new JSONObject();
        try {
            o.put(COOKIE, this.cookie);
            o.put(COOKIE_NAME, this.cookieName);
            o.put(USER, this.user.toJSONObject());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return o;
    }

    public String toString() {

        return this.toJSONObject().toString();

    }

    public void logInToApp(Application app) {
        SharedPreferences sp = app.getSharedPreferences("USER_SESSION", Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.putString(Constants.USER_SESSION_INTENT_EXTRA, this.toString());
        //asynk
        e.apply();
        //synk
//        boolean success = e.commit();
//        if (!success) {}

    }

    public void logOutFromApp(Application app) {
        SharedPreferences sp = app.getSharedPreferences("USER_SESSION", Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.remove(Constants.USER_SESSION_INTENT_EXTRA);
        //asynk
        e.apply();
        //synk
//        boolean success = e.commit();
//        if (!success) {}

    }

    public static UserSession loadUserFromSharedPreferences(Application app) {
        SharedPreferences sharedPref = app.getSharedPreferences(Constants.USER_SESSION_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        String s = sharedPref.getString(Constants.USER_SESSION_INTENT_EXTRA, "");
        UserSession user = null;
        if (s.length() > 0) {
            try {
                user = new UserSession(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return user;
    }
}
