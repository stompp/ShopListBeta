package com.riggitt.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by josem on 09/09/2016.
 */
public class Utils {

    public static String toString(InputStream stream) throws IOException {
        if(stream==null) return "";
        BufferedReader r = new BufferedReader(new InputStreamReader(stream));
        StringBuilder total = new StringBuilder();
        String line;
        while ((line = r.readLine()) != null) {
            total.append(line).append('\n');
        }
        if(r!=null) r.close();

        return total.toString();
    }
}
