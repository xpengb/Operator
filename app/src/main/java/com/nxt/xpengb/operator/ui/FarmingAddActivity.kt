package com.nxt.xpengb.operator.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Handler
import android.support.v7.app.AlertDialog
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import com.lzy.okgo.OkGo
import com.lzy.okgo.callback.StringCallback
import com.nxt.xpengb.common.base.BaseActivity
import com.nxt.xpengb.common.okgo.StringDialogCallback
import com.nxt.xpengb.common.refresh.adapter.BaseRecyclerAdapter
import com.nxt.xpengb.common.refresh.adapter.SmartViewHolder
import com.nxt.xpengb.common.utils.SharePrefHelper
import com.nxt.xpengb.operator.R
import com.nxt.xpengb.operator.app.Constant
import com.nxt.xpengb.operator.app.OperatorApp
import com.nxt.xpengb.operator.model.Batch
import com.nxt.xpengb.operator.model.FarmingAdd
import com.nxt.xpengb.operator.model.FarmingAdd.ListBean.InsertedBean
import com.orhanobut.logger.Logger
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.GlideEngine
import com.zhihu.matisse.internal.entity.CaptureStrategy
import kotlinx.android.synthetic.main.activity_farming_add.*
import okhttp3.Call
import okhttp3.Response
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import top.zibin.luban.Luban
import top.zibin.luban.OnCompressListener
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

/**
 *   @author: xpengb@outlook.com
 *   @date: 2017/11/13
 *   @version: v1.0
 *   @desc:
 */
class FarmingAddActivity : BaseActivity() {
    private val REQUEST_CODE_CHOOSE = 10
    private lateinit var key: String
    private lateinit var imageRv: RecyclerView
    private var dateFormat = ""
    private var batchId = ""

    override fun getLayoutId(): Int {
        return R.layout.activity_farming_add
    }

    override fun initView() {
        initToolBar("农事发布", true)

        imageRv = find(R.id.text_timeline_image_list)
        imageRv.layoutManager = GridLayoutManager(this, 3)

        if(OperatorApp.location != null) {
            tv_location.text = OperatorApp.location!!.address
        }

        tv_batch.setOnClickListener {
            getBatch()
        }
        date.setOnClickListener {
            showDatePickDialog()
        }

        image_add.setOnClickListener {
            Matisse.from(this)
                    .choose(MimeType.ofImage())
                    .countable(true)
                    .capture(true)
                    .captureStrategy(CaptureStrategy(true, packageName))
                    .maxSelectable(3)
                    .gridExpectedSize(resources.getDimensionPixelSize(R.dimen.grid_expected_size))
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                    .thumbnailScale(0.85f)
                    .imageEngine(GlideEngine())
                    .forResult(REQUEST_CODE_CHOOSE)
        }

        btn_farmwork_add.setOnClickListener {
            farmingAdd()
        }


        getKey()
    }

    private fun getKey(){
        OkGo.get(Constant.KEY_URL)
                .execute(object : StringCallback() {
                    override fun onSuccess(t: String?, call: Call?, response: Response?) {
                        try {
                            key = t.toString()
                            Logger.i(key)
                        }catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                })
    }


    private fun showBatchDialog(batch: Batch) {
        val batchs = batch.rows.map { it.text + "\n批次号:" + it.value}.toTypedArray()

        val builder = AlertDialog.Builder(this)
        builder.setTitle("选择批次")
        builder.setItems(batchs, {
            _, i ->
            tv_batch.text = batchs[i]
            batchId = batch.rows[i].value
        })
        builder.show()
    }

    @SuppressLint("SetTextI18n")
    private fun showDatePickDialog() {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener {
                    _, p1, p2, p3 ->
                    date.text = "$p1 年 ${p2+1} 月 $p3 日"
                    dateFormat = "$p1-${p2+1}-$p3"
                 },
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE))
        datePickerDialog.show()
    }

    private fun getBatch() {
        OkGo.get(Constant.PRODUCTION_BATCH).tag(this)
                .params("_r", "0.531961542953719")
                .params("ProjectCode", SharePrefHelper.getString(Constant.APP_PROJECT_CODE))
                .params("_lookupType", "Fct.MaterialBath")
                .params("page", "1")
                .params("rows", "99")
                .execute(object : StringCallback() {
                    override fun onSuccess(t: String?, call: Call?, response: Response?) {
                        try {
                            val batch = Gson().fromJson(t, Batch::class.java)
                            if (batch != null) {
                                showBatchDialog(batch)
                            }
                        }catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                })
    }


    private fun farmingAdd () {
        val insert = InsertedBean()
        insert.id = key//key
        insert.landNumber = et_land_number.text.toString()
        insert.workarea = et_work_area.text.toString()
        insert.note = et_note.text.toString()
        insert.operator1 = et_operator.text.toString()
        insert.palntName = et_plant_name.text.toString()
        insert.varietyName = et_variety_name.text.toString()
        insert.projectCode = SharePrefHelper.getString(Constant.APP_PROJECT_CODE)
        insert.date = dateFormat
        insert.batchId = batchId
        val insertList = ArrayList<InsertedBean>()
        insertList.add(insert)

        val farmingAdd= FarmingAdd()
        val deleted = listOf<String>()
        val updated = listOf<String>()
        val list = FarmingAdd.ListBean(insertList, deleted, updated, true)
        farmingAdd.list = list

        Logger.i(Gson().toJson(farmingAdd))
        OkGo.post(Constant.FARMWORK_EDIT_URL).tag(this)//
                .upJson(Gson().toJson(farmingAdd))
                .execute(object : StringDialogCallback(this) {
                    override fun onSuccess(t: String?, call: Call?, response: Response?) {
                        try {
                            val addResponse = Gson().fromJson(t, AddResponse::class.java)
                            toast(addResponse.msg)
                            if (addResponse.success) {
                                doAsync { uploadImage() }
//                                Handler().postDelayed({
//                                    finish()
//                                }, 1500)
                            }

                        }catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun onError(call: Call?, response: Response?, e: Exception?) {
                        super.onError(call, response, e)
                        toast(e.toString())
                    }
                })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == Activity.RESULT_OK) {
            imageRv.adapter = object :BaseRecyclerAdapter<String>(Matisse.obtainPathResult(data), R.layout.item_image) {
                override fun onBindViewHolder(holder: SmartViewHolder?, model: String?, position: Int) {
                    val farmingIv = holder!!.itemView.find<ImageView>(R.id.iv_farming)
                    Glide.with(this@FarmingAddActivity).asBitmap()
                            .load(model)
                            .apply(RequestOptions().centerCrop()).into(farmingIv)
                }
            }

//            uploadImage(Matisse.obtainPathResult(data))
            compressImage(Matisse.obtainPathResult(data))
        }
    }

    private val imageFiles = arrayListOf<File>()
    private fun compressImage(photos: List<String>){
        Luban.with(this)
                .load(photos)
                .ignoreBy(100)
                .setCompressListener(object : OnCompressListener {
                    override fun onStart() {
                    }

                    override fun onSuccess(file: File?) {
                        imageFiles.add(file!!)
                    }

                    override fun onError(e: Throwable?) {
                    }
                }).launch()
    }

    private fun uploadImage( ) {
        OkGo.post(Constant.IMAGE_URL).tag(this)
                .params("farmWorkId", key)
                .params("ProjectCode", SharePrefHelper.getString(Constant.APP_PROJECT_CODE))
                .addFileParams("key", imageFiles)
                .execute(object : StringCallback() {
                    override fun onSuccess(t: String?, call: Call?, response: Response?) {
                        Logger.i("success" + t.toString())
                        finish()
                    }

                    override fun onError(call: Call?, response: Response?, e: Exception?) {
                        super.onError(call, response, e)
                        Logger.i("error" + e.toString())
                        finish()
                    }
                })
    }

    data class AddResponse(val success: Boolean, val msg: String, val error: String)
}
