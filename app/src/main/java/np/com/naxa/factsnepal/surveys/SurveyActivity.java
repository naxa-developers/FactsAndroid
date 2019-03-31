package np.com.naxa.factsnepal.surveys;

import android.os.Bundle;
import android.support.annotation.Nullable;

import np.com.naxa.factsnepal.R;
import np.com.naxa.factsnepal.common.BaseActivity;

public class SurveyActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_survey);
        setupToolbar();

    }
}
