package com.nxt.xpengb.operator.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.List;

/**
 * Create : xpengb@outlook.com
 * Date   : 2017/4/9
 * Version: V1.0
 * Desc   :
 */

public class ViewPagerAdapter extends PagerAdapter {
    private List<View> views;       //创建一个集合，用来承载所有的view
    private Context context;        //承接上下文

    public ViewPagerAdapter(List<View> views, Context context){

        this.views = views;
        this.context = context;
    }

    //销毁不需要的view
    @Override
    public void destroyItem(View container, int position, Object object) {
        ((ViewPager)container).removeView(views.get(position));
    }

    //加载view的方法
    @Override
    public Object instantiateItem(View container, int position) {
        ((ViewPager)container).addView(views.get(position));
        return views.get(position);
    }

    //返回view的数量
    @Override
    public int getCount() {
        return views.size();
    }

    //判断当前view是否为需要的对象
    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return (arg0 == arg1);
    }
}
