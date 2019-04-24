package np.com.naxa.factsnepal.surveys;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import np.com.naxa.factsnepal.FactsNepal;
import np.com.naxa.factsnepal.R;
import np.com.naxa.factsnepal.common.BaseActivity;
import np.com.naxa.factsnepal.feed.Fact;
import np.com.naxa.factsnepal.surveys.surveyforms.SurveyQuestionDetailsResponse;
import np.com.naxa.factsnepal.utils.JsonView;

public class SurveyActivity extends BaseActivity {
    private static final String TAG = "SurveyActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        setupToolbar("Survey Activity");
        RecyclerView recyclerView = findViewById(R.id.rv_survey);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new SurveyAdapter(buildUi()));

    }


    private JSONArray buildUi (){
        Gson gson = new Gson();
        SurveyQuestionDetailsResponse surveyQuestionDetailsResponse = gson.fromJson(SurveyQuestionDetailsResponse.getDemoJson(), SurveyQuestionDetailsResponse.class);
        try {
            Log.d(TAG, "buildUi: " + SurveyQuestionDetailsResponse.getDemoJson());
            String json = " [\n" +
                    "       {\n" +
                    "           \"id\": 1,\n" +
                    "           \"question\": \"Question no. one?\",\n" +
                    "           \"survey_id\": 6,\n" +
                    "           \"question_type\": \"radio\",\n" +
                    "           \"options\": [\n" +
                    "               {\n" +
                    "                   \"id\": 1,\n" +
                    "                   \"question\": \"New Party\",\n" +
                    "                   \"question_id\": 1\n" +
                    "               },\n" +
                    "               {\n" +
                    "                   \"id\": 2,\n" +
                    "                   \"question\": \"Old Party\",\n" +
                    "                   \"question_id\": 1\n" +
                    "               }\n" +
                    "           ]\n" +
                    "       },\n" +
                    "       {\n" +
                    "           \"id\": 2,\n" +
                    "           \"question\": \"uy\",\n" +
                    "           \"survey_id\": 6,\n" +
                    "           \"question_type\": \"radio\",\n" +
                    "           \"options\": [\n" +
                    "               {\n" +
                    "                   \"id\": 3,\n" +
                    "                   \"question\": \"Yes\",\n" +
                    "                   \"question_id\": 2\n" +
                    "               },\n" +
                    "               {\n" +
                    "                   \"id\": 4,\n" +
                    "                   \"question\": \"No\",\n" +
                    "                   \"question_id\": 2\n" +
                    "               }\n" +
                    "           ]\n" +
                    "       },\n" +
                    "       {\n" +
                    "           \"id\": 3,\n" +
                    "           \"question\": \"nh\",\n" +
                    "           \"survey_id\": 6,\n" +
                    "           \"question_type\": \"radio\",\n" +
                    "           \"options\": [\n" +
                    "               {\n" +
                    "                   \"id\": 5,\n" +
                    "                   \"question\": \"No\",\n" +
                    "                   \"question_id\": 3\n" +
                    "               },\n" +
                    "               {\n" +
                    "                   \"id\": 6,\n" +
                    "                   \"question\": \"New Party\",\n" +
                    "                   \"question_id\": 3\n" +
                    "               }\n" +
                    "           ]\n" +
                    "       },\n" +
                    "       {\n" +
                    "           \"id\": 4,\n" +
                    "           \"question\": \"Select Options.\",\n" +
                    "           \"survey_id\": 7,\n" +
                    "           \"question_type\": \"checkbox\",\n" +
                    "           \"options\": [\n" +
                    "               {\n" +
                    "                   \"id\": 5,\n" +
                    "                   \"question\": \"Option One\",\n" +
                    "                   \"question_id\": 4\n" +
                    "               },\n" +
                    "               {\n" +
                    "                   \"id\": 6,\n" +
                    "                   \"question\": \"Option Two\",\n" +
                    "                   \"question_id\": 4\n" +
                    "               }\n" +
                    "           ]\n" +
                    "       },\n" +
                    "       {\n" +
                    "           \"id\": 5,\n" +
                    "           \"question\": \"Rate out of five.\",\n" +
                    "           \"survey_id\": 7,\n" +
                    "           \"question_type\": \"rating\",\n" +
                    "           \"options\": [\n" +
                    "               {\n" +
                    "                   \"id\": 6,\n" +
                    "                   \"question\": \"5\",\n" +
                    "                   \"question_id\": 5\n" +
                    "               }"+
                    "           ]\n" +
                    "       },\n" +
                    "       {\n" +
                    "           \"id\": 7,\n" +
                    "           \"question\": \"Full Name.\",\n" +
                    "           \"survey_id\": 7,\n" +
                    "           \"question_type\": \"edittext\",\n" +
                    "           \"options\": [\n" +
                    "               {\n" +
                    "                   \"id\": 6,\n" +
                    "                   \"question\": \"Please enter your name\",\n" +
                    "                   \"question_id\": 7\n" +
                    "               }"+
                    "           ]\n" +
                    "       }\n" +
                    "   ]";
            return new JSONArray(json);
        } catch (Exception e){ return new JSONArray(); }
    }
}

class SurveyAdapter extends RecyclerView.Adapter<SurveyViewHolder> {
    JSONArray surveyArray;
    Context context ;
    int arrayCounter = -1 ;
    public SurveyAdapter(JSONArray surveyArray) {
        this.surveyArray = surveyArray;
    }

    private CardView convert(JSONObject jsonObject, Context context) {
        this.context = context;
        try {
            JsonView.ViewParams params = JsonView.ViewParams.instanceFromJSON(jsonObject);
            JsonView jsonView = new JsonView(context);
            jsonView.init(params).create();

           return wrapByCardView(jsonView);
        }catch (Exception e){e.printStackTrace();
        return wrapByCardView(new LinearLayout(context));
        }
    }

    @NonNull
    @Override
    public SurveyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        arrayCounter++;
        Log.d("SurveyAdapter", "onCreateViewHolder: optJSON "+surveyArray.optJSONObject(arrayCounter)+" position : "+arrayCounter);
        return new SurveyViewHolder(convert(surveyArray.optJSONObject(arrayCounter), viewGroup.getContext()));
    }

    @Override
    public void onBindViewHolder(@NonNull SurveyViewHolder surveyViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        Log.d("SurveyRecycler", "getItemCount: "+surveyArray.length());
        return surveyArray.length();
    }

    private CardView wrapByCardView(LinearLayout linearLayout){
        CardView card = new CardView(FactsNepal.getInstance());

        // Set the CardView layoutParams
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(16, 16, 16, 16);
        card.setLayoutParams(params);

        // Set CardView corner radius
        card.setRadius(9);

        // Set cardView content padding
        card.setContentPadding(15, 15, 15, 15);

        // Set a background color for CardView
        card.setCardBackgroundColor(FactsNepal.getInstance().getResources().getColor(R.color.silver_grey_windows_background));

        // Set the CardView maximum elevation
        card.setMaxCardElevation(15);

        // Set CardView elevation
        card.setCardElevation(9);


        // Put the TextView in CardView
        card.addView(linearLayout);

        return card;
    }

}



class SurveyViewHolder extends RecyclerView.ViewHolder {
    public SurveyViewHolder(View view) {
        super(view);
    }


}
