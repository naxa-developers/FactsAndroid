package np.com.naxa.factsnepal.userprofile;

import android.os.Bundle;
import androidx.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.facebook.GraphRequest;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.gson.Gson;

import org.json.JSONException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import np.com.naxa.factsnepal.common.BaseLoginActivity;
import np.com.naxa.factsnepal.feed.list.FeedListActivity;
import np.com.naxa.factsnepal.network.NetworkApiClient;

import np.com.naxa.factsnepal.utils.ActivityUtil;
import np.com.naxa.factsnepal.utils.SharedPreferenceUtils;
import np.com.naxa.factsnepal.utils.Utils;

import static np.com.naxa.factsnepal.common.Constant.SharedPrefKey.KEY_IS_USER_LOGGED_IN;

public class LoginActivity extends BaseLoginActivity {
    private static final String TAG = "LoginActivity";

    SharedPreferenceUtils sharedPreferenceUtils ;
    Gson gson = new Gson();


    @Override
    public void onFacebookLogiSuccess(LoginResult result) {
        Profile profile = Profile.getCurrentProfile();

        sharedPreferenceUtils = new SharedPreferenceUtils(this);

        Log.d(TAG, "onFacebookLogiSuccess: Token"+ result.getAccessToken().getToken());
        Log.d(TAG, "onFacebookLogiSuccess: Token"+ result.getAccessToken().getUserId());

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender, birthday");
        GraphRequest request = GraphRequest.newMeRequest(result.getAccessToken(), (object, response) -> {
            try {
                String name = object.getString("name");
                String email = object.getString("email");

                sharedPreferenceUtils.setValue(BaseLoginActivity.KEY_USER_SOCIAL_LOGGED_IN_DETAILS, gson.toJson(new UserSocialLoginDetails(result.getAccessToken().getToken(), 1,
                        name, email, String.format("https://graph.facebook.com/%s/picture?type=large",result.getAccessToken().getUserId()))));

                getLoginResponse(new LoginCredentials(email, "123456"));
//                getLoginResponse(new LoginCredentials("samir1001@gmail.com", "123456"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        request.setParameters(parameters);
        request.executeAsync();

        //        Log.d(TAG, "onCreate: userToken "+token.getToken());
//        HashMap<String, AccessToken> map = new HashMap<>();
//        map.put("token", result.getAccessToken());

//        if(sharedPreferenceUtils.getBoolanValue(LoginActivity.KEY_IS_USER_LOGGED_IN, false)){
//            ActivityUtil.openActivity(FeedListActivity.class, this);
//        }else {
//            ActivityUtil.openActivity(UpdateProfileActivity.class, this, map, false);
//        }


    }

    @Override
    public void onGoogleLoginSuccess(GoogleSignInAccount account) {
        sharedPreferenceUtils = new SharedPreferenceUtils(this);
        Utils.log(this.getClass(), account.getDisplayName());
        sharedPreferenceUtils.setValue(BaseLoginActivity.KEY_USER_SOCIAL_LOGGED_IN_DETAILS, gson.toJson(new UserSocialLoginDetails(account.getIdToken(), 2,
                account.getDisplayName(), account.getEmail(), account.getPhotoUrl().toString())));

        Log.d(TAG, "onGoogleLoginSuccess: "+account.getEmail());
        getLoginResponse(new LoginCredentials(account.getEmail(), "123456"));
//        getLoginResponse(new LoginCredentials("samir1001@gmail.com", "123456"));

    }


    UserLoginResponse userLoginResponse ;
    private void getLoginResponse (LoginCredentials loginCredentials){
//        apiInterface.getUserLoginResponse(loginCredentials.getEmail(), loginCredentials.getPassword())
        apiInterface.getUserLoginResponse(loginCredentials)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<UserLoginResponse>() {
                    @Override
                    public void onNext(UserLoginResponse userLoginResponse1) {
                        userLoginResponse = userLoginResponse1;
                        checkSuccessStatusAndGetUserDetails(userLoginResponse1);

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                            checkSuccessStatusAndGetUserDetails(userLoginResponse);
                    }
                });
    }

    private void checkSuccessStatusAndGetUserDetails(@NonNull UserLoginResponse userLoginResponse){
        if(userLoginResponse.isSuccess()){
            getUserDetails(userLoginResponse.getToken());
        }else {
            Toast.makeText(this, "User does not exist, Please register first.", Toast.LENGTH_SHORT).show();
            ActivityUtil.openActivity(UpdateProfileActivity.class, LoginActivity.this, null, false);
        }

    }

    public void getUserDetails (@NonNull String bearerAccessToken){
        apiInterface.getUserDetailsResponse(NetworkApiClient.getHeaders(bearerAccessToken))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<UserLoginResponse>() {
                    @Override
                    public void onNext(UserLoginResponse userLoginResponse1) {
                        if(userLoginResponse1.isSuccess()){
                            String loginResponseInString = gson.toJson(userLoginResponse1);
                            Log.d(TAG, "onNext: "+loginResponseInString);
                            sharedPreferenceUtils.setValue(LoginActivity.KEY_USER_LOGGED_IN_DETAILS, loginResponseInString);
                            sharedPreferenceUtils.setValue(KEY_IS_USER_LOGGED_IN , true);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: "+e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        ActivityUtil.openActivity(FeedListActivity.class, LoginActivity.this, null, false);

                    }
                });
    }
}
