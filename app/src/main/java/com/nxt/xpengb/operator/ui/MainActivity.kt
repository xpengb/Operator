package com.nxt.xpengb.operator.ui

import android.Manifest
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import com.gyf.barlibrary.ImmersionBar
import com.lzy.okgo.OkGo
import com.lzy.okgo.callback.StringCallback
import com.nxt.xpengb.common.utils.SharePrefHelper
import com.nxt.xpengb.common.utils.TimeUtils
import com.nxt.xpengb.operator.R
import com.nxt.xpengb.operator.app.AtyContainer
import com.nxt.xpengb.operator.app.Constant
import com.nxt.xpengb.operator.app.OperatorApp
import com.nxt.xpengb.operator.model.HeWeather6
import kotlinx.android.synthetic.main.nav_header_main.*
import okhttp3.Call
import okhttp3.Response
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private val LOCATION_REQUEST = 100
    private lateinit var location: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE), LOCATION_REQUEST)
        }

        setContentView(R.layout.activity_main)
        ImmersionBar.with(this)
                .statusBarColor(R.color.colorPrimary)
                .init()

        val toolbar = find<Toolbar>(R.id.toolbar)
        toolbar.title = ""
        setSupportActionBar(toolbar)

        location = find(R.id.location)
        if (OperatorApp.location != null) {
            location.text = OperatorApp.location!!.city
        }

        val fab = find<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
//                            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                                    .setAction("Action", null).show();
            startActivity(Intent(this@MainActivity, FarmingAddActivity::class.java))
        }

        val drawer = find<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = find<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

       beginTransactionFragment()

        doAsync { getWeather() }
    }

    private fun beginTransactionFragment() {
        val refreshFragment = MainListFragment()//RefreshFragment()
        val beginTransaction = supportFragmentManager.beginTransaction()
//        if (!beginTransaction.isEmpty) {
//            beginTransaction.remove(refreshFragment)
//        }
        beginTransaction.add(R.id.container, refreshFragment).commit()
    }

    override fun onBackPressed() {
        val drawer = find<DrawerLayout>(R.id.drawer_layout)
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        when (id) {
            R.id.nav_camera -> {
//                startActivity<WeatherActivity>()
//                beginTransactionFragment()
            }
//            R.id.nav_gallery -> {
//
//            }
//            R.id.nav_slideshow -> {
//
//            }
//            R.id.nav_manage -> {
//
//            }
            R.id.nav_share -> {
                switchAccount()
            }
            R.id.nav_send -> {
                closeApp()
            }
        }

        val drawer = find<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (OperatorApp.location != null) {
                    location.text = OperatorApp.location!!.city
                }
            } else {
                Toast.makeText(this, "请授权相关权限", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getWeather() {
        val weatherApi: String = Constant.HE_WEATHER_API.plus(OperatorApp.location!!.city)
        OkGo.get(weatherApi).tag(this)
                .execute(object : StringCallback() {
                    override fun onSuccess(t: String?, call: Call?, response: Response?) {
                        val heWeather = Gson().fromJson(t, HeWeather6::class.java)
                        tv_city_name.text = heWeather.heWeather6[0].basic.location
                        tv_weather_string.text = heWeather.heWeather6[0].daily_forecast[0].cond_txt_d
                        tv_weather_aqi.text = heWeather.heWeather6[0].daily_forecast[0].wind_dir +
                                heWeather.heWeather6[0].daily_forecast[0].wind_sc + "级"
                        tv_temp.text = String.format("%s℃", heWeather.heWeather6[0].daily_forecast[0].tmp_min)
                        val updateTime = TimeUtils.string2String(heWeather.heWeather6[0].update.loc,
                                SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()),
                                SimpleDateFormat("HH:mm", Locale.getDefault()))
                        tv_update_time.text = String.format("截止 %s", updateTime)
                    }
                })
    }

    private fun switchAccount() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("提示")
        builder.setMessage("切换账号")
        builder.setOnCancelListener {
            toast("已取消")
        }
        builder.setNegativeButton("取消", { dialogInterface, _ -> dialogInterface.dismiss() })
        builder.setPositiveButton("切换", { _, _ ->
            val progressDialog = ProgressDialog(this)
            progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
            progressDialog.setMessage("请求网络中...")
            progressDialog.show()
            Handler().postDelayed({
                progressDialog.dismiss()
                SharePrefHelper.put("log_in", false)
                startActivity<LoginActivity>()
            }, 1500)
        })
        builder.show()
    }

    private fun closeApp() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("提示")
        builder.setMessage("退出应用")
        builder.setOnCancelListener {
            toast("已取消")
        }

        builder.setNegativeButton("取消", { dialogInterface, _ -> dialogInterface.dismiss() })
        builder.setPositiveButton("退出", { _, _ ->
            AtyContainer.finishAllActivity()
            finish()
            System.exit(0)
            moveTaskToBack(false)
        })
        builder.show()
    }
}
