package br.org.eldorado.cst.collector;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import br.org.eldorado.cst.collector.data.db.room.dao.Entities.CollectionStats;
import br.org.eldorado.cst.collector.domain.DataCollectionModel;
import br.org.eldorado.cst.collector.domain.SyncedModel;
import br.org.eldorado.cst.collector.ui.adapter.StatsListAdapter;
import br.org.eldorado.cst.collector.domain.model.CollectionInfo;

public class StatsActivity extends AppCompatActivity {
    StatsListAdapter listAdapter;
    ArrayList<CollectionInfo> dataArrayList = new ArrayList<>();
    DataCollectionModel statsReport;
    SyncedModel syncedModel;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        // Add back button
        if (getSupportActionBar() != null) {
            if (!isTaskRoot())
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setTitle("Data Stats");

        syncedModel = new SyncedModel(this.getApplicationContext());
        statsReport = new DataCollectionModel(this.getApplicationContext());

        ListView listview = findViewById(R.id.listview);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        setAdapterData(listview);

        // Implementing setOnRefreshListener on SwipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                setAdapterData(listview);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void setAdapterData(ListView listview) {
        dataArrayList.clear();

        for (CollectionStats location : statsReport.getListOfCollectedData()){
            dataArrayList.add(new CollectionInfo(location.uuid, location.startDate, location.endDate, location.numberOfItems, location.synced));
        }

        listAdapter = new StatsListAdapter(StatsActivity.this, dataArrayList, statsReport);
        listview.setAdapter(listAdapter);
    }
}