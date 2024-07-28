package com.hui.testlayout.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.hui.testlayout.R;
import com.hui.testlayout.utils.UIUtils;

public class AdLeftLogoImage extends androidx.appcompat.widget.AppCompatImageView {
    public AdLeftLogoImage(Context context) {
        super(context);
        init(context);
    }

    public AdLeftLogoImage(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AdLeftLogoImage(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public AdLeftLogoImage(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context);
        init(context);
    }

    private void init(Context context){
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(UIUtils.dp2px(99),UIUtils.dp2px(24.67f));
        this.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.logo));
        this.setLayoutParams(layoutParams);
    }
}
