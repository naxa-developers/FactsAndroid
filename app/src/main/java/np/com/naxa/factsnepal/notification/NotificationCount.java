package np.com.naxa.factsnepal.notification;

import com.google.android.gms.common.util.SharedPreferencesUtils;

import org.json.JSONArray;

import java.util.ArrayList;

public class NotificationCount {

    public JSONArray getJsonArray(){

        ArrayList<FactsNotification> factsNotificationArrayList = FactsNotification.getDemoItems();
        JSONArray factsNotificationJSONArray = new JSONArray(factsNotificationArrayList);
        return factsNotificationJSONArray;
    }

    public void saveNotification(){

    }
}
