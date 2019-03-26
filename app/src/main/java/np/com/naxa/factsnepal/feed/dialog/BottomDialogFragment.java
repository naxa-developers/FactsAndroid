package np.com.naxa.factsnepal.feed.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.chip.Chip;
import android.support.design.chip.ChipGroup;
import android.support.design.widget.BottomSheetDialogFragment;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.facebook.login.LoginResult;

import java.util.ArrayList;

import np.com.naxa.factsnepal.R;
import np.com.naxa.factsnepal.feed.Fact;
import np.com.naxa.factsnepal.feed.list.FeedListActivity;

@SuppressLint("ValidFragment")
public class BottomDialogFragment extends BottomSheetDialogFragment {
    FeedListActivity feedListActivity = new FeedListActivity();

    public static BottomDialogFragment getInstance() {
        return new BottomDialogFragment();
    }

    private ChipGroup chipGroup;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.layout_dialog_category_chips, container, false);
        bindUI(view);
        setupChips();
        return view;
    }

    public static ArrayList<Integer> categoryList = new ArrayList<Integer>();

    private void setupChips() {
        if(Fact.getCategories() == null){
            return;
        }
        for (Pair p : Fact.getCategories()) {
            Chip chip = new Chip(new ContextThemeWrapper(requireActivity(), R.style.Feed_Widget_Chip));
            chip.setText(p.second.toString());
            chip.setTag(p);
            chip.setCheckable(true);


            chip.setOnCheckedChangeListener((buttonView, isChecked) -> {
                String label = ((Pair) buttonView.getTag()).second.toString();
                int id = Integer.parseInt(((Pair) buttonView.getTag()).first.toString());
                Log.d("bottomsheet", "setupChips: "+label);
                Log.d("bottomsheet", "setupChips: "+id);

                if(!categoryList.contains(id)) {
                    categoryList.add(id);

                    feedListActivity.listenChipsStatus();

                }

            });

            chipGroup.addView(chip);
        }
        chipGroup.invalidate();
    }

    public static void getSelectedCategories( @NonNull CategorySelectedListener listener) {
        listener.onClick(categoryList);
    }

        public interface CategorySelectedListener {
        void onClick(ArrayList<Integer> categoriesList);
    }


    private void bindUI(View view) {
        chipGroup = view.findViewById(R.id.cg_dialog_category);
    }


}


