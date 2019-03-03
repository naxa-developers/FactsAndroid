package np.com.naxa.factsnepal.feed.list;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.text.TextUtils;

import java.util.ArrayList;

import np.com.naxa.factsnepal.feed.Fact;

public class FactsDiffCallback extends DiffUtil.Callback {
    private ArrayList<Fact> oldFacts;
    private ArrayList<Fact> newFacts;

    public FactsDiffCallback(ArrayList<Fact> oldFacts, ArrayList<Fact> newFacts) {
        this.oldFacts = oldFacts;
        this.newFacts = newFacts;
    }

    @Override
    public int getOldListSize() {
        return oldFacts.size();
    }

    @Override
    public int getNewListSize() {
        return newFacts.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return TextUtils.equals(oldFacts.get(oldItemPosition).getTitle(), newFacts.get(newItemPosition).getTitle());

    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldFacts.get(oldItemPosition).equals(newFacts.get(newItemPosition));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
