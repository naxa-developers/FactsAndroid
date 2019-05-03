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
import np.com.naxa.factsnepal.userprofile.UserLoginDetails;
import np.com.naxa.factsnepal.userprofile.UserLoginResponse;
import np.com.naxa.factsnepal.utils.ActivityUtil;
import np.com.naxa.factsnepal.utils.JsonView;
import np.com.naxa.factsnepal.utils.SharedPreferenceUtils;

import static np.com.naxa.factsnepal.common.Constant.KEY_EXTRA_OBJECT;
import static np.com.naxa.factsnepal.common.Constant.KEY_OBJECT;
import static np.com.naxa.factsnepal.common.Constant.SharedPrefKey.KEY_RECENT_SURVEY_FORM_DETAILS;

public class SurveyActivity extends BaseActivity {
    private static final String TAG = "SurveyActivity";
    Button btnFormSubmit;
    List<String> checkedStringList = new ArrayList<String>();
    String checkboxString = "";
    String viewTag = "";
    String lastViewTag = "";

    JSONObject jsonObject = new JSONObject();
    SurveyCompany surveyCompany;
    SurevyForms surevyForms;

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
            UserLoginResponse userLoginResponse = gson.fromJson(SharedPreferenceUtils.getInstance(SurveyActivity.this).getStringValue(Constant.SharedPrefKey.KEY_USER_LOGGED_IN_DETAILS, null), UserLoginResponse.class);

            try {
                jsonObject.put("company_id", surveyCompany.getId());
                jsonObject.put("survey_id", surevyForms.getId());
                jsonObject.put("user_id", userLoginResponse.getUserLoginDetails().getId());
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

    private void getValueFromView() {
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
                        Log.d(TAG, "json to send : "+jsonObject.toString());
                        Constant.generatedViewIdsList = null;
                        Constant.generatedViewIdsList = new ArrayList<Integer>();
//                        ActivityUtil.openActivity(SurveyCompanyListActivity.class, SurveyActivity.this);


                    }
                });

    }


    public synchronized void getChildFromLinearLayout(View view) throws Exception {
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
                    jsonObject.put(viewTag, getValueFromRadioGroup((RadioGroup) view) );
                Log.d(TAG, "getChildFromRadioGroup: " + ((RadioGroup) view).getChildCount());
                break;

            case "RatingBar":
                    jsonObject.put(viewTag, getValueFromratingBar((RatingBar) view) );
                break;

            case "CheckBox":
                getValueFromCheckBox((CheckBox) view);
                if (TextUtils.equals(viewTag, lastViewTag)) {
                    try {
                        jsonObject.put(viewTag, checkboxString);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                break;

            case "LinearLayout":
                LinearLayout linearLayout = (LinearLayout)view ;
                if(linearLayout.getChildAt(0) instanceof CheckBox){
                    jsonObject.put(viewTag, getValueFromCheckedLinearLayout(linearLayout));
                    Log.d(TAG, "getChildFromLinearLayout: "+linearLayout.getChildCount());
                }
                break;

            case "EditText":
                    jsonObject.put(viewTag, getValueFromEditTex((EditText) view) );
                break;

        }

    }

    public synchronized String getValueFromCheckedLinearLayout (LinearLayout linearLayout){
        checkedStringList = new ArrayList<String>();
        for (int i = 0 ; i<linearLayout.getChildCount(); i++){
            CheckBox checkBox = (CheckBox)linearLayout.getChildAt(i);
            if(checkBox.isChecked()){
                checkedStringList.add(checkBox.getTag().toString());
            }
        }

        return checkedStringList.toString();
    }

    public synchronized String getValueFromRadioGroup(RadioGroup radioGroup) throws Exception {
        RadioButton rb1 = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
        if (rb1 == null) {
            showToast("no any option selected");
            throw new  Exception();
        }
//        String radiovalue = rb1.getText().toString();
        String radiovalue = rb1.getTag().toString();
        Log.d(TAG, "getValueFromRadioGroup: radiovalue " + radiovalue);
        return radiovalue;
    }

    public synchronized String getValueFromTextView(TextView textView) {
        Log.d(TAG, "getValueFromTextView: " + textView.getClass().getSimpleName() + " " + textView.getText().toString());
        return textView.getText().toString();
    }

    public synchronized String getValueFromEditTex(EditText editText) {
        Log.d(TAG, "getValueFromTextView: " + editText.getClass().getSimpleName() + " " + editText.getText().toString());
        return editText.getText().toString();
    }

    public synchronized void getValueFromCheckBox(CheckBox checkBox) {

        if (checkBox.isChecked()) {

            if (TextUtils.equals("",viewTag) || TextUtils.equals(lastViewTag,viewTag)) {
                checkedStringList.add(checkBox.getText().toString());
            } else {
//                lastViewTag = checkBox.getTag().toString();
                checkedStringList = new ArrayList<String>();
                checkedStringList.add(checkBox.getText().toString());

            }
        } else {
            int position = checkedStringList.indexOf(checkBox.getText().toString());
            checkedStringList.remove(position);
        }

        lastViewTag = viewTag;
        checkboxString = checkedStringList.toString();
        Log.d(TAG, "getValueFromCheckBox: " + checkboxString);
    }

    public synchronized String getValueFromratingBar(RatingBar ratingBar) {
        String rating = String.valueOf(ratingBar.getRating());
        Log.d(TAG, "getValueFromratingBar: " + rating);
        return rating;
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


}







