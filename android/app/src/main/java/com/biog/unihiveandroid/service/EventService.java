package com.biog.unihiveandroid.service;

import com.biog.unihiveandroid.model.Event;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface EventService {
    @GET("/event/events")
    Call<ResponseBody> getEvents();
}
