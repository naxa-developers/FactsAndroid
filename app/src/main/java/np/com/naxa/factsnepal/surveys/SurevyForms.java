
package np.com.naxa.factsnepal.surveys;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SurevyForms {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("public_date")
    @Expose
    private Integer publicDate;
    @SerializedName("survey_id")
    @Expose
    private Integer surveyId;
    @SerializedName("question_type")
    @Expose
    private String questionType;
    @SerializedName("active")
    @Expose
    private Integer active;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getPublicDate() {
        return publicDate;
    }

    public void setPublicDate(Integer publicDate) {
        this.publicDate = publicDate;
    }

    public Integer getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Integer surveyId) {
        this.surveyId = surveyId;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }


}
