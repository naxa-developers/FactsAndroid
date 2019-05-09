package np.com.naxa.factsnepal.surveys;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostSurveyAnswerResponse {
    @SerializedName("success")
    @Expose
    private boolean success;

    @SerializedName("error")
    @Expose
    private String error;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
