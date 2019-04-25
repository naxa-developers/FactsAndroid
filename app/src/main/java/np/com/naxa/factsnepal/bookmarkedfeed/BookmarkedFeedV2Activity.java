package np.com.naxa.factsnepal.bookmarkedfeed;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import np.com.naxa.factsnepal.R;
import np.com.naxa.factsnepal.common.BaseActivity;
import np.com.naxa.factsnepal.common.BaseRecyclerViewAdapter;
import np.com.naxa.factsnepal.feed.Fact;
import np.com.naxa.factsnepal.feed.FactsLocalSource;

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
                    public void viewBookmarkedListner(Fact fact) {
                        Log.d("Bookmarked", "viewBookmarkedListner: ");
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


}
