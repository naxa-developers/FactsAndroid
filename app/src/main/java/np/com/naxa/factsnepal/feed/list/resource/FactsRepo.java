package np.com.naxa.factsnepal.feed.list.resource;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.observers.DisposableObserver;
import np.com.naxa.factsnepal.FactsNepal;
import np.com.naxa.factsnepal.R;
import np.com.naxa.factsnepal.common.NetworkUtils;
import np.com.naxa.factsnepal.feed.Fact;
import np.com.naxa.factsnepal.feed.FactsLocalSource;
import np.com.naxa.factsnepal.feed.list.FactsRemoteSource;
import np.com.naxa.factsnepal.network.facts.Category;

public class FactsRepo {

    private static FactsRepo INSTANCE = null;

    public static FactsRepo getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new FactsRepo();
        }
        return INSTANCE;
    }


    public LiveData<List<Fact>> getAllFacts(boolean refreshCache) {
        Log.d("FactsRepo", "get ALl Fact called");
        if (refreshCache && NetworkUtils.isConnected()) {
            FactsRemoteSource.getINSTANCE().getAllFacts().subscribe(new DisposableObserver<List<Fact>>() {
                @Override
                public void onNext(List<Fact> facts) {
                    Log.d("success", "datarecieved");
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            });
        }
        return FactsLocalSource.getINSTANCE().getAll();
    }


    private void showMessage(String message) {
            Toast.makeText(FactsNepal.getInstance(), message, Toast.LENGTH_SHORT).show();
    }

    public LiveData<Fact> getById(String factId, boolean refreshCache) {
        if (refreshCache) {
            //not implemented
        }
        return FactsLocalSource.getINSTANCE().getById(factId);
    }

    public Single<List<Fact>> getFactsCategories(boolean refreshCache) {
        Log.d("FactsRepo", "categories by id called");
        if (refreshCache && NetworkUtils.isConnected()) {
            FactsRemoteSource.getINSTANCE().getCategories()
                    .subscribe(new DisposableObserver<List<Category>>() {
                        @Override
                        public void onNext(List<Category> categories) {
                            Log.d("FactsRepo", "data recieved by categories");
                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            showMessage(FactsNepal.getInstance().getString(R.string.msg_can_not_refresh_feed));
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }

        return FactsLocalSource.getINSTANCE().getAllCategories();

    }

    public LiveData<List<Fact>> getByCategoryIds(List<Integer> categoryIds, boolean refreshCache) {
        if (refreshCache && NetworkUtils.isConnected()) {
            FactsRemoteSource.getINSTANCE().getFactsByCategoryId(categoryIds)
                    .subscribe(new DisposableObserver<List<Fact>>() {
                        @Override
                        public void onNext(List<Fact> facts) {

                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            showMessage(FactsNepal.getInstance().getString(R.string.msg_can_not_refresh_feed));

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
        return FactsLocalSource.getINSTANCE().getByIds(categoryIds);
    }
}
