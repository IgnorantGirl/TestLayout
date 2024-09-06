package com.hui.testlayout

import android.app.ActivityManager
import android.app.ActivityManager.RunningAppProcessInfo
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.hui.testlayout.interfaces.OnShakeListener
import com.hui.testlayout.manager.ShakeManager


open class ShakeActivity : AppCompatActivity() {

    private val TAG = ShakeActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shake)
        val textView: TextView = findViewById<TextView>(R.id.tv_shake)

        ShakeManager(this, 1500, 2, 15, object : OnShakeListener {
            override fun onShake() {
                if (!isForeground(this@ShakeActivity)) {
                    return
                }
                Log.d(TAG, "onShake")
                textView.text = "摇一摇"
                textView.setTextColor(0xFFFF0000.toInt())
            }
        })

        // 加载lottie
        val animationView: LottieAnimationView = findViewById(R.id.lottie_animation_view)
        animationView.playAnimation()
        animationView.imageAssetsFolder = "images"

    }

    override fun onResume() {
        Log.d(TAG, "onResume")
        super.onResume()
    }

    override fun onDetachedFromWindow() {
        Log.d(TAG, "onDetachedFromWindow")
        super.onDetachedFromWindow()
    }

    override fun onAttachedToWindow() {
        Log.d(TAG, "onAttachedToWindow")
        super.onAttachedToWindow()
    }

    override fun onStop() {
        Log.d(TAG, "onStop")
        super.onStop()
    }

    override fun onPause() {
        Log.d(TAG, "onPause")
        super.onPause()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        Log.d(TAG, "onWindowFocusChanged hasFocus:$hasFocus")
        super.onWindowFocusChanged(hasFocus)
    }

    //当前应用是否处于前台
    private fun isForeground(context: Context?): Boolean {
        if (context != null) {
            val activityManager =
                context.getSystemService(ACTIVITY_SERVICE) as ActivityManager ?: return false
            val processes = activityManager.runningAppProcesses ?: return false
            for (processInfo in processes) {
                if (processInfo.processName == context.packageName) {
                    if (processInfo.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                        return true
                    }
                }
            }
        }
        return false
    }
}