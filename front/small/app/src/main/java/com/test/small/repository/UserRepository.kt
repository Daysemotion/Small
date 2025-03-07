package com.test.small.Api

import com.test.small.userInfoItem

class UserRepository {
    private val apiService = RetrofitInstance.getInstance().create(ApiInterface::class.java)

    suspend fun  getUserData(page: Int): Resopnse<Liser<userInfoItem>> {

    }
}