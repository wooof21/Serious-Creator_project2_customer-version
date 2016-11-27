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

import com.alipay.a.a.l;
import com.mkcomingc.BaseActivity;
import com.mkcomingc.R;

import adapter.RechargeCenterListAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
public class RechargeCenter extends BaseActivity implements OnClickListener {

    private ImageView back;
    private TextView balance;
    private TextView record;
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
	setContentView(R.layout.recharge_center);
	Exit.getInstance().addActivity(this);
	prepareView();

	new AsyncTask<Void, Void, String>() {
	    private CustomProgressDialog pd;

	    @Override
	    protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		pd = CustomProgressDialog.createDialog(RechargeCenter.this);
		pd.show();
		list = new ArrayList<HashMap<String,String>>();
	    }

	    @Override
	    protected String doInBackground(Void... params) {
		// TODO Auto-generated method stub
		String url = Config.RECHARGE_LIST_URL
			+ new Tools().getUserId(RechargeCenter.this);
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
			String balance = _data.getString("ye");
			Message msg = handler.obtainMessage();
			msg.what = 1;
			msg.obj = balance;
			msg.sendToTarget();
			
			JSONArray jArray = _data.getJSONArray("czk");
			for(int i=0,j=jArray.length();i<j;i++){
			    JSONObject job = jArray.optJSONObject(i);
			    HashMap<String, String> hashMap = new HashMap<String, String>();
			    hashMap.put("id", job.getString("id"));
			    hashMap.put("title", job.getString("title"));
			    hashMap.put("pic", job.getString("pic"));
			    hashMap.put("je", job.getString("je"));
			    hashMap.put("zsje", job.getString("zsje"));
			    
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
		    RechargeCenterListAdapter adapter = new RechargeCenterListAdapter(list, RechargeCenter.this);
		    lv.setAdapter(adapter);
		    lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent,
				View view, int position, long id) {
			    // TODO Auto-generated method stub
			    Intent intent = new Intent(RechargeCenter.this, Pay.class);
			    intent.putExtra("id", list.get(position).get("id"));
			    startActivity(intent);
			}
		    });
		}else{
		    Toast.makeText(RechargeCenter.this, "Œﬁ≥‰÷µ”≈ª›ø®£°", Toast.LENGTH_SHORT).show();
		}
	    }
	}.execute();
    }
    
    private Handler handler = new Handler(){

	@Override
	public void handleMessage(Message msg) {
	    // TODO Auto-generated method stub
	    super.handleMessage(msg);
	    switch (msg.what) {
	    case 1:
		String balance = (String) msg.obj;
		RechargeCenter.this.balance.setText("£§"+balance);
		break;

	    default:
		break;
	    }
	}
	
    };

    private void prepareView() {
	back = (ImageView) findViewById(R.id.title_back);
	balance = (TextView) findViewById(R.id.recharge_center_balance);
	record = (TextView) findViewById(R.id.recharge_center_record);
	lv = (ListView) findViewById(R.id.recharge_center_lv);

	back.setOnClickListener(this);
	record.setOnClickListener(this);
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    @Override
    public void onClick(View v) {
	// TODO Auto-generated method stub
	switch (v.getId()) {
	case R.id.title_back:
	    finish();
	    break;
	case R.id.recharge_center_record:
	    startActivity(new Intent(this, RechargeRecord.class));
	    break;

	default:
	    break;
	}
    }
}
