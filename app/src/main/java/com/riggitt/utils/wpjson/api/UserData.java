package com.riggitt.utils.wpjson.api;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.StringBuilderPrinter;

import org.json.JSONException;
import org.json.JSONObject;

public class UserData implements Parcelable {
    public static final Creator<UserData> CREATOR = new Creator<UserData>() {
        @Override
        public UserData createFromParcel(Parcel source) {
            UserData var = new UserData();
            var.firstname = source.readString();
            var.capabilities = source.readString();
            var.registered = source.readString();
            var.description = source.readString();
            var.nicename = source.readString();
            var.avatar = source.readString();
            var.url = source.readString();
            var.lastname = source.readString();
            var.displayname = source.readString();
            var.nickname = source.readString();
            var.id = source.readString();
            var.email = source.readString();
            var.username = source.readString();
            return var;
        }

        @Override
        public UserData[] newArray(int size) {
            return new UserData[size];
        }
    };
    private String firstname;
    private String capabilities;
    private String registered;
    private String description;
    private String nicename;
    private String avatar;
    private String url;
    private String lastname;
    private String displayname;
    private String nickname;
    private String id;
    private String email;
    private String username;

    public String getFirstname() {
        return this.firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getCapabilities() {
        return this.capabilities;
    }

    public void setCapabilities(String capabilities) {
        this.capabilities = capabilities;
    }

    public String getRegistered() {
        return this.registered;
    }

    public void setRegistered(String registered) {
        this.registered = registered;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNicename() {
        return this.nicename;
    }

    public void setNicename(String nicename) {
        this.nicename = nicename;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLastname() {
        return this.lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDisplayname() {
        return this.displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.firstname);
        dest.writeString(this.capabilities);
        dest.writeString(this.registered);
        dest.writeString(this.description);
        dest.writeString(this.nicename);
        dest.writeString(this.avatar);
        dest.writeString(this.url);
        dest.writeString(this.lastname);
        dest.writeString(this.displayname);
        dest.writeString(this.nickname);
        dest.writeString(this.id);
        dest.writeString(this.email);
        dest.writeString(this.username);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void createFrom(String s) {
        try {
            this.createFrom(new JSONObject(s));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void createFrom(JSONObject o) {
        this.firstname = o.optString(Constants.FIRSTNAME);
        this.capabilities = o.optString(Constants.CAPABILITIES);
        this.registered = o.optString(Constants.REGISTERED);
        this.description = o.optString(Constants.DESCRIPTION);
        this.nicename = o.optString(Constants.NICENAME);
        this.avatar = o.optString(Constants.AVATAR);
        this.url = o.optString(Constants.URL);
        this.lastname = o.optString(Constants.LASTNAME);
        this.displayname = o.optString(Constants.DISPLAYNAME);
        this.nickname = o.optString(Constants.NICKNAME);
        this.id = o.optString(Constants.ID);
        this.email = o.optString(Constants.EMAIL);
        this.username = o.optString(Constants.USERNAME);


    }

    public JSONObject toJSONObject() {
        JSONObject o = new JSONObject();
        try {
            o.put(Constants.FIRSTNAME, this.firstname);
            o.put(Constants.CAPABILITIES, this.capabilities);
            o.put(Constants.REGISTERED, this.registered);
            o.put(Constants.DESCRIPTION, this.description);
            o.put(Constants.NICENAME, this.nicename);
            o.put(Constants.AVATAR, this.avatar);
            o.put(Constants.URL, this.url);
            o.put(Constants.LASTNAME, this.lastname);
            o.put(Constants.DISPLAYNAME, this.displayname);
            o.put(Constants.NICKNAME, this.nickname);
            o.put(Constants.ID, this.id);
            o.put(Constants.EMAIL, this.email);
            o.put(Constants.USERNAME, this.username);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return o;
    }

    public String toString() {
        return this.toJSONObject().toString();
    }

    public String test(){
        return String.format("User id is %s and display_name is %s",this.getId(),this.getDisplayname());
    }
    public UserData() {
        this.firstname = "";
        this.capabilities = "";
        this.registered = "";
        this.description = "";
        this.nicename = "";
        this.avatar = "";
        this.url = "";
        this.lastname = "";
        this.displayname = "";
        this.nickname = "";
        this.id = "";
        this.email = "";
        this.username = "";
    }

    public UserData(String jsonString){
        this();
        if(jsonString != null) {
            this.createFrom(jsonString);
        }
    }
    public UserData(JSONObject jsonObject){
        this();
        if(jsonObject != null) {
            this.createFrom(jsonObject);
        }
    }
}
