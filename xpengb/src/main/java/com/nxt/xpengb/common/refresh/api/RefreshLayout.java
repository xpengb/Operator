package com.nxt.xpengb.common.refresh.api;

import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.view.animation.Interpolator;

import com.nxt.xpengb.common.refresh.constant.RefreshState;
import com.nxt.xpengb.common.refresh.listener.OnLoadmoreListener;
import com.nxt.xpengb.common.refresh.listener.OnMultiPurposeListener;
import com.nxt.xpengb.common.refresh.listener.OnRefreshListener;
import com.nxt.xpengb.common.refresh.listener.OnRefreshLoadmoreListener;

/**
 * 刷新布局
 * Created by SCWANG on 2017/5/26.
 */

public interface RefreshLayout {

    RefreshLayout setFooterHeight(float dp);

    RefreshLayout setFooterHeight(int px);

    RefreshLayout setHeaderHeight(float dp);

    RefreshLayout setHeaderHeight(int px);

    /**
     * 设置下拉最大高度和Header高度的比率（将会影响可以下拉的最大高度）
     */
    RefreshLayout setHeaderMaxDragRate(float rate);

    /**
     * 设置上啦最大高度和Footer高度的比率（将会影响可以上啦的最大高度）
     */
    RefreshLayout setFooterMaxDragRate(float rate);

    /**
     * 设置回弹显示插值器
     */
    RefreshLayout setReboundInterpolator(Interpolator interpolator);

    /**
     * 设置回弹动画时长
     */
    RefreshLayout setReboundDuration(int duration);

    /**
     * 设置是否启用上啦加载更多（默认启用）
     */
    RefreshLayout setEnableLoadmore(boolean enable);

    /**
     * 是否启用下拉刷新（默认启用）
     */
    RefreshLayout setEnableRefresh(boolean enable);

    /**
     * 设置是否启在下拉Header的同事下拉内容
     */
    RefreshLayout setEnableHeaderTranslationContent(boolean enable);

    /**
     * 设置是否启在上拉Footer的同事上拉内容
     */
    RefreshLayout setEnableFooterTranslationContent(boolean enable);

    /**
     * 设置是否开启在刷新时候禁止操作内容视图
     */
    RefreshLayout setDisableContentWhenRefresh(boolean disable);

    /**
     * 设置是否开启在加载时候禁止操作内容视图
     */
    RefreshLayout setDisableContentWhenLoading(boolean disable);

    /**
     * 设置是否监听列表在滚动到底部时触发加载事件（默认true）
     */
    RefreshLayout setEnableAutoLoadmore(boolean enable);

    /**
     * 设置数据全部加载完成，将不能再次触发加载功能
     */
    RefreshLayout setLoadmoreFinished(boolean finished);

    /**
     * 设置指定的Header
     */
    RefreshLayout setRefreshFooter(RefreshFooter bottom);

    /**
     * 设置是否启用越界回弹
     */
    RefreshLayout setEnableOverScrollBounce(boolean enable);

    /**
     * 设置指定的Header
     */
    RefreshLayout setRefreshHeader(RefreshHeader header);

    /**
     * 单独设置刷新监听器
     */
    RefreshLayout setOnRefreshListener(OnRefreshListener listener);

    /**
     * 单独设置加载监听器
     */
    RefreshLayout setOnLoadmoreListener(OnLoadmoreListener listener);

    /**
     * 同时设置刷新和加载监听器
     */
    RefreshLayout setOnRefreshLoadmoreListener(OnRefreshLoadmoreListener listener);

    /**
     * 设置多功能监听器
     */
    RefreshLayout setOnMultiPurposeListener(OnMultiPurposeListener listener);

    /**
     * 设置主题颜色
     */
    RefreshLayout setPrimaryColorsId(@ColorRes int... primaryColorId);

    /**
     * 设置主题颜色
     */
    RefreshLayout setPrimaryColors(int... colors);

    /**
     * 完成刷新
     */
    RefreshLayout finishRefresh();

    /**
     * 完成加载
     */
    RefreshLayout finishLoadmore();

    /**
     * 完成刷新
     */
    RefreshLayout finishRefresh(int delayed);

    /**
     * 完成加载
     */
    RefreshLayout finishLoadmore(int delayed);

    /**
     * 获取当前 Header
     */
    @Nullable
    RefreshHeader getRefreshHeader();

    /**
     * 获取当前 Footer
     */
    @Nullable
    RefreshFooter getRefreshFooter();

    /**
     * 获取当前状态
     */
    RefreshState getState();

    /**
     * 获取实体布局视图
     */
    ViewGroup getLayout();

    /**
     * 是否正在刷新
     */
    boolean isRefreshing();

    /**
     * 是否正在加载
     */
    boolean isLoading();

    /**
     * 自动刷新
     */
    boolean autoRefresh();

    /**
     * 自动刷新
     */
    boolean autoRefresh(int delayed);

    /**
     * 自动刷新
     */
    boolean autoRefresh(int delayed, float dragrate);

    /**
     * 自动加载
     */
    boolean autoLoadmore();

    /**
     * 自动加载
     */
    boolean autoLoadmore(int delayed);

    /**
     * 自动加载
     */
    boolean autoLoadmore(int delayed, float dragrate);

    boolean isEnableRefresh();

    boolean isEnableLoadmore();

    boolean isLoadmoreFinished();

    boolean isEnableAutoLoadmore();

    boolean isEnableOverScrollBounce();
}
