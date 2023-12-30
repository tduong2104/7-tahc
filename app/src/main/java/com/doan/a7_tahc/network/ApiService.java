package com.doan.a7_tahc.network;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

public interface ApiService {

    @POST("send")
    Call<String> sendMessage(
            @HeaderMap HashMap<String,Object> headers,
            @Body String messageBody);
}
