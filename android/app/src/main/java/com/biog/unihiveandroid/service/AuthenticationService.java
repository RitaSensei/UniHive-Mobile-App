package com.biog.unihiveandroid.service;

import com.biog.unihiveandroid.model.RegisterRequest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthenticationService {
    @POST("/auth/signup")
    Call<Void> register(@Body RegisterRequest registerRequest);

    @POST("/auth/authenticate")
    Call<ResponseBody> login(@Body RegisterRequest loginRequest);

    @POST("/auth/logout")
    Call<Void> logout(@Body RegisterRequest logoutRequest);

    @POST("/auth/forgotPassword")
    Call<ResponseBody> forgottenPassword(@Body RegisterRequest forgotPasswordRequest);

}
