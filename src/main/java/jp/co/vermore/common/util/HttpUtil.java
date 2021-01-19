package jp.co.vermore.common.util;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.impl.cookie.DefaultCookieSpec;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.*;

/**
 * HTTP Request Util Class
 *
 * @author : Richard Jia
 * @date : 2018/05/07
 */

public class HttpUtil {

    private static int bufferSize= 1024;

    private static volatile HttpUtil instance;

    private volatile CloseableHttpClient client;

    private volatile BasicCookieStore cookieStore;

    public static String defaultEncoding= "utf-8";

    private static List<NameValuePair> paramsConverter(Map<String, String> params){
        List<NameValuePair> nvps = new LinkedList<NameValuePair>();
        Set<Map.Entry<String, String>> paramsSet= params.entrySet();
        for (Map.Entry<String, String> paramEntry : paramsSet) {
            nvps.add(new BasicNameValuePair(paramEntry.getKey(), paramEntry.getValue()));
        }
        return nvps;
    }

    public static String readStream(InputStream in, String encoding){
        if (in == null){
            return null;
        }
        try {
            InputStreamReader inReader= null;
            if (encoding == null){
                inReader= new InputStreamReader(in, defaultEncoding);
            }else{
                inReader= new InputStreamReader(in, encoding);
            }
            char[] buffer= new char[bufferSize];
            int readLen= 0;
            StringBuffer sb= new StringBuffer();
            while((readLen= inReader.read(buffer))!=-1){
                sb.append(buffer, 0, readLen);
            }
            inReader.close();
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private HttpUtil()throws Exception{

        SSLContextBuilder builder = new SSLContextBuilder();
        builder.loadTrustMaterial(new AnyTrustStrategy());

        HostnameVerifier hostnameVerifierAllowAll = new HostnameVerifier() {
            @Override
            public boolean verify(String name, SSLSession session) {
                return true;
            }
        };
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build(),
                new String[] { "SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.1", "TLSv1.2" }, null, hostnameVerifierAllowAll);

        HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler() {
            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                // retry setting
                if (executionCount >= 5) {
                    // Do not retry if over max retry count
                    return false;
                }
                if (exception instanceof InterruptedIOException) {
                    // Timeout
                    return false;
                }
                if (exception instanceof UnknownHostException) {
                    // Unknown host
                    return false;
                }
                if (exception instanceof ConnectTimeoutException) {
                    // Connection refused
                    return false;
                }
                if (exception instanceof SSLException) {
                    // SSL handshake exception
                    return false;
                }
                HttpClientContext clientContext = HttpClientContext.adapt(context);
                HttpRequest request = clientContext.getRequest();
                boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
                if (idempotent) {
                    return true;
                }
                return false;
            }
        };
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(120000).setSocketTimeout(120000)// set timeout
                .build();
        client = HttpClients.custom().setSSLSocketFactory(sslsf).setRetryHandler(myRetryHandler)// retry setting
                .setDefaultRequestConfig(requestConfig).build();
    }

    public static HttpUtil getInstance(){
        synchronized (HttpUtil.class) {
            if (HttpUtil.instance == null){
                try {
                    instance = new HttpUtil();
                } catch (Exception e) {	}
            }
            return instance;
        }
    }

    public InputStream doGet(String url) throws URISyntaxException, ClientProtocolException, IOException{
        HttpResponse response= this.doGet(url, null);
        return response!=null ? response.getEntity().getContent() : null;
    }

    public String doGetForString(String url) throws URISyntaxException, ClientProtocolException, IOException{
        return HttpUtil.readStream(this.doGet(url), null);
    }

    public InputStream doGetForStream(String url, Map<String, String> queryParams) throws URISyntaxException, ClientProtocolException, IOException{
        HttpResponse response= this.doGet(url, queryParams);
        return response!=null ? response.getEntity().getContent() : null;
    }

    public String doGetForString(String url, Map<String, String> queryParams) throws URISyntaxException, ClientProtocolException, IOException{
        return HttpUtil.readStream(this.doGetForStream(url, queryParams), null);
    }

    /**
     * Basic Get requests
     * @param url request url
     * @param queryParams request header params
     * @return
     * @throws URISyntaxException
     * @throws IOException
     * @throws ClientProtocolException
     */
    public HttpResponse doGet(String url, Map<String, String> queryParams) throws URISyntaxException, ClientProtocolException, IOException{
        HttpGet gm = new HttpGet();
        URIBuilder builder = new URIBuilder(url);
        // Fill in query parameters
        if (queryParams!=null && !queryParams.isEmpty()){
            builder.setParameters(HttpUtil.paramsConverter(queryParams));
        }
        gm.setURI(builder.build());
        return client.execute(gm);
    }

    public InputStream doPostForStream(String url, Map<String, String> queryParams) throws URISyntaxException, ClientProtocolException, IOException {
        HttpResponse response = this.doPost(url, queryParams, null);
        return response!=null ? response.getEntity().getContent() : null;
    }

    public String doPostForString(String url, Map<String, String> queryParams) throws URISyntaxException, ClientProtocolException, IOException {
        return HttpUtil.readStream(this.doPostForStream(url, queryParams), null);
    }

    public InputStream doPostForStream(String url, Map<String, String> queryParams, Map<String, String> formParams) throws URISyntaxException, ClientProtocolException, IOException{
        HttpResponse response = this.doPost(url, queryParams, formParams);
        return response!=null ? response.getEntity().getContent() : null;
    }

    public String doPostRetString(String url, Map<String, String> queryParams, Map<String, String> formParams) throws URISyntaxException, ClientProtocolException, IOException{
        return HttpUtil.readStream(this.doPostForStream(url, queryParams, formParams), null);
    }

    /**
     * Basic Post requests
     * @param url request url
     * @param queryParams request header params
     * @param formParams post form params
     * @return
     * @throws URISyntaxException
     * @throws IOException
     * @throws ClientProtocolException
     */
    public HttpResponse doPost(String url, Map<String, String> queryParams, Map<String, String> formParams) throws URISyntaxException, ClientProtocolException, IOException{
        HttpPost pm = new HttpPost();
        URIBuilder builder = new URIBuilder(url);
        //Fill in query parameters
        if (queryParams!=null && !queryParams.isEmpty()){
            builder.setParameters(HttpUtil.paramsConverter(queryParams));
        }
        pm.setURI(builder.build());
        //Fill in the form parameters
        if (formParams!=null && !formParams.isEmpty()){
            pm.setEntity(new UrlEncodedFormEntity(HttpUtil.paramsConverter(formParams),defaultEncoding));
        }
        return client.execute(pm);
    }


    /**
     * Get the Cookie in the current Http client state
     * @param domain scope
     * @param port port if null default 80
     * @param path Cookie direction if null default "/"
     * @param useSecure Whether Cookie uses security mechanisms if null default false
     * @return
     */
    public Map<String, Cookie> getCookie(String domain, Integer port, String path, Boolean useSecure){
        if (domain == null){
            return null;
        }
        if (port==null){
            port= 80;
        }
        if (path==null){
            path="/";
        }
        if (useSecure==null){
            useSecure= false;
        }
        List<Cookie> cookies = cookieStore.getCookies();
        if (cookies==null || cookies.isEmpty()){
            return null;
        }

        CookieOrigin origin= new CookieOrigin(domain, port, path, useSecure);
        DefaultCookieSpec cookieSpec = new DefaultCookieSpec(null, false);
        Map<String, Cookie> retVal= new HashMap<String, Cookie>();
        for (Cookie cookie : cookies) {
            if(cookieSpec.match(cookie, origin)){
                retVal.put(cookie.getName(), cookie);
            }
        }
        return retVal;
    }

    /**
     * Set Cookie in batch
     * @param outCookies cookie key value map
     * @param domain scope domain not null
     * @param path path if null default "/"
     * @param useSecure Whether Cookie uses security mechanisms if null default false
     * @return Whether or not the cookie has been successfully set up
     */
    public boolean setCookie(Map<String, String> outCookies, String domain, String path, Boolean useSecure){
        synchronized (cookieStore) {
            if (domain==null){
                return false;
            }
            if (path==null){
                path= "/";
            }
            if (useSecure==null){
                useSecure= false;
            }
            if (outCookies==null || outCookies.isEmpty()){
                return true;
            }
            Set<Map.Entry<String, String>> set= outCookies.entrySet();
            List<Cookie> cookies = cookieStore.getCookies();
            for (int i = 0; i < cookies.size(); i++) {
                outCookies.put(cookies.get(i).getName(), cookies.get(i).getValue());
            }

            String key= null;
            String value= null;
            for (Map.Entry<String, String> entry : set) {
                key= entry.getKey();
                if (key==null || key.isEmpty() || value==null || value.isEmpty()){
                    throw new IllegalArgumentException("cookies key and value both can not be empty");
                }
                BasicClientCookie cookie= new BasicClientCookie(key, value);
                cookie.setDomain(domain);
                cookie.setPath(path);
                cookie.setSecure(useSecure);
                cookieStore.addCookie(cookie);
            }
            return true;
        }
    }

    /**
     * Set a single Cookie
     * @param key Cookie key
     * @param value Cookie value
     * @param domain scope domain not null
     * @param path path if null default "/"
     * @param useSecure Whether Cookie uses security mechanisms if null default false
     * @return Whether or not the cookie has been successfully set up
     */
    public boolean setCookie(String key, String value, String domain, String path, Boolean useSecure){
        Map<String, String> cookies= new HashMap<String, String>();
        cookies.put(key, value);
        return setCookie(cookies, domain, path, useSecure);
    }

}

class AnyTrustStrategy implements TrustStrategy{

    @Override
    public boolean isTrusted(java.security.cert.X509Certificate[] chain, String authType)
            throws java.security.cert.CertificateException {
        return true;
    }

}
