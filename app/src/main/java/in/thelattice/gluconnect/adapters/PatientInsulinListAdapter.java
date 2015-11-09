package in.thelattice.gluconnect.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import in.thelattice.gluconnect.R;
import in.thelattice.gluconnect.models.PatientInsulinRowItem;

/**
 * Created by Ishan on 03-11-2015.
 */
public class PatientInsulinListAdapter extends BaseAdapter {
    Context context;
    List<PatientInsulinRowItem> rowItems;

    public PatientInsulinListAdapter(Context context, List<PatientInsulinRowItem> items) {
        this.context = context;
        this.rowItems = items;
    }

    /*private view holder class*/
    private class ViewHolder {
        TextView tvTime;
        TextView tvAMorPM;
        TextView tvField1;
        TextView tvField2;
        TextView tvField3;
        TextView tvField4;
        TextView tvField5;
        TextView tvDate;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.insulin_listelement, null);
            holder = new ViewHolder();

            holder.tvTime = (TextView) convertView.findViewById(R.id.tvTime);
            holder.tvAMorPM = (TextView) convertView.findViewById(R.id.tvAMorPM);
            holder.tvField1 = (TextView) convertView.findViewById(R.id.tvField1);
            holder.tvField2 = (TextView) convertView.findViewById(R.id.tvField2);
            holder.tvField3 = (TextView) convertView.findViewById(R.id.tvField3);
            holder.tvField4 = (TextView) convertView.findViewById(R.id.tvField4);
            holder.tvField5 = (TextView) convertView.findViewById(R.id.tvField5);
            holder.tvDate =(TextView) convertView.findViewById(R.id.tvDate);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        PatientInsulinRowItem rowItem = (PatientInsulinRowItem) getItem(position);

        String[] timeParts = rowItem.getTime_of_glucose_reading().split(" ");
        holder.tvTime.setText(timeParts[0]);
        holder.tvAMorPM.setText(timeParts[1]);
        holder.tvField1.setText(rowItem.getFeed_status());
        holder.tvField2.setText(rowItem.getGlucose_reading());
        /*holder.tvField3.setText(rowItem.getField3());
        holder.tvField4.setText(rowItem.getField4());
        holder.tvField5.setText(rowItem.getField5());
        holder.tvDate.setText(rowItem.getDate());*/

        return convertView;
    }

    public void changeData( List<PatientInsulinRowItem> _items){
        rowItems = _items;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }
}
