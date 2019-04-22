package np.com.naxa.factsnepal.feed.list.resource;

import java.util.List;

import np.com.naxa.factsnepal.database.FactsDAO;
import np.com.naxa.factsnepal.feed.Fact;

public class RemoteSource implements Source<Fact> {

    private static RemoteSource INSTANCE = null;
    private FactsDAO factsDAO;

    public static RemoteSource getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteSource();
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
