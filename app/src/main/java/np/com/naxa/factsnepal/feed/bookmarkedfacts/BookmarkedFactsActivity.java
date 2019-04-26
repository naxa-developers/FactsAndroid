package np.com.naxa.factsnepal.feed.bookmarkedfacts;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.schedulers.Schedulers;
import np.com.naxa.factsnepal.R;
import np.com.naxa.factsnepal.common.BaseActivity;
import np.com.naxa.factsnepal.common.Constant;
import np.com.naxa.factsnepal.common.ListPaddingDecoration;
import np.com.naxa.factsnepal.feed.Fact;
import np.com.naxa.factsnepal.feed.FactsLocalSource;
import np.com.naxa.factsnepal.feed.detail.FactDetailActivity;
import np.com.naxa.factsnepal.feed.list.FactsFeedAdapter;
import np.com.naxa.factsnepal.utils.ActivityUtil;

public class BookmarkedFactsActivity extends BaseActivity implements FactsFeedAdapter.OnFeedCardItemClickListener {

    private RecyclerView recyclerViewFeed;
    private FactsFeedAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.facts_bookmarked_activity);
        bindUI();
        setupRecyclerView();
        setupToolbar(getString(R.string.toolbar_title_bookmarked));


        FactsLocalSource.getINSTANCE().getAllBookmarked()
                .observe(this, (List<Fact> facts) -> {
                    adapter.updateList(facts);
                });
    }

    private void bindUI() {
        recyclerViewFeed = findViewById(R.id.rv_bookmarked_facts);
    }

    private void setupRecyclerView() {
        adapter = new FactsFeedAdapter(new ArrayList<>(), this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewFeed.setLayoutManager(layoutManager);
        recyclerViewFeed.addItemDecoration(new ListPaddingDecoration(this));
        recyclerViewFeed.setAdapter(adapter);
    }


    @Override
    public void onCardTap(Fact fact, ImageView imageView) {
        Intent intent = new Intent(this, FactDetailActivity.class);
        intent.putExtra(Constant.EXTRA_IMAGE, fact);

        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, imageView, getString(R.string.transtion_fact_list_details));
        startActivity(intent, options.toBundle());

    }

    @Override
    public void onBookmarkButtonTap(Fact fact) {

        FactsLocalSource.getINSTANCE().toggleBookMark(fact)
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    @Override
    public void onShareButtonTap(Fact fact) {
        ActivityUtil.openShareIntent(this, fact.getTitle());
    }

}
