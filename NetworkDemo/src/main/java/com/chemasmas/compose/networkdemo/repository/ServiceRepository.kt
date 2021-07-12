package com.chemasmas.compose.networkdemo.repository

import com.chemasmas.compose.networkdemo.util.GitHub
import com.chemasmas.compose.networkdemo.interfaces.GitHubService
import com.chemasmas.compose.networkdemo.model.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ServiceRepository(private val service: GitHubService = GitHub.service){
    fun getRepos(user:String): Flow<List<Repo>?> {
        return flow {
            val repos = service.listRepos(user)
            emit(repos)
        }.flowOn(Dispatchers.IO)
    }
}