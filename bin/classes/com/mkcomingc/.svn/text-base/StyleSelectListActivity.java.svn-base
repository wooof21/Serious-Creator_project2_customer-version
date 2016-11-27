/**
 * 
 */
package com.mkcomingc;

import java.util.ArrayList;
import java.util.HashMap;

import login.LoginActivity;

import order.GroupOrderRequest;
import order.PostOrder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import designers.DesignersList;

import tools.Config;
import tools.CustomProgressDialog;
import tools.Exit;
import tools.Tools;
import adapter.StyleSelectListAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * @author Liming Chu
 * 
 * @param
 * @return
 */
public class StyleSelectListActivity extends BaseActivity {

    private ListView lv;

    private String type;

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */

    /*
     * (non-Javadoc)
     * 
     * @see com.mkcomingc.BaseActivity#onResume()
     */
    @Override
    protected void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.mkcomingc.BaseActivity#onPause()
     */
    @Override
    protected void onPause() {
	// TODO Auto-generated method stub
	super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.style_select_main);
	Exit.getInstance().addActivity(this);
	type = getIntent().getExtras().getString("type");

	findViewById(R.id.title_back).setOnClickListener(new OnClickListener() {

	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub
		finish();
	    }
	});

	lv = (ListView) findViewById(R.id.listView1);

	new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {

	    private CustomProgressDialog pd;
	    private ArrayList<HashMap<String, String>> list;

	    /*
	     * (non-Javadoc)
	     * 
	     * @see android.os.AsyncTask#onPreExecute()
	     */
	    @Override
	    protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		pd = CustomProgressDialog
			.createDialog(StyleSelectListActivity.this);
		pd.show();
		list = new ArrayList<HashMap<String, String>>();
	    }

	    @Override
	    protected ArrayList<HashMap<String, String>> doInBackground(
		    Void... params) {
		// TODO Auto-generated method stub
		String data = new Tools().getURL(Config.STYLE_TYPE_URL);
		System.out.println(data);

		try {
		    JSONObject jObject = new JSONObject(data);
		    JSONObject result = jObject.getJSONObject("result");
		    String code = result.getString("code");
		    if (code.equals("1")) {
			JSONArray jArray = jObject.getJSONArray("data");
			for (int i = 0, j = jArray.length(); i < j; i++) {
			    JSONObject job = jArray.optJSONObject(i);
			    HashMap<String, String> hashMap = new HashMap<String, String>();
			    hashMap.put("id", job.getString("id"));
			    hashMap.put("classname", job.getString("classname"));
			    hashMap.put("tjch", job.getString("tjch"));
			    hashMap.put("contents", job.getString("contents"));
			    hashMap.put("bgpic", job.getString("bgpic"));
			    hashMap.put("dds", job.getString("dds"));

			    list.add(hashMap);
			}
		    }
		} catch (JSONException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}

		return list;
	    }

	    /*
	     * (non-Javadoc)
	     * 
	     * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	     */
	    @Override
	    protected void onPostExecute(
		    final ArrayList<HashMap<String, String>> result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		pd.dismiss();
		if (result.size() != 0) {
		    StyleSelectListAdapter adapter = new StyleSelectListAdapter(
			    result, StyleSelectListActivity.this);
		    lv.setAdapter(adapter);
		    lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent,
				View view, int position, long id) {
			    // TODO Auto-generated method stub
			    if (new Tools()
				    .isUserLogin(StyleSelectListActivity.this)) {
				if (result.get(position).get("id").equals("19")) {
				    Intent intent = new Intent(
					    StyleSelectListActivity.this,
					    GroupOrderRequest.class);
				    intent.putExtra("zrid", result
					    .get(position).get("id"));
				    startActivity(intent);
				} else {
				    if (type.equals("yy")) {
					String _id = result.get(position).get(
						"id");
					String _name = result.get(position)
						.get("classname");
					Intent intent = new Intent(
						StyleSelectListActivity.this,
						DesignersList.class);
					intent.putExtra("id", _id);
					intent.putExtra("name", _name);
					intent.putExtra("type", "yy");
					startActivity(intent);

				    } else {
					String _id = result.get(position).get(
						"id");
					String _name = result.get(position)
						.get("classname");
					Intent intent = new Intent(
						StyleSelectListActivity.this,
						PostOrder.class);
					intent.putExtra("id", _id);
					intent.putExtra("name", _name);
					startActivity(intent);

				    }
				}
			    } else {
				Toast.makeText(StyleSelectListActivity.this,
					"«Îœ»µ«¬º£°", Toast.LENGTH_SHORT).show();
				startActivityForResult(new Intent(
					StyleSelectListActivity.this,
					LoginActivity.class), 2000);
			    }

			}
		    });
		} else {
		    Toast.makeText(StyleSelectListActivity.this,
			    "ªÒ»°◊±»›∑÷¿‡ ß∞‹£¨«Î∑µªÿ÷ÿ ‘£°", Toast.LENGTH_SHORT).show();
		}
	    }
	}.execute();

    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onActivityResult(int, int,
     * android.content.Intent)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	// TODO Auto-generated method stub
	super.onActivityResult(requestCode, resultCode, data);
	switch (resultCode) {
	case 1000:
	    
	    break;
	case 1001:
	    Toast.makeText(StyleSelectListActivity.this, "µ«¬Ω ß∞‹£°", Toast.LENGTH_SHORT)
		    .show();
	    break;
	default:
	    break;
	}
    }
}
