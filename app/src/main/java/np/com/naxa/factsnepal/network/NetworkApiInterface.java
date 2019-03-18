package np.com.naxa.factsnepal.network;

import io.reactivex.Observable;
import np.com.naxa.factsnepal.network.facts.FactsResponse;
import retrofit2.http.GET;

public interface NetworkApiInterface {
    @GET("MapApi/get_category")
    Observable<FactsResponse> getFactsDetailsResponse();
}
