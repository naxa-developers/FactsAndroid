package np.com.naxa.factsnepal.userprofile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import np.com.naxa.factsnepal.R;
import np.com.naxa.factsnepal.common.BaseActivity;

public class UserProfileInfoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_info);

        setupToolbar("Your Profile");
    }
}
