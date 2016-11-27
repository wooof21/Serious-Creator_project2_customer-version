/**
 * 
 */
package adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.mkcomingc.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author Liming Chu
 * 
 * @param
 * @return
 */
public class AddressListAdapter extends BaseAdapter {

    private ArrayList<HashMap<String, String>> list;
    private Context context;
    private LayoutInflater lInflater;

    public AddressListAdapter(ArrayList<HashMap<String, String>> list,
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
	return list == null ? 0 : list.size();
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
	    convertView = lInflater.inflate(R.layout.address_list_item, null);
	}
	vHolder.pic = (ImageView) convertView
		.findViewById(R.id.addr_list_item_iv);
	vHolder.address = (TextView) convertView
		.findViewById(R.id.addr_list_item_tv);

	if(list.get(position).get("pic").equals("un_s") || list.get(position).get("pic").equals("0")){
	    vHolder.pic.setImageResource(R.drawable.designer_unselect);
	}else{
	    vHolder.pic.setImageResource(R.drawable.select_pic);
	}
	
	vHolder.address.setText(list.get(position).get("dz"));
	return convertView;
    }

    class ViewHolder {
	ImageView pic;
	TextView address;
    }
}
