
package np.com.naxa.factsnepal.surveys;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SurveyCompanyDetails implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.surveyCompany);
        dest.writeTypedList(this.surevyForms);
    }

    public SurveyCompanyDetails() {
    }

    protected SurveyCompanyDetails(Parcel in) {
        this.surveyCompany = in.createTypedArrayList(SurveyCompany.CREATOR);
        this.surevyForms = in.createTypedArrayList(SurevyForms.CREATOR);
    }

    public static final Parcelable.Creator<SurveyCompanyDetails> CREATOR = new Parcelable.Creator<SurveyCompanyDetails>() {
        @Override
        public SurveyCompanyDetails createFromParcel(Parcel source) {
            return new SurveyCompanyDetails(source);
        }

        @Override
        public SurveyCompanyDetails[] newArray(int size) {
            return new SurveyCompanyDetails[size];
        }
    };
}
