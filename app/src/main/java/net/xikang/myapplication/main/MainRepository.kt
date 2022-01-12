package net.xikang.myapplication.main

/**
 *
 * @ProjectName:    My Application
 * @Package:        net.xikang.myapplication.main
 * @ClassName:      MainRepository
 * @Description:    类作用描述
 * @Author:         孙浩
 * @CreateDate:     2022/1/10 3:36 下午
 * @UpdateUser:
 * @UpdateDate:     2022/1/10 3:36 下午
 * @UpdateRemark:
 * @Version:        1.0
 */
object MainRepository {
    suspend fun getDataList(index: Int, pageSize: Int): List<String> {
        var list = mutableListOf<String>();
        for (i in 0 until pageSize) {
            list.add("列表页item_$index,item_$i,pagesize_$pageSize")
        }
        return list
    }
}