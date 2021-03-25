package com.example.recyclerviewwithpagination.api;


import com.example.recyclerviewwithpagination.model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiRequestData {
    @GET("users")
    Call<EventModel> getPageResult(
            @Query("page") int pageIndex
    );

    @POST("LoginDemo")
    Call<LoginResponse> doLogin(@Field("username") String username,
                                @Field("password") String password);


}
