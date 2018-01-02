package com.nxt.xpengb.operator.ui.adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.nxt.xpengb.common.base.baseadapter.BaseQuickAdapter;
import com.nxt.xpengb.common.base.baseadapter.BaseViewHolder;
import com.nxt.xpengb.operator.R;
import com.nxt.xpengb.operator.app.OperatorApp;
import com.nxt.xpengb.operator.model.HeWeather;

import java.util.List;

/**
 * Created by liyu on 2017/4/1.
 */

public class SuggestionAdapter extends BaseQuickAdapter<Object, BaseViewHolder> {

    public SuggestionAdapter(int layoutResId, List<Object> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, Object item, int position) {
        int width = getScreenWidth() / 4;
        holder.itemView.setLayoutParams(new LinearLayout.LayoutParams(width, width));
        if (item instanceof HeWeather.SuggestionBean.AirBean) {
            holder.setText(R.id.tvName, "空气");
            holder.setText(R.id.tvMsg, ((HeWeather.SuggestionBean.AirBean) item).getBrf());
        } else if (item instanceof HeWeather.SuggestionBean.ComfBean) {
            holder.setText(R.id.tvName, "舒适度");
            holder.setText(R.id.tvMsg, ((HeWeather.SuggestionBean.ComfBean) item).getBrf());
        } else if (item instanceof HeWeather.SuggestionBean.CwBean) {
            holder.setText(R.id.tvName, "洗车");
            holder.setText(R.id.tvMsg, ((HeWeather.SuggestionBean.CwBean) item).getBrf());
        } else if (item instanceof HeWeather.SuggestionBean.DrsgBean) {
            holder.setText(R.id.tvName, "穿衣");
            holder.setText(R.id.tvMsg, ((HeWeather.SuggestionBean.DrsgBean) item).getBrf());
        } else if (item instanceof HeWeather.SuggestionBean.FluBean) {
            holder.setText(R.id.tvName, "感冒");
            holder.setText(R.id.tvMsg, ((HeWeather.SuggestionBean.FluBean) item).getBrf());
        } else if (item instanceof HeWeather.SuggestionBean.SportBean) {
            holder.setText(R.id.tvName, "运动");
            holder.setText(R.id.tvMsg, ((HeWeather.SuggestionBean.SportBean) item).getBrf());
        } else if (item instanceof HeWeather.SuggestionBean.TravBean) {
            holder.setText(R.id.tvName, "旅游");
            holder.setText(R.id.tvMsg, ((HeWeather.SuggestionBean.TravBean) item).getBrf());
        } else if (item instanceof HeWeather.SuggestionBean.UvBean) {
            holder.setText(R.id.tvName, "紫外线");
            holder.setText(R.id.tvMsg, ((HeWeather.SuggestionBean.UvBean) item).getBrf());
        } else if (item instanceof HeWeather.AqiBean) {
            holder.setText(R.id.tvName, "空气指数");
            holder.setText(R.id.tvMsg, ((HeWeather.AqiBean) item).getCity().getAqi());
        }
    }

    public static int getScreenWidth() {
        WindowManager windowManager = (WindowManager) OperatorApp.Companion.getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();// 创建了一张白纸
        windowManager.getDefaultDisplay().getMetrics(dm);// 给白纸设置宽高
        return dm.widthPixels;
    }
}
