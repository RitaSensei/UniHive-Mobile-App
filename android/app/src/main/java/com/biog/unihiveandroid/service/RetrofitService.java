package com.biog.unihiveandroid.service;

import com.google.gson.Gson;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    private Retrofit retrofit;
    private static final String BASE_URL = "http://192.168.11.108:8080";
    public RetrofitService(){
        initRetrofit();
    }

    private void initRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public Retrofit getRetrofit(){
        return retrofit;
    }
}
