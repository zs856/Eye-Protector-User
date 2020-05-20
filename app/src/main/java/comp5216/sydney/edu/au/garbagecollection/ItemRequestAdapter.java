package comp5216.sydney.edu.au.garbagecollection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import comp5216.sydney.edu.au.garbagecollection.mode.Request;

/**
 * ItemRequestAdapter for StaffActivity's ListView
 */
public class ItemRequestAdapter extends BaseAdapter {

    private List<Request> objects = new ArrayList<>();

    private Context context;
    private LayoutInflater layoutInflater;

    public ItemRequestAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setObjects(List<Request> objects) {
        this.objects = objects;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Request getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_request, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews(getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    /**
     * setup view by {@link Request} info
     *
     * @param object
     * @param holder
     */
    private void initializeViews(Request object, ViewHolder holder) {
        holder.name.setText("Name: " + object.getName());
        holder.time.setVisibility(View.VISIBLE);
        // show collect time
        if (Request.STATUS_UNCOMPLETED.equalsIgnoreCase(object.getStatus()) && object.getScheduledTime() != null) {
            holder.time.setText("Appointment time: " + object.getScheduledTime().toDate().toString());
        }
        // show finished time
        else if (Request.STATUS_COMPLETED.equalsIgnoreCase(object.getStatus()) && object.getFinishedTime() != null) {
            holder.time.setText("Completed time: " + object.getFinishedTime().toDate().toString());
        }
        // show created time
        else if (object.getCreatedTime() != null) {
            holder.time.setText("Created time: " + object.getCreatedTime().toDate().toString());
        }
        // hide time's view
        else {
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
