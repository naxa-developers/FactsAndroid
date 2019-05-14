package np.com.naxa.factsnepal.common;

import java.util.ArrayList;
import java.util.List;

public class Constant {
    public static final String KEY_OBJECT = "object";
    public static final String KEY_EXTRA_OBJECT = "extra_object";
    public static final String EXTRA_IMAGE = "extra_image";
    public static String RANDOM_PHOTO = "https://picsum.photos/1000/600/?random";
    public static String FACT_PHOTO = "https://scontent.fktm8-1.fna.fbcdn.net/v/t1.0-9/52638797_2396505700373002_4565827759254798336_n.png?_nc_cat=105&_nc_ht=scontent.fktm8-1.fna&oh=7bbb8ead8c153edfe8e2f1af17f8eef2&oe=5D134EF8";

    public static List<Integer> generatedViewIdsList = new ArrayList<Integer>();

    public class SharedPrefKey {
        public static final String KEY_LOGGED_IN_TYPE = "logged_in_type";
        public static final String KEY_USER_BEARER_ACCESS_TOKEN = "bear_access_token";
        public static final String KEY_USER_SOCIAL_LOGGED_IN_DETAILS = "social_logged_in_details";
        public static final String KEY_USER_LOGGED_IN_DETAILS = "user_logged_in_details";

        public static final String SELECTED_CATEGORIES = "selected_categories";
        public static final String KEY_IS_USER_LOGGED_IN = "is_user_logged_in";
        public static final String IS_APP_FIRST_TIME_LAUNCH = "is_app_first_time_launch";
        public static final String KEY_NOTIFICATION_LIST = "notification_list";
        public static final String KEY_RECENT_SURVEY_FORM_DETAILS = "recent_survey_form_details";

        public static final String KEY_SURVEY_COMPANY_DETAILS_JSON = "survey_company_details_json";
    }

    public class JsonKeySurvey {
        public static final String KEY_COMPANY_ID = "company_id";
        public static final String KEY_SURVEY_ID = "survey_id";
        public static final String KEY_USER_ID = "user_id";
        public static final String KEY_QN_ID = "question_id";
        public static final String KEY_ANS_ID = "answer_id";
        public static final String KEY_QN_TYPE = "question_type";
        public static final String KEY_ANSWER = "answer_data";
        public static final String KEY_JSON_DATA = "survey";
    }

    public class ViewTypeStringKey {
        public static final String KEY_TEXTVIEW = "textview";
        public static final String KEY_EDITTEXT = "edittext";
        public static final String KEY_CHECKBOX = "checkbox";
        public static final String KEY_RADIO = "radio";
        public static final String KEY_RATING_BAR = "rating";
        public static final String KEY_SPINNER = "dropdown";

    }

    public class Permission {
        public static final int RC_STORAGE = 1994;
    }
}
