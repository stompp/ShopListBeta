package com.riggitt.utils.wpjson.api;

import android.util.Log;

import com.example.josem.shoplistbesta.api.Listra;
import com.riggitt.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by josem on 20/09/2016.
 */
public class Response {

    protected int responseCode;

    public int getResponseCode() {
        return this.responseCode;
    }

    protected String responseContent;

    public String getResponseContent() {
        return this.responseContent;
    }

    protected Map<String, List<String>> headers;

    public Map<String, List<String>> getResponseHeaders(){
        return this.headers;
    }

    public boolean headerExists(String header){
        if(this.headers==null) return false;
        Set<String> keys = this.headers.keySet();
        if(keys.contains(header)) return true;
        return false;
    }

    public String getResponseHeader(String header){
        if(!this.headerExists(header)) return "";
        return this.headers.get(header).toString();
    }

    public Response() {
        this.responseCode = -1;
        this.responseContent = "RESPONSE NOT SET";
        this.headers = null;
    }

    public Response(int responseCode,String responseContent){
        this.responseCode = responseCode;
        this.responseContent = responseContent;
        this.headers = null;
    }

    public static Response createErrorResponse(int responseCode,String errorMessage){
        JSONObject o = new JSONObject();

        try {
            o.put(Constants.STATUS,Constants.ERROR);
            o.put(Constants.ERROR,errorMessage);
            return new Response(responseCode,o.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }


    public Response(HttpURLConnection c){
        this();
        try {
            this.createFrom(c);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("Response","Creation from HttpURLConnection failed");
        }
    }

    protected void createFrom(HttpURLConnection c) throws IOException {

        this.headers = c.getHeaderFields();
        this.responseCode = c.getResponseCode();
        InputStream is = (this.responseCode == HttpURLConnection.HTTP_OK) ?
                c.getInputStream() :
                c.getErrorStream();
        this.responseContent = Utils.toString(is);
        if (is != null) {is.close();}
        if (c != null) {c.disconnect();}
    }


    public boolean success(){
        if (this.responseCode == HttpURLConnection.HTTP_OK) return true;
        return false;
    }
    public boolean contentSet(){
        if (this.responseContent == null) return false;
        else if (this.responseContent.length() == 0) return false;
        return true;
    }
    public String testHeaders(){
        StringBuilder sb = new StringBuilder();
        Map<String,List<String>> headers = this.getResponseHeaders();
        if(headers==null) return "NULL HEADERS";
        Set<String> keys = headers.keySet();

        for (String k : keys)
            sb.append(String.format("%s : %s \r\n",k,headers.get(k)));
//            sb.append("\r\n");

        return sb.toString();
    }
    public String test(){
        StringBuilder sb = new StringBuilder();
        sb.append("#RESPONSE TEST#");
        sb.append("\r\n");

        sb.append(String.format("\tResponse code : %d",this.getResponseCode()));
        sb.append("\r\n");
        sb.append("\r\n");
        sb.append("Headers");
        sb.append("\r\n");
        sb.append(this.testHeaders());
        sb.append("\r\n");
        sb.append("\r\n");
        sb.append("Content : ");
        sb.append("\r\n");
        sb.append(this.getResponseContent());
        sb.append("\r\n");



        sb.append("#!RESPONSE TEST#");

        return sb.toString();
    }


    public ResponseContentReader getContent() throws JSONException {
        return new ResponseContentReader(this.getResponseContent());
    }

}


