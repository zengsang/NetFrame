package com.example.netframe.utils;

import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class PtrLoadingHelper extends DynamicLoadingUIHelper {

    public PtrLoadingHelper(IDynamicLoadingHandler loadingHandler) {
        super(loadingHandler, null);
    }

    private class PtrLoadingHandler extends PtrDefaultHandler {

        @Override
        public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
            return PtrLoadingHelper.this.checkCanDoRefresh(frame, content, header);
        }

        /**
         * This handler will cause showLoadingDlg() and consequently mPtrFrame.autoRefresh().
         * But thanks to PtrFrameLayout's self defense, this will not cause any problem.
         * i.e. PtrFrameLayout will ignore refresh request if it is performing refreshing.
         *
         * Conversely, mPtrFrame.autoRefresh() in showLoadingDlg() will lead this handler
         * sending retry() to state machine. But don't worry, the state machine will handle this
         * extra retry() event correctly too. (i.e. it ignore it when it is loading)
         * @param frame
         */
        @Override
        public void onRefreshBegin(PtrFrameLayout frame) {
            retry();
        }
    }

    private PtrFrameLayout mPtrFrame = null;
    private PtrLoadingHandler mPtrHandler = null;

    public void setPtrFrameLayout(PtrFrameLayout ptrFrame) {
        mPtrFrame = ptrFrame;
        if (mPtrHandler == null) {
            mPtrHandler = new PtrLoadingHandler();
        }
        mPtrFrame.setPtrHandler(mPtrHandler);
    }

    public void setVisible(final boolean isVisible) {
        if(mPtrFrame != null) {
            mPtrFrame.post(new Runnable() {
                @Override
                public void run() {
                    mCurrLoadingState.changeVisibility(PtrLoadingHelper.this, isVisible);
                }
            });
        } else {
            super.setVisible(true);
        }

    }

    protected PtrFrameLayout getPtrFrameLayout() {
        return mPtrFrame;
    }

    /**
     * Override this method to adapt to your particular view.
     * @param frame
     * @param content
     * @param header
     * @return
     */
    protected boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        View currView = content;
        while(currView instanceof LinearLayout) {
            if( LinearLayout.VERTICAL == ((LinearLayout) currView).getOrientation()
                    && 1 == ((LinearLayout)currView).getChildCount()) {
                currView = ((LinearLayout) currView).getChildAt(0);
            } else {
                break;
            }
        }
        return !canChildScrollUp(currView);
    }

    protected static boolean canChildScrollUp(View view) {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (view instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) view;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return view.getScrollY() > 0;
            }
        } else {
            return view.canScrollVertically(-1);
        }
    }

    @Override
    protected void showLoading() {
        if (mPtrFrame != null) {
            mPtrFrame.autoRefresh();
        }
    }

    @Override
    protected void hideLoding() {
        if (mPtrFrame != null) {
            mPtrFrame.refreshComplete();
        }
    }
}
