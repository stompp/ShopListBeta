package com.riggitt.utils;

/**
 * Created by josem on 11/09/2016.
 */
public class ConnectorConnectionResult {

    private int statusCode;
    private String responseContent;

    public ConnectorConnectionResult() {
        this.statusCode = 0;
        this.responseContent = "";
    }

    public ConnectorConnectionResult(String responseContent, int statusCode) {
        this.responseContent = responseContent;
        this.statusCode = statusCode;
    }

    public String getResponseContent() {
        return this.responseContent;
    }

    public void setResponseContent(String responseContent) {
        this.responseContent = responseContent;

    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;

    }
}
