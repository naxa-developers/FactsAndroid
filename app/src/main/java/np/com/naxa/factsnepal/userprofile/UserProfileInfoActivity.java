package np.com.naxa.factsnepal.userprofile;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import np.com.naxa.factsnepal.R;
import np.com.naxa.factsnepal.common.BaseActivity;
import np.com.naxa.factsnepal.common.BaseRecyclerViewAdapter;
import np.com.naxa.factsnepal.surveys.SurveyEarningDetails;

public class UserProfileInfoActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private BaseRecyclerViewAdapter<SurveyEarningDetails, SurveyEarningHistoryVH> adapter;
    private static final String TAG = "NotificationActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_info);

        setupToolbar("Your Profile");

        setupRecyclerView(SurveyEarningDetails.getDemoItems());
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
