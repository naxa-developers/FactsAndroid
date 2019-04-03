package np.com.naxa.factsnepal.notification;


import android.content.Context;
import android.util.Log;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collections;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import np.com.naxa.factsnepal.utils.SharedPreferenceUtils;

public class NotificationCount {

    private static final String TAG = "NotificationCount";
    private static final String KEY_NOTIFICATION_LIST = "notification_list";

    SharedPreferenceUtils sharedPreferenceUtils;

    int unreadCount = 0;

    public NotificationCount(Context context) {
        sharedPreferenceUtils = new SharedPreferenceUtils(context);
    }

    public JSONArray getJsonArray(){

        ArrayList<FactsNotification> factsNotificationArrayList = FactsNotification.getDemoItems();
        JSONArray factsNotificationJSONArray = new JSONArray(factsNotificationArrayList);

        Log.d(TAG, "getJsonArray: " +factsNotificationJSONArray.toString());

//        return Observable.just(factsNotificationJSONArray);
        return factsNotificationJSONArray;
    }


    public long getNotificationCount(){

        long count = getJsonArray().length();
        return count;
    }



    public void saveNotification(){

        if((sharedPreferenceUtils.getStringValue(KEY_NOTIFICATION_LIST, "")).equals("")){

        }

    }
}
