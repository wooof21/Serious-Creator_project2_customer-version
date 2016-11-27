/**
 * 
 */
package order;

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
import view.MarqueeTV;

import com.mkcomingc.BaseActivity;
import com.mkcomingc.R;

import designers.DesignerReserve;

import adapter.AddressListAdapter;
import android.app.Activity;
import android.content.Context;
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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author Liming Chu
 * 
 * @param
 * @return
 */
public class PostOrder extends BaseActivity implements OnClickListener {

    private ImageView back;
    private TextView style;
    private TextView timeCount;
    private TextView keep;
    private LinearLayout price1;
    private ImageView iv1;
    private LinearLayout price2;
    private ImageView iv2;
    private LinearLayout price3;
    private ImageView iv3;
    private EditText phone;
    private RelativeLayout addressRL;
    private MarqueeTV address;
    private TextView total;
    private TextView pay;
    private TextView price_tv1, price_tv2, price_tv3, level1, level2, level3;
    private RelativeLayout parent;
    private Spinner spinner;

    protected String phoneNo, reMark, zrid, zrName, fgid, jg, djId;
    public ArrayList<HashMap<String, String>> addrList;
    private HashMap<String, String> fgId;
    private ArrayList<String> fgList, djList;

    
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
	setContentView(R.layout.post_order);
	Exit.getInstance().addActivity(this);
	prepareView();
	zrid = getIntent().getExtras().getString("id");
	zrName = getIntent().getExtras().getString("name");
	style.setText(zrName);
	new InfoAsync().execute(zrid);
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
	    pd = CustomProgressDialog.createDialog(PostOrder.this);
	    pd.show();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#doInBackground(Params[])//
	 */
	@Override
	protected String doInBackground(String... params) {
	    // TODO Auto-generated method stub
	    String url = Config.IME_POST_ORDER_URL + params[0] + "&uid="
		    + new Tools().getUserId(PostOrder.this);
	    Log.e("url", url);
	    String data = new Tools().getURL(url);
	    System.out.println(data);
	    String code = "";

	    try {
		JSONObject jObject = new JSONObject(data);
		JSONObject result = jObject.getJSONObject("result");
		code = result.getString("code");
		if (code.equals("1")) {
		    JSONObject _data = jObject.getJSONObject("data");
		    HashMap<String, String> hashMap = new HashMap<String, String>();
		    hashMap.put("hs", _data.getString("hs"));
		    hashMap.put("mrdz", _data.getString("mrdz"));
		    hashMap.put("mrjg", _data.getString("mrjg"));
		    Message msg1 = handler.obtainMessage();
		    msg1.what = 1;
		    msg1.obj = hashMap;
		    msg1.sendToTarget();

		    JSONArray jg = _data.getJSONArray("jglist");
		    djList = new ArrayList<String>();
		    ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		    for (int i = 0, j = jg.length(); i < j; i++) {
			JSONObject job1 = jg.optJSONObject(i);
			HashMap<String, String> hashMap1 = new HashMap<String, String>();
			hashMap1.put("jg", job1.getString("jg"));
			hashMap1.put("dj", job1.getString("dj"));
			djList.add(job1.getString("dj1"));

			list.add(hashMap1);
		    }
		    Message msg2 = handler.obtainMessage();
		    msg2.what = 2;
		    msg2.obj = list;
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

		    JSONArray zr = _data.getJSONArray("zrlist");
		    fgId = new HashMap<String, String>();
		    fgList = new ArrayList<String>();
		    for (int i = 0, j = zr.length(); i < j; i++) {
			JSONObject job3 = zr.optJSONObject(i);
			fgId.put(job3.getString("classname"),
				job3.getString("id"));
			fgList.add(job3.getString("classname"));
		    }

		    Message msg3 = handler.obtainMessage();
		    msg3.what = 3;
		    msg3.sendToTarget();
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

	    adapter = new AddressListAdapter(addrList, PostOrder.this);
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
		    dismiss();
		}
	    });

	}

    }

    private Handler handler = new Handler() {

	@Override
	public void handleMessage(Message msg) {
	    // TODO Auto-generated method stub
	    super.handleMessage(msg);
	    switch (msg.what) {
	    case 1:
		HashMap<String, String> hashMap = (HashMap<String, String>) msg.obj;
		timeCount.setText(hashMap.get("hs"));
		address.setText(hashMap.get("mrdz"));
		total.setText(hashMap.get("mrjg") + "元");
		break;
	    case 2:
		ArrayList<HashMap<String, String>> list = (ArrayList<HashMap<String, String>>) msg.obj;
		price_tv1.setText("￥" + list.get(0).get("jg"));
		level1.setText("(" + list.get(0).get("dj") + ")");
		price_tv2.setText("￥" + list.get(1).get("jg"));
		level2.setText("(" + list.get(1).get("dj") + ")");
		price_tv3.setText("￥" + list.get(2).get("jg"));
		level3.setText("(" + list.get(2).get("dj") + ")");
		break;
	    case 3:
		fgid = fgId.get(fgList.get(0));
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
			PostOrder.this, R.layout.area_spinner_tv, R.id.text,
			fgList);
		adapter.setDropDownViewResource(R.layout.area_spinner_dropdown);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

		    @Override
		    public void onItemSelected(AdapterView<?> parent,
			    View view, int position, long id) {
			// TODO Auto-generated method stub
			fgid = fgId.get(fgList.get(position));
			Log.e("fgid", fgid);
		    }

		    @Override
		    public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			fgid = fgId.get(fgList.get(0));
		    }
		});
		break;
	    case 10:
		String addr = (String) msg.obj;
		address.setText(addr);
		break;
	    default:
		break;
	    }
	}

    };

    private void prepareView() {
	back = (ImageView) findViewById(R.id.title_back);
	style = (TextView) findViewById(R.id.post_order_style);
	timeCount = (TextView) findViewById(R.id.post_order_time_count);
	keep = (TextView) findViewById(R.id.post_order_keep);
	price1 = (LinearLayout) findViewById(R.id.post_order_gaoji_ll);
	iv1 = (ImageView) findViewById(R.id.post_order_gaoji_iv);
	price2 = (LinearLayout) findViewById(R.id.post_order_teji_ll);
	iv2 = (ImageView) findViewById(R.id.post_order_teji_iv);
	price3 = (LinearLayout) findViewById(R.id.post_order_mingxing_ll);
	iv3 = (ImageView) findViewById(R.id.post_order_mingxing_iv);
	phone = (EditText) findViewById(R.id.post_order_phone);
	addressRL = (RelativeLayout) findViewById(R.id.post_order_address_rl);
	address = (MarqueeTV) findViewById(R.id.post_order_address);
	total = (TextView) findViewById(R.id.post_order_total);
	pay = (TextView) findViewById(R.id.post_order_pay);
	price_tv1 = (TextView) findViewById(R.id.post_order_price1);
	price_tv2 = (TextView) findViewById(R.id.post_order_price2);
	price_tv3 = (TextView) findViewById(R.id.post_order_price3);
	level1 = (TextView) findViewById(R.id.post_order_level1);
	level2 = (TextView) findViewById(R.id.post_order_level2);
	level3 = (TextView) findViewById(R.id.post_order_level3);
	parent = (RelativeLayout) findViewById(R.id.post_order_parent);
	spinner = (Spinner) findViewById(R.id.post_order_spinner);

	back.setOnClickListener(this);
	addressRL.setOnClickListener(this);
	pay.setOnClickListener(this);
	price1.setOnClickListener(this);
	price2.setOnClickListener(this);
	price3.setOnClickListener(this);

	jg = "";
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
	case R.id.post_order_address_rl:
	    new PopupWindowsAddr(this, parent);
	    break;
	case R.id.post_order_pay:
	    if (validation()) {
		new AsyncTask<Void, Void, String>() {

		    private CustomProgressDialog pd;
		    private String msg;
		    @Override
		    protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = CustomProgressDialog.createDialog(PostOrder.this);
			pd.show();
		    }

		    @Override
		    protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			String url = Config.IME_ORDER_POST_URL
				+ new Tools().getUserId(PostOrder.this)
				+ getOrder();
			Log.e("url", url);
			String data = new Tools().getURL(url);
			System.out.println(data);
			String code = "";
			
			try {
			    JSONObject jObject = new JSONObject(data);
			    JSONObject result = jObject.getJSONObject("result");
			    code = result.getString("code");
			    msg = result.getString("msg");
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
			    Toast.makeText(PostOrder.this, "发布需求后，造型师抢单成功就可以支付喽！", Toast.LENGTH_SHORT).show();
			    finish();
			}else{
			    Toast.makeText(PostOrder.this, msg, Toast.LENGTH_SHORT).show();
			}
		    }

		}.execute();
	    }
	    break;
	case R.id.post_order_gaoji_ll:
	    setIvBg(R.id.post_order_gaoji_ll);
	    djId = djList.get(0);
	    break;

	case R.id.post_order_teji_ll:
	    setIvBg(R.id.post_order_teji_ll);
	    djId = djList.get(1);
	    break;
	case R.id.post_order_mingxing_ll:
	    setIvBg(R.id.post_order_mingxing_ll);
	    djId = djList.get(2);
	    break;
	default:
	    break;
	}
    }

    private void setIvBg(int id) {
	switch (id) {
	case R.id.post_order_gaoji_ll:
	    iv1.setImageResource(R.drawable.designer_select);
	    iv2.setImageResource(R.drawable.designer_unselect);
	    iv3.setImageResource(R.drawable.designer_unselect);
	    jg = price_tv1.getText().toString()
		    .substring(1, price_tv1.getText().toString().length());
	    total.setText(jg + "元");
	    break;
	case R.id.post_order_teji_ll:
	    iv2.setImageResource(R.drawable.designer_select);
	    iv1.setImageResource(R.drawable.designer_unselect);
	    iv3.setImageResource(R.drawable.designer_unselect);
	    jg = price_tv2.getText().toString()
		    .substring(1, price_tv2.getText().toString().length());
	    total.setText(jg + "元");
	    break;
	case R.id.post_order_mingxing_ll:
	    iv3.setImageResource(R.drawable.designer_select);
	    iv1.setImageResource(R.drawable.designer_unselect);
	    iv2.setImageResource(R.drawable.designer_unselect);
	    jg = price_tv3.getText().toString()
		    .substring(1, price_tv3.getText().toString().length());
	    total.setText(jg + "元");
	    break;
	default:
	    break;
	}
    }

    private boolean validation() {
	if (phone.getText().toString().length() == 0) {
	    Toast.makeText(this, "请填写电话！", Toast.LENGTH_SHORT).show();
	    return false;
	} else if (phone.getText().toString().length() != 11) {
	    Toast.makeText(this, "请填写正确的11位电话！", Toast.LENGTH_SHORT).show();
	    return false;
	} else if (jg.equals("")) {
	    Toast.makeText(this, "请选择化妆师等级！", Toast.LENGTH_SHORT).show();
	    return false;
	} else if (address.getText().toString().length() == 0) {
	    Toast.makeText(this, "请选择服务地址！", Toast.LENGTH_SHORT).show();
	    return false;
	} else {
	    return true;
	}
    }

    private String getOrder() {
	String dz = address.getText().toString();
	String dz_utf8 = "";

	try {
	    dz_utf8 = URLEncoder.encode(dz, "utf-8");
	} catch (UnsupportedEncodingException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	StringBuilder sb = new StringBuilder();
	sb.append("&dh=");
	sb.append(phone.getText().toString());
	sb.append("&dz=");
	sb.append(dz_utf8);
	sb.append("&fgid=");
	sb.append(fgid);
	sb.append("&zrid=");
	sb.append(zrid);
	sb.append("&jg=");
	sb.append(jg);
	sb.append("&ljfwdj=");
	sb.append(djId);

	return sb.toString();
    }
}
