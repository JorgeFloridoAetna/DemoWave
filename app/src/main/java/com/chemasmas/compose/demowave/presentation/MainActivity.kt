package com.chemasmas.compose.demowave.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.chemasmas.compose.demowave.ui.theme.DemoWaveTheme
import com.chemasmas.compose.networkdemo.model.Repo
import com.chemasmas.compose.networkdemo.viewmodel.ReposViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val model: ReposViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContent {
            DemoWaveTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    ReposList(model.repos)
                }
            }
        }

        lifecycleScope.launch {
            model.start("chemasmas78")
        }
    }
}

@Composable
fun ReposList(ld: LiveData<List<Repo>>){
    val lista:List<Repo>? by ld.observeAsState()

    if(lista?.isEmpty() == true){
        Text("No Repos!!")
    }else{
        LazyColumn() {

            items(items = lista ?: listOf()){
                Log.d("repo",it.toString())
                Text(it.toString())
            }

        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val liveData:LiveData<List<Repo>> = MutableLiveData(listOf(Repo.DUMMY
        , Repo.DUMMY
        , Repo.DUMMY
        , Repo.DUMMY
        , Repo.DUMMY
    ))
    DemoWaveTheme {
        ReposList(liveData)
    }
}


@Preview(showBackground = true)
@Composable
fun EmptyPreview() {
    val liveData:LiveData<List<Repo>> = MutableLiveData(listOf(
    ))
    DemoWaveTheme {
        ReposList(liveData)
    }
}


