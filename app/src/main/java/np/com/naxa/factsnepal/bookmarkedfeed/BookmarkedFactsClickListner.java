package np.com.naxa.factsnepal.bookmarkedfeed;

import android.widget.ImageView;

import np.com.naxa.factsnepal.feed.Fact;

public interface BookmarkedFactsClickListner {

    public abstract void unBookmarkedListner(Fact fact);

    public abstract void viewBookmarkedListner(Fact fact, ImageView imageView);
}
