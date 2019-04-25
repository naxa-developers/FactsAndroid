package np.com.naxa.factsnepal.common;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.view.animation.LayoutAnimationController;

import np.com.naxa.factsnepal.R;


public class AnimationUtils {



    public static void runLayoutAnimation(final RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                android.view.animation.AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();

    }
}
