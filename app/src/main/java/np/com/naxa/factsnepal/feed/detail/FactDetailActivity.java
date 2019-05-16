package np.com.naxa.factsnepal.feed.detail;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import io.reactivex.schedulers.Schedulers;
import np.com.naxa.factsnepal.R;
import np.com.naxa.factsnepal.common.BaseActivity;
import np.com.naxa.factsnepal.common.Constant;
import np.com.naxa.factsnepal.feed.Fact;
import np.com.naxa.factsnepal.feed.FactsLocalSource;
import np.com.naxa.factsnepal.utils.ImageUtils;

public class FactDetailActivity extends BaseActivity implements View.OnClickListener {

    ImageView imageView;
    TextView tvTitle;
    private TextView tvDesc, tvLikeCount;
    private CheckBox tvBookmark;
    private Fact fact;
    private final String FACT = "fact";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fact_detail);
        supportPostponeEnterTransition();

        setupToolbar();
        bindUI();

        onNewIntent(getIntent());
        loadFact(savedInstanceState, getIntent());
        setFactInUI(fact);
    }

    private void setFactInUI(Fact fact) {
        if (fact != null) {
            tvTitle.setText(fact.getTitle());
            setupImageLoad(fact);
            setupFactUI();
        }
    }

    private void setupFactUI() {
        if (fact == null) {
            finish();
            return;
        }

        tvTitle.setText(fact.getTitle());
        tvDesc.setText(fact.getDescription());
        tvLikeCount.setText(fact.getLikeCount());
        tvBookmark.setChecked(fact.isBookmarked());
        setupImageLoad(fact);
    }


    private void loadFact(Bundle savedInstanceState, Intent intent) {

        Uri data = intent.getData();
        if (data != null && data.getQueryParameterNames().contains("id")) {
            String factsId = data.getQueryParameter("id");
            FactsLocalSource.getINSTANCE().getById(factsId)
                    .observe(this, this::setFactInUI);
        }

        if (savedInstanceState != null) {
            fact = (Fact) savedInstanceState.getSerializable(FACT);
        } else {
            fact = (Fact) intent.getSerializableExtra(Constant.EXTRA_IMAGE);

        }
    }

    private void setupImageLoad(Fact fact) {

        ImageUtils.loadRemoteImage(this, fact.getImagePath())
                .apply(RequestOptions.placeholderOf(R.color.colorPrimaryDark).dontTransform())

                .fitCenter()
                .dontAnimate()
                .addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        supportStartPostponedEnterTransition();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        supportStartPostponedEnterTransition();
                        return false;
                    }
                })
                .into(imageView);
    }

    private void bindUI() {
        imageView = findViewById(R.id.iv_fact_detail);
        tvTitle = findViewById(R.id.tv_fact_details_title);
        tvDesc = findViewById(R.id.tv_fact_detail_desc);
        tvBookmark = findViewById(R.id.tv_saved);
        tvLikeCount = findViewById(R.id.three);
        tvBookmark.setOnClickListener(this);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        supportFinishAfterTransition();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_saved:
                FactsLocalSource.getINSTANCE().toggleBookMark(fact)
                        .subscribeOn(Schedulers.io())
                        .subscribe();
                break;
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(FACT, fact);
    }
}
