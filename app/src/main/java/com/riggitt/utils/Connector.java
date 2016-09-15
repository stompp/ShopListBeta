package com.riggitt.utils;

import android.util.Base64;

/**
 * Created by josem on 11/09/2016.
 */
public class Connector {
    private String url;
    private String script;
    private String user;
    private String password;
    private String httpMethod;
    private ConnectorConnectionResult connectionResult;

    public ConnectorConnectionResult getConnectionResult() {
        return this.connectionResult;
    }

    public void setConnectionResult(ConnectorConnectionResult connectionResult) {
        this.connectionResult = connectionResult;
    }







    public String getHttpMethod() {
        return this.httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public ConnectorListener getListener() {
        return this.listener;
    }

    public void setListener(ConnectorListener listener) {
        this.listener = listener;
    }







    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public void setUrl(String url,String script) {
        this.url = url;
        this.setScript(script);
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    public void setUser(String user,String password) {
        this.user = user;
        this.setPassword(password);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String test() {
        StringBuilder b = new StringBuilder();
        b.append("Url : ").append(this.getFullUrl())
//                    .append("\nScript : ").append(this.getScript())
                .append("\nUser : ").append(this.getUser())
                .append("\nPassword : ").append(this.getPassword())
                .append("\nMethod : ").append(this.getHttpMethod());
        return b.toString();
    }

    public String getFullUrl() {
        StringBuilder b = new StringBuilder();
        if (!this.url.startsWith("http://")) {
            b.append("http://");
        }
        b.append(this.url);
        if (this.script.length() > 0) {
            b.append("/").append(this.script);
        }
        return b.toString();
    }

    public String getBase64UserPasswordEncodedString(){
        String s = this.user + ":" + this.password;
        String o = Base64.encodeToString(s.getBytes(),Base64.DEFAULT);
        return o;
    }
    public String getBasicBase64UserPasswordEncodedString(){
        return "Basic " + this.getBase64UserPasswordEncodedString();
    }

    public interface ConnectorListener{
        public void onConnectionSuccess(Connector connector);
        public void onConnectionError(Connector connector);
    }
    ConnectorListener listener;

    public void setConnectorListener(ConnectorListener listener){
        this.listener = listener;
    }



    public Connector() {
        this.url = "";
        this.script = "";
        this.user = "";
        this.password = "";
        this.listener = null;
        this.httpMethod = "GET";
        this.connectionResult = null;

    }

    public Connector(String url, String script, String user, String password) {
        this.url = url;
        this.script = script;
        this.user = user;
        this.password = password;
        this.listener = null;
        this.httpMethod = "GET";
        this.connectionResult = null;

    }

    public Connector(String url, String script, String user, String password,String httpMethod) {
        this.url = url;
        this.script = script;
        this.user = user;
        this.password = password;
        this.listener = null;
        this.httpMethod = httpMethod;
        this.connectionResult = null;

    }

    public void doListener(){
        if(this.listener !=null){
            this.listener.onConnectionSuccess(this);
        }
    }
    public void onConnectionSuccess(){
        if(this.listener !=null){
            this.listener.onConnectionSuccess(this);
        }
    }
    public void onConnectionError(){
        if(this.listener !=null){
            this.listener.onConnectionError(this);
        }
    }

    public boolean isReadyToConnect(){
        return (this.url.length()>0) ? true : false;
    }
}

