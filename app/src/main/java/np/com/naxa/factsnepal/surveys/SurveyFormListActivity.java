package np.com.naxa.factsnepal.surveys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import np.com.naxa.factsnepal.R;
import np.com.naxa.factsnepal.common.BaseActivity;
import np.com.naxa.factsnepal.common.BaseRecyclerViewAdapter;
import np.com.naxa.factsnepal.surveys.surveyforms.SurveyQuestionDetailsResponse;
import np.com.naxa.factsnepal.utils.ActivityUtil;
import np.com.naxa.factsnepal.utils.SharedPreferenceUtils;

import static np.com.naxa.factsnepal.common.Constant.KEY_EXTRA_OBJECT;
import static np.com.naxa.factsnepal.common.Constant.KEY_OBJECT;
import static np.com.naxa.factsnepal.common.Constant.SharedPrefKey.KEY_RECENT_SURVEY_FORM_DETAILS;
import static np.com.naxa.factsnepal.surveys.SurveyCompanyListActivity.KEY_FORM_TYPE;
import static np.com.naxa.factsnepal.surveys.SurveyCompanyListActivity.KEY_SURVEY_COMPANY_DETAILS_JSON;

public class SurveyFormListActivity extends BaseActivity {
    private static final String TAG = "SurveyFormsListActivity";
    private BaseRecyclerViewAdapter<SurevyForms, SurveyItemListVH> adapter;
    List<SurevyForms> surevyFormsList;


    private RecyclerView recyclerView;
    Gson gson;
    protected SharedPreferenceUtils sharedPreferenceUtils;
    SurveyCompany surveyCompany;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_form_list);

        Intent intent = getIntent();
        surveyCompany = intent.getParcelableExtra(KEY_OBJECT);
        gson = new Gson();
        sharedPreferenceUtils = new SharedPreferenceUtils(this);
        setupToolbar(surveyCompany.getTitle());

        getSurveyFormsFromSharedPref();
    }

    private void getSurveyFormsFromSharedPref() {
        surevyFormsList = new ArrayList<SurevyForms>();

        SurveyCompanyDetails surveyCompanyDetails = gson.fromJson(sharedPreferenceUtils.getStringValue(KEY_SURVEY_COMPANY_DETAILS_JSON, ""), SurveyCompanyDetails.class);

        Observable.just(surveyCompanyDetails)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<SurveyCompanyDetails>() {
                    @Override
                    public void onNext(SurveyCompanyDetails surveyCompanyDetails) {
                        
                        for(SurevyForms surevyForms : surveyCompanyDetails.getSurevyForms()){
                            if(surevyForms.getSurveyCompanyId() ==  surveyCompany.getId()){
                                surevyFormsList.add(surevyForms);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        showToast(e.getMessage());
                        hideProgressDialog();
                    }

                    @Override
                    public void onComplete() {
                        hideProgressDialog();
                        setupRecyclerView(surevyFormsList);
                    }
                });
    }

    private void setupRecyclerView(List<SurevyForms> surevyFormsList) {
        recyclerView = findViewById(R.id.rv_survey_form_list);


        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new BaseRecyclerViewAdapter<SurevyForms, SurveyItemListVH>(surevyFormsList, R.layout.survey_list_item_row_layout) {


            @Override
            public void viewBinded(SurveyItemListVH surveyItemListVH, SurevyForms surevyForms, int position) {
                surveyItemListVH.bindView(surevyForms);
                surveyItemListVH.itemView.setOnClickListener((v -> {
                    fetctQuestioOptions(surevyForms);
                }));
            }

            @Override
            public SurveyItemListVH attachViewHolder(View view) {
                return new SurveyItemListVH(view, KEY_FORM_TYPE);
            }
        };

        recyclerView.setAdapter(adapter);

    }

    boolean isQuestionNotNull = false;

    private void fetctQuestioOptions(SurevyForms surevyForms) {
        if (!isNetworkAvailable()) {
            showToast("No Internet Connection");
            return;
        }

            apiInterface.getSurveyQuestionDetailsResponse(surveyCompany.getId(), surevyForms.getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableObserver<SurveyQuestionDetailsResponse>() {
                        @Override
                        public void onNext(SurveyQuestionDetailsResponse surveyQuestionDetailsResponse) {

                            if (surveyQuestionDetailsResponse == null) {
                                showToast("Null response from the server.");
                                isQuestionNotNull = false;
                            } else {
                                Log.d(TAG, "onNext: "+gson.toJson(surveyQuestionDetailsResponse));
                                isQuestionNotNull = true;
                                sharedPreferenceUtils.setValue(KEY_RECENT_SURVEY_FORM_DETAILS, gson.toJson(surveyQuestionDetailsResponse));
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            showToast(e.getMessage());
                            Log.d(TAG, "onError: "+e.getMessage());
                        }

                        @Override
                        public void onComplete() {
                            if (isQuestionNotNull) {
                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put(KEY_OBJECT, surveyCompany);
                                hashMap.put(KEY_EXTRA_OBJECT, surevyForms);
                                ActivityUtil.openActivity(SurveyActivity.class, SurveyFormListActivity.this, hashMap, false);

                            }
                        }
                    });
    }
}
