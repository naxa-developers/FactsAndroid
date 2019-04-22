package np.com.naxa.factsnepal.feed.list.resource;

import java.util.List;

import np.com.naxa.factsnepal.database.FactsDAO;
import np.com.naxa.factsnepal.feed.Fact;
import np.com.naxa.factsnepal.feed.FactsLocalSource;

public class LocalSource implements Source<Fact> {


    private static LocalSource INSTANCE = null;
    private FactsDAO factsDAO;

    public static LocalSource getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new LocalSource();
        }
        return INSTANCE;
    }

    @Override
    public List<Fact> fetchAll() {
        return null;
    }

    @Override
    public List<Fact> fetchById(int id) {
        return null;
    }
}
