package br.org.eldorado.cst.collector.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import br.org.eldorado.cst.collector.R;
import br.org.eldorado.cst.collector.constants.Constants;
import br.org.eldorado.cst.collector.domain.StatsReport;
import br.org.eldorado.cst.collector.domain.model.CollectInfo;

public class StatsListAdapter extends ArrayAdapter<CollectInfo> {
    private final StatsReport statsReport;
    private final Context context;
    public StatsListAdapter(@NonNull Context context, ArrayList<CollectInfo> dataArrayList, StatsReport statsReport) {
        super(context, R.layout.stat_item, dataArrayList);
        this.statsReport = statsReport;
        this.context = context;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        CollectInfo listData = getItem(position);
        if (view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.stat_item, parent, false);
        }

        TextView periodTxt = view.findViewById(R.id.periodTxt);
        TextView numberOfElementsTxt = view.findViewById(R.id.numberOfElementsTxt);
        TextView isSyncedTxt = view.findViewById(R.id.isSyncedTxt);
        ImageButton syncBtn = view.findViewById(R.id.syncBtn);
        ImageButton deleteBtn = view.findViewById(R.id.deleteBtn);

        Date startDate = new Date(listData.getStartDate());
        Date endDate = new Date(listData.getEndDate());

        DateFormat dateFormatStart = new SimpleDateFormat("MMM, dd (HH:mm");
        DateFormat dateFormatEnd = new SimpleDateFormat(" ~ HH:mm)");

        periodTxt.setText(dateFormatStart.format(startDate) + dateFormatEnd.format(endDate));
        numberOfElementsTxt.setText(listData.getNumberOfItems() + "");

        if (listData.getSyncedState() == Constants.SYNCED_DATA.YES) {
            isSyncedTxt.setTextColor(Color.GREEN);
            isSyncedTxt.setText(R.string.synced);
        } else if (listData.getSyncedState() == Constants.SYNCED_DATA.NO) {
            isSyncedTxt.setTextColor(Color.RED);
            isSyncedTxt.setText(R.string.not_synced);
        } else {
            isSyncedTxt.setTextColor(Color.BLUE);
            isSyncedTxt.setText(R.string.on_going);
        }

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listData.getSyncedState() != Constants.SYNCED_DATA.ON_GOING) {
                    synchronized (this) {
                        statsReport.deleteCollectedData(listData.getUuid());
                        remove(listData);
                    }
                } else {
                    Toast.makeText(context, "Can't delete on going...", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

}
