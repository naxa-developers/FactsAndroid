package np.com.naxa.factsnepal.feed.detail;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.ViewUtils;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.Fade;
import android.transition.TransitionSet;
import android.util.Log;
import android.view.Menu;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import np.com.naxa.factsnepal.R;
import np.com.naxa.factsnepal.common.BaseActivity;
import np.com.naxa.factsnepal.common.Constant;
import np.com.naxa.factsnepal.feed.Fact;
import np.com.naxa.factsnepal.feed.FactsLocalSource;
import np.com.naxa.factsnepal.utils.ImageUtils;

public class FactDetailActivity extends BaseActivity {

    ImageView imageView;
    TextView tvTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fact_detail);
        setupToolbar();
        bindUI();
        onNewIntent(getIntent());
    }

    protected void onNewIntent(Intent intent) {
        Uri data = intent.getData();
        if (data != null && data.getQueryParameterNames().contains("id")) {
            String factsId = data.getQueryParameter("id");
            FactsLocalSource.getINSTANCE().getById(factsId)
                    .observe(this, this::setFactInUI);
        }

        Fact fact = (Fact) intent.getSerializableExtra(Constant.EXTRA_IMAGE);
        setFactInUI(fact);
    }

    private void setFactInUI(Fact fact) {
        if (fact != null) {
            tvTitle.setText(fact.getTitle());
            setupImageLoad(fact);
        }
    }

    private void setupImageLoad(Fact fact) {
        ImageUtils.loadRemoteImage(this, fact.getImagePath())
                .apply(RequestOptions.placeholderOf(R.color.colorPrimaryDark).dontTransform())
                .fitCenter()
                .addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
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
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        supportFinishAfterTransition();
    }
}
