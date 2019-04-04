package np.com.naxa.factsnepal.feed.list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import np.com.naxa.factsnepal.feed.Fact;
import np.com.naxa.factsnepal.notification.FactsNotification;
import np.com.naxa.factsnepal.utils.ActivityUtil;
import np.com.naxa.factsnepal.utils.ImageUtils;
import np.com.naxa.factsnepal.common.OnCardItemClickListener;
import np.com.naxa.factsnepal.R;

public class FactsFeedAdapter extends RecyclerView.Adapter<FactsFeedAdapter.FeedCardVH> {

    private final OnFeedCardItemClickListener listener;
    private ArrayList<Fact> facts;


    public FactsFeedAdapter(ArrayList<Fact> facts, OnFeedCardItemClickListener listener) {
        this.facts = facts;
        this.listener = listener;
    }

    void updateList(ArrayList<Fact> newList) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new FactsDiffCallback(this.facts, newList));
        diffResult.dispatchUpdatesTo(this);
    }

    public void add(Fact mc) {
        this.facts.add(mc);
        notifyItemInserted(this.facts.size() - 1);
    }

    public void addAll(List<Fact> mcList) {
        for (Fact mc : mcList) {
            add(mc);
        }
    }

    public void remove(Fact fact) {
        int position = this.facts.indexOf(fact);
        if (position > -1) {
            this.facts.remove(position);
            notifyItemRemoved(position);
        }
    }


    @NonNull
    @Override
    public FeedCardVH onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_facts_feed_card, parent, false);
        return new FeedCardVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedCardVH viewHolder, int i) {
        Context context = viewHolder.imageView.getContext();
        int position = viewHolder.getAdapterPosition();
        Fact fact = facts.get(position);
        ImageUtils.loadRemoteImage(context, fact.getImagePath())
                .fitCenter()
                .into(viewHolder.imageView);

        viewHolder.tvTitle.setText(fact.getTitle());
        viewHolder.tvCategory.setText(fact.getCategory());
    }

    @Override
    public int getItemCount() {
        return facts != null ? facts.size() : 0;
    }

    public ArrayList<Fact> getItems() {
        return facts;
    }


    class FeedCardVH extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvTitle, tvCategory, tvSaved;
        ImageView imageView;
        View root;
        TextView btnShare;

        FeedCardVH(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_feed_card_title);
            tvCategory = itemView.findViewById(R.id.tv_feed_card_category);
            tvSaved = itemView.findViewById(R.id.tv_saved);
            root = itemView.findViewById(R.id.root_item_facts_feed_card);
            imageView = itemView.findViewById(R.id.iv_feed);
            btnShare = itemView.findViewById(R.id.btn_share);
            root.setOnClickListener(this);
            tvSaved.setOnClickListener(this);
            btnShare.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_saved:
                    tvSaved.getCompoundDrawables()[0].setState(new int[]{
                            android.R.attr.checked
                    });
                    listener.onBookmarkButtonTap(facts.get(getAdapterPosition()));
                    break;
                case R.id.root_item_facts_feed_card:
                    int pos = getAdapterPosition();
                    listener.onCardTap(facts.get(pos), imageView);
                    break;
                case R.id.btn_share:
                    listener.onShareButtonTap(facts.get(getAdapterPosition()));
                    break;
            }
        }


    }


    public interface OnFeedCardItemClickListener {
        void onCardTap(Fact fact, ImageView imageView);

        void onBookmarkButtonTap(Fact fact);

        void onShareButtonTap(Fact fact);
    }


}
