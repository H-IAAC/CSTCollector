package br.org.eldorado.cst.collector;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.org.eldorado.cst.collector.data.db.Db;
import br.org.eldorado.cst.collector.data.db.room.dao.Entities.LocationStats;
import br.org.eldorado.cst.collector.ui.adapter.StatsListAdapter;
import br.org.eldorado.cst.collector.domain.model.CollectInfo;

public class StatsActivity extends AppCompatActivity {
    StatsListAdapter listAdapter;
    ArrayList<CollectInfo> dataArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        setTitle("Data Stats");

        ListView listview = findViewById(R.id.listview);

        Db db = new Db(this.getApplicationContext());

        List<LocationStats> listData = db.getRepository().getLocationInfoPojo();

        for (LocationStats location : listData){
            dataArrayList.add(new CollectInfo(location.startDate, location.endDate, location.numberOfItems, false));
        }

        listAdapter = new StatsListAdapter(StatsActivity.this, dataArrayList);
        listview.setAdapter(listAdapter);
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
}