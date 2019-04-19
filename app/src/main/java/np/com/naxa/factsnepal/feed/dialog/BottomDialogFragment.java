package np.com.naxa.factsnepal.feed.dialog;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import np.com.naxa.factsnepal.R;
import np.com.naxa.factsnepal.common.BaseRecyclerViewAdapter;
import np.com.naxa.factsnepal.common.ItemOffsetDecoration;
import np.com.naxa.factsnepal.notification.FactsNotification;

@SuppressLint("ValidFragment")
public class BottomDialogFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    private BaseRecyclerViewAdapter<String, CategoryVH> adapter;
    private View btnClose;

    public static BottomDialogFragment getInstance() {
        return new BottomDialogFragment();
    }

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.layout_dialog_category_chips, container, false);
        bindUI(view);

        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i <= 100; i++) {
            strings.add("Category " + i);
        }

        setupListAdapter(strings);
        return view;
    }

    private void bindUI(View view) {
        recyclerView = view.findViewById(R.id.rv_categories_chips);
        btnClose = view.findViewById(R.id.btn_close_dialog_category_chips);
        btnClose.setOnClickListener(this);
    }

    private void setupListAdapter(List<String> list) {

        LinearLayoutManager manager = new GridLayoutManager(requireActivity(), 3);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(requireActivity(), R.dimen.margin_small);
        recyclerView.addItemDecoration(itemDecoration);

        adapter = new BaseRecyclerViewAdapter<String, CategoryVH>(list, R.layout.item_categories) {
            @Override
            public void viewBinded(CategoryVH categoryVH, String s, int position) {
                categoryVH.bindView(s);
            }

            @Override
            public CategoryVH attachViewHolder(View view) {
                return new CategoryVH(view);
            }
        };

        recyclerView.setAdapter(adapter);
    }

    public static ArrayList<Integer> categoryList = new ArrayList<Integer>();


    public static void getSelectedCategories(@NonNull CategorySelectedListener listener) {
        listener.onClick(categoryList);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_close_dialog_category_chips:
                dismiss();
                break;
        }
    }

    public interface CategorySelectedListener {
        void onClick(ArrayList<Integer> categoriesList);
    }


}


