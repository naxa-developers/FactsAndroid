package np.com.naxa.factsnepal.userprofile;

import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import np.com.naxa.factsnepal.R;
import np.com.naxa.factsnepal.common.BaseActivity;
import np.com.naxa.factsnepal.common.BaseLoginActivity;
import np.com.naxa.factsnepal.common.BaseRecyclerViewAdapter;
import np.com.naxa.factsnepal.network.NetworkApiClient;
import np.com.naxa.factsnepal.userprofile.earninghistory.SurveyDetail;
import np.com.naxa.factsnepal.userprofile.earninghistory.UsersEarningResponse;
import np.com.naxa.factsnepal.utils.ImageUtils;
import np.com.naxa.factsnepal.utils.SharedPreferenceUtils;

import static np.com.naxa.factsnepal.common.BaseLoginActivity.KEY_USER_BEARER_ACCESS_TOKEN;

public class UserProfileInfoActivity extends BaseActivity {

    private static final String TAG = "UserProfileInfoActivity";
    private static final String KEY_USERS_EARNING_DETAILS = "users_earning_details";

    private RecyclerView recyclerView;
    private BaseRecyclerViewAdapter<SurveyDetail, SurveyEarningHistoryVH> adapter;

    private TextView tvUserName, tvEmail, tvTotalEarning;
    private ImageView ivUserProfileImage;

    SharedPreferenceUtils sharedPreferenceUtils;
    Gson gson;
    List<SurveyDetail> surveyDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_info);

        sharedPreferenceUtils = new SharedPreferenceUtils(this);
        gson = new Gson();
        surveyDetails = new ArrayList<SurveyDetail>();

        setupToolbar("Your Profile");
        iniUI();
        fetchEarningHistory();

    }

    private void iniUI() {
        tvUserName = findViewById(R.id.tv_users_username);
        tvEmail = findViewById(R.id.tv_users_email);
        tvTotalEarning = findViewById(R.id.tv_users_total_earning);
        ivUserProfileImage = findViewById(R.id.iv_users_profile_image);

        BaseLoginActivity.UserSocialLoginDetails userSocialLoginDetails = gson.fromJson((sharedPreferenceUtils.getStringValue(LoginActivity.KEY_USER_SOCIAL_LOGGED_IN_DETAILS, null)), BaseLoginActivity.UserSocialLoginDetails.class);

        tvUserName.setText(userSocialLoginDetails.getUser_name());
        tvEmail.setText(userSocialLoginDetails.getUser_email());

        ImageUtils.loadRemoteImage(this, userSocialLoginDetails.getUser_image_url()).circleCrop().into(ivUserProfileImage);
    }





    private void fetchEarningHistory(){
        if(isNetworkAvailable()){
            fetchEarningHistoryFromServer();
        }else {
            fetchEarningHistoryFromSharedPrefs();
        }
    }

    private void fetchEarningHistoryFromSharedPrefs(){
        Observable.just(gson.fromJson((sharedPreferenceUtils.getStringValue(KEY_USERS_EARNING_DETAILS, null)), UsersEarningResponse.class))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<UsersEarningResponse>() {
                    @Override
                    public void onNext(UsersEarningResponse usersEarningResponse) {
                        if (usersEarningResponse != null) {
                            tvTotalEarning.setText(usersEarningResponse.getEarning().get(0).getTotal());
                            surveyDetails = usersEarningResponse.getSurveyDetails();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        if (surveyDetails == null) {
                            setupRecyclerView(SurveyDetail.getDemoItems());
                        } else {
                            setupRecyclerView(surveyDetails);
                        }
                    }
                });

    }

    private void fetchEarningHistoryFromServer() {
        apiInterface.getUserEarningDetailsResponse(NetworkApiClient.getHeaders(sharedPreferenceUtils.getStringValue(KEY_USER_BEARER_ACCESS_TOKEN, null)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<UsersEarningResponse>() {
                    @Override
                    public void onNext(UsersEarningResponse usersEarningResponse) {
                        if (usersEarningResponse != null) {
                            Log.d(TAG, "onNext: "+usersEarningResponse.getEarning().size());
                            tvTotalEarning.setText(usersEarningResponse.getEarning().get(0).getTotal());
                            sharedPreferenceUtils.setValue(KEY_USERS_EARNING_DETAILS, gson.toJson(usersEarningResponse));
                            surveyDetails = usersEarningResponse.getSurveyDetails();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: "+e.getMessage());

//                        //TODO : remove from here after working API
                        setupRecyclerView(SurveyDetail.getDemoItems());
                    }

                    @Override
                    public void onComplete() {
                        if (surveyDetails == null) {
                            setupRecyclerView(SurveyDetail.getDemoItems());
                        } else {
                            setupRecyclerView(surveyDetails);
                        }
                    }
                });
    }

    private void setupRecyclerView(List<SurveyDetail> surveyDetailList) {
        recyclerView = findViewById(R.id.rv_survey_earning_history);


        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new BaseRecyclerViewAdapter<SurveyDetail, SurveyEarningHistoryVH>(surveyDetailList, R.layout.user_earning_table_item_layout) {


            @Override
            public void viewBinded(SurveyEarningHistoryVH surveyEarningHistoryVH, SurveyDetail surveyDetailList, int position) {
                surveyEarningHistoryVH.bindView(surveyDetailList);
                surveyEarningHistoryVH.itemView.setOnClickListener((v -> {

                }));
            }


            @Override
            public SurveyEarningHistoryVH attachViewHolder(View view) {
                return new SurveyEarningHistoryVH(view);
            }
        };

        recyclerView.setAdapter(adapter);

    }
}
