/**
 * 
 */
package designers;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;

import login.LoginActivity;

import order.InsuranceView;
import order.OrderListMain;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import popup.AddAddressPopUp;

import tools.Config;
import tools.CustomProgressDialog;
import tools.Exit;
import tools.Tools;
import view.MarqueeTV;
import vip.MyRegularInfo;

import com.alipay.sdk.app.PayTask;
import com.mkcomingc.BaseActivity;
import com.mkcomingc.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import designers.DesignerReserve.PayAsync;

import adapter.AddressListAdapter;
import adapter.MyCouponListAdapter;
import adapter.ScheduleGVAdapter;
import alipay.Keys;
import alipay.PayResult;
import alipay.SignUtils;
import android.R.menu;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
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
public class DesignerReserve1 extends BaseActivity implements OnClickListener {

    private ImageView back;
    private ImageView pic;
    private TextView name;
    private TextView style;
    private TextView price;
    private TextView timeCount;
    private TextView keep;
    private RelativeLayout phoneRL;
    private TextView phone;
    private RelativeLayout addressRL;
    private MarqueeTV address;
    private RelativeLayout remarkRL;
    private TextView remark;
    private RelativeLayout couponRL;
    private TextView coupon;
    private ImageView _switch;
    private LinearLayout myNameLL;
    private EditText myName;
    private LinearLayout myIdLL, balance_ll;
    private EditText myID;
    private TextView protocol;
    private TextView total;
    private TextView left;
    private TextView pay;

    private Spinner styleSpinner;
    private TextView dt;
    private EditText phone_et;
    private ImageView address_iv;
    private TextView style_tv;
    private EditText remark_et;
    private ImageView coupon_iv;
    private LinearLayout name_submit_ll;
    private TextView submit_name, submit_id;
    private RelativeLayout balance_rl, alipay_rl;
    private ImageView balance_select_iv, alipay_select_iv;
    private RelativeLayout parent;

    private TextView addrTitle;
    private RelativeLayout addr_rl2;
    private EditText addr_et2;

    private TextView bx_tv1;
    private LinearLayout bx_ll2;

    protected String phoneNo;
    protected String reMark;

    private ArrayList<HashMap<String, String>> addrList, areaList, yhqList;
    public String zid, zrid, _name, type, sqId, addrSel, addr, oid;

    private String yhName, yhId, fgId, yhqStatus, totalPay, bxStatus,
	    payMethod, balance, style_text, addr_validation, payButton;

    private HashMap<String, String> fgIdMap;

    private long timeStamp;

    private String orderNum;

    private String alipayOrderInfo;

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
	setContentView(R.layout.adapter_order_submit);
	Exit.getInstance().addActivity(this);
	prepareView();

	new InfoAsync().execute(oid);

    }

    private void prepareView() {
	back = (ImageView) findViewById(R.id.title_back);
	pic = (ImageView) findViewById(R.id.reserve_order_pic);
	name = (TextView) findViewById(R.id.reserve_order_name);
	style = (TextView) findViewById(R.id.reserve_order_style);
	price = (TextView) findViewById(R.id.reserve_order_price);
	timeCount = (TextView) findViewById(R.id.reserve_order_time_count);
	keep = (TextView) findViewById(R.id.reserve_order_keep);
	phoneRL = (RelativeLayout) findViewById(R.id.reserve_order_phone_rl);
	phone = (TextView) findViewById(R.id.reserve_order_phone);
	addressRL = (RelativeLayout) findViewById(R.id.reserve_order_address_rl);
	address = (MarqueeTV) findViewById(R.id.reserve_order_address);
	remarkRL = (RelativeLayout) findViewById(R.id.reserve_order_remark_rl);
	remark = (TextView) findViewById(R.id.reserve_order_remark);
	couponRL = (RelativeLayout) findViewById(R.id.reserve_order_coupon_rl);
	coupon = (TextView) findViewById(R.id.reserve_order_coupon);
	_switch = (ImageView) findViewById(R.id.reserve_order_switch);
	myNameLL = (LinearLayout) findViewById(R.id.reserve_order_my_name_ll);
	myName = (EditText) findViewById(R.id.reserve_order_my_name);
	myIdLL = (LinearLayout) findViewById(R.id.reserve_order_my_id_ll);
	myID = (EditText) findViewById(R.id.reserve_order_id);
	protocol = (TextView) findViewById(R.id.reserve_order_protocol);
	total = (TextView) findViewById(R.id.reserve_order_total);
	left = (TextView) findViewById(R.id.reserve_order_left);
	pay = (TextView) findViewById(R.id.reserve_order_pay);

	styleSpinner = (Spinner) findViewById(R.id.reserve_order_style_spinner);
	dt = (TextView) findViewById(R.id.reserve_order_dt);
	phone_et = (EditText) findViewById(R.id.reserve_order_phone_et);
	style_tv = (TextView) findViewById(R.id.reserve_order_style_spinner_tv);
	remark_et = (EditText) findViewById(R.id.reserve_order_remark_et);
	coupon_iv = (ImageView) findViewById(R.id.reserve_order_coupon_arrow);
	name_submit_ll = (LinearLayout) findViewById(R.id.reserve_order_my_name_submit_ll);
	submit_name = (TextView) findViewById(R.id.reserve_order_my_name_submit_name);
	submit_id = (TextView) findViewById(R.id.reserve_order_my_name_submit_id);
	balance_rl = (RelativeLayout) findViewById(R.id.reserve_order_balance_rl);
	alipay_rl = (RelativeLayout) findViewById(R.id.reserve_order_alipay_rl);
	balance_select_iv = (ImageView) findViewById(R.id.reserve_order_balance_select_iv);
	alipay_select_iv = (ImageView) findViewById(R.id.reserve_order_balance_alipay_iv);
	parent = (RelativeLayout) findViewById(R.id.reserve_order_parent);
	address_iv = (ImageView) findViewById(R.id.reserve_order_address_iv);
	balance_ll = (LinearLayout) findViewById(R.id.reserve_order_balance_ll);

	addrTitle = (TextView) findViewById(R.id.aaaaaaaaaaaaaa);
	addr_rl2 = (RelativeLayout) findViewById(R.id.reserve_order_address_rl2);
	addr_et2 = (EditText) findViewById(R.id.reserve_order_address2);
	bx_tv1 = (TextView) findViewById(R.id.bx_tv1);
	bx_ll2 = (LinearLayout) findViewById(R.id.bx_ll2);

	back.setOnClickListener(this);
	phoneRL.setOnClickListener(this);
	addressRL.setOnClickListener(this);
	remarkRL.setOnClickListener(this);
	couponRL.setOnClickListener(this);
	_switch.setOnClickListener(this);
	myNameLL.setOnClickListener(this);
	myIdLL.setOnClickListener(this);
	protocol.setOnClickListener(this);
	pay.setOnClickListener(this);
	balance_rl.setOnClickListener(this);
	alipay_rl.setOnClickListener(this);

	preperImageLoader();

	zid = getIntent().getExtras().getString("zid");
	zrid = getIntent().getExtras().getString("zrid");
	_name = getIntent().getExtras().getString("name");
	oid = getIntent().getExtras().getString("oid");
	fgId = getIntent().getExtras().getString("fgid1");
	String time = getIntent().getExtras().getString("time") + ":00:00";
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	sdf.setTimeZone(TimeZone.getTimeZone("GMT+08"));
	java.util.Date d = null;
	try {
	    d = sdf.parse(time);
	} catch (ParseException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	timeStamp = d.getTime() / 1000;
	Log.e("timeStamp", timeStamp + "");
	addr_validation = "1";
	// type = getIntent().getExtras().getString("type");
	// sqId = getIntent().getExtras().getString("sqid");
	// addrSel = getIntent().getExtras().getString("sel");
	// addr = getIntent().getExtras().getString("address");
	// String time = getIntent().getExtras().getString("time");
	// String hr = "";
	//
	// Time t = new Time("GTM+8");
	// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// sdf.setTimeZone(TimeZone.getTimeZone("GMT+08"));
	// java.util.Date d = null;
	//
	// if (type.equals("ada")) {
	// dt.setText(time + ":00:00");
	// address.setText(addr);
	// } else {
	// hr = time.split(" ")[2].substring(0,
	// time.split(" ")[2].length() - 1);
	// dt.setText(getDateTime(time.split(" ")[0].substring(0,
	// time.split(" ")[0].length() - 3), hr));
	// Log.e("hr", hr);
	// System.out.println("hr ---> " + hr);
	// System.out.println("date ---> "
	// + time.split(" ")[0].substring(0,
	// time.split(" ")[0].length() - 3));
	// getDateTime(time.split(" ")[0].substring(0,
	// time.split(" ")[0].length() - 3), hr);
	// }
	// try {
	// d = sdf.parse(dt.getText().toString());
	// } catch (ParseException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// timeStamp = d.getTime() / 1000;
	// System.out.println("" + timeStamp);
	//
	// if (type.equals("yy")) {
	// if (addrSel.equals("1")) {
	// addrTitle.setText("所在地区");
	// addr_rl2.setVisibility(View.VISIBLE);
	// address.setText(addr);
	// address.setClickable(false);
	// } else if (addrSel.equals("2")) {
	// addrTitle.setText("服务地址");
	// addr_rl2.setVisibility(View.GONE);
	// address.setText(addr);
	//
	// }
	// } else {
	// addrTitle.setText("服务地址");
	// }
	//
	// style.setText(_name);
	//
	bxStatus = "0";
	payMethod = "0";
	yhId = "";
	payButton = "0";

    }

    class InfoAsync extends AsyncTask<String, Void, String> {
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
	    pd = CustomProgressDialog.createDialog(DesignerReserve1.this);
	    pd.show();
	    yhqList = new ArrayList<HashMap<String, String>>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected String doInBackground(String... params) {
	    // TODO Auto-generated method stub
	    String url = Config.ORDER_IME_SUBMIT_URL + params[0];
	    Log.e("url", url);
	    String data = new Tools().getURL(url);
	    System.out.println(data);
	    String code = "";

	    try {
		JSONObject jObject = new JSONObject(data);
		JSONObject result = jObject.getJSONObject("result");
		code = result.getString("code");
		if (code.equals("1")) {
		    JSONObject job = jObject.getJSONObject("data");
		    HashMap<String, String> hashMap = new HashMap<String, String>();
		    hashMap.put("pic", job.getString("pic"));
		    hashMap.put("zrid", job.getString("zrid"));
		    hashMap.put("xm", job.getString("xm"));
		    hashMap.put("zrmc", job.getString("zrmc"));
		    hashMap.put("jg", job.getString("jg"));
		    hashMap.put("hs", job.getString("hs"));
		    hashMap.put("dh", job.getString("dh"));
		    hashMap.put("dz", job.getString("dz"));
		    hashMap.put("bz", job.getString("bz"));
		    hashMap.put("je", job.getString("je"));
		    hashMap.put("bx_xm", job.getString("bx_xm"));
		    hashMap.put("bx_sfz", job.getString("bx_sfz"));
		    hashMap.put("num", job.getString("num"));
		    hashMap.put("yhqje", job.getString("yhqje"));
		    hashMap.put("zhye", job.getString("zhye"));

		    totalPay = job.getString("je");

		    Message msg = handler.obtainMessage();
		    msg.what = 1;
		    msg.obj = hashMap;
		    msg.sendToTarget();

		    JSONArray yhq = job.getJSONArray("yhqlist");
		    if (yhq.length() == 0) {
			yhqStatus = "0";
		    } else {
			yhqStatus = "1";
			for (int i = 0, j = yhq.length(); i < j; i++) {
			    JSONObject job3 = yhq.optJSONObject(i);
			    HashMap<String, String> hashMap3 = new HashMap<String, String>();
			    hashMap3.put("id", job3.getString("id"));
			    hashMap3.put("je", job3.getString("je"));
			    hashMap3.put("pic", job3.getString("pic"));

			    yhqList.add(hashMap3);
			}
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
	    if (result.equals("1")) {
		new AddrAsync().execute();
	    } else {
		Toast.makeText(DesignerReserve1.this, "获取造型师信息失败，请重试！",
			Toast.LENGTH_SHORT).show();
		finish();
	    }
	}
    }

    class ValidateAsync extends AsyncTask<String, Void, String> {

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
	    pd = CustomProgressDialog.createDialog(DesignerReserve1.this);
	    pd.show();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected String doInBackground(String... params) {
	    // TODO Auto-generated method stub
	    String url = Config.VALIDATE_ADDR_URL + params[0] + "&sqid="
		    + params[1];
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
	    if (result.equals("1")) {
		addr_validation = "1";
	    } else {
		Toast.makeText(DesignerReserve1.this, "造型师不在服务范围之内，请选择服务地址！",
			Toast.LENGTH_SHORT).show();
		addr_validation = "0";
		// finish();
	    }
	}

    }

    // class AddrAsync extends AsyncTask<Void, Void, String> {
    // private CustomProgressDialog pd;
    //
    // /*
    // * (non-Javadoc)
    // *
    // * @see android.os.AsyncTask#onPreExecute()
    // */
    // @Override
    // protected void onPreExecute() {
    // // TODO Auto-generated method stub
    // super.onPreExecute();
    // pd = CustomProgressDialog.createDialog(DesignerReserve.this);
    // pd.show();
    // addrList = new ArrayList<HashMap<String, String>>();
    // }
    //
    // /*
    // * (non-Javadoc)
    // *
    // * @see android.os.AsyncTask#doInBackground(Params[])
    // */
    // @Override
    // protected String doInBackground(Void... params) {
    // // TODO Auto-generated method stub
    // String url = Config.RESERVE_SELECT_ADDR_URL
    // + new Tools().getUserId(DesignerReserve.this);
    // Log.e("url", url);
    // String data = new Tools().getURL(url);
    // System.out.println(data);
    // String code = "";
    // try {
    // JSONObject jObject = new JSONObject(data);
    // JSONObject result = jObject.getJSONObject("result");
    // code = result.getString("code");
    // if (code.equals("1")) {
    // JSONArray jArray = jObject.getJSONArray("data");
    // for (int i = 0, j = jArray.length(); i < j; i++) {
    // JSONObject job = jArray.optJSONObject(i);
    // HashMap<String, String> hashMap = new HashMap<String, String>();
    // hashMap.put("id", job.getString("id"));
    // hashMap.put("pic", job.getString("zt"));
    // hashMap.put("sqid", job.getString("sqid"));
    // hashMap.put("dz", job.getString("dz"));
    //
    // addrList.add(hashMap);
    // }
    // if (addrList.get(0).get("pic").equals("1")) {
    // Message msg = handler.obtainMessage();
    // msg.what = 1;
    // msg.obj = addrList.get(0).get("dz");
    // sqid = addrList.get(0).get("sqid");
    // msg.sendToTarget();
    // }
    // }
    // } catch (JSONException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // return code;
    // }
    //
    // /*
    // * (non-Javadoc)
    // *
    // * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
    // */
    // @Override
    // protected void onPostExecute(String result) {
    // // TODO Auto-generated method stub
    // super.onPostExecute(result);
    // pd.dismiss();
    // }
    //
    // }

    class MainAsync extends AsyncTask<Void, String, String> {
	private CustomProgressDialog pd;

	@Override
	protected void onPreExecute() {
	    // TODO Auto-generated method stub
	    super.onPreExecute();
	    pd = CustomProgressDialog.createDialog(DesignerReserve1.this);
	    pd.show();
	}

	@Override
	protected String doInBackground(Void... params) {
	    // TODO Auto-generated method stub
	    String url = Config.PERSONAL_REGULAR_INFO_URL
		    + new Tools().getUserId(DesignerReserve1.this);
	    Log.e("url", url);
	    String data = new Tools().getURL(url);
	    System.out.println(data);
	    String code = "";

	    try {
		JSONObject jObject = new JSONObject(data);
		JSONObject result = jObject.getJSONObject("result");
		code = result.getString("code");
		String _cydz = result.getString("cydz");
		JSONObject job = jObject.getJSONObject("data");
		if (code.equals("1")) {
		    areaList = new ArrayList<HashMap<String, String>>();

		    JSONArray area = job.getJSONArray("sheng");
		    for (int i = 0, j = area.length(); i < j; i++) {
			JSONObject job1 = area.optJSONObject(i);
			HashMap<String, String> areaMap = new HashMap<String, String>();
			areaMap.put("id", job1.getString("id"));
			areaMap.put("classname", job1.getString("classname"));

			areaList.add(areaMap);

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
	}
    }

    class PostAsync extends AsyncTask<Void, Void, String> {
	private CustomProgressDialog pd;
	private String msg;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	@Override
	protected void onPreExecute() {
	    // TODO Auto-generated method stub
	    super.onPreExecute();
	    pd = CustomProgressDialog.createDialog(DesignerReserve1.this);
	    pd.show();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected String doInBackground(Void... params) {
	    // TODO Auto-generated method stub
	    String url = Config.ORDER_IME_CHANGE_URL
		    + new Tools().getUserId(DesignerReserve1.this) + getOrder();
	    Log.e("url", url);
	    String data = new Tools().getURL(url);
	    System.out.println(data);
	    String code = "";
	    try {
		JSONObject job = new JSONObject(data);
		JSONObject result = job.getJSONObject("result");
		code = result.getString("code");
		msg = result.getString("msg");
		if (code.equals("1")) {
		    orderNum = result.getString("num");
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
	protected void onPostExecute(final String result) {
	    // TODO Auto-generated method stub
	    super.onPostExecute(result);
	    handler.postDelayed(new Runnable() {

		@Override
		public void run() {
		    // TODO Auto-generated method stub
		    pd.dismiss();
		    if (result.equals("1")) {
			Toast.makeText(DesignerReserve1.this,
				"提交订单成功，请选择付款方式！", Toast.LENGTH_SHORT).show();
			Message msg = handler.obtainMessage();
			msg.what = 555;
			msg.sendToTarget();
		    } else {
			Toast.makeText(DesignerReserve1.this, msg,
				Toast.LENGTH_SHORT).show();
		    }
		}
	    }, 3000);

	}

    }

    class PayAsync extends AsyncTask<Void, Void, String> {
	private CustomProgressDialog pd;
	private String msg = "";

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	@Override
	protected void onPreExecute() {
	    // TODO Auto-generated method stub
	    super.onPreExecute();
	    pd = CustomProgressDialog.createDialog(DesignerReserve1.this);
	    pd.dismiss();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected String doInBackground(Void... params) {
	    // TODO Auto-generated method stub
	    String url = Config.BALANCE_PAY_POST_URL + orderNum;
	    Log.e("url", url);
	    String data = new Tools().getURL(url);
	    System.out.println(data);
	    String code = "";

	    try {
		JSONObject job = new JSONObject(data);
		JSONObject result = job.getJSONObject("result");
		code = result.getString("code");
		msg = result.getString("msg");
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
	    Toast.makeText(DesignerReserve1.this, msg, Toast.LENGTH_SHORT)
		    .show();
	    if (result.equals("1")) {
		startActivity(new Intent(DesignerReserve1.this,
			OrderListMain.class));
		DesignerReserve1.this.finish();
	    }
	}

    }

    private Handler handler = new Handler() {

	@Override
	public void handleMessage(Message msg) {
	    // TODO Auto-generated method stub
	    super.handleMessage(msg);
	    switch (msg.what) {
	    case 1:
		// 填写用户信息
		HashMap<String, String> hashMap = (HashMap<String, String>) msg.obj;
		if (hashMap.get("pic").startsWith("http")) {
		    ImageLoader.getInstance().displayImage(hashMap.get("pic"),
			    pic, options);
		} else {
		    ImageLoader.getInstance().displayImage(
			    Config.URL + hashMap.get("pic"), pic, options);
		}
		name.setText(hashMap.get("xm"));
		style.setText(hashMap.get("zrmc"));
		price.setText("￥" + hashMap.get("jg"));
		timeCount.setText(hashMap.get("hs"));
		phone_et.setText(hashMap.get("dh"));
		address.setText(hashMap.get("dz"));
		remark_et.setText(hashMap.get("bz"));
		coupon.setText(hashMap.get("yhqje") + "元");
		total.setText(hashMap.get("je") + "元");
		left.setText(hashMap.get("zhye") + "元");
		break;
	    case 10:
		String addr1 = (String) msg.obj;
		address.setText(addr1);
		System.out.println(sqId);
		new ValidateAsync().execute(zid, sqId);
		break;
	    // case 100:
	    // HashMap<String, String> hashMap = (HashMap<String, String>)
	    // msg.obj;
	    // ImageLoader.getInstance().displayImage(
	    // Config.URL + hashMap.get("pic"), pic, options);
	    // name.setText(hashMap.get("xm"));
	    // phone.setText(hashMap.get("dh"));
	    // price.setText("￥" + hashMap.get("jg1"));
	    // timeCount.setText(hashMap.get("hs"));
	    // coupon.setText(yhName);
	    // if (yhId.equals("0")) {
	    // total.setText(hashMap.get("jg1") + "元");
	    // } else {
	    // int _total = Integer.valueOf(hashMap.get("jg1"));
	    // int _coupon = Integer.valueOf(yhName.substring(0,
	    // yhName.length() - 1));
	    // totalPay = "" + (_total - _coupon);
	    // total.setText(totalPay + "元");
	    // }
	    // left.setText(balance + "元");
	    // break;
	    // case 101:
	    // final ArrayList<String> fgList = (ArrayList<String>) msg.obj;
	    // ArrayAdapter<String> adapter = new ArrayAdapter<String>(
	    // DesignerReserve1.this, R.layout.area_spinner_tv,
	    // R.id.text, fgList);
	    // adapter.setDropDownViewResource(R.layout.area_spinner_dropdown);
	    // styleSpinner.setAdapter(adapter);
	    // styleSpinner
	    // .setOnItemSelectedListener(new OnItemSelectedListener() {
	    //
	    // @Override
	    // public void onItemSelected(AdapterView<?> parent,
	    // View view, int position, long id) {
	    // // TODO Auto-generated method stub
	    // System.out.println("fgId --->"
	    // + fgIdMap.get(fgList.get(position)));
	    // fgId = fgIdMap.get(fgList.get(position));
	    // style_text = fgList.get(position);
	    // }
	    //
	    // @Override
	    // public void onNothingSelected(AdapterView<?> parent) {
	    // // TODO Auto-generated method stub
	    //
	    // }
	    // });
	    // break;
	    case 900:
		String je = (String) msg.obj;
		coupon.setText(je + "元");
		int _total = Integer.valueOf(price.getText().toString()
			.substring(1, price.getText().toString().length()));
		int _coupon = Integer.valueOf(je);
		totalPay = "" + (_total - _coupon);
		total.setText(totalPay + "元");
		break;
	    case 555:
		balance_ll.setVisibility(View.VISIBLE);
		phone_et.setFocusable(false);
		phone_et.setFocusableInTouchMode(false);
		address.setClickable(false);
		remark_et.setFocusable(false);
		remark_et.setFocusableInTouchMode(false);
		myNameLL.setVisibility(View.GONE);
		myIdLL.setVisibility(View.GONE);
		address_iv.setVisibility(View.GONE);
		addressRL.setClickable(false);
		coupon_iv.setVisibility(View.GONE);
		couponRL.setClickable(false);
		_switch.setClickable(false);
		if (bxStatus.equals("1")) {
		    name_submit_ll.setVisibility(View.VISIBLE);
		    submit_name.setText(myName.getText().toString());
		    submit_id.setText(myID.getText().toString());
		}
		pay.setText("确认支付");
		pay.setBackgroundResource(R.drawable.tvbg_green);
		pay.setOnClickListener(new OnClickListener() {

		    @Override
		    public void onClick(View v) {
			// TODO Auto-generated method stub
			if (payMethod.equals("0")) {
			    Toast.makeText(DesignerReserve1.this, "请选择支付方式！",
				    Toast.LENGTH_SHORT).show();
			} else {
			    if (payMethod.equals("3")) {// 3 为余额支付
				new PayAsync().execute();
			    } else if (payMethod.equals("1")) {// 1为支付宝支付
				alipayOrderInfo = AliPayOrder();
				// 对订单做RSA 签名
				String sign = Keys.sign(alipayOrderInfo);
				System.out.println("rsa sign ---> " + sign);
				// 仅需对sign 做URL编码
				try {
				    sign = URLEncoder.encode(sign, "UTF-8");
				} catch (UnsupportedEncodingException e) {
				    // TODO Auto-generated catch block
				    e.printStackTrace();
				}

				// 完整的符合支付宝参数规范的订单信息
				final String payInfo = alipayOrderInfo
					+ "&sign=\"" + sign + "\"&"
					+ Keys.getSignType();
				System.out.println("payInfo ---> " + payInfo);
				Runnable payRunnable = new Runnable() {

				    @Override
				    public void run() {
					// 构造PayTask 对象
					PayTask alipay = new PayTask(
						DesignerReserve1.this);
					// 调用支付接口，获取支付结果
					String result = alipay.pay(payInfo);
					System.out.println("ali result ---> "
						+ result);
					Message msg = aliHandler
						.obtainMessage();
					msg.what = 1;
					msg.obj = result;
					msg.sendToTarget();
				    }
				};

				// 必须异步调用
				Thread payThread = new Thread(payRunnable);
				payThread.start();
			    }
			}
		    }
		});
		break;

	    default:
		break;
	    }
	}

    };
    private DisplayImageOptions options;

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
	case R.id.reserve_order_phone_rl:

	    break;
	case R.id.reserve_order_address_rl:
	    new PopupWindowsAddr(this, parent);
	    break;
	case R.id.reserve_order_remark_rl:

	    break;
	case R.id.reserve_order_coupon_rl:
	    if (yhqStatus.equals("0")) {
		Toast.makeText(DesignerReserve1.this, "没有优惠券！",
			Toast.LENGTH_SHORT).show();
	    } else {
		new PopupWindowsCoupon(this, parent);
	    }
	    break;
	case R.id.reserve_order_switch:
	    if (bxStatus.equals("0")) {
		bxStatus = "1";
		_switch.setImageResource(R.drawable.switch_on);
		myNameLL.setVisibility(View.VISIBLE);
		myIdLL.setVisibility(View.VISIBLE);
		bx_tv1.setVisibility(View.VISIBLE);
		bx_ll2.setVisibility(View.VISIBLE);
	    } else {
		bxStatus = "0";
		_switch.setImageResource(R.drawable.switch_off);
		myNameLL.setVisibility(View.GONE);
		myIdLL.setVisibility(View.GONE);
		bx_tv1.setVisibility(View.GONE);
		bx_ll2.setVisibility(View.GONE);
	    }
	    break;
	case R.id.reserve_order_my_name_ll:

	    break;
	case R.id.reserve_order_my_id_ll:

	    break;
	case R.id.reserve_order_protocol:
	    startActivity(new Intent(DesignerReserve1.this, InsuranceView.class));
	    break;
	case R.id.reserve_order_pay:
	    if (payButton.equals("0")) {// 提交订单
		if (addr_validation.equals("1")) {
		    if (validation()) {
			new PostAsync().execute();
		    }
		} else {
		    Toast.makeText(this, "造型师服务地址不在范围内，请重新选择地址！",
			    Toast.LENGTH_SHORT).show();
		}
	    } else {// 支付状态
		if (payMethod.equals("1")) {
		    alipayOrderInfo = AliPayOrder();
		    // 对订单做RSA 签名
		    String sign = Keys.sign(alipayOrderInfo);
		    System.out.println("rsa sign ---> " + sign);
		    // 仅需对sign 做URL编码
		    try {
			sign = URLEncoder.encode(sign, "UTF-8");
		    } catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    }

		    // 完整的符合支付宝参数规范的订单信息
		    final String payInfo = alipayOrderInfo + "&sign=\"" + sign
			    + "\"&" + Keys.getSignType();

		    Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
			    // 构造PayTask 对象
			    PayTask alipay = new PayTask(DesignerReserve1.this);
			    // 调用支付接口，获取支付结果
			    String result = alipay.pay(payInfo);

			    Message msg = aliHandler.obtainMessage();
			    msg.what = 1;
			    msg.obj = result;
			    msg.sendToTarget();
			}
		    };

		    // 必须异步调用
		    Thread payThread = new Thread(payRunnable);
		    payThread.start();
		} else {// 3.余额支付
		    new PayAsync().execute();
		}

	    }
	    break;
	case R.id.reserve_order_balance_rl:
	    payMethod = "3";
	    balance_select_iv.setImageResource(R.drawable.select_pic);
	    alipay_select_iv.setImageResource(R.drawable.designer_unselect);
	    break;
	case R.id.reserve_order_alipay_rl:
	    payMethod = "1";
	    balance_select_iv.setImageResource(R.drawable.designer_unselect);
	    alipay_select_iv.setImageResource(R.drawable.select_pic);
	    break;
	default:
	    break;
	}
    }

    private Handler aliHandler = new Handler() {

	@Override
	public void handleMessage(Message msg) {
	    // TODO Auto-generated method stub
	    super.handleMessage(msg);
	    switch (msg.what) {
	    case 1:
		PayResult payResult = new PayResult((String) msg.obj);
		// 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
		String resultInfo = payResult.getResult();
		System.out.println("resultInfo ---> " + resultInfo);
		String resultStatus = payResult.getResultStatus();
		// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
		if (TextUtils.equals(resultStatus, "9000")) {
		    Toast.makeText(DesignerReserve1.this, "支付成功",
			    Toast.LENGTH_SHORT).show();
		} else {
		    // 判断resultStatus 为非“9000”则代表可能支付失败
		    // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
		    if (TextUtils.equals(resultStatus, "8000")) {
			Toast.makeText(DesignerReserve1.this, "支付结果确认中",
				Toast.LENGTH_SHORT).show();

		    } else {
			// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
			Toast.makeText(DesignerReserve1.this, "支付失败",
				Toast.LENGTH_SHORT).show();

		    }
		}
		break;

	    default:
		break;
	    }
	}

    };

    private String AliPayOrder() {
	StringBuilder sb = new StringBuilder();
	sb.append("partner=");
	sb.append("\"" + Keys.PARTNER + "\"");
	sb.append("&seller_id=");
	sb.append("\"" + Keys.SELLER + "\"");
	sb.append("&out_trade_no=");
	sb.append("\"" + orderNum + "\"");
	sb.append("&subject=");
	sb.append("\"" + "Android美咖来了服务支付" + "\"");
	sb.append("&body=");
	sb.append("\"" + "美咖来了造型服务支付" + "\"");
	sb.append("&total_fee=");
	sb.append("\"" + totalPay + "\"");
	sb.append("&notify_url=");
	sb.append("\""
		+ "http://mkll.weiqipingtai.com/kehu/zhifubao/notify_url.php");
	sb.append("\"" + "&service=\"mobile.securitypay.pay\"");
	sb.append("&payment_type=\"1\"");
	sb.append("&_input_charset=\"utf-8\"");
	sb.append("&it_b_pay=\"20m\"");
	// sb.append("&return_url=");

	System.out.println(sb.toString());
	return sb.toString();
    }

    private boolean validation() {

	if (phone_et.getText().toString().length() == 0) {
	    Toast.makeText(this, "请填写电话！", Toast.LENGTH_SHORT).show();
	    return false;
	} else if (phone_et.getText().toString().length() != 11) {
	    Toast.makeText(this, "请填写正确的11位电话！", Toast.LENGTH_SHORT).show();
	    return false;
	} else if (address.getText().toString().length() == 0) {
	    Toast.makeText(this, "请选择服务地址！", Toast.LENGTH_SHORT).show();
	    return false;
	}
	// else if (fgId.equals("请选择造型风格！")) {
	// Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
	// return false;
	// }
	// else if (payMethod.equals("")) {
	// Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
	// return false;
	// }
	else {
	    if (bxStatus.equals("1")) {
		if (myName.getText().toString().length() == 0) {
		    Toast.makeText(this, "请填写保险人姓名！", Toast.LENGTH_SHORT)
			    .show();
		    return false;
		} else if (myID.getText().toString().length() == 0) {
		    Toast.makeText(this, "请填写保险人身份证号！", Toast.LENGTH_SHORT)
			    .show();
		    return false;
		} else {
		    return true;
		}
	    } else {
		return true;
	    }
	}

    }

    private String getOrder() {
	String remark_utf8 = "", name_utf8 = "", addr_utf8 = "";
	try {
	    remark_utf8 = URLEncoder.encode(remark_et.getText().toString(),
		    "UTF-8");
	    name_utf8 = URLEncoder.encode(myName.getText().toString(), "UTF-8");
	    addr_utf8 = URLEncoder
		    .encode(address.getText().toString(), "UTF-8");
	} catch (UnsupportedEncodingException e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	}

	StringBuilder sb = new StringBuilder();
	sb.append("&oid=");
	sb.append(oid);
	sb.append("&dh=");
	sb.append(phone_et.getText().toString());
	sb.append("&dz=");
	sb.append(addr_utf8);
	sb.append("&bz=");
	sb.append(remark_utf8);
	sb.append("&yhqid=");
	sb.append(yhId);
	sb.append("&jg=");
	sb.append(total.getText().toString()
		.substring(0, total.getText().toString().length() - 1));
	sb.append("&bx_xm=");
	if (bxStatus.equals("1")) {
	    sb.append(name_utf8);
	} else {
	    sb.append("");
	}
	sb.append("&bx_sfz=");
	if (bxStatus.equals("1")) {
	    sb.append(myID.getText().toString());
	} else {
	    sb.append("");
	}
	System.out.println(sb.toString());
	return sb.toString();
    }

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

	    adapter = new AddressListAdapter(addrList, DesignerReserve1.this);
	    lv.setAdapter(adapter);
	    lv.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view,
			int position, long id) {
		    // TODO Auto-generated method stub
		    Message msg = handler.obtainMessage();
		    msg.what = 10;
		    if (addrList.get(position).get("pic").equals("1")) {
			String addr = addrList.get(position).get("dz");
			sqId = addrList.get(position).get("sqid");
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
			sqId = addrList.get(position).get("sqid");

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
		    dismiss();
		}
	    });

	}

    }

    class PopupWindowsCoupon extends PopupWindow {

	public PopupWindowsCoupon(Context mContext, View parent) {

	    View view = View.inflate(mContext, R.layout.my_coupon, null);
	    view.startAnimation(AnimationUtils.loadAnimation(mContext,
		    R.anim.push_bottom_in_2));
	    ImageView back = (ImageView) view.findViewById(R.id.title_back);
	    TextView title = (TextView) view.findViewById(R.id.title_text);
	    title.setText("选择优惠券");
	    // TextView add = (TextView)
	    // view.findViewById(R.id.dialog_view_add);
	    ListView lv = (ListView) view.findViewById(R.id.my_coupon_lv);

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

	    MyCouponListAdapter adapter = new MyCouponListAdapter(yhqList,
		    DesignerReserve1.this);
	    lv.setAdapter(adapter);
	    lv.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view,
			int position, long id) {
		    // TODO Auto-generated method stub
		    yhId = yhqList.get(position).get("id");
		    Message msg = handler.obtainMessage();
		    msg.what = 900;
		    msg.obj = yhqList.get(position).get("je");
		    msg.sendToTarget();
		    dismiss();
		}
	    });

	    back.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
		    // TODO Auto-generated method stub
		    dismiss();
		}
	    });

	}

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
	switch (requestCode) {
	case 100:
	    if (resultCode == 404) {
		Toast.makeText(this, "取消添加常用地址！", Toast.LENGTH_SHORT).show();
	    } else {
		Toast.makeText(this, "添加常用地址成功！", Toast.LENGTH_SHORT).show();
		new MainAsync().execute();
	    }
	    break;

	default:
	    break;
	}
    }

    private void preperImageLoader() {

	/******************* 配置ImageLoder ***********************************************/
	File cacheDir = StorageUtils.getOwnCacheDirectory(this,
		"imageloader/Cache");

	ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
		this).denyCacheImageMultipleSizesInMemory()
		.discCache(new UnlimitedDiscCache(cacheDir))// 自定义缓存路径
		.build();// 开始构建

	options = new DisplayImageOptions.Builder().cacheInMemory()
		.cacheOnDisc().imageScaleType(ImageScaleType.IN_SAMPLE_INT)
		.showImageForEmptyUri(R.drawable.question)
		.showImageOnFail(R.drawable.question).build();

	ImageLoader.getInstance().init(config);// 全局初始化此配置
	/*********************************************************************************/
    }

    class AddrAsync extends AsyncTask<Void, Void, Void> {
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
	    pd = CustomProgressDialog.createDialog(DesignerReserve1.this);
	    pd.show();
	    addrList = new ArrayList<HashMap<String, String>>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected Void doInBackground(Void... params) {
	    // TODO Auto-generated method stub
	    String url = Config.RESERVE_SELECT_ADDR_URL
		    + new Tools().getUserId(DesignerReserve1.this);
	    Log.e("url", url);
	    String data = new Tools().getURL(url);
	    System.out.println(data);

	    try {
		JSONObject jObject = new JSONObject(data);
		JSONObject result = jObject.getJSONObject("result");
		String code = result.getString("code");
		if (code.equals("1")) {
		    JSONArray cydz = jObject.getJSONArray("data");
		    for (int i = 0, j = cydz.length(); i < j; i++) {
			JSONObject job2 = cydz.optJSONObject(i);
			HashMap<String, String> hashMap2 = new HashMap<String, String>();
			hashMap2.put("id", job2.getString("id"));
			hashMap2.put("pic", job2.getString("zt"));
			hashMap2.put("sqid", job2.getString("sqid"));
			hashMap2.put("dz", job2.getString("dz"));

			addrList.add(hashMap2);
		    }
		}
	    } catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(Void result) {
	    // TODO Auto-generated method stub
	    super.onPostExecute(result);
	    pd.dismiss();
	}

    }
}
