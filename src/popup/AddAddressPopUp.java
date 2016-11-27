/**
 * 
 */
package popup;

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

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Liming Chu
 * 
 * @param
 * @return
 */
public class AddAddressPopUp extends BaseActivity implements OnClickListener {

    private ImageView back;
    private Spinner spinner1, spinner2;
    private EditText et;
    private TextView submit;

    private ArrayList<HashMap<String, String>> area;
    private ArrayList<String> areaList;
    private ArrayList<HashMap<String, String>> street;
    private String areaId, strretId;

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

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.dialog_view_add_address);
	Exit.getInstance().addActivity(this);
	prepareView();

	area = (ArrayList<HashMap<String, String>>) getIntent().getExtras()
		.getSerializable("areaMap");
	areaList = new ArrayList<String>();
	for (HashMap<String, String> map : area) {
	    areaList.add(map.get("classname"));
	}
	areaId = area.get(0).get("id");
	new StreetAsync().execute(areaId);
	Log.e("id", areaId);
	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		R.layout.area_spinner_tv, R.id.text, areaList);
	adapter.setDropDownViewResource(R.layout.area_spinner_dropdown);
	spinner1.setAdapter(adapter);
	spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {

	    @Override
	    public void onItemSelected(AdapterView<?> parent, View view,
		    int position, long id) {
		// TODO Auto-generated method stub
		System.out.println(area.get(position).get("id"));
		areaId = area.get(position).get("id");
		new StreetAsync().execute(areaId);
	    }

	    @Override
	    public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	    }
	});

    }

    class StreetAsync extends AsyncTask<String, Void, ArrayList<String>> {
	private CustomProgressDialog pd;

	@Override
	protected void onPreExecute() {
	    // TODO Auto-generated method stub
	    super.onPreExecute();
	    pd = CustomProgressDialog.createDialog(AddAddressPopUp.this);
	    pd.show();
	    street = new ArrayList<HashMap<String, String>>();
	}

	@Override
	protected ArrayList<String> doInBackground(String... params) {
	    // TODO Auto-generated method stub
	    String url = Config.ADDRESS_STREET_URL + params[0];
	    Log.e("url", url);
	    String data = new Tools().getURL(url);
	    System.out.println(data);
	    ArrayList<String> s_list = new ArrayList<String>();
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

			s_list.add(job.getString("classname"));

			street.add(hashMap);

		    }
		    strretId = street.get(0).get("id");
		    Log.e("street id", strretId);
		} else {
		    Message msg = handler.obtainMessage();
		    msg.sendToTarget();
		}
	    } catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    return s_list;
	}

	@Override
	protected void onPostExecute(ArrayList<String> result) {
	    // TODO Auto-generated method stub
	    super.onPostExecute(result);
	    pd.dismiss();
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
		    AddAddressPopUp.this, R.layout.area_spinner_tv, R.id.text,
		    result);
	    adapter.setDropDownViewResource(R.layout.area_spinner_dropdown);
	    spinner2.setAdapter(adapter);
	    spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
			int position, long id) {
		    // TODO Auto-generated method stub
		    if (street.size() != 0) {
			System.out.println(street.get(position).get("id"));
		    }
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
		    // TODO Auto-generated method stub

		}
	    });
	}
    }

    private Handler handler = new Handler() {

	@Override
	public void handleMessage(Message msg) {
	    // TODO Auto-generated method stub
	    super.handleMessage(msg);
	    Toast.makeText(AddAddressPopUp.this, "Œﬁ ˝æ›£°", Toast.LENGTH_SHORT)
		    .show();
	}

    };

    private void prepareView() {
	back = (ImageView) findViewById(R.id.title_back);
	spinner1 = (Spinner) findViewById(R.id.add_addr_spinner1);
	spinner2 = (Spinner) findViewById(R.id.add_addr_spinner2);
	et = (EditText) findViewById(R.id.add_addr_et);
	submit = (TextView) findViewById(R.id.add_addr_submit);

	back.setOnClickListener(this);
	submit.setOnClickListener(this);
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
	    setResult(404);
	    finish();
	    break;
	case R.id.add_addr_submit:
	    if (et.getText().toString().length() == 0) {
		Toast.makeText(this, "«ÎÃÓ–¥œÍœ∏µÿ÷∑£°", Toast.LENGTH_SHORT).show();
		return;
	    } else {
		String addr_utf8 = "";
		try {
		    addr_utf8 = URLEncoder.encode(et.getText().toString(),
			    "utf-8");
		} catch (UnsupportedEncodingException e1) {
		    // TODO Auto-generated catch block
		    e1.printStackTrace();
		}
		new AsyncTask<String, Void, String>() {
		    // private CustomProgressDialog pd;

		    @Override
		    protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			// pd = CustomProgressDialog
			// .createDialog(AddAddressPopUp.this);
			// pd.show();
		    }

		    @Override
		    protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String url = Config.ADDRESS_ADD_URL
				+ new Tools().getUserId(AddAddressPopUp.this)
				+ "&city2=" + params[0] + "&city3=" + params[1]
				+ "&dz=" + params[2];
			Log.e("url", url);
			String data = new Tools().getURL(url);
			System.out.println(data);
			String code = "";
			try {
			    JSONObject jObject = new JSONObject(data);
			    JSONObject result = jObject.getJSONObject("result");
			    code = result.getString("code");
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
			// pd.dismiss();
			if (result.equals("1")) {
			    setResult(101);
			    AddAddressPopUp.this.finish();
			} else {
			    Toast.makeText(AddAddressPopUp.this, "ÃÌº”µÿ÷∑ ß∞‹£¨«Î÷ÿ ‘£°",
				    Toast.LENGTH_SHORT).show();
			}
		    }

		}.execute(areaId, strretId, addr_utf8);
	    }
	    break;
	default:
	    break;
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onTouchEvent(android.view.MotionEvent)
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
	// TODO Auto-generated method stub
	return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onBackPressed()
     */
    @Override
    public void onBackPressed() {
	// TODO Auto-generated method stub
	return;
    }
}
