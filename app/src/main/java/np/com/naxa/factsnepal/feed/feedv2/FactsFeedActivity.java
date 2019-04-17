package np.com.naxa.factsnepal.feed.feedv2;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.observers.DisposableObserver;
import np.com.naxa.factsnepal.R;
import np.com.naxa.factsnepal.common.BaseActivity;
import np.com.naxa.factsnepal.feed.Fact;
import np.com.naxa.factsnepal.feed.FactsLocalSource;
import np.com.naxa.factsnepal.feed.list.FactsFeedAdapter;

public class FactsFeedActivity extends AppCompatActivity implements FactsFeedAdapter.OnFeedCardItemClickListener {

    private View rootLayout;
    private FactsFeedAdapter adapter;
    private RecyclerView recyclerViewFeed;
    private DisposableObserver<List<Fact>> factsDisposable;
    private LinearLayoutManager layoutManager;
    private Toolbar toolbar;


    String colors[] = new String[]{"#571821", "#5C3219", "#103B31"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facts_feed);
        bindUI();
        setupRecyclerView();
        FactsLocalSource.getINSTANCE().getAll()
                .observe(this, facts -> adapter.updateList(facts));

        setSupportActionBar(toolbar);
        setUpToolbar();


    }

    private void bindUI() {
        recyclerViewFeed = findViewById(R.id.rv_facts);
        rootLayout = findViewById(R.id.root_activity_facts_feed);
        toolbar = findViewById(R.id.app_bar);


    }

    private void setupRecyclerView() {
        adapter = new FactsFeedAdapter(new ArrayList<>(), this);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewFeed.setLayoutManager(layoutManager);
        recyclerViewFeed.setAdapter(adapter);
        recyclerViewFeed.setNestedScrollingEnabled(false);

        /**
         s * stackoverflow.com/questions/38247602/android-how-can-i-get-current-positon-on-recyclerview-that-user-scrolled-to-item
         */
        recyclerViewFeed.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int position = getCurrentItem();
                    onPageChanged(position);
                }
            }
        });


        SnapHelper snapHelper = new PagerSnapHelper();

        snapHelper.attachToRecyclerView(recyclerViewFeed);
    }

    /*
     *https://stackoverflow.com/questions/2614545/animate-change-of-view-background-color-on-android
     */
    private void onPageChanged(int position) {
        int lastPosition = position - 1;
        int colorTo = Color.parseColor(colors[lastPosition == -1 ? 0 : lastPosition]);
        int colorFrom = Color.parseColor(colors[position]);
        int duration = 1000;

        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);

        colorAnimation.setDuration(1000);
        colorAnimation.addUpdateListener(animator -> rootLayout.setBackgroundColor((int) animator.getAnimatedValue()));
        colorAnimation.start();
    }

    private int getCurrentItem() {
        return layoutManager.findFirstVisibleItemPosition();
    }

    @Override
    public void onCardTap(Fact fact, ImageView imageView) {

    }

    @Override
    public void onBookmarkButtonTap(Fact fact) {

    }

    @Override
    public void onShareButtonTap(Fact fact) {

    }

    private void setUpToolbar() {

        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new NavigationIconClickListener(
                this,
                findViewById(R.id.product_grid),
                new AccelerateDecelerateInterpolator(),
                getResources().getDrawable(R.drawable.ic_expand_more_24dp), // Menu open icon
                getResources().getDrawable(R.drawable.ic_expand_less_24dp))); // Menu close icon
    }
}
