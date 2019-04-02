package np.com.naxa.factsnepal.feed.list;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.chip.Chip;
import android.support.design.chip.ChipGroup;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import np.com.naxa.factsnepal.R;
import np.com.naxa.factsnepal.common.BaseActivity;
import np.com.naxa.factsnepal.common.Constant;
import np.com.naxa.factsnepal.common.ListPaddingDecoration;
import np.com.naxa.factsnepal.common.OnCardItemClickListener;
import np.com.naxa.factsnepal.feed.EndlessScrollListener;
import np.com.naxa.factsnepal.feed.Fact;
import np.com.naxa.factsnepal.feed.detail.FactDetailActivity;
import np.com.naxa.factsnepal.feed.dialog.BottomDialogFragment;
import np.com.naxa.factsnepal.network.facts.Category;
import np.com.naxa.factsnepal.network.facts.FactsResponse;
import np.com.naxa.factsnepal.notification.NotificationActivity;
import np.com.naxa.factsnepal.publicpoll.PublicPollActivity;
import np.com.naxa.factsnepal.network.facts.FetchFacts;
import np.com.naxa.factsnepal.publicpoll.PublicPollActivity;
import np.com.naxa.factsnepal.surveys.SurveyStartActivity;

import np.com.naxa.factsnepal.userprofile.LoginActivity;
import np.com.naxa.factsnepal.utils.ActivityUtil;

import static np.com.naxa.factsnepal.feed.Fact.hasCategories;

public class FeedListActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnCardItemClickListener<Fact>, View.OnClickListener {

    private static final String TAG = "FeedListActivity";

    private RecyclerView recyclerViewFeed;
    private FactsFeedAdapter adapter;
    private CardView surveyCardView;
    private LinearLayoutManager layoutManager;
    private ProgressBar progressBar;
    private ChipGroup chipGroup;

    private static final int MAX_ITEMS_PER_REQUEST = 10;
    private static final int NUMBER_OF_ITEMS = 20;
    private static final int SIMULATED_LOADING_TIME = (int) TimeUnit.SECONDS.toMillis(10);
    private int page;
    private List<Fact> facts;
    private CardView cardSurvey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar();

        this.facts = Fact.getDemoItems(NUMBER_OF_ITEMS, 0);
        fetchFactsFromServer(null);


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
//        setupChips(null);
        initChips();
        setupNavigationDrawer();

        cardSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtil.openActivity(PublicPollActivity.class, FeedListActivity.this, null, false);
            }
        });

    }


    private void setupNavigationDrawer() {
        findViewById(R.id.footer_item_facebook).setOnClickListener(this);
        findViewById(R.id.footer_item_instagram).setOnClickListener(this);
        findViewById(R.id.footer_item_twitter).setOnClickListener(this);

    }

    public void listenChipsStatus() {

        BottomDialogFragment.getSelectedCategories(new BottomDialogFragment.CategorySelectedListener() {
            @Override
            public void onClick(ArrayList<Integer> categoriesList) {
                Log.d(TAG, "onClick: chip selected" + categoriesList.size());
                fetchFactsFromServer(categoriesList);
            }
        });
    }

    private void initChips() {
        chipGroup = findViewById(R.id.chipgroup);
        findViewById(R.id.btn_add_more_chips)
                .setOnClickListener(v -> {
                    BottomDialogFragment bottomSheetDialog = BottomDialogFragment.getInstance();
                    bottomSheetDialog.show(getSupportFragmentManager(), "Chips Dialog");
                });
    }

    private void setupChips(List<Category> categoryList) {
        if (categoryList == null) {
            return;
        }
        Observable.just(categoryList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMapIterable(new Function<List<Category>, Iterable<Category>>() {
                    @Override
                    public Iterable<Category> apply(List<Category> categoryList) throws Exception {
                        Fact.setCategories(categoryList);

                        return categoryList;
                    }
                }).map(new Function<Category, Category>() {
            @Override
            public Category apply(Category category) throws Exception {
                return category;
            }
        })
                .subscribe(new DisposableObserver<Category>() {
                    @Override
                    public void onNext(Category category) {

                        Chip chip = new Chip(chipGroup.getContext());
                        chip.setChipText(category.getTitle());
                        chip.setId(category.getId());
                        Log.d(TAG, "onNext: id" + category.getId());
//                         chip.setCloseIconEnabled(true);
//            chip.setCloseIconResource(R.drawable.your_icon);
                        chip.setChipBackgroundColorResource(R.color.colorAccent);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            chip.setElevation(15);
                        }

                        chipGroup.addView(chip);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
//                        chipGroup.invalidate();

                        Log.d(TAG, "onComplete: chips added successfully");
                    }
                });


    }

    private void bindUI() {
        recyclerViewFeed = findViewById(R.id.rv_feed);
        surveyCardView = findViewById(R.id.cv_survey_info);
        progressBar = findViewById(R.id.progress_bar_feed);
        cardSurvey = findViewById(R.id.cv_survey_info);

    }

    private void setupRecyclerView() {
        adapter = new FactsFeedAdapter(new ArrayList<>(this.facts.subList(page, MAX_ITEMS_PER_REQUEST)), this);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewFeed.setLayoutManager(layoutManager);
        recyclerViewFeed.setItemAnimator(new DefaultItemAnimator());

        recyclerViewFeed.addItemDecoration(new ListPaddingDecoration(this));

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
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_notification:
                ActivityUtil.openActivity(NotificationActivity.class, this);
                break;
            case android.R.id.home:

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
        } else if (id == R.id.nav_public_poll) {
            ActivityUtil.openActivity(PublicPollActivity.class, this);
        } else if (id == R.id.nav_survey) {
            ActivityUtil.openActivity(SurveyStartActivity
                    .class, this);
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
        }, TimeUnit.SECONDS.toMillis(2));
    }

    private void setupSurveyCard() {
        SwipeDismissBehavior swipeDismissBehavior = new SwipeDismissBehavior();
        swipeDismissBehavior.setSwipeDirection(SwipeDismissBehavior.SWIPE_DIRECTION_ANY);

        CoordinatorLayout.LayoutParams layoutParams =
                (CoordinatorLayout.LayoutParams) surveyCardView.getLayoutParams();
        swipeDismissBehavior.setListener(new SwipeDismissBehavior.OnDismissListener() {
            @Override
            public void onDismiss(View view) {
                surveyCardView.setVisibility(View.GONE);
            }

            @Override
            public void onDragStateChanged(int i) {

            }
        });
        layoutParams.setBehavior(swipeDismissBehavior);

    }

    private void fetchFactsFromServer(ArrayList<Integer> categories) {

        if (categories != null) {
            Log.d(TAG, "fetchFactsFromServer: categories id lis" + categories.toString());

        }

        apiInterface.getFactsDetailsResponse(categories)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<List<FactsResponse>>() {
                    @Override
                    public void onNext(List<FactsResponse> factsResponse) {
                        Log.d(TAG, "onNext: " + factsResponse.get(0).toString());
                        if (factsResponse.get(0).getCategory() != null) {

                            if (!hasCategories) {
                                setupChips(factsResponse.get(0).getCategory());
                            }
                            hasCategories = true;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.d(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");


                    }
                });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.footer_item_facebook:
                break;
            case R.id.footer_item_twitter:
                break;
            case R.id.footer_item_instagram:
                break;
        }
    }
}
