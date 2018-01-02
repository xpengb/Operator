package com.nxt.xpengb.common.base

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.nxt.xpengb.R
import com.nxt.xpengb.common.agentweb.AgentWeb
import com.nxt.xpengb.common.agentweb.ChromeClientCallbackManager
import com.nxt.xpengb.common.agentweb.WebDefaultSettingsManager
import org.jetbrains.anko.find

/**
 *   Create : xpengb@outlook.com
 *   Date   : 2017/6/29
 *   Version: V1.0
 *   Desc   :
 */
class AgentWebFragment : Fragment() {

    private var agentWeb: AgentWeb? = null
    private var titleTv: TextView? = null
    private var backIv: ImageView? = null
    private var finishIv: ImageView? = null
    private var lineView: View? = null
    private var url: String? = null

    companion object {
        fun getInstance(url: String): AgentWebFragment {
            val agentWebFragment = AgentWebFragment()
            val bundle = Bundle()
            bundle.putString("url", url)
            agentWebFragment.arguments = bundle

            return agentWebFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_agentweb, container, false)
        url = arguments.getString("url")
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        agentWeb = AgentWeb.with(this)
                .setAgentWebParent(view as ViewGroup, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))//
                .setIndicatorColorWithHeight(-1, 2)
                .setWebSettings(getSettings())
                .setWebViewClient(webViewClient)
                .setReceivedTitleCallback(callback)
                .setSecurityType(AgentWeb.SecurityType.strict)
                .createAgentWeb()
                .ready()
                .go(url)

        initView(view)
    }

    fun getSettings() = WebDefaultSettingsManager.getInstance()!!

    var callback: ChromeClientCallbackManager.ReceivedTitleCallback = ChromeClientCallbackManager.ReceivedTitleCallback { _, title ->
        var title = title
        if (titleTv != null && !TextUtils.isEmpty(title))
            if (title.length > 10)
                title = title.substring(0, 10) + "..."
        titleTv!!.text = title
    }

    var webViewClient: WebViewClient = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            return false
        }

        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
            if (url == url) {
                pageNavigator(View.GONE)
            } else {
                pageNavigator(View.VISIBLE)
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        agentWeb!!.uploadFileResult(requestCode, resultCode, data)
    }

    fun initView(view: View) {
        backIv = view.find(R.id.iv_back)
        lineView = view.find(R.id.view_line)

        finishIv = view.find(R.id.iv_finish)
        titleTv = view.find(R.id.toolbar_title)

        backIv!!.setOnClickListener(mOnClickListener)
        finishIv!!.setOnClickListener(mOnClickListener)

        pageNavigator(View.GONE)
    }

    private fun pageNavigator(tag: Int) {
        backIv!!.visibility = tag
        lineView!!.visibility = tag
    }

    internal var mOnClickListener: View.OnClickListener = View.OnClickListener { v ->
        when (v.id) {
            R.id.iv_back ->
                if (!agentWeb!!.back())
                    this@AgentWebFragment.activity.finish()
            R.id.iv_finish -> this@AgentWebFragment.activity.finish()
        }
    }

    override fun onResume() {
        agentWeb!!.webLifeCycle.onResume()
        super.onResume()
    }

    override fun onPause() {
        agentWeb!!.webLifeCycle.onPause()
        super.onPause()
    }

    override fun onDestroyView() {
        agentWeb!!.webLifeCycle.onDestroy()
        super.onDestroyView()
    }
}