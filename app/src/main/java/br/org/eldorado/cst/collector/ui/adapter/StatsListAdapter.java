package br.org.eldorado.cst.collector.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import br.org.eldorado.cst.collector.R;
import br.org.eldorado.cst.collector.domain.model.CollectInfo;

public class StatsListAdapter extends ArrayAdapter<CollectInfo> {
    public StatsListAdapter(@NonNull Context context, ArrayList<CollectInfo> dataArrayList) {
        super(context, R.layout.report_item, dataArrayList);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        CollectInfo listData = getItem(position);
        if (view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.report_item, parent, false);
        }

        TextView periodTxt = view.findViewById(R.id.periodTxt);
        TextView numberOfElementsTxt = view.findViewById(R.id.numberOfElementsTxt);
        TextView isSyncedTxt = view.findViewById(R.id.isSyncedTxt);

        Date startDate = new Date(listData.getStartDate());
        Date endDate = new Date(listData.getEndDate());

        DateFormat dateFormatStart = new SimpleDateFormat("MMM, dd (HH:mm");
        DateFormat dateFormatEnd = new SimpleDateFormat(" ~ HH:mm)");

        periodTxt.setText(dateFormatStart.format(startDate) + dateFormatEnd.format(endDate));
        numberOfElementsTxt.setText(listData.getNumberOfItems() + "");

        if (listData.idSynced()) {
            isSyncedTxt.setTextColor(Color.GREEN);
            isSyncedTxt.setText(R.string.synced);
        } else {
            isSyncedTxt.setTextColor(Color.RED);
            isSyncedTxt.setText(R.string.not_synced);
        }

        return view;
    }
}
