package np.com.naxa.factsnepal.userprofile;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import np.com.naxa.factsnepal.R;
import np.com.naxa.factsnepal.common.BaseActivity;
import np.com.naxa.factsnepal.common.BaseLoginActivity;
import np.com.naxa.factsnepal.common.BaseRecyclerViewAdapter;
import np.com.naxa.factsnepal.surveys.SurveyEarningDetails;
import np.com.naxa.factsnepal.utils.ImageUtils;
import np.com.naxa.factsnepal.utils.SharedPreferenceUtils;

public class UserProfileInfoActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private BaseRecyclerViewAdapter<SurveyEarningDetails, SurveyEarningHistoryVH> adapter;
    private static final String TAG = "NotificationActivity";

    private TextView tvUserName, tvEmail, tvTotalEarning;
    private ImageView ivUserProfileImage;

    SharedPreferenceUtils sharedPreferenceUtils;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_info);

        sharedPreferenceUtils = new SharedPreferenceUtils(this);
        gson = new Gson();

        setupToolbar("Your Profile");
        iniUI();
        setupRecyclerView(SurveyEarningDetails.getDemoItems());
    }

    private void iniUI(){
        tvUserName = findViewById(R.id.tv_users_username);
        tvEmail = findViewById(R.id.tv_users_email);
        tvTotalEarning = findViewById(R.id.tv_users_total_earning);
        ivUserProfileImage = findViewById(R.id.iv_users_profile_image);

        BaseLoginActivity.UserSocialLoginDetails userSocialLoginDetails = gson.fromJson((sharedPreferenceUtils.getStringValue(LoginActivity.KEY_USER_SOCIAL_LOGGED_IN_DETAILS, null)), BaseLoginActivity.UserSocialLoginDetails.class);

        tvUserName.setText(userSocialLoginDetails.getUser_name());
        tvEmail.setText(userSocialLoginDetails.getUser_email());

        ImageUtils.loadRemoteImage(this, userSocialLoginDetails.getUser_image_url()).circleCrop().into(ivUserProfileImage);
    }

    private void setupRecyclerView (ArrayList<SurveyEarningDetails> surveyEarningDetailsArrayList){
        recyclerView = findViewById(R.id.rv_survey_earning_history);


        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new BaseRecyclerViewAdapter<SurveyEarningDetails, SurveyEarningHistoryVH>(surveyEarningDetailsArrayList, R.layout.user_earning_table_item_layout) {


            @Override
            public void viewBinded(SurveyEarningHistoryVH surveyEarningHistoryVH, SurveyEarningDetails surveyEarningDetails, int position) {
                surveyEarningHistoryVH.bindView(surveyEarningDetails);
                surveyEarningHistoryVH.itemView.setOnClickListener((v -> {

                }));
            }


            @Override
            public SurveyEarningHistoryVH attachViewHolder(View view) {
                return new SurveyEarningHistoryVH(view);
            }
        };

        recyclerView.setAdapter(adapter);

    }
}
