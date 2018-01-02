package com.nxt.xpengb.operator.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.nxt.xpengb.base.BaseFragment;
import com.nxt.xpengb.common.refresh.adapter.BaseRecyclerAdapter;
import com.nxt.xpengb.common.refresh.adapter.SmartViewHolder;
import com.nxt.xpengb.common.refresh.api.RefreshLayout;
import com.nxt.xpengb.common.refresh.listener.OnRefreshLoadmoreListener;
import com.nxt.xpengb.common.utils.SharePrefHelper;
import com.nxt.xpengb.common.widget.photoviewer.PictureActivity;
import com.nxt.xpengb.common.widget.photoviewer.PictureData;
import com.nxt.xpengb.common.widget.photoviewer.Utils;
import com.nxt.xpengb.common.widget.photoviewer.ninegride.NineGridImageView;
import com.nxt.xpengb.common.widget.photoviewer.ninegride.NineGridImageViewAdapter;
import com.nxt.xpengb.operator.R;
import com.nxt.xpengb.operator.app.Constant;
import com.nxt.xpengb.operator.model.FarmWork;
import com.nxt.xpengb.operator.util.DateTimeUtils;
import com.nxt.xpengb.operator.util.VectorDrawableUtils;
import com.nxt.xpengb.operator.widget.timelineview.TimelineView;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * @author: xpengb@outlook.com
 * @date: 2017/12/5
 * @version: v1.0
 * @desc:
 */

public class MainListFragment extends BaseFragment implements OnRefreshLoadmoreListener {
    private RefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private BaseRecyclerAdapter<FarmWork.RowsBean> adapter;
    private int pageNo = 1;
    private boolean firstEnter = true;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_refresh;
    }

    @Override
    public void initView(@Nullable View view, @Nullable Bundle savedInstanceState) {
        refreshLayout = (RefreshLayout) view.findViewById(R.id.smartLayout);
        refreshLayout.setEnableAutoLoadmore(false);
        if (firstEnter) {
            firstEnter = false;
            refreshLayout.autoRefresh();
        }

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        refreshLayout.setOnRefreshLoadmoreListener(this);
        refreshLayout.setOnLoadmoreListener(this);

    }


    @Override
    public void onRefresh(RefreshLayout refreshayout) {
        pageNo = 1;
        getData(pageNo);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        pageNo++;
        getData(pageNo);
    }

    private void getData(final int page) {
        if (!SharePrefHelper.getBoolean(Constant.LOGIN_IN)) {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
        OkGo.get(Constant.FARMWORK_URL).tag(this)
                .params("page", page + "")
                .params("rows", 10)
                .params("ProjectCode", "201306030001")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            FarmWork farmWork = new Gson().fromJson(s, FarmWork.class);
                            if (farmWork.getRows().size() == 0) {
                                refreshLayout.finishLoadmore();
                                Toast.makeText(getContext(), "没有更多数据啦", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            bindViewAndData(page, farmWork);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (refreshLayout.isRefreshing()) {
                            refreshLayout.finishRefresh();
                        } else if (refreshLayout.isLoading()) {
                            refreshLayout.finishLoadmore();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                        if (refreshLayout.isRefreshing()) {
                            refreshLayout.finishRefresh();
                        }
                    }
                });
    }

    private void bindViewAndData(int page, FarmWork farmWork) {
        if (page == 1) {
            adapter = new BaseRecyclerAdapter<FarmWork.RowsBean>(farmWork.getRows(), R.layout.item_timeline) {
                @Override
                protected void onBindViewHolder(final SmartViewHolder holder, FarmWork.RowsBean model, int position) {
                    TimelineView timelineView = (TimelineView) holder.itemView.findViewById(R.id.time_marker);
                    TextView operator = (TextView) holder.itemView.findViewById(R.id.text_timeline_operator);
                    TextView date = (TextView) holder.itemView.findViewById(R.id.text_timeline_date);
                    TextView message = (TextView) holder.itemView.findViewById(R.id.text_timeline_title);
                    final NineGridImageView nineGridImageView = (NineGridImageView) holder.itemView.findViewById(R.id.nine_grid_image_view);
                    timelineView.initLine(holder.getItemViewType());

                    switch (position) {
                        case 0:
                            timelineView.setMarker(VectorDrawableUtils.getDrawable(getContext(), R.drawable.ic_marker_inactive, android.R.color.darker_gray));
                            break;
                        case 1:
                            timelineView.setMarker(VectorDrawableUtils.getDrawable(getContext(), R.drawable.ic_marker_active, R.color.colorPrimary));
                            break;
                        default:
//                            timelineView.setMarker(VectorDrawableUtils.getDrawable(getContext(), R.drawable.ic_marker, R.color.colorPrimary));
                            break;
                    }

                    if (model.getOperator1() != null) {
                        operator.setText(model.getOperator1());
                    }
                    if (model.getNote() != null) {
                        message.setText(model.getNote());
                    }
                    if (model.getDate() != null) {
                        date.setText(DateTimeUtils.parseDateTime(model.getDate(), "yyyy-MM-dd'T'HH:mm", "hh:mm a, dd-MMM-yyyy"));
                    }
                    if (model.getFarmWorkImgList() != null) {
                        final String[] imagePathList = model.getFarmWorkImgList().split(",");
                        nineGridImageView.setGap(Utils.dip2px(getContext(), 4F));
                        if (imagePathList.length > 0) {
                            if (imagePathList.length == 1) {
                                nineGridImageView.setSingleImgSize(160, 160);
                            }
                            nineGridImageView.setAdapter(new NineGridImageViewAdapter() {
                                String imageUrl = "https://cdn.ruguoapp.com/FjtcJoC5CBoki6TQyQpSfBOIE-ZM.jpeg?imageView2/1/w/120/h/120/format/jpeg/q/80";
                                @Override
                                protected void onDisplayImage(Context context, final ImageView imageView, int position) {
                                    Glide.with(context)
                                            .asDrawable()
//                                          .load(Constant.API_BASE + imagePathList[position])
                                            .load(imageUrl)
                                            .thumbnail(.2f)
                                            .transition(withCrossFade())
                                            .into(new SimpleTarget<Drawable>() {
                                                @Override
                                                public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                                                    imageView.setImageDrawable(resource);
                                                }
                                            });
                                }

                                @Override
                                protected void onItemImageClick(Context context, ImageView imageView, int position) {
                                    super.onItemImageClick(context, imageView, position);
                                    ArrayList<PictureData> arrayList = new ArrayList<>();
                                    for (int i = 0; i < imagePathList.length; i ++) {
                                        ImageView view = (ImageView) nineGridImageView.getChildAt(i);
                                        PictureData pictureData = new PictureData();
                                        pictureData.location = new int[2];
                                        view.getLocationOnScreen(pictureData.location);
                                        pictureData.matrixValue = new float[9];
                                        view.getImageMatrix().getValues(pictureData.matrixValue);
                                        pictureData.size = new int[]{view.getWidth(), view.getHeight()};
                                        pictureData.url = imageUrl;
                                        pictureData.originalUrl = imageUrl;
                                        pictureData.imageSize = new int[]{1500, 1000};
                                        arrayList.add(pictureData);
                                    }
                                    PictureActivity.start(getContext(), arrayList, position);
                                }
                            });
                            nineGridImageView.setImagesCount(imagePathList.length);
                        } else {
                            nineGridImageView.setVisibility(View.GONE);
                        }
                    }
                }
            };
            recyclerView.setAdapter(adapter);
        } else {
            adapter.loadmore(farmWork.getRows());
        }
    }
}
