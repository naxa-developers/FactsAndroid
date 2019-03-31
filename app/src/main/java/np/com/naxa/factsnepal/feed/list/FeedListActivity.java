package np.com.naxa.factsnepal.feed.list;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.SwipeDismissBehavior;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import np.com.naxa.factsnepal.LoginActivity;
import np.com.naxa.factsnepal.R;
import np.com.naxa.factsnepal.common.BaseActivity;
import np.com.naxa.factsnepal.common.Constant;
import np.com.naxa.factsnepal.common.ListPaddingDecoration;
import np.com.naxa.factsnepal.common.OnCardItemClickListener;
import np.com.naxa.factsnepal.feed.EndlessScrollListener;
import np.com.naxa.factsnepal.feed.Fact;
import np.com.naxa.factsnepal.feed.detail.FactDetailActivity;
import np.com.naxa.factsnepal.feed.dialog.BottomDialogFragment;
import np.com.naxa.factsnepal.network.facts.FetchFacts;

public class FeedListActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnCardItemClickListener<Fact> {

    private RecyclerView recyclerViewFeed;
    private FactsFeedAdapter adapter;
    private CardView surveyCardView;
    private LinearLayoutManager layoutManager;
    private ProgressBar progressBar;

    private static final int MAX_ITEMS_PER_REQUEST = 10;
    private static final int NUMBER_OF_ITEMS = 20;
    private static final int SIMULATED_LOADING_TIME = (int) TimeUnit.SECONDS.toMillis(10);
    private int page;
    private List<Fact> facts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar();

        this.facts = Fact.getDemoItems(NUMBER_OF_ITEMS, 0);

        FetchFacts.fetchFactsFromServer(apiInterface);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getHeaderView(0).setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        });
        bindUI();
        setupRecyclerView();
        setupSurveyCard();
        mockSurveyCard();
        setupChips();

    }

    private void setupChips() {
        findViewById(R.id.btn_add_more_chips)
                .setOnClickListener(v -> {
                    BottomDialogFragment bottomSheetDialog = BottomDialogFragment.getInstance();
                    bottomSheetDialog.show(getSupportFragmentManager(), "Chips Dialog");
                });
    }

    private void bindUI() {
        recyclerViewFeed = findViewById(R.id.rv_feed);
        surveyCardView = findViewById(R.id.cv_survey_info);
        progressBar = findViewById(R.id.progress_bar_feed);

    }

    private void setupRecyclerView() {
        adapter = new FactsFeedAdapter(new ArrayList<>(this.facts.subList(page, MAX_ITEMS_PER_REQUEST)), this);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewFeed.setLayoutManager(layoutManager);
        recyclerViewFeed.setItemAnimator(new DefaultItemAnimator());

        recyclerViewFeed.addItemDecoration(new ListPaddingDecoration(this));
        recyclerViewFeed.addOnScrollListener(createInfiniteScrollListener());

        recyclerViewFeed.setAdapter(adapter);

    }

    private EndlessScrollListener createInfiniteScrollListener() {
        return new EndlessScrollListener(MAX_ITEMS_PER_REQUEST, layoutManager) {
            @Override
            public void onScrolledToEnd(final int firstVisibleItemPosition) {


            }
        };
    }


    @SuppressLint("StaticFieldLeak")
    private void simulateLoading() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Thread.sleep(SIMULATED_LOADING_TIME);
                } catch (InterruptedException ignored) {
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void param) {
                int start = ++page * MAX_ITEMS_PER_REQUEST;
                final boolean allItemsLoaded = start >= facts.size();
                if (allItemsLoaded) {
                    showToast("End of list");
                } else {
                    int end = start + MAX_ITEMS_PER_REQUEST;
                    final ArrayList<Fact> itemsLocal = getItemsToBeLoaded(start, end);
                    adapter.addAll(itemsLocal);
                }

                progressBar.setVisibility(View.GONE);

            }
        }.execute();
    }

    private void loadNextPage() {
        adapter.addAll(Fact.getDemoItems(10, adapter.getItemCount()));
    }


    private ArrayList<Fact> getItemsToBeLoaded(int start, int end) {
        List<Fact> newItems = facts.subList(start, end);
        final ArrayList<Fact> oldItems = ((FactsFeedAdapter) recyclerViewFeed.getAdapter()).getItems();
        final ArrayList<Fact> itemsLocal = new ArrayList<>();
        itemsLocal.addAll(oldItems);
        itemsLocal.addAll(newItems);
        return itemsLocal;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onCardItemClicked(Fact fact, int position) {

    }

    @Override
    public void onCardItemLongClicked(Fact fact) {

    }

    @Override
    public void onCardItemClicked(Fact fact, View view) {
        Intent intent = new Intent(this, FactDetailActivity.class);
        intent.putExtra(Constant.EXTRA_IMAGE, fact);

        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, (ImageView) view, getString(R.string.transtion_fact_list_details));
        startActivity(intent, options.toBundle());


    }

    private void mockSurveyCard() {
        surveyCardView.setVisibility(View.GONE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                surveyCardView.setVisibility(View.VISIBLE);
            }
        }, TimeUnit.SECONDS.toMillis(30));
    }

    private void setupSurveyCard() {
        SwipeDismissBehavior swipeDismissBehavior = new SwipeDismissBehavior();
        swipeDismissBehavior.setSwipeDirection(SwipeDismissBehavior.SWIPE_DIRECTION_ANY);

        CoordinatorLayout.LayoutParams layoutParams =
                (CoordinatorLayout.LayoutParams) surveyCardView.getLayoutParams();

        layoutParams.setBehavior(swipeDismissBehavior);

    }
}
