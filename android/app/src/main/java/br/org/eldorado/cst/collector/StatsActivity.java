package br.org.eldorado.cst.collector;

import static br.org.eldorado.cst.collector.constants.Constants.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

import br.org.eldorado.cst.collector.data.db.room.dao.entities.CollectionStats;
import br.org.eldorado.cst.collector.domain.DataCollectionModel;
import br.org.eldorado.cst.collector.domain.SyncedModel;
import br.org.eldorado.cst.collector.ui.adapter.StatsListAdapter;
import br.org.eldorado.cst.collector.domain.model.CollectionInfo;

public class StatsActivity extends AppCompatActivity {
    private ListView listview;
    private StatsListAdapter listAdapter;
    private ArrayList<CollectionInfo> dataArrayList = new ArrayList<>();
    private DataCollectionModel statsReport;
    private SyncedModel syncedModel;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        // Add back button
        if (getSupportActionBar() != null) {
          // if (!isTaskRoot())
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setTitle("Data Stats");

        syncedModel = new SyncedModel(this.getApplicationContext());
        statsReport = new DataCollectionModel(this.getApplicationContext());

        listview = findViewById(R.id.listview);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        setAdapterData();

        // Implementing setOnRefreshListener on SwipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(false);
            setAdapterData();
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        swipeRefreshLayout.post(() -> {
            swipeRefreshLayout.setRefreshing(false);
            setAdapterData();
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (!isTaskRoot())
            return super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                        Intent.FLAG_ACTIVITY_SINGLE_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getApplicationContext().startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setAdapterData() {
        dataArrayList.clear();

        for (CollectionStats location : statsReport.getListOfCollectedData()) {
            Log.i(TAG, "uuid: " + location.uuid + " numberOfItemsNotSent: " + location.numberOfItemsNotSent + " numberOfItems: " + location.numberOfItems);
            dataArrayList.add(new CollectionInfo(location.uuid, location.startDate, location.endDate, location.numberOfItems, location.numberOfItemsNotSent, location.synced));
        }

        listAdapter = new StatsListAdapter(StatsActivity.this, dataArrayList, statsReport);
        listview.setAdapter(listAdapter);
    }
}