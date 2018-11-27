package com.example.pcuc;


import android.support.annotation.NonNull;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.pcuc.Connection_Detail.configureClient;
import static com.example.pcuc.Connection_Detail.getHeader;

class HttpConnection {
    public String requestPost(@NonNull String url, final Map<String, Object> paramMap) throws KeyManagementException, NoSuchAlgorithmException, IOException {
        OkHttpClient client = new OkHttpClient();
        if (url.contains("https"))
            client= configureClient(new OkHttpClient().newBuilder()).build();
        Request.Builder builder = new Request.Builder().url(url);
        if (paramMap != null)
        {
            FormBody.Builder postData = new FormBody.Builder();
            for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
                postData.add(entry.getKey(), (String) entry.getValue());
            }
            builder.post(postData.build());
        }
        builder.header("User-Agent","UA_DEFINE");
        for(Map.Entry<String,String> head: getHeader().entrySet()){
            builder.addHeader(head.getKey(),head.getValue());
        }

        Request request = builder.build();
        Response response = client.newCall(request).execute();

        //Log.d("TestHttp", "OkHttp-Selected-Protocol: " + response.headers());
        //Log.d("TestHttp", "Response code is " + response.code());
        int rcode = response.code();
        if (rcode==200) {
            //noinspection ConstantConditions
            return response.body().string();
        }
        else
            return "";
    }
}
