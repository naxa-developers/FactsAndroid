package np.com.naxa.factsnepal.network.facts;

import androidx.annotation.NonNull;
import android.util.Log;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import np.com.naxa.factsnepal.network.NetworkApiInterface;

public class FetchFacts {
    private static final String TAG = "FetchFacts";


    public static void fetchFactsFromServer(@NonNull NetworkApiInterface networkApiInterface){

        networkApiInterface.getFactsDetailsResponse(null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new DisposableObserver<List<FactsResponse>>() {
            @Override
            public void onNext(List<FactsResponse> factsResponse) {
                Log.d(TAG, "onNext: "+factsResponse.get(0).toString());
            }

            @Override
            public void onError(Throwable e) {

                Log.d(TAG, "onError: "+e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");

            }
        });
    }
}
