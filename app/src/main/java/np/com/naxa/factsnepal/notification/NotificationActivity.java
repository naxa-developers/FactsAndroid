package np.com.naxa.factsnepal.notification;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import np.com.naxa.factsnepal.R;
import np.com.naxa.factsnepal.common.BaseActivity;
import np.com.naxa.factsnepal.common.BaseRecyclerViewAdapter;

public class NotificationActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private BaseRecyclerViewAdapter<FactsNotification, FactsNotificationVH> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        setupToolbar();
        bindUI();
        setupListAdapter(FactsNotification.getDemoItems());

    }

    private void bindUI() {
        recyclerView = findViewById(R.id.rv_notification);
    }


    private void setupListAdapter(List<FactsNotification> factsNotifications) {

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new BaseRecyclerViewAdapter<FactsNotification, FactsNotificationVH>(factsNotifications, R.layout.item_notification) {


            @Override
            public void viewBinded(FactsNotificationVH factsNotificationVH, FactsNotification factsNotification) {
                factsNotificationVH.bindView(factsNotification);
            }

            @Override
            public FactsNotificationVH attachViewHolder(View view) {
                return new FactsNotificationVH(view);
            }
        };

        recyclerView.setAdapter(adapter);
    }

}
