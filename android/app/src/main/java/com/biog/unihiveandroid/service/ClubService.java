package com.biog.unihiveandroid.service;

import com.biog.unihiveandroid.model.Club;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ClubService {
    @GET("/club/clubs")
    Call<List<Club>> getClubs();
}
