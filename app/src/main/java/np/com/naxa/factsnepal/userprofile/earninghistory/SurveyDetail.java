
package np.com.naxa.factsnepal.userprofile.earninghistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SurveyDetail {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("survey_name")
    @Expose
    private String surveyName;
    @SerializedName("survey_date")
    @Expose
    private String surveydate;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("active")
    @Expose
    private Integer active;

    public SurveyDetail(String surveyName, String surveydate, String total) {
        this.surveyName = surveyName;
        this.surveydate = surveydate;
        this.total = total;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSurveyName() {
        return surveyName;
    }

    public void setSurveyName(String surveyName) {
        this.surveyName = surveyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public String getSurveydate() {
        return surveydate;
    }

    public void setSurveydate(String surveydate) {
        this.surveydate = surveydate;
    }

    @Deprecated
    public static List<SurveyDetail> getDemoItems() {
        List<SurveyDetail> list = new ArrayList<>();

        list.add(new SurveyDetail("Ncell survey in customer research", "04-09-2019", "Rs.50"));
        list.add(new SurveyDetail("Ncell survey in Marketing research", "04-01-2019", "Rs.10"));
        list.add(new SurveyDetail("Ntc survey in Internet Status", "04-08-2019", "Rs.60"));
        list.add(new SurveyDetail("Tornado survey in Bara, Parsa District", "04-05-2019", "Rs.40"));
        list.add(new SurveyDetail("Weather status survey", "03-30-2019", "Rs.100"));
        list.add(new SurveyDetail("Transportation Facility survey", "02-09-2019", "Rs.30"));
        list.add(new SurveyDetail("Flood survey", "03-15-2019", "Rs.70"));
        list.add(new SurveyDetail("Ncell survey in customer research", "04-09-2019", "Rs.50"));
        list.add(new SurveyDetail("Ncell survey in Marketing research", "04-01-2019", "Rs.10"));
        list.add(new SurveyDetail("Ntc survey in Internet Status", "04-08-2019", "Rs.60"));
        list.add(new SurveyDetail("Tornado survey in Bara, Parsa District", "04-05-2019", "Rs.40"));
        list.add(new SurveyDetail("Weather status survey", "03-30-2019", "Rs.100"));
        list.add(new SurveyDetail("Transportation Facility survey", "02-09-2019", "Rs.30"));
        list.add(new SurveyDetail("Flood survey", "03-15-2019", "Rs.70"));


        return list;
    }
}
