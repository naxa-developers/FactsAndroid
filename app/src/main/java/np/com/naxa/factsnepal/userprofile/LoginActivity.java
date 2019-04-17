package np.com.naxa.factsnepal.userprofile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.gson.Gson;

import org.json.JSONException;

import java.util.HashMap;

import np.com.naxa.factsnepal.common.BaseLoginActivity;
import np.com.naxa.factsnepal.feed.list.FeedListActivity;
import np.com.naxa.factsnepal.utils.ActivityUtil;
import np.com.naxa.factsnepal.utils.SharedPreferenceUtils;
import np.com.naxa.factsnepal.utils.Utils;

public class LoginActivity extends BaseLoginActivity {
    private static final String TAG = "LoginActivity";
    public static final String KEY_IS_USER_LOGGED_IN = "is_user_logged_in";

    SharedPreferenceUtils sharedPreferenceUtils ;
    Gson gson = new Gson();


    @Override
    public void onFacebookLogiSuccess(LoginResult result) {
        Profile profile = Profile.getCurrentProfile();

        sharedPreferenceUtils = new SharedPreferenceUtils(this);

        Log.d(TAG, "onFacebookLogiSuccess: Token"+ result.getAccessToken().getToken());
        Log.d(TAG, "onFacebookLogiSuccess: Token"+ result.getAccessToken().getUserId());

        //        Log.d(TAG, "onCreate: userToken "+token.getToken());
        HashMap<String, AccessToken> map = new HashMap<>();
        map.put("token", result.getAccessToken());
        ActivityUtil.openActivity(UpdateProfileActivity.class, this, map, false);

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender, birthday");
        GraphRequest request = GraphRequest.newMeRequest(result.getAccessToken(), (object, response) -> {
            try {
                String name = object.getString("name");
                String email = object.getString("email");

                sharedPreferenceUtils.setValue(BaseLoginActivity.KEY_USER_SOCIAL_LOGGED_IN_DETAILS, gson.toJson(new UserLoginDetails(result.getAccessToken().getToken(), 1,
                        name, email, String.format("https://graph.facebook.com/%s/picture?type=large",result.getAccessToken().getUserId()))));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        request.setParameters(parameters);
        request.executeAsync();



    }

//    @Override
//    public void onTwitterLoginSuccess(Result<TwitterSession> twitterSessionResult) {
//        Utils.log(this.getClass(), twitterSessionResult.data.getUserName());
//    }

    @Override
    public void onGoogleLoginSuccess(GoogleSignInAccount account) {
        sharedPreferenceUtils = new SharedPreferenceUtils(this);
        Utils.log(this.getClass(), account.getDisplayName());
        sharedPreferenceUtils.setValue(BaseLoginActivity.KEY_USER_SOCIAL_LOGGED_IN_DETAILS, gson.toJson(new UserLoginDetails(account.getIdToken(), 2,
                account.getDisplayName(), account.getEmail(), account.getPhotoUrl().toString())));

        Log.d(TAG, "onGoogleLoginSuccess: "+account.getEmail());
        ActivityUtil.openActivity(UpdateProfileActivity.class, this, null, false);


    }


}
