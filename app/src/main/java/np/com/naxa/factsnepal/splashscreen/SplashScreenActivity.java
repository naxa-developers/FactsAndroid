package np.com.naxa.factsnepal.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import np.com.naxa.factsnepal.R;
import np.com.naxa.factsnepal.feed.list.FeedListActivity;
import np.com.naxa.factsnepal.userprofile.LoginActivity;


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
                startActivity(new Intent(SplashScreenActivity.this, FeedListActivity.class));
                finish();
            }
        }, 2000);
    }

}
