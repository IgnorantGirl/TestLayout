package com.hui.testlayout.view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * @author: gezongpan
 * @date: 2023/7/17
 * @Description:
 */
class FeatureCollectView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    LinearLayout(context, attrs, defStyleAttr) {

    companion object {
        const val TAG = "FeatureCollectViewTag"
    }

    init {
//        LayoutInflater.from(context).inflate(R.layout.common_ui_feature_collect_view, this, true)
    }

    var mTitle: TextView? = null
    var mRv: RecyclerView? = null

    override fun onFinishInflate() {
        super.onFinishInflate()
//        mTitle = findViewById(R.id.collect_title)
//        mRv = findViewById(R.id.collect_recycler_view)
    }
}

