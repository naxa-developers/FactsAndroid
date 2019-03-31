package np.com.naxa.factsnepal.network;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import np.com.naxa.factsnepal.network.facts.FactsResponse;
import np.com.naxa.factsnepal.userprofile.UserDetails;
import np.com.naxa.factsnepal.userprofile.UserDetailsResponse;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface NetworkApiInterface {
    @POST(UrlConstant.FETCH_CONFIG_URL)
    @FormUrlEncoded
    Observable<List<FactsResponse>> getFactsDetailsResponse(@Field("category")ArrayList<Integer> categories);

    @Headers("Content-Type: application/json; charset=utf-8")
    @POST(UrlConstant.POST_USER_DETAILS_URL)
    Observable<UserDetailsResponse> getUserDetailsResponse(@Body UserDetails userDetails);

}
