/**
 * 
 */
package vip;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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

import adapter.BrandAreaListAdapter;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author Liming Chu
 *
 * @param
 * @return
 */
public class MyResume extends BaseActivity implements OnClickListener{

    private ImageView back;
    private EditText city, name, phone, yr, salary;
    private ListView lv1, lv2;
    private TextView submit;

    private ArrayList<HashMap<String, String>> list1, list2;
    private BrandAreaListAdapter adapter1, adapter2;
    private ArrayList<String> names;
    
    
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
    
    
    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_resume);
	Exit.getInstance().addActivity(this);
        prepareView();
        
	new AsyncTask<Void, Void, String>() {

	    private CustomProgressDialog pd;

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
			.createDialog(MyResume.this);
		pd.show();
		list1 = new ArrayList<HashMap<String, String>>();
		list2 = new ArrayList<HashMap<String, String>>();
	    }

	    @Override
	    protected String doInBackground(
		    Void... params) {
		// TODO Auto-generated method stub
		String data = new Tools().getURL(Config.STYLE_TYPE_URL);
		System.out.println(data);
		String code = "";
		try {
		    JSONObject jObject = new JSONObject(data);
		    JSONObject result = jObject.getJSONObject("result");
		    code = result.getString("code");
		    if (code.equals("1")) {
			JSONArray jArray = jObject.getJSONArray("data");
			for (int i = 0, j = jArray.length(); i < j; i++) {
			    JSONObject job = jArray.optJSONObject(i);
			    HashMap<String, String> hashMap = new HashMap<String, String>();
			    hashMap.put("id", job.getString("id"));
			    hashMap.put("classname", job.getString("classname"));
			    hashMap.put("iv_status", "0");

			    if (i < jArray.length() / 2) {
				list1.add(hashMap);
			    } else {
				list2.add(hashMap);
			    }
			}
			
			Message msg1 = handler.obtainMessage();
			msg1.what = 1;
			handler.sendMessage(msg1);

			Message msg2 = handler.obtainMessage();
			msg2.what = 2;
			handler.sendMessage(msg2);
		    }
		} catch (JSONException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}

		return code;
	    }

	    /*
	     * (non-Javadoc)
	     * 
	     * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	     */
	    @Override
	    protected void onPostExecute(
		    final String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		pd.dismiss();
		if (!result.equals("1")) {
		    Toast.makeText(MyResume.this,
			    "获取妆容分类失败，请返回重试！", Toast.LENGTH_SHORT).show();
		    MyResume.this.finish();
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
		adapter1 = new BrandAreaListAdapter(list1, MyResume.this);
		lv1.setAdapter(adapter1);
		lv1.setOnItemClickListener(new OnItemClickListener() {

		    @Override
		    public void onItemClick(AdapterView<?> arg0, View arg1,
			    int arg2, long arg3) {
			// TODO Auto-generated method stub
			if (names.size() == 0) {
			    names.add(list1.get(arg2).get("classname"));
			    list1.get(arg2).put("iv_status", "1");
			    adapter1.notifyDataSetChanged();
			} else {
			    if (names.contains(list1.get(arg2).get("classname"))) {
				list1.get(arg2).put("iv_status", "0");
				names.remove(list1.get(arg2).get("classname"));
				adapter1.notifyDataSetChanged();
			    } else {
				names.add(list1.get(arg2).get("classname"));
				list1.get(arg2).put("iv_status", "1");
				adapter1.notifyDataSetChanged();
			    }
			}
		    }
		});
		break;
	    case 2:
		adapter2 = new BrandAreaListAdapter(list2, MyResume.this);
		lv2.setAdapter(adapter2);
		lv2.setOnItemClickListener(new OnItemClickListener() {

		    @Override
		    public void onItemClick(AdapterView<?> arg0, View arg1,
			    int arg2, long arg3) {
			// TODO Auto-generated method stub
			if (names.size() == 0) {
			    names.add(list2.get(arg2).get("classname"));
			    list2.get(arg2).put("iv_status", "1");
			    adapter2.notifyDataSetChanged();
			} else {
			    if (names.contains(list2.get(arg2).get("classname"))) {
				list2.get(arg2).put("iv_status", "0");
				names.remove(list2.get(arg2).get("classname"));
				adapter2.notifyDataSetChanged();
			    } else {
				names.add(list2.get(arg2).get("classname"));
				list2.get(arg2).put("iv_status", "1");
				adapter2.notifyDataSetChanged();
			    }
			}
		    }
		});
		break;
	    case 100:
		String text = (String) msg.obj;
		Toast.makeText(MyResume.this,
			    text, Toast.LENGTH_SHORT).show();
		break;
	    default:
		break;
	    }
	}
	
    };
    
    private void prepareView(){
	back = (ImageView) findViewById(R.id.title_back);
	city = (EditText) findViewById(R.id.my_resume_city);
	name = (EditText) findViewById(R.id.my_resume_name);
	phone = (EditText) findViewById(R.id.my_resume_phone);
	yr = (EditText) findViewById(R.id.my_resume_yrs);
	salary = (EditText) findViewById(R.id.my_resume_salary);
	lv1 = (ListView) findViewById(R.id.my_resume_lv1);
	lv2 = (ListView) findViewById(R.id.my_resume_lv2);
	submit = (TextView) findViewById(R.id.my_resume_submit);
	
	back.setOnClickListener(this);
	submit.setOnClickListener(this);
	
	names = new ArrayList<String>();
    }

    /* (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    @Override
    public void onClick(View v) {
	// TODO Auto-generated method stub
	switch (v.getId()) {
	case R.id.title_back:
	    finish();
	    break;
	case R.id.my_resume_submit:
	    if(validation()){
		new AsyncTask<Void, Void, String>(){
		    private CustomProgressDialog pd;
		    @Override
		    protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = CustomProgressDialog.createDialog(MyResume.this);
			pd.show();
		    }

		    @Override
		    protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			String url = Config.RESUME_URL + new Tools().getUserId(MyResume.this) + getOrder();
			Log.e("url", url);
			String data = new Tools().getURL(url);
			System.out.println(data);
			String code = "";
			
			try {
			    JSONObject job = new JSONObject(data);
			    JSONObject result = job.getJSONObject("result");
			    code = result.getString("code");
			    if(code.equals("1")){
				Message msg = handler.obtainMessage();
				msg.what = 100;
				msg.obj = job.getString("msg");
				msg.sendToTarget();
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
			    finish();
			}else {
			    Toast.makeText(MyResume.this, "提交失败，请重试！", Toast.LENGTH_SHORT).show();
			}
		    }
		    
		}.execute();
	    }
	    break;
	default:
	    break;
	}
    }
    private String getOrder(){
	String zr = "";
	for(String s : names){
	    zr += s + ",";
	}
	System.out.println("zr ---> " + zr.substring(0, zr.length()-1));
	String city_utf8 = "", name_utf8 = "", yr_utf8 = "", salary_utf8 = "", zr_utf8 = "";
	try {
	    city_utf8 = URLEncoder.encode(city.getText().toString(), "utf-8");
	    name_utf8 = URLEncoder.encode(name.getText().toString(), "utf-8");
	    yr_utf8 = URLEncoder.encode(yr.getText().toString(), "utf-8");
	    salary_utf8 = URLEncoder.encode(salary.getText().toString(), "utf-8");
	    zr_utf8 = URLEncoder.encode(zr.substring(0, zr.length()-1), "utf-8");
	} catch (UnsupportedEncodingException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	StringBuilder sb = new StringBuilder();
	sb.append("&city=");
	sb.append(city_utf8);
	sb.append("&name=");
	sb.append(name_utf8);
	sb.append("&tel=");
	sb.append(phone.getText().toString());
	sb.append("&gznx=");
	sb.append(yr_utf8);
	sb.append("&qwyx=");
	sb.append(salary_utf8);
	sb.append("&sczl=");
	sb.append(zr_utf8);
	
	System.out.println(sb.toString());
	
	return sb.toString();
	
    }
    private boolean validation(){
	if(city.getText().toString().length() == 0){
	    Toast.makeText(this, "请填写城市！", Toast.LENGTH_SHORT).show();
	    return false;
	}else if(name.getText().toString().length() == 0){
	    Toast.makeText(this, "请填写姓名！", Toast.LENGTH_SHORT).show();
	    return false;
	}else if(phone.getText().toString().length() == 0){
	    Toast.makeText(this, "请填写电话！", Toast.LENGTH_SHORT).show();
	    return false;
	}else if(phone.getText().toString().length() != 11){
	    Toast.makeText(this, "请填写正确的11位电话！", Toast.LENGTH_SHORT).show();
	    return false;
	}else if(yr.getText().toString().length() == 0){
	    Toast.makeText(this, "请填写工作年限！", Toast.LENGTH_SHORT).show();
	    return false;
	}else if(salary.getText().toString().length() == 0){
	    Toast.makeText(this, "请填写期望工资！", Toast.LENGTH_SHORT).show();
	    return false;
	}else if(names.size() == 0){
	    Toast.makeText(this, "请选择擅长妆容！", Toast.LENGTH_SHORT).show();
	    return false;
	}else{
	    return true;
	}
    }
}
