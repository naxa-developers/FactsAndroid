
package np.com.naxa.factsnepal.publicpoll;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostPublicPollAnswerResponse {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result_data")
    @Expose
    private List<ResultData> resultData = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ResultData> getResultData() {
        return resultData;
    }

    public void setResultData(List<ResultData> resultData) {
        this.resultData = resultData;
    }

}
