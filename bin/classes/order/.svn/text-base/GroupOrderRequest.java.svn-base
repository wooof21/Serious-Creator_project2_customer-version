/**
 * 
 */
package order;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import order.PostOrder.PopupWindowsAddr;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tools.Config;
import tools.CustomProgressDialog;
import tools.Exit;
import tools.Tools;
import view.MarqueeTV;

import com.mkcomingc.BaseActivity;
import com.mkcomingc.R;

import designers.DesignerInfo;
import designers.DesignerReserve;

import adapter.AddressListAdapter;
import adapter.ScheduleGVAdapter;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Liming Chu
 * 
 * @param
 * @return
 */
public class GroupOrderRequest extends BaseActivity implements OnClickListener {

    private ImageView back;
    private Spinner count;
    private LinearLayout level1;
    private ImageView selector1;
    private LinearLayout level2;
    private ImageView selector2;
    private LinearLayout level3;
    private ImageView selector3;
    private EditText phone;
    private RelativeLayout addressRL;
    private MarqueeTV address;
    private EditText where;
    private TextView total;
    private TextView pay;
    private TextView dt;
    private RelativeLayout timeRL;
    private Spinner spinner;
    private RelativeLayout parent;

    private String zrid, rs, date, date1, date2, date3, date4;

    private ArrayList<String> rsList;
    private HashMap<String, String> rsId;
    protected ArrayList<HashMap<String, String>> addrList, tList;

    
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
	setContentView(R.layout.group_order);
	Exit.getInstance().addActivity(this);
	prepareView();
	zrid = getIntent().getExtras().getString("zrid");

	new AsyncTask<String, Void, String>() {
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
		pd = CustomProgressDialog.createDialog(GroupOrderRequest.this);
		pd.show();
	    }

	    @Override
	    protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		String url = Config.GROUP_ORDER_MAIN_URL
			+ new Tools().getUserId(GroupOrderRequest.this)
			+ "&zrid=" + params[0];
		Log.e("url", url);
		String data = new Tools().getURL(url);
		System.err.println(data);
		String code = "";

		try {
		    JSONObject jObject = new JSONObject(data);
		    JSONObject result = jObject.getJSONObject("result");
		    code = result.getString("code");
		    if (code.equals("1")) {
			JSONObject _data = jObject.getJSONObject("data");
			String mrdz = _data.getString("mrdz");
			Message msg1 = handler.obtainMessage();
			msg1.what = 1;
			msg1.obj = mrdz;
			msg1.sendToTarget();

			JSONArray rslist = _data.getJSONArray("rslist");
			rsId = new HashMap<String, String>();
			rsList = new ArrayList<String>();
			for (int i = 0, j = rslist.length(); i < j; i++) {
			    JSONObject job1 = rslist.optJSONObject(i);
			    rsList.add(job1.getString("rs1"));
			    rsId.put(job1.getString("rs1"),
				    job1.getString("rs"));
			}
			Message msg2 = handler.obtainMessage();
			msg2.what = 2;
			msg2.sendToTarget();

			JSONArray cydz = _data.getJSONArray("cydz");
			addrList = new ArrayList<HashMap<String, String>>();
			for (int i = 0, j = cydz.length(); i < j; i++) {
			    JSONObject job2 = cydz.optJSONObject(i);
			    HashMap<String, String> hashMap2 = new HashMap<String, String>();
			    hashMap2.put("id", job2.getString("id"));
			    hashMap2.put("pic", "0");
			    hashMap2.put("dz", job2.getString("dz"));

			    addrList.add(hashMap2);
			}
			date1 = _data.getString("tian1");
			date2 = _data.getString("tian2");
			date3 = _data.getString("tian3");
			date4 = _data.getString("tian4");

			JSONArray jArray = _data.getJSONArray("shijian");
			tList = new ArrayList<HashMap<String, String>>();
			for (int i = 0, j = jArray.length(); i < j; i++) {
			    JSONObject job3 = jArray.optJSONObject(i);
			    HashMap<String, String> hashMap = new HashMap<String, String>();
			    hashMap.put("dian", job3.getString("dian"));
			    hashMap.put("zt", job3.getString("zt"));

			    tList.add(hashMap);
			}

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
	    protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		pd.dismiss();
	    }
	}.execute(zrid);
    }

    private Handler handler = new Handler() {

	@Override
	public void handleMessage(Message msg) {
	    // TODO Auto-generated method stub
	    super.handleMessage(msg);
	    switch (msg.what) {
	    case 1:
		String addr = (String) msg.obj;
		address.setText(addr);
		break;
	    case 2:
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
			GroupOrderRequest.this, R.layout.area_spinner_tv,
			R.id.text, rsList);
		adapter.setDropDownViewResource(R.layout.area_spinner_dropdown);
		spinner.setAdapter(adapter);
		rs = rsId.get(rsList.get(0));
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

		    @Override
		    public void onItemSelected(AdapterView<?> parent,
			    View view, int position, long id) {
			// TODO Auto-generated method stub
			rs = rsId.get(rsList.get(position));
			Log.e("rs", rs);
		    }

		    @Override
		    public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			rs = rsId.get(rsList.get(0));
		    }
		});
		break;
	    default:
		break;
	    }
	}

    };

    class PopupWindowsAddr extends PopupWindow {

	private AddressListAdapter adapter;

	public PopupWindowsAddr(Context mContext, View parent) {

	    View view = View.inflate(mContext, R.layout.dialog_view_address,
		    null);
	    view.startAnimation(AnimationUtils.loadAnimation(mContext,
		    R.anim.push_bottom_in_2));
	    ImageView back = (ImageView) view.findViewById(R.id.title_back);
	    // TextView add = (TextView)
	    // view.findViewById(R.id.dialog_view_add);
	    ListView lv = (ListView) view.findViewById(R.id.dialog_view_lv);
	    LinearLayout submit = (LinearLayout) view
		    .findViewById(R.id.dialog_view_submit);
	    // lv.startAnimation(AnimationUtils.loadAnimation(mContext,
	    // R.anim.push_bottom_in_2));

	    setWidth(LayoutParams.FILL_PARENT);
	    setHeight(LayoutParams.WRAP_CONTENT);
	    setBackgroundDrawable(new BitmapDrawable());
	    setFocusable(true);
	    setOutsideTouchable(true);
	    setContentView(view);
	    showAtLocation(parent, Gravity.CENTER, 0, 0);
	    update();

	    adapter = new AddressListAdapter(addrList, GroupOrderRequest.this);
	    lv.setAdapter(adapter);
	    lv.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view,
			int position, long id) {
		    // TODO Auto-generated method stub
		    Message msg = handler.obtainMessage();
		    msg.what = 1;
		    if (addrList.get(position).get("pic").equals("1")) {
			String addr = addrList.get(position).get("dz");
			msg.obj = addr;
			msg.sendToTarget();
			dismiss();
		    } else {
			for (HashMap<String, String> map : addrList) {
			    map.put("pic", "0");
			}
			addrList.get(position).put("pic", "1");
			adapter.notifyDataSetChanged();

			String addr = addrList.get(position).get("dz");

			msg.obj = addr;
			msg.sendToTarget();
			dismiss();
		    }
		}
	    });

	    back.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
		    // TODO Auto-generated method stub
		    dismiss();
		}
	    });

	    // add.setOnClickListener(new OnClickListener() {
	    //
	    // @Override
	    // public void onClick(View v) {
	    // // TODO Auto-generated method stub
	    // Intent intent = new Intent(DesignerReserve.this,
	    // AddAddressPopUp.class);
	    // intent.putExtra("areaMap", areaList);
	    // startActivityForResult(intent, 100);
	    // }
	    // });

	    submit.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
		    // TODO Auto-generated method stub

		}
	    });

	}

    }

    class PopupWindows extends PopupWindow {

	public PopupWindows(Context mContext, View parent) {

	    View view = View.inflate(mContext, R.layout.my_time_setting, null);
	    view.startAnimation(AnimationUtils.loadAnimation(mContext,
		    R.anim.push_bottom_in_2));
	    final TextView tv1 = (TextView) view
		    .findViewById(R.id.my_time_date1);
	    final TextView tv2 = (TextView) view
		    .findViewById(R.id.my_time_date2);
	    final TextView tv3 = (TextView) view
		    .findViewById(R.id.my_time_date3);
	    final TextView tv4 = (TextView) view
		    .findViewById(R.id.my_time_date4);
	    final GridView gv = (GridView) view.findViewById(R.id.my_time_gv);
	    // lv.startAnimation(AnimationUtils.loadAnimation(mContext,
	    // R.anim.push_bottom_in_2));
	    tv1.setText(date1);
	    tv2.setText(date2);
	    tv3.setText(date3);
	    tv4.setText(date4);
	    tv1.setBackgroundColor(Color.rgb(240, 240, 240));
	    tv2.setBackgroundColor(Color.rgb(255, 255, 255));
	    tv3.setBackgroundColor(Color.rgb(255, 255, 255));
	    tv4.setBackgroundColor(Color.rgb(255, 255, 255));

	    ScheduleGVAdapter adapter = new ScheduleGVAdapter(tList,
		    GroupOrderRequest.this);
	    gv.setAdapter(adapter);
	    gv.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view,
			int position, long id) {
		    // TODO Auto-generated method stub
		    String t = tList.get(position).get("dian");
		    dt.setText(date + " " + t);
		    dismiss();
		}
	    });

	    setWidth(LayoutParams.FILL_PARENT);
	    setHeight(LayoutParams.WRAP_CONTENT);
	    setBackgroundDrawable(new BitmapDrawable());
	    setFocusable(true);
	    setOutsideTouchable(true);
	    setContentView(view);
	    showAtLocation(parent, Gravity.BOTTOM, 0, -100);
	    update();

	    tv1.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
		    // TODO Auto-generated method stub
		    tv1.setBackgroundColor(Color.rgb(240, 240, 240));
		    tv2.setBackgroundColor(Color.rgb(255, 255, 255));
		    tv3.setBackgroundColor(Color.rgb(255, 255, 255));
		    tv4.setBackgroundColor(Color.rgb(255, 255, 255));
		    date = date1;

		}
	    });
	    tv2.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
		    // TODO Auto-generated method stub
		    tv1.setBackgroundColor(Color.rgb(255, 255, 255));
		    tv2.setBackgroundColor(Color.rgb(240, 240, 240));
		    tv3.setBackgroundColor(Color.rgb(255, 255, 255));
		    tv4.setBackgroundColor(Color.rgb(255, 255, 255));
		    date = date2;
		}
	    });
	    tv3.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
		    // TODO Auto-generated method stub
		    tv1.setBackgroundColor(Color.rgb(255, 255, 255));
		    tv2.setBackgroundColor(Color.rgb(255, 255, 255));
		    tv3.setBackgroundColor(Color.rgb(240, 240, 240));
		    tv4.setBackgroundColor(Color.rgb(255, 255, 255));
		    date = date3;
		}
	    });
	    tv4.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
		    // TODO Auto-generated method stub
		    tv1.setBackgroundColor(Color.rgb(255, 255, 255));
		    tv2.setBackgroundColor(Color.rgb(255, 255, 255));
		    tv3.setBackgroundColor(Color.rgb(255, 255, 255));
		    tv4.setBackgroundColor(Color.rgb(240, 240, 240));
		    date = date4;
		}
	    });

	}

    }

    private void prepareView() {
	back = (ImageView) findViewById(R.id.title_back);
	level1 = (LinearLayout) findViewById(R.id.group_order_gaoji_ll);
	selector1 = (ImageView) findViewById(R.id.group_order_gaoji_iv);
	level2 = (LinearLayout) findViewById(R.id.group_order_teji_ll);
	selector1 = (ImageView) findViewById(R.id.group_order_teji_iv);
	level3 = (LinearLayout) findViewById(R.id.group_order_mingxing_ll);
	selector1 = (ImageView) findViewById(R.id.group_order_mingxing_iv);
	phone = (EditText) findViewById(R.id.group_order_phone);
	addressRL = (RelativeLayout) findViewById(R.id.group_order_address_rl);
	address = (MarqueeTV) findViewById(R.id.group_order_address);
	where = (EditText) findViewById(R.id.group_order_where);
	total = (TextView) findViewById(R.id.group_order_total);
	pay = (TextView) findViewById(R.id.group_order_pay);
	dt = (TextView) findViewById(R.id.group_order_dt);
	timeRL = (RelativeLayout) findViewById(R.id.group_order_time_rl);
	spinner = (Spinner) findViewById(R.id.group_order_spinner);
	parent = (RelativeLayout) findViewById(R.id.group_order_parent);

	back.setOnClickListener(this);
	addressRL.setOnClickListener(this);
	timeRL.setOnClickListener(this);
	pay.setOnClickListener(this);
	level1.setOnClickListener(this);
	level2.setOnClickListener(this);
	level3.setOnClickListener(this);

	rs = "";
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
	case R.id.group_order_time_rl:
	    new PopupWindows(this, parent);
	    date = date1;
	    break;
	case R.id.group_order_address_rl:
	    new PopupWindowsAddr(this, parent);
	    break;
	case R.id.group_order_pay:
	    if (validation()) {
		new AsyncTask<Void, Void, String>() {
		    private CustomProgressDialog pd;

		    @Override
		    protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = CustomProgressDialog
				.createDialog(GroupOrderRequest.this);
			pd.show();
		    }

		    @Override
		    protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			String url = Config.GROUP_ORDER_POST_URL
				+ new Tools().getUserId(GroupOrderRequest.this)
				+ getOrder();
			Log.e("url", url);
			String data = new Tools().getURL(url);
			System.out.println(data);
			String code = "";

			try {
			    JSONObject job = new JSONObject(data);
			    JSONObject result = job.getJSONObject("result");
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
			pd.dismiss();
			if (result.equals("1")) {
			    Toast.makeText(GroupOrderRequest.this, "发布订单成功！",
				    Toast.LENGTH_SHORT).show();
			    finish();
			} else {
			    Toast.makeText(GroupOrderRequest.this,
				    "发布订单失败，请重试！", Toast.LENGTH_SHORT).show();
			}
		    }

		}.execute();
	    }
	    break;
	default:
	    break;
	}
    }

    private Long convertTime(String dt) {
	String d = dt.split(" ")[0].substring(0, dt.split(" ")[0].length() - 3);
	String t = dt.split(" ")[1].substring(0, dt.split(" ")[1].length() - 1);
	Log.e("d", d);
	Log.e("t", t);
	Calendar c = Calendar.getInstance();
	int yr = c.get(Calendar.YEAR);
	Log.e("yr", yr + "");
	String dm = d.split("\\.")[0];
	String dd = d.split("\\.")[1];
	String _date = yr + "-" + dm + "-" + dd;
	Log.e("date", _date);
	String time = t + ":00:00";
	Log.e("time", time);

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	sdf.setTimeZone(TimeZone.getTimeZone("GMT+08"));
	java.util.Date _d = null;

	try {
	    _d = sdf.parse(_date + " " + time);
	} catch (ParseException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	return _d.getTime() / 1000;
    }

    private boolean validation() {
	if (phone.getText().toString().length() == 0) {
	    Toast.makeText(this, "请填写电话！", Toast.LENGTH_SHORT).show();
	    return false;
	} else if (phone.getText().toString().length() != 11) {
	    Toast.makeText(this, "请填写正确的11位电话！", Toast.LENGTH_SHORT).show();
	    return false;
	} else if (dt.getText().toString().contains("选择")) {
	    Toast.makeText(this, "请选择服务时间！", Toast.LENGTH_SHORT).show();
	    return false;
	} else if (address.getText().toString().length() == 0) {
	    Toast.makeText(this, "请选择服务地址！", Toast.LENGTH_SHORT).show();
	    return false;
	} else if (where.getText().toString().length() == 0) {
	    Toast.makeText(this, "请填写用妆场合！", Toast.LENGTH_SHORT).show();
	    return false;
	} else {
	    return true;
	}
    }

    private String getOrder() {
	String dz_utf8 = "", ch_utf8 = "";

	try {
	    dz_utf8 = URLEncoder.encode(address.getText().toString(), "utf-8");
	    ch_utf8 = URLEncoder.encode(where.getText().toString(), "utf-8");
	} catch (UnsupportedEncodingException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	StringBuilder sb = new StringBuilder();
	sb.append("&dh=");
	sb.append(phone.getText().toString());
	sb.append("&rs=");
	sb.append(rs);
	sb.append("&dz=");
	sb.append(dz_utf8);
	sb.append("&ch=");
	sb.append(ch_utf8);
	sb.append("&yysj=");
	sb.append(convertTime(dt.getText().toString()));

	return sb.toString();
    }
}
