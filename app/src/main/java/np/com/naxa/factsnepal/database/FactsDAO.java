package np.com.naxa.factsnepal.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

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
}
