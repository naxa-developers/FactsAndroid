package np.com.naxa.factsnepal.surveys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import np.com.naxa.factsnepal.R;
import np.com.naxa.factsnepal.common.BaseActivity;
import np.com.naxa.factsnepal.common.BaseRecyclerViewAdapter;
import np.com.naxa.factsnepal.utils.SharedPreferenceUtils;

import static np.com.naxa.factsnepal.common.Constant.KEY_OBJECT;
import static np.com.naxa.factsnepal.common.Constant.Network.KEY_MAX_RETRY_COUNT;
import static np.com.naxa.factsnepal.common.Constant.SharedPrefKey.KEY_SURVEY_COMPANY_DETAILS_JSON;

public class SurveyCompanyListActivity extends BaseActivity {
    private static final String TAG = "SurveyCompanyListActivity";
    public static final int KEY_COMPANY_TYPE = 001;
    public static final int KEY_FORM_TYPE = 002;
    private BaseRecyclerViewAdapter<SurveyCompany, SurveyItemListVH> adapter;
    List<SurveyCompany> surveyCompanies;


    private RecyclerView recyclerView;
    Gson gson;
    protected SharedPreferenceUtils sharedPreferenceUtils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_company_list);
        gson = new Gson();
        sharedPreferenceUtils = new SharedPreferenceUtils(this);

        setupToolbar("Survey Company List");

        getSurveyListData();

    }



    private void getSurveyListData() {
        createProgressDialog("fetching data.\nPlease wait..");
        if (isNetworkAvailable()) {
            getSurveyQuestionDetailsResponseFromServer();
        } else {
            getSurveyQuestionDetailsResponseFromSharedPref();
        }
    }

    private void getSurveyQuestionDetailsResponseFromServer() {
        final String[] jsonInString = {""};
        surveyCompanies = new ArrayList<SurveyCompany>();

        apiInterface.getSurveyCompanyDetailsResponse()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(KEY_MAX_RETRY_COUNT)
//                .retryWhen(errors -> errors.flatMap(error -> Observable.timer(5, TimeUnit.SECONDS)))
                .subscribe(new DisposableObserver<SurveyCompanyDetails>() {
                    @Override
                    public void onNext(SurveyCompanyDetails surveyCompanyDetails) {
                        jsonInString[0] = gson.toJson(surveyCompanyDetails);
                        sharedPreferenceUtils.setValue(KEY_SURVEY_COMPANY_DETAILS_JSON, jsonInString[0]);
                        surveyCompanies = surveyCompanyDetails.getSurveyCompany();
                    }

                    @Override
                    public void onError(Throwable e) {
                        showToast(e.getMessage());
                        hideProgressDialog();
//                        if(e instanceof SocketTimeoutException){
//                            getSurveyQuestionDetailsResponseFromServer();
//                        }
//                        if(e instanceof EOFException){
//                            getSurveyQuestionDetailsResponseFromServer();
//                        }
                    }

                    @Override
                    public void onComplete() {
                        hideProgressDialog();
                        setupRecyclerView(surveyCompanies);
                    }
                });
    }

    private void getSurveyQuestionDetailsResponseFromSharedPref() {
        surveyCompanies = new ArrayList<SurveyCompany>();

        SurveyCompanyDetails surveyCompanyDetails = gson.fromJson(sharedPreferenceUtils.getStringValue(KEY_SURVEY_COMPANY_DETAILS_JSON, ""), SurveyCompanyDetails.class);

        if(surveyCompanyDetails != null){
            setupRecyclerView(surveyCompanyDetails.getSurveyCompany());
            hideProgressDialog();
        }else {
            showToast("No Offline Data found");
            hideProgressDialog();
        }
    }


    private void setupRecyclerView(List<SurveyCompany> surveyCompanyList) {
        recyclerView = findViewById(R.id.rv_survey_company_list);


        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new BaseRecyclerViewAdapter<SurveyCompany, SurveyItemListVH>(surveyCompanyList, R.layout.survey_list_item_row_layout) {


            @Override
            public void viewBinded(SurveyItemListVH surveyItemListVH, SurveyCompany surveyCompany, int position) {
                surveyItemListVH.bindView(surveyCompany);
                surveyItemListVH.itemView.setOnClickListener((v -> {
                    Intent intent  = new Intent(SurveyCompanyListActivity.this, SurveyStartActivity.class);
                    intent.putExtra(KEY_OBJECT, surveyCompany);
                    startActivity(intent);
                }));
            }

            @Override
            public SurveyItemListVH attachViewHolder(View view) {
                return new SurveyItemListVH(view, KEY_COMPANY_TYPE);
            }
        };

        recyclerView.setAdapter(adapter);

    }


}
