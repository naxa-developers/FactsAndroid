package np.com.naxa.factsnepal.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import np.com.naxa.factsnepal.R;
import np.com.naxa.factsnepal.feed.feedv2.FactsFeedActivity;
import np.com.naxa.factsnepal.userprofile.LoginActivity;
import np.com.naxa.factsnepal.userprofile.UpdateProfileActivity;
import np.com.naxa.factsnepal.utils.ActivityUtil;
import np.com.naxa.factsnepal.utils.SharedPreferenceUtils;


public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                SharedPreferenceUtils sharedPreferenceUtils = new SharedPreferenceUtils(SplashScreenActivity.this);
                if (sharedPreferenceUtils.getBoolanValue(LoginActivity.KEY_IS_USER_LOGGED_IN, false)) {
                    ActivityUtil.openActivity(FactsFeedActivity.class, SplashScreenActivity.this);
                } else {
                    ActivityUtil.openActivity(LoginActivity.class, SplashScreenActivity.this, null, false);
                }
            }
        }, 2000);
    }

}
