/**
 * 
 */
package adapter;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.mkcomingc.R;

import tools.Config;
import tools.CustomProgressDialog;
import tools.ScrollListView;
import tools.Tools;
import view.MGridView;

import model.MyWorkListModel;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Liming Chu
 * 
 * @param
 * @return
 */
public class MyWorkListViewAdapter extends BaseAdapter {

    private ArrayList<MyWorkListModel> list;
    private Context context;
    private LayoutInflater lInflater;
    private MyWorkGridViewAdapter adapter;
    private CustomProgressDialog pd;
    private HashMap<String, String> picId;

    public MyWorkListViewAdapter(ArrayList<MyWorkListModel> list,
	    Context context, HashMap<String, String> picId) {
	super();
	this.list = list;
	this.context = context;
	this.picId = picId;
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
	    convertView = lInflater.inflate(R.layout.my_work_lv_item, null);
	}

	vHolder.title = (TextView) convertView
		.findViewById(R.id.my_work_lv_item_name);
	vHolder.gv = (MGridView) convertView
		.findViewById(R.id.my_work_lv_item_gv);
	vHolder.gv.clearFocus();
	final MyWorkListModel model = list.get(position);
	vHolder.title.setText(model.getTitle());

	System.out.println("size " + model.getPicAddr().size());
	adapter = new MyWorkGridViewAdapter(model.getPicAddr(), context);
	vHolder.gv.setAdapter(adapter);

	return convertView;
    }

    class ViewHolder {
	TextView title;
	MGridView gv;
    }
}
