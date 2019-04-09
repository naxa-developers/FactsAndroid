package np.com.naxa.factsnepal.surveys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import np.com.naxa.factsnepal.R;
import np.com.naxa.factsnepal.common.BaseActivity;

public class SurveyCompanyListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_company_list);

        setupToolbar("Survey Company List");

    }
}
