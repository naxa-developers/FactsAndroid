package np.com.naxa.factsnepal.feed.dialog;

import android.view.View;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import np.com.naxa.factsnepal.R;
import np.com.naxa.factsnepal.feed.Fact;

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
        tvCategoryTitle.setOnClickListener((View v) -> {
            s.setCategorySelected(!s.isCategorySelected());
        });
    }
}
