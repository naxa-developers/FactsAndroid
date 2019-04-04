package np.com.naxa.factsnepal.feed;

import java.util.List;

import io.reactivex.Completable;
import np.com.naxa.factsnepal.FactsNepal;
import np.com.naxa.factsnepal.database.FactsDAO;
import np.com.naxa.factsnepal.database.FactsNepalDatabase;
import np.com.naxa.factsnepal.database.base.BaseLocalDataSourceRX;

public class FactsLocalSource implements BaseLocalDataSourceRX<Fact> {

    private static FactsLocalSource INSTANCE = null;
    private FactsDAO factsDAO;

    public static FactsLocalSource getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new FactsLocalSource();
        }
        return INSTANCE;
    }

    private FactsLocalSource() {
        this.factsDAO = FactsNepalDatabase.getDatabase(FactsNepal.getInstance()).getFactsDAO();
    }

    private Completable markFactAsBookmarked(Fact fact) {
        return Completable.fromAction(() -> {
            fact.setBookmarked(true);
            factsDAO.update(fact);
        });

    }

    private Completable markFactAsNotBookmarked(Fact fact) {
        return Completable.fromAction(() -> {
            fact.setBookmarked(false);
            factsDAO.update(fact);
        });
    }

    @Override
    public Completable saveCompletable(Fact... items) {
        return Completable.fromAction(() -> factsDAO.insert(items));
    }

    @Override
    public Completable saveCompletable(List<Fact> items) {
        return Completable.fromAction(() -> factsDAO.insert(items));
    }
}
