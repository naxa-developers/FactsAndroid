package np.com.naxa.factsnepal.feed.detail;

import android.os.Bundle;
import android.support.v7.widget.ViewUtils;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import np.com.naxa.factsnepal.R;
import np.com.naxa.factsnepal.common.BaseActivity;
import np.com.naxa.factsnepal.common.Constant;
import np.com.naxa.factsnepal.feed.Fact;
import np.com.naxa.factsnepal.utils.ImageUtils;

public class FactDetailActivity extends BaseActivity {

    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fact_detail);
        bindUI();

        Fact fact = (Fact) getIntent().getSerializableExtra(Constant.EXTRA_IMAGE);
        ImageUtils.loadRemoteImage(this,fact.getImagePath())
                .centerCrop()
                .into(imageView);


    }

    private void bindUI() {
        imageView = findViewById(R.id.iv_fact_detail);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        supportFinishAfterTransition();
    }
}
