/**
 * 
 */
package vip;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tools.Config;
import tools.CustomProgressDialog;
import tools.Exit;
import tools.Tools;

import com.mkcomingc.BaseActivity;
import com.mkcomingc.R;

import adapter.RechargeRecordListAdapter;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Liming Chu
 * 
 * @param
 * @return
 */
public class RechargeRecord extends BaseActivity {

    private ImageView back;
    private TextView total;
    private ListView lv;
    
    private ArrayList<HashMap<String, String>> list;

    
    /* (non-Javadoc)
     * @see com.mkcomingc.BaseActivity#onResume()
     */
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }
    /* (non-Javadoc)
     * @see com.mkcomingc.BaseActivity#onPause()
     */
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }
    
    
    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.recharge_record);
	Exit.getInstance().addActivity(this);
	prepareView();
	
	new AsyncTask<Void, Void, String>() {
	    private CustomProgressDialog pd;
	    @Override
	    protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		pd = CustomProgressDialog.createDialog(RechargeRecord.this);
		pd.show();
		list = new ArrayList<HashMap<String,String>>();
	    }

	    @Override
	    protected String doInBackground(Void... params) {
		// TODO Auto-generated method stub
		String url = Config.RECHARGE_RECORD_URL + new Tools().getUserId(RechargeRecord.this);
		Log.e("url", url);
		String data = new Tools().getURL(url);
		System.out.println(data);
		String code = "";
		
		try {
		    JSONObject jObject = new JSONObject(data);
		    JSONObject result = jObject.getJSONObject("result");
		    code = result.getString("code");
		    if(code.equals("1")){
			JSONObject _data = jObject.getJSONObject("data");
			String czzje = _data.getString("czzje");
			Message msg = handler.obtainMessage();
			msg.obj = czzje;
			msg.sendToTarget();
			
			JSONArray jArray = _data.getJSONArray("czjl");
			for(int i=0,j=jArray.length();i<j;i++){
			    JSONObject job = jArray.optJSONObject(i);
			    HashMap<String, String> hashMap = new HashMap<String, String>();
			    hashMap.put("id", job.getString("id"));
			    hashMap.put("je", job.getString("je"));
			    hashMap.put("sj", job.getString("sj"));
			    hashMap.put("czfs", job.getString("czfs"));
			    
			    list.add(hashMap);
			}
		    }
		} catch (JSONException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		return code;
	    }

	    @Override
	    protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		pd.dismiss();
		if(result.equals("1")){
		    RechargeRecordListAdapter adapter = new RechargeRecordListAdapter(list, RechargeRecord.this);
		    lv.setAdapter(adapter);
		}else{
		    Toast.makeText(RechargeRecord.this, "ÎÞ³äÖµ¼ÇÂ¼£¡", Toast.LENGTH_SHORT).show();
		}
	    }
	}.execute();
    }

    private Handler handler = new Handler(){

	@Override
	public void handleMessage(Message msg) {
	    // TODO Auto-generated method stub
	    super.handleMessage(msg);
	    String total = (String) msg.obj;
	    RechargeRecord.this.total.setText("£¤"+total);
	}
	
    };
    private void prepareView() {
	back = (ImageView) findViewById(R.id.title_back);
	total = (TextView) findViewById(R.id.recharge_total);
	lv = (ListView) findViewById(R.id.recharge_record_lv);

	back.setOnClickListener(new OnClickListener() {

	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub
		finish();
	    }
	});
    }
}
