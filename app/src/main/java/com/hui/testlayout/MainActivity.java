package com.hui.testlayout;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hui.testlayout.view.AdBrandView;
import com.hui.testlayout.view.AdLeftLogoImage;
import com.hui.testlayout.view.BottomInfoView;

import com.hui.testlayout.view.BottomJumpView;
import com.hui.testlayout.view.CountDownView;
import com.hui.testlayout.view.RelatedSearchView;

public class MainActivity extends AppCompatActivity {

    LinearLayout parentLayout;

    LinearLayout childLayout;

    Button button;
    Button childButton;
    RelatedSearchView mHotPotView;
    BottomInfoView bottomInfoView;

    CountDownView mCountDownView;
    LinearLayout container;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomInfoView = new BottomInfoView(this);

        button = findViewById(R.id.btn_parent);
        childButton = findViewById(R.id.btn_child);
        childLayout = findViewById(R.id.child_layout);
        parentLayout = findViewById(R.id.parent_layout);
        button.setOnClickListener(v -> {

            bottomInfoView.updateBottomInfo();
        });
        childButton.setOnClickListener(v -> {
            bottomInfoView.appearBottomInfo();
            Toast.makeText(childButton.getContext(), "alpha:", Toast.LENGTH_SHORT).show();
        });

        container = findViewById(R.id.container_layout);
        mHotPotView = new RelatedSearchView(this);
        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                getApplicationContext().getApplicationContext().getResources().
                        getDimensionPixelSize(R.dimen.feed_hot_pot_height));
        params.bottomMargin =  getApplicationContext().getResources()
                .getDimensionPixelSize(R.dimen.feed_hot_pot_bottom_margin);
        params.leftMargin =  getApplicationContext().getResources()
                .getDimensionPixelSize(R.dimen.feed_hot_pot_left_margin);
        params.rightMargin =  getApplicationContext().getResources()
                .getDimensionPixelSize(R.dimen.feed_hot_pot_right_margin);
        mHotPotView.setLayoutParams(params);
        mHotPotView.setBackgroundResource(R.drawable.bg_rect_corner_6_66333333);
        container.addView(mHotPotView);
        container.addView(bottomInfoView);

        mCountDownView = new CountDownView(this,new CountDownView.AdCountDownListener() {

            @Override
            public void onFinish() {
                Log.i("wh_test", "main finish广告");
                // 跳转首页
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }

            @Override
            public void onSkip() {
                Log.i("wh_test", "main 跳过广告");
                // 跳转首页
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });

        container.addView(mCountDownView);

        // 广告品牌
        AdBrandView adBrandView = new AdBrandView(this);
        container.addView(adBrandView);
        // 好看logo
        AdLeftLogoImage image = new AdLeftLogoImage(this);
        container.addView(image);

        BottomJumpView jumpView = new BottomJumpView(this);
        container.addView(jumpView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mCountDownView.startCountDownTimer();
    }

    @Override
    protected void onDestroy() {
        if(mCountDownView != null){
            mCountDownView.finishCountDownTimer();
        }
        super.onDestroy();
    }
}