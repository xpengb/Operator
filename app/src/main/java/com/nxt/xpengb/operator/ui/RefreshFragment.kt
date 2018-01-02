package com.nxt.xpengb.operator.ui

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.google.gson.Gson
import com.lzy.okgo.OkGo
import com.lzy.okgo.callback.StringCallback
import com.nxt.xpengb.base.BaseFragment
import com.nxt.xpengb.common.refresh.SmartRefreshLayout
import com.nxt.xpengb.common.refresh.adapter.BaseRecyclerAdapter
import com.nxt.xpengb.common.refresh.adapter.SmartViewHolder
import com.nxt.xpengb.common.refresh.api.RefreshLayout
import com.nxt.xpengb.common.refresh.listener.OnLoadmoreListener
import com.nxt.xpengb.common.refresh.listener.OnRefreshListener
import com.nxt.xpengb.common.utils.SharePrefHelper
import com.nxt.xpengb.common.widget.photoviewer.Utils
import com.nxt.xpengb.common.widget.photoviewer.ninegride.NineGridImageView
import com.nxt.xpengb.common.widget.photoviewer.ninegride.NineGridImageViewAdapter
import com.nxt.xpengb.operator.R
import com.nxt.xpengb.operator.app.Constant
import com.nxt.xpengb.operator.model.FarmWork
import com.nxt.xpengb.operator.util.DateTimeUtils
import com.nxt.xpengb.operator.util.VectorDrawableUtils
import com.nxt.xpengb.operator.widget.timelineview.TimelineView
import okhttp3.Call
import okhttp3.Response
import org.jetbrains.anko.find
import java.lang.Exception

/**
 *   @author: xpengb@outlook.com
 *   @date: 2017/11/14
 *   @version: v1.0
 *   @desc:
 */
open class RefreshFragment : BaseFragment(), OnRefreshListener, OnLoadmoreListener {
    private lateinit var refreshLayout: SmartRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BaseRecyclerAdapter<FarmWork.RowsBean>
    private var pageNo = 1
    private var isFirstEnter = true

    override fun getLayoutId(): Int {
        return R.layout.fragment_refresh
    }

//    override fun onResume() {
//        super.onResume()
//        refreshLayout.autoRefresh()
//    }

    override fun initView(view: View?, savedInstanceState: Bundle?) {
        getData(pageNo)

        refreshLayout = view!!.find(R.id.smartLayout)
        refreshLayout.setEnableHeaderTranslationContent(false)
        if (isFirstEnter) {
            isFirstEnter = false
            refreshLayout.autoRefresh()
        }

        recyclerView = view.find(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)
        recyclerView.itemAnimator = DefaultItemAnimator()

        refreshLayout.setOnRefreshListener(this)
        refreshLayout.setOnLoadmoreListener(this)

    }

    override fun onRefresh(refreshayout: RefreshLayout?) {
//        refreshLayout.finishRefresh(1500)
        pageNo = 1
        getData(pageNo)
    }

    override fun onLoadmore(refreshlayout: RefreshLayout?) {
        pageNo++
        getData(pageNo)
    }

    private fun getData(page: Int) {
        if (!SharePrefHelper.getBoolean("log_in")) {
            startActivity(Intent(activity, LoginActivity::class.java))
        }
        OkGo.get(Constant.FARMWORK_URL).tag(this)
                .params("page", page.toString())



                .params("rows", 10)
                .params("ProjectCode", "201306030001")
                .execute(object : StringCallback() {
                    override fun onSuccess(t: String?, call: Call?, response: Response?) {
                        try {
                            val farmwork = Gson().fromJson(t, FarmWork::class.java)
                            if (farmwork.rows.size == 0) {
                                refreshLayout.finishLoadmore()
                                toast("没有更多数据啦")
                                return
                            }

                            if (page == 1) {
                                adapter = object : BaseRecyclerAdapter<FarmWork.RowsBean>(farmwork.rows, R.layout.item_timeline) {
                                    override fun onBindViewHolder(holder: SmartViewHolder?, model: FarmWork.RowsBean?, position: Int) {
                                        val timeLineView = holder!!.itemView.find<TimelineView>(R.id.time_marker)
                                        val operator = holder.itemView.find<TextView>(R.id.text_timeline_operator)
                                        val date = holder.itemView.find<TextView>(R.id.text_timeline_date)
                                        val message = holder.itemView.find<TextView>(R.id.text_timeline_title)
                                        val nineImageView = holder.itemView.find<NineGridImageView>(R.id.nine_grid_image_view)
//                                        val imageListRv = holder.itemView.find<RecyclerView>(R.id.rv_image_list)
//                                        imageListRv.layoutManager = GridLayoutManager(activity, 3)
                                        timeLineView.initLine(holder.itemViewType)

                                        when (position) {
                                            0 -> timeLineView.setMarker(VectorDrawableUtils.getDrawable(activity, R.drawable.ic_marker_inactive, android.R.color.darker_gray))
                                            1 -> timeLineView.setMarker(VectorDrawableUtils.getDrawable(activity, R.drawable.ic_marker_active, R.color.colorPrimary))
//                                            else -> timeLineView.setMarker(VectorDrawableUtils.getDrawable(activity, R.drawable.ic_marker, R.color.colorPrimary))
                                        }

                                        if (model!!.operator1 != null) {
                                            operator.text = model.operator1
                                        }
                                        if (model.note != null) {
                                            message.text = model.note
                                        }
                                        if (model.date != null) {
                                            date.text = DateTimeUtils.parseDateTime(model.date, "yyyy-MM-dd'T'HH:mm", "hh:mm a, dd-MMM-yyyy")
                                        }

                                        if (model.farmWorkImgList != null) {
                                            val imagePathList = model.farmWorkImgList.split(",") as ArrayList<String>
                                            nineImageView.setGap(Utils.dip2px(activity, 4F))
                                            if (imagePathList != null && imagePathList.size > 0) {
                                                if (imagePathList.size == 1) {
                                                    nineImageView.setSingleImgSize(160, 160)
                                                }
                                                nineImageView.adapter = object : NineGridImageViewAdapter() {
                                                    override fun onDisplayImage(context: Context?, imageView: ImageView?, position: Int) {
                                                        Glide.with(context)
                                                                .asDrawable()
//                                                                .load(Constant.API_BASE + imagePathList[position])
                                                                .load("https://cdn.ruguoapp.com/FjtcJoC5CBoki6TQyQpSfBOIE-ZM.jpeg?imageView2/1/w/120/h/120/format/jpeg/q/80")
                                                                .thumbnail(.2f)
                                                                .transition(withCrossFade())
                                                                .into(object : SimpleTarget<Drawable>() {
                                                                    override fun onResourceReady(resource: Drawable?, transition: Transition<in Drawable>?) {
                                                                        imageView!!.setImageDrawable(resource)
                                                                    }
                                                                })
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                recyclerView.adapter = adapter
                            } else {
                                adapter.loadmore(farmwork.rows)
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                        if (refreshLayout.isRefreshing) {
                            refreshLayout.finishRefresh()
                        } else if (refreshLayout.isLoading) {
                            refreshLayout.finishLoadmore()
                        }
                    }
                })
    }
}