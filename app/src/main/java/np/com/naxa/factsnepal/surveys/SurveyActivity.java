package np.com.naxa.factsnepal.surveys;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import np.com.naxa.factsnepal.FactsNepal;
import np.com.naxa.factsnepal.R;
import np.com.naxa.factsnepal.common.BaseActivity;
import np.com.naxa.factsnepal.common.Constant;
import np.com.naxa.factsnepal.surveys.surveyforms.SurveyQuestionDetailsResponse;
import np.com.naxa.factsnepal.utils.ActivityUtil;
import np.com.naxa.factsnepal.utils.JsonView;
import np.com.naxa.factsnepal.utils.SharedPreferenceUtils;

import static np.com.naxa.factsnepal.common.Constant.SharedPrefKey.KEY_RECENT_SURVEY_FORM_DETAILS;

public class SurveyActivity extends BaseActivity {
    private static final String TAG = "SurveyActivity";
    Button btnFormSubmit;
    List<String> checkedStringList = new ArrayList<String>();
    String checkboxString = "";
    String viewTag = "";
    String lastViewTag = "";

    JSONObject jsonObject = new JSONObject();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        btnFormSubmit = findViewById(R.id.btn_form_submit);
        setupToolbar("Survey Activity");
        RecyclerView recyclerView = findViewById(R.id.rv_survey);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new SurveyAdapter(buildUi()));

        btnFormSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValueFromView();
            }
        });

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
                        Constant.generatedViewIdsList = null;
                        Log.d(TAG, "json to send : "+jsonObject.toString());
                        ActivityUtil.openActivity(SurveyCompanyListActivity.class, SurveyActivity.this);


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

            case "Spinner":
                    jsonObject.put(viewTag, getValueFromSpinner((Spinner) view) );
                break;

            case "CheckBox":
                getValueFromCheckBox((CheckBox) view);
                if (!TextUtils.equals(viewTag, lastViewTag)) {
                    try {
                        jsonObject.put(viewTag, checkboxString);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                break;

            case "EditText":
                    jsonObject.put(viewTag, getValueFromEditTex((EditText) view) );
                break;

        }

    }

    public synchronized String getValueFromRadioGroup(RadioGroup radioGroup) throws Exception {
        RadioButton rb1 = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
        if (rb1 == null) {
            showToast("no any option selected");
            throw new  Exception();
        }
        String radiovalue = rb1.getText().toString();
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

            if (TextUtils.equals("",viewTag) || TextUtils.equals(viewTag,checkBox.getTag().toString())) {
                checkedStringList.add(checkBox.getText().toString());
            } else {
                lastViewTag = checkBox.getTag().toString();
                checkedStringList = new ArrayList<String>();
                checkedStringList.add(checkBox.getText().toString());

            }
        } else {
            int position = checkedStringList.indexOf(checkBox.getText().toString());
            checkedStringList.remove(position);
        }

        viewTag = checkBox.getTag().toString();
        checkboxString = checkedStringList.toString();
        Log.d(TAG, "getValueFromCheckBox: " + checkboxString);
    }

    public synchronized String getValueFromratingBar(RatingBar ratingBar) {
        String rating = String.valueOf(ratingBar.getRating());
        Log.d(TAG, "getValueFromratingBar: " + rating);
        return rating;
    }

    public synchronized String getValueFromSpinner(Spinner spinner) {
        String selectedSpinnerValue = String.valueOf(spinner.getSelectedItem());
        Log.d(TAG, "getValueFromratingBar: " + selectedSpinnerValue);
        return selectedSpinnerValue;
    }

}

class SurveyAdapter extends RecyclerView.Adapter<SurveyViewHolder> {
    private static final String TAG = "SurveyAdapter";
    JSONArray surveyArray;
    Context context;
    int arrayCounter = -1;

    public SurveyAdapter(JSONArray surveyArray) {
        this.surveyArray = surveyArray;
    }

    private CardView convert(JSONObject jsonObject, Context context) {
        this.context = context;
        try {
            JsonView.ViewParams params = JsonView.ViewParams.instanceFromJSON(jsonObject);
            JsonView jsonView = new JsonView(context);
            jsonView.init(params).create();

            return wrapByCardView(jsonView);
        } catch (Exception e) {
            e.printStackTrace();
            return wrapByCardView(new LinearLayout(context));
        }
    }

    @NonNull
    @Override
    public SurveyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        arrayCounter++;

        Log.d(TAG, "onCreateViewHolder: position "+i);
        Log.d(TAG, "onCreateViewHolder: arrayCounter "+arrayCounter);
        return new SurveyViewHolder(convert(surveyArray.optJSONObject(arrayCounter), viewGroup.getContext()));
    }

    @Override
    public void onBindViewHolder(@NonNull SurveyViewHolder surveyViewHolder, int i) {
//        surveyViewHolder.setIsRecyclable(false);
    }

    @Override
    public int getItemCount() {
        Log.d("SurveyRecycler", "getItemCount: " + surveyArray.length());
        return surveyArray.length();
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


class SurveyViewHolder extends RecyclerView.ViewHolder {
    public SurveyViewHolder(View view) {
        super(view);
    }

}






