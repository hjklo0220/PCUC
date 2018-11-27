package com.example.pcuc;


import android.util.Log;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class CustomThread extends Thread{
    private String output = "";
    private Map<String, Object> Map = new HashMap<>();
    private String URL ="";

    public CustomThread(Map<String, Object> paramMap, String url){
        output = "";
        Map = paramMap;
        URL = url;
    }


    public void run(){
        try {

            String data = new HttpConnection().requestPost("http://203.247.211.78/" + URL, Map);
            output = data;
            //JSONObject result = new JSONObject(data);
            Log.d("Response",data);
            //output = result.getString("result_code");
            //System.out.println("[CustomThread] result_code : " + output);
        }
        catch (Throwable throwable) {
            output = "";
            Log.d("Response","Throw catch");
            throwable.printStackTrace();
        }
    }

    public String Getresult(){
        return output;
    }
}
