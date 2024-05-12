package com.biog.unihiveandroid.service;

import com.biog.unihiveandroid.model.Club;
import com.biog.unihiveandroid.model.Event;
import com.biog.unihiveandroid.model.Student;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface StudentService {
    // GET APIS
    @GET("/student/email/{id}")
    Call<ResponseBody> getStudent(@Header("Authorization") String token, @Path("id") String id);
    @GET("/student/events")
    Call<List<Event>> getEvents(@Header("Authorization") String token);
    @GET("/student/club/{id}")
    Call<Club> getClub(@Header("Authorization") String token, @Path("id") String id);
    @GET("/student/club/{id}/events")
    Call<List<Event>> getEventsByClub(@Header("Authorization") String token, @Path("id") String id);

    // PUT APIS
    @PUT("student/upstudent/{id}/email/{email}")
    Call<Void> updateStudentEmail(
            @Header("Authorization") String token,
            @Path("id") String id,
            @Path("email") String email
    );
    @PUT("student/upstudent/{id}/profileimage/{profileImage}")
    Call<Void> updateStudentProfileImage(
            @Header("Authorization") String token,
            @Path("id") String id,
            @Path("profileImage") String profileImage
    );
}

