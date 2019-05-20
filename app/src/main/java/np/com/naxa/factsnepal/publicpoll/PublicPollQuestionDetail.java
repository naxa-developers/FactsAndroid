
package np.com.naxa.factsnepal.publicpoll;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PublicPollQuestionDetail implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.question);
        dest.writeValue(this.dayPoll);
        dest.writeString(this.pollDate);
        dest.writeString(this.questionType);
        dest.writeValue(this.active);
        dest.writeList(this.options);
    }

    public PublicPollQuestionDetail() {
    }

    protected PublicPollQuestionDetail(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.question = in.readString();
        this.dayPoll = (Integer) in.readValue(Integer.class.getClassLoader());
        this.pollDate = in.readString();
        this.questionType = in.readString();
        this.active = (Integer) in.readValue(Integer.class.getClassLoader());
        this.options = new ArrayList<Option>();
        in.readList(this.options, Option.class.getClassLoader());
    }

    public static final Parcelable.Creator<PublicPollQuestionDetail> CREATOR = new Parcelable.Creator<PublicPollQuestionDetail>() {
        @Override
        public PublicPollQuestionDetail createFromParcel(Parcel source) {
            return new PublicPollQuestionDetail(source);
        }

        @Override
        public PublicPollQuestionDetail[] newArray(int size) {
            return new PublicPollQuestionDetail[size];
        }
    };
}
