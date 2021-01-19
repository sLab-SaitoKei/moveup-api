package jp.co.vermore.common.util;

import jp.co.vermore.common.Constant;
import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xieyoujun on 2018/02/04.
 */
public class StringUtil {
    public static String getUuid() {
        return RandomStringUtils.random(Constant.UUID.SIZE, Constant.SEEDS);
    }

    public static String getSessionId() {
        return RandomStringUtils.random(Constant.SESSION.SIZE, Constant.SEEDS);
    }

    public static String sha256(String password) {
        return DigestUtils.sha256Hex(password);
    }

    public static String getAWSFileName(int awsFileNameLength) {
        return RandomStringUtils.random(awsFileNameLength, Constant.SEEDS);
    }

    public static String getAWSUri(String awsRegion, String awsBucket, String name) {
        return "https://s3-" + awsRegion + ".amazonaws.com/"+awsBucket+"/"+name;
    }

    public static String getOneTimeToken() {
        return RandomStringUtils.random(Constant.ONE_TIME_KEY.SIZE, Constant.SEEDS);
    }

    public static String urlEncode(String url) throws UnsupportedEncodingException {
        URLCodec codec = new URLCodec("UTF-8");
        return codec.encode(url, "UTF-8");
    }

    public static String getSerialNumber() {
        String serialNumber = null;
        Date date = new Date();
        String formatter= DateUtil.dateToStringyyyyMMddForSerialNumber(date);
        String yyyy = formatter.substring(0,4);
        String mmdd = formatter.substring(4,8);
        String random = RandomStringUtils.random(6, true, true);
        serialNumber = yyyy + "-"+ mmdd + "-" + random;
        return serialNumber;
    }

    public static String getPswd(){
        return RandomStringUtils.random(8, Constant.SEEDS);
    }

    public static String getPrice(int priceNum){
        String Price = null;
        DecimalFormat df = new DecimalFormat("#,###");
        Price = df.format(priceNum);
        return Price ;
    }

}
