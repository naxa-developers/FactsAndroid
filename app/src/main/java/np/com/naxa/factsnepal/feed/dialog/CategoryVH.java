package np.com.naxa.factsnepal.feed.dialog;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import np.com.naxa.factsnepal.R;
import np.com.naxa.factsnepal.feed.Fact;
import np.com.naxa.factsnepal.network.facts.Category;

class CategoryVH extends RecyclerView.ViewHolder {

    private final CheckBox tvCategoryTitle;
    private View itemView;

    CategoryVH(@NonNull View itemView) {
        super(itemView);
        tvCategoryTitle = itemView.findViewById(R.id.tv_category_title);
        this.itemView = itemView;
    }

    void bindView(Fact s) {
        tvCategoryTitle.setText(s.getCategoryName());
        tvCategoryTitle.setChecked(s.isCategorySelected());
        tvCategoryTitle.setOnClickListener(v -> s.setCategorySelected(!s.isCategorySelected()));
    }
}
