package np.com.naxa.factsnepal.surveys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import np.com.naxa.factsnepal.R;
import np.com.naxa.factsnepal.common.BaseActivity;
import np.com.naxa.factsnepal.utils.ActivityUtil;
import np.com.naxa.factsnepal.utils.ImageUtils;

import static np.com.naxa.factsnepal.common.Constant.KEY_OBJECT;

public class SurveyStartActivity extends BaseActivity implements View.OnClickListener {

    private int companyId ;
    SurveyCompany surveyCompany;

    TextView tvCOmpanyTitle, tvCompanyDesc;
    ImageView ivCompanyImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_survey_start);

        Intent intent = getIntent();
        surveyCompany = intent.getParcelableExtra(KEY_OBJECT);
        setupToolbar(surveyCompany.getTitle());


        bindUI();

    }

    private void bindUI() {
        findViewById(R.id.btn_start_survey).setOnClickListener(this);
        tvCOmpanyTitle = findViewById(R.id.tv_company_name);
        tvCompanyDesc = findViewById(R.id.tv_company_description);
        ivCompanyImage = findViewById(R.id.iv_company_image);

        tvCOmpanyTitle.setText(surveyCompany.getTitle());
        tvCompanyDesc.setText(surveyCompany.getDescription());
        ImageUtils.loadRemoteImage(this, surveyCompany.getImage()).circleCrop().into(ivCompanyImage);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start_survey:
                Intent intent = new Intent(SurveyStartActivity.this, SurveyFormListActivity.class);
                intent.putExtra(KEY_OBJECT, surveyCompany);
                startActivity(intent);
                break;
        }
    }
}
