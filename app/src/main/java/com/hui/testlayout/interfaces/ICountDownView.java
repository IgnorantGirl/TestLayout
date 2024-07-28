package com.hui.testlayout.interfaces;

/**
 * 支持倒计时的组件接口
 *
 * @author liyabing
 * @version 0
 * @since 2020-03-12
 */
public interface ICountDownView {

    /**
     * 开始倒计时
     *
     * @param elapsedMillis 已执行时间，单位ms
     * @param totalMillis   总时长，单位ms
     */
    void onStart(long elapsedMillis, long totalMillis);


    /**
     * 进度更新
     *
     * @param elapsedMillis 已执行时间，单位ms
     * @param totalMillis   总时长，单位ms
     */
    void onProgress(long elapsedMillis, long totalMillis);

    /**
     * 倒计时结束
     *
     * @param totalMillis 总时长，单位ms
     */
    void onFinish(long totalMillis);

    /**
     * 取消倒计时
     *
     * @param elapsedMillis 已执行时间，单位ms
     * @param totalMillis   总时长，单位ms
     */
    void onCancel(long elapsedMillis, long totalMillis);

}
