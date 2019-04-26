package np.com.naxa.factsnepal.splashscreen;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import np.com.naxa.factsnepal.R;
import np.com.naxa.factsnepal.feed.list.FeedListActivity;
import np.com.naxa.factsnepal.network.NetworkApiClient;
import np.com.naxa.factsnepal.network.NetworkApiInterface;
import np.com.naxa.factsnepal.surveys.surveyforms.SurveyQuestionDetailsResponse;
import np.com.naxa.factsnepal.feed.feedv2.FactsFeedActivity;
import np.com.naxa.factsnepal.userprofile.LoginActivity;
import np.com.naxa.factsnepal.utils.ActivityUtil;
import np.com.naxa.factsnepal.utils.SharedPreferenceUtils;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import np.com.naxa.factsnepal.walkthroughscreen.WalkThroughSliderActivity;

import static np.com.naxa.factsnepal.common.Constant.SharedPrefKey.IS_APP_FIRST_TIME_LAUNCH;
import static np.com.naxa.factsnepal.common.Constant.SharedPrefKey.KEY_IS_USER_LOGGED_IN;


public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        makeActivityFullScreen();
        setContentView(R.layout.activity_splash_screen);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                SharedPreferenceUtils sharedPreferenceUtils = new SharedPreferenceUtils(SplashScreenActivity.this);

                if (SharedPreferenceUtils.getInstance(SplashScreenActivity.this).getBoolanValue(IS_APP_FIRST_TIME_LAUNCH, true)) {
                    ActivityUtil.openActivity(WalkThroughSliderActivity.class, SplashScreenActivity.this);
                } else {

                    if (SharedPreferenceUtils.getInstance(SplashScreenActivity.this).getBoolanValue(KEY_IS_USER_LOGGED_IN, false)) {
                        ActivityUtil.openActivity(FactsFeedActivity.class, SplashScreenActivity.this);
                    } else {
                        ActivityUtil.openActivity(LoginActivity.class, SplashScreenActivity.this, null, false);
                    }
                }
            }

        }, 2000);
    }


    private void makeActivityFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

}
