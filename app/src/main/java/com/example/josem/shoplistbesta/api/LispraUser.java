package com.example.josem.shoplistbesta.api;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by josem on 18/09/2016.
 */
public class LispraUser {


//    {
//        "status": "ok",
//            "cookie": "wplispra|1475387416|TH2BOIoE2G7doCaUXOPv9dZI1SJhYPSXnlJIvm5MimG|0e765c18efc4fd25b97f15b63a49dc258fca340d19dfa085e9a958cdb7026c96",
//            "cookie_name": "wordpress_logged_in_cb23e2cbfdb77846d64cc9f4322a52f3",
//            "user": {
//        "id": 1,
//                "username": "wplispra",
//                "nicename": "wplispra",
//                "email": "admin@riggitt.org",
//                "url": "",
//                "registered": "2016-08-02 23:50:58",
//                "displayname": "wplispra",
//                "firstname": "",
//                "lastname": "",
//                "nickname": "wplispra",
//                "description": "",
//                "capabilities": "",
//                "avatar": null
//    }
//    }
    private String cookie;
    private String cookie_name;
    private int id;
    private String username;
    private String nicename;
    private String email;
    private String registered;
    private String displayname;
    private String firstname;
    private String lastname;
    private String nickname;
    private String description;
    private String capabilities;
    private String avatar;


    public LispraUser (JSONObject r){
        if(r.has("cookie")) try {
            this.cookie = r.getString("cookie");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(r.has("cookie_name")) try {
            this.cookie_name = r.getString("cookie_name");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
