package np.com.naxa.factsnepal.feed.dialog;

import android.annotation.SuppressLint;
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

import np.com.naxa.factsnepal.R;
import np.com.naxa.factsnepal.feed.Fact;

@SuppressLint("ValidFragment")
public class BottomDialogFragment extends BottomSheetDialogFragment {

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

    private void setupChips() {
        for (Pair p : Fact.getDemoCategories()) {
            Chip chip = new Chip(new ContextThemeWrapper(requireActivity(), R.style.Feed_Widget_Chip));
            chip.setText(p.second.toString());
            chip.setTag(p);
            chip.setCheckable(true);

            chip.setOnCheckedChangeListener((buttonView, isChecked) -> {
                String label = ((Pair) buttonView.getTag()).second.toString();

            });

            chipGroup.addView(chip);
        }
        chipGroup.invalidate();
    }

    private void bindUI(View view) {
        chipGroup = view.findViewById(R.id.cg_dialog_category);
    }


}


