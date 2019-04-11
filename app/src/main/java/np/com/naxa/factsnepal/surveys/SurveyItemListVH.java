package np.com.naxa.factsnepal.surveys;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import np.com.naxa.factsnepal.FactsNepal;
import np.com.naxa.factsnepal.R;
import np.com.naxa.factsnepal.utils.ImageUtils;

public class SurveyItemListVH extends RecyclerView.ViewHolder {
    private TextView tvSurveyName, tvSurveyDate;
    ImageView ivSurveyImage;
    int type;


    public SurveyItemListVH(@NonNull View itemView, int type) {
        super(itemView);
        this.type = type;

        tvSurveyName = itemView.findViewById(R.id.tv_survey_list_item_title);
        tvSurveyDate = itemView.findViewById(R.id.tv_survey_list_item_date);
        ivSurveyImage = itemView.findViewById(R.id.iv_survey_list_item_image);

        if(type == SurveyCompanyListActivity.KEY_FORM_TYPE){
            ivSurveyImage.setVisibility(View.GONE);
        }

    }

    void bindView(SurveyCompany surveyCompany) {
        tvSurveyName.setText(surveyCompany.getTitle());
        tvSurveyDate.setText(surveyCompany.getShortdesc());

        if(type == SurveyCompanyListActivity.KEY_COMPANY_TYPE) {
            ImageUtils.loadRemoteImage(FactsNepal.getInstance(), surveyCompany.getImage())
                    .into(ivSurveyImage);
        }

    }

    void bindView(SurevyForms surevyForms) {
        tvSurveyName.setText(surevyForms.getQuestion());
        tvSurveyDate.setText(surevyForms.getPublicDate());

    }
}
