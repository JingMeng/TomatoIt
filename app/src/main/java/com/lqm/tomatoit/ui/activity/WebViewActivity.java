package com.lqm.tomatoit.ui.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.widget.NestedScrollView;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lqm.tomatoit.R;
import com.lqm.tomatoit.ui.base.BaseActivity;
import com.lqm.tomatoit.ui.presenter.WebViewPresenter;
import com.lqm.tomatoit.ui.view.CommonWebView;
import com.lqm.tomatoit.util.ActivityUtils;
import com.lqm.tomatoit.util.SharesUtils;
import com.lqm.tomatoit.util.UIUtils;
import com.lqm.tomatoit.widget.CustomPopWindow;
import com.lqm.tomatoit.widget.IconFontTextView;
import com.lqm.tomatoit.widget.WebViewFragment;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * user锛歭qm
 * desc锛歐ebView鐣岄潰锛屽姞杞芥枃绔犺鎯呯瓑
 * (tip:涓婃粦闅愯棌topbar,鍥犱负webview鐒︾偣闂锛岃繖閲屽湪甯冨眬涓敤NestedScrollView锛屽啀鍔ㄦ€佹坊鍔爓ebview)
 */

public class WebViewActivity extends BaseActivity<CommonWebView, WebViewPresenter>
        implements CommonWebView {

    public static final String WEB_URL = "web_url";

    @Bind(R.id.progress_bar)
    ProgressBar progressBar;
    @Bind(R.id.tv_return)
    IconFontTextView tvReturn;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_other)
    IconFontTextView tvOther;
    @Bind(R.id.rl_topbar_layout)
    RelativeLayout rlTopbarLayout;
    @Bind(R.id.webview_container)
    NestedScrollView webViewContainer;


    private String mUrl;
    private CustomPopWindow mMorePopWindow;
    private WebViewFragment mWebViewFragment;
    private WebView webView;

    public static void runActivity(Context context, String url) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(WEB_URL, url);
        context.startActivity(intent);
    }

    @Override
    protected WebViewPresenter createPresenter() {
        return new WebViewPresenter();
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_webview;
    }

    @Override
    public ProgressBar getProgressBar() {
        return progressBar;
    }

    @Override
    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    @Override
    public void init() {
        mUrl = getIntent().getStringExtra(WEB_URL);
    }

    @Override
    public void initView() {
        tvOther.setText(UIUtils.getString(R.string.ic_more));

        mWebViewFragment = new WebViewFragment();
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mWebViewFragment, R.id.webview_container);

    }

    @Override
    protected void onStart() {
        super.onStart();

        //鑾峰彇webview
        webView = mWebViewFragment.getWebView();
        mPresenter.setWebView(webView, mUrl);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.destroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();
                return true;
            } else {
                finish();//閫€鍑虹▼搴?                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    @OnClick({R.id.tv_return, R.id.tv_other})

    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_return:
                finish();
                break;
            case R.id.tv_other:
                //鏇村鎸夐挳
                View popView = View.inflate(WebViewActivity.this, R.layout.popup_webview_more, null);
                mMorePopWindow = new CustomPopWindow.PopupWindowBuilder(this)
                        .setView(popView)
                        .enableBackgroundDark(false) //寮瑰嚭popWindow鏃讹紝鑳屾櫙鏄惁鍙樻殫
                        .create()
                        .showAsDropDown(tvOther, -430, -10);

                //鍒嗕韩
                popView.findViewById(R.id.tv_shape).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharesUtils.share(WebViewActivity.this, webView.getUrl());
                        mMorePopWindow.dissmiss();
                    }
                });
                //澶嶅埗閾炬帴
                popView.findViewById(R.id.tv_copy_link).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ClipboardManager cmd = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        cmd.setPrimaryClip(ClipData.newPlainText(getString(R.string.copy_link), webView.getUrl()));
                        Snackbar.make(getWindow().getDecorView(), R.string.copy_link_success, Snackbar.LENGTH_SHORT).show();
                        mMorePopWindow.dissmiss();
                    }
                });
                //浣跨敤绯荤粺娴忚鍣ㄦ墦寮€
                popView.findViewById(R.id.tv_open_out).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(webView.getUrl()));
                        startActivity(intent);
                        mMorePopWindow.dissmiss();
                    }
                });
                break;
        }
    }
}

