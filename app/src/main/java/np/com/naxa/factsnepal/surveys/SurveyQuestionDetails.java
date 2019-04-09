
package np.com.naxa.factsnepal.surveys;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SurveyQuestionDetails {

    @SerializedName("survey_company")
    @Expose
    private List<SurveyCompany> surveyCompany = null;
    @SerializedName("surevy_fro")
    @Expose
    private List<SurevyFro> surevyFro = null;

    public List<SurveyCompany> getSurveyCompany() {
        return surveyCompany;
    }

    public void setSurveyCompany(List<SurveyCompany> surveyCompany) {
        this.surveyCompany = surveyCompany;
    }

    public List<SurevyFro> getSurevyFro() {
        return surevyFro;
    }

    public void setSurevyFro(List<SurevyFro> surevyFro) {
        this.surevyFro = surevyFro;
    }

}
