package np.com.naxa.factsnepal.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Single;
import np.com.naxa.factsnepal.database.base.BaseDAO;
import np.com.naxa.factsnepal.feed.Fact;

@Dao
public abstract class FactsDAO implements BaseDAO<Fact> {
    @Query("SELECT * from facts")
    public abstract LiveData<List<Fact>> getAll();

    @Query("SELECT * from facts WHERE isBookmarked=1")
    public abstract LiveData<List<Fact>> getAllBookmarked();

    @Query("SELECT * FROM facts WHERE id=:id")
    public abstract LiveData<Fact> getById(String id);

    @Query("SELECT * FROM facts WHERE catgoryId IN(:categoryIds)")
    public abstract LiveData<List<Fact>> getByIds(List<Integer> categoryIds);

    @Query("SELECT * FROM facts GROUP BY catgoryId")
    public abstract Single<List<Fact>> getAllCategories();

}
