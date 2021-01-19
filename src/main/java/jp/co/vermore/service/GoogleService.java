package jp.co.vermore.service;

import jp.co.vermore.common.util.HttpUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Richard Jia.
 * DateTime: 2018/05/07
 * Copyright: sLab, Corp
 */

@Service
public class GoogleService {

    @Value(value = "${google.recaptcha.url}")
    private String url;

    @Value(value = "${google.recaptcha.secret}")
    private String secret;

    public boolean verifyRecaptcha(String response){
        Map<String, String> formparams = new HashMap<>();
        formparams.put("secret", secret);
        formparams.put("response", response);
        String responseStr = null;
        try {
            responseStr = HttpUtil.getInstance().doPostRetString(url, null, formparams);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(responseStr != null && responseStr.length() > 0) {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(responseStr);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                return jsonObject.getBoolean("success");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}
