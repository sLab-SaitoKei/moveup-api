package jp.co.vermore.controller;

import com.google.firebase.messaging.*;
import jp.co.vermore.common.JsonObject;
import jp.co.vermore.common.JsonStatus;
import jp.co.vermore.entity.Demo;
import jp.co.vermore.form.DemoForm;
import jp.co.vermore.form.PushForm;
import jp.co.vermore.service.DemoService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xieyoujun on 2017/11/20.
 */
@Controller
public class testController {

    public final static String AUTH_KEY_FCM= "";//app key

    public final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";//


    public static void main(String[] args) {
        pushFCMNotification3();
    }

    @RequestMapping(value = "/api/push/", method = RequestMethod.GET)
    @ResponseBody
    public String getNewsList() {
        String result = pushFCMNotification3();
        return result;
    }

    public static int pushFCMNotification() {

        String AUTH_KEY_FCM= "AIzaSyAZO_Rzg0AO8x2sbwpSlwkbnAueUPQ_2_0";//
        String API_URL_FCM = "https://fcm.googleapis.com/v1/projects/myproject-b5ae1/messages:send HTTP/1.1";//google push address
        int result = 0 ;

        try {
            URL url = new URL(API_URL_FCM);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization","Bearer"+" "+AUTH_KEY_FCM);
            conn.setRequestProperty("Content-Type","application/json");//

            JSONObject json1 = new JSONObject();
            JSONObject json2 = new JSONObject();
            JSONObject info = new JSONObject();

            json2.put("token",("dVD9KgJRqoE:APA91bFL5AvXhDcGjcwY-GvGll7noE8SZYh-N0Zi1wnOz2_oEJBjal8UWUwPwm3LnfinkBWCQVl7a_hkwmj2qoAZ0IxhtGuWB-cYkha10EoO3XdoNt9cMvIRBapSMsbP0kU8RI1AkGTP"));//
            info.put("body", "This is an FCM notification message!");
            info.put("title", "FCM Message");
            json2.put("notification", info);
            json1.put("message", json2);

            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(json1.toString());
            wr.flush();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = reader.readLine())!= null) {
                System.out.println(line);
            }
            wr.close();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
            result = 1;
        }
        return result;
    }

    public static String pushFCMNotification2() {

        String AUTH_KEY_FCM= "AIzaSyAZO_Rzg0AO8x2sbwpSlwkbnAueUPQ_2_0";//
        String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";//google push address
        String result = "" ;

        try {
            URL url = new URL(API_URL_FCM);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization","key="+AUTH_KEY_FCM);
            conn.setRequestProperty("Content-Type","application/json");//

            JSONObject json = new JSONObject();

            json.put("to","dVD9KgJRqoE:APA91bFL5AvXhDcGjcwY-GvGll7noE8SZYh-N0Zi1wnOz2_oEJBjal8UWUwPwm3LnfinkBWCQVl7a_hkwmj2qoAZ0IxhtGuWB-cYkha10EoO3XdoNt9cMvIRBapSMsbP0kU8RI1AkGTP");//
            JSONObject info = new JSONObject();
            info.put("badge", "1");
            info.put("sound", "default");

//            info.put("registration_ids", new JSONArray(Arrays.asList(tokens)));
//            info.put("title","Notification Tilte");
//            info.put("body", "Hello Test notification");
//            info.put("icon", "myicon");
            json.put("data", info);

            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(json.toString());
            wr.flush();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = reader.readLine())!= null) {
                System.out.println(line);
            }
            wr.close();
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
            result = "1";
        }
        return result;
    }

    public static String pushFCMNotification3() {
        int result = 0;
        // This registration token comes from the client FCM SDKs.

        String registrationToken = "fKerKSNXsHA:APA91bF8Lw-NKNkuc372WitV48LDq8mPjND8rbyKI7jQoM9J8EUK4hUjU1cGQ3nddF2X1-IqUw3MKQWD1i-0dKTb7ojEJqZB31Hc0lMb1qg-RcgFaQ4T_eMAjfmOQ2opeXY7B4GgFpf_";

        // See documentation on defining a message payload.
        Message message = Message.builder()
                .setAndroidConfig(AndroidConfig.builder()
                        .setTtl(24*3600 * 1000) // 1 day in milliseconds
                        .setPriority(AndroidConfig.Priority.NORMAL)
                        .putData("pushType","")
                        .putData("shopType","")
                        .putData("shopUuid","")
                        .putData("contentId","")
                        .setNotification(AndroidNotification.builder()
                                .setTitle("987654321")
                                .setBody("")
                                .setIcon("")
                                .setSound("default")
                                .setColor("#f45342")
                                .build())
                        .build())
                .setToken(registrationToken)
                .build();

        // Send a message to the device corresponding to the provided
        // registration token.
        String response = null;
        try {
            response = FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
            result= 1;
        }
        // Response is a message ID string.
        System.out.println("Successfully sent message: " + response);
        return response;
    }

}
