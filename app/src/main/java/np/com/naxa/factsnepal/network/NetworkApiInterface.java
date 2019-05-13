package np.com.naxa.factsnepal.network;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import np.com.naxa.factsnepal.common.Constant;
import np.com.naxa.factsnepal.network.facts.FactsResponse;
import np.com.naxa.factsnepal.surveys.PostSurveyAnswerResponse;
import np.com.naxa.factsnepal.surveys.SurveyCompanyDetails;
import np.com.naxa.factsnepal.surveys.surveyforms.SurveyQuestionDetailsResponse;
import np.com.naxa.factsnepal.userprofile.LoginCredentials;
import np.com.naxa.factsnepal.userprofile.UserLoginResponse;
import np.com.naxa.factsnepal.userprofile.UserRegistrationDetails;
import np.com.naxa.factsnepal.userprofile.UserRegistrationDetailsResponse;
import np.com.naxa.factsnepal.userprofile.earninghistory.UsersEarningResponse;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NetworkApiInterface {
    @POST(UrlConstant.FETCH_CONFIG_URL)
    @FormUrlEncoded
    Observable<List<FactsResponse>> getFactsDetailsResponse(@Field("category") List<Integer> categories);

    @Headers("Content-Type: application/json; charset=utf-8")
    @POST(UrlConstant.POST_USER_DETAILS_URL)
    Observable<UserRegistrationDetailsResponse> getUserDetailsResponse(@Body UserRegistrationDetails userRegistrationDetails);

    @GET(UrlConstant.GET_SURVEY_COMPANY_DETAILS_URL)
    Observable<SurveyCompanyDetails> getSurveyCompanyDetailsResponse();

    @Headers("Content-Type: application/json; charset=utf-8")
    @POST(UrlConstant.POST_USER_LOGIN_URL)
    Observable<UserLoginResponse> getUserLoginResponse(@Body LoginCredentials loginCredentials);

    @POST(UrlConstant.GET_USER_DETAILS_URL)
    Observable<UserLoginResponse> getUserDetailsResponse(@HeaderMap Map<String, String> headers);

    @Headers("Content-Type: application/json; charset=utf-8")
    @GET(UrlConstant.GET_SURVEY_QUESTION_DETAILS_URL + "/{company_id}/{survey_id}")
    Observable<SurveyQuestionDetailsResponse> getSurveyQuestionDetailsResponse(@Path("company_id") int company_id,
                                                                               @Path("survey_id") int survey_id);

    @POST(UrlConstant.GET_USER_EARNING_HISTORY_DETAILS_URL)
    Observable<UsersEarningResponse> getUserEarningDetailsResponse(@HeaderMap Map<String, String> headers);


    @Headers("Content-Type: application/json; charset=utf-8")
    @GET(UrlConstant.GET_SAMPLE_SURVEY_QUESTION_DETAILS_URL)
    Observable<SurveyQuestionDetailsResponse> getSampleSurveyQuestionDetailsResponse(@Query("file_name") String filename);

//    @Headers("Content-Type: application/json; charset=utf-8")
//    @GET(UrlConstant.GET_SAMPLE_SURVEY_QUESTION_DETAILS_URL)
//    Call<Response<SurveyQuestionDetailsResponse>> getSampleSurveyQuestionDetailsResponse(@Query("file_name") String filename);

    @Headers("Content-Type: application/json; charset=utf-8")
    @POST(UrlConstant.POST_SURVEY_ANSWER_DETAILS_URL)
    @FormUrlEncoded
    Observable<PostSurveyAnswerResponse> postSurveyAnswerDetailsResponse(@Field(Constant.JsonKeySurvey.KEY_JSON_DATA) String formData);
}