/**
 * 
 */
package adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.mkcomingc.R;

import android.R.array;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author Liming Chu
 * 
 * @param
 * @return
 */
public class RechargeRecordListAdapter extends BaseAdapter {

    private ArrayList<HashMap<String, String>> list;
    private Context context;
    private LayoutInflater lInflater;

    public RechargeRecordListAdapter(ArrayList<HashMap<String, String>> list,
	    Context context) {
	super();
	this.list = list;
	this.context = context;
	this.lInflater = LayoutInflater.from(context);
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.widget.Adapter#getCount()
     */
    @Override
    public int getCount() {
	// TODO Auto-generated method stub
	return list.size();
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.widget.Adapter#getItem(int)
     */
    @Override
    public Object getItem(int position) {
	// TODO Auto-generated method stub
	return list.get(position);
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.widget.Adapter#getItemId(int)
     */
    @Override
    public long getItemId(int position) {
	// TODO Auto-generated method stub
	return position;
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.widget.Adapter#getView(int, android.view.View,
     * android.view.ViewGroup)
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
	// TODO Auto-generated method stub
	ViewHolder vHolder = new ViewHolder();
	if (convertView == null) {
	    convertView = lInflater.inflate(R.layout.recharge_record_lv_item,
		    null);
	}
	vHolder.time = (TextView) convertView
		.findViewById(R.id.recharge_record_item_time);
	vHolder.amount = (TextView) convertView
		.findViewById(R.id.recharge_record_item_amount);
	vHolder.method = (TextView) convertView
		.findViewById(R.id.recharge_record_item_method);

	vHolder.time.setText(list.get(position).get("sj"));
	vHolder.amount.setText("гд"+list.get(position).get("je"));
	vHolder.method.setText(list.get(position).get("czfs"));
	
	return convertView;
    }

    class ViewHolder {
	TextView time;
	TextView amount;
	TextView method;
    }
}
