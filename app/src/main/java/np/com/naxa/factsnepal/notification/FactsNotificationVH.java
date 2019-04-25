package np.com.naxa.factsnepal.notification;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import np.com.naxa.factsnepal.R;

class FactsNotificationVH extends RecyclerView.ViewHolder {
    private TextView tvNotificationTitle, tvNotificationTime;
    private View rootLayout;


    public FactsNotificationVH(@NonNull View itemView) {
        super(itemView);
        tvNotificationTitle = itemView.findViewById(R.id.tvNotificationTitle);
        tvNotificationTime = itemView.findViewById(R.id.tv_notification_time);
        rootLayout = itemView.findViewById(R.id.root_layout);

    }

    void bindView(FactsNotification factsNotification) {
        tvNotificationTitle.setText(factsNotification.getTitle());
        tvNotificationTime.setText(factsNotification.getTime());
//        rootLayout.setEnabled(factsNotification.isRead());
    }
}
