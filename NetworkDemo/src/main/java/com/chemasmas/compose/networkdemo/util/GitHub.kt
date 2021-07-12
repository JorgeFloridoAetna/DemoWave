package com.chemasmas.compose.networkdemo.util

import com.chemasmas.compose.networkdemo.interfaces.GitHubService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GitHub {
    private val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    val service: GitHubService = retrofit.create(GitHubService::class.java)
}
