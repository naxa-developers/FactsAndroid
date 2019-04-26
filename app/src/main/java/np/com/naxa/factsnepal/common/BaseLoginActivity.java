package np.com.naxa.factsnepal.common;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import np.com.naxa.factsnepal.R;
import np.com.naxa.factsnepal.utils.SharedPreferenceUtils;
import np.com.naxa.factsnepal.utils.Utils;

public abstract class BaseLoginActivity extends BaseActivity implements View.OnClickListener {

    CardView cv_login_google, cv_login_twitter, cv_login_facebook;
    CallbackManager callbackManager;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "BaseLoginActivity";
//    TwitterAuthClient mTwitterAuthClient;
    SharedPreferenceUtils sharedPreferenceUtils ;

    public static final int FACEBOOK_LOG_IN = 1;
    public static final int GMAIL_LOG_IN = 2;
    public static final String KEY_LOGGED_IN_TYPE = "logged_in_type";
    public static final String KEY_USER_BEARER_ACCESS_TOKEN = "bear_access_token";
    public static final String KEY_USER_SOCIAL_LOGGED_IN_DETAILS = "social_logged_in_details";
    public static final String KEY_USER_LOGGED_IN_DETAILS = "user_logged_in_details";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferenceUtils = new SharedPreferenceUtils(this);
        initView();
        init();
    }


    public void init() {
        callbackManager = CallbackManager.Factory.create();
        // [START config_signin]
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // [END config_signin]
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        cv_login_facebook = findViewById(R.id.cv_login_facebook);
        cv_login_google = findViewById(R.id.cv_login_google);
//        cv_login_twitter = findViewById(R.id.cv_login_twitter);
//        cv_login_twitter.setOnClickListener(this);
        cv_login_facebook.setOnClickListener(this);
        cv_login_google.setOnClickListener(this);
    }


    public void initView() {
        // Configure Twitter SDK
//        TwitterAuthConfig authConfig = new TwitterAuthConfig(
//                getString(R.string.twitter_consumer_key),
//                getString(R.string.twitter_consumer_secret));
//
//        TwitterConfig twitterConfig = new TwitterConfig.Builder(this)
//                .twitterAuthConfig(authConfig)
//                .build();
//
//        Twitter.initialize(twitterConfig);

        setContentView(R.layout.activity_login);
    }


    @Override
    public void onClick(View v) {
        if (!Utils.isNetworkConnected(getApplicationContext())) {
            toast("No internet connection available");
            return;
        }
        switch (v.getId()) {
            case R.id.cv_login_facebook:
                printHashKey(this);
                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "public_profile"));
                LoginManager.getInstance().registerCallback(callbackManager,
                        new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(LoginResult loginResult) {
                                // App code
                                onFacebookLogiSuccess(loginResult);
                                sharedPreferenceUtils.setValue(KEY_LOGGED_IN_TYPE, FACEBOOK_LOG_IN);
                            }

                            @Override
                            public void onCancel() {
                                // App code
                                Utils.log(BaseLoginActivity.class, "facebook login is cancelled");
                            }

                            @Override
                            public void onError(FacebookException exception) {
                                // App code
                                Utils.log(BaseLoginActivity.class, exception.getMessage());
                            }
                        });
                break;
            case R.id.cv_login_google:
                signIn();
                break;
//            case R.id.cv_login_twitter:
//                mTwitterAuthClient = new TwitterAuthClient();
//                mTwitterAuthClient.authorize(this, new Callback<TwitterSession>() {
//                    @Override
//                    public void success(Result<TwitterSession> twitterSessionResult) {
//                        // Success
//                        onTwitterLoginSuccess(twitterSessionResult);
//                    }
//
//                    @Override
//                    public void failure(TwitterException e) {
//                        e.printStackTrace();
//                    }
//                });
//                break;
        }
    }

    public void printHashKey(Context pContext) {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i(TAG, "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "printHashKey()", e);
        } catch (Exception e) {
            Log.e(TAG, "printHashKey()", e);
        }
    }

    // [START signin]
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [END signin]

    public abstract void onFacebookLogiSuccess(LoginResult result);

//    public abstract void onTwitterLoginSuccess(Result<TwitterSession> twitterSessionResult);

    public abstract void onGoogleLoginSuccess(GoogleSignInAccount account);


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                onGoogleLoginSuccess(account);
                sharedPreferenceUtils.setValue(KEY_LOGGED_IN_TYPE, GMAIL_LOG_IN);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("BaseLoginActivity", "Google sign in failed", e);
                // [START_EXCLUDE]
                // [END_EXCLUDE]
            }
        }
        callbackManager.onActivityResult(requestCode, resultCode, data);
//        if (mTwitterAuthClient != null)
//            mTwitterAuthClient.onActivityResult(requestCode, resultCode, data);


    }

    public static class UserSocialLoginDetails {
        String user_access_token;
        int user_login_type;
        String user_name;
        String user_email;
        String user_image_url;

        public UserSocialLoginDetails(String user_access_token, int user_login_type, String user_name, String user_email, String user_image_url) {
            this.user_access_token = user_access_token;
            this.user_login_type = user_login_type;
            this.user_name = user_name;
            this.user_email = user_email;
            this.user_image_url = user_image_url;
        }

        public UserSocialLoginDetails() {
        }


        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getUser_email() {
            return user_email;
        }

        public void setUser_email(String user_email) {
            this.user_email = user_email;
        }

        public String getUser_image_url() {
            return user_image_url;
        }

        public void setUser_image_url(String user_image_url) {
            this.user_image_url = user_image_url;
        }

        public String getUser_access_token() {
            return user_access_token;
        }

        public void setUser_access_token(String user_access_token) {
            this.user_access_token = user_access_token;
        }

        public int getUser_login_type() {
            return user_login_type;
        }

        public void setUser_login_type(int user_login_type) {
            this.user_login_type = user_login_type;
        }
    }

}
