package np.com.naxa.factsnepal.feed.list;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import android.text.TextUtils;

import java.util.List;

import np.com.naxa.factsnepal.feed.Fact;

public class FactsDiffCallback extends DiffUtil.Callback {
    private List<Fact> oldFacts;
    private List<Fact> newFacts;

    public FactsDiffCallback(List<Fact> oldFacts, List<Fact> newFacts) {
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
