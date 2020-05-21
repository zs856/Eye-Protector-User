package comp5216.sydney.edu.au.garbagecollection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import comp5216.sydney.edu.au.garbagecollection.mode.Results;

/**
 * ItemRequestAdapter for StaffActivity's ListView
 */
public class ItemResultAdapter extends BaseAdapter {

    private List<Results> objects = new ArrayList<>();

    private Context context;
    private LayoutInflater layoutInflater;

    public ItemResultAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setObjects(List<Results> objects) {
        this.objects = objects;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Results getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_results, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews(getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    /**
     * setup view by {@link Results} info
     *
     * @param object
     * @param holder
     */
    private void initializeViews(Results object, ViewHolder holder) {
        holder.name.setText("severity: " + object.getSeverity());

        holder.time.setVisibility(View.VISIBLE);

        if (object.getTimestamp() != null) {
            holder.time.setText("Created time: " + object.getTimestamp().toDate().toString());
        } else {
            holder.time.setVisibility(View.GONE);
        }
    }

    protected class ViewHolder {
        private TextView name;
        private TextView time;

        public ViewHolder(View view) {
            name = (TextView) view.findViewById(R.id.name);
            time = (TextView) view.findViewById(R.id.time);
        }
    }
}
