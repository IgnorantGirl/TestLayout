package com.hui.testlayout;

import android.os.CountDownTimer;

import androidx.annotation.NonNull;

import com.hui.testlayout.interfaces.ICountDownView;

import java.lang.ref.WeakReference;

/**
 * 支持倒计时的组件计时器
 *
 * @author liyabing
 * @version 0
 * @since 2020-03-12
 */
public class CountDownViewTimer<VIEW extends ICountDownView> {

    /** 倒计时Timer */
    private CountDownTimer mCountDownTimer;
    /** 倒计时总时长，单位ms */
    private long mTotalTimeMillis;
    /** 倒计时已执行时长，单位ms */
    private long mElapsedTimeMills;
    /** 倒计时组件 */
    private WeakReference<VIEW> mCountDownViewRef;

    public CountDownViewTimer(@NonNull VIEW view) {
        mCountDownViewRef = new WeakReference<>(view);
    }

    /**
     * 开启计时任务
     *
     * @param totalCountDownMills 计时的总时间
     */
    public void startCountDown(long totalCountDownMills) {
        // 先终止老timer
        stopCountDown();
        mTotalTimeMillis = totalCountDownMills;
        mElapsedTimeMills = 0L;
        mCountDownTimer = new InnerCountDownTimer(this, mTotalTimeMillis, 1000);
        mCountDownTimer.start();
        VIEW view = view();
        if (view != null) {
            view.onStart(mTotalTimeMillis, mTotalTimeMillis);
        }
    }

    /**
     * 计时器停止计时
     */
    public void stopCountDown() {
        if (mCountDownTimer == null) {
            return;
        }
        mCountDownTimer.cancel();
        VIEW view = view();
        if (view != null) {
            view.onCancel(mElapsedTimeMills, mTotalTimeMillis);
        }
    }

    /**
     * 进度更新
     *
     * @param elapsedMillis 已执行时间，单位ms
     * @param totalMillis   总时长，单位ms
     */
    private void onProgress(long elapsedMillis, long totalMillis) {
        VIEW view = view();
        if (view != null) {
            view.onProgress(elapsedMillis, totalMillis);
        }
    }

    /**
     * 倒计时结束
     *
     * @param totalMillis 总时长，单位ms
     */
    private void onFinish(long totalMillis) {
        VIEW view = view();
        if (view != null) {
            view.onFinish(totalMillis);
        }
    }

    /**
     * 返回已经走完的时间
     */
    public long elapsedTimeMills() {
        return mElapsedTimeMills;
    }

    /**
     * 倒计时总时长
     */
    public long totalTimeMills() {
        return mTotalTimeMillis;
    }

    /**
     * 倒计时组件
     */
    private VIEW view() {
        return mCountDownViewRef.get();
    }

    private static class InnerCountDownTimer extends CountDownTimer {

        private final WeakReference<CountDownViewTimer> mCountDownViewRef;

        /**
         * @param timer             timer
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public InnerCountDownTimer(@NonNull CountDownViewTimer timer, long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            mCountDownViewRef = new WeakReference<>(timer);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            CountDownViewTimer timer = mCountDownViewRef.get();
            if (timer == null) {
                return;
            }
            timer.mElapsedTimeMills = timer.mTotalTimeMillis - millisUntilFinished;
            timer.onProgress(timer.elapsedTimeMills(), timer.totalTimeMills());

        }

        @Override
        public void onFinish() {
            CountDownViewTimer timer = mCountDownViewRef.get();
            if (timer == null) {
                return;
            }
            timer.onFinish(timer.totalTimeMills());
        }
    }
}
