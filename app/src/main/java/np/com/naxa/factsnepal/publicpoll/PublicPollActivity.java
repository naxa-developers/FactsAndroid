package np.com.naxa.factsnepal.publicpoll;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.button.MaterialButton;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import np.com.naxa.factsnepal.R;
import np.com.naxa.factsnepal.common.BaseActivity;
import np.com.naxa.factsnepal.utils.ActivityUtil;

public class PublicPollActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_poll);
        setupToolbar();

        setupButtonForDemo();
    }

    @Deprecated
    private void setupButtonForDemo() {
        LinearLayout view = findViewById(R.id.layout_button_wrapper);
        View button = null;

        View.OnClickListener onClick = v -> {
            ActivityUtil.openActivity(PublicPollResultActivity.class, this, null, false);
        };

        String[] array = getApplicationContext().getResources().getStringArray(R.array.demo_options_array);
        for (String item : array) {
            button = LayoutInflater.from(this).inflate(R.layout.layout_button_survey, view, false);
            ((MaterialButton) button).setText(item);
            button.setOnClickListener(onClick);
            view.addView(button);

        }


    }
}
