
package np.com.naxa.factsnepal.publicpoll;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultData implements Parcelable {

    @SerializedName("total_count")
    @Expose
    private Integer totalCount;
    @SerializedName("question")
    @Expose
    private String question;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.totalCount);
        dest.writeString(this.question);
    }

    public ResultData() {
    }

    protected ResultData(Parcel in) {
        this.totalCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.question = in.readString();
    }

    public static final Parcelable.Creator<ResultData> CREATOR = new Parcelable.Creator<ResultData>() {
        @Override
        public ResultData createFromParcel(Parcel source) {
            return new ResultData(source);
        }

        @Override
        public ResultData[] newArray(int size) {
            return new ResultData[size];
        }
    };
}
