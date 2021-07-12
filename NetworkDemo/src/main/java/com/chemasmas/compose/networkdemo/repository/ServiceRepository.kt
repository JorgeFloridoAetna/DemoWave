package com.chemasmas.compose.networkdemo.repository

import com.chemasmas.compose.networkdemo.util.GitHub
import com.chemasmas.compose.networkdemo.interfaces.GitHubService
import com.chemasmas.compose.networkdemo.model.Repo
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


@Module
@InstallIn(ActivityRetainedComponent::class)
class ServiceRepository @Inject constructor(private val service: GitHubService){

    suspend fun getRepos(user:String) = service.listRepos(user)

}