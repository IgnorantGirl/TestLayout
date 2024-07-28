package com.hui.testlayout.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.hui.testlayout.R;
import com.hui.testlayout.utils.UIUtils;

/**
 * 广告底部跳转按钮
 */
public class BottomJumpView extends RelativeLayout {

    BottomButtonClickCallback mBottomButtonClickCallback;

    public BottomJumpView(Context context) {
        super(context);
        init(context);
    }

    public BottomJumpView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BottomJumpView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public BottomJumpView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context);
        init(context);
    }

    public void setBottomButtonClickCallback(BottomButtonClickCallback bottomButtonClickCallback) {
        mBottomButtonClickCallback = bottomButtonClickCallback;
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.bottom_jump_btn, this, true);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(UIUtils.dp2px(223),UIUtils.dp2px(65));
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        this.setLayoutParams(layoutParams);
        this.setOnClickListener(v -> {
            // 点击事件回调
            if(mBottomButtonClickCallback!= null){
                mBottomButtonClickCallback.onClick();
            }
        });
    }

    /**
     * 设置底部按钮背景颜色
     * @param color 传入的颜色值
     */
    public void setBottomBtnBackgroundColor(int color){
        // todo 二期 修改按钮颜色
    }

    public interface BottomButtonClickCallback {
        void onClick();
    }
}
