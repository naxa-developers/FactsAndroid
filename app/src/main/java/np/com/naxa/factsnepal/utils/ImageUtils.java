package np.com.naxa.factsnepal.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import np.com.naxa.factsnepal.common.GlideApp;
import np.com.naxa.factsnepal.common.GlideRequest;

public class ImageUtils {

    public static GlideRequest<Drawable> loadLocalImage(Context context, String path) {
        return GlideApp.with(context)
                .load(path)
                .centerInside()
                .transition(DrawableTransitionOptions.withCrossFade())
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE);
    }


    public static GlideRequest<Drawable> loadRemoteImage(Context context, @NonNull String path) {
        return GlideApp.with(context)
                .load(path.trim())
//                .placeholder(R.color.colorPrimaryDark)
                .transition(DrawableTransitionOptions.withCrossFade())
                .thumbnail(0.5f)
                .centerInside()
                .skipMemoryCache(false)
                .priority(Priority.LOW)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
    }


    public static GlideRequest<Bitmap> loadAsBitmap(Context context, @NonNull String path){
        return GlideApp.with(context)
                .asBitmap()
                .load(path)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
    }

}
