package np.com.naxa.factsnepal.feed.list;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.chip.Chip;
import android.support.design.chip.ChipGroup;
import android.support.design.widget.NavigationView;
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
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

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
import np.com.naxa.factsnepal.common.BaseLoginActivity;
import np.com.naxa.factsnepal.common.BaseLogout;
import np.com.naxa.factsnepal.common.Constant;
import np.com.naxa.factsnepal.common.ListPaddingDecoration;
import np.com.naxa.factsnepal.feed.EndlessScrollListener;
import np.com.naxa.factsnepal.feed.Fact;
import np.com.naxa.factsnepal.feed.FactsLocalSource;
import np.com.naxa.factsnepal.feed.bookmarkedfacts.BookmarkedFactsActivity;
import np.com.naxa.factsnepal.feed.detail.FactDetailActivity;
import np.com.naxa.factsnepal.feed.dialog.BottomDialogFragment;
import np.com.naxa.factsnepal.network.facts.Category;
import np.com.naxa.factsnepal.network.facts.FactsResponse;
import np.com.naxa.factsnepal.notification.CountDrawable;
import np.com.naxa.factsnepal.notification.NotificationActivity;
import np.com.naxa.factsnepal.notification.NotificationCount;
import np.com.naxa.factsnepal.preferences.PreferencesActivity;
import np.com.naxa.factsnepal.publicpoll.PublicPollActivity;
import np.com.naxa.factsnepal.surveys.SurveyCompanyListActivity;
import np.com.naxa.factsnepal.userprofile.LoginActivity;
import np.com.naxa.factsnepal.utils.ActivityUtil;
import np.com.naxa.factsnepal.utils.ImageUtils;
import np.com.naxa.factsnepal.utils.SharedPreferenceUtils;

import static np.com.naxa.factsnepal.feed.Fact.hasCategories;

public class FeedListActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, FactsFeedAdapter.OnFeedCardItemClickListener, View.OnClickListener {

    private static final String TAG = "FeedListActivity";
    private static final String KEY_FEED_LIST_DETAILS_JSON = "feed_list_details_json";

    private RecyclerView recyclerViewFeed;
    private FactsFeedAdapter adapter;
    private CardView surveyCardView;
    private LinearLayoutManager layoutManager;
    private ProgressBar progressBar;
    private ChipGroup chipGroup;

    private static final int MAX_ITEMS_PER_REQUEST = 10;
    private static final int NUMBER_OF_ITEMS = 5;
    private static final int SIMULATED_LOADING_TIME = (int) TimeUnit.SECONDS.toMillis(10);
    private int page;

    private CardView cardSurvey;

    NotificationCount notificationCount;

    SharedPreferenceUtils sharedPreferenceUtils;
    Gson gson;
    String jsonInStringFeed = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar();

        notificationCount = new NotificationCount(FeedListActivity.this);
        sharedPreferenceUtils = new SharedPreferenceUtils(this);
        gson = new Gson();


        fetchFactsFromServer(null);
        FactsLocalSource.getINSTANCE()
                .saveAsync(Fact.getDemoItems(NUMBER_OF_ITEMS, 0));

        FactsLocalSource.getINSTANCE().getAll()
                .observe(this, facts -> {
                    adapter.addAll(facts);
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) drawer.findViewById(R.id.nav_view);


        View headerLayout = navigationView.getHeaderView(0);
        ImageView profileIageView = (ImageView) headerLayout.findViewById(R.id.nav_user_profile_image_view);
        TextView tvUserName = (TextView) headerLayout.findViewById(R.id.nav_user_username);
        TextView tvUserEmail = (TextView) headerLayout.findViewById(R.id.nav_user_email);

        BaseLoginActivity.UserSocialLoginDetails userSocialLoginDetails = gson.fromJson((sharedPreferenceUtils.getStringValue(BaseLoginActivity.KEY_USER_SOCIAL_LOGGED_IN_DETAILS, null)), BaseLoginActivity.UserSocialLoginDetails.class);
        ImageUtils.loadRemoteImage(this, userSocialLoginDetails.getUser_image_url())
                .fitCenter()
                .circleCrop()
                .into(profileIageView);
        tvUserName.setText(userSocialLoginDetails.getUser_name());
        tvUserEmail.setText(userSocialLoginDetails.getUser_email());

        if (sharedPreferenceUtils.getIntValue(BaseLoginActivity.KEY_LOGGED_IN_TYPE, -1) == 1 || sharedPreferenceUtils.getIntValue(BaseLoginActivity.KEY_LOGGED_IN_TYPE, -1) == 2) {
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_user_logout).setVisible(true);
        }

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getHeaderView(0).setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        });


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
        adapter = new FactsFeedAdapter(new ArrayList<>(), this);

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


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    Menu menu;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        SharedPreferenceUtils sharedPreferenceUtils = new SharedPreferenceUtils(this);
//        sharedPreferenceUtils.removeKey(NotificationCount.KEY_NOTIFICATION_LIST);
        this.menu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_notification:
                ActivityUtil.openActivity(NotificationActivity.class, this);
                break;

            case R.id.action_profile:
                try {
                    notificationCount.saveNotification(notificationCount.getJsonArray());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                onPrepareOptionsMenu(menu);
                break;

            case android.R.id.home:

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main, menu);
        try {
            long count = notificationCount.getNotificationCount();
            setCount(FeedListActivity.this, count + "", menu);
        } catch (JSONException | NullPointerException e) {
            e.printStackTrace();
        }
        return super.onPrepareOptionsMenu(menu);
    }

    public void setCount(Context context, String count, Menu defaultMenu) {
        MenuItem menuItem = defaultMenu.findItem(R.id.menu_notification);
        LayerDrawable icon = (LayerDrawable) menuItem.getIcon();

        CountDrawable badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_group_count);
        if (reuse != null && reuse instanceof CountDrawable) {
            badge = (CountDrawable) reuse;
        } else {
            badge = new CountDrawable(context);
        }

        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_group_count, badge);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_home:
                break;

            case R.id.nav_public_poll:
                ActivityUtil.openActivity(PublicPollActivity.class, this);
                break;

            case R.id.nav_survey:
                ActivityUtil.openActivity(SurveyCompanyListActivity
                        .class, this);
                break;

            case R.id.nav_bookmarked:
                ActivityUtil.openActivity(BookmarkedFactsActivity.class, this);
                break;

            case R.id.nav_user_logout:
                BaseLogout baseLogout = new BaseLogout(FeedListActivity.this) {
                    @Override
                    public void onLogoutSuccess() {
                        sharedPreferenceUtils.setValue(LoginActivity.KEY_IS_USER_LOGGED_IN, false);
                        sharedPreferenceUtils.clearAll();
                        ActivityUtil.openActivity(LoginActivity.class, FeedListActivity.this);

                    }
                };
                break;
            case R.id.nav_settings:
                ActivityUtil.openActivity(PreferencesActivity.class, this);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
        surveyCardView.setOnClickListener(v -> {

        });

        findViewById(R.id.iv_close_survey_card)
                .setOnClickListener(v -> {
                    surveyCardView.setVisibility(View.INVISIBLE);
                });

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

                            jsonInStringFeed = gson.toJson(factsResponse);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.d(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        sharedPreferenceUtils.setValue(KEY_FEED_LIST_DETAILS_JSON , jsonInStringFeed);
                        Log.d(TAG, "onComplete: ");


                    }
                });
    }

    private void fetchFactsFromSharedPrefs(){

        List<FactsResponse> factsResponses = gson.fromJson(sharedPreferenceUtils.getStringValue(KEY_FEED_LIST_DETAILS_JSON, null),new TypeToken<List<FactsResponse>>(){}.getType());

        Observable.just(factsResponses)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<List<FactsResponse>>() {
                    @Override
                    public void onNext(List<FactsResponse> factsResponses) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

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
