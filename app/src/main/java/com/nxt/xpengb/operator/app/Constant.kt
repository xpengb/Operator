package com.nxt.xpengb.operator.app

/**
 *   Create : xpengb@outlook.com
 *   Date   : 2017/10/29
 *   Version: V1.0
 *   Desc   :
 */
object Constant {
    /**
     * DB
     */
    const val DB_NAME = "operator"

    /**
     * update
     */
    val CHECK_UPDATE_URL = "http://182.116.57.248:88/apk/cuckoo.txt"

    /**
     * api
     */
    const val API_BASE = "http://hcscda.yzs.yn15.com"
    const val LOGIN_URL = API_BASE + "/login/doAction"
    const val LOGOUT_URL = API_BASE + "/Login/Logout"
    const val FARMWORK_URL = API_BASE + "/api/Fct/FarmWork"
    const val FARMWORK_EDIT_URL = API_BASE + "/api/Fct/FarmWork/edit"
    const val PRODUCTION_BATCH = API_BASE + "/plugins/getlookupdata"
    const val IMAGE_URL = API_BASE + "/api/Fct/FarmWork/PostImgFileUpload"
    const val KEY_URL = API_BASE + "/api/Fct/Farmwork/GetNewKey"

    /**
     * static
     */
    const val LOGIN_IN = "log_in"
    const val ACCOUNT = "account"
    const val APP_PROJECT_CODE = "appprojectcode"

    /**
     * sms
     */
    const val URL_SMS = "http://http.yunsms.cn/tx/?uid=55088&pwd=c777049c24d28e9ed5d6a954613efaca&mobile=%s&content=%s"
    /**
     * he weather api
     */
    const val HE_WEATHER_API = "https://free-api.heweather.com/s6/weather/forecast?parameters&key=00e9a03632444c49944c8e7e52af14d4&location="
}