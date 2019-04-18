
package np.com.naxa.factsnepal.userprofile.earninghistory;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UsersEarningResponse {

    @SerializedName("earning")
    @Expose
    private List<Earning> earning = null;
    @SerializedName("survey_details")
    @Expose
    private List<SurveyDetail> surveyDetails = null;

    public List<Earning> getEarning() {
        return earning;
    }

    public void setEarning(List<Earning> earning) {
        this.earning = earning;
    }

    public List<SurveyDetail> getSurveyDetails() {
        return surveyDetails;
    }

    public void setSurveyDetails(List<SurveyDetail> surveyDetails) {
        this.surveyDetails = surveyDetails;
    }

}
