package np.com.naxa.factsnepal.firebase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import np.com.naxa.factsnepal.R;
import np.com.naxa.factsnepal.feed.feedv2.FactsFeedActivity;


public class FirebaseMessanging extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";
    String NOTIFICATION_CHANNEL_ID = "fact_channel_id_01";


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Map<String, String> a = remoteMessage.getData();
        a.put("flag", "0");
        a.put("date", getDateTime());
        generateNotification(getApplicationContext(), a.get("alert_title"), a.get("alert_message"), a.get("data"), a.get("type"), a.get("image"));
    }


    public static String getDateTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        String from = df.format(Calendar.getInstance().getTime());
        return from;
    }


    public void generateNotification(Context context, String title, String message, String data, String type, String image_url) {
        NotificationCompat.Builder mBuilder;
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, getString(R.string.app_name), NotificationManager.IMPORTANCE_DEFAULT);
            // Configure the notification channel.
            notificationChannel.setDescription(getString(R.string.app_name));
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
//              notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
//              notificationChannel.enableVibration(true);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
        Intent notificationIntent;
        if (data == null || data.length() == 0) {
            notificationIntent = new Intent(context, FactsFeedActivity.class);
            notificationIntent.putExtra("page", type);
        }/*else if(type.equals("youtube")) {
            notificationIntent = new Intent(context, PlayerActivity.class)
                    .putExtra("path", data)
                    .putExtra("name", title)
                    .putExtra("gcm", true);

        }*/ else {
            notificationIntent = new Intent(Intent.ACTION_VIEW);
            notificationIntent.setData(Uri.parse(data));
        }
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        Intent i = new Intent(this, FeedListActivity.class).putExtra("alert_title", title).putExtra("data", data).putExtra("page", type);
//        PendingIntent pe = PendingIntent.getBroadcast(context, (int) Math.random(), i, PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID);
        mBuilder.setContentTitle(title)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .setContentText(message)
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message));

        if (!TextUtils.isEmpty(image_url)) {
            try {
                Bitmap bitmap = Glide.with(getApplicationContext())
                        .asBitmap()
                        .load(image_url)
                        .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .get();
                NotificationCompat.BigPictureStyle style = new NotificationCompat.BigPictureStyle()
                        .setBigContentTitle(title)
                        .bigPicture(bitmap)
                        .setSummaryText(message);
                mBuilder.setStyle(style);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //Log.d("MyReciever", "Notifying " + title);
        notificationManager.notify(title.length() + message.length(), mBuilder.build());
    }

}
