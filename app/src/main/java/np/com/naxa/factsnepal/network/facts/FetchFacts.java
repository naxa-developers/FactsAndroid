package np.com.naxa.factsnepal.network.facts;

import android.support.annotation.NonNull;
import android.util.Log;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import np.com.naxa.factsnepal.network.NetworkApiInterface;

public class FetchFacts {
    private static final String TAG = "FetchFacts";


    public static void fetchFactsFromServer(@NonNull NetworkApiInterface networkApiInterface){

        networkApiInterface.getFactsDetailsResponse()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<FactsResponse>() {
                    @Override
                    public void onNext(FactsResponse factsResponse) {

                        Log.d(TAG, "onNext: "+factsResponse.toString());

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onNext: "+e.getMessage());

                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }
}
