package np.com.naxa.factsnepal.surveys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.EOFException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import np.com.naxa.factsnepal.FactsNepal;
import np.com.naxa.factsnepal.R;
import np.com.naxa.factsnepal.common.BaseActivity;
import np.com.naxa.factsnepal.common.Constant;
import np.com.naxa.factsnepal.userprofile.UserLoginResponse;
import np.com.naxa.factsnepal.utils.ActivityUtil;
import np.com.naxa.factsnepal.utils.GetDataFromJsonView;
import np.com.naxa.factsnepal.utils.JsonView;
import np.com.naxa.factsnepal.utils.SharedPreferenceUtils;

import static np.com.naxa.factsnepal.common.Constant.JsonKeySurvey.KEY_ANSWER;
import static np.com.naxa.factsnepal.common.Constant.JsonKeySurvey.KEY_COMPANY_ID;
import static np.com.naxa.factsnepal.common.Constant.JsonKeySurvey.KEY_SURVEY_ID;
import static np.com.naxa.factsnepal.common.Constant.JsonKeySurvey.KEY_USER_ID;
import static np.com.naxa.factsnepal.common.Constant.KEY_EXTRA_OBJECT;
import static np.com.naxa.factsnepal.common.Constant.KEY_OBJECT;
import static np.com.naxa.factsnepal.common.Constant.SharedPrefKey.KEY_RECENT_SURVEY_FORM_DETAILS;

public class SurveyActivity extends BaseActivity {
    private static final String TAG = "SurveyActivity";

    Button btnFormSubmit;

    JSONObject jsonObject = new JSONObject();
    JSONArray jsonArray = new JSONArray();
    SurveyCompany surveyCompany;
    SurevyForms surevyForms;
    UserLoginResponse userLoginResponse;

    LinearLayout linearLayoutFormList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        btnFormSubmit = findViewById(R.id.btn_form_submit);
        setupToolbar("Survey Activity");
        linearLayoutFormList = findViewById(R.id.ll_survey_form_list);

        getNewIntent(getIntent());

        generateViewFromJSON(buildUi());

        btnFormSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonArray = new JSONArray();
                getDataFromJsonVIew();
            }
        });

    }

    protected void getNewIntent(Intent intent) {
        if (intent != null) {
            Gson gson = new Gson();

            HashMap<String, Object> hashMap = (HashMap<String, Object>) intent.getSerializableExtra("map");
            surveyCompany = (SurveyCompany) hashMap.get(KEY_OBJECT);
            surevyForms = (SurevyForms) hashMap.get(KEY_EXTRA_OBJECT);
            userLoginResponse = gson.fromJson(SharedPreferenceUtils.getInstance(SurveyActivity.this).getStringValue(Constant.SharedPrefKey.KEY_USER_LOGGED_IN_DETAILS, null), UserLoginResponse.class);

            try {
                jsonObject.put(KEY_COMPANY_ID, surveyCompany.getId());
                jsonObject.put(KEY_SURVEY_ID, surevyForms.getId());
                jsonObject.put(KEY_USER_ID, userLoginResponse.getUserLoginDetails().getId());
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    private synchronized JSONArray buildUi() {
        try {
            String json1 = SharedPreferenceUtils.getInstance(this).getStringValue(KEY_RECENT_SURVEY_FORM_DETAILS, "");
            JSONObject jsonObject = new JSONObject(json1);
            JSONArray jsonArray = jsonObject.getJSONArray("survey_question");
            String json = " [\n" +
                    "       {\n" +
                    "           \"id\": 1,\n" +
                    "           \"question\": \"Question no. one?\",\n" +
                    "           \"survey_id\": 6,\n" +
                    "           \"question_type\": \"radio\",\n" +
                    "           \"options\": [\n" +
                    "               {\n" +
                    "                   \"id\": 1,\n" +
                    "                   \"question\": \"New Party\",\n" +
                    "                   \"question_id\": 1\n" +
                    "               },\n" +
                    "               {\n" +
                    "                   \"id\": 2,\n" +
                    "                   \"question\": \"Old Party\",\n" +
                    "                   \"question_id\": 1\n" +
                    "               }\n" +
                    "           ]\n" +
                    "       },\n" +
                    "       {\n" +
                    "           \"id\": 2,\n" +
                    "           \"question\": \"uy\",\n" +
                    "           \"survey_id\": 6,\n" +
                    "           \"question_type\": \"radio\",\n" +
                    "           \"options\": [\n" +
                    "               {\n" +
                    "                   \"id\": 3,\n" +
                    "                   \"question\": \"Yes\",\n" +
                    "                   \"question_id\": 2\n" +
                    "               },\n" +
                    "               {\n" +
                    "                   \"id\": 4,\n" +
                    "                   \"question\": \"No\",\n" +
                    "                   \"question_id\": 2\n" +
                    "               }\n" +
                    "           ]\n" +
                    "       },\n" +
                    "       {\n" +
                    "           \"id\": 3,\n" +
                    "           \"question\": \"nh\",\n" +
                    "           \"survey_id\": 6,\n" +
                    "           \"question_type\": \"radio\",\n" +
                    "           \"options\": [\n" +
                    "               {\n" +
                    "                   \"id\": 5,\n" +
                    "                   \"question\": \"No\",\n" +
                    "                   \"question_id\": 3\n" +
                    "               },\n" +
                    "               {\n" +
                    "                   \"id\": 6,\n" +
                    "                   \"question\": \"New Party\",\n" +
                    "                   \"question_id\": 3\n" +
                    "               }\n" +
                    "           ]\n" +
                    "       },\n" +
                    "       {\n" +
                    "           \"id\": 4,\n" +
                    "           \"question\": \"Select Options.\",\n" +
                    "           \"survey_id\": 7,\n" +
                    "           \"question_type\": \"checkbox\",\n" +
                    "           \"options\": [\n" +
                    "               {\n" +
                    "                   \"id\": 5,\n" +
                    "                   \"question\": \"Option One\",\n" +
                    "                   \"question_id\": 4\n" +
                    "               },\n" +
                    "               {\n" +
                    "                   \"id\": 6,\n" +
                    "                   \"question\": \"Option Two\",\n" +
                    "                   \"question_id\": 4\n" +
                    "               }\n" +
                    "           ]\n" +
                    "       },\n" +
                    "       {\n" +
                    "           \"id\": 5,\n" +
                    "           \"question\": \"Rate out of five.\",\n" +
                    "           \"survey_id\": 7,\n" +
                    "           \"question_type\": \"rating\",\n" +
                    "           \"options\": [\n" +
                    "               {\n" +
                    "                   \"id\": 6,\n" +
                    "                   \"question\": \"5\",\n" +
                    "                   \"question_id\": 5\n" +
                    "               }" +
                    "           ]\n" +
                    "       },\n" +
                    "       {\n" +
                    "           \"id\": 7,\n" +
                    "           \"question\": \"Full Name.\",\n" +
                    "           \"survey_id\": 7,\n" +
                    "           \"question_type\": \"edittext\",\n" +
                    "           \"options\": [\n" +
                    "               {\n" +
                    "                   \"id\": 6,\n" +
                    "                   \"question\": \"Please enter your name\",\n" +
                    "                   \"question_id\": 7\n" +
                    "               }" +
                    "           ]\n" +
                    "       }\n" +
                    "   ]";
            Log.d(TAG, "buildUi: " + jsonArray.toString());
//            Log.d(TAG, "buildUi: " + SurveyQuestionDetailsResponse.getDemoJson());
//            return new JSONArray(SurveyQuestionDetailsResponse.getDemoJson());
            return jsonArray;
        } catch (Exception e) {
            return new JSONArray();
        }
    }


    private synchronized void generateViewFromJSON(@NonNull JSONArray jsonArray1) {
        if (jsonArray1 == null) {
            showToast("null data \ncan't create view");
            return;
        }
        Observable.just(jsonArray1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<JSONArray, ObservableSource<JSONObject>>() {
                    @Override
                    public ObservableSource<JSONObject> apply(JSONArray jsonArray) throws Exception {
                        return Observable.range(0, jsonArray.length())
                                .map(new Function<Integer, JSONObject>() {
                                    @Override
                                    public JSONObject apply(Integer index) throws Exception {
                                        return jsonArray.getJSONObject(index);
                                    }
                                });
                    }
                })
                .map(new Function<JSONObject, JSONObject>() {
                    @Override
                    public JSONObject apply(JSONObject jsonObject) throws Exception {
                        return jsonObject;
                    }
                })
                .subscribe(new DisposableObserver<JSONObject>() {
                    @Override
                    public void onNext(JSONObject jsonObject) {
                        linearLayoutFormList.addView(convert(jsonObject));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    private CardView convert(JSONObject jsonObject) {
        try {
            JsonView.ViewParams params = JsonView.ViewParams.instanceFromJSON(jsonObject);
            JsonView jsonView = new JsonView(SurveyActivity.this);
            jsonView.init(params).create();

            return wrapByCardView(jsonView);
        } catch (Exception e) {
            e.printStackTrace();
            return wrapByCardView(new LinearLayout(SurveyActivity.this));
        }
    }

    private CardView wrapByCardView(LinearLayout linearLayout) {
        CardView card = new CardView(FactsNepal.getInstance());

        // Set the CardView layoutParams
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(16, 16, 16, 16);
        card.setLayoutParams(params);

        // Set CardView corner radius
        card.setRadius(9);

        // Set cardView content padding
        card.setContentPadding(15, 15, 15, 15);

        // Set a background color for CardView
        card.setCardBackgroundColor(FactsNepal.getInstance().getResources().getColor(R.color.silver_grey_windows_background));

        // Set the CardView maximum elevation
        card.setMaxCardElevation(15);

        // Set CardView elevation
        card.setCardElevation(9);


        // Put the TextView in CardView
        card.addView(linearLayout);

        return card;
    }


    private void getDataFromJsonVIew() {
        createProgressDialog("Sending data");
        new GetDataFromJsonView(SurveyActivity.this, buildUi()) {
            @Override
            public void onErrorListner(String errorMsg) {
                showToast(errorMsg);
                hideProgressDialog();
            }

            @Override
            public void onCompleteListner(JSONArray answerJsonArray) {
                Log.d(TAG, "json onCompleteListner: " + answerJsonArray);
                try {
                    jsonObject.put(KEY_ANSWER, answerJsonArray);
                    postFormDataToSerever();
                } catch (JSONException e) {
                    showToast(e.getMessage());
                    hideProgressDialog();
                    e.printStackTrace();
                }
            }
        };
    }


    private void postFormDataToSerever() {
        apiInterface.postSurveyAnswerDetailsResponse(jsonObject.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<PostSurveyAnswerResponse>() {
                    @Override
                    public void onNext(PostSurveyAnswerResponse postSurveyAnswerResponse) {

                        hideProgressDialog();
                        if (postSurveyAnswerResponse.getSuccess()) {
                            showToast(postSurveyAnswerResponse.getMessage());
                            Constant.generatedViewIdsList = null;
                            Constant.generatedViewIdsList = new ArrayList<Integer>();
                            jsonArray = new JSONArray();

                            ActivityUtil.openActivity(SurveyCompanyListActivity.class, SurveyActivity.this);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        hideProgressDialog();
                        if (e instanceof EOFException || e instanceof SocketTimeoutException) {
                            createProgressDialog("Retrying Data send");
                            postFormDataToSerever();
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected void onDestroy() {
        JsonView jsonView = new JsonView(SurveyActivity.this);
        jsonView.destroy();
        super.onDestroy();
    }
}







