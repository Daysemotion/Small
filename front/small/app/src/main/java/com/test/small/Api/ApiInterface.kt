package com.test.small.Api

import com.test.small.repository.userInfoItem
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiInterface {
    @GET("comments/")
    suspend fun getUserData(
        @Query("_page")page : Int,
        @Query("_limit")limit : Int
    ): Response<List<userInfoItem>>

    @FormUrlEncoded
    @POST("posts/")
    suspend fun postUserList(
        @FieldMap param: HashMap<String, Any>
    ): Response<userInfoItem>
}