package np.com.naxa.factsnepal.network;

import android.content.Context;

import com.github.simonpercic.oklog3.BuildConfig;
import com.github.simonpercic.oklog3.OkLogInterceptor;

import java.util.HashMap;
import java.util.Map;

import np.com.naxa.factsnepal.utils.SharedPreferenceUtils;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static np.com.naxa.factsnepal.common.BaseLoginActivity.KEY_USER_BEARER_ACCESS_TOKEN;

public class NetworkApiClient {

    private static Retrofit retrofit = null;

    public static Retrofit getAPIClient() {

        if (retrofit == null) {
            OkLogInterceptor okLogInterceptor = OkLogInterceptor.builder().build();
            OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();

            Dispatcher dispatcher = new Dispatcher();
            dispatcher.setMaxRequests(3);

            okHttpBuilder.dispatcher(dispatcher);
            okHttpBuilder.addInterceptor(okLogInterceptor);

            OkHttpClient okHttpClient = okHttpBuilder.build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(UrlConstant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;

    }

    public static Map<String, String> getHeaders(String token){
//        SharedPreferenceUtils sharedPreferenceUtils = new SharedPreferenceUtils(context);

        Map<String, String> map = new HashMap<>();

        if (BuildConfig.DEBUG) {
            map.put("Content-Type", "application/json");
            map.put("charset", "utf-8");
//            map.put("Authorization", "Bearer "+sharedPreferenceUtils.getStringValue(KEY_USER_BEARER_ACCESS_TOKEN, null));
            map.put("Authorization", "Bearer "+token);
        }
        else {
            map.put("Content-Type", "application/json");
            map.put("charset", "utf-8");
            map.put("Authorization", "Bearer "+token);
        }

        return map;
    }

}
