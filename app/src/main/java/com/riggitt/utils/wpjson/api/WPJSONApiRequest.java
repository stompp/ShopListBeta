package com.riggitt.utils.wpjson.api;

/**
 * Created by josem on 24/09/2016.
 */

public class WPJSONApiRequest extends Request {

    public final static String INSECURE = "insecure";
    public final static String COOL = "cool";
    public final static String DEV = "dev";
    public final static boolean SHOW_DEV = true;

    public void setInsecure(boolean insecure){
        if (insecure) {
            setRequestParameter(INSECURE, COOL);
            usingHTTPS(false);
        } else {
            removeRequestParameter(INSECURE);
            usingHTTPS(true);
        }
    }
    public WPJSONApiRequest(boolean insecure) {
        super();
        setInsecure(insecure);
    }

    public WPJSONApiRequest(boolean insecure,String path) {
        this(insecure);
        this.setPath(path);
    }

    public WPJSONApiRequest(String path,boolean insecure,int requestMode,boolean dev) {
       super();
        setPath(path);
        setInsecure(insecure);
        setRequestMode(requestMode);
        setDevMode(dev);
    }

    public void setDevMode(boolean dev){
        if(dev) {
            this.setRequestParameter(DEV, "1");
        }else{
                removeRequestParameter(DEV);
        }
    }
}
