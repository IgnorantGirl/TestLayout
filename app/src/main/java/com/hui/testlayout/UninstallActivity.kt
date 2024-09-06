package com.hui.testlayout

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


open class UninstallActivity : AppCompatActivity() {

    private val TAG = UninstallActivity::class.java.simpleName

    val  linearContainer: LinearLayout by lazy { findViewById<LinearLayout>(R.id.accelerate_text) }
    val  subTitle: TextView by lazy { findViewById<TextView>(R.id.sub_title) }
    val button: TextView by lazy { findViewById<TextView>(R.id.haokan_btn) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_uninstall1)
       // var lineCount = subTitle.layout.lineCount
        subTitle.post(Runnable {
            var layoutParams: RelativeLayout.LayoutParams? = linearContainer.layoutParams as RelativeLayout.LayoutParams?
            var lineCount = subTitle.lineCount
            Log.e(TAG, "onCreate: $lineCount" )
            var percent: Float? = (layoutParams?.rightMargin?.times(1.0f))?.let { layoutParams.leftMargin.div(it) }
            if(lineCount > 1) {
                layoutParams?.leftMargin = 0;
                layoutParams?.rightMargin = 0;
                linearContainer.requestLayout()
            }
        })

        button.setOnClickListener { showDialog1(it) }
    }


    fun showDialog1(v: View?) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        val dialog1 = builder.setIcon(R.drawable.ares_bottom_yellow_point).setTitle("普通对话框")
            .setMessage("普通对话框的内容").setPositiveButton("确定", null)
            .setNegativeButton("取消", null).create()

        val window: Window? = dialog1.window
        if (window != null) {
            window.setGravity(Gravity.BOTTOM) // 设置对话框显示在屏幕底部
            window.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
        }
        dialog1.show()


    }
}