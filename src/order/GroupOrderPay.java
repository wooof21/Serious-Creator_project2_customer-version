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


import com.alipay.sdk.app.PayTask;
import com.mkcomingc.BaseActivity;
import com.mkcomingc.R;


import adapter.AddressListAdapter;
import adapter.GroupPayListAdapter;
import adapter.MyCouponListAdapter;
import alipay.Keys;
import alipay.PayResult;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author Liming Chu
 * 
 * @param
 * @return
 */
public class GroupOrderPay extends BaseActivity implements OnClickListener {

    private ImageView back, infoIV, balanceIV, alipayIV;
    private TextView where, price, time, couponTV, total, balance, submit;
    private FrameLayout infoFL;
    private ListView lv;
    private EditText phone, remark;
    private MarqueeTV addr;
    private RelativeLayout couponRL, balanceRL, alipayRL, parent;
    private LinearLayout hideLL;

    private String yhqStatus, listDisplay, oid, yhId, totalPay, payMethod, payButton, orderNum, alipayOrderInfo;
    public ArrayList<HashMap<String, String>> zxsList, yhqList, addrList;

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
	setContentView(R.layout.group_order_pay);
	Exit.getInstance().addActivity(this);
	prepareView();

	new InfoAsync().execute(oid);
    }

    private void prepareView() {
	back = (ImageView) findViewById(R.id.title_back);
	where = (TextView) findViewById(R.id.group_order_where);
	price = (TextView) findViewById(R.id.group_order_price);
	time = (TextView) findViewById(R.id.group_order_time);
	infoFL = (FrameLayout) findViewById(R.id.group_order_info_fl);
	infoIV = (ImageView) findViewById(R.id.group_order_info_iv);
	lv = (ListView) findViewById(R.id.group_order_lv);
	phone = (EditText) findViewById(R.id.group_order_phone_et);
	addr = (MarqueeTV) findViewById(R.id.group_order_address);
	remark = (EditText) findViewById(R.id.group_order_remark_et);
	couponRL = (RelativeLayout) findViewById(R.id.group_order_coupon_rl);
	couponTV = (TextView) findViewById(R.id.group_order_coupon);
	total = (TextView) findViewById(R.id.group_order_total);
	balanceRL = (RelativeLayout) findViewById(R.id.group_order_balance_rl);
	balance = (TextView) findViewById(R.id.group_order_balance);
	balanceIV = (ImageView) findViewById(R.id.group_order_balance_select_iv);
	alipayRL = (RelativeLayout) findViewById(R.id.group_order_alipay_rl);
	alipayIV = (ImageView) findViewById(R.id.group_order_balance_alipay_iv);
	submit = (TextView) findViewById(R.id.group_order_pay);
	parent = (RelativeLayout) findViewById(R.id.group_parent);
	hideLL = (LinearLayout) findViewById(R.id.group_order_hide_ll);
	
	
	back.setOnClickListener(this);
	infoFL.setOnClickListener(this);
	addr.setOnClickListener(this);
	couponRL.setOnClickListener(this);
	balanceRL.setOnClickListener(this);
	alipayRL.setOnClickListener(this);
	submit.setOnClickListener(this);

	listDisplay = "1";// 1-չ���б�
	oid = getIntent().getExtras().getString("oid");

	payMethod = "0";
	yhId = "";
	payButton = "1";//���֧��

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
	    pd = CustomProgressDialog.createDialog(GroupOrderPay.this);
	    pd.show();
	    zxsList = new ArrayList<HashMap<String, String>>();
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
	    String url = Config.ORDER_GROUP_CHECK_URL
		    + new Tools().getUserId(GroupOrderPay.this) + "&oid="
		    + params[0];
	    Log.e("url", url);
	    String _data = new Tools().getURL(url);
	    System.out.println(_data);
	    String code = "";

	    try {
		JSONObject jObject = new JSONObject(_data);
		JSONObject result = jObject.getJSONObject("result");
		code = result.getString("code");
		if (code.equals("1")) {
		    JSONObject data = jObject.getJSONObject("data");
		    HashMap<String, String> hashMap = new HashMap<String, String>();
		    hashMap.put("dh", data.getString("dh"));
		    hashMap.put("dz", data.getString("dz"));
		    hashMap.put("ch", data.getString("ch"));
		    hashMap.put("bz", data.getString("bz"));
		    hashMap.put("je", data.getString("je"));
		    hashMap.put("hs", data.getString("hs"));
		    hashMap.put("yhqje", data.getString("yhqje"));
		    hashMap.put("yhqid", data.getString("yhqid"));
		    hashMap.put("zhye", data.getString("zhye"));
		    
		    if(data.getString("yhqje").equals("��")){
			totalPay = data.getString("je");
		    }else{
			int yh = Integer.valueOf(data.getString("yhqje"));
			int _total = Integer.valueOf(data.getString("je"));
			totalPay = "" + (_total - yh);
		    }
		    
		    yhId = data.getString("yhqid");

		    Message msg = handler.obtainMessage();
		    msg.what = 1;
		    msg.obj = hashMap;
		    msg.sendToTarget();

		    JSONArray zxslist = data.getJSONArray("zxslist");
		    for (int i = 0, j = zxslist.length(); i < j; i++) {
			JSONObject job = zxslist.optJSONObject(i);
			HashMap<String, String> hashMap2 = new HashMap<String, String>();
			hashMap2.put("xm", job.getString("xm"));
			hashMap2.put("xb", job.getString("xb"));
			hashMap2.put("jg", job.getString("jg"));
			hashMap2.put("pic", job.getString("pic"));
			hashMap2.put("dj", job.getString("dj"));
			hashMap2.put("fwpj", job.getString("fwpj"));
			hashMap2.put("ds", job.getString("ds"));
			hashMap2.put("zypj", job.getString("zypj"));

			zxsList.add(hashMap2);
		    }

		    JSONArray yhq = data.getJSONArray("yhqlist");
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
		GroupPayListAdapter adapter = new GroupPayListAdapter(zxsList,
			GroupOrderPay.this);
		lv.setAdapter(adapter);
	    } else {
		Toast.makeText(GroupOrderPay.this, "��ȡ������Ϣʧ�ܣ������ԣ�",
			Toast.LENGTH_SHORT).show();
		GroupOrderPay.this.finish();
	    }
	}

    }

    class PostAsync extends AsyncTask<Void, Void, String> {
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
	    pd = CustomProgressDialog.createDialog(GroupOrderPay.this);
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
	    String url = Config.ORDER_GROUP_SUBMIT_URL
		    + new Tools().getUserId(GroupOrderPay.this) + getOrder();
	    Log.e("url", url);
	    String data = new Tools().getURL(url);
	    System.out.println(data);
	    String code = "";
	    
	    try {
		JSONObject jObject = new JSONObject(data);
		JSONObject result = jObject.getJSONObject("result");
		code = result.getString("code");
		if(code.equals("1")){
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
	protected void onPostExecute(String result) {
	    // TODO Auto-generated method stub
	    super.onPostExecute(result);
	    pd.dismiss();
	    if(result.equals("1")){
		Toast.makeText(GroupOrderPay.this, "�ύ�����ɹ�����ѡ��֧����ʽ��", Toast.LENGTH_SHORT).show();
		Message msg = handler.obtainMessage();
		msg.what = 555;
		msg.sendToTarget();
	    }else{
		Toast.makeText(GroupOrderPay.this, "�ύ����ʧ�ܣ������ԣ�", Toast.LENGTH_SHORT).show();
	    }
	}
    }
    
    private String getOrder(){
	String address_utf8 = "", remark_utf8 = "";
	try {
	    address_utf8 = URLEncoder.encode(addr.getText().toString(), "utf-8");
	    remark_utf8 = URLEncoder.encode(remark.getText().toString(), "utf-8");
	} catch (UnsupportedEncodingException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	
	
	StringBuilder sb = new StringBuilder();
	sb.append("&oid=");
	sb.append(oid);
	sb.append("&dh=");
	sb.append(phone.getText().toString());
	sb.append("&dz=");
	sb.append(address_utf8);
	sb.append("&bz=");
	sb.append(remark_utf8);
	sb.append("&yhqid=");
	sb.append(yhId);
	
	return sb.toString();
	
    }

    private Handler handler = new Handler() {

	@Override
	public void handleMessage(Message msg) {
	    // TODO Auto-generated method stub
	    super.handleMessage(msg);
	    switch (msg.what) {
	    case 1:
		HashMap<String, String> hashMap = (HashMap<String, String>) msg.obj;
		where.setText(hashMap.get("ch"));
		price.setText("��" + hashMap.get("je"));
		time.setText(hashMap.get("hs"));
		phone.setText(hashMap.get("dh"));
		addr.setText(hashMap.get("dz"));
		remark.setText(hashMap.get("bz"));
		couponTV.setText(hashMap.get("yhqje"));
		total.setText(totalPay + "Ԫ");
		balance.setText(hashMap.get("zhye") + "Ԫ");
		break;
	    case 10:
		String address = (String) msg.obj;
		addr.setText(address);
		break;
	    case 900:
		String je = (String) msg.obj;
		couponTV.setText(je + "Ԫ");
		int _total = Integer.valueOf(price.getText().toString()
			.substring(1, price.getText().toString().length()));
		int _coupon = Integer.valueOf(je);
		totalPay = "" + (_total - _coupon);
		total.setText(totalPay + "Ԫ");

		break;
	    case 555://�����ύ�ɹ�
		phone.setFocusable(false);
		phone.setFocusableInTouchMode(false);
		addr.setClickable(false);
		remark.setFocusable(false);
		remark.setFocusableInTouchMode(false);
		couponRL.setClickable(false);
		hideLL.setVisibility(View.VISIBLE);
		submit.setText("ȷ��֧��");
		submit.setBackgroundColor(Color.rgb(69, 192, 26));
		submit.setOnClickListener(new OnClickListener() {

		    @Override
		    public void onClick(View v) {
			// TODO Auto-generated method stub
			if (payMethod.equals("0")) {
			    Toast.makeText(GroupOrderPay.this, "��ѡ��֧����ʽ��",
				    Toast.LENGTH_SHORT).show();
			} else {
			    if (payMethod.equals("3")) {// 3 Ϊ���֧��
				new PayAsync().execute();
			    } else if (payMethod.equals("1")) {// 1Ϊ֧����֧��
				alipayOrderInfo = AliPayOrder();
				// �Զ�����RSA ǩ��
				String sign = Keys.sign(alipayOrderInfo);
				System.out.println("rsa sign ---> " + sign);
				// �����sign ��URL����
				try {
				    sign = URLEncoder.encode(sign, "UTF-8");
				} catch (UnsupportedEncodingException e) {
				    // TODO Auto-generated catch block
				    e.printStackTrace();
				}

				// �����ķ���֧���������淶�Ķ�����Ϣ
				final String payInfo = alipayOrderInfo
					+ "&sign=\"" + sign + "\"&"
					+ Keys.getSignType();
				System.out.println("payInfo ---> " + payInfo);
				Runnable payRunnable = new Runnable() {

				    @Override
				    public void run() {
					// ����PayTask ����
					PayTask alipay = new PayTask(
						GroupOrderPay.this);
					// ����֧���ӿڣ���ȡ֧�����
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

				// �����첽����
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

    
    
    
    private Handler aliHandler = new Handler() {

	@Override
	public void handleMessage(Message msg) {
	    // TODO Auto-generated method stub
	    super.handleMessage(msg);
	    switch (msg.what) {
	    case 1:
		PayResult payResult = new PayResult((String) msg.obj);
		// ֧�������ش˴�֧���������ǩ�������֧����ǩ����Ϣ��ǩԼʱ֧�����ṩ�Ĺ�Կ����ǩ
		String resultInfo = payResult.getResult();
		System.out.println("resultInfo ---> " + resultInfo);
		String resultStatus = payResult.getResultStatus();
		// �ж�resultStatus Ϊ��9000�������֧���ɹ�������״̬�������ɲο��ӿ��ĵ�
		if (TextUtils.equals(resultStatus, "9000")) {
		    Toast.makeText(GroupOrderPay.this, "֧���ɹ�",
			    Toast.LENGTH_SHORT).show();
		} else {
		    // �ж�resultStatus Ϊ�ǡ�9000����������֧��ʧ��
		    // ��8000������֧�������Ϊ֧������ԭ�����ϵͳԭ���ڵȴ�֧�����ȷ�ϣ����ս����Ƿ�ɹ��Է�����첽֪ͨΪ׼��С����״̬��
		    if (TextUtils.equals(resultStatus, "8000")) {
			Toast.makeText(GroupOrderPay.this, "֧�����ȷ����",
				Toast.LENGTH_SHORT).show();

		    } else {
			// ����ֵ�Ϳ����ж�Ϊ֧��ʧ�ܣ������û�����ȡ��֧��������ϵͳ���صĴ���
			Toast.makeText(GroupOrderPay.this, "֧��ʧ��",
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
	sb.append("\"" + "Android���������������֧��" + "\"");
	sb.append("&body=");
	sb.append("\"" + "���������������ͷ���֧��" + "\"");
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
 	    pd = CustomProgressDialog.createDialog(GroupOrderPay.this);
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
 	    Toast.makeText(GroupOrderPay.this, msg, Toast.LENGTH_SHORT)
 		    .show();
 	    if (result.equals("1")) {
 		startActivity(new Intent(GroupOrderPay.this,
 			OrderListMain.class));
 		GroupOrderPay.this.finish();
 	    }
 	}

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
	case R.id.group_order_info_fl:
	    if (listDisplay.equals("1")) {
		listDisplay = "0";
		lv.setVisibility(View.GONE);
		infoIV.setImageResource(R.drawable.up_dark);
	    } else {
		listDisplay = "1";
		lv.setVisibility(View.VISIBLE);
		infoIV.setImageResource(R.drawable.down_dark);
	    }
	    break;
	case R.id.group_order_address:
	    new PopupWindowsAddr(this, parent);
	    break;
	case R.id.group_order_coupon_rl:
	    if (yhqStatus.equals("0")) {
		Toast.makeText(GroupOrderPay.this, "����û���Ż�ȯ��",
			Toast.LENGTH_SHORT).show();
	    } else {
		new PopupWindowsCoupon(this, parent);
	    }
	    break;
	case R.id.group_order_balance_rl:
	    payMethod = "3";
	    balanceIV.setImageResource(R.drawable.select_pic);
	    alipayIV.setImageResource(R.drawable.designer_unselect);
	    break;
	case R.id.group_order_alipay_rl:
	    payMethod = "1";
	    balanceIV.setImageResource(R.drawable.designer_unselect);
	    alipayIV.setImageResource(R.drawable.select_pic);
	    break;
	case R.id.group_order_pay:
	    if(payButton.equals("1")){
		if(validation()){
		    new PostAsync().execute();
		}
	    }
	    break;
	default:
	    break;
	}
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
	    pd = CustomProgressDialog.createDialog(GroupOrderPay.this);
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
		    + new Tools().getUserId(GroupOrderPay.this);
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

    class PopupWindowsCoupon extends PopupWindow {

	public PopupWindowsCoupon(Context mContext, View parent) {

	    View view = View.inflate(mContext, R.layout.my_coupon, null);
	    view.startAnimation(AnimationUtils.loadAnimation(mContext,
		    R.anim.push_bottom_in_2));
	    ImageView back = (ImageView) view.findViewById(R.id.title_back);
	    TextView title = (TextView) view.findViewById(R.id.title_text);
	    title.setText("ѡ���Ż�ȯ");
	    // TextView add = (TextView)
	    // view.findViewById(R.id.dialog_view_add);
	    ListView lv = (ListView) view.findViewById(R.id.my_coupon_lv);

	    // lv.startAnimation(AnimationUtils.loadAnimation(mContext,
	    // R.anim.push_bottom_in_2));

	    setWidth(LayoutParams.FILL_PARENT);
	    setHeight(LayoutParams.FILL_PARENT);
	    //setBackgroundDrawable(new BitmapDrawable());//
	    setFocusable(true);
	    setOutsideTouchable(true);
	    setContentView(view);
	    showAtLocation(parent, Gravity.CENTER, 0, 0);
	    update();

	    MyCouponListAdapter adapter = new MyCouponListAdapter(yhqList,
		    GroupOrderPay.this);
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
	    setHeight(LayoutParams.FILL_PARENT);
	    //setBackgroundDrawable(new BitmapDrawable());
	    setFocusable(true);
	    setOutsideTouchable(true);
	    setContentView(view);
	    showAtLocation(parent, Gravity.CENTER, 0, 0);
	    update();

	    adapter = new AddressListAdapter(addrList, GroupOrderPay.this);
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
    
    private boolean validation(){
	if(phone.getText().toString().length() == 0){
	    Toast.makeText(GroupOrderPay.this, "����д�绰���룡", Toast.LENGTH_SHORT).show();
	    return false;
	}else if(phone.getText().toString().length() != 11){
	    Toast.makeText(GroupOrderPay.this, "����д��ȷ��11λ�绰��", Toast.LENGTH_SHORT).show();
	    return false;
	}else if(addr.getText().toString().length() == 0){
	    Toast.makeText(GroupOrderPay.this, "��ѡ���ַ��", Toast.LENGTH_SHORT).show();
	    return false;
	}else{
	    return true;
	}
    }
    
    

}
