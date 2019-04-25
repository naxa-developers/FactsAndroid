package np.com.naxa.factsnepal.preferences;

import android.os.Bundle;
import androidx.annotation.Nullable;

import np.com.naxa.factsnepal.R;
import np.com.naxa.factsnepal.common.BaseActivity;

public class PreferencesActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prefences);
        setupToolbar("Settings");
    }
}
