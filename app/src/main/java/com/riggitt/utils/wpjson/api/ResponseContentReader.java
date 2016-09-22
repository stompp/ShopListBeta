package com.riggitt.utils.wpjson.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by josem on 22/09/2016.
 */
public class ResponseContentReader extends JSONObject implements Constants {



    public ResponseContentReader(String c) throws JSONException {
        super(c);
    }

    public String getByKey(String key){
        return this.optString(key);
    }

    public boolean isOK(){
        return OK.equals(this.getStatus());
    }

    public boolean isError(){
        return ERROR.equals(this.getStatus());
    }

    public boolean isKeyStringSet(String key){
        if(!this.has(key)) return false;
        if (this.optString(key).length() > 0) return true;
        return false;
    }
    public String getStatus(){
        return this.optString(STATUS);
    }
    public String getError(){
        return this.optString("error");
    }
    public String getMessage(){
        return this.optString("message");
    }

    public String debugKeys(){
        JSONArray a = this.names();
        StringBuilder sb = new StringBuilder();
        for(int n = 0 ; n < a.length() ; n++){
            if(sb.length()>0){
                sb.append("&");
            }
            sb.append(a.optString(n));

        }
        return sb.toString();
    }
}
