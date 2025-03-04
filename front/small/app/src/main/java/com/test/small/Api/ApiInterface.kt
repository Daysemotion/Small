package com.test.small.Api

import com.test.small.userInfoItem
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiInterface {
    @GET("users/")
    fun getUserData(
        @Query("page")page : Int
    ): Call<List<userInfoItem>>

    @FormUrlEncoded
    @POST("posts/")
    fun getPostList(
        @FieldMap param: HashMap<String, Any>
    ): Call<userInfoItem>
}