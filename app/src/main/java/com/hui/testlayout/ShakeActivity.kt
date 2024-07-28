package com.hui.testlayout

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.hui.testlayout.interfaces.OnShakeListener
import com.hui.testlayout.manager.ShakeManager

open class ShakeActivity : AppCompatActivity() {

    private val TAG = ShakeActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shake)
        val textView:TextView = findViewById<TextView>(R.id.tv_shake)

        ShakeManager(this,1500,2,15,object :OnShakeListener{
            override fun onShake() {
                Log.d(TAG,"onShake")
                textView.text = "摇一摇"
                textView.setTextColor(0xFFFF0000.toInt())
            }
        })

        // todo 加载lottie
    }
}