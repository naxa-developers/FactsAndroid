package np.com.naxa.factsnepal.feed.list.resource;

import java.util.List;

public interface Source<T> {
    List<T> fetchAll();

    List<T> fetchById(int id);
}
