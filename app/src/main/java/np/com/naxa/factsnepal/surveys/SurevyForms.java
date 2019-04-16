
package np.com.naxa.factsnepal.surveys;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SurevyForms implements Parcelable {

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
    private Integer surveyCompanyId;
    @SerializedName("survey_company_id")
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

    public Integer getSurveyCompanyId() {
        return surveyCompanyId;
    }

    public void setSurveyCompanyId(Integer surveyCompanyId) {
        this.surveyCompanyId = surveyCompanyId;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.question);
        dest.writeValue(this.publicDate);
        dest.writeValue(this.surveyCompanyId);
        dest.writeString(this.questionType);
        dest.writeValue(this.active);
    }

    public SurevyForms() {
    }

    protected SurevyForms(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.question = in.readString();
        this.publicDate = (Integer) in.readValue(Integer.class.getClassLoader());
        this.surveyCompanyId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.questionType = in.readString();
        this.active = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<SurevyForms> CREATOR = new Parcelable.Creator<SurevyForms>() {
        @Override
        public SurevyForms createFromParcel(Parcel source) {
            return new SurevyForms(source);
        }

        @Override
        public SurevyForms[] newArray(int size) {
            return new SurevyForms[size];
        }
    };
}
