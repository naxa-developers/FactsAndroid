package np.com.naxa.factsnepal.notification;


import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.reactivex.Observable;
import np.com.naxa.factsnepal.utils.SharedPreferenceUtils;

public class NotificationCount {

    private static final String TAG = "NotificationCount";
    public static final String KEY_NOTIFICATION_LIST = "notification_list";

    SharedPreferenceUtils sharedPreferenceUtils;

    int unreadCount = 0;

    public NotificationCount(Context context) {
        sharedPreferenceUtils = new SharedPreferenceUtils(context);
    }

    public JSONArray getJsonArray() throws JSONException {
        Gson gson = new Gson();
        ArrayList<FactsNotification> factsNotificationArrayList = FactsNotification.getDemoItems();
        String jsonString = gson.toJson(factsNotificationArrayList);
        JSONArray factsNotificationJSONArray = new JSONArray(jsonString);
        Log.d(TAG, "getJsonArray: default" +jsonString);
//        saveNotification(factsNotificationJSONArray);
        return factsNotificationJSONArray;
    }


    public synchronized long getNotificationCount() throws JSONException {
        long count = 0;

        if(updateData) {
            JSONArray jsonArray = new JSONArray(sharedPreferenceUtils.getStringValue(KEY_NOTIFICATION_LIST, ""));
//            for ( int i = 0 ; i< jsonArray.length(); i++ ) {
//                if(!jsonArray.getJSONObject(i).getBoolean("isRead")){
//                    count = count++;
//                    Log.d(TAG, "getNotificationCount: "+jsonArray.length());
//                    Log.d(TAG, "getJsonArray: " +jsonArray.toString());

//                }
//            }
            count = jsonArray.length();
            return count;
        }else {
            count = getJsonArray().length();
            return count;
        }
    }


public static boolean updateData = false;
    public void saveNotification(JSONArray jsonArray) throws JSONException {

        if((sharedPreferenceUtils.getStringValue(KEY_NOTIFICATION_LIST, "")).equals("")){
            sharedPreferenceUtils.setValue(KEY_NOTIFICATION_LIST, getJsonArray().toString());
        }else {
            try {
                JSONArray jsonArrayOld = new JSONArray(sharedPreferenceUtils.getStringValue(KEY_NOTIFICATION_LIST, ""));
                Observable.range(0,jsonArray.length())
                        .map(jsonArray::get)
                        .subscribe(e -> {
                            JSONObject jsonObject = (JSONObject)e;
                            jsonArrayOld.put((JSONObject) e);
                            sharedPreferenceUtils.setValue(KEY_NOTIFICATION_LIST, jsonArrayOld.toString());
                            updateData = true;

                        });

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
