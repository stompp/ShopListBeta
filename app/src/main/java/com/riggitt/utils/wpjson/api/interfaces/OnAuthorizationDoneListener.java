package com.riggitt.utils.wpjson.api.interfaces;

import com.riggitt.utils.wpjson.api.Response;
import com.riggitt.utils.wpjson.api.UserSession;

/**
 * Created by josem on 24/09/2016.
 */

public interface OnAuthorizationDoneListener {
    void onAuthorizationSuccess(UserSession user);
    void onAuthorizationFail(Response r);
}
