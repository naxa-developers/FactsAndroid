package np.com.naxa.factsnepal.feedv2;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import np.com.naxa.factsnepal.R;


import np.com.naxa.factsnepal.common.OnCardItemClickListener;
import np.com.naxa.factsnepal.feed.Fact;
import np.com.naxa.factsnepal.feed.list.FactsDiffCallback;
import np.com.naxa.factsnepal.utils.ImageUtils;

public class ShowcaseFeedAdapter extends RecyclerView.Adapter<ShowcaseFeedAdapter.ViewHolder> {

    private ArrayList<Fact> facts;
    private OnCardItemClickListener<Fact> listener;
    private RecyclerView recyclerView;


    public ShowcaseFeedAdapter(ArrayList<Fact> facts, OnCardItemClickListener<Fact> listener) {
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

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        super.onAttachedToRecyclerView(recyclerView);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_facts_feed_showcase_card, parent, false);
        int width = view.getHeight();
        Log.i("DUCK", width + "");
//        ViewGroup.LayoutParams params = view.getLayoutParams();
//        params.height = (int)(1000);
//        view.setLayoutParams(params);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Context context = viewHolder.imageView.getContext();
        int position = viewHolder.getAdapterPosition();
        Fact fact = facts.get(position);
        ImageUtils.loadRemoteImage(context, fact.getImagePath())
                .centerCrop()
                .into(viewHolder.imageView);

        viewHolder.tvTitle.setText(fact.getTitle());
        viewHolder.tvCategory.setText(fact.getCategory());

        setupCard(viewHolder);
    }

    private void setupCard(ViewHolder viewHolder) {
        Context context = viewHolder.imageView.getContext();

        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int devicewidth = displaymetrics.widthPixels / 2;
        int deviceheight = displaymetrics.heightPixels / 2;

        int imageHeight = deviceheight / 2;

        viewHolder.layout_card.getLayoutParams().height = deviceheight;

        viewHolder.imageView.getLayoutParams().height = imageHeight;
    }

    @Override
    public int getItemCount() {
        return facts != null ? facts.size() : 0;
    }

    public ArrayList<Fact> getItems() {
        return facts;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvCategory, tvSaved;
        ImageView imageView;
        CardView layout_card;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_showcase_feed_card_desc);
            tvCategory = itemView.findViewById(R.id.tv_showcase_feed_card_category);
            tvSaved = itemView.findViewById(R.id.tv_saved);
            layout_card = itemView.findViewById(R.id.layout_card);

            tvSaved.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tvSaved.getCompoundDrawables()[0].setState(new int[]{
                            android.R.attr.checked
                    });

                }
            });

            imageView = itemView.findViewById(R.id.iv_feed_showcase);
        }

    }
}
