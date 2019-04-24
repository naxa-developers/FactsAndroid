package np.com.naxa.factsnepal.splashscreen;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import np.com.naxa.factsnepal.R;
import np.com.naxa.factsnepal.feed.feedv2.FactsFeedActivity;
import np.com.naxa.factsnepal.userprofile.LoginActivity;
import np.com.naxa.factsnepal.utils.ActivityUtil;
import np.com.naxa.factsnepal.utils.SharedPreferenceUtils;
import np.com.naxa.factsnepal.walkthroughscreen.WalkThroughSliderActivity;

import static np.com.naxa.factsnepal.common.Constant.SharedPrefKey.IS_APP_FIRST_TIME_LAUNCH;
import static np.com.naxa.factsnepal.common.Constant.SharedPrefKey.KEY_IS_USER_LOGGED_IN;


public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            if (SharedPreferenceUtils.getInstance(SplashScreenActivity.this).getBoolanValue(IS_APP_FIRST_TIME_LAUNCH, true)) {
                ActivityUtil.openActivity(WalkThroughSliderActivity.class, SplashScreenActivity.this);
            } else {
                if (SharedPreferenceUtils.getInstance(SplashScreenActivity.this).getBoolanValue(KEY_IS_USER_LOGGED_IN, false)) {
                    ActivityUtil.openActivity(FactsFeedActivity.class, SplashScreenActivity.this);
                } else {
                    ActivityUtil.openActivity(LoginActivity.class, SplashScreenActivity.this, null, false);
                }
            }

        }, 2000);
    }

}
