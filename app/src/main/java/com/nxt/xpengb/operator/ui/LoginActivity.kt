package com.nxt.xpengb.operator.ui

import android.os.CountDownTimer
import android.support.design.widget.TextInputEditText
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.Gson
import com.lzy.okgo.OkGo
import com.lzy.okgo.callback.StringCallback
import com.nxt.xpengb.common.base.BaseActivity
import com.nxt.xpengb.common.okgo.StringDialogCallback
import com.nxt.xpengb.common.utils.CommonUtils
import com.nxt.xpengb.common.utils.SharePrefHelper
import com.nxt.xpengb.operator.app.Constant
import com.nxt.xpengb.operator.R
import okhttp3.Call
import okhttp3.Response
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.io.UnsupportedEncodingException
import java.lang.Exception
import java.net.URLEncoder

/**
 *   Create : xpengb@outlook.com
 *   Date   : 2017/6/30
 *   Version: V1.0
 *   Desc   :
 */
class LoginActivity : BaseActivity(), View.OnClickListener {

    var account: TextInputEditText? = null
    private lateinit var code: TextInputEditText
    var countDown: TextView? = null
    var smsCode = ""
    private lateinit var password: TextInputEditText

    override fun onResume() {
        super.onResume()
//        account!!.text.clear()
        account!!.setText(SharePrefHelper.getString("account"))
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun initView() {
        initToolBar("登录", true)
        account = find(R.id.login_tie_account)
        password = find(R.id.login_tie_pass)
        code = find(R.id.login_tie_code)
        countDown = find(R.id.login_tv_countdown)
        val login = find<Button>(R.id.login_btn_login)
        val clear = find<ImageView>(R.id.login_iv_account_clear)
        val agree = find<CheckBox>(R.id.login_cb_agree)

        agree.setOnCheckedChangeListener { _, b ->
            login.isClickable = b
            if (b) {
                if (!TextUtils.isEmpty(account!!.text) and !TextUtils.isEmpty(password.text)) {
                    login.background = resources.getDrawable(R.drawable.btn_bg)
                }
            } else {
                login.setBackgroundColor(resources.getColor(R.color.color_bebebe))
            }
        }
        agree.isChecked = true

        //
//        find<ImageView>(R.id.title_iv_back).setOnClickListener(this)
        countDown!!.setOnClickListener(this)
        login.setOnClickListener(this)
        clear.setOnClickListener(this)

        login.setBackgroundColor(resources.getColor(R.color.color_bebebe))
        login.isClickable = false
        account!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (TextUtils.isEmpty(p0)) {
                    clear.visibility = View.GONE
                } else {
                    clear.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(p0: Editable?) {
//                if (TextUtils.isEmpty(account!!.text.toString())) {//CommonUtils.isChinaPhoneLegal(account!!.text.toString())验证手机号
//                    login.setBackgroundColor(resources.getColor(R.color.color_bebebe))
//                    login.isClickable = false
//                }else {
//                    login.background = resources.getDrawable(R.drawable.btn_bg)
//                    login.isClickable = true
//                }
                if (TextUtils.isEmpty(account!!.text) or TextUtils.isEmpty(password.text) or !agree.isChecked) {
                    login.setBackgroundColor(resources.getColor(R.color.color_bebebe))
                    login.isClickable = false
                } else {
                    login.background = resources.getDrawable(R.drawable.btn_bg)
                    login.isClickable = true
                }
            }
        })
        password.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!TextUtils.isEmpty(account!!.text) and !TextUtils.isEmpty(password.text)) {
                    login.background = resources.getDrawable(R.drawable.btn_bg)
                    login.isClickable = true
                } else {
                    login.setBackgroundColor(resources.getColor(R.color.color_bebebe))
                    login.isClickable = false
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                if (TextUtils.isEmpty(account!!.text) or TextUtils.isEmpty(password.text) or !agree.isChecked) {
                    login.setBackgroundColor(resources.getColor(R.color.color_bebebe))
                    login.isClickable = false
                } else {
                    login.background = resources.getDrawable(R.drawable.btn_bg)
                    login.isClickable = true
                }
            }
        })
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.login_tv_countdown -> getMsg()
            R.id.login_btn_login -> doLogin()
            R.id.login_iv_account_clear -> {
                account!!.text.clear()
                password.text.clear()
            }
        }
        false
    }

    private fun getMsg() {
        if (!CommonUtils.isChinaPhoneLegal(account!!.text.toString())) {
            toast("请输入正确的手机号...")
            return
        }

        if (!CommonUtils.isNetWorkConnected(this)) {
            toast("网络未连接，请连接网络...")
            return
        }

        object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                countDown!!.text = (millisUntilFinished / 1000).toString() + "s 后重发"
                countDown!!.isEnabled = false
            }

            override fun onFinish() {
                countDown!!.text = "重新获取"
                countDown!!.isEnabled = true
            }
        }.start()
        receiverMsg()
    }

    private fun receiverMsg() {
        smsCode = CommonUtils.getRandomPSMSValidateCode()
        val content = String.format(getString(R.string.sms_content), smsCode)
        try {
            val smsURL = String.format(Constant.URL_SMS, account!!.text.toString(), URLEncoder.encode(content, "gbk"))
            OkGo.get(smsURL).tag(this).execute(object : StringCallback() {
                override fun onSuccess(s: String, call: Call, response: Response) {
                    receiveSMS(s)
                }
            })
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
    }

    private fun receiveSMS(result: String) {
        if (TextUtils.equals(result, "100")) {
            toast(R.string.msg_success)
        } else {
            toast(R.string.error_get_sms)
        }
    }

    private fun doLogin() {
//        if (TextUtils.isEmpty(code.text.toString())) {
//            toast("验证码不正确")
//            return
//        }
//
//        if (!TextUtils.equals(smsCode, code.text.toString())) {
//            toast("验证码不正确")
//            return
//        }
        if (TextUtils.isEmpty(account!!.text.toString())) {
            toast("登录名不能为空")
            return
        }
        if (TextUtils.isEmpty(password.text.toString())) {
            toast("密码不能为空")
            return
        }
        val loginRequest = LoginRequest(account!!.text.toString(), password.text.toString(), "false", "")

        OkGo.post(Constant.LOGIN_URL).tag(this)
                .upJson(Gson().toJson(loginRequest))
                .execute(object : StringDialogCallback(this) {
                    override fun onSuccess(t: String?, call: Call?, response: Response?) {
                        try {
                            val loginResponse = Gson().fromJson(t, LoginResponse::class.java)
                            if ("success" == loginResponse.status) {
                                startActivity<MainActivity>()
                                SharePrefHelper.put(Constant.LOGIN_IN, true)
                                SharePrefHelper.put(Constant.ACCOUNT, account!!.text.toString())
                                SharePrefHelper.put(Constant.APP_PROJECT_CODE, loginResponse.appprojectcode)
                            }
                            toast(loginResponse.message)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onError(call: Call?, response: Response?, e: Exception?) {
                        toast("服务访问错误...")
                        super.onError(call, response, e)
                    }
                })
    }

    data class LoginResponse(val status: String, val message: String, val appprojectcode: String)
    data class LoginRequest(val usercode: String, val password: String, val remember: String = "false", val city: String = "")
}