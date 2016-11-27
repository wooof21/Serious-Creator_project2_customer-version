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

import adapter.MyCouponListAdapter;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * @author Liming Chu
 * 
 * @param
 * @return
 */
public class MyCoupon extends BaseActivity {

    private ImageView back;
    private ListView lv;

    
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
	setContentView(R.layout.my_coupon);
	Exit.getInstance().addActivity(this);
	prepareView();
	
	new AsyncTask<Void, Void, String>() {
	    private CustomProgressDialog pd;
	    private ArrayList<HashMap<String, String>> list;
	    /* (non-Javadoc)
	     * @see android.os.AsyncTask#onPreExecute()
	     */
	    @Override
	    protected void onPreExecute() {
	        // TODO Auto-generated method stub
	        super.onPreExecute();
	        pd = CustomProgressDialog.createDialog(MyCoupon.this);
	        pd.show();
	    }
	    @Override
	    protected String doInBackground(Void... params) {
		// TODO Auto-generated method stub
		String url = Config.VIP_COUPON_LIST_URL + new Tools().getUserId(MyCoupon.this);
		Log.e("url", url);
		String data = new Tools().getURL(url);
		System.out.println(data);
		String code = "";
		list = new ArrayList<HashMap<String,String>>();
		try {
		    JSONObject jObject = new JSONObject(data);
		    JSONObject result = jObject.getJSONObject("result");
		    code = result.getString("code");
		    if(code.equals("1")){
			JSONArray jArray = jObject.getJSONArray("data");
			for(int i=0,j=jArray.length();i<j;i++){
			    JSONObject job = jArray.optJSONObject(i);
			    HashMap<String, String> hashMap = new HashMap<String, String>();
			    hashMap.put("id", job.getString("id"));
			    hashMap.put("je", job.getString("je"));
			    hashMap.put("pic", job.getString("pic"));
			    
			    list.add(hashMap);
			}
		    }
		} catch (JSONException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		return code;
	    }
	    /* (non-Javadoc)
	     * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	     */
	    @Override
	    protected void onPostExecute(String result) {
	        // TODO Auto-generated method stub
	        super.onPostExecute(result);
	        pd.dismiss();
	        if(result.equals("0")){
	            Toast.makeText(MyCoupon.this, "√ª”–”≈ª›»Ø£°", Toast.LENGTH_SHORT).show();
	            //finish();
	        }else{
	            MyCouponListAdapter adapter = new MyCouponListAdapter(list, MyCoupon.this);
	            lv.setAdapter(adapter);
	        }
	    }
	}.execute();
    }

    private void prepareView() {
	back = (ImageView) findViewById(R.id.title_back);
	lv = (ListView) findViewById(R.id.my_coupon_lv);

	back.setOnClickListener(new OnClickListener() {

	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub
		finish();
	    }
	});
    }
}
