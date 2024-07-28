package com.hui.testlayout.view;

import android.animation.AnimatorInflater;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.hui.testlayout.R;

public class BottomInfoView extends ConstraintLayout {
    private Context mContext;

    private ImageView mLeftCoverImg;

    private TextView mTitleText;

    private TextView mContentText;

    private TextView mLabelText;

    private ViewGroup mContentView;

    public BottomInfoView(Context context) {
        super(context);
        init(context);
    }

    public BottomInfoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BottomInfoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public BottomInfoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    @SuppressLint("ResourceType")
    private void init(Context context) {
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.bottom_message, this, true);
        // 初始化控件
        mLeftCoverImg = findViewById(R.id.left_cover);
        mTitleText = findViewById(R.id.title);
        mContentText = findViewById(R.id.content);
        mLabelText = findViewById(R.id.label);
        mContentText.setSingleLine(true);
        mContentText.setEllipsize(TextUtils.TruncateAt.END);

        LayoutTransition transition = new LayoutTransition();
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 0f, 1f);
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 0f, 1f);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 0f, 1f);
        ObjectAnimator changeAnimator = ObjectAnimator.ofPropertyValuesHolder(this, alpha,scaleX,scaleY);
        changeAnimator.setDuration(3000);

        // 通过xml加载
        transition.setAnimator(LayoutTransition.DISAPPEARING, AnimatorInflater.loadAnimator(mContext, R.xml.animator_view_disappearing));
        transition.setAnimator(LayoutTransition.APPEARING, changeAnimator);
        this.setLayoutTransition(transition);


//        findViewById<ViewGroup>(R.id.container).apply {
//            if(null == layoutTransition) {
//                layoutTransition = LayoutTransition()
//            }
//            layoutTransition.setAnimator(LayoutTransition.DISAPPEARING, AnimatorInflater.loadAnimator(this@AnimActivity, R.xml.animator_view_disappearing))
//            layoutTransition.setAnimator(LayoutTransition.APPEARING, AnimatorInflater.loadAnimator(this@AnimActivity, R.xml.animator_view_appearing))


//            this.animate()
//                .setDuration(3000)
//                .alpha(0)
//                .withEndAction(() -> {
//                    mLabelText.setVisibility(GONE);
//                    mLeftCoverImg.setVisibility(GONE);
//                    mContentText.setSingleLine(false);
//                });
//        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);

    }

    public void updateBottomInfo() {
        mLeftCoverImg.setVisibility(GONE);
        mContentText.setSingleLine(false);

        if(mContentText.getLineCount() >1){
            mLabelText.setVisibility(GONE);
        }
    }

    public void appearBottomInfo() {
        mLeftCoverImg.setVisibility(VISIBLE);

        mLabelText.setVisibility(VISIBLE);
        mContentText.setSingleLine(true);
    }
}
