package np.com.naxa.factsnepal.utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import np.com.naxa.factsnepal.R;
import np.com.naxa.factsnepal.common.ChipDialog;

public class DialogUtils {
    public static ChipDialog showChipsDialog(Context context) {
        return new ChipDialog(context);
    }





    /**
     * Ensures that a dialog is shown safely and doesn't causes a crash. Useful in the event
     * of a screen rotation, async operations or activity navigation.
     *
     * @param dialog   that needs to be shown
     * @param activity that has the dialog
     */
    public static void showDialog(Dialog dialog, Activity activity) {

        if (activity == null || activity.isFinishing()) {
            return;
        }
        if (dialog == null || dialog.isShowing()) {
            return;
        }

        try {
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Ensures that a dialog is dismissed safely and doesn't causes a crash. Useful in the event
     * of a screen rotation, async operations or activity navigation.
     *
     * @param dialog   that needs to be shown
     * @param activity that has the dialog
     */
    public static void dismissDialog(Dialog dialog, Activity activity) {

        if (activity == null || activity.isFinishing()) {
            return;
        }
        if (dialog == null || !dialog.isShowing()) {
            return;
        }

        try {
            dialog.dismiss();
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

}
