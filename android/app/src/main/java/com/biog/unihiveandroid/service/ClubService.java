package com.biog.unihiveandroid.service;

import com.biog.unihiveandroid.model.Club;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ClubService {
    @GET("/club/clubs")
    Call<ResponseBody> getClubs();
}
