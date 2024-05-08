package com.biog.unihiveandroid.service;

import com.biog.unihiveandroid.model.RegisterRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthenticationService {
    @POST("/auth/signup")
    Call<Void> register(@Body RegisterRequest registerRequest);

    @POST("/auth/authenticate")
    Call<Void> login(@Body RegisterRequest loginRequest);

    @POST("/auth/logout")
    Call<Void> logout(@Body RegisterRequest logoutRequest);

    @POST("/auth/forgotPassword")
    Call<Void> forgottenPassword(@Body RegisterRequest forgotPasswordRequest);

}
