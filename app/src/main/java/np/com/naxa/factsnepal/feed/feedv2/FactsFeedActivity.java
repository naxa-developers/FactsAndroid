package np.com.naxa.factsnepal.feed.feedv2;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;

import com.littlemango.stacklayoutmanager.StackLayoutManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import np.com.naxa.factsnepal.R;
import np.com.naxa.factsnepal.common.BaseActivity;
import np.com.naxa.factsnepal.common.Constant;
import np.com.naxa.factsnepal.common.ItemOffsetDecoration;
import np.com.naxa.factsnepal.feed.Fact;
import np.com.naxa.factsnepal.feed.FactsLocalSource;
import np.com.naxa.factsnepal.feed.bookmarkedfacts.BookmarkedFactsActivity;
import np.com.naxa.factsnepal.feed.detail.FactDetailActivity;
import np.com.naxa.factsnepal.feed.list.FactsFeedAdapter;
import np.com.naxa.factsnepal.preferences.PreferencesActivity;
import np.com.naxa.factsnepal.publicpoll.PublicPollActivity;
import np.com.naxa.factsnepal.surveys.SurveyCompanyListActivity;
import np.com.naxa.factsnepal.utils.ActivityUtil;

public class FactsFeedActivity extends BaseActivity implements FactsFeedAdapter.OnFeedCardItemClickListener, View.OnClickListener {

    private View rootLayout;
    private FactsFeedAdapter adapter;
    private RecyclerView recyclerViewFeed;
    private DisposableObserver<List<Fact>> factsDisposable;
    private LinearLayoutManager layoutManager;
    private Toolbar toolbar;
    private MaterialButton btnHome, btnPublicPoll, btnSurvey, btnBookmarked, btnAccount;


    String colors[] = new String[]{"#571821", "#5C3219", "#103B31"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facts_feed);
        bindUI();
        setupRecyclerView();
        FactsLocalSource.getINSTANCE().getAll()
                .observe(this, facts -> {
                    adapter.updateList(facts);
                });

        setUpToolbar();

    }

    private void bindUI() {
        recyclerViewFeed = findViewById(R.id.rv_facts);
        rootLayout = findViewById(R.id.root_activity_facts_feed);
        toolbar = findViewById(R.id.app_bar);

        btnAccount = findViewById(R.id.backdrop_account);
        btnBookmarked = findViewById(R.id.backdrop_bookmark);
        btnHome = findViewById(R.id.backdrop_home);
        btnPublicPoll = findViewById(R.id.backdrop_public_poll);
        btnSurvey = findViewById(R.id.backdrop_public_survey);

        btnAccount.setOnClickListener(this);
        btnBookmarked.setOnClickListener(this);
        btnHome.setOnClickListener(this);
        btnPublicPoll.setOnClickListener(this);
        btnSurvey.setOnClickListener(this);
    }

    private void setupRecyclerView() {
        adapter = new FactsFeedAdapter(new ArrayList<>(), this);
        boolean useSnapLayout = false;

        if (useSnapLayout) {
            layoutManager = new LinearLayoutManager(this);
            recyclerViewFeed.setLayoutManager(layoutManager);
            /*
             * stackoverflow.com/questions/38247602/android-how-can-i-get-current-positon-on-recyclerview-that-user-scrolled-to-item
             */
            recyclerViewFeed.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@android.support.annotation.NonNull @NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        int position = getCurrentItem();
                        getColorPallete(position);
                    }
                }
            });

            SnapHelper snapHelper = new PagerSnapHelper();
            snapHelper.attachToRecyclerView(recyclerViewFeed);
        } else {
            StackLayoutManager manager = new StackLayoutManager(StackLayoutManager.ScrollOrientation.TOP_TO_BOTTOM, 2);
            recyclerViewFeed.setLayoutManager(manager);

            ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.margin_large);
            recyclerViewFeed.addItemDecoration(itemDecoration);
        }


        recyclerViewFeed.setAdapter(adapter);


    }

    private void getColorPallete(int position) {
        Fact fact = adapter.getItems().get(position);
        if (fact != null) {
            String url = fact.getImagePath();

        }
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
        toast(getString(R.string.msg_bookmared));
    }

    @Override
    public void onShareButtonTap(Fact fact) {
        ActivityUtil.openShareIntent(this, fact.getTitle());
    }


    private void setUpToolbar() {
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new NavigationIconClickListener(
                this,
                findViewById(R.id.rv_facts),
                new AccelerateDecelerateInterpolator(),
                getResources().getDrawable(R.drawable.ic_expand_more_black_24dp), // Menu open icon
                getResources().getDrawable(R.drawable.ic_expand_less_black_24dp))); // Menu close icon
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backdrop_home:
                break;
            case R.id.backdrop_account:
                ActivityUtil.openActivity(PreferencesActivity.class, this);

                break;
            case R.id.backdrop_public_poll:
                ActivityUtil.openActivity(PublicPollActivity.class, this);
                break;
            case R.id.backdrop_bookmark:
                ActivityUtil.openActivity(BookmarkedFactsActivity.class, this);

                break;
            case R.id.backdrop_public_survey:
                ActivityUtil.openActivity(SurveyCompanyListActivity
                        .class, this);

                break;
        }
    }
}
