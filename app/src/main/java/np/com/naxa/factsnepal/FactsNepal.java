package np.com.naxa.factsnepal;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

public class FactsNepal extends Application {

    private static Context instance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = getApplicationContext();
        Stetho.initializeWithDefaults(this);
    }

    public static Context getInstance() {
        return instance;
    }
}
