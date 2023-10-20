package com.cofbro.qian.utils

import android.content.Context
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import com.hjq.toast.ToastUtils


fun String.showSignResult() {
    var toast = ""
    toast = if (this.contains("success") || this.contains("签到成功")) {
        "签到成功"
    } else if (contains("签到过了")) {
        "您已经签到过啦~"
    } else {
        "签到失败!"
    }
    ToastUtils.show(toast)
}

fun getStatusBarHeight(context: Context): Int {
    var result = 0
    val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = context.resources.getDimensionPixelSize(resourceId)
    }
    return result
}

fun Context.saveJsonArraySp(key: String, value: List<*>){
    val json = JSONArray()
    json.addAll(value)
    val editor = getSharedPreferences("sp_data", Context.MODE_PRIVATE).edit()
    editor.putString(key, json.toJSONString()).apply()

}
fun Context.getJsonArraySp(key: String): String? {
    val sp = getSharedPreferences("sp_data", Context.MODE_PRIVATE)
    return sp.getString(key, "")
}


