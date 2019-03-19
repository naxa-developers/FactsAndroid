package np.com.naxa.factsnepal.network;

import java.util.List;

import io.reactivex.Observable;
import np.com.naxa.factsnepal.network.facts.FactsResponse;
import retrofit2.http.GET;

public interface NetworkApiInterface {
    @GET(UrlClass.FETCH_FACTS_URL)
    Observable<List<FactsResponse>> getFactsDetailsResponse();
}
