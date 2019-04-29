package np.com.naxa.factsnepal.feed.list;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import np.com.naxa.factsnepal.R;
import np.com.naxa.factsnepal.feed.Fact;
import np.com.naxa.factsnepal.utils.ImageUtils;
import np.com.naxa.factsnepal.utils.TouchImageView;

public class FactsFeedAdapter extends RecyclerView.Adapter<FactsFeedAdapter.FeedCardVH> {

    private final OnFeedCardItemClickListener listener;
    private ArrayList<Fact> facts;


    public FactsFeedAdapter(ArrayList<Fact> facts, OnFeedCardItemClickListener listener) {
        this.facts = facts;
        this.listener = listener;
    }

    public void updateList(List<Fact> newList) {
        ArrayList<Fact> oldList = this.facts;
        this.facts = (ArrayList<Fact>) newList;
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new FactsDiffCallback(oldList, newList));
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_facts_feed_card_v2, parent, false);
        return new FeedCardVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedCardVH viewHolder, int i) {
        Context context = viewHolder.itemView.getContext();
        try {
            int position = viewHolder.getAdapterPosition();
            Fact fact = facts.get(position);
//            http://paletton.com/#uid=75x0u0kjfdpbPiWdzeenjbwsi8E
            ImageUtils.loadAsBitmap(context, fact.getImagePath())
                    .fitCenter()
//                    .listener(new RequestListener<Bitmap>() {
//                        @Override
//                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
//                            return false;
//                        }
//
//                        @Override
//                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
//
//                            if (resource != null) {
//                                Palette p = Palette.from(resource).generate();
//                                // Use generated instance
//                                int bgColor = p.getDominantColor(Color.parseColor("#252121"));
//                                viewHolder.imageView.setBackgroundColor(bgColor);
//                            }
//                            return false;
//                        }`
//                    })
                    .into(viewHolder.imageView);


            viewHolder.tvTitle.setText(fact.getTitle().trim());
            viewHolder.tvCategory.setText(fact.getCategoryName());
            viewHolder.tvSaved.setChecked(fact.isBookmarked());
            viewHolder.tvLikeCount.setText(fact.getLikeCount());


        } catch (Exception e) {
            //fail silently
            e.printStackTrace();
        }

    }

    private void changeSaturation(ImageView imageView) {
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(0.9f);

        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(colorMatrix);
        imageView.setColorFilter(filter);
    }

    @Override
    public int getItemCount() {
        return facts != null ? facts.size() : 0;
    }

    public ArrayList<Fact> getItems() {
        return facts;
    }


    class FeedCardVH extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvTitle, tvCategory, btnShare, tvLikeCount;
        TouchImageView imageView;
        View rootView;
        Button fab;

        CheckBox tvSaved;


        FeedCardVH(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_feed_card_title);
            tvCategory = itemView.findViewById(R.id.tv_feed_card_category);
            tvSaved = itemView.findViewById(R.id.btn_bookmark);
            imageView = itemView.findViewById(R.id.iv_feed);
            imageView.setMaxZoom(4f);
            btnShare = itemView.findViewById(R.id.btn_share);
            tvLikeCount = itemView.findViewById(R.id.btn_like);
            rootView = itemView.findViewById(R.id.root_item_facts_feed_card);
            fab = itemView.findViewById(R.id.fab);

            tvSaved.setOnClickListener(this);

            btnShare.setOnClickListener(this);
            rootView.setOnClickListener(this);
            fab.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.btn_bookmark:
                    listener.onBookmarkButtonTap(facts.get(getAdapterPosition()));
                    break;
                case R.id.fab:
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
