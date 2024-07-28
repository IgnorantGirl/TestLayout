package com.hui.testlayout.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.hui.testlayout.R;
import com.hui.testlayout.utils.UIUtils;

/**
 * 广告品牌名称
 */
public class AdBrandView extends androidx.appcompat.widget.AppCompatTextView {
    public AdBrandView(Context context) {
        super(context);
        init(context);
    }

    public AdBrandView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AdBrandView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public AdBrandView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context);
        init(context);
    }

    private void init(Context context){
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(UIUtils.dp2px(28),UIUtils.dp2px(14));
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        this.setBackground( ContextCompat.getDrawable(context, R.drawable.bg_corner_3));
        this.setTextColor(ContextCompat.getColor(context, R.color.white));
        this.setTextSize(8);
        this.setText("广告");
        this.setLayoutParams(layoutParams);
        this.setGravity(android.view.Gravity.CENTER);

    }

    /**
     * 设置广告品牌名称
     * @param brandText
     */

    public void setBrandText(String brandText) {
        this.setText(brandText);
    }
}
