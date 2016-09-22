package com.riggitt.utils.wpjson.api;

import android.os.AsyncTask;

import org.json.JSONException;

/**
 * Created by josem on 20/09/2016.
 */
public class UserController extends WPJSONApi {

    public static final String CONTROLLER_NAME = "user";

    public static final String METHOD_GENERATE_AUTH_COOKIE = "generate_auth_cookie";
    public static final String METHOD_GET_CURRENT_USERINFO = "get_currentuserinfo";
    public static final String METHOD_GET_USERINFO = "get_userinfo";
    public static final String METHOD_GET_USER_META = "get_user_meta";
    public static final String METHOD_VALIDATE_AUTH_COOKIE = "validate_auth_cookie";

    public static String getMethodPath(String method) {
        return String.format(
                "/%s/%s/%s/",
                API_BASE,
                CONTROLLER_NAME,
                method
        ).toString();
    }

    public interface OnAuthorizationDoneListener {
        void onAuthorizationSuccess(Response r);

        void onAuthorizationFail(Response r);
    }


    public static void startAuthorization(String user, String password, boolean insecure, final OnAuthorizationDoneListener listener) {

        Request r = new Request();
        r.setPath(getMethodPath(METHOD_GENERATE_AUTH_COOKIE));
        r.postParamsInBody(true);
        r.setRequestParameter("dev", "1");
        r.setRequestParameter("username", user);
        r.setRequestParameter("password", password);
        if (insecure) {
            r.setRequestParameter("insecure", "cool");
        } else {
            r.usingHTTPS(true);
        }
        r.setOnRequestDoneListener(new Request.OnRequestDoneListener() {
            @Override
            public void onRequestDone(Request request) {
                boolean success = false;
                try {
                    ResponseContentReader rcr = request.getResponse().getContent();
                    if (rcr.isOK()
                            && rcr.isKeyStringSet(ResponseContentReader.COOKIE)
                            && rcr.isKeyStringSet(ResponseContentReader.COOKIE_NAME))
                    {
                        success = true;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    if(success){
                        listener.onAuthorizationSuccess(request.getResponse());
                    }else{
                        listener.onAuthorizationFail(request.getResponse());
                    }
                }

            }
        });
        r.start();
    }


}
