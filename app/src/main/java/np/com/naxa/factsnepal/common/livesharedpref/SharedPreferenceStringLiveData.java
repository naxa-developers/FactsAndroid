package np.com.naxa.factsnepal.common.livesharedpref;

import android.content.SharedPreferences;

import np.com.naxa.factsnepal.common.livesharedpref.LiveSharePref;

public class SharedPreferenceStringLiveData extends LiveSharePref<String> {


    private final SharedPreferences sharedPreferences;

    public SharedPreferenceStringLiveData(SharedPreferences sharedPreferences, String key, String defaultValue) {
        super(sharedPreferences, key, defaultValue);
        this.sharedPreferences = sharedPreferences;
    }


    @Override
    String getValueFromPreferences(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }
}
