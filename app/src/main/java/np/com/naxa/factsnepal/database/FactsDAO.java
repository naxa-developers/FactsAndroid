package np.com.naxa.factsnepal.database;

import android.arch.persistence.room.Dao;

import np.com.naxa.factsnepal.database.base.BaseDAO;
import np.com.naxa.factsnepal.feed.Fact;

@Dao
public abstract class FactsDAO implements BaseDAO<Fact> {
}
