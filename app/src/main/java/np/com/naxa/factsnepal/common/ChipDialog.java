package np.com.naxa.factsnepal.common;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import android.util.Log;
import android.view.View;

import np.com.naxa.factsnepal.R;

public class ChipDialog extends AlertDialog {
    private ChipGroup group;

    public ChipDialog(Context context) {
        super(context );

        setButton(BUTTON_POSITIVE, "SAVE", (dialog, which) -> {

        });


    }

    @Override
    public void dismiss() {
        super.dismiss();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dialog_category_chips);

//        findAllChips(group);
    }

    private void findAllChips(@NonNull ChipGroup chipGroup) {

        int count = chipGroup.getChildCount();
        for (int i = 0; i < count; i++) {
            Chip view = (Chip) chipGroup.getChildAt(i);
                Chip chip = (Chip) view;
                chip.setOnCloseIconClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("DUCK","DUCK");
                    }
                });

            }
        }

    }

