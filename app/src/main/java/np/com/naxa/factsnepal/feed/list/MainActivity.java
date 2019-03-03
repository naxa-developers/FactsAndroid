package np.com.naxa.factsnepal.feed.list;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.SwipeDismissBehavior;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import np.com.naxa.factsnepal.R;
import np.com.naxa.factsnepal.common.Constant;
import np.com.naxa.factsnepal.common.OnCardItemClickListener;
import np.com.naxa.factsnepal.common.PaginationScrollListener;
import np.com.naxa.factsnepal.feed.Fact;
import np.com.naxa.factsnepal.feed.detail.FactDetailActivity;
import np.com.naxa.factsnepal.utils.DialogUtils;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnCardItemClickListener<Fact> {

    private RecyclerView recyclerViewFeed;
    private FactsFeedAdapter adapter;
    private CardView surveyCardView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        bindUI();
        setupRecyclerView();
        setupSurveyCard();
        mockSurveyCard();
        setupChips();
    }

    private void setupChips() {
        findViewById(R.id.btn_add_more_chips)
                .setOnClickListener(v -> {
                    DialogUtils.showChipsDialog(this).show();
                });
    }

    private void bindUI() {
        recyclerViewFeed = findViewById(R.id.rv_feed);
        surveyCardView = findViewById(R.id.cv_survey_info);
    }

    private void setupRecyclerView() {
        adapter = new FactsFeedAdapter(new ArrayList<>(0), this);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewFeed.setLayoutManager(manager);
        recyclerViewFeed.setItemAnimator(new DefaultItemAnimator());

        recyclerViewFeed.setAdapter(adapter);

        loadNextPage();

        recyclerViewFeed.addOnScrollListener(new PaginationScrollListener(manager) {
            @Override
            protected void loadMoreItems() {
                loadNextPage();
            }

            @Override
            public boolean isLastPage() {
                return false;
            }

            @Override
            public boolean isLoading() {
                return false;
            }
        });

    }

    private void loadNextPage() {
        adapter.addAll(Fact.getDemoItems(10, adapter.getItemCount()));
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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
                makeSceneTransitionAnimation(this, (ImageView)view, getString(R.string.transtion_fact_list_details));
        startActivity(intent, options.toBundle());


    }

    private void mockSurveyCard() {
        surveyCardView.setVisibility(View.GONE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                surveyCardView.setVisibility(View.VISIBLE);
            }
        }, TimeUnit.SECONDS.toMillis(3));
    }

    private void setupSurveyCard() {
        SwipeDismissBehavior swipeDismissBehavior = new SwipeDismissBehavior();
        swipeDismissBehavior.setSwipeDirection(SwipeDismissBehavior.SWIPE_DIRECTION_ANY);

        CoordinatorLayout.LayoutParams layoutParams =
                (CoordinatorLayout.LayoutParams) surveyCardView.getLayoutParams();

        layoutParams.setBehavior(swipeDismissBehavior);

    }
}
