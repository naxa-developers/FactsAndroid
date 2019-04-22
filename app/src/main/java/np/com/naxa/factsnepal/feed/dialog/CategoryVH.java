package np.com.naxa.factsnepal.feed.dialog;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import np.com.naxa.factsnepal.R;
import np.com.naxa.factsnepal.network.facts.Category;

class CategoryVH extends RecyclerView.ViewHolder {

    private final CheckBox tvCategoryTitle;
    private View itemView;

    CategoryVH(@NonNull View itemView) {
        super(itemView);
        tvCategoryTitle = itemView.findViewById(R.id.tv_category_title);
        this.itemView = itemView;
    }

    void bindView(Category s) {
        tvCategoryTitle.setText(s.getTitle());
        tvCategoryTitle.setChecked(s.isSelected());

        tvCategoryTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s.setSelected(!s.isSelected());
            }
        });
    }
}
