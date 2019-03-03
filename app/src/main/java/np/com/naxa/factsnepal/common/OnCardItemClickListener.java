package np.com.naxa.factsnepal.common;

public interface OnCardItemClickListener<T> {


    void onCardItemClicked(T t, int position);

    void onCardItemLongClicked(T t);



}
