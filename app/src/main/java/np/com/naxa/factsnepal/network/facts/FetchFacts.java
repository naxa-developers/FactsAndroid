package np.com.naxa.factsnepal.network.facts;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import np.com.naxa.factsnepal.network.NetworkApiInterface;

public class FetchFacts {
    private static final String TAG = "FetchFacts";


    public static void fetchFactsFromServer(@NonNull NetworkApiInterface networkApiInterface){

        networkApiInterface.getFactsDetailsResponse()
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
