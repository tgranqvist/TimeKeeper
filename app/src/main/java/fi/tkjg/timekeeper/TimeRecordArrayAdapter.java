package fi.tkjg.timekeeper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import fi.tkjg.timekeeper.data.TimeRecord;

/**
 * Created by granqto on 14-12-2016.
 */

public class TimeRecordArrayAdapter extends ArrayAdapter<TimeRecord> {

    public TimeRecordArrayAdapter(Context context, List<TimeRecord> records) {
        super(context, 0, records);
    }

    @Override
    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        TimeRecord record = getItem(position);

        if (null == convertView) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_time_record, parent, false);
        }

        //noinspection ConstantConditions
        ((TextView)(convertView.findViewById(R.id.list_item_day))).setText(record.date);
        ((CheckBox)(convertView.findViewById(R.id.list_item_velho))).setChecked(record.isVelhoOk);

        return convertView;
    }
}
