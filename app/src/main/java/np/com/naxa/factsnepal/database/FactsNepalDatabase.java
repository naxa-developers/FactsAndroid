package np.com.naxa.factsnepal.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import np.com.naxa.factsnepal.feed.Fact;


@Database(entities =
        {
                Fact.class,
        }, version = 1)


public abstract class FactsNepalDatabase extends RoomDatabase {

    private static FactsNepalDatabase INSTANCE;
    private static final String DB_PATH = "facts_nepal.db";


    public static FactsNepalDatabase getDatabase(final Context context) {
        if (INSTANCE != null) {
            return INSTANCE;
        }

        synchronized (FactsNepalDatabase.class) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        FactsNepalDatabase.class, DB_PATH)
                        .fallbackToDestructiveMigration()//todo: remove this once we are live
                        .build();
            }
        }

        return INSTANCE;
    }

    public abstract FactsDAO getFactsDAO();

}
