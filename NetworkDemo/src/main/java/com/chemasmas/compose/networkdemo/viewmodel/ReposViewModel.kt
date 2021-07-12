package com.chemasmas.compose.networkdemo.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.chemasmas.compose.networkdemo.model.Repo
import com.chemasmas.compose.networkdemo.repository.ServiceRepository
import com.chemasmas.compose.networkdemo.util.isNetworkConnected
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.lang.reflect.Type
import javax.inject.Inject

@HiltViewModel
class ReposViewModel @Inject constructor(private val repository: ServiceRepository,
@ApplicationContext private val context: Context): ViewModel() {


    private val _repos = MutableLiveData<List<Repo>>( listOf() )
    val repos: LiveData<List<Repo>> = _repos


    fun start(user:String) = viewModelScope.launch {
        if(context.isNetworkConnected()){
            try{
                val res = repository.getRepos(user)
                storeToSharedPrefs(res)
                _repos.postValue(res)
            }catch (e:Exception){
                Log.e("viewModel",e.localizedMessage,e.cause)
            }

        }
        else{
            restoreFromSharedPrefs()
        }
    }

    private fun storeToSharedPrefs(res: List<Repo>?):Boolean{
        val prefs = context.getSharedPreferences("repos",Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val gson = Gson()
        return editor.putString("repos",gson.toJson(res)).commit()
    }

    private fun restoreFromSharedPrefs():Unit{
        val prefs = context.getSharedPreferences("repos",Context.MODE_PRIVATE)
        val gson = Gson()
        val json = prefs.getString("repos","[]")
        val typeList: Type = object:TypeToken<List<Repo>>(){}.type
        val res:List<Repo> = gson.fromJson(json,typeList)

        _repos.postValue(res)
    }
}