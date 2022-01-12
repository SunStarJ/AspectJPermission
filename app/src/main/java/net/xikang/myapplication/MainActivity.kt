package net.xikang.myapplication

import android.Manifest
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import coil.annotation.ExperimentalCoilApi
import com.github.SunStarJ.aspectj_permission.aspectj.PermissionChecker
import com.github.SunStarJ.aspectj_permission.aspectj.PermissionDine
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.snail.antifake.deviceid.macaddress.MacAddressUtils
import net.xikang.myapplication.main.MainViewModel
import net.xikang.myapplication.ui.theme.MyApplicationTheme

class MainActivity : AppCompatActivity() {
    val mainVM by lazy {
        MainViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    ListGreeting(mainVM) {
                        //打开相机操作
                        openCamera()
                    }

                }
            }
        }
        var data = TestData();
        data.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                Log.e("MainActivity", "onPropertyChanged: ${propertyId}")
            }
        })
        data.name = "123";
        data.notifyChange()
        Log.e("mac", "onCreate: ${MacAddressUtils.getMacAddress(this)}")
    }

    @PermissionChecker([Manifest.permission.WRITE_EXTERNAL_STORAGE])//权限数组
    fun openCamera() {
        //同意请求
        //业务代码
    }

    @PermissionDine
    fun permissionDine() {
        //请求失败
        Log.e("getPermission", "getPermission: error")
    }


}


private val refreshing = MutableLiveData(false)

@ExperimentalCoilApi
@Composable
fun ListGreeting(mainViewModel: MainViewModel, itemClick: () -> Unit) {
    val isRefreshing by refreshing.observeAsState(false)
    val lazyItemList = mainViewModel.listData.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Test") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {

            }, content = {
                Icon(Icons.Filled.Add, contentDescription = null)
            })
        }
    ) {

        when (lazyItemList.loadState.refresh) {
            is LoadState.NotLoading -> {
                SwipeRefresh(
                    state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
                    onRefresh = { refresh() }) {
                    LazyColumn {
                        itemsIndexed(lazyItemList) { index, data ->
                            Greeting(text = data ?: "", itemClick)
                        }
                    }
                }
            }
            LoadState.Loading -> {
                Text(text = "loading")
            }
            is LoadState.Error -> {
                Text(text = "error")
            }
        }


    }


}

fun refresh() {
    refreshing.value = true
    Handler().postDelayed({
        refreshing.value = false
    }, 2000)
}

@Composable
fun Greeting(text: String, itemClick: () -> Unit) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .clickable {
                itemClick.invoke()
            }
    ) {
//        Image(
//            painter = rememberImagePainter(
//                data = "https://img0.baidu.com/it/u=312301072,1324966529&fm=26&fmt=auto&gp=0.jpg",
//                builder = { ->
//                    placeholder(R.mipmap.ic_launcher_round)
//                }),
//            contentDescription = null,
//            modifier = Modifier
//                .size(60.dp)
//                .clip(CircleShape)
//        )
        Box(modifier = Modifier.size(20.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = "Hello Android!")
            Text(text = "Hello $text!")
        }
    }

}

@Preview(showBackground = true)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        ListGreeting(MainViewModel()) {}
    }
}

