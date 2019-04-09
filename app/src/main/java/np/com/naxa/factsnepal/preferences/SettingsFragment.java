package np.com.naxa.factsnepal.preferences;

import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;

import com.google.firebase.messaging.FirebaseMessaging;

import np.com.naxa.factsnepal.BuildConfig;
import np.com.naxa.factsnepal.R;
import np.com.naxa.factsnepal.utils.ActivityUtil;


public class SettingsFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener, Preference.OnPreferenceChangeListener {

    String[] firebaseTopics = new String[]{"topicA","topicB"};

    private void firebasesubscribe(String[] firebaseTopics, boolean shouldTurnOn) {
        for (String topic : firebaseTopics) {
            if (shouldTurnOn) {
                FirebaseMessaging.getInstance().subscribeToTopic(topic);
            } else {
                FirebaseMessaging.getInstance().unsubscribeFromTopic(topic);
            }
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();


    }

    private void init() {
        addPreferencesFromResource(R.xml.app_settings);
        String formattedAppName = getString(R.string.app_name) + " " + BuildConfig.VERSION_NAME;
        findPreference(SettingsKeys.KEY_APP_UPDATE).setTitle(formattedAppName);

        findPreference(SettingsKeys.KEY_APP_UPDATE).setOnPreferenceClickListener(this);
        findPreference(SettingsKeys.KEY_ABOUT_US).setOnPreferenceClickListener(this);
        findPreference(SettingsKeys.KEY_PRIVACY_POLICY).setOnPreferenceClickListener(this);
        findPreference(SettingsKeys.KEY_CLEAR_AND_SYNC).setOnPreferenceClickListener(this);
        findPreference(SettingsKeys.KEY_SYNC).setOnPreferenceClickListener(this);

        findPreference(SettingsKeys.KEY_NOTIFICATION).setOnPreferenceChangeListener(this);

    }


    @Override
    public boolean onPreferenceClick(Preference preference) {
        switch (preference.getKey()) {
            case SettingsKeys.KEY_APP_UPDATE:
                ActivityUtil.openUri(getActivity(), Uri.parse("market://details?id" + BuildConfig.APPLICATION_ID));
                break;
            case SettingsKeys.KEY_ABOUT_US:
                ActivityUtil.openUri(getActivity(), Uri.parse("http://naxa.com.np"));
                break;
            case SettingsKeys.KEY_PRIVACY_POLICY:
                ActivityUtil.openUri(getActivity(), Uri.parse("http://naxa.com.np/privacy/"));
                break;
            case SettingsKeys.KEY_CLEAR_AND_SYNC:
                break;
            case SettingsKeys.KEY_SYNC:
                break;
        }
        return false;
    }


    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        switch (preference.getKey()) {
            case SettingsKeys.KEY_NOTIFICATION:
                boolean isOn = (Boolean) newValue;
                SwitchPreference switchPreference = (SwitchPreference) findPreference(SettingsKeys.KEY_NOTIFICATION);
                switchPreference.setChecked(isOn);
                firebasesubscribe(firebaseTopics, isOn);

                break;
        }
        return false;
    }


}