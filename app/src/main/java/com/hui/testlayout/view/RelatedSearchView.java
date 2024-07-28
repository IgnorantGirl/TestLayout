package com.hui.testlayout.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.hui.testlayout.R;

/**
 * 相关搜索扩展卡UI
 *
 * @author wanghui58
 * @since 2024/04/01
 */
public class RelatedSearchView extends ConstraintLayout {

    private Context mContext;

    private TextView mSearchTitleText;

    private TextView mSearchWordText;

    private ImageView mRightArrowImg;

    private ImageView mLeftImg;

    public RelatedSearchView(Context context) {
        super(context);
        init(context);
    }

    public RelatedSearchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RelatedSearchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public RelatedSearchView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.ares_bottom_search_related_card_view, this, true);
        mSearchTitleText = findViewById(R.id.ares_search_related_title_txt);
        mSearchWordText = findViewById(R.id.ares_search_related_word_txt);

        mRightArrowImg = findViewById(R.id.ares_search_related_arrow_img);
        mLeftImg = findViewById(R.id.ares_search_related_magnifier_img);
        mLeftImg.setImageResource(R.drawable.r3);
        mRightArrowImg.setImageResource(R.drawable.icon_feed_search_relate_entrance_arrow);
        mSearchTitleText.setText("相关搜索·");
        mSearchWordText.setText("红红火火恍恍惚惚东方兜底红火火恍恍惚惚东方兜底红火火恍恍惚惚东方兜底");


    }

    public void setSearchText(String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        mSearchWordText.setText(text);
    }
}
