package np.com.naxa.factsnepal.surveys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import np.com.naxa.factsnepal.R;
import np.com.naxa.factsnepal.common.BaseActivity;
import np.com.naxa.factsnepal.common.BaseRecyclerViewAdapter;
import np.com.naxa.factsnepal.userprofile.SurveyEarningHistoryVH;

public class SurveyCompanyListActivity extends BaseActivity {
    private static final String TAG = "SurveyCompanyListActivity";
    private BaseRecyclerViewAdapter<SurveyCompany, SurveyItemListVH> adapter;

    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_company_list);

        setupToolbar("Survey Company List");

        getSurveyListData();

    }

    private void getSurveyListData(){
        if (isNetworkAvailable()) {
            createProgressDialog("fetching data.\nPlease wait..");
            getSurveyWuestionDetailsResponse();
        }else {

        }
    }

    List<SurveyCompany> surveyCompanies;
    private void getSurveyWuestionDetailsResponse(){
        final String[] jsonInString = {""};
        Gson gson = new Gson();

        surveyCompanies = new ArrayList<SurveyCompany>();

        apiInterface.getSurveyQuestionDetailsResponse()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<SurveyQuestionDetails>() {
                    @Override
                    public void onNext(SurveyQuestionDetails surveyQuestionDetails) {
                        jsonInString[0] = gson.toJson(surveyQuestionDetails);
                        surveyCompanies = surveyQuestionDetails.getSurveyCompany();
                        Log.d(TAG, "onComplete: company list size "+surveyQuestionDetails.getSurveyCompany().size());

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        hideProgressDialog();
                        setupRecyclerView(surveyCompanies);
                        Log.d(TAG, "onComplete: "+jsonInString[0]);
                        Log.d(TAG, "onComplete: company list size "+surveyCompanies.size());
                    }
                });
    }


    private void setupRecyclerView (List<SurveyCompany> surveyCompanyList){
        recyclerView = findViewById(R.id.rv_survey_company_list);


        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new BaseRecyclerViewAdapter<SurveyCompany, SurveyItemListVH>(surveyCompanyList, R.layout.survey_list_item_row_layout) {


            @Override
            public void viewBinded(SurveyItemListVH surveyItemListVH, SurveyCompany surveyCompany, int position) {
                surveyItemListVH.bindView(surveyCompany);
                surveyItemListVH.itemView.setOnClickListener((v -> {

                }));
            }

            @Override
            public SurveyItemListVH attachViewHolder(View view) {
                return new SurveyItemListVH(view);
            }
        };

        recyclerView.setAdapter(adapter);

    }




}
