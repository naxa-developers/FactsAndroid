package np.com.naxa.factsnepal.feed.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import np.com.naxa.factsnepal.R;
import np.com.naxa.factsnepal.common.BaseRecyclerViewAdapter;
import np.com.naxa.factsnepal.common.Constant;
import np.com.naxa.factsnepal.common.ItemOffsetDecoration;
import np.com.naxa.factsnepal.feed.Fact;
import np.com.naxa.factsnepal.feed.feedv2.FactsFeedActivity;
import np.com.naxa.factsnepal.feed.list.resource.FactsRepo;
import np.com.naxa.factsnepal.utils.SharedPreferenceUtils;

public class BottomDialogFragment extends BottomSheetDialogFragment implements View.OnClickListener {
    private OnCategoriesSelectedListener listener;
    private BaseRecyclerViewAdapter<Fact, CategoryVH> adapter;
    Set<String> selectedCategories = new HashSet<>();

    public BottomDialogFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FactsFeedActivity) {
            this.listener = (OnCategoriesSelectedListener) context;
        }
    }

    public static BottomDialogFragment getInstance() {
        return new BottomDialogFragment();
    }

    private RecyclerView recyclerView;
    public static CheckBox selectAllCheckbox;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Constant.isIndividualCheckboxClicked = false;
        return inflater.inflate(R.layout.layout_dialog_category_chips, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        selectedCategories = SharedPreferenceUtils.getInstance(getContext()).getSetValue(Constant.SharedPrefKey.SELECTED_CATEGORIES);
        bindUI();
        FactsRepo.getINSTANCE().getFactsCategories(true)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(this::mapFactsToCategories)
                .subscribe(new DisposableSingleObserver<List<Fact>>() {
                    @Override
                    public void onSuccess(List<Fact> categories) {
                        setupListAdapter(categories);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });

    }

    private List<Fact> mapFactsToCategories(@NonNull List<Fact> facts) {
        for (Fact fact : facts) {
            fact.setCategorySelected(selectedCategories.contains(fact.getCatgoryId()));
        }
        return facts;
    }

    private void bindUI() {
        recyclerView = getView().findViewById(R.id.rv_categories_chips);
        View btnClose = getView().findViewById(R.id.btn_close_dialog_category_chips);
        selectAllCheckbox = getView().findViewById(R.id.checkbox_dialog_category_chips);
        selectAllCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            setAllCheckBoxSelectStatus(isChecked);
        });
        btnClose.setOnClickListener(this);

    }

    private void setAllCheckBoxSelectStatus(boolean isChecked){
        if(!Constant.isIndividualCheckboxClicked) {
            for (Fact fact : adapter.getData()) {
                fact.setCategorySelected(isChecked);
            }
            adapter.notifyDataSetChanged();
        }
        Constant.isIndividualCheckboxClicked = false;
    }

    private void setupListAdapter(List<Fact> list) {
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(requireActivity(), R.dimen.margin_small);
        adapter = new BaseRecyclerViewAdapter<Fact, CategoryVH>(list, R.layout.item_categories) {
            @Override
            public void viewBinded(CategoryVH categoryVH, Fact category, int position) {
                categoryVH.bindView(category);
            }

            @Override
            public CategoryVH attachViewHolder(View view) {
                return new CategoryVH(view);
            }
        };

        GridLayoutManager manager = new GridLayoutManager(requireActivity(), 3);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setHasFixedSize(true);
        recyclerView.setMinimumHeight(400);
        recyclerView.setAdapter(adapter);

        if(Constant.isFactsFeedFirstTime){
            selectAllCheckbox.setChecked(true);
            setAllCheckBoxSelectStatus(true);
            Constant.isFactsFeedFirstTime = false;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_close_dialog_category_chips:
                selectedCategories.clear();
                for (Fact fact : adapter.getData()) {
                    if (fact.isCategorySelected())
                        selectedCategories.add(fact.getCatgoryId());
                }
                SharedPreferenceUtils.getInstance(getContext()).setSetValue(Constant.SharedPrefKey.SELECTED_CATEGORIES, selectedCategories);
                if (listener != null) listener.onCategoriesSelected(selectedCategories);
                dismiss();
        }
    }

    public interface OnCategoriesSelectedListener {
        void onCategoriesSelected(Set<String> categories);
    }
}


