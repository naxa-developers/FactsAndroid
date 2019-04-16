
package np.com.naxa.factsnepal.surveys;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SurveyCompany implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("shortdesc")
    @Expose
    private String shortdesc;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortdesc() {
        return shortdesc;
    }

    public void setShortdesc(String shortdesc) {
        this.shortdesc = shortdesc;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.title);
        dest.writeString(this.image);
        dest.writeString(this.description);
        dest.writeString(this.shortdesc);
    }

    public SurveyCompany() {
    }

    protected SurveyCompany(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.title = in.readString();
        this.image = in.readString();
        this.description = in.readString();
        this.shortdesc = in.readString();
    }

    public static final Parcelable.Creator<SurveyCompany> CREATOR = new Parcelable.Creator<SurveyCompany>() {
        @Override
        public SurveyCompany createFromParcel(Parcel source) {
            return new SurveyCompany(source);
        }

        @Override
        public SurveyCompany[] newArray(int size) {
            return new SurveyCompany[size];
        }
    };
}
