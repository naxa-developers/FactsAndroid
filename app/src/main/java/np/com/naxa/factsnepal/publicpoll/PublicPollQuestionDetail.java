
package np.com.naxa.factsnepal.publicpoll;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PublicPollQuestionDetail {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("day_poll")
    @Expose
    private Integer dayPoll;
    @SerializedName("poll_date")
    @Expose
    private String pollDate;
    @SerializedName("question_type")
    @Expose
    private String questionType;
    @SerializedName("active")
    @Expose
    private Integer active;
    @SerializedName("options")
    @Expose
    private List<Option> options = null;

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

    public Integer getDayPoll() {
        return dayPoll;
    }

    public void setDayPoll(Integer dayPoll) {
        this.dayPoll = dayPoll;
    }

    public String getPollDate() {
        return pollDate;
    }

    public void setPollDate(String pollDate) {
        this.pollDate = pollDate;
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

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

}
