package br.org.eldorado.cst.collector;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import br.org.eldorado.cst.collector.data.db.room.dao.Entities.LocationStats;
import br.org.eldorado.cst.collector.domain.StatsReport;
import br.org.eldorado.cst.collector.ui.adapter.StatsListAdapter;
import br.org.eldorado.cst.collector.domain.model.CollectInfo;

public class StatsActivity extends AppCompatActivity {
    StatsListAdapter listAdapter;
    ArrayList<CollectInfo> dataArrayList = new ArrayList<>();
    StatsReport statsReport;

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

        statsReport = new StatsReport(this.getApplicationContext());

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

        //binding.listview.setClickable(true);
        /*binding.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, DetailedActivity.class);
                intent.putExtra("name", nameList[i]);
                intent.putExtra("time", timeList[i]);
                intent.putExtra("ingredients", ingredientList[i]);
                intent.putExtra("desc", descList[i]);
                intent.putExtra("image", imageList[i]);
                startActivity(intent);
            }
        });*/
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void setAdapterData(ListView listview) {
        dataArrayList.clear();

        for (LocationStats location : statsReport.getListOfCollectedData()){
            dataArrayList.add(new CollectInfo(location.uuid, location.startDate, location.endDate, location.numberOfItems, location.synced));
        }

        listAdapter = new StatsListAdapter(StatsActivity.this, dataArrayList, statsReport);
        listview.setAdapter(listAdapter);
    }
}