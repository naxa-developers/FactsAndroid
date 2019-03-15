package np.com.naxa.factsnepal.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import np.com.naxa.factsnepal.BuildConfig;

public class Utils {
    public static boolean isNetworkConnected(Context ctx) {
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    public static void log(Class<?> mClass, String message) {
        if (BuildConfig.DEBUG) {
            Log.d(mClass.getSimpleName(), message);
        }
    }
}
