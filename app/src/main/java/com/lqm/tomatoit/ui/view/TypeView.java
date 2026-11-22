package com.lqm.tomatoit.ui.view;

import com.google.android.material.tabs.TabLayout;

import com.lqm.tomatoit.model.pojo.ArticleBean;
import com.lqm.tomatoit.ui.adapter.ArticleListAdapter;
import com.lqm.tomatoit.widget.AutoLinefeedLayout;

import java.util.List;

/**
 * user锛歭qm
 * desc锛氬垎绫籚iew
 */

public interface TypeView {

    TabLayout getTabLayout();
    AutoLinefeedLayout getTagLayout();
    ArticleListAdapter getAdapter();

    void getDataError(String message);
    void getRefreshDataSuccess(List<ArticleBean> data);
    void getMoreDataSuccess(List<ArticleBean> data);

}

