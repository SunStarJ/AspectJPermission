package net.xikang.myapplication.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import kotlinx.coroutines.launch
import net.xikang.myapplication.helper.PagingHelper

/**
 *
 * @ProjectName:    My Application
 * @Package:        net.xikang.myapplication.main
 * @ClassName:      MainViewModel
 * @Description:    类作用描述
 * @Author:         孙浩
 * @CreateDate:     2022/1/10 3:35 下午
 * @UpdateUser:
 * @UpdateDate:     2022/1/10 3:35 下午
 * @UpdateRemark:
 * @Version:        1.0
 */
class MainViewModel : ViewModel() {

    val listData = Pager(config = PagingConfig(pageSize = 20, initialLoadSize = 20)) {
        PagingHelper<String> { index, pageSize ->
            MainRepository.getDataList(pageSize = pageSize, index = index)
        }
    }.flow.cachedIn(viewModelScope)

//    fun getListData(index: Int, pageSize: Int): List<String> {
//        val result = mutableListOf<String>()
//        viewModelScope.launch {
//
//        }
//        return result
//    }
}