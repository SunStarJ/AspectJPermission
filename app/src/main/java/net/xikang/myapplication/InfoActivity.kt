package net.xikang.myapplication

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberImagePainter
import net.xikang.myapplication.ui.theme.MyApplicationTheme

/**
 *
 * @ProjectName:    My Application
 * @Package:        net.xikang.myapplication
 * @ClassName:      InfoActivity
 * @Description:    类作用描述
 * @Author:         孙浩
 * @CreateDate:     2022/1/11 11:47 上午
 * @UpdateUser:
 * @UpdateDate:     2022/1/11 11:47 上午
 * @UpdateRemark:
 * @Version:        1.0
 */
class InfoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                pageBody()
            }
        }
    }
}

@Composable
fun pageBody() {
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(text = "详情")
        })
    }) {
        Column() {
            Image(painter = rememberImagePainter(data = ""), contentDescription = null)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun preview() {
    MyApplicationTheme {
        pageBody()
    }
}