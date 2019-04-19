
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


    @org.jetbrains.annotations.Contract(pure = true)
    public static String getDemoJson (){
        String json = "{\n" +
                "   \"survey_question\": [\n" +
                "       {\n" +
                "           \"id\": 1,\n" +
                "           \"question\": \"h\",\n" +
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
                "                   \"question\": \"New Party\",\n" +
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
                "                   \"question\": \"No\",\n" +
                "                   \"question_id\": 2\n" +
                "               },\n" +
                "               {\n" +
                "                   \"id\": 4,\n" +
                "                   \"question\": \"New Party\",\n" +
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
                "       }\n" +
                "   ]\n" +
                "}";

        return json;
    }

}
