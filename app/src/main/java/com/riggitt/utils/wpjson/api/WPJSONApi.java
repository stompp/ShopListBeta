package com.riggitt.utils.wpjson.api;

/**
 * Created by josem on 20/09/2016.
 */
public class WPJSONApi {

    public static final String SERVER_URL = "wplispra.riggitt.org";
    public static final String API_BASE = "api";

    public static String getControllerURL(String controller){
        StringBuilder sb = new StringBuilder();
        sb.append(SERVER_URL);
        sb.append("/");
        sb.append(API_BASE);
        sb.append("/");
        sb.append(controller);
        sb.append("/");
        return sb.toString();
    }

    public static String getControllerMethodURL(String controller,String method){
        StringBuilder sb = new StringBuilder();
        sb.append(WPJSONApi.getControllerURL(controller));
        sb.append(method);
        sb.append("/");

        return sb.toString();
    }


    public interface RequestDoneListener{
        public void onRequestDone(Response response);
    }

    public static boolean startJSONAPIUSERAuthentication(String user,String password,RequestDoneListener requestDoneListener){

        return true;

    }


}
