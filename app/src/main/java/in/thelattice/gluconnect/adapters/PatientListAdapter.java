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
import in.thelattice.gluconnect.models.PatientRowItem;


public class PatientListAdapter extends BaseAdapter {
    Context context;
    List<PatientRowItem> rowItems;

    public PatientListAdapter(Context context, List<PatientRowItem> items) {
        this.context = context;
        this.rowItems = items;
    }

    /*private view holder class*/
    private class ViewHolder {
        TextView tvNameFirstLetter;
        TextView tvPatientName;
        TextView tvWard;
        TextView tvAgeSex;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.patients_listitem, null);
            holder = new ViewHolder();

            holder.tvNameFirstLetter = (TextView) convertView.findViewById(R.id.tvNameFirstLetter);
            holder.tvPatientName = (TextView) convertView.findViewById(R.id.tvPatientName);
            holder.tvWard = (TextView) convertView.findViewById(R.id.tvWard);
            holder.tvAgeSex =(TextView) convertView.findViewById(R.id.tvAgeSex);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        PatientRowItem rowItem = (PatientRowItem) getItem(position);

        holder.tvNameFirstLetter.setText(rowItem.getName().charAt(0)+"");
        holder.tvPatientName.setText(rowItem.getName());
        holder.tvWard.setText(rowItem.getWard());
        holder.tvAgeSex.setText(rowItem.getAge() + "/" + rowItem.getGender());

        return convertView;
    }

    public void  changeData(List<PatientRowItem> items){
        rowItems = items;

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
