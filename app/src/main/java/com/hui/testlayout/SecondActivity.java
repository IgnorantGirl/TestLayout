package com.hui.testlayout;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.hui.testlayout.utils.UIUtils;
import com.hui.testlayout.view.AdBrandView;
import com.hui.testlayout.view.AdLeftLogoImage;
import com.hui.testlayout.view.BottomJumpView;
import com.hui.testlayout.view.CountDownView;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RelativeLayout layout = new RelativeLayout(this);
        RelativeLayout.LayoutParams rootParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        layout.setLayoutParams(rootParams);
        // 添加logo
        AdLeftLogoImage logo = new AdLeftLogoImage(this);
        ViewGroup.LayoutParams layoutParams = logo.getLayoutParams();
        setLayoutMargin(logo, layoutParams, 15, 23, 0, 0);
        layout.addView(logo);
        // 添加倒计时
        CountDownView countDownView = new CountDownView(this,new CountDownView.AdCountDownListener() {

            @Override
            public void onFinish() {
                Log.i("wh_test", "111 main finish广告");
            }

            @Override
            public void onSkip() {
                Log.i("wh_test", "222 main 跳过广告");
                // 跳转首页
                // 跳转shake
                Intent intent = new Intent(SecondActivity.this, ShakeActivity.class);
                startActivity(intent);
            }
        });
        ViewGroup.LayoutParams countDownViewParams = countDownView.getLayoutParams();
        setLayoutMargin(countDownView, countDownViewParams, 0, 20, 15, 0);
        countDownView.startCountDownTimer();
        layout.addView(countDownView);
        // 添加广告brand
        AdBrandView brand = new AdBrandView(this);
        ViewGroup.LayoutParams brandParams = brand.getLayoutParams();
        setLayoutMargin(brand, brandParams, 15, 0, 0, 15);
        layout.addView(brand);
        // 添加底部按钮
        BottomJumpView bottomJump = new BottomJumpView(this);
        ViewGroup.LayoutParams bottomJumpParams = bottomJump.getLayoutParams();
        setLayoutMargin(bottomJump, bottomJumpParams, 0, 0, 0, 40);


      //  layout.addView(bottomJump);

        layout.setBackground(ContextCompat.getDrawable(this, android.R.color.holo_red_dark));

        TextView text = new TextView(this);
        text.setId(View.generateViewId());
        text.setText("扭动或点击跳转详情页或第三方应用");
        text.setTextSize(18);
        text.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        RelativeLayout.LayoutParams textParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        textParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        textParams.topMargin = 10;
        //setLayoutMargin(text,textParams,0,0,0,40);
        layout.addView(text,textParams);

        // 动效下面的文字
        LottieAnimationView lottie = new LottieAnimationView(this);
        lottie.setAnimation("shake3.json");

//        lottie.loop(true);
        // lottie.setRepeatMode(LottieDrawable.REVERSE);//设置播放模式
        lottie.setRepeatCount(LottieDrawable.INFINITE);//设置重复次数
        RelativeLayout.LayoutParams lottieParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, UIUtils.dp2px(110));
        lottieParams.addRule(RelativeLayout.ABOVE,text.getId());
        lottieParams.bottomMargin = 10;
        lottie.setLayoutParams(lottieParams);
        layout.addView(lottie,lottieParams);





        setContentView(layout);
        lottie.playAnimation();

        bottomJump.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.i("wh_test", "点击了底部按钮");
                // 跳转首页
                handleDeepLink(getApplicationContext(),"",
                        "openapp.jdmobile://virtual?params=%7B%22SE%22%3A%22ADC_SOoNrdcJ3b6G09oorLllK1vk%2BxTqeyuPiBKOzXQIzV8VzE%2FIa1%2FUjb3iX7l1RHFyx4BIBFLDpeAuauzkgz4lmWmunzlgG2HTopBsXVLAxSQxms3vi4x2e0oo6EJNMpCZ0zQCVlSFEZ4uOXZM%2BB%2BWzQ%3D%3D%22%2C%22action%22%3A%22to%22%2C%22category%22%3A%22jump%22%2C%22des%22%3A%22getCoupon%22%2C%22ext%22%3A%22%7B%5C%22ad%5C%22%3A%5C%22%5C%22%2C%5C%22ch%5C%22%3A%5C%22%5C%22%2C%5C%22shop%5C%22%3A%5C%22%5C%22%2C%5C%22sku%5C%22%3A%5C%22%5C%22%2C%5C%22ts%5C%22%3A%5C%22%5C%22%2C%5C%22uniqid%5C%22%3A%5C%22%7B%5C%5C%5C%22pos_id%5C%5C%5C%22%3A%5C%5C%5C%2234858%5C%5C%5C%22%2C%5C%5C%5C%22sid%5C%5C%5C%22%3A%5C%5C%5C%226370_2b19814b0bfb49d0adba70104bdeea22_1%5C%5C%5C%22%2C%5C%5C%5C%22sku_id%5C%5C%5C%22%3A%5C%5C%5C%2210035293106440%5C%5C%5C%22%7D%5C%22%7D%22%2C%22kepler_param%22%3A%7B%22channel%22%3A%222e3b9ecfb3a1465badbbbeb48df4140c%22%2C%22source%22%3A%22kepler-open%22%7D%2C%22m_param%22%3A%7B%22jdv%22%3A%22238571484%7Capi%7Ct_1001802371_207944_34858%7Cadrealizable%7C_2_app_0_c8337d47f2124fabbf84aa17771caf73-p_34858%22%7D%2C%22sourceType%22%3A%22adx%22%2C%22sourceValue%22%3A%22api_6370%22%2C%22url%22%3A%22https%3A%2F%2Fccc-x.jd.com%2Fdsp%2Fnc%3Fext%3DaHR0cDovL3JlLm0uamQuY29tL2xpc3QvaXRlbS8zMDU3Ni0xMDAzNTI5MzEwNjQ0MC5odG1sP3JlX2RjcD00OEY4OGdpMzRvc25oT1FQQ3NXQXQ5TmVoN1JheTl1anZiR1N1Mk5kWVdLSDRzcGd5Nzd2Y3kwSXhfTFhDbHp0a2dQOXJtTjZOYkFKQU9BbHFHRWJ5RTc0b0JPRUZLMmc4U0xvVmdCalBmTW1wc3A3MklfRFk2Q19JMlBLVVFxNXBMN2RkeWlhOTBIazF1ZTNMRGR0U29DUWxpeWpCcWpHbjBxMmw0dTB5WTU0V3VtdlFGMV9Jc0x0dnJ5UWlYcVYxZHQ4LURXODlmY3JISlRlSk1YUU9KUTRTVzQ1SXpDbTViUkwyaFFQWS15Y3VBamJ5M3p3MXclM0QlM0QmYWRfb2Q9MQ%26log%3Din4TB3bfvW2tGNAFI7Z3vR0VN17zFeye3ycL20j9SbOLcq9AGCDRdhS1eKQ6fmsa8CF3nVrNnrlEePQPOfuJjVkaEGrmkmBe8TdsIePRrjI_hV8i8kvMoWMD7qXT1FEr9yPBElrTl9CMFTCP1ul9hdJQxGLRvpjCIBbcJntU2WZDzsjbXs4wwjl8Djr2SGoMMyqPWkTAKqjsBGU1_N42APq5asVLIxYZznrBhsfAecn-FQ9NnvFlOAsRmKMzwzP-Kh2wOC7v344jBAe6CPegrbeZBI8_CowFxsmzcg8Q09qVOUhHTEPqzb5dG7hm6hkapiQ6LAlSLjzEI5qA3pn2MTKtmQ8vyZdEpE9YvsvzFfr3YtmxSkh_1y7afMrx1cx7yIyte9h5nTBZiwjzB_e8l1rz3bvzcYhnNCswDkEBuaM5tDuK531QlbFUSau4fLabsswHj-kmSRfjcDzNEnhdQ-04kUghvyV4ugmCDKb0nHqugz9P2J6rYHRjgOSrO0zBWz9NgdEWwQJsWVuVygoB5GK5rVfFjTgTznPbh580vnnpV5HqmrIqlA4Sp_AFcSXvAx2mLvl150eKms7LjItZy1T9S4z5rjG9Ok8jzBw5bO95Lbpf8qjXMigjwGwEXCdV1wl9lbX9uGkQDOAO4FwEhcfr5l04Ml3Qlp3OoAs7LWsRPm_wTi_E0LhOsnMjELxc%26v%3D404%26kms_v%3D1189_a1%26SE%3D1%22%7D%0A");
            }
        });
    }

    public static void handleDeepLink(Context context, String scene, String url) {
        if (TextUtils.isEmpty(url)) {
            return ;
        }

        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Log.d( "wh_test111", "ActivityNotFoundException: " + e.getMessage());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setLayoutMargin(View targentView, ViewGroup.LayoutParams layoutParams, int marginLeftDp, int marginTopDp, int marginRightDp, int marginBottomDp) {
        // 检查layoutParams的类型，因为不同类型的布局（如LinearLayout、RelativeLayout等）有不同的LayoutParams子类
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            // 转换为MarginLayoutParams以便可以访问margin属性
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
            // 设置margin
            marginLayoutParams.leftMargin = UIUtils.dp2px(marginLeftDp);
            marginLayoutParams.topMargin = UIUtils.dp2px(marginTopDp);
            marginLayoutParams.rightMargin = UIUtils.dp2px(marginRightDp);
            marginLayoutParams.bottomMargin = UIUtils.dp2px(marginBottomDp);

            // 应用新的布局参数
            targentView.setLayoutParams(marginLayoutParams);
        } else if (layoutParams != null) {
            // 如果当前的LayoutParams不是MarginLayoutParams，创建一个新的MarginLayoutParams实例
            RelativeLayout.LayoutParams newLayoutParams = new RelativeLayout.LayoutParams(
                    layoutParams.width,
                    layoutParams.height
            );
            newLayoutParams.setMargins(
                    UIUtils.dp2px(marginLeftDp), // left margin
                    UIUtils.dp2px(marginTopDp), // top margin
                    UIUtils.dp2px(marginRightDp), // right margin
                    UIUtils.dp2px(marginBottomDp)  // bottom margin
            );
            targentView.setLayoutParams(newLayoutParams);
        } else {
            Log.i("wh_test", "layoutParams is null");
        }
    }
}