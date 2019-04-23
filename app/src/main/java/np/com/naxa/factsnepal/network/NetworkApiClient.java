package np.com.naxa.factsnepal.network;

import android.content.Context;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.github.simonpercic.oklog3.BuildConfig;
import com.github.simonpercic.oklog3.OkLogInterceptor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import np.com.naxa.factsnepal.FactsNepal;
import np.com.naxa.factsnepal.utils.SharedPreferenceUtils;
import okhttp3.Cache;
import okhttp3.Dispatcher;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static np.com.naxa.factsnepal.common.BaseLoginActivity.KEY_USER_BEARER_ACCESS_TOKEN;

public class NetworkApiClient {

    private static Retrofit retrofit = null;
    private static Retrofit cacheablesRetrofit = null;

    public static Retrofit getAPIClient() {

        if (retrofit == null) {
            OkLogInterceptor okLogInterceptor = OkLogInterceptor.builder().build();
            OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder()
                    .addNetworkInterceptor(new StethoInterceptor());

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

    public static <T> T createCacheService(Class<T> serviceClass) {
        if (cacheablesRetrofit == null) {
            cacheablesRetrofit = new Retrofit.Builder()
                    .client(createCacheablesOkHttpClient())
                    .baseUrl(UrlConstant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }

        return cacheablesRetrofit.create(serviceClass);
    }

    private static OkHttpClient createCacheablesOkHttpClient() {

        int cacheSize = 10 * 1024 * 1024;//ten mb of cache
        Cache cache = new Cache(FactsNepal.getInstance().getCacheDir(), cacheSize);

        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder.cache(cache);
        okHttpBuilder.addInterceptor(chain -> {
            try {
                Request request = chain.request().newBuilder().build();
                return chain.proceed(request);
            } catch (Exception e) {

                Request offlineRequest = chain.request().newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7)
                        .build();
                return chain.proceed(offlineRequest);
            }

        });


        return okHttpBuilder.build();
    }

    public static Map<String, String> getHeaders(String token) {
//        SharedPreferenceUtils sharedPreferenceUtils = new SharedPreferenceUtils(context);

        Map<String, String> map = new HashMap<>();

        if (BuildConfig.DEBUG) {
            map.put("Content-Type", "application/json");
            map.put("charset", "utf-8");
//            map.put("Authorization", "Bearer "+sharedPreferenceUtils.getStringValue(KEY_USER_BEARER_ACCESS_TOKEN, null));
            map.put("Authorization", "Bearer " + token);
        } else {
            map.put("Content-Type", "application/json");
            map.put("charset", "utf-8");
            map.put("Authorization", "Bearer " + token);
        }

        return map;
    }

}
