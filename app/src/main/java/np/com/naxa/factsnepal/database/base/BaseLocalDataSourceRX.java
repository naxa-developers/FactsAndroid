package np.com.naxa.factsnepal.database.base;

import java.util.List;

import io.reactivex.Completable;

public interface BaseLocalDataSourceRX<T> {

    Completable saveCompletable(T... items);

    Completable saveCompletable(List<T> items);

    void saveAsync(List<T> items);

    void save(List<T> items);

}
