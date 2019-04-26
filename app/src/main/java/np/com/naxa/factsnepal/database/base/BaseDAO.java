package np.com.naxa.factsnepal.database.base;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import java.util.List;


public interface BaseDAO<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<T> items);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(T... items);

    @Update
    void update(T entity);

    @Delete
    void delete(T entity);
}
