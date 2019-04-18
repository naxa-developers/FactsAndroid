
package np.com.naxa.factsnepal.surveys.surveyforms;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SurveyQuestionDetailsResponse {

    @SerializedName("survey_question")
    @Expose
    private List<SurveyQuestion> surveyQuestion = null;

    public List<SurveyQuestion> getSurveyQuestion() {
        return surveyQuestion;
    }

    public void setSurveyQuestion(List<SurveyQuestion> surveyQuestion) {
        this.surveyQuestion = surveyQuestion;
    }

}
