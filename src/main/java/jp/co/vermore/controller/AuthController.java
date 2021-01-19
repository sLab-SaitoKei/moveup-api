package jp.co.vermore.controller;

import jp.co.vermore.common.Constant;
import jp.co.vermore.common.JsonObject;
import jp.co.vermore.common.JsonStatus;
import jp.co.vermore.common.mvc.APIException;
import jp.co.vermore.common.mvc.BaseController;
import jp.co.vermore.common.util.DateUtil;
import jp.co.vermore.common.util.StringUtil;
import jp.co.vermore.entity.*;
import jp.co.vermore.form.LoginForm;
import jp.co.vermore.service.*;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * AuthController
 * Created by xieyoujun.
 * <p>
 * DateTime: 2018/03/21 15:08
 * Copyright: sLab, Corp
 */

@Controller
public class AuthController extends BaseController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AWSService awsService;

    @Autowired
    private PlatformTransactionManager txManager;

    @Autowired
    private WithdrawService withdrawService;

    @Autowired
    private EntryService entryService;

    @Autowired
    private GoogleService googleService;

    @Autowired
    private PersonService personService;

    @Value(value = "${hosturl}")
    private String hosturl;

    //---- facebook
    @Value(value = "${facebook.clientId}")
    private String clientId;

    @Value(value = "${facebook.clientSecret}")
    private String clientSecret;

    private String fbAuthURL = "https://www.facebook.com/dialog/oauth";

    private String fbTokenURL = "https://graph.facebook.com/oauth/access_token";

    private String scope = "public_profile,user_friends,email,user_posts,user_likes,user_photos,user_birthday";

    private String callbackURL = "/api/auth/facebook/callback/";

    private String fbMeURL = "https://graph.facebook.com/me";

    //---- twitter
    @Value(value = "${twitter.consumerKey}")
    private String consumerKey;

    @Value(value = "${twitter.consumerSecret}")
    private String consumerSecret;

//    private String ownerID = "982432097070596096";

    private Boolean isApp = false;

    Twitter twitter;
    RequestToken requestToken;


    // ----------------------------
    // Twitter Auth
    // ----------------------------

    // Auth twitter app
    @RequestMapping(value = "/api/auth/twitter/app/", method = RequestMethod.GET)
    public String authTWApp() throws TwitterException {
        this.isApp = true;
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(consumerKey)
                .setOAuthConsumerSecret(consumerSecret);
        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();
        requestToken = twitter.getOAuthRequestToken();
        String redirectURL = requestToken.getAuthorizationURL();
        logger.debug("--------0.1--------" + redirectURL);
        return "redirect:" + redirectURL;
    }

    // Auth twitter
    @RequestMapping(value = "/api/auth/twitter/", method = RequestMethod.GET)
    public String authTW() throws TwitterException {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(consumerKey)
                .setOAuthConsumerSecret(consumerSecret);
        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();
        requestToken = twitter.getOAuthRequestToken();
        String redirectURL = requestToken.getAuthorizationURL();

        logger.debug("--------0.0--------consumerKey=" + consumerKey);
        logger.debug("--------0.0--------consumerSecret=" + consumerSecret);

        logger.debug("--------0.1--------" + redirectURL);
        return "redirect:" + redirectURL;
    }

    // Auth twitter callback (both twitter & app)
    @RequestMapping(value = "/api/auth/twitter/callback/", method = RequestMethod.GET)
    @ResponseBody
    public Object authTWCallback(
            @RequestParam(value = "oauth_token") String oauthToken,
            @RequestParam(value = "oauth_verifier") String oauthVerifier,
            HttpServletResponse response) throws TwitterException, APIException {


        String twUserId = "";
        String token = "";
        String tokenSecret = "";

        twitter4j.User twUser;

        try {

            logger.debug("--------0.2--------oauthVerifier=" + oauthVerifier);
            logger.debug("--------0.3--------oauthToken=" + oauthToken);

            AccessToken accessToken = twitter.getOAuthAccessToken(oauthVerifier);

            logger.debug("--------0.4--------accessToken=" + accessToken.toString());
            twUserId = String.valueOf(accessToken.getUserId());
            token = (accessToken.getToken()).split("-")[1];
            tokenSecret = accessToken.getTokenSecret();
            twUser = twitter.showUser(twitter.getId());

        } catch (Exception e) {
            logger.error("---------------1e" + e.toString());
            logger.error("---------------2e" + e.getMessage());
            logger.error("---------------3e" + e.getStackTrace());
            StringWriter stack = new StringWriter();
            e.printStackTrace(new PrintWriter(stack));
            logger.error("---------------e" + stack.toString());
            throw e;
        }

        logger.debug("--------1--------twUserId=" + twUserId);
        logger.debug("--------2--------token=" + token);
        logger.debug("--------3--------tokenSecret=" + tokenSecret);

        // トランザクション管理の開始
        DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
        txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(txDefinition);

        Long userId = 0l;
        String uuid = "";
        String sessionId = "";
        Boolean isProfileInit = false;

        try {
            logger.debug("--------5--------");
            // Yes login
            UserForeign userForeign = authService.getUserForeignByForeignTypeAndForeignId(Constant.USER_FOREIGN_TYPE.TWITTER, twUserId);
            if (userForeign == null) {
                logger.info("Create twitter.twUserId=" + twUserId);
                logger.info("Create twitter.token=" + token);
                logger.info("Create twitter.tokenSecret=" + tokenSecret);
                logger.debug("--------6--------");
                userId = authService.addUser();
                logger.debug("--------6.1--------");

                UserForeign userForeignNew = new UserForeign();
                userForeignNew.setUserId(userId);
                userForeignNew.setForeignType(Constant.USER_FOREIGN_TYPE.TWITTER);
                userForeignNew.setForeignId(String.valueOf(twUserId));
                userForeignNew.setPassword(Constant.EMPTY_STRING);
                userForeignNew.setAccessToken(token);
                userForeignNew.setTokenSecret(tokenSecret);
                userForeignNew.setRefreshToken(Constant.EMPTY_STRING);
                userForeignNew.setIsLogin(Constant.USER_FOREIGN_ISLOGIN.ISLOGIN);
                authService.addUserForeign(userForeignNew);
                logger.debug("--------7--------");

                uuid = authService.getUserById(userId).getUuid();
                sessionId = StringUtil.getSessionId();
                authService.insertSessionIdByUuidAndSessionId(uuid, sessionId);
                logger.debug("--------8--------");

                URL url = new URL(twUser.getOriginalProfileImageURL().toString());
                String path = "/tmp/tmp" + ".jpg";
                File file = new File(path);
                file.deleteOnExit();
                FileUtils.copyURLToFile(url, file);
                String awsTwPictureUrl = awsService.postFile(file);

                logger.debug("--------8.1--------" + twUser.getOriginalProfileImageURL().toString());
                logger.debug("--------8.2--------" + awsTwPictureUrl);

                Person person = new Person();
                person.setUserId(userId);
                person.setGender(Constant.ZERO);
                person.setCareer(Constant.CAREER.CAREER);
                person.setAddress(Constant.EMPTY_STRING);
                person.setBirthday(DateUtil.getDefaultDate());

                logger.debug("--------8.1--------twName=" + twUser.getName());
                person.setNickname(twUser.getName());
                person.setSecondName(Constant.EMPTY_STRING);
                person.setFirstNameKana(Constant.EMPTY_STRING);
                person.setSecondNameKana(Constant.EMPTY_STRING);
                person.setFirstName(Constant.EMPTY_STRING);
                awsTwPictureUrl = "https://s3-ap-northeast-1.amazonaws.com/japanmoveupwest-dev/ozbV6t27IbxW75yMFAQY";
                person.setThumbnailUrl(awsTwPictureUrl);
                person.setZipcode(Constant.EMPTY_STRING);
                person.setArea(Constant.ZERO);
                person.setMail(twUser.getEmail());
                person.setKey(Constant.EMPTY_STRING);
                person.setValue(Constant.EMPTY_STRING);
                person.setPassword(Constant.EMPTY_STRING);
                person.setCreateDatetime(new Date(System.currentTimeMillis()));
                person.setUpdateDatetime(new Date(System.currentTimeMillis()));
                authService.addPerson(person);
                isProfileInit = true;

                setLoginCookie(response, uuid, sessionId);

                logger.info("Login twitter succeed.twUserId=" + twUserId + ", uuid=" + uuid);
            } else {
                logger.debug("--------9--------");
                User user = authService.getUserById(userForeign.getUserId());
                if (user == null) throw new APIException(JsonStatus.DATA_NOT_FOUND);
                logger.debug("--------9.1--------");
                if (user.getStatus() == Constant.USER_STATUS.WITHDRAWAL ||
                        user.getStatus() == Constant.USER_STATUS.NON_EFFECT ||
                        user.getStatus() == Constant.USER_STATUS.TEMP_WITHDRAWAL) {
                    logger.debug("--------9.2--------");

                    logger.info("Create twitter(after withdraw).twUserId=" + twUserId);
                    logger.info("Create twitter(after withdraw).token=" + token);
                    logger.info("Create twitter(after withdraw).tokenSecret=" + tokenSecret);


                    userId = authService.addUser();
                    UserForeign userForeignNew = new UserForeign();
                    userForeignNew.setUserId(userId);
                    userForeignNew.setForeignType(Constant.USER_FOREIGN_TYPE.TWITTER);
                    userForeignNew.setForeignId(twUserId);
                    userForeignNew.setPassword(Constant.EMPTY_STRING);
                    userForeignNew.setAccessToken(token);
                    userForeignNew.setTokenSecret(tokenSecret);
                    userForeignNew.setRefreshToken(Constant.EMPTY_STRING);
                    userForeignNew.setIsLogin(Constant.USER_FOREIGN_ISLOGIN.ISLOGIN);
                    authService.addUserForeign(userForeignNew);
                    logger.debug("--------12--------");

                    URL url = new URL(twUser.getOriginalProfileImageURL().toString());
                    String path = "/tmp/tmp" + ".jpg";
                    File file = new File(path);
                    file.deleteOnExit();
                    FileUtils.copyURLToFile(url, file);
                    String awsTwPictureUrl = awsService.postFile(file);

                    logger.debug("--------8.1--------" + twUser.getOriginalProfileImageURL().toString());
                    logger.debug("--------8.2--------" + awsTwPictureUrl);

                    Person person = new Person();
                    person.setUserId(userId);
                    person.setGender(Constant.ZERO);
                    person.setCareer(Constant.CAREER.CAREER);
                    person.setAddress(Constant.EMPTY_STRING);
                    person.setBirthday(DateUtil.getDefaultDate());

                    logger.debug("--------8.1--------twName=" + twUser.getName());
                    person.setNickname(twUser.getName());
                    person.setSecondName(Constant.EMPTY_STRING);
                    person.setFirstNameKana(Constant.EMPTY_STRING);
                    person.setSecondNameKana(Constant.EMPTY_STRING);
                    person.setFirstName(Constant.EMPTY_STRING);
                    awsTwPictureUrl = "https://s3-ap-northeast-1.amazonaws.com/japanmoveupwest-dev/ozbV6t27IbxW75yMFAQY";
                    person.setThumbnailUrl(awsTwPictureUrl);
                    person.setZipcode(Constant.EMPTY_STRING);
                    person.setArea(Constant.ZERO);
                    person.setMail(twUser.getEmail());
                    person.setKey(Constant.EMPTY_STRING);
                    person.setValue(Constant.EMPTY_STRING);
                    person.setPassword(Constant.EMPTY_STRING);
                    person.setCreateDatetime(new Date(System.currentTimeMillis()));
                    person.setUpdateDatetime(new Date(System.currentTimeMillis()));
                    authService.addPerson(person);

                    uuid = authService.getUserById(userId).getUuid();
                    sessionId = StringUtil.getSessionId();
                    authService.insertSessionIdByUuidAndSessionId(uuid, sessionId);
                    logger.debug("--------13--------");

                    logger.info("Login twitter succeed(after withdraw).twUserId=" + twUserId + ", uuid=" + uuid);
                    setLoginCookie(response, uuid, sessionId);
                } else if (user.getStatus() == Constant.USER_STATUS.EFFECT) {

                    logger.info("Login twitter.twUserId=" + twUserId);

                    logger.debug("--------14.1--------");

                    uuid = authService.getUserById(userForeign.getUserId()).getUuid();
                    sessionId = StringUtil.getSessionId();
                    authService.insertSessionIdByUuidAndSessionId(uuid, sessionId);
                    logger.debug("--------14.2--------");


                    logger.info("Login twitter succeed.twUserId=" + twUserId + ", uuid=" + uuid);
                    setLoginCookie(response, uuid, sessionId);
                }
            }
            txManager.commit(txStatus);
            logger.info("New twitter user is regist/login. USERID=" + userId);

        } catch (Exception e) {
            txManager.rollback(txStatus);
            logger.error("Auth twitter failed, error=" + e.getMessage());
            logger.error("Auth twitter failed, error=" + e.toString());
            e.printStackTrace();
            throw new APIException(JsonStatus.UNKNOWN_ERROR);
        }

        Map<String, Object> m = new HashMap<>();
        m.put("uid", uuid);
        m.put("sid", sessionId);
        jsonObject.setResultList(m);

        logger.debug("--------15--------");
        if (this.isApp == true) {
            this.isApp = false;
            logger.info("Login twitter succeed(app).twUserId=" + twUserId + ", uuid=" + uuid);
            logger.debug("--------15.1--------");
            return jsonObject;
        } else if (isProfileInit) {
            logger.info("Login twitter succeed(app).fbUserId=" + twUserId + ", uuid=" + uuid + ", with proifile init.");
            return new ModelAndView("redirect:" + "/");
        } else {
            logger.info("Login twitter succeed(web).twUserId=" + twUserId + ", uuid=" + uuid);
            logger.debug("--------15.2--------domainName=" + hosturl);
            return new ModelAndView("redirect:" + hosturl);
        }

    }

    // ----------------------------
    // Facebook Auth
    // ----------------------------

    // Auth facebook
    @RequestMapping(value = "/api/auth/facebook/", method = RequestMethod.GET)
    public String authFB() throws Exception {
        String redircetURL = fbAuthURL + "?client_id=" + clientId + "&redirect_uri=" + StringUtil.urlEncode(hosturl + callbackURL);
        logger.debug("--------0.2--------" + redircetURL);
        return "redirect:" + redircetURL;
    }

    // Auth facebook app
    @RequestMapping(value = "/api/auth/facebook/app/", method = RequestMethod.GET)
    public String authFBApp() throws Exception {
        this.isApp = true;
        String redircetURL = fbAuthURL + "?client_id=" + clientId + "&redirect_uri=" + StringUtil.urlEncode(hosturl + callbackURL);
        logger.debug("--------0.2--------" + redircetURL);
        return "redirect:" + redircetURL;
    }

    // Auth mail facebook callback
    @RequestMapping(value = "/api/auth/facebook/callback/", method = RequestMethod.GET)
    @ResponseBody
    public Object authFBCallback(@RequestParam(value = "code") String code, HttpServletRequest hsr, HttpServletResponse response) throws Exception {

        RestTemplate rt = new RestTemplate();
        logger.debug("--------1--------" + code);

        String oAuthURL = fbTokenURL + "?client_id=" + clientId + "&redirect_uri=" + hosturl + callbackURL + "&client_secret=" + clientSecret + "&code=" + code;
        logger.debug("--------2--------" + oAuthURL);

        // get access token
        ResponseEntity<String> stringResponseEntity = rt.exchange(oAuthURL, HttpMethod.GET, null, String.class);
        JSONObject jsonObj = new JSONObject(stringResponseEntity.getBody());
        String accessToken = jsonObj.get("access_token").toString();
        logger.debug("--------3--------" + accessToken);

        // get facebook userID
        String meURL = fbMeURL + "?fields=id,name,gender,birthday,email,picture.type(large)&access_token=" + accessToken;
        ResponseEntity<String> meStringResponseEntity = rt.exchange(meURL, HttpMethod.GET, null, String.class);
        jsonObj = new JSONObject(meStringResponseEntity.getBody());
        logger.debug("--------3.1--------" + meStringResponseEntity.getBody());

        String fbUserId = jsonObj.get("id").toString();
        String fbName = jsonObj.get("name").toString();
        String fbGender = jsonObj.has("gender") ? jsonObj.get("gender").toString() : Constant.EMPTY_STRING;
        String fbMail = jsonObj.has("email") ? jsonObj.get("email").toString() : Constant.EMPTY_STRING;
        String fbPicture = jsonObj.getJSONObject("picture").getJSONObject("data").get("url").toString();

        URL url = new URL(fbPicture);
        String path = "/tmp/tmp" + ".jpg";
        File file = new File(path);
        file.deleteOnExit();
        FileUtils.copyURLToFile(url, file);
        String awsFbPictureUrl = awsService.postFile(file);

        logger.debug("--------4--------" + fbUserId);
        logger.debug("--------4.1--------" + fbPicture);
        logger.debug("--------4.2--------" + awsFbPictureUrl);

        // トランザクション管理の開始
        DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
        txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(txDefinition);

        Long userId = 0l;
        String uuid = "";
        String sessionId = "";
        Boolean isProfileInit = false;
        try {
            logger.info("--------5--------");
            // Yes login
            UserForeign userForeign = authService.getUserForeignByForeignTypeAndForeignId(Constant.USER_FOREIGN_TYPE.FACEBOOK, fbUserId);
            if (userForeign == null) {
                logger.info("Create facebook.fbUserId=" + fbUserId);
                logger.info("Create facebook.accessToken=" + accessToken);

                logger.debug("--------6--------");
                userId = authService.addUser();
                logger.debug("--------6.1--------");

                UserForeign userForeignNew = new UserForeign();
                userForeignNew.setUserId(userId);
                userForeignNew.setForeignType(Constant.USER_FOREIGN_TYPE.FACEBOOK);
                userForeignNew.setForeignId(fbUserId);
                userForeignNew.setPassword(Constant.EMPTY_STRING);
                userForeignNew.setAccessToken(accessToken);
                userForeignNew.setRefreshToken(Constant.EMPTY_STRING);
                userForeignNew.setTokenSecret(Constant.EMPTY_STRING);
                userForeignNew.setIsLogin(Constant.USER_FOREIGN_ISLOGIN.ISLOGIN);
                authService.addUserForeign(userForeignNew);
                logger.debug("--------7--------");

                uuid = authService.getUserById(userId).getUuid();
                sessionId = StringUtil.getSessionId();
                authService.insertSessionIdByUuidAndSessionId(uuid, sessionId);
                logger.debug("--------8--------");

                Person person = new Person();
                person.setUserId(userId);
                person.setGender(fbGender.equals("male") ? 1 : 2);
                person.setCareer(Constant.CAREER.CAREER);
                person.setAddress(Constant.EMPTY_STRING);
                person.setBirthday(DateUtil.getDefaultDate());
                String firstName = "";
                String secondName = "";
                String[] fbNameArray = fbName.split(Constant.SPACE);
                if (fbNameArray.length > 0) firstName = fbNameArray[0];
                if (fbNameArray.length > 1) secondName = fbNameArray[1];
                person.setFirstName(firstName);
                person.setSecondName(secondName);
                person.setFirstNameKana(Constant.EMPTY_STRING);
                person.setSecondNameKana(Constant.EMPTY_STRING);
                person.setNickname(Constant.EMPTY_STRING);
                awsFbPictureUrl = "https://s3-ap-northeast-1.amazonaws.com/japanmoveupwest-dev/ozbV6t27IbxW75yMFAQY";
                person.setThumbnailUrl(awsFbPictureUrl);
                person.setZipcode(Constant.EMPTY_STRING);
                person.setArea(Constant.ZERO);
                person.setMail(fbMail);
                person.setKey(Constant.EMPTY_STRING);
                person.setValue(Constant.EMPTY_STRING);
                person.setPassword(Constant.EMPTY_STRING);
                person.setCreateDatetime(new Date(System.currentTimeMillis()));
                person.setUpdateDatetime(new Date(System.currentTimeMillis()));
                authService.addPerson(person);
                isProfileInit = true;

                logger.info("Create facebook succeed.fbUserId=" + fbUserId + ", uuid=" + uuid);
                setLoginCookie(response, uuid, sessionId);
            } else {
                logger.debug("--------9--------");
                User user = authService.getUserById(userForeign.getUserId());
                logger.debug("--------9.1--------userId=" + user.getId());
                if (user == null) throw new APIException(JsonStatus.DATA_NOT_FOUND);
                logger.debug("--------10--------");
                if (user.getStatus() == Constant.USER_STATUS.WITHDRAWAL ||
                        user.getStatus() == Constant.USER_STATUS.NON_EFFECT ||
                        user.getStatus() == Constant.USER_STATUS.TEMP_WITHDRAWAL) {
                    logger.debug("--------11--------");
                    userId = authService.addUser();

                    UserForeign userForeignNew = new UserForeign();
                    userForeignNew.setUserId(userId);
                    userForeignNew.setForeignType(Constant.USER_FOREIGN_TYPE.FACEBOOK);
                    userForeignNew.setForeignId(fbUserId);
                    userForeignNew.setPassword(Constant.EMPTY_STRING);
                    userForeignNew.setAccessToken(accessToken);
                    userForeignNew.setRefreshToken(Constant.EMPTY_STRING);
                    userForeignNew.setTokenSecret(Constant.EMPTY_STRING);
                    userForeignNew.setIsLogin(Constant.USER_FOREIGN_ISLOGIN.ISLOGIN);
                    authService.addUserForeign(userForeignNew);
                    logger.debug("--------12--------");

                    Person person = new Person();
                    person.setUserId(userId);
                    person.setGender(fbGender.equals("male") ? 1 : 2);
                    person.setCareer(Constant.CAREER.CAREER);
                    person.setAddress(Constant.EMPTY_STRING);
                    person.setBirthday(DateUtil.getDefaultDate());
                    String firstName = "";
                    String secondName = "";
                    String[] fbNameArray = fbName.split(Constant.SPACE);
                    if (fbNameArray.length > 0) firstName = fbNameArray[0];
                    if (fbNameArray.length > 1) secondName = fbNameArray[1];
                    person.setFirstName(firstName);
                    person.setSecondName(secondName);
                    person.setFirstNameKana(Constant.EMPTY_STRING);
                    person.setSecondNameKana(Constant.EMPTY_STRING);
                    person.setNickname(Constant.EMPTY_STRING);
                    awsFbPictureUrl = "https://s3-ap-northeast-1.amazonaws.com/japanmoveupwest-dev/ozbV6t27IbxW75yMFAQY";
                    person.setThumbnailUrl(awsFbPictureUrl);
                    person.setZipcode(Constant.EMPTY_STRING);
                    person.setArea(Constant.ZERO);
                    person.setMail(fbMail);
                    person.setKey(Constant.EMPTY_STRING);
                    person.setValue(Constant.EMPTY_STRING);
                    person.setPassword(Constant.EMPTY_STRING);
                    person.setCreateDatetime(new Date(System.currentTimeMillis()));
                    person.setUpdateDatetime(new Date(System.currentTimeMillis()));
                    authService.addPerson(person);

                    uuid = authService.getUserById(userId).getUuid();
                    sessionId = StringUtil.getSessionId();
                    authService.insertSessionIdByUuidAndSessionId(uuid, sessionId);
                    logger.debug("--------13--------");

                    logger.info("Create facebook succeed(after withdraw).fbUserId=" + fbUserId + ", uuid=" + uuid);
                    setLoginCookie(response, uuid, sessionId);
                } else if (user.getStatus() == Constant.USER_STATUS.EFFECT) {
                    logger.debug("--------14--------");

                    uuid = authService.getUserById(userForeign.getUserId()).getUuid();
                    sessionId = StringUtil.getSessionId();
                    authService.insertSessionIdByUuidAndSessionId(uuid, sessionId);
                    logger.debug("--------15--------");

                    logger.info("Login facebook succeed.fbUserId=" + fbUserId + ", uuid=" + uuid);
                    setLoginCookie(response, uuid, sessionId);
                }
            }
            txManager.commit(txStatus);
            logger.info("New facebook user is regist/login. USERID=" + userId);

        } catch (Exception e) {
            txManager.rollback(txStatus);
            logger.error("Auth facebook failed, error=" + e.getMessage());
            logger.error("Auth facebook failed, error=" + e.toString());
            e.printStackTrace();
            throw new APIException(JsonStatus.UNKNOWN_ERROR);
        }

        Map<String, Object> m = new HashMap<>();
        m.put("uid", uuid);
        m.put("sid", sessionId);
        jsonObject.setResultList(m);


        if (this.isApp == true) {
            this.isApp = false;
            logger.info("Login facebook succeed(app).fbUserId=" + fbUserId + ", uuid=" + uuid);
            return jsonObject;
        } else if (isProfileInit) {
            logger.info("Login facebook succeed(app).fbUserId=" + fbUserId + ", uuid=" + uuid + ", with proifile init.");
            return new ModelAndView("redirect:" + "/");
        } else {
            logger.info("Login facebook succeed(web).fbUserId=" + fbUserId + ", uuid=" + uuid);
            return new ModelAndView("redirect:" + hosturl);
        }

    }

    // ----------------------------
    // Mail Auth
    // ----------------------------

    // Auth mail test
    @RequestMapping(value = "/api/auth/mail/sendtest/", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject authMailSendTest() throws IOException {
        awsService.sendRegistMail("support@japanmoveupwest.com", hosturl + "/mail/test/" + "1234567890");
        return jsonObject.withStatus(JsonStatus.DATA_NOT_FOUND);
    }

    // Auth mail confirm
    @RequestMapping(value = "/api/auth/mail/confirm/", method = {RequestMethod.POST, RequestMethod.OPTIONS})
    @ResponseBody
    public JsonObject authMailConfirm(@RequestBody MultiValueMap<String, String> formData, HttpServletResponse response) throws APIException, IOException {
        String mail = formData.getFirst("mail");
        String gResponse = formData.getFirst("gResponse");

        UserForeign userForeign = authService.getUserForeignByForeignTypeAndForeignId(Constant.USER_FOREIGN_TYPE.MAIL, mail);

        User user = null;
        if (userForeign != null) {
            user = authService.getUserById(userForeign.getUserId());
        }
        if (userForeign != null && user != null &&
                user.getStatus().equals(Constant.USER_STATUS.EFFECT)) {
            logger.warn("Mail data existed warn, mail=" + mail);
            return jsonObject.withStatus(JsonStatus.DATA_EXISTED);
        } else if (!googleService.verifyRecaptcha(gResponse)) {
            return jsonObject.withStatus(JsonStatus.DATA_EXISTED);
        } else {
            String token = StringUtil.getOneTimeToken();
            authService.addMailRegistOneTimeKey(token, mail);
            logger.info("Send confirm mail=" + mail);
            awsService.sendRegistMail(mail, hosturl + "/signup/" + token + "/");
            logger.info("Send confirm mail=" + mail);
            logger.info("Regist mail send to mail=" + mail);
            return jsonObject.withStatus(JsonStatus.SUCCESS);
        }
    }


    // Auth mail confirm
    @RequestMapping(value = "/api/auth/mail/regist/app/", method = {RequestMethod.POST, RequestMethod.OPTIONS})
    @ResponseBody
    public JsonObject authMailConfirmIos(@RequestBody MultiValueMap<String, String> formData, HttpServletResponse response) throws APIException, IOException {
        String mail = formData.getFirst("mail");

        UserForeign userForeign = authService.getUserForeignByForeignTypeAndForeignId(Constant.USER_FOREIGN_TYPE.MAIL, mail);
        User user = null;
        if (userForeign != null) {
            user = authService.getUserById(userForeign.getUserId());
        }
        if (userForeign != null && user != null &&
                user.getStatus().equals(Constant.USER_STATUS.EFFECT)) {
            logger.warn("Mail data existed warn, mail=" + mail);
            return jsonObject.withStatus(JsonStatus.DATA_EXISTED);
        } else {
            String token = StringUtil.getOneTimeToken();
            authService.addMailRegistOneTimeKey(token, mail);
            logger.info("Send confirm mail=" + mail);
            awsService.sendRegistMail(mail, hosturl + "/api/auth/app/signup/" + token + "/");
            logger.info("Send confirm mail=" + mail);
            logger.info("Regist mail send to mail=" + mail);
            Map<String, Object> m = new HashMap<>();
            m.put("mail", mail);
            jsonObject.setResultList(m);
            return jsonObject.withStatus(JsonStatus.SUCCESS);
        }
    }

    // Auth mail Transition
    @RequestMapping(value = "/api/auth/app/signup/{token}/", method = RequestMethod.GET)
    public String authMailTransition(Model model, @PathVariable String token) {
        LoginForm form = new LoginForm();
        String signupForApp = "jmuw:/" + "/signup/" + token + "/";
        form.setLink(signupForApp);
        model.addAttribute("form", form);
        return "blank";
    }


    // Auth mail regist
    @RequestMapping(value = "/api/auth/mail/regist/{token}", method = {RequestMethod.GET, RequestMethod.OPTIONS})
    @ResponseBody
    public JsonObject authMailRegist(@PathVariable String token) throws APIException {

        OneTimeKey oneTimeKey = authService.getOneTimeKeyByTypeAndToken(Constant.ONE_TIME_KEY.MAIL_REGIST, token);
        Date nowTime = new Date(System.currentTimeMillis());
        if (oneTimeKey != null && nowTime.getTime() > oneTimeKey.getExpireDatetime().getTime()) {
            logger.warn("Mail regist ExpireDatetime is Expired. token=" + token);
            return jsonObject.withStatus(JsonStatus.DATA_NOT_FOUND);
        } else {
            if (oneTimeKey == null) {
                logger.warn("Mail regist token not find. token=" + token);
                jsonObject.setResultList(null);
                return jsonObject.withStatus(JsonStatus.DATA_NOT_FOUND);
            } else {
                logger.info("Mail regist token accessed. token=" + token);
                Map<String, Object> m = new HashMap<>();
                m.put("mail", oneTimeKey.getMail());
                authService.delOneTimeKeyByTypeAndToken(oneTimeKey.getId());
                jsonObject.setResultList(m);
                return jsonObject.withStatus(JsonStatus.SUCCESS);
            }
        }
    }

    // Auth mail regist form
    @RequestMapping(value = "/api/auth/mail/regist/form/", method = {RequestMethod.POST, RequestMethod.OPTIONS})
    @ResponseBody
    public JsonObject authMailRegistFrom(@RequestBody MultiValueMap<String, String> formData, HttpServletResponse response) throws APIException, IOException {
        String mail = formData.getFirst("mail");
        String password = formData.getFirst("password");
        String firstName = formData.getFirst("firstName");
        String secondName = formData.getFirst("secondName");
        String firstNameKana = formData.getFirst("firstNameKana");
        String secondNameKana = formData.getFirst("secondNameKana");
        Integer gender = Integer.valueOf(formData.getFirst("gender"));
        Integer birthyear = Integer.valueOf(formData.getFirst("birthyear"));
        Integer birthmonth = Integer.valueOf(formData.getFirst("birthmonth"));
        Integer birthday = Integer.valueOf(formData.getFirst("birthday"));
        String zipcode = formData.getFirst("zipcode");
        String address = formData.getFirst("address");
        String address2 = formData.getFirst("address2");
        String address3 = formData.getFirst("address3");
        String nickname = formData.getFirst("nickname");
        Integer career = Integer.valueOf(formData.getFirst("career"));

        logger.info("Create mail user. mail=" + mail);

        Long userId = 0l;
        String uuid = "";
        String sessionId = "";

        // トランザクション管理の開始
        DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
        txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(txDefinition);

        try {
            logger.debug("--------6--------");
            userId = authService.addUser();
            logger.debug("--------6.1--------");

            UserForeign userForeignNew = new UserForeign();
            userForeignNew.setUserId(userId);
            userForeignNew.setForeignType(Constant.USER_FOREIGN_TYPE.MAIL);
            userForeignNew.setForeignId(mail);
            userForeignNew.setPassword(StringUtil.sha256(password));
            userForeignNew.setAccessToken(Constant.EMPTY_STRING);
            userForeignNew.setRefreshToken(Constant.EMPTY_STRING);
            userForeignNew.setTokenSecret(Constant.EMPTY_STRING);
            userForeignNew.setIsLogin(Constant.USER_FOREIGN_ISLOGIN.ISLOGIN);
            authService.addUserForeign(userForeignNew);
            logger.debug("--------7--------");

            uuid = authService.getUserById(userId).getUuid();
            sessionId = StringUtil.getSessionId();
            authService.insertSessionIdByUuidAndSessionId(uuid, sessionId);
            logger.debug("--------8--------");

            Person person = new Person();
            person.setUserId(userId);
            person.setGender(gender);
            person.setCareer(career);
            person.setAddress(address);
            person.setAddress2(address2);
            person.setAddress3(address3);
            person.setBirthday(DateUtil.getBrithdayDate(birthyear, birthmonth, birthday));
            person.setFirstName(firstName);
            person.setSecondName(secondName);
            person.setFirstNameKana(firstNameKana);
            person.setSecondNameKana(secondNameKana);
            person.setNickname(nickname);
            person.setThumbnailUrl(Constant.EMPTY_STRING);
            person.setZipcode(zipcode);
            person.setArea(Constant.ZERO);
            person.setMail(mail);
            person.setKey(Constant.EMPTY_STRING);
            person.setValue(Constant.EMPTY_STRING);
            person.setPassword(Constant.EMPTY_STRING);
            person.setCreateDatetime(new Date(System.currentTimeMillis()));
            person.setUpdateDatetime(new Date(System.currentTimeMillis()));
            authService.addPerson(person);

            setLoginCookie(response, uuid, sessionId);

            txManager.commit(txStatus);
            logger.info("New mail user is regist. USERID=" + userId);

        } catch (Exception e) {
            txManager.rollback(txStatus);
            logger.error("Auth mial failed, error=" + e.getMessage());
            logger.error("Auth mail failed, error=" + e.toString());
            e.printStackTrace();
            throw new APIException(JsonStatus.UNKNOWN_ERROR);
        }

        jsonObject.setResultList(null);
        return jsonObject.withStatus(JsonStatus.SUCCESS);
    }


    // Auth mail login
    @RequestMapping(value = "/api/auth/mail/login/", method = {RequestMethod.POST, RequestMethod.OPTIONS})
    @ResponseBody
    public JsonObject authMailLogin(@RequestBody MultiValueMap<String, String> formData, HttpServletResponse response) throws APIException {
        String mail = formData.getFirst("mail");
        String password = formData.getFirst("password");

        UserForeign userForeign = authService.getUserForeignByForeignTypeAndForeignIdAndPassword(Constant.USER_FOREIGN_TYPE.MAIL, mail, StringUtil.sha256(password));
        if (userForeign == null) {
            logger.warn("Login with null userForeign, clean cookie");
            setLoginCookie(response, Constant.EMPTY_STRING, Constant.EMPTY_STRING);
            throw new APIException(JsonStatus.DATA_NOT_FOUND);
        }

        User user = authService.getUserById(userForeign.getUserId());
        if (user == null ||
                user.getStatus() == Constant.USER_STATUS.WITHDRAWAL ||
                user.getStatus() == Constant.USER_STATUS.NON_EFFECT ||
                user.getStatus() == Constant.USER_STATUS.TEMP_WITHDRAWAL) {
            setLoginCookie(response, null, null);
            logger.warn("Login user with out effect status. mail=" + mail);
            throw new APIException(JsonStatus.DATA_NOT_FOUND);
        }

        String sessionId = StringUtil.getSessionId();
        authService.insertSessionIdByUuidAndSessionId(user.getUuid(), sessionId);

        logger.info("Mail user login sucess mail=" + mail + "userId=" + user.getId());
        setLoginCookie(response, user.getUuid(), sessionId);

        Map<String, Object> m = new HashMap<>();
        m.put("uid", user.getUuid());
        m.put("sid", sessionId);
        jsonObject.setResultList(m);
        return jsonObject.withStatus(JsonStatus.SUCCESS);
    }

    // Auth mail forget
    @RequestMapping(value = "/api/auth/mail/forget/", method = RequestMethod.POST)
    @ResponseBody
    public JsonObject authMailForget(@RequestBody MultiValueMap<String, String> formData) throws IOException {
        String mail = formData.getFirst("mail");
        String gResponse = formData.getFirst("gResponse");

        UserForeign userForeign = authService.getUserForeignByForeignTypeAndForeignId(Constant.USER_FOREIGN_TYPE.MAIL, mail);

        if (userForeign != null) {
            // verify google recaptcha
            if (googleService.verifyRecaptcha(gResponse)) {
                User user = authService.getUserById(userForeign.getUserId());

                if (!(user == null ||
                        user.getStatus() == Constant.USER_STATUS.WITHDRAWAL ||
                        user.getStatus() == Constant.USER_STATUS.NON_EFFECT ||
                        user.getStatus() == Constant.USER_STATUS.TEMP_WITHDRAWAL)) {
                    String token = StringUtil.getSessionId();
                    authService.addMailForgetOneTimeKey(token, mail);
                    logger.info("Send forget mail=" + mail);
                    awsService.sendForgetMail(mail, hosturl + "/forget/" + token + "/");
                    logger.info("Mail forget token is send, mail=" + mail + ", Sucess!");
                    return jsonObject.withStatus(JsonStatus.SUCCESS);
                }
            }
        } else logger.warn("Mail forget is not found, mail=" + mail);
        return jsonObject.withStatus(JsonStatus.DATA_NOT_FOUND);
    }

    // Auth mail forget for app
    @RequestMapping(value = "/api/auth/mail/forget/app/", method = RequestMethod.POST)
    @ResponseBody
    public JsonObject authMailForgetForApp(@RequestBody MultiValueMap<String, String> formData) throws IOException {
        String mail = formData.getFirst("mail");

        UserForeign userForeign = authService.getUserForeignByForeignTypeAndForeignId(Constant.USER_FOREIGN_TYPE.MAIL, mail);

        if (userForeign != null) {
            User user = authService.getUserById(userForeign.getUserId());
            if (!(user == null ||
                    user.getStatus() == Constant.USER_STATUS.WITHDRAWAL ||
                    user.getStatus() == Constant.USER_STATUS.NON_EFFECT ||
                    user.getStatus() == Constant.USER_STATUS.TEMP_WITHDRAWAL)) {
                String token = StringUtil.getSessionId();
                authService.addMailForgetOneTimeKey(token, mail);
                logger.info("app Send forget mail=" + mail);
                awsService.sendForgetMail(mail, hosturl + "/api/auth/app/mail/forget/" + token + "/");
                logger.info("app Mail forget token is send, mail=" + mail + ", Sucess!");
                return jsonObject.withStatus(JsonStatus.SUCCESS);
            }
        } else logger.warn("app Mail forget is not found, mail=" + mail);
        return jsonObject.withStatus(JsonStatus.DATA_NOT_FOUND);
    }


    // Auth mail Transition
    @RequestMapping(value = "/api/auth/app/mail/forget/{token}/", method = RequestMethod.GET)
    public String authMailForgotMail(Model model, @PathVariable String token) {
        LoginForm form = new LoginForm();
        String forgetMalForApp = "jmuw:/" + "/forget/" + token + "/";
        form.setLink(forgetMalForApp);
        model.addAttribute("form", form);
        return "blank";
    }

    // Auth mail forget reset
    @RequestMapping(value = "/api/auth/mail/forget/{token}", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject authMailForgetToken(@PathVariable String token) throws APIException {

        OneTimeKey oneTimeKey = authService.getOneTimeKeyByTypeAndToken(Constant.ONE_TIME_KEY.MAIL_FORGET, token);
        Date nowTime = new Date(System.currentTimeMillis());

        if (oneTimeKey == null) {
            logger.warn("Mail forget token not find. token=" + token);
            jsonObject.setResultList(null);
            return jsonObject.withStatus(JsonStatus.DATA_NOT_FOUND);
        } else {
            if (nowTime.getTime() > oneTimeKey.getExpireDatetime().getTime()) {
                logger.warn("Mail forget ExpireDatetime is Expired. token=" + token);
                jsonObject.setResultList(null);
                return jsonObject.withStatus(JsonStatus.DATA_NOT_FOUND);
            }else{
                logger.info("Mail forget token accessed. token=" + token);
                Map<String, Object> m = new HashMap<>();
                m.put("mail", oneTimeKey.getMail());
                authService.delOneTimeKeyByTypeAndToken(oneTimeKey.getId());
                jsonObject.setResultList(m);
                return jsonObject.withStatus(JsonStatus.SUCCESS);
            }
        }
    }

    // Auth mail reset password
    @RequestMapping(value = "/api/auth/mail/reset/", method = RequestMethod.POST)
    @ResponseBody
    public JsonObject authMailReset(@RequestBody MultiValueMap<String, String> formData) throws APIException {
        String mail = formData.getFirst("mail");
        String newPassword = formData.getFirst("new_password");

        UserForeign userForeign = authService.getUserForeignByForeignTypeAndForeignId(Constant.USER_FOREIGN_TYPE.MAIL, mail);

        if (userForeign == null) {
            logger.warn("Mail password reset is not found, mail=" + mail);
            throw new APIException(JsonStatus.DATA_NOT_FOUND);
        }

        userForeign.setPassword(StringUtil.sha256(newPassword));
        userForeign.setUpdateDatetime(DateUtil.getNowDate());
        authService.updateUserForeign(userForeign);

        logger.info("Mail password reset sucess, mail=" + mail);
        return jsonObject.withStatus(JsonStatus.SUCCESS);
    }

    // Auth logout
    @RequestMapping(value = "/api/auth/logout/", method = {RequestMethod.POST, RequestMethod.OPTIONS})
    @ResponseBody
    public JsonObject authLogout(HttpServletRequest request, HttpServletResponse response) throws APIException {
        String uuid = request.getHeader(Constant.SESSION.UUID);
        String sid = request.getHeader(Constant.SESSION.SESSIONID);

        if (sid == null || sid.isEmpty()) {
            Cookie cookies[] = request.getCookies();
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if (c.getName().equals(Constant.SESSION.SESSIONID)) sid = c.getValue();
                    if (c.getName().equals(Constant.SESSION.UUID)) uuid = c.getValue();
                }
            }
        }

        if (sid != null && uuid != null) {
            setLogoutCookie(response, uuid, sid);
        }

        jsonObject.setResultList(null);
        return jsonObject.withStatus(JsonStatus.SUCCESS);
    }

    // Auth mail reset address
    @RequestMapping(value = "/api/auth/mail/change/", method = RequestMethod.POST)
    @ResponseBody
    public JsonObject authMailChange(@RequestBody MultiValueMap<String, String> formData, HttpServletRequest request) throws IOException, APIException {
        String mail = formData.getFirst("mail");

        long userId = authService.getUserId(request);

        if (mail == null || mail.isEmpty()) {
            logger.warn("User change mail with empty mail, userId=" + userId);
            jsonObject.withStatus(JsonStatus.DATA_NOT_FOUND);
        }

        User user = authService.getUserById(userId);
        if (user == null ||
                user.getStatus() == Constant.USER_STATUS.WITHDRAWAL ||
                user.getStatus() == Constant.USER_STATUS.NON_EFFECT ||
                user.getStatus() == Constant.USER_STATUS.TEMP_WITHDRAWAL) {
            logger.warn("Mail change with invalid user status, userId=" + userId);
            return jsonObject.withStatus(JsonStatus.DATA_NOT_FOUND);
        }


        UserForeign userForeign = authService.getUserForeignByForeignTypeAndForeignId(Constant.USER_FOREIGN_TYPE.MAIL, mail);
        if (userForeign != null) {
            user = authService.getUserById(userId);
            if (!(user == null ||
                    user.getStatus() == Constant.USER_STATUS.WITHDRAWAL ||
                    user.getStatus() == Constant.USER_STATUS.NON_EFFECT ||
                    user.getStatus() == Constant.USER_STATUS.TEMP_WITHDRAWAL)) {
                logger.warn("Mail change with exist mail in user foreign, mail=" + mail);
                return jsonObject.withStatus(JsonStatus.DATA_EXISTED);
            }
        }

        Person person = personService.getPersonByMail(mail);
        if (person != null) {
            user = authService.getUserById(person.getUserId());
            if (!(user == null ||
                    user.getStatus() == Constant.USER_STATUS.WITHDRAWAL ||
                    user.getStatus() == Constant.USER_STATUS.NON_EFFECT ||
                    user.getStatus() == Constant.USER_STATUS.TEMP_WITHDRAWAL)) {
                logger.warn("Mail change with exist mail in person, mail=" + mail);
                return jsonObject.withStatus(JsonStatus.DATA_EXISTED);
            }
        }

        String token = StringUtil.getSessionId();
        authService.addMailForgetOneTimeKey(token, mail);
        logger.info("Send change mail=" + mail);
        awsService.sendChangeMail(mail, hosturl + "/changeMail/" + token + "/");
        logger.info("Mail change token is send, mail=" + mail + ", Sucess!");
        return jsonObject.withStatus(JsonStatus.SUCCESS);
    }


    // Auth mail reset address
    @RequestMapping(value = "/api/auth/mail/change/app/", method = RequestMethod.POST)
    @ResponseBody
    public JsonObject authMailChangeForApp(@RequestBody MultiValueMap<String, String> formData, HttpServletRequest request) throws IOException, APIException {
        String mail = formData.getFirst("mail");

        long userId = authService.getUserId(request);

        if (mail == null || mail.isEmpty()) {
            logger.warn("User change mail with empty mail, userId=" + userId);
            jsonObject.withStatus(JsonStatus.DATA_NOT_FOUND);
        }

        User user = authService.getUserById(userId);
        if (user == null ||
                user.getStatus() == Constant.USER_STATUS.WITHDRAWAL ||
                user.getStatus() == Constant.USER_STATUS.NON_EFFECT ||
                user.getStatus() == Constant.USER_STATUS.TEMP_WITHDRAWAL) {
            logger.warn("Mail change with invalid user status, userId=" + userId);
            return jsonObject.withStatus(JsonStatus.DATA_NOT_FOUND);
        }


        UserForeign userForeign = authService.getUserForeignByForeignTypeAndForeignId(Constant.USER_FOREIGN_TYPE.MAIL, mail);
        if (userForeign != null) {
            user = authService.getUserById(userId);
            if (!(user == null ||
                    user.getStatus() == Constant.USER_STATUS.WITHDRAWAL ||
                    user.getStatus() == Constant.USER_STATUS.NON_EFFECT ||
                    user.getStatus() == Constant.USER_STATUS.TEMP_WITHDRAWAL)) {
                logger.warn("Mail change with exist mail in user foreign, mail=" + mail);
                return jsonObject.withStatus(JsonStatus.DATA_EXISTED);
            }
        }

        Person person = personService.getPersonByMail(mail);
        if (person != null) {
            user = authService.getUserById(person.getUserId());
            if (!(user == null ||
                    user.getStatus() == Constant.USER_STATUS.WITHDRAWAL ||
                    user.getStatus() == Constant.USER_STATUS.NON_EFFECT ||
                    user.getStatus() == Constant.USER_STATUS.TEMP_WITHDRAWAL)) {
                logger.warn("Mail change with exist mail in person, mail=" + mail);
                return jsonObject.withStatus(JsonStatus.DATA_EXISTED);
            }
        }

        String token = StringUtil.getSessionId();
        authService.addMailForgetOneTimeKey(token, mail);
        logger.info("Send change mail=" + mail);
        awsService.sendChangeMail(mail, hosturl + "/api/changeMail/" + token + "/");
        logger.info("Mail change token is send, mail=" + mail + ", Sucess!");
        return jsonObject.withStatus(JsonStatus.SUCCESS);
    }

    // Auth mail Transition
    @RequestMapping(value = "/api/changeMail/{token}/", method = RequestMethod.GET)
    public String changeMailConfirm(Model model, @PathVariable String token) {
        LoginForm form = new LoginForm();
        String changeMailForApp = "jmuw:/" + "/changeMail/" + token + "/";
        form.setLink(changeMailForApp);
        model.addAttribute("form", form);
        return "blank";
    }


    // Auth mail change
    @RequestMapping(value = "/api/auth/mail/change/{token}", method = RequestMethod.GET)
    @ResponseBody
    public JsonObject authMailChangeToken(@PathVariable String token, HttpServletRequest request) throws APIException {

        long userId = authService.getUserId(request);

        OneTimeKey oneTimeKey = authService.getOneTimeKeyByTypeAndToken(Constant.ONE_TIME_KEY.MAIL_FORGET, token);
        Date nowTime = new Date(System.currentTimeMillis());

        if (oneTimeKey == null) {
            logger.warn("Mail change token not find. token=" + token);
            return jsonObject.withStatus(JsonStatus.DATA_NOT_FOUND);
        } else {
            if (oneTimeKey != null && nowTime.getTime() > oneTimeKey.getExpireDatetime().getTime()) {
                logger.warn("Mail change ExpireDatetime is Expired. token=" + token);
                return jsonObject.withStatus(JsonStatus.DATA_NOT_FOUND);
            }else{
                logger.info("Mail change token accessed. token=" + token);

                User user = authService.getUserById(userId);
                if (user == null ||
                        user.getStatus() == Constant.USER_STATUS.WITHDRAWAL ||
                        user.getStatus() == Constant.USER_STATUS.NON_EFFECT ||
                        user.getStatus() == Constant.USER_STATUS.TEMP_WITHDRAWAL) {
                    logger.warn("User change mail with invalid status, userId=" + userId);
                    return jsonObject.withStatus(JsonStatus.DATA_NOT_FOUND);
                }

                UserForeign userForeign = authService.getUserForeignByUserId(userId);
                if (userForeign == null) {
                    logger.warn("User change mail with user foreign not found, userId=" + userId);
                    return jsonObject.withStatus(JsonStatus.DATA_NOT_FOUND);
                }

                int userForeignType = userForeign.getForeignType();
                if (userForeignType == Constant.USER_FOREIGN_TYPE.MAIL) {
                    userForeign.setForeignId(oneTimeKey.getMail());
                    authService.updateUserForeign(userForeign);
                    Person person = personService.getPersonById(userId);
                    person.setMail(oneTimeKey.getMail());
                    person.setUpdateDatetime(new Date(System.currentTimeMillis()));
                    personService.updatePerson(person);
                } else if (userForeignType == Constant.USER_FOREIGN_TYPE.FACEBOOK || userForeignType == Constant.USER_FOREIGN_TYPE.TWITTER) {
                    Person person = personService.getPersonById(userId);
                    person.setMail(oneTimeKey.getMail());
                    person.setUpdateDatetime(new Date(System.currentTimeMillis()));
                    personService.updatePerson(person);
                } else {
                    logger.warn("User change mail with invalid user foreign type, userId=" + userId);
                    return jsonObject.withStatus(JsonStatus.DATA_NOT_FOUND);
                }

                Map<String, Object> m = new HashMap<>();
                m.put("mail", oneTimeKey.getMail());
                authService.delOneTimeKeyByTypeAndToken(oneTimeKey.getId());
                jsonObject.setResultList(m);
                return jsonObject.withStatus(JsonStatus.SUCCESS);
            }
        }
    }

    private void setLoginCookie(HttpServletResponse response, String uid, String sid) {

        Cookie uidCookie = new Cookie(Constant.SESSION.UUID, uid);
        Cookie sidCookie = new Cookie(Constant.SESSION.SESSIONID, sid);
        uidCookie.setPath("/");
        sidCookie.setPath("/");
        uidCookie.setHttpOnly(true);
        sidCookie.setHttpOnly(true);
        uidCookie.setMaxAge(Constant.SESSION.MAX_AGE);
        sidCookie.setMaxAge(Constant.SESSION.MAX_AGE);

        response.addCookie(uidCookie);
        response.addCookie(sidCookie);
    }

    private void setLogoutCookie(HttpServletResponse response, String uid, String sid) {

        Cookie uidCookie = new Cookie(Constant.SESSION.UUID, uid);
        Cookie sidCookie = new Cookie(Constant.SESSION.SESSIONID, sid);
        uidCookie.setPath("/");
        sidCookie.setPath("/");
        uidCookie.setHttpOnly(true);
        sidCookie.setHttpOnly(true);
        uidCookie.setMaxAge(0);
        sidCookie.setMaxAge(0);

        response.addCookie(uidCookie);
        response.addCookie(sidCookie);
    }

    // Auth islogin
    @RequestMapping(value = "/api/person/islogin/", method = {RequestMethod.GET, RequestMethod.OPTIONS})
    @ResponseBody
    public JsonObject authIsLogin(HttpServletRequest request, HttpServletResponse response) throws APIException {

        authService.getUserId(request);
        return jsonObject.withStatus(JsonStatus.SUCCESS);
    }

    @RequestMapping(value = "/api/auth/withdraw/", method = RequestMethod.POST)
    @ResponseBody
    public JsonObject deleteAuth(@RequestBody MultiValueMap<String, String> formData, HttpServletRequest hsr, HttpServletResponse response) throws APIException, IOException {

        long userId = authService.getUserId(hsr);
        User user = authService.getUserById(userId);
        if (user == null) {
            logger.warn("Withdraw with illegal userID=" + user);
            jsonObject.withStatus(JsonStatus.DATA_NOT_FOUND);
        }

        String mail = personService.getPersonMailByUid(userId);

        // トランザクション管理の開始
        DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition();
        txDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = txManager.getTransaction(txDefinition);

        try {

            Withdraw withdraw = new Withdraw();
            byte reason = 0;
            String detail = "";
            if (formData.getFirst("reason") != null || formData.getFirst("reason") != "") {
                reason = Byte.parseByte(formData.getFirst("reason"));
            }
            if (formData.getFirst("detail") != null) {
                detail = formData.getFirst("detail");
            }

            withdraw.setUserId(userId);
            withdraw.setReason(reason);
            withdraw.setDetail(detail);
            withdrawService.insertWithdraw(withdraw);
            authService.updateUserWithdraw(userId);
            entryService.deleteEntryByID(userId);
            personService.deletePerson(userId);
//            authService.deleteUserForeignByUserId(userId);

            txManager.commit(txStatus);
        } catch (Exception e) {
            txManager.rollback(txStatus);
            logger.error("withdraw  failed!, error=" + e.getMessage());
            logger.error("withdraw  failed!, error=" + e.toString());
            e.printStackTrace();
            throw new APIException(JsonStatus.DATA_SAVE_FAILED);
        }

        String uuid = hsr.getHeader(Constant.SESSION.UUID);
        String sid = hsr.getHeader(Constant.SESSION.SESSIONID);
        if (sid == null || sid.isEmpty()) {
            Cookie cookies[] = hsr.getCookies();
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if (c.getName().equals(Constant.SESSION.SESSIONID)) sid = c.getValue();
                    if (c.getName().equals(Constant.SESSION.UUID)) uuid = c.getValue();
                }
            }
        }
        if (sid != null && uuid != null) {
            setLogoutCookie(response, uuid, sid);
        }

        if(mail != null && mail != ""){
            awsService.sendRetreattMail(mail);
        }

        //todo:おトク情報、ストア
        jsonObject.setResultList(null);
        return jsonObject.withStatus(JsonStatus.SUCCESS);
    }
}
