package com.hui.testlayout;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

import com.hui.testlayout.interfaces.ICountDownView;


/**
 * 支持倒计时的组件
 *
 * @author liyabing
 * @version 0
 * @since 2020-03-12
 */
public class CountDownTextView extends LinearLayout implements ICountDownView {
    /** 倒计时描述文案 */
    private TextView mLabelText;
    /** 倒计时文案 */
    private TextView mTimerText;
    /** 倒计时文案格式，默认格式：ns */
    private String mTimerTexFormat = "%ss";

    public CountDownTextView(Context context) {
        this(context, null);
    }

    public CountDownTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountDownTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.ad_count_down_text_view, this);
        mLabelText = view.findViewById(R.id.ad_count_down_label_text);
        mTimerText = view.findViewById(R.id.ad_count_down_timer_text);
    }

    /**
     * 设置文本字号dp值
     *
     * @param textSize 字号，dp值
     */
    public void setTextSize(int textSize) {
        mLabelText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize);
        mTimerText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize);
    }

    /**
     * 设置文本颜色
     *
     * @param color 色值
     */
    public void setTextColor(@ColorInt int color) {
        mLabelText.setTextColor(color);
        mTimerText.setTextColor(color);
    }

    /**
     * 设置标签文案
     *
     * @param labelText 标签文案
     */
    public void setLabelText(CharSequence labelText) {
        mLabelText.setText(labelText);
    }

    /**
     * 设置倒计时文本格式，格式：prefix+%s+suffix
     *
     * @param prefix 前缀
     * @param suffix 后缀
     */
    public void setTimerTextFormat(String prefix, String suffix) {
        mTimerTexFormat = ((TextUtils.isEmpty(prefix)) ? "" : prefix);
        mTimerTexFormat += "%s";
        mTimerTexFormat += ((TextUtils.isEmpty(suffix)) ? "" : suffix);
    }

    @Override
    public void onStart(long elapsedMillis, long totalMillis) {
        mTimerText.setText(String.format(mTimerTexFormat, (int) Math.ceil((totalMillis - elapsedMillis) / 1000f)));
    }

    @Override
    public void onProgress(long elapsedMillis, long totalMillis) {
        mTimerText.setText(String.format(mTimerTexFormat, (int) Math.ceil((totalMillis - elapsedMillis) / 1000f)));
    }

    @Override
    public void onFinish(long totalMillis) {
        mTimerText.setText(String.format(mTimerTexFormat, 0));
    }

    @Override
    public void onCancel(long elapsedMillis, long totalMillis) {
        mTimerText.setText(String.format(mTimerTexFormat, (int) Math.ceil((totalMillis - elapsedMillis) / 1000f)));
    }
}
