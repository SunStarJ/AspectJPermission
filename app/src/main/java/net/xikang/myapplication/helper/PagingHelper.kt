package net.xikang.myapplication.helper

import androidx.paging.PagingSource
import androidx.paging.PagingState

/**
 *
 * @ProjectName:    My Application
 * @Package:        net.xikang.myapplication.helper
 * @ClassName:      PagingHelper
 * @Description:    类作用描述
 * @Author:         孙浩
 * @CreateDate:     2022/1/10 3:44 下午
 * @UpdateUser:
 * @UpdateDate:     2022/1/10 3:44 下午
 * @UpdateRemark:
 * @Version:        1.0
 */
class PagingHelper<T : Any>(var repositoryCallBack: suspend (pageIndex: Int, pageSize: Int) -> List<T>) :
    PagingSource<Int, T>() {
    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return kotlin.runCatching {
            val currentPage = params.key ?: 1
            val pageSize = params.loadSize
            // 上一页页码
            val preKey = if (currentPage == 1) null else currentPage.minus(1)
            // 下一页页码
            var nextKey: Int? = currentPage.plus(1)
            val responseList = repositoryCallBack.invoke(currentPage, pageSize) ?: emptyList()
            if (responseList.isEmpty()) {
                nextKey = null
            }
            return@load LoadResult.Page(
                data = responseList,
                prevKey = preKey,
                nextKey = nextKey
            )
        }.getOrElse {
            return@load LoadResult.Error(it)
        }
    }
}