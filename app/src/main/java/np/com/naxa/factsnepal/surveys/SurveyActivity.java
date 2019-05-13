package np.com.naxa.factsnepal.surveys;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import np.com.naxa.factsnepal.utils.JsonView;
import np.com.naxa.factsnepal.utils.SharedPreferenceUtils;

import static np.com.naxa.factsnepal.common.Constant.JsonKeySurveyAnswer.KEY_ANS_ID;
import static np.com.naxa.factsnepal.common.Constant.JsonKeySurveyAnswer.KEY_QN_ID;
import static np.com.naxa.factsnepal.common.Constant.JsonKeySurveyAnswer.KEY_QN_TYPE;
import static np.com.naxa.factsnepal.common.Constant.KEY_EXTRA_OBJECT;
import static np.com.naxa.factsnepal.common.Constant.KEY_OBJECT;
import static np.com.naxa.factsnepal.common.Constant.SharedPrefKey.KEY_RECENT_SURVEY_FORM_DETAILS;

public class SurveyActivity extends BaseActivity {
    private static final String TAG = "SurveyActivity";

    Button btnFormSubmit;
    List<Integer> checkedStringList = new ArrayList<Integer>();
    String checkboxString = "";
    String viewTag = "";
    int viewPosition;
    String lastViewTag = "";

    JSONArray jsonArray = new JSONArray();
    SurveyCompany surveyCompany;
    SurevyForms surevyForms;
    UserLoginResponse userLoginResponse;

    LinearLayout linearLayoutFormList ;

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
                getValueFromView();
            }
        });

    }

    protected void getNewIntent(Intent intent){
        if(intent != null){
            Gson gson = new Gson();

            HashMap<String, Object> hashMap = (HashMap<String, Object>)intent.getSerializableExtra("map");
            surveyCompany = (SurveyCompany)hashMap.get(KEY_OBJECT);
            surevyForms = (SurevyForms)hashMap.get(KEY_EXTRA_OBJECT);
            userLoginResponse = gson.fromJson(SharedPreferenceUtils.getInstance(SurveyActivity.this).getStringValue(Constant.SharedPrefKey.KEY_USER_LOGGED_IN_DETAILS, null), UserLoginResponse.class);

//            try {
//                jsonObject.put("company_id", surveyCompany.getId());
//                jsonObject.put("survey_id", surevyForms.getId());
//                jsonObject.put("user_id", userLoginResponse.getUserLoginDetails().getId());
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }


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

    private void getValueFromView() {
        viewPosition = -1;
        Log.d(TAG, "getValueFromView: " + Constant.generatedViewIdsList.size());
        Observable.just(Constant.generatedViewIdsList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMapIterable(new Function<List<Integer>, Iterable<Integer>>() {
                    @Override
                    public Iterable<Integer> apply(List<Integer> integers) throws Exception {
                        return integers;
                    }
                })
                .map(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer) throws Exception {
                        return integer;
                    }
                })
                .subscribe(new DisposableObserver<Integer>() {
                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "onNext: view id " + integer);

                        try {
                            View view = findViewById(integer);
                            viewTag = view.getTag().toString();
                            viewPosition++;
                            getChildFromLinearLayout(view);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "json to send : "+jsonArray.toString());
                        postFormDataToSerever();
                    }
                });

    }


    public synchronized void getChildFromLinearLayout(View view) throws JSONException {
        if (view == null) {
            Log.d(TAG, "getChildFromLinearLayout: Null View");
            return;
        }
        Log.d(TAG, "getChildFromLinearLayout: " + view.getClass().getSimpleName());


        switch (view.getClass().getSimpleName()) {
            case "TextView":
                getValueFromTextView((TextView) view);
                break;

            case "RadioGroup":
                    jsonArray.put(getValueFromRadioGroup((RadioGroup) view) );
                Log.d(TAG, "getChildFromRadioGroup: " + ((RadioGroup) view).getChildCount());
                break;

            case "RatingBar":
                jsonArray.put(getValueFromRatingBar((RatingBar) view) );
                break;

            case "Spinner":
                    jsonArray.put(getValueFromSpinner((Spinner) view) );
                break;

            case "CheckBox":
                getValueFromCheckBox((CheckBox) view);
                if (TextUtils.equals(viewTag, lastViewTag)) {
                    try {
                        jsonArray.put(getValueFromCheckBox((CheckBox)view));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                break;

            case "LinearLayout":
                LinearLayout linearLayout = (LinearLayout)view ;
                if(linearLayout.getChildAt(0) instanceof CheckBox){
                    jsonArray.put(getValueFromCheckedLinearLayout(linearLayout));
                    Log.d(TAG, "getChildFromLinearLayout: "+linearLayout.getChildCount());
                }
                break;

            case "EditText":
                jsonArray.put(getValueFromEditTex((EditText) view) );
                break;

        }

    }

    public synchronized JSONObject getValueFromCheckedLinearLayout (LinearLayout linearLayout) throws JSONException{
        List<Integer> checkedStringList1 = new ArrayList<Integer>();
        for (int i = 0 ; i<linearLayout.getChildCount(); i++){
            CheckBox checkBox = (CheckBox)linearLayout.getChildAt(i);
            if(checkBox.isChecked()){
                checkedStringList1.add(Integer.parseInt(checkBox.getTag().toString()));
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(KEY_QN_TYPE, Constant.ViewTypeStringKey.KEY_CHECKBOX);
        jsonObject.put(KEY_QN_ID, viewTag);
        jsonObject.put(KEY_ANS_ID, checkedStringList1);

        return jsonObject;
    }

    public synchronized JSONObject getValueFromRadioGroup(RadioGroup radioGroup) throws JSONException {
        RadioButton rb1 = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
        if (rb1 == null) {
            showToast("no any option selected");
            throw new  JSONException("no any option selected");
        }
//        String radiovalue = rb1.getText().toString();
        int radiovalue = Integer.parseInt(rb1.getTag().toString());
        Log.d(TAG, "getValueFromRadioGroup: radiovalue " + radiovalue);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(KEY_QN_TYPE, Constant.ViewTypeStringKey.KEY_RADIO);
        jsonObject.put(KEY_QN_ID, viewTag);
        jsonObject.put(KEY_ANS_ID, radiovalue);
        return jsonObject;
    }

    public synchronized JSONObject getValueFromTextView(TextView textView) throws JSONException {
        Log.d(TAG, "getValueFromTextView: " + textView.getClass().getSimpleName() + " " + textView.getText().toString());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(KEY_QN_TYPE, Constant.ViewTypeStringKey.KEY_TEXTVIEW);
        jsonObject.put(KEY_QN_ID, viewTag);
        jsonObject.put(KEY_ANS_ID, textView.getText().toString());
        return jsonObject;
    }

    public synchronized JSONObject getValueFromEditTex(EditText editText) throws JSONException {
        Log.d(TAG, "getValueFromTextView: " + editText.getClass().getSimpleName() + " " + editText.getText().toString());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(KEY_QN_TYPE, Constant.ViewTypeStringKey.KEY_EDITTEXT);
        jsonObject.put(KEY_QN_ID, viewTag);
        jsonObject.put(KEY_ANS_ID, editText.getText().toString());
        return jsonObject;
    }

    public synchronized JSONObject getValueFromCheckBox(CheckBox checkBox) throws JSONException {

        if (checkBox.isChecked()) {

            if (TextUtils.equals("",viewTag) || TextUtils.equals(lastViewTag,viewTag)) {
                checkedStringList.add(Integer.parseInt(checkBox.getTag().toString()));
            } else {
//                lastViewTag = checkBox.getTag().toString();
                checkedStringList = new ArrayList<Integer>();
                checkedStringList.add(Integer.parseInt(checkBox.getTag().toString()));
            }
        } else {
            int position = checkedStringList.indexOf(Integer.parseInt(checkBox.getTag().toString()));
            checkedStringList.remove(position);
        }

        lastViewTag = viewTag;
        checkboxString = checkedStringList.toString();
        Log.d(TAG, "getValueFromCheckBox: " + checkboxString);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(KEY_QN_TYPE, Constant.ViewTypeStringKey.KEY_CHECKBOX);
        jsonObject.put(KEY_QN_ID, viewTag);
        jsonObject.put(KEY_ANS_ID, checkedStringList);
        return jsonObject;
    }

    public synchronized JSONObject getValueFromRatingBar(RatingBar ratingBar)throws JSONException {
        String rating = String.valueOf(ratingBar.getRating());

        Log.d(TAG, "getValueFromratingBar: " + rating);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(KEY_QN_TYPE, Constant.ViewTypeStringKey.KEY_RATING_BAR);
        jsonObject.put(KEY_QN_ID, viewTag);
        jsonObject.put(KEY_ANS_ID, rating);
        return jsonObject;
    }

    public synchronized JSONObject getValueFromSpinner(Spinner spinner) throws JSONException {
        int selectedSpinnerValuePos = spinner.getSelectedItemPosition();
        int selectedSpinnerValueID = buildUi().optJSONObject(viewPosition).optJSONArray("options").optJSONObject(selectedSpinnerValuePos).optInt("id");
        Log.d(TAG, "getValueFromSpinner: TAG "+selectedSpinnerValueID);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(KEY_QN_TYPE, Constant.ViewTypeStringKey.KEY_SPINNER);
        jsonObject.put(KEY_QN_ID, viewTag);
        jsonObject.put(KEY_ANS_ID, selectedSpinnerValueID);
        return jsonObject;
    }


    private synchronized void generateViewFromJSON (@NonNull JSONArray jsonArray1){
        if(jsonArray1 == null){
            showToast("null data \ncan't create view");
            return;
        }
        Observable.just(jsonArray1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<JSONArray, ObservableSource<JSONObject>>() {
                    @Override
                    public ObservableSource<JSONObject> apply(JSONArray jsonArray) throws Exception {
                        return Observable.range(0,jsonArray.length())
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



    private void postFormDataToSerever (){
        apiInterface.postSurveyAnswerDetailsResponse(userLoginResponse.getUserLoginDetails().getId(),
                surveyCompany.getId(), surevyForms.getId(), jsonArray.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<PostSurveyAnswerResponse>() {
                    @Override
                    public void onNext(PostSurveyAnswerResponse postSurveyAnswerResponse) {

                        if (postSurveyAnswerResponse.isSuccess()){
                            Constant.generatedViewIdsList = null;
                            Constant.generatedViewIdsList = new ArrayList<Integer>();

                            ActivityUtil.openActivity(SurveyCompanyListActivity.class, SurveyActivity.this);
                            onDestroy();
                            finish();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {

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







