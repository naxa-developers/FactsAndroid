package np.com.naxa.factsnepal.surveys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import static np.com.naxa.factsnepal.common.Constant.KEY_EXTRA_OBJECT;
import static np.com.naxa.factsnepal.common.Constant.KEY_OBJECT;
import static np.com.naxa.factsnepal.surveys.SurveyCompanyListActivity.KEY_FORM_TYPE;
import static np.com.naxa.factsnepal.surveys.SurveyCompanyListActivity.KEY_SURVEY_QUESTION_DETAILS_JSON;

public class SurveyFormListActivity extends BaseActivity {
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

        SurveyCompanyDetails surveyCompanyDetails = gson.fromJson(sharedPreferenceUtils.getStringValue(KEY_SURVEY_QUESTION_DETAILS_JSON, ""), SurveyCompanyDetails.class);

        Observable.just(surveyCompanyDetails)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<SurveyCompanyDetails>() {
                    @Override
                    public void onNext(SurveyCompanyDetails surveyCompanyDetails) {
                        surevyFormsList = surveyCompanyDetails.getSurevyForms();
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
                    Intent intent  = new Intent(SurveyFormListActivity.this, SurveyActivity.class);
                    intent.putExtra(KEY_OBJECT, surveyCompany);
                    intent.putExtra(KEY_EXTRA_OBJECT, surevyForms);
                    startActivity(intent);
                }));
            }

            @Override
            public SurveyItemListVH attachViewHolder(View view) {
                return new SurveyItemListVH(view, KEY_FORM_TYPE);
            }
        };

        recyclerView.setAdapter(adapter);

    }
}
