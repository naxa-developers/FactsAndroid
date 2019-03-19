package np.com.naxa.factsnepal.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
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

import java.util.Arrays;

import np.com.naxa.factsnepal.R;
import np.com.naxa.factsnepal.utils.Utils;

public abstract class BaseLoginActivity extends BaseActivity implements View.OnClickListener {

    CardView cv_login_google, cv_login_twitter, cv_login_facebook;
    CallbackManager callbackManager;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
//    TwitterAuthClient mTwitterAuthClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        init();
    }


    public void init() {
        callbackManager = CallbackManager.Factory.create();
        // [START config_signin]
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
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
                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "public_profile"));
                LoginManager.getInstance().registerCallback(callbackManager,
                        new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(LoginResult loginResult) {
                                // App code
                                onFacebookLogiSuccess(loginResult);
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
}