package np.com.naxa.factsnepal.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.text.Html;

import java.util.HashMap;

import np.com.naxa.factsnepal.BuildConfig;

public class ActivityUtil {

    public static void openActivity(Class className, Context context, HashMap<String, ?> data, boolean skipAnimation) {
        Intent intent = new Intent(context, className);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (skipAnimation) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        }

        if (EmptyUtil.isNotNull(data) && EmptyUtil.isNotEmpty(data)) {
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("map", data);
//            intent.putExtra("bundle", bundle);
            intent.putExtra("map", data);
        }

        context.startActivity(intent);
    }

    public static void openActivity(Class className, Context context) {
        openActivity(className, context, null, false);
    }

    public static void openShareIntent(Context context, String text) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, text);
        context.startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }

    public static void openUri(Context context, Uri uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        context.startActivity(intent);
    }
}
