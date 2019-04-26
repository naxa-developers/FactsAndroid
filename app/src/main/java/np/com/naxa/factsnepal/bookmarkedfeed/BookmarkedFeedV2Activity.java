package np.com.naxa.factsnepal.bookmarkedfeed;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import np.com.naxa.factsnepal.R;
import np.com.naxa.factsnepal.common.BaseActivity;
import np.com.naxa.factsnepal.common.BaseRecyclerViewAdapter;
import np.com.naxa.factsnepal.common.Constant;
import np.com.naxa.factsnepal.feed.Fact;
import np.com.naxa.factsnepal.feed.FactsLocalSource;
import np.com.naxa.factsnepal.feed.detail.FactDetailActivity;

public class BookmarkedFeedV2Activity extends BaseActivity {


    private RecyclerView recyclerView;
    private BaseRecyclerViewAdapter<Fact, BookmarkedFactsVH> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarked_feed_v2);

        setupToolbar();
        bindUI();

        FactsLocalSource.getINSTANCE().getAllBookmarked()
                .observe(this, (List<Fact> facts) -> {
                    if(facts != null) {
                        setupListAdapter(facts);
                    }
                });
    }


    private void bindUI() {
        recyclerView = findViewById(R.id.rv_bookmarked_facts);
    }


    private void setupListAdapter(List<Fact> facts) {

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new BaseRecyclerViewAdapter<Fact, BookmarkedFactsVH>(facts, R.layout.bookmarked_fact_list_item_row_layout) {


            @Override
            public void viewBinded(BookmarkedFactsVH bookmarkedFactsVH, Fact fact, int position) {
                bookmarkedFactsVH.bindView(fact, new BookmarkedFactsClickListner() {
                    @Override
                    public void unBookmarkedListner(Fact fact) {
                        Log.d("Bookmarked", "unBookmarkedListner: ");
                    }

                    @Override
                    public void viewBookmarkedListner(Fact fact, ImageView imageView) {
                        Log.d("Bookmarked", "viewBookmarkedListner: ");
                        openFactDetailsActivity(fact, imageView);
                    }
                });
            }


            @Override
            public BookmarkedFactsVH attachViewHolder(View view) {
                return new BookmarkedFactsVH(view);
            }
        };

        recyclerView.setAdapter(adapter);
    }


    private void openFactDetailsActivity(Fact fact, ImageView imageView){
        Intent intent = new Intent(this, FactDetailActivity.class);
        intent.putExtra(Constant.EXTRA_IMAGE, fact);

        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, imageView, getString(R.string.transtion_fact_list_details));
        startActivity(intent, options.toBundle());
    }

}
