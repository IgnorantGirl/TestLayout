package com.hui.testlayout.manager

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Vibrator
import android.util.Log
import com.hui.testlayout.interfaces.OnShakeListener
import kotlin.math.abs
// https://www.51cto.com/article/779388.html
// https://blog.51cto.com/u_16213704/10861222

class ShakeManager(
    context: Context,
    intervalTimeMillis: Long,
    thresholdCount: Int,
    shakeThreshold: Int,
    onShakeListener: OnShakeListener
) : SensorEventListener {
    /**
     * 时间范围
     */
    private var mIntervalTimeMillis: Long = 15000

    /**
     * 要换次数阈值
     */
    private var mThresholdCount: Int = 2

    /**
     * 加速度阈值
     */
    private var mShakeThreshold: Int = 15

    private var mSensorManager: SensorManager? = null
    private var mVibrator: Vibrator? = null
    private var mOnShakeListener: OnShakeListener? = null

    /**
     * 上一次摇晃时间
     */
    private var mLastShakeTimeMillis: Long = 0

    /**
     * 摇晃次数
     */
    private var mShakeCount = 0

    init {
        this.mIntervalTimeMillis = intervalTimeMillis
        this.mThresholdCount = thresholdCount
        this.mShakeThreshold = shakeThreshold
        this.mSensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager?
        this.mVibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?
        this.mOnShakeListener = onShakeListener
        mSensorManager?.registerListener(
            this,
            mSensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    override fun onSensorChanged(event: SensorEvent) {
//加速度变化
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            val currentTimeMillis = System.currentTimeMillis()
            // 摇晃时间大于1500
            if (currentTimeMillis - mLastShakeTimeMillis > mIntervalTimeMillis) {
                mShakeCount = 0
            }
            val values = event.values
            Log.d("TAG wanghui", "onSensorChanged: ${values[0]}  ${values[1]}  ${values[2]}")
            if (abs(values[0]) > mShakeThreshold || abs(values[1]) > mShakeThreshold || abs(values[2]) > mShakeThreshold) {
                mLastShakeTimeMillis = currentTimeMillis
                mShakeCount += 1
                if (mShakeCount > mThresholdCount) {
                    mVibrator?.vibrate(100)
                    mShakeCount = 0
                    mLastShakeTimeMillis = 0
                    mOnShakeListener?.onShake()
                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // TODO("Not yet implemented")
    }
}