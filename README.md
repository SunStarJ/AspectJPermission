# AspectJPermission
AspectJPermission


核心代码
```
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


```
调用
```
 //打开相机操作
 openCamera()
```