package np.com.naxa.factsnepal.bookmarkedfeed;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.reactivex.schedulers.Schedulers;
import np.com.naxa.factsnepal.R;
import np.com.naxa.factsnepal.common.BaseActivity;
import np.com.naxa.factsnepal.common.BaseRecyclerViewAdapter;
import np.com.naxa.factsnepal.common.Constant;
import np.com.naxa.factsnepal.feed.Fact;
import np.com.naxa.factsnepal.feed.FactsLocalSource;
import np.com.naxa.factsnepal.feed.detail.FactDetailActivity;
import np.com.naxa.factsnepal.utils.DialogUtils;

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

                       createUnBookmarkAlertDialog(fact);
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


    private void createUnBookmarkAlertDialog (Fact fact){
        new AlertDialog.Builder(BookmarkedFeedV2Activity.this)
                .setTitle("Un Bookmark Fact")
                .setMessage("Are you sure you want to Un Bookmark this Fact?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        FactsLocalSource.getINSTANCE().toggleBookMark(fact)
                                .subscribeOn(Schedulers.io())
                                .subscribe();
                        Toast.makeText(BookmarkedFeedV2Activity.this, "Un Bookmarked Successfully", Toast.LENGTH_SHORT).show();
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}
