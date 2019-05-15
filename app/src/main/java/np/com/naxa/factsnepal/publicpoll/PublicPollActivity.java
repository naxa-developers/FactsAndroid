package np.com.naxa.factsnepal.publicpoll;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.button.MaterialButton;

import java.io.EOFException;
import java.net.SocketTimeoutException;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import np.com.naxa.factsnepal.R;
import np.com.naxa.factsnepal.common.BaseActivity;
import np.com.naxa.factsnepal.utils.ActivityUtil;

public class PublicPollActivity extends BaseActivity {
    private static final String TAG = "PublicPollActivity";

    private TextView tvQuestionTitle ;
    private int retryCounter = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_poll);
        tvQuestionTitle = findViewById(R.id.tv_question_title);
        setupToolbar();

//        setupButtonForDemo("checkbox");
        createProgressDialog("Loading Poll Data");
        fetchPublicPollFromServer();
    }

    private void setupButtonForDemo(@NonNull PublicPollQuestionDetail publicPollQuestionDetail) {
        tvQuestionTitle.setText(publicPollQuestionDetail.getQuestion());

        LinearLayout view = findViewById(R.id.layout_button_wrapper);
        View button = null;

        View.OnClickListener onClick = v -> {
            Log.d(TAG, "setupButtonForDemo: view tag " +v.getTag().toString());
            ActivityUtil.openActivity(PublicPollResultActivity.class, this, null, false);
        };

        List<Option> optionList = publicPollQuestionDetail.getOptions();
        for (Option option : optionList) {
            button = LayoutInflater.from(this).inflate(R.layout.layout_button_survey, view, false);
            ((MaterialButton) button).setText(option.getQuestion());
            ((MaterialButton) button).setTag(option.getId().toString());
            button.setOnClickListener(onClick);
            view.addView(button);

        }

        hideProgressDialog();
    }



    private void fetchPublicPollFromServer(){
        apiInterface.getPublicPollQuestionDetail()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<List<PublicPollQuestionDetail>>() {
                    @Override
                    public void onNext(List<PublicPollQuestionDetail> publicPollQuestionDetailList) {

                        if (publicPollQuestionDetailList == null){
                            hideProgressDialog();
                            showToast("Null response from the server");
                        }else {
                            setupButtonForDemo(publicPollQuestionDetailList.get(0));
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        hideProgressDialog();
                        showToast(e.getMessage());
                        if((retryCounter < 5)&&(e instanceof EOFException || e instanceof SocketTimeoutException)){
                            createProgressDialog("Retry loading poll data");
                            fetchPublicPollFromServer();
                            retryCounter++;
                        }else {
                            showToast("Unable to fetch data from server");
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
