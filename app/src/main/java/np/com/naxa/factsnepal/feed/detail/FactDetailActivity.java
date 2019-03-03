package np.com.naxa.factsnepal.feed.detail;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.ViewUtils;
import android.transition.ChangeBounds;
import android.transition.ChangeImageTransform;
import android.transition.Fade;
import android.transition.TransitionSet;
import android.view.Menu;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import np.com.naxa.factsnepal.R;
import np.com.naxa.factsnepal.common.BaseActivity;
import np.com.naxa.factsnepal.common.Constant;
import np.com.naxa.factsnepal.feed.Fact;
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

        Fact fact = (Fact) getIntent().getSerializableExtra(Constant.EXTRA_IMAGE);
        tvTitle.setText(fact.getTitle());
        ImageUtils.loadRemoteImage(this, fact.getImagePath())
                .apply(RequestOptions.placeholderOf(R.color.colorPrimaryDark).dontTransform())
                .centerCrop()
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

//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            getWindow().setEnterTransition(new Fade());
//            getWindow().setSharedElementExitTransition(new TransitionSet()
//                    .addTransition(new ChangeBounds())
//                    .addTransition(new Fade())
//
//            );
//            getWindow().setSharedElementEnterTransition(
//                    new TransitionSet()
//                            .addTransition(new ChangeBounds())
//                            .addTransition(new Fade())
//
//            );
//        }

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
