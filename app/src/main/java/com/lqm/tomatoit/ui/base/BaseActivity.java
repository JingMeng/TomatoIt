package com.lqm.tomatoit.ui.base;

import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.lqm.tomatoit.R;
import com.lqm.tomatoit.app.App;
import com.lqm.tomatoit.widget.CustomDialog;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.ButterKnife;


public abstract class BaseActivity<V, T extends BasePresenter<V>> extends AutoLayoutActivity {


    protected T mPresenter;
    private CustomDialog mDialogWaiting;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.activities.add(this);

        init();

        //鍒ゆ柇鏄惁浣跨敤MVP妯″紡
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView((V) this);//鍥犱负涔嬪悗鎵€鏈夌殑瀛愮被閮借瀹炵幇瀵瑰簲鐨刅iew鎺ュ彛
        }

        //瀛愮被涓嶅啀闇€瑕佽缃竷灞€ID锛屼篃涓嶅啀闇€瑕佷娇鐢˙utterKnife.bind()
        setContentView(provideContentViewId());
        ButterKnife.bind(this);

        excuteStatesBar();

        initView();
        initData();
        initListener();
    }

    /**
     * 瑙ｅ喅4.4璁剧疆鐘舵€佹爮棰滆壊涔嬪悗锛屽竷灞€鍐呭宓屽叆鐘舵€佹爮浣嶇疆闂
     */
    private void excuteStatesBar(){
        ViewGroup mContentView = (ViewGroup) getWindow().findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            //娉ㄦ剰涓嶆槸璁剧疆 ContentView 鐨?FitsSystemWindows,
            // 鑰屾槸璁剧疆 ContentView 鐨勭涓€涓瓙 View 锛岄鐣欏嚭绯荤粺 View 鐨勭┖闂?
            mChildView.setFitsSystemWindows(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    //鍦╯etContentView()璋冪敤涔嬪墠璋冪敤锛屽彲浠ヨ缃甒indowFeature(濡傦細this.requestWindowFeature(Window.FEATURE_NO_TITLE);)
    public void init() {
    }

    public void initView() {
    }

    public void initData() {
    }

    public void initListener() {
    }

    //鐢ㄤ簬鍒涘缓Presenter鍜屽垽鏂槸鍚︿娇鐢∕VP妯″紡(鐢卞瓙绫诲疄鐜?
    protected abstract T createPresenter();

    //寰楀埌褰撳墠鐣岄潰鐨勫竷灞€鏂囦欢id(鐢卞瓙绫诲疄鐜?
    protected abstract int provideContentViewId();

    /**
     * 鏄剧ず绛夊緟鎻愮ず妗?     */
    public Dialog showWaitingDialog(String tip) {
        hideWaitingDialog();
        View view = View.inflate(this, R.layout.dialog_waiting, null);
        if (!TextUtils.isEmpty(tip))
            ((TextView) view.findViewById(R.id.tvTip)).setText(tip);
        mDialogWaiting = new CustomDialog(this, view, R.style.MyDialog);
        mDialogWaiting.show();
        mDialogWaiting.setCancelable(false);
        return mDialogWaiting;
    }

    /**
     * 闅愯棌绛夊緟鎻愮ず妗?     */
    public void hideWaitingDialog() {
        if (mDialogWaiting != null) {
            mDialogWaiting.dismiss();
            mDialogWaiting = null;
        }
    }

}

