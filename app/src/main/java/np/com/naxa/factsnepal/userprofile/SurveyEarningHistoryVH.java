package np.com.naxa.factsnepal.userprofile;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import np.com.naxa.factsnepal.R;
import np.com.naxa.factsnepal.userprofile.earninghistory.SurveyDetail;

public class SurveyEarningHistoryVH extends RecyclerView.ViewHolder {
    private TextView tvSurveyName, tvSurveyDate, tvEarning;
        private View rootLayout;


        public SurveyEarningHistoryVH(@NonNull View itemView) {
            super(itemView);
            tvSurveyName = itemView.findViewById(R.id.tv_survey_name);
            tvSurveyDate = itemView.findViewById(R.id.tv_survey_date);
            tvEarning = itemView.findViewById(R.id.tv_earning_from_survey);
            rootLayout = itemView.findViewById(R.id.root_layout);

        }

        void bindView(SurveyDetail surveyDetail) {
            tvSurveyName.setText(surveyDetail.getSurveyName());
            tvSurveyDate.setText(surveyDetail.getSurveydate());
            tvEarning.setText(surveyDetail.getTotal());
//        rootLayout.setEnabled(factsNotification.isRead());
        }
}
