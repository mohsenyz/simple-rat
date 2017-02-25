package com.phoenix.rat.helper;


import java.net.URLEncoder;

/**
 * Created by dahlia on 2/25/17.
 */
public class URLHelper {
    public static URLHelper fromURL(String url){
        return new URLHelper(url);
    }
    String url = null;
    private URLHelper(String url){
        this.url = url;
    }

    public URLHelper addParam(String key, String value){
        try {
            key = "{" + key + "}";
            this.url = this.url.replace(key, URLEncoder.encode(value, "utf8"));
        }catch (Exception e){
            e.printStackTrace();
        }
        return this;
    }


    public String build(){
        return this.url;
    }


}
