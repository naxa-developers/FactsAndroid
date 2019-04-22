package np.com.naxa.factsnepal.feed.list.resource;

import android.content.Context;

import np.com.naxa.factsnepal.FactsNepal;
import np.com.naxa.factsnepal.common.Constant;
import np.com.naxa.factsnepal.common.livesharedpref.SharedPreferenceStringLiveData;
import np.com.naxa.factsnepal.utils.SharedPreferenceUtils;

public class FactCategoryLocalSource {
    private static FactCategoryLocalSource INSTANCE;
    private final Context context;

    public static FactCategoryLocalSource getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new FactCategoryLocalSource();
        }
        return INSTANCE;
    }

    private FactCategoryLocalSource() {
        context = FactsNepal.getInstance();
    }

    public void saveCategories(String categories) {
        Context context = FactsNepal.getInstance();
        SharedPreferenceUtils.getInstance(context).setValue(Constant.SharedPrefKey.CATEGORIES, categories);
    }


    public SharedPreferenceStringLiveData getCategories() {
        return new SharedPreferenceStringLiveData(SharedPreferenceUtils.getInstance(context).getSharePref(), Constant.SharedPrefKey.CATEGORIES, "");
    }
}
