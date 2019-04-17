package np.com.naxa.factsnepal.network;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import np.com.naxa.factsnepal.network.facts.FactsResponse;
import np.com.naxa.factsnepal.surveys.SurveyCompanyDetails;
import np.com.naxa.factsnepal.userprofile.LoginCredentials;
import np.com.naxa.factsnepal.userprofile.UserLoginResponse;
import np.com.naxa.factsnepal.userprofile.UserRegistrationDetails;
import np.com.naxa.factsnepal.userprofile.UserRegistrationDetailsResponse;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface NetworkApiInterface {
    @POST(UrlConstant.FETCH_CONFIG_URL)
    @FormUrlEncoded
    Observable<List<FactsResponse>> getFactsDetailsResponse(@Field("category")ArrayList<Integer> categories);

    @Headers("Content-Type: application/json; charset=utf-8")
    @POST(UrlConstant.POST_USER_DETAILS_URL)
    Observable<UserRegistrationDetailsResponse> getUserDetailsResponse(@Body UserRegistrationDetails userRegistrationDetails);

    @GET(UrlConstant.GET_SURVEY_COMPANY_DETAILS_URL)
    Observable<SurveyCompanyDetails> getSurveyQuestionDetailsResponse();

    @Headers("Content-Type: application/json; charset=utf-8")
    @POST(UrlConstant.POST_USER_LOGIN_URL)
    Observable<UserLoginResponse> getUserLoginResponse(@Body LoginCredentials loginCredentials);

    @POST(UrlConstant.GET_USER_DETAILS_URL)
    Observable<UserRegistrationDetailsResponse> getUserDetailsResponse(@HeaderMap Map<String, String> headers);


}