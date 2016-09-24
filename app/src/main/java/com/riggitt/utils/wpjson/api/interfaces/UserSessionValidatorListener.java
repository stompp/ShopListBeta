package com.riggitt.utils.wpjson.api.interfaces;

import com.riggitt.utils.wpjson.api.Response;

/**
 * Created by josem on 24/09/2016.
 */
public interface UserSessionValidatorListener {
    void onValidationStarted();
    void onValidationSuccess();
    void OnValidationFail(Response r);

}
