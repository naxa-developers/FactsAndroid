package np.com.naxa.factsnepal;

import android.app.Application;
import android.content.Context;

public class FactsNepal extends Application {

    private static Context instance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = getApplicationContext();
    }

    public static Context getInstance() {
        return instance;
    }
}
