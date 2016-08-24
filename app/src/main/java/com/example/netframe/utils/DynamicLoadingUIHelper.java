package com.example.netframe.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

/**
 * @author Zeng zhiqiang
 */
public class DynamicLoadingUIHelper {

    protected static class LoadingState {
        static final LoadingState UNLOADED = new Unloaded();
        static final LoadingState LOADING = new Loading();
        static final LoadingState LOADED = new Loaded();

        public void entry(DynamicLoadingUIHelper context) {}
        public void exit(DynamicLoadingUIHelper context) {}
        public void retry(DynamicLoadingUIHelper context) {}
        public void changeVisibility(DynamicLoadingUIHelper context,boolean isVisiable) {}
        public void loaded(DynamicLoadingUIHelper context,boolean success) {}
        final public  void initCurrState(DynamicLoadingUIHelper context) {
            context.setCurrState(this);
            this.entry(context);
        }
        final protected void changeState(DynamicLoadingUIHelper context,LoadingState nextState) {
            context.setCurrState(nextState);
            if(this != nextState) {
                this.exit(context);
                nextState.entry(context);
            }
        }

        private static class Unloaded extends LoadingState {

            @Override
            public void entry(DynamicLoadingUIHelper context) {
                context.unload();
            }

            @Override
            public void changeVisibility(DynamicLoadingUIHelper context, boolean isVisiable) {
                if(isVisiable) {
                    this.changeState(context,LOADING);
                }
            }

            @Override
            public void retry(DynamicLoadingUIHelper context) {
                this.changeState(context, LOADING);
            }
        }

        private static class Loading extends LoadingState {
            @Override
            public void entry(DynamicLoadingUIHelper context) {
                context.showLoading();
                context.startLoading();
            }

            @Override
            public void exit(DynamicLoadingUIHelper context) {
                context.hideLoding();
            }

            @Override
            public void changeVisibility(DynamicLoadingUIHelper context, boolean isVisiable) {
                if(!isVisiable) {
                    this.changeState(context, UNLOADED);
                }
            }

            @Override
            public void retry(DynamicLoadingUIHelper context) {
                // I am already loading,
                // So intentionally do nothing.
            }

            @Override
            public void loaded(DynamicLoadingUIHelper context, boolean success) {
                if(success) {
                    this.changeState(context, LOADED);
                } else {
                    this.changeState(context, UNLOADED);
                }
            }
        }

        private static class Loaded extends LoadingState {
            @Override
            public void changeVisibility(DynamicLoadingUIHelper context, boolean isVisiable) {
                /*
                 * Following code will cause loading content everytime when the UI shows, even if the content is loaded
                 * successfully last time.
                 * Commenting out the following code will show the content successfully loaded last time.
                 * i.e. it "caches" the successful loading. Then the UI can only be reloaded by manually refreshing.
                 * e.g. click a "refresh" button which will send retry() to this helper.
                 */
                if(!context.mCacheSuccessfulyLoading && !isVisiable) {
                    this.changeState(context, UNLOADED);
                }
            }

            @Override
            public void retry(DynamicLoadingUIHelper context) {
                this.changeState(context, LOADING);
            }
        }
    }

    protected void unload() {

    }

    private  IDynamicLoadingHandler mHandler;

    private Context mContext;

    private ProgressDialog mProgressDlg;

    private boolean mCacheSuccessfulyLoading = true;

    protected LoadingState mCurrLoadingState = LoadingState.UNLOADED;

    private Handler mMainThreadHander = null;

    public DynamicLoadingUIHelper(IDynamicLoadingHandler loadingHandler, Context context) {
        this.mHandler = loadingHandler;
        this.mContext = context;
    }

    public void setVisible(final boolean isVisible) {
        if(mMainThreadHander == null) {
            mMainThreadHander = new Handler(Looper.getMainLooper());
        }
        mMainThreadHander.post(new Runnable() {
            @Override
            public void run() {
                mCurrLoadingState.changeVisibility(DynamicLoadingUIHelper.this, isVisible);
            }
        });

    }

    public void setContentLoaded(boolean success) {
        mCurrLoadingState.loaded(this, success);
    }

    public void retry() {
        mCurrLoadingState.retry(this);
    }

    public void setCacheSuccessfulyLoading(boolean cacheSuccessfulyLoading) {
        this.mCacheSuccessfulyLoading = cacheSuccessfulyLoading;
    }

    private void startLoading() {
        if(mHandler != null) {
            mHandler.startLoading();
        }
    }



    protected void hideLoding() {
        if(mProgressDlg != null) {
            mProgressDlg.dismiss();
        }
    }

    protected void showLoading() {
        if(mProgressDlg == null) {
            mProgressDlg = new ProgressDialog(mContext);
        }
        // 设置进度条风格，风格为圆形，旋转的
        mProgressDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // 设置ProgressDialog 提示信息
        mProgressDlg.setMessage("正在加载...");
        // 设置ProgressDialog 的进度条是否不明确
        mProgressDlg.setIndeterminate(false);
        // 设置ProgressDialog 是否可以按退回按键取消
        mProgressDlg.setCancelable(false);

        mProgressDlg.show();
    }


    private void setCurrState(LoadingState currState) {
        mCurrLoadingState = currState;
    }

}
