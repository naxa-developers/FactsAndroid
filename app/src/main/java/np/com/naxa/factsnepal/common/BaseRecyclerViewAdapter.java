package np.com.naxa.factsnepal.common;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import np.com.naxa.factsnepal.FactsNepal;


//import com.rillmark.royalworldcup.MainApplication;

public abstract class BaseRecyclerViewAdapter<L, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    private List<L> itemList;
    private int layout;

    protected BaseRecyclerViewAdapter(List<L> itemList, int layout) {
        this.itemList = itemList;
        this.layout = layout;

    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(FactsNepal.getInstance()).inflate(layout, parent, false);
        return attachViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        viewBinded(holder, itemList.get(position), position);
    }

    public List<L> getData() {
        return this.itemList;
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public abstract void viewBinded(VH vh, L l, int position);

    public abstract VH attachViewHolder(View view);
}