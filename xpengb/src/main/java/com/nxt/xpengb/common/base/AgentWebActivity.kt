package com.nxt.xpengb.common.base

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.KeyEvent
import android.widget.LinearLayout
import android.widget.TextView
import com.nxt.xpengb.R
import com.nxt.xpengb.common.agentweb.AgentWeb
import com.nxt.xpengb.common.agentweb.ChromeClientCallbackManager
import com.nxt.xpengb.common.agentweb.LogUtils
import com.nxt.xpengb.common.view.translucentbar.TranslucentBarManager
import org.jetbrains.anko.find

/**
 *   Create : xpengb@outlook.com
 *   Date   : 2017/6/28
 *   Version: V1.0
 *   Desc   :
 */
class AgentWebActivity : AppCompatActivity() {
    private var agentWeb: AgentWeb? = null
    private var titleTv: TextView? = null
    private var alertDialog: AlertDialog? = null
    private var webTitle: String? = null

    companion object {
        fun startActivity(context: Context, title: String, url: String) {
            val intent = Intent(context, AgentWebActivity::class.java)
            intent.putExtra("title", title)
            intent.putExtra("url", url)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        val linearLayout = find<LinearLayout>(R.id.web_container)
        val toolBar = find<Toolbar>(R.id.web_toolbar)
        toolBar.setTitleTextColor(Color.WHITE)
        toolBar.title = ""
        titleTv = find<TextView>(R.id.web_title)
        setSupportActionBar(toolBar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
        toolBar.setNavigationOnClickListener {
            showDialog()
        }

        webTitle = intent.getStringExtra("title")
        val url = intent.getStringExtra("url")

//        val p = System.currentTimeMillis()

        agentWeb = AgentWeb.with(this)
                .setAgentWebParent(linearLayout, LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .defaultProgressBarColor()
                .setReceivedTitleCallback(mCallback)
                .setSecutityType(AgentWeb.SecurityType.strict)
                .createAgentWeb()
                .ready()
                .go(url)

//        agentWeb!!.loader.loadUrl(url)

//        val n = System.currentTimeMillis()
    }

    private val mCallback = ChromeClientCallbackManager.ReceivedTitleCallback { _, title ->
        if (TextUtils.isEmpty(webTitle)) {
            if (title != null)
                this.titleTv!!.text = title
        }else {
            titleTv!!.text = webTitle
        }



    }

    fun showDialog() {
        alertDialog = AlertDialog.Builder(this)
                .setMessage("您确定要关闭该页面吗?")
                .setNegativeButton("再逛逛") { _, _ ->
                    if (alertDialog != null)
                        alertDialog!!.dismiss()
                }
                .setPositiveButton("确定") { _, _ ->
                    if (alertDialog != null)
                        alertDialog!!.dismiss()

                    this@AgentWebActivity.finish()
                }.create()
        alertDialog!!.show()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (agentWeb!!.handleKeyEvent(keyCode, event)) {
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onPause() {
        agentWeb!!.webLifeCycle.onPause()
        super.onPause()

    }

    override fun onResume() {
        agentWeb!!.webLifeCycle.onResume()
        super.onResume()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {

        LogUtils.i("Info", "result:$requestCode result:$resultCode")
        agentWeb!!.uploadFileResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }


    override fun onDestroy() {
        super.onDestroy()
        agentWeb!!.webLifeCycle.onDestroy()
    }
}