package com.nxt.xpengb.common.refresh.constant;

public enum RefreshState {
    None,
    PullDownToRefresh, PullToUpLoad,
    PullDownCanceled, PullUpCanceled,
    ReleaseToRefresh, ReleaseToLoad,
    Refreshing, Loading,
    RefreshFinish, LoadingFinish,
}