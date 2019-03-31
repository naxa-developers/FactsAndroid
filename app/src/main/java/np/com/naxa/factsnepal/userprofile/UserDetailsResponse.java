package np.com.naxa.factsnepal.userprofile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDetailsResponse {

    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("data")
    @Expose
    private UserDetails data;
    @SerializedName("message")
    @Expose
    private String message;

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public UserDetails getData() {
        return data;
    }

    public void setData(UserDetails data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
