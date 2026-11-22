package com.lqm.tomatoit.ui.base;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import io.reactivex.annotations.Nullable;

/**
 * user锛歭qm
 * desc锛欱aseFragment
 */

public abstract class BaseFragment<V, T extends BasePresenter<V>> extends Fragment {

    protected T mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();

        //鍒ゆ柇鏄惁浣跨敤MVP妯″紡
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView((V) this);//鍥犱负涔嬪悗鎵€鏈夌殑瀛愮被閮借瀹炵幇瀵瑰簲鐨刅iew鎺ュ彛
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(provideContentViewId(), container, false);
        ButterKnife.bind(this, rootView);
        initView(rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initListener();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    public void init() {

    }

    public void initView(View rootView) {
    }

    public void initData() {

    }

    public void initListener() {

    }


    //鐢ㄤ簬鍒涘缓Presenter鍜屽垽鏂槸鍚︿娇鐢∕VP妯″紡(鐢卞瓙绫诲疄鐜?
    protected abstract T createPresenter();

    //寰楀埌褰撳墠鐣岄潰鐨勫竷灞€鏂囦欢id(鐢卞瓙绫诲疄鐜?
    protected abstract int provideContentViewId();
}

