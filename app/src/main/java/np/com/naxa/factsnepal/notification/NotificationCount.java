package np.com.naxa.factsnepal.notification;


import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.Observable;
import np.com.naxa.factsnepal.utils.SharedPreferenceUtils;

public class NotificationCount {

    private static final String TAG = "NotificationCount";
    public static final String KEY_NOTIFICATION_LIST = "notification_list";

    SharedPreferenceUtils sharedPreferenceUtils;

    Gson gson = new Gson();

    int unreadCount = 0;

    public NotificationCount(Context context) {
        sharedPreferenceUtils = new SharedPreferenceUtils(context);
    }

    public JSONArray getJsonArray() throws JSONException {
        Gson gson = new Gson();
        ArrayList<FactsNotification> factsNotificationArrayList = FactsNotification.getDemoItems();
        String jsonString = gson.toJson(factsNotificationArrayList);

        JSONArray factsNotificationJSONArray = new JSONArray(jsonString);
        Log.d(TAG, "getJsonArray: default" + jsonString);
        return factsNotificationJSONArray;
    }

    long count;

    public synchronized long getNotificationCount() throws JSONException {
        count = 0;
        if (sharedPreferenceUtils.getStringValue(KEY_NOTIFICATION_LIST, "").equals("")) {
            return count;

        } else {
            JSONArray jsonArray = new JSONArray(sharedPreferenceUtils.getStringValue(KEY_NOTIFICATION_LIST, ""));
            for (int i = 0; i < jsonArray.length(); i++) {
                if (!jsonArray.getJSONObject(i).getBoolean("isRead")) {
                    count = count + 1;
                }
            }
            return count;
        }
    }


    public void saveNotification(JSONArray jsonArray) throws JSONException {

        if ((sharedPreferenceUtils.getStringValue(KEY_NOTIFICATION_LIST, "")).equals("")) {
            sharedPreferenceUtils.setValue(KEY_NOTIFICATION_LIST, getJsonArray().toString());
        } else {
            try {
                JSONArray jsonArrayOld = new JSONArray(sharedPreferenceUtils.getStringValue(KEY_NOTIFICATION_LIST, ""));
                Observable.range(0, jsonArray.length())
                        .map(jsonArray::get)
                        .subscribe(e -> {
                            jsonArrayOld.put(addFieldsToJsonObj((JSONObject) e));
                            sharedPreferenceUtils.setValue(KEY_NOTIFICATION_LIST, jsonArrayOld.toString());

                        });

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    private JSONObject addFieldsToJsonObj (@NonNull JSONObject jsonObject) throws JSONException {

        jsonObject.remove("time");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String formatted = format1.format(cal.getTime());
        jsonObject.put("time", formatted);


        jsonObject.remove("isRead");
        jsonObject.put("isRead", false);


        return jsonObject;

    }


    public static JSONObject changeNotificationStatus(JSONObject jsonObject) throws JSONException {
        jsonObject.remove("isRead");
        jsonObject.put("isRead", false);
        return jsonObject;
    }
}
