
package np.com.naxa.factsnepal.surveys;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SurveyCompanyDetails {

    @SerializedName("survey_company")
    @Expose
    private List<SurveyCompany> surveyCompany = null;
    @SerializedName("forms")
    @Expose
    private List<SurevyForms> surevyForms = null;

    public List<SurveyCompany> getSurveyCompany() {
        return surveyCompany;
    }

    public void setSurveyCompany(List<SurveyCompany> surveyCompany) {
        this.surveyCompany = surveyCompany;
    }

    public List<SurevyForms> getSurevyForms() {
        return surevyForms;
    }

    public void setSurevyForms(List<SurevyForms> surevyForms) {
        this.surevyForms = surevyForms;
    }

}
