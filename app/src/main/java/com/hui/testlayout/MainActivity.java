package com.hui.testlayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hui.testlayout.messeneger.MessengerActivity;
import com.hui.testlayout.view.AdBrandView;
import com.hui.testlayout.view.AdLeftLogoImage;
import com.hui.testlayout.view.BottomInfoView;

import com.hui.testlayout.view.BottomJumpView;
import com.hui.testlayout.view.ComponentView;
import com.hui.testlayout.view.CountDownView;
import com.hui.testlayout.view.RelatedSearchView;
import com.hui.testlayout.widget.CommonWidgetManager;
import com.hui.testlayout.widget.IRequestWidgetListener;
import com.hui.testlayout.widget.PhoneAccelerateWidget;

public class MainActivity extends AppCompatActivity {


    public static final String FOLLOW_SUCCESS_THANKS_SEPARATOR =":  ";

    LinearLayout parentLayout;

    LinearLayout childLayout;

    Button button;
    Button childButton;
    RelatedSearchView mHotPotView;
    BottomInfoView bottomInfoView;

    CountDownView mCountDownView;
    LinearLayout container;

    EditText editText;

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
        params.bottomMargin = getApplicationContext().getResources()
                .getDimensionPixelSize(R.dimen.feed_hot_pot_bottom_margin);
        params.leftMargin = getApplicationContext().getResources()
                .getDimensionPixelSize(R.dimen.feed_hot_pot_left_margin);
        params.rightMargin = getApplicationContext().getResources()
                .getDimensionPixelSize(R.dimen.feed_hot_pot_right_margin);
        mHotPotView.setLayoutParams(params);
        mHotPotView.setBackgroundResource(R.drawable.bg_rect_corner_6_66333333);
        container.addView(mHotPotView);
        container.addView(bottomInfoView);

        mCountDownView = new CountDownView(this, new CountDownView.AdCountDownListener() {

            @Override
            public void onFinish() {
                Log.i("wh_test", "main finish广告");
                // 跳转首页
//                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
//                startActivity(intent);
            }

            @Override
            public void onSkip() {
                Log.i("wh_test", "main 跳过广告");
                // 跳转首页
                Intent intent = new Intent(MainActivity.this, MessengerActivity.class);
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

        editText = new EditText(this);
        editText.setText("wh");
        container.addView(editText);

        BottomJumpView jumpView = new BottomJumpView(this);
        container.addView(jumpView);
        jumpView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转uninstall
                Intent intent = new Intent(MainActivity.this, UninstallActivity.class);
                intent.setDataAndType(Uri.parse("file:/abc"),"image/png");
                startActivity(intent);
            }
        });

        ComponentView componentView = new ComponentView(this);
        container.addView(componentView);

        Button addWidgetBtn = new Button(this);
        String thanksStr = FOLLOW_SUCCESS_THANKS_SEPARATOR + "添加组件";

        addWidgetBtn.setText(thanksStr);
        container.addView(addWidgetBtn);
        addWidgetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 添加小组件
                CommonWidgetManager.addWidget(PhoneAccelerateWidget.class, new IRequestWidgetListener() {
                    @Override
                    public void requestCallback(boolean isSupportInstalled) {

                    }
                }, MainActivity.this);
            }
        });




    }

    @Override
    protected void onStart() {
        Log.i("wh_test", "onDestroy");
        super.onStart();
        mCountDownView.startCountDownTimer();
    }

    @Override
    protected void onDestroy() {
        Log.i("wh_test", "onDestroy");
        if (mCountDownView != null) {
            mCountDownView.finishCountDownTimer();
        }
        super.onDestroy();
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i("wh_test", "onSaveInstanceState");
        outState.putString("edit_text", editText.getText().toString());

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i("wh_test", "onNewIntent");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String text = savedInstanceState.getString("edit_text");
        Log.i("wh_test", "onRestoreInstanceState exit:"+text);
        editText.setText(text);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.i("wh_test", "onConfigurationChanged:newConfig:" + newConfig.orientation);
    }
}