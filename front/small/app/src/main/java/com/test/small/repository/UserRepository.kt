package com.test.small.repository

import com.test.small.Api.ApiInterface
import com.test.small.Api.RetrofitInstance
import retrofit2.Response

class UserRepository {
    private val apiService = RetrofitInstance.getInstance().create(ApiInterface::class.java)

    suspend fun getUserData(page: Int, limit: Int): Response<List<userInfoItem>> {
        return apiService.getUserData(page, limit)
    }

    suspend fun postUserData(input: HashMap<String, Any>): Response<userInfoItem>{
        return apiService.postUserList(input)
    }
}