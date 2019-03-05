package np.com.naxa.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;

import np.com.naxa.factsnepal.R;

public class RatingView extends LinearLayout implements Widget {

    private RatingBar ratingBar;

    public RatingView(Context context) {
        this(context, null);
    }

    public RatingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupView();
    }

    private void setupView() {
        inflate(getContext(), R.layout.layout_rating_view, this);
        ratingBar = findViewById(R.id.rating_bar);
        ratingBar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    @Override
    public String getAnswer() {
        return null;
    }

    @Override
    public void clearAnswer() {

    }
}
