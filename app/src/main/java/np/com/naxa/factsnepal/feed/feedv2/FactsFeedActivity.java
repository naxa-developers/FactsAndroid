package np.com.naxa.factsnepal.feed.feedv2;

import android.Manifest;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityOptionsCompat;
import androidx.lifecycle.MediatorLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.recyclerview.widget.SnapHelper;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.button.MaterialButton;
import com.littlemango.stacklayoutmanager.StackLayoutManager;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import io.reactivex.schedulers.Schedulers;
import np.com.naxa.factsnepal.R;
import np.com.naxa.factsnepal.bookmarkedfeed.BookmarkedFeedV2Activity;
import np.com.naxa.factsnepal.common.BaseActivity;
import np.com.naxa.factsnepal.common.BaseLogout;
import np.com.naxa.factsnepal.common.Constant;
import np.com.naxa.factsnepal.common.GlideApp;
import np.com.naxa.factsnepal.common.ItemOffsetDecoration;
import np.com.naxa.factsnepal.feed.Fact;
import np.com.naxa.factsnepal.feed.FactsLocalSource;
import np.com.naxa.factsnepal.feed.detail.FactDetailActivity;
import np.com.naxa.factsnepal.feed.dialog.BottomDialogFragment;
import np.com.naxa.factsnepal.feed.list.FactsFeedAdapter;
import np.com.naxa.factsnepal.feed.list.resource.FactsRepo;
import np.com.naxa.factsnepal.notification.CountDrawable;
import np.com.naxa.factsnepal.notification.NotificationActivity;
import np.com.naxa.factsnepal.notification.NotificationCount;
import np.com.naxa.factsnepal.preferences.PreferencesActivity;
import np.com.naxa.factsnepal.publicpoll.PublicPollActivity;
import np.com.naxa.factsnepal.surveys.SurveyCompanyListActivity;
import np.com.naxa.factsnepal.userprofile.LoginActivity;
import np.com.naxa.factsnepal.userprofile.UserProfileInfoActivity;
import np.com.naxa.factsnepal.utils.ActivityUtil;
import np.com.naxa.factsnepal.utils.SharedPreferenceUtils;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import static np.com.naxa.factsnepal.common.Constant.SharedPrefKey.KEY_IS_USER_LOGGED_IN;

public class FactsFeedActivity extends BaseActivity implements FactsFeedAdapter.OnFeedCardItemClickListener, View.OnClickListener, BottomDialogFragment.OnCategoriesSelectedListener,EasyPermissions.PermissionCallbacks {

    private static final String TAG = "FactsFeedActivity";
    private View rootLayout;
    private FactsFeedAdapter adapter;
    private RecyclerView recyclerViewFeed;
    private LinearLayoutManager layoutManager;
    private Toolbar toolbar;
    private MaterialButton btnHome, btnPublicPoll, btnSurvey, btnBookmarked, btnAccount, btnLogout;
    private NotificationCount notificationCount;

    String colors[] = new String[]{"#571821", "#5C3219", "#103B31"};
    private Menu menu;
    private MediatorLiveData<List<Fact>> factsLiveData = new MediatorLiveData<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facts_feed);
        notificationCount = new NotificationCount(this);
        bindUI();
        setupRecyclerView();

        factsLiveData.addSource(FactsRepo.getINSTANCE().getAllFacts(true), facts -> {
            if (getSelectedCategories().size() == 0) {
                factsLiveData.postValue(facts);
            }
        });
        factsLiveData.addSource(FactsRepo.getINSTANCE().getByCategoryIds(getSelectedCategories(), true),
                facts -> {
                    if (getSelectedCategories().size() > 0) {
                        factsLiveData.postValue(facts);
                    }
                });

        factsLiveData.observe(this, facts -> {
            adapter.updateList(facts);
        });
        setUpToolbar();
    }

    private List<Integer> getSelectedCategories() {
        Set<String> idSet = SharedPreferenceUtils.getInstance(getApplicationContext()).getSetValue(Constant.SharedPrefKey.SELECTED_CATEGORIES);
        List<Integer> idInteger = new ArrayList<>();
        for (String id : idSet) {
            idInteger.add(Integer.parseInt(id));
        }
        return idInteger;
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
        btnLogout = findViewById(R.id.backdrop_user_logout);

        btnAccount.setOnClickListener(this);
        btnBookmarked.setOnClickListener(this);
        btnHome.setOnClickListener(this);
        btnPublicPoll.setOnClickListener(this);
        btnSurvey.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
    }

    private void setupRecyclerView() {
        adapter = new FactsFeedAdapter(new ArrayList<>(), this);
        boolean useSnapLayout = false;

        if (useSnapLayout) {
            layoutManager = new LinearLayoutManager(this);
            recyclerViewFeed.setLayoutManager(layoutManager);
            SnapHelper snapHelper = new PagerSnapHelper();
            snapHelper.attachToRecyclerView(recyclerViewFeed);

            /*
             * stackoverflow.com/questions/38247602/android-how-can-i-get-current-positon-on-recyclerview-that-user-scrolled-to-item
             */


        } else {
            StackLayoutManager manager = new StackLayoutManager(StackLayoutManager.ScrollOrientation.TOP_TO_BOTTOM, 2);
            recyclerViewFeed.setLayoutManager(manager);

            ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.margin_large);
            recyclerViewFeed.addItemDecoration(itemDecoration);
        }

        RecyclerView.ItemAnimator animator = recyclerViewFeed.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
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

        toast(fact.isBookmarked() ? getString(R.string.msg_not_bookmarked) : getString(R.string.msg_bookmared));
    }

    @Override
    public void onShareButtonTap(Fact fact) {
        loadImageAndOpenShareDialog(fact);
    }

    @AfterPermissionGranted(Constant.Permission.RC_STORAGE)
    private void loadImageAndOpenShareDialog(Fact fact) {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            GlideApp.with(getApplicationContext())
                    .asBitmap()
                    .load(fact.getImagePath())
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@androidx.annotation.NonNull Bitmap bitmap, Transition<? super Bitmap> transition) {
                            Uri uri = getImageUri(bitmap);
                            Intent shareIntent = new Intent();
                            shareIntent.setAction(Intent.ACTION_SEND);
                            shareIntent.putExtra(Intent.EXTRA_TEXT, fact.getTitle());
                            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                            shareIntent.setType("image/*");
                            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            startActivity(Intent.createChooser(shareIntent, "Share fact..."));
                        }
                    });
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.storage_rationale),
                    Constant.Permission.RC_STORAGE, perms);
        }
    }




    public Uri getImageUri(Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), inImage, UUID.randomUUID().toString() + ".png", "drawing");
        return Uri.parse(path);
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
    public boolean onPrepareOptionsMenu(Menu menu) {
        try {
            MenuItem morecategories = menu.findItem(R.id.action_categories);
            morecategories.setVisible(true);

            long count = notificationCount.getNotificationCount();
            setNotificationCount(this, count + "", menu);
        } catch (NullPointerException | JSONException e) {
            e.printStackTrace();
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
                    ActivityUtil.openActivity(UserProfileInfoActivity.class, this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                onPrepareOptionsMenu(menu);
                break;

            case R.id.action_categories:

                BottomDialogFragment bottomSheetDialog = BottomDialogFragment.getInstance();
                bottomSheetDialog.show(getSupportFragmentManager(), "Chips Dialog");

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setNotificationCount(Context context, String count, Menu defaultMenu) {
        MenuItem menuItem = defaultMenu.findItem(R.id.menu_notification);
        LayerDrawable icon = (LayerDrawable) menuItem.getIcon();
        CountDrawable badge;

        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_group_count);
        if (reuse instanceof CountDrawable) {
            badge = (CountDrawable) reuse;
        } else {
            badge = new CountDrawable(context);
        }

        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_group_count, badge);
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
//                ActivityUtil.openActivity(BookmarkedFactsActivity.class, this);
                ActivityUtil.openActivity(BookmarkedFeedV2Activity.class, this);
                break;
            case R.id.backdrop_public_survey:
                ActivityUtil.openActivity(SurveyCompanyListActivity.class, this);
                break;

            case R.id.backdrop_user_logout:
                Log.d(TAG, "onClick: logout ");
                new BaseLogout(FactsFeedActivity.this) {
                    @Override
                    public void onLogoutSuccess() {
                        SharedPreferenceUtils.getInstance(FactsFeedActivity.this).setValue(KEY_IS_USER_LOGGED_IN, false);
                        SharedPreferenceUtils.getInstance(FactsFeedActivity.this).clearAll();
                        ActivityUtil.openActivity(LoginActivity.class, FactsFeedActivity.this);

                    }
                };
                break;
        }
    }

    @Override
    public void onCategoriesSelected(Set<String> categories) {

        FactsRepo.getINSTANCE().getByCategoryIds(getSelectedCategories(), true);
        Toast.makeText(getApplicationContext(), categories.size() + " categories added to your preference list", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }
}
