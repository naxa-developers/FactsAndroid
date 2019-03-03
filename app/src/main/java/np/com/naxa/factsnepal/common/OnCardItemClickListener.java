package np.com.naxa.factsnepal.common;

import android.view.View;

public interface OnCardItemClickListener<T> {


    void onCardItemClicked(T t, int position);

    void onCardItemLongClicked(T t);

    void onCardItemClicked(T t, View view);


}
