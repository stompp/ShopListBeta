package com.riggitt.utils.wpjson.api;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.riggitt.utils.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by josem on 20/09/2016.
 */
public class Request {

    public static final int NOT_SET = 0;
    public static final int HTTP = 1;
    public static final int HTTPS = 2;
    public static final int GET = 3;
    public static final int POST = 4;
    public static final int POST_PARAMS_IN_BODY = 5;

    public static final int READ_TIMEOUT_DEFAULT = 10000;
    public static final int CONNECT_TIMEOUT_DEFAULT = 15000;

    protected int readTimeOut;

    public int getReadTimeOut() {
        return readTimeOut;
    }

    public void setReadTimeOut(int readTimeOut) {
        this.readTimeOut = readTimeOut;
    }

    protected int connectTimeOut;

    public int getConnectTimeOut() {
        return connectTimeOut;
    }

    public void setConnectTimeOut(int connectTimeOut) {
        this.connectTimeOut = connectTimeOut;
    }


    protected int httpMode;

    public int getHttpMode() {
        return this.httpMode;
    }

    public void setHttpMode(int httpMode) {
        this.httpMode = httpMode;
    }

    public boolean usingHTTPS() {
        return (this.httpMode == HTTPS);
    }

    public void usingHTTPS(boolean use) {
        this.httpMode = (use) ? HTTPS : HTTP;
    }


    protected int requestMode;

    public int getRequestMode() {
        return requestMode;
    }

    public boolean isGet() {
        return (this.requestMode == GET);
    }

    public boolean isPost() {
        return ((this.requestMode == POST) || (this.requestMode == POST_PARAMS_IN_BODY));
    }

    public void setGetMode() {
        this.requestMode = GET;
    }

    public void setPostMode() {
        this.requestMode = POST;
    }

    public void setPostMode(boolean paramsInBody) {
        this.requestMode = (paramsInBody) ? POST_PARAMS_IN_BODY : POST;
        if (paramsInBody) {
            this.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        }
    }

    public void postParamsInBody(boolean paramsInBody) {
        this.setPostMode(paramsInBody);
    }

    public boolean postParamsInBody() {
        return (this.requestMode == POST_PARAMS_IN_BODY) ? true : false;
    }


    public void setRequestMode(int requestMode) {
        switch (requestMode) {
            case POST:
                this.setPostMode();
                break;
            case POST_PARAMS_IN_BODY:
                this.setPostMode(true);
                break;
            default:
                this.setGetMode();
                break;
        }
    }


    protected Map<String, String> headers;

    public boolean hasRequestProperties() {
        return !this.headers.isEmpty();
    }

    public Map<String, String> getRequestProperties() {
        return this.headers;
    }

    public void setRequestProperty(Map<String, String> headers) {
        this.headers.putAll(headers);
    }

    public void setRequestProperty(String key, String value) {
        this.headers.put(key, value);
    }

    public void setRequestProperties(HttpURLConnection c) {
        for (Map.Entry<String, String> h : this.getRequestProperties().entrySet()) {
            c.setRequestProperty(h.getKey(), h.getValue());
        }
    }


    protected String url;

    public void setUrl(String url) {

        if (url.length() < 3) return;

        if (url.endsWith("/")) {
            this.url = url.substring(0, url.length() - 2);

        } else {
            this.url = url;
        }

        if (this.url.startsWith("http://")) {
            this.url.replace("http://", "");
            this.usingHTTPS(false);
        } else if (this.url.startsWith("https://")) {
            this.url.replace("https://", "");
            this.usingHTTPS(true);
        }

    }

    protected String path;

    public void setPath(String path) {

        if (path.startsWith("/")) {
            this.path = path.substring(1);
        } else {
            this.path = path;
        }

    }

    public boolean hasPath() {
        if (this.path.length() > 0) return true;
        else return false;
    }


    protected Map<String, String> params;

    public Map<String, String> getParams() {
        return this.params;
    }

    public boolean hasParams() {
        return !this.params.isEmpty();
    }


    public void setRequestParameter(Map<String, String> params) {
        this.params.putAll(params);
    }

    public void setRequestParameter(String key, String value) {
        this.params.put(key, value);
    }

    public void removeRequestParameter(String key) {
        if (this.params.containsKey(key)) {
            this.params.remove(key);
        }
    }


    public String buildEncodedParams() {
        if (this.params.isEmpty()) return "";
        return Utils.urlEncodeUTF8(this.params);
    }

    public String buildRequestURL() {
        StringBuilder sb = new StringBuilder();

        sb.append(
                String.format(
                        "%s://%s/%s",
                        this.usingHTTPS() ? "https" : "http",
                        this.url,
                        this.hasPath() ? this.path : "")
        );

        if (this.hasParams()) {
            if (!this.postParamsInBody()) {
                sb.append("?");
                sb.append(this.buildEncodedParams());
            }

        }
        return sb.toString();
    }

    protected boolean hasBody;
    protected String requestBody;

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
        this.hasBody = true;
    }

    public boolean isBodySet() {
        return (this.requestBody == null) ? false : hasBody;
    }

    protected OnRequestDoneListener listener;

    public OnRequestDoneListener getListener() {
        return listener;
    }

    public void setOnRequestDoneListener(OnRequestDoneListener listener) {
        this.listener = listener;
    }

    public void doListener() {
        if (this.listener != null) {
            this.listener.onRequestDone(this);
        }
    }

    protected Response response;


    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public boolean isResponseSet() {
        if (this.response != null) return true;
        return false;
    }


    public String test() {
        StringBuilder sb = new StringBuilder();
        sb.append("REQUEST TEST");
        sb.append("\r\n");
        sb.append(String.format("%s : %s \r\n", "httpMode", this.usingHTTPS() ? "https" : "http"));
        sb.append(String.format("%s : %s \r\n", "requestMode", this.isGet() ? "GET" : "POST"));
        sb.append(String.format("%s : %s \r\n", "postParamsInBody", this.postParamsInBody() ? "true" : "false"));

        sb.append(String.format("%s : %d \r\n", "readTimeOut", this.getReadTimeOut()));
        sb.append(String.format("%s : %d \r\n", "readTimeOut", this.getConnectTimeOut()));

        sb.append(String.format("%s : %s \r\n", "url", this.url));
        sb.append(String.format("%s : %s \r\n", "path", this.path));
        sb.append(String.format("%s : %s \r\n", "hasParams", (this.hasParams()) ? "true" : "false"));
        for (Map.Entry<String, String> e : this.params.entrySet()) {
            sb.append(String.format("PARAM %s : %s \r\n", e.getKey(), e.getValue()));
        }
        sb.append(String.format("%s : %s \r\n", "encodedParams", this.buildEncodedParams()));
        sb.append(String.format("%s : %s \r\n", "encoded url", this.buildRequestURL()));


        sb.append(String.format("%s : %s \r\n", "hasRequestProperties", (this.hasRequestProperties()) ? "true" : "false"));

        for (Map.Entry<String, String> e : this.getRequestProperties().entrySet()) {
            sb.append(String.format("HEADER %s : %s \r\n", e.getKey(), e.getValue()));
        }

        sb.append(String.format("%s : %s \r\n", "hasBody", (this.isBodySet()) ? "true" : "false"));
//        sb.append(String.format("%s : %s \r\n","hasBody",(this.isBodySet()) ? "true":"false"));
        sb.append(String.format("%s :\n\n %s \r\n", "body", this.getRequestBody()));


        return sb.toString();
    }

    public void start() {
        new Connection().execute(this);
    }

    public interface OnRequestDoneListener {
        public void onRequestDone(Request request);
    }


    public class Connection extends AsyncTask<Request, Request, Request> {

        public Request doRequest(Request request) {

            try {
                URL url = new URL(request.buildRequestURL());
                HttpURLConnection c = (HttpURLConnection) url.openConnection();
                c.setReadTimeout(request.getReadTimeOut());
                c.setConnectTimeout(request.getConnectTimeOut());

                if (!request.isGet()) {
                    c.setDoOutput(true);
                    c.setRequestMethod("POST");
//                c.setFixedLengthStreamingMode(p.getBytes().length);
                }
                request.setRequestProperties(c);

                if (!request.isGet()) {
                    PrintWriter out = new PrintWriter(c.getOutputStream());
                    if (request.postParamsInBody()) {
                        out.println(request.buildEncodedParams());
                    }
                    if (request.hasBody) {
                        out.print(request.getRequestBody());
                    }
                    out.close();
                }

                Response r = new Response(c);
                request.setResponse(r);


            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.d("ReqConn-doRequest", "Malformed url for : " + request.buildRequestURL());
            } catch (IOException e) {
                Log.d("ReqConn-doRequest", "Can't open connection to " + request.buildRequestURL());
                e.printStackTrace();
            } finally {
                return request;
            }

        }


        @Override
        protected Request doInBackground(Request... requests) {

            Request r = requests[0];
            return doRequest(r);
//            if (r.isGet()) return this.modeGet(r);
//            else if (r.isPost()) return this.modePost(r);
//            return null;
        }

//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }

        @Override
        protected void onPostExecute(Request request) {
            if (request != null) {
                request.doListener();
            }
//            super.onPostExecute(request);
//            request.


        }

//        @Override
//        protected void onProgressUpdate(Request... values) {
//            super.onProgressUpdate(values);
//        }

//        @Override
//        protected void onCancelled(Request request) {
//            super.onCancelled(request);
//        }
//
//        @Override
//        protected void onCancelled() {
//            super.onCancelled();
//        }
    }

    public Request() {
        this.readTimeOut = READ_TIMEOUT_DEFAULT;
        this.connectTimeOut = CONNECT_TIMEOUT_DEFAULT;
        this.httpMode = HTTP;
        this.requestMode = GET;
        this.url = WPJSONApi.SERVER_URL;
        this.path = "";
        this.headers = new HashMap<String, String>();
        this.params = new HashMap<String, String>();
        this.hasBody = false;
        this.requestBody = "";
        this.listener = null;
        this.response = null;

    }
}
