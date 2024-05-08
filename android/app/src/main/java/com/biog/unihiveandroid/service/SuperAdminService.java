package com.biog.unihiveandroid.service;

import com.biog.unihiveandroid.model.Admin;
import com.biog.unihiveandroid.model.Club;
import com.biog.unihiveandroid.model.Event;
import com.biog.unihiveandroid.model.Request;
import com.biog.unihiveandroid.model.School;
import com.biog.unihiveandroid.model.Student;
import java.util.List;
import java.util.UUID;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface SuperAdminService {
    //GET APIS
    @GET("/superadmin/clubs")
    Call<List<Club>> getAllClubs();
    @GET("/superadmin/students")
    Call<List<Student>> getAllStudents();
    @GET("/superadmin/events")
    Call<List<Event>> getAllEvents();
    @GET("/superadmin/schools")
    Call<List<School>> getAllSchools();
    @GET("/superadmin/admins")
    Call<List<Admin>> getAllAdmins();
    @GET("/superadmin/requests")
    Call<List<Request>> getAllRequests();
    @GET("/superadmin/admin/{id}")
    Call<Admin> getAdmin(@Path("id") UUID id);
    @GET("/superadmin/school/{id}")
    Call<School> getSchool(@Path("id") UUID id);
    @GET("/superadmin/student/{id}")
    Call<Student> getStudent(@Path("id") UUID id);
    @GET("/superadmin/club/{id}")
    Call<Club> getClub(@Path("id") UUID id);
    @GET("/superadmin/event/{id}")
    Call<Event> getEvent(@Path("id") UUID id);
    @GET("/superadmin/request/{id}")
    Call<Request> getRequest(@Path("id") UUID id);

    //POST APIS
    @POST("/superadmin/addschool")
    Call<School> addSchool(@Body School school);
    @POST("/superadmin/addevent")
    Call<Event> addEvent(@Body Event event);
    @POST("/superadmin/addrequest")
    Call<Request> addRequest(@Body Request request);
    @POST("/superadmin/addfollowers/{id}")
    Call<Club> addFollowers(@Path("id") UUID id, @Body List<Student> followers);
    @POST("/auth/register/club")
    Call<Club> addClub(@Body Club club);
    @POST("auth/register/admin")
    Call<Admin> addAdmin(@Body Admin admin);
    @POST("/auth/register/student")
    Call<Student> addStudent(@Body Student student);

    //PUT APIS
    @PUT("/superadmin/upadmin/{id}")
    Call<Admin> updateAdmin(@Path("id") UUID id, @Body Admin newadmin);
    @PUT("/superadmin/upschool/{id}")
    Call<School> updateSchool(@Path("id") UUID id, @Body School newschool);
    @PUT("/superadmin/upstudent/{id}")
    Call<Student> updateStudent(@Path("id") UUID id, @Body Student newstudent);
    @PUT("/superadmin/upclub/{id}")
    Call<Club> updateClub(@Path("id") UUID id, @Body Club newclub);
    @PUT("/superadmin/upevent/{id}")
    Call<Event> updateEvent(@Path("id") UUID id, @Body Event newevent);
    @PUT("/superadmin/uprequest/{id}")
    Call<Request> updateRequest(@Path("id") UUID id, @Body Request newrequest);

    //DELETE APIS
    @DELETE("/superadmin/deladmin/{id}")
    Call<Void> deleteAdmin(@Path("id") UUID id);
    @DELETE("/superadmin/delschool/{id}")
    Call<Void> deleteSchool(@Path("id") UUID id);
    @DELETE("/superadmin/delstudent/{id}")
    Call<Void> deleteStudent(@Path("id") UUID id);
    @DELETE("/superadmin/delclub/{id}")
    Call<Void> deleteClub(@Path("id") UUID id);
    @DELETE("/superadmin/delfollowers/{id}")
    Call<Void> deleteFollowers(@Path("id") UUID id, @Body List<Student> followers);
    @DELETE("/superadmin/delevent/{id}")
    Call<Void> deleteEvent(@Path("id") UUID id);
    @DELETE("/superadmin/delrequest/{id}")
    Call<Void> deleteRequest(@Path("id") UUID id);

    //UPLOAD FILE
    @POST("/upload")
    @Headers("Content-Type: multipart/form-data")
    @Multipart
    Call<String> uploadFile(@Part MultipartBody.Part file);

    // ACCEPT REQUEST
    @PUT("/auth/acceptrequest/")
    Call<Void> acceptRequest(@Body Request request);
}
