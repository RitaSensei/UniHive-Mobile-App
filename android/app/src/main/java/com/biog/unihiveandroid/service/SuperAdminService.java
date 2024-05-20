package com.biog.unihiveandroid.service;

import java.util.List;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface SuperAdminService {
    //GET APIS
    @GET("/superadmin/clubs")
    Call<ResponseBody> listClubs(@Header("Authorization") String token);
    @GET("/superadmin/admins")
    Call<ResponseBody> listAdmins(@Header("Authorization") String token);
    @GET("/superadmin/events")
    Call<ResponseBody> listEvents(@Header("Authorization") String token);
    @GET("/superadmin/schools")
    Call<ResponseBody> listSchools(@Header("Authorization") String token);
    @GET("/superadmin/students")
    Call<ResponseBody> listStudents(@Header("Authorization") String token);
    @GET("/superadmin/requests")
    Call<ResponseBody> listRequests(@Header("Authorization") String token);

    @GET("/superadmin/club/{id}")
    Call<Void> getClub(@Header("Authorization") String token, @Path("id") String id);
    @GET("/superadmin/admin/{id}")
    Call<Void> getAdmin(@Header("Authorization") String token, @Path("id") String id);
    @GET("/superadmin/event/{id}")
    Call<Void> getEvent(@Header("Authorization") String token, @Path("id") String id);
    @GET("/superadmin/school/{id}")
    Call<Void> getSchool(@Header("Authorization") String token, @Path("id") String id);
    @GET("/superadmin/student/{id}")
    Call<Void> getStudent(@Header("Authorization") String token, @Path("id") String id);
    @GET("/superadmin/request/{id}")
    Call<Void> getRequest(@Header("Authorization") String token, @Path("id") String id);
    @GET("/superadmin/allcounts")
    Call<ResponseBody> getAllCounts(@Header("Authorization") String token);
    @GET("/superadmin/all")
    Call<ResponseBody> getAll(@Header("Authorization") String token);

    //POST APIS
    @POST("/superadmin/addschool")
    Call<Void> addSchool(@Header("Authorization") String token, @Body Object data);
    @POST("/superadmin/addevent")
    Call<Void> addEvent(@Header("Authorization") String token, @Body Object data);
    @POST("/superadmin/addrequest")
    Call<Void> addRequest(@Header("Authorization") String token, @Body Object data);
//    @POST("/superadmin/addfollowers/{id}")
//    Call<Club> addFollowers(@Path("id") UUID id, @Body List<Student> followers);
    @POST("/auth/register/club")
    Call<Void> addClub(@Header("Authorization") String token, @Body Object data);
    @POST("auth/register/admin")
    Call<Void> addAdmin(@Header("Authorization") String token, @Body Object data);
    @POST("/auth/register/student")
    Call<Void> addStudent(@Header("Authorization") String token, @Body Object data);

    //PUT APIS
    @PUT("/superadmin/upadmin/{id}")
    Call<Void> updateAdmin(@Header("Authorization") String token,@Path("id") String id, @Body Object data);
    @PUT("/superadmin/upschool/{id}")
    Call<Void> updateSchool(@Header("Authorization") String token,@Path("id") String id, @Body Object data);
    @PUT("/superadmin/upstudent/{id}")
    Call<Void> updateStudent(@Header("Authorization") String token,@Path("id") String id, @Body Object data);
    @PUT("/superadmin/upclub/{id}")
    Call<Void> updateClub(@Header("Authorization") String token,@Path("id") String id, @Body Object data);
    @PUT("/superadmin/upevent/{id}")
    Call<Void> updateEvent(@Header("Authorization") String token,@Path("id") String id, @Body Object data);
    @PUT("/superadmin/uprequest/{id}")
    Call<Void> updateRequest(@Header("Authorization") String token,@Path("id") String id, @Body Object data);
    @PUT("/superamdin/upemail/{email}")
    Call<ResponseBody> updateSuperAdminEmail(@Header("Authorization") String token,@Path("email") String email);
    @PUT("/superamdin/uppassword")
    Call<ResponseBody> updateSuperAdminPassword(@Header("Authorization") String token,@Body Object data);

    //DELETE APIS
    @DELETE("/superadmin/deladmin/{id}")
    Call<Void> deleteAdmin(@Header("Authorization") String token, @Path("id") String id);
    @DELETE("/superadmin/delschool/{id}")
    Call<Void> deleteSchool(@Header("Authorization") String token, @Path("id") String id);
    @DELETE("/superadmin/delstudent/{id}")
    Call<Void> deleteStudent(@Header("Authorization") String token, @Path("id") String id);
    @DELETE("/superadmin/delclub/{id}")
    Call<Void> deleteClub(@Header("Authorization") String token, @Path("id") String id);
//    @DELETE("/superadmin/delfollowers/{id}")
//    Call<Void> deleteFollowers(@Path("id") UUID id, @Body List<Student> followers);
    @DELETE("/superadmin/delevent/{id}")
    Call<Void> deleteEvent(@Header("Authorization") String token, @Path("id") String id);
    @DELETE("/superadmin/delrequest/{id}")
    Call<Void> deleteRequest(@Header("Authorization") String token, @Path("id") String id);

    // ACCEPT REQUEST
    @PUT("/auth/acceptrequest/{id}")
    Call<Void> acceptRequest(@Header("Authorization") String token, @Header("Content_Type") String contentType, @Path("id") String id);

    //UPLOAD FILE
    @POST("/upload")
    @Multipart
    Call<String> uploadFiles(@Part List<MultipartBody.Part> files);

}
