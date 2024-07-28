package com.hui.testlayout.view;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.hui.testlayout.R;
import com.hui.testlayout.utils.UIUtils;

public class CountDownView extends RelativeLayout {
    private TextView mCountDownTextView;
    private CountDownTimer mCountDownTimer;

    private AdCountDownListener adCountDownListener;

    public CountDownView(@NonNull Context context,AdCountDownListener listener) {
        super(context);
        init(context);
        this.adCountDownListener = listener;
    }

    public void init(Context context) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(UIUtils.dp2px( 72), UIUtils.dp2px( 30));
        params.addRule(RelativeLayout.ALIGN_PARENT_END);
        this.setLayoutParams(params);
        this.setBackgroundResource(R.drawable.bg_start_page_circle);

        TextView skipView = new TextView(context);
        params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params.addRule(RelativeLayout.ALIGN_PARENT_START);
        params.leftMargin = UIUtils.dp2px( 12);
        params.topMargin = UIUtils.dp2px( 5);
        skipView.setLayoutParams(params);
        skipView.setText("跳过");
        skipView.setTextColor(Color.WHITE);
        skipView.setTextSize(14);
        this.addView(skipView);

        mCountDownTextView = new TextView(context);
        params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(ALIGN_TOP, skipView.getId());
        params.leftMargin = UIUtils.dp2px( 45);
        params.topMargin = UIUtils.dp2px( 5);
        mCountDownTextView.setLayoutParams(params);
        mCountDownTextView.setTextSize(14);
        mCountDownTextView.setTextColor(Color.WHITE);
        mCountDownTextView.setGravity(View.TEXT_ALIGNMENT_CENTER);
        mCountDownTextView.setText("05");
        this.addView(mCountDownTextView);

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 取消倒计时
                if (mCountDownTimer != null) {
                    mCountDownTimer.cancel();
                }
                // 倒计时跳过  事件回调
                if (adCountDownListener != null) {
                    adCountDownListener.onSkip();
                }
                // 跳过广告
                Log.i("wh_test", "跳过广告");
            }
        });
    }

    public void startCountDownTimer() {
        mCountDownTimer = new CountDownTimer(5 * 1000, 500) {


            @Override
            public void onTick(long millisUntilFinished) {
                String formattedSeconds = String.format("%02d", millisUntilFinished/1000); // 将秒数格式化为两位数
                System.out.println(formattedSeconds); // 输出: 05
                mCountDownTextView.setText(formattedSeconds);
            }

            @Override
            public void onFinish() {
                mCountDownTextView.setText("00");
                mCountDownTimer.cancel();
                // 倒计时结束  事件回调
                if (adCountDownListener != null) {
                    adCountDownListener.onFinish();
                }
            }
        };
        mCountDownTimer.start();
    }

    public void finishCountDownTimer() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        mCountDownTextView = null;
    }

    public interface AdCountDownListener {
        void onFinish();

        void onSkip();
    }
}


