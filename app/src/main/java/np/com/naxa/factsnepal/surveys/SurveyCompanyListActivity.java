package np.com.naxa.factsnepal.surveys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import np.com.naxa.factsnepal.R;
import np.com.naxa.factsnepal.common.BaseActivity;

public class SurveyCompanyListActivity extends BaseActivity {
    private static final String TAG = "SurveyCompanyListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_company_list);

        setupToolbar("Survey Company List");

        getSurveyWuestionDetailsResponse();
    }


    private void getSurveyWuestionDetailsResponse(){
        final String[] jsonInString = {""};
        Gson gson = new Gson();

        apiInterface.getSurveyQuestionDetailsResponse()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<SurveyQuestionDetails>() {
                    @Override
                    public void onNext(SurveyQuestionDetails surveyQuestionDetails) {

                        jsonInString[0] = gson.toJson(surveyQuestionDetails);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                        Log.d(TAG, "onComplete: "+jsonInString[0]);
                    }
                });
    }
}
