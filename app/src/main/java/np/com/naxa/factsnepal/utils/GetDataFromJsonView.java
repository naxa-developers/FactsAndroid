package np.com.naxa.factsnepal.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonIOException;

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
import np.com.naxa.factsnepal.common.Constant;

import static np.com.naxa.factsnepal.common.Constant.JsonKeySurvey.KEY_ANS_ID;
import static np.com.naxa.factsnepal.common.Constant.JsonKeySurvey.KEY_QN_ID;
import static np.com.naxa.factsnepal.common.Constant.JsonKeySurvey.KEY_QN_TYPE;

public abstract class GetDataFromJsonView {
    private static final String TAG = "GetDataFromJsonView";
    List<Integer> checkedStringList = new ArrayList<Integer>();
    String checkboxString = "";
    String viewTag = "";
    int viewPosition;
    String lastViewTag = "";

    JSONArray jsonArray = new JSONArray();
    JSONArray rawQnBuilderJsonArray;
    Context context;
    AppCompatActivity activity;

    public GetDataFromJsonView(AppCompatActivity activity, JSONArray rawQnBuilderJsonArray) {
        this.context = activity;
        this.activity = activity;
        this.rawQnBuilderJsonArray = rawQnBuilderJsonArray;
        getValueFromView();
    }


    public void getValueFromView() {
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
                            View view = activity.findViewById(integer);
                            viewTag = view.getTag().toString();
                            viewPosition++;
                            getChildFromLinearLayout(view);
                        } catch (Exception e) {
                            e.printStackTrace();
                            onError(e);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        onErrorListner(e.getMessage());
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        onCompleteListner(jsonArray);
                        Log.d(TAG, "json to send : " + jsonArray.toString());
                    }
                });

    }


    private synchronized void getChildFromLinearLayout(View view) throws JSONException {
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
                jsonArray.put(getValueFromRadioGroup((RadioGroup) view));
                Log.d(TAG, "getChildFromRadioGroup: " + ((RadioGroup) view).getChildCount());
                break;

            case "RatingBar":
                jsonArray.put(getValueFromRatingBar((RatingBar) view));
                break;

            case "Spinner":
                jsonArray.put(getValueFromSpinner((Spinner) view));
                break;

            case "CheckBox":
                getValueFromCheckBox((CheckBox) view);
                if (TextUtils.equals(viewTag, lastViewTag)) {
                    try {
                        jsonArray.put(getValueFromCheckBox((CheckBox) view));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                break;

            case "LinearLayout":
                LinearLayout linearLayout = (LinearLayout) view;
                if (linearLayout.getChildAt(0) instanceof CheckBox) {
                    jsonArray.put(getValueFromCheckedLinearLayout(linearLayout));
                    Log.d(TAG, "getChildFromLinearLayout: " + linearLayout.getChildCount());
                }
                break;

            case "EditText":
                jsonArray.put(getValueFromEditTex((EditText) view));
                break;

        }

    }

    private synchronized JSONObject getValueFromCheckedLinearLayout(@NonNull LinearLayout linearLayout) throws JSONException {
        List<Integer> checkedStringList1 = new ArrayList<Integer>();
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            CheckBox checkBox = (CheckBox) linearLayout.getChildAt(i);
            if (checkBox.isChecked()) {
                checkedStringList1.add(Integer.parseInt(checkBox.getTag().toString()));
            }
        }

        if(checkedStringList1.size() == 0){
            linearLayout.requestFocus();

            throw new JsonIOException("Atleast one checkbox should be selected");
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(KEY_QN_TYPE, Constant.ViewTypeStringKey.KEY_CHECKBOX);
        jsonObject.put(KEY_QN_ID, viewTag);
        jsonObject.put(KEY_ANS_ID, checkedStringList1);

        return jsonObject;
    }

    private synchronized JSONObject getValueFromRadioGroup(@NonNull RadioGroup radioGroup) throws JSONException {
        RadioButton rb1 = (RadioButton) activity.findViewById(radioGroup.getCheckedRadioButtonId());
        if (rb1 == null) {
            Toast.makeText(context, "no any option selected", Toast.LENGTH_SHORT).show();
            radioGroup.requestFocus();
            throw new JsonIOException("No any radio option selected");
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

    private synchronized JSONObject getValueFromTextView(@NonNull TextView textView) throws JSONException {
        Log.d(TAG, "getValueFromTextView: " + textView.getClass().getSimpleName() + " " + textView.getText().toString());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(KEY_QN_TYPE, Constant.ViewTypeStringKey.KEY_TEXTVIEW);
        jsonObject.put(KEY_QN_ID, viewTag);
        jsonObject.put(KEY_ANS_ID, textView.getText().toString());
        return jsonObject;
    }

    private synchronized JSONObject getValueFromEditTex(@NonNull EditText editText) throws JSONException {
        Log.d(TAG, "getValueFromTextView: " + editText.getClass().getSimpleName() + " " + editText.getText().toString());
        if (TextUtils.isEmpty(editText.getText().toString())) {
            editText.requestFocus();
            throw new JsonIOException("Field can not be empty");
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(KEY_QN_TYPE, Constant.ViewTypeStringKey.KEY_EDITTEXT);
        jsonObject.put(KEY_QN_ID, viewTag);
        jsonObject.put(KEY_ANS_ID, editText.getText().toString());
        return jsonObject;
    }

    private synchronized JSONObject getValueFromCheckBox(@NonNull CheckBox checkBox) throws JSONException {

        if (checkBox.isChecked()) {

            if (TextUtils.equals("", viewTag) || TextUtils.equals(lastViewTag, viewTag)) {
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

    private synchronized JSONObject getValueFromRatingBar(@NonNull RatingBar ratingBar) throws JSONException {
        String rating = String.valueOf(ratingBar.getRating());

        Log.d(TAG, "getValueFromratingBar: " + rating);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(KEY_QN_TYPE, Constant.ViewTypeStringKey.KEY_RATING_BAR);
        jsonObject.put(KEY_QN_ID, viewTag);
        jsonObject.put(KEY_ANS_ID, rating);
        return jsonObject;
    }

    private synchronized JSONObject getValueFromSpinner(@NonNull Spinner spinner) throws JSONException {
        int selectedSpinnerValuePos = spinner.getSelectedItemPosition();

        if(selectedSpinnerValuePos == 0){
            spinner.requestFocus();
            spinner.requestLayout();
            spinner.invalidate();
            throw new JsonIOException("please Select option");
        }

        int selectedSpinnerValueID = rawQnBuilderJsonArray.optJSONObject(viewPosition).optJSONArray("options").optJSONObject(selectedSpinnerValuePos-1).optInt("id");
        Log.d(TAG, "getValueFromSpinner: TAG " + selectedSpinnerValueID);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(KEY_QN_TYPE, Constant.ViewTypeStringKey.KEY_SPINNER);
        jsonObject.put(KEY_QN_ID, viewTag);
        jsonObject.put(KEY_ANS_ID, selectedSpinnerValueID);
        return jsonObject;
    }


    public abstract void onErrorListner(String errorMsg);

    public abstract void onCompleteListner(JSONArray answerJsonArray);


}
