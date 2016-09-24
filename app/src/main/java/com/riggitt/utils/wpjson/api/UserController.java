package com.riggitt.utils.wpjson.api;

import android.app.Application;
import android.os.AsyncTask;

import com.riggitt.utils.Utils;
import com.riggitt.utils.wpjson.api.interfaces.OnAuthorizationDoneListener;
import com.riggitt.utils.wpjson.api.interfaces.UserSessionValidatorListener;

import org.json.JSONException;

import java.util.Map;

/**
 * Created by josem on 20/09/2016.
 */
public class UserController extends WPJSONApi implements Constants {

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
        );
    }






    public static void startAuthorization(final Application app,String user, String password, boolean insecure, final OnAuthorizationDoneListener listener) {
        WPJSONApiRequest r = new WPJSONApiRequest(
                getMethodPath(METHOD_GENERATE_AUTH_COOKIE),
                insecure,
                Request.POST_PARAMS_IN_BODY,
                true
        );

        r.setRequestParameter("username", user);
        r.setRequestParameter("password", password);

        r.setOnRequestDoneListener(new Request.OnRequestDoneListener() {
            @Override
            public void onRequestDone(Request request) {
                boolean success = false;
                try {
                    ResponseContentReader rcr = request.getResponse().getContent();
                    if (rcr.isOK()
                            && rcr.isKeyStringSet(ResponseContentReader.COOKIE)
                            && rcr.isKeyStringSet(ResponseContentReader.COOKIE_NAME)) {
                        success = true;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    if (success) {
                        try {
                            UserSession user = new UserSession(request.getResponse().getContent());
                            user.logInToApp(app);
                            listener.onAuthorizationSuccess(user);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            listener.onAuthorizationFail(request.getResponse());
                        }

                    } else {
                        listener.onAuthorizationFail(request.getResponse());
                    }
                }

            }
        });
        r.start();
    }

    public static void validateUserSession(final Application app, boolean insecure, final UserSessionValidatorListener listener) {
        final UserSession user = UserSession.loadUserFromSharedPreferences(app);
        if (user == null) {
            listener.OnValidationFail(Response.createErrorResponse(-1, "No User"));
            return;
        }
        String cookie = user.getCookie();
        if (Utils.isNullOrEmpty(cookie)) {
            user.logOutFromApp(app);
            listener.OnValidationFail(Response.createErrorResponse(-1, "Wrong user data"));
            return;
        }
//        WPJSONApiRequest r = new WPJSONApiRequest(
//                insecure,
//                getMethodPath(METHOD_VALIDATE_AUTH_COOKIE));
        WPJSONApiRequest r = new WPJSONApiRequest(
                getMethodPath(METHOD_VALIDATE_AUTH_COOKIE),
                insecure,
                Request.POST_PARAMS_IN_BODY,
                true
        );
        r.setRequestParameter("cookie", cookie);

        r.setOnRequestDoneListener(new Request.OnRequestDoneListener() {
            @Override
            public void onRequestDone(Request request) {
                boolean success = false;
                if (request.isResponseSet()) {
                    Response r = request.getResponse();
                    try {
                        ResponseContentReader rcr = r.getContent();
                        if (rcr.isOK() && rcr.has(VALID)) {
                            success = rcr.optBoolean(VALID);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    if (success) {
                        listener.onValidationSuccess();
                    } else {
                        user.logOutFromApp(app);
                        listener.OnValidationFail(r);
                    }
                }else{
                    listener.OnValidationFail(Response.createErrorResponse(-1, "No Response"));
                }


            }
        });
        r.start();
        listener.onValidationStarted();

    }


}
