package np.com.naxa.factsnepal.surveys;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import np.com.naxa.factsnepal.R;
import np.com.naxa.factsnepal.common.BaseActivity;
import np.com.naxa.factsnepal.common.BaseRecyclerViewAdapter;
import np.com.naxa.factsnepal.utils.SharedPreferenceUtils;

import static np.com.naxa.factsnepal.common.Constant.KEY_OBJECT;

public class SurveyCompanyListActivity extends BaseActivity {
    private static final String TAG = "SurveyCompanyListActivity";
    public static final String KEY_SURVEY_COMPANY_DETAILS_JSON = "survey_company_details_json";
    public static final String KEY_COMPANY_ID = "company_id";
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

        Observable.just(surveyCompanyDetails)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<SurveyCompanyDetails>() {
                    @Override
                    public void onNext(SurveyCompanyDetails surveyCompanyDetails) {
                        surveyCompanies = surveyCompanyDetails.getSurveyCompany();
                    }

                    @Override
                    public void onError(Throwable e) {
                        showToast(e.getMessage());
                        hideProgressDialog();
                    }

                    @Override
                    public void onComplete() {
                        hideProgressDialog();
                        setupRecyclerView(surveyCompanies);
                    }
                });
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
