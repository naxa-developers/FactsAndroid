package np.com.naxa.factsnepal.feed.dialog;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import np.com.naxa.factsnepal.R;
import np.com.naxa.factsnepal.common.BaseRecyclerViewAdapter;
import np.com.naxa.factsnepal.common.Constant;
import np.com.naxa.factsnepal.common.ItemOffsetDecoration;
import np.com.naxa.factsnepal.feed.list.FactsRemoteSource;
import np.com.naxa.factsnepal.network.facts.Category;
import np.com.naxa.factsnepal.utils.SharedPreferenceUtils;

@SuppressLint("ValidFragment")
public class BottomDialogFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    private final OnCategoriesSelectedListener listener;
    private BaseRecyclerViewAdapter<Category, CategoryVH> adapter;
    private DisposableObserver<List<Category>> dis;
    private Gson gson;

    public BottomDialogFragment(OnCategoriesSelectedListener listener) {
        this.listener = listener;
    }

    public static BottomDialogFragment getInstance(OnCategoriesSelectedListener listener) {
        return new BottomDialogFragment(listener);
    }

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.layout_dialog_category_chips, container, false);
        bindUI(view);

        this.gson = new Gson();

        dis = FactsRemoteSource.getINSTANCE()
                .getCategories()

                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Category>>() {
                    @Override
                    public void onNext(List<Category> categories) {
                        setupListAdapter(categories);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });


        return view;
    }

    private void bindUI(View view) {
        recyclerView = view.findViewById(R.id.rv_categories_chips);
        View btnClose = view.findViewById(R.id.btn_close_dialog_category_chips);
        btnClose.setOnClickListener(this);
    }

    private void setupListAdapter(List<Category> list) {

        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(requireActivity(), R.dimen.margin_small);
        recyclerView.addItemDecoration(itemDecoration);
        adapter = new BaseRecyclerViewAdapter<Category, CategoryVH>(list, R.layout.item_categories) {
            @Override
            public void viewBinded(CategoryVH categoryVH, Category category, int position) {
                categoryVH.bindView(category);
            }

            @Override
            public CategoryVH attachViewHolder(View view) {
                return new CategoryVH(view);
            }
        };

        GridLayoutManager manager = new GridLayoutManager(requireActivity(), 3);
        recyclerView.setLayoutManager(manager);

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_close_dialog_category_chips:
                getSelectedItems();

                break;
        }
    }

    private void getSelectedItems() {
        List<Category> data = adapter.getData();
        Single<List<Category>> observable = Observable.just(data)
                .subscribeOn(Schedulers.io())
                .flatMapIterable((Function<List<Category>, Iterable<Category>>) categories -> categories)
                .filter(Category::isSelected)
                .toList();

        observable.subscribe(new DisposableSingleObserver<List<Category>>() {
            @Override
            public void onSuccess(List<Category> categories) {
                if (listener != null) listener.onCategoriesSelected(categories);
                SharedPreferenceUtils.getInstance(getContext()).setValue(Constant.SharedPrefKey.SELECTED_CATEGORIES, gson.toJson(categories));
                dismiss();
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    public interface OnCategoriesSelectedListener {
        void onCategoriesSelected(List<Category> categories);
    }
}


