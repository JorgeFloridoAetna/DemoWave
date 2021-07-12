package com.chemasmas.compose.demowave.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.chemasmas.compose.demowave.ui.theme.DemoWaveTheme
import com.chemasmas.compose.networkdemo.model.Repo
import com.chemasmas.compose.networkdemo.repository.ServiceRepository
import com.chemasmas.compose.networkdemo.util.isNetworkConnected

import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val _mld = MutableLiveData<List<Repo>>()
    val ld:LiveData<List<Repo>> get() = _mld
    val repository = ServiceRepository()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContent {
            DemoWaveTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    if(isNetworkConnected()){
                        ReposList(ld)
                    }else{
                        Text("No Network!")
                    }
                }
            }
        }

        lifecycleScope.launch {
            repository.getRepos("chemasmas")
                .onStart {
                    println("Conectando")
                }
                .catch { e->
                    System.err.println("Se murio ${e.localizedMessage}")
                }
                .collect {
                    println(it)
                    _mld.value = it
                }
        }
    }
}

@Composable
fun ReposList(ld: LiveData<List<Repo>>){
    val lista:List<Repo>? by ld.observeAsState()

    LazyColumn() {
        items(items = lista ?: listOf()){
            Log.d("repo",it.toString())
            Text(it.toString())
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val liveData:LiveData<List<Repo>> = MutableLiveData(listOf(Repo.DUMMY, Repo.DUMMY))
    DemoWaveTheme {
        ReposList(liveData)
    }
}


