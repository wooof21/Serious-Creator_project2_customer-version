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
import vip.Pay;

import com.alipay.sdk.app.PayTask;
import com.mkcomingc.BaseActivity;
import com.mkcomingc.R;

import adapter.OrderListAdapter;
import alipay.Keys;
import alipay.PayResult;
import android.app.Activity;
import android.content.Context;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Liming Chu
 * 
 * @param
 * @return
 */
public class OrderListMain extends BaseActivity implements OnClickListener {

    private TextView order;
    private TextView wait;
    private TextView done;
    private ListView lv;

    private TextView underline1, underline2, underline3;
    private RelativeLayout parent;

    private ArrayList<HashMap<String, String>> list;
    private String totalPay, orderNum, alipayOrderInfo;

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
	setContentView(R.layout.order_list_main);
	Exit.getInstance().addActivity(this);
	prepareVIew();

	new OrderAsync().execute("1");
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onResume()
     */
    @Override
    protected void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	setUnderLine(R.id.order_list_main_order);
	new OrderAsync().execute("1");

    }

    private Handler handler = new Handler() {

	@Override
	public void handleMessage(Message msg) {
	    // TODO Auto-generated method stub
	    super.handleMessage(msg);
	    switch (msg.what) {
	    case 1:
		setUnderLine(R.id.order_list_main_order);
		new OrderAsync().execute("1");
		break;
	    case 2:
		String oid = (String) msg.obj;
		new ZjAsync().execute(oid);
		break;
	    default:
		break;
	    }
	}

    };

    class PopupWindows extends PopupWindow {

	public PopupWindows(Context mContext, View parent, String zjje,
		String zrid) {

	    View view = View.inflate(mContext, R.layout.wy_submit, null);
	    view.startAnimation(AnimationUtils.loadAnimation(mContext,
		    R.anim.push_bottom_in_2));
	    ImageView back = (ImageView) view.findViewById(R.id.title_back);
	    TextView submit = (TextView) view.findViewById(R.id.wyb_ok);
	    TextView style = (TextView) view.findViewById(R.id.zj_style);
	    TextView amount = (TextView) view.findViewById(R.id.zj_amount);

	    style.setText(zrid);
	    amount.setText(zjje + "元");
	    totalPay = zjje;

	    setWidth(LayoutParams.FILL_PARENT);
	    setHeight(LayoutParams.FILL_PARENT);
	    // setBackgroundDrawable(new BitmapDrawable());
	    setFocusable(true);
	    setOutsideTouchable(true);
	    setContentView(view);
	    showAtLocation(parent, Gravity.CENTER, 0, 0);
	    update();

	    back.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
		    // TODO Auto-generated method stub
		    dismiss();
		}
	    });

	    submit.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
		    // TODO Auto-generated method stub
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
		    System.out.println("payInfo ---> " + payInfo);
		    Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
			    // 构造PayTask 对象
			    PayTask alipay = new PayTask(OrderListMain.this);
			    // 调用支付接口，获取支付结果
			    String result = alipay.pay(payInfo);
			    System.out.println("ali result ---> " + result);
			    Message msg = aliHandler.obtainMessage();
			    msg.what = 1;
			    msg.obj = result;
			    msg.sendToTarget();
			}
		    };

		    // 必须异步调用
		    Thread payThread = new Thread(payRunnable);
		    payThread.start();
		    dismiss();
		}
	    });

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
		    Toast.makeText(OrderListMain.this, "支付成功",
			    Toast.LENGTH_SHORT).show();
		    new OrderAsync().execute("1");
		} else {
		    // 判断resultStatus 为非“9000”则代表可能支付失败
		    // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
		    if (TextUtils.equals(resultStatus, "8000")) {
			Toast.makeText(OrderListMain.this, "支付结果确认中",
				Toast.LENGTH_SHORT).show();

		    } else {
			// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
			Toast.makeText(OrderListMain.this, "支付失败",
				Toast.LENGTH_SHORT).show();

		    }
		}
		break;

	    default:
		break;
	    }
	}

    };

    class ZjAsync extends AsyncTask<String, Void, String> {
	private CustomProgressDialog pd;
	private String zjje, zrid;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	@Override
	protected void onPreExecute() {
	    // TODO Auto-generated method stub
	    super.onPreExecute();
	    pd = CustomProgressDialog.createDialog(OrderListMain.this);
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
	    String url = Config.ZJ_URL + params[0] + "&uid="
		    + new Tools().getUserId(OrderListMain.this);
	    Log.e("url", url);
	    String data = new Tools().getURL(url);
	    System.err.println(data);
	    String code = "";

	    try {
		JSONObject job = new JSONObject(data);
		JSONObject result = job.getJSONObject("result");
		code = result.getString("code");
		JSONObject zj = job.getJSONObject("data");
		zjje = zj.getString("zjje");
		zrid = zj.getString("zrid");
		orderNum = zj.getString("num");
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
		new PopupWindows(OrderListMain.this, parent, zjje, zrid);
	    } else {
		Toast.makeText(OrderListMain.this, "读取追加信息失败，请重试！",
			Toast.LENGTH_SHORT).show();
	    }
	}

    }

    private String AliPayOrder() {
	StringBuilder sb = new StringBuilder();
	sb.append("partner=");
	sb.append("\"" + Keys.PARTNER + "\"");
	sb.append("&seller_id=");
	sb.append("\"" + Keys.SELLER + "\"");
	sb.append("&out_trade_no=");
	sb.append("\"" + orderNum + "\"");
	sb.append("&subject=");
	sb.append("\"" + "Android美咖来了追加支付" + "\"");
	sb.append("&body=");
	sb.append("\"" + "美咖来了追加支付" + "\"");
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

    private void prepareVIew() {
	order = (TextView) findViewById(R.id.order_list_main_order);
	wait = (TextView) findViewById(R.id.order_list_main_waiting);
	done = (TextView) findViewById(R.id.order_list_main_done);
	lv = (ListView) findViewById(R.id.order_list_main_lv);
	underline1 = (TextView) findViewById(R.id.order_list_main_underline1);
	underline2 = (TextView) findViewById(R.id.order_list_main_underline2);
	underline3 = (TextView) findViewById(R.id.order_list_main_underline3);
	parent = (RelativeLayout) findViewById(R.id.order_list_parent);
	order.setOnClickListener(this);
	wait.setOnClickListener(this);
	done.setOnClickListener(this);
    }

    class OrderAsync extends AsyncTask<String, Void, String> {

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
	    pd = CustomProgressDialog.createDialog(OrderListMain.this);
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
	    String url = Config.MY_ORDER_LIST_URL
		    + new Tools().getUserId(OrderListMain.this) + "&lei="
		    + params[0];
	    Log.e("url", url);
	    String data = new Tools().getURL(url);
	    System.out.println(data);
	    String code = "";
	    list = new ArrayList<HashMap<String, String>>();

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
			hashMap.put("pic", job.getString("pic"));
			hashMap.put("xm", job.getString("xm"));
			hashMap.put("fgid", job.getString("fgid"));
			hashMap.put("fgid1", job.getString("fgid1"));
			hashMap.put("zrid", job.getString("zrid"));
			hashMap.put("je", job.getString("je"));
			hashMap.put("yysj", job.getString("yysj"));
			hashMap.put("bz", job.getString("bz"));
			hashMap.put("dz", job.getString("dz"));
			hashMap.put("fwsc", job.getString("fwsc"));
			hashMap.put("jlqxsj", job.getString("jlqxsj"));
			hashMap.put("jlfwsj", job.getString("jlfwsj"));
			hashMap.put("yfwsc", job.getString("yfwsc"));
			hashMap.put("dh", job.getString("dh"));
			hashMap.put("num", job.getString("num"));
			hashMap.put("zt", job.getString("zt"));
			hashMap.put("dj", job.getString("dj"));
			hashMap.put("zid", job.getString("zid"));
			hashMap.put("zrid1", job.getString("zrid1"));
			hashMap.put("lei", job.getString("lei"));

			list.add(hashMap);
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
	    OrderListAdapter adapter = new OrderListAdapter(list,
		    OrderListMain.this, handler);
	    lv.setAdapter(adapter);
//	    if (result.equals("1")) {
//
//	    } else {
//		Toast.makeText(OrderListMain.this, "暂无订单信息！",
//			Toast.LENGTH_SHORT).show();
//	    }
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
	case R.id.order_list_main_order:
	    setUnderLine(R.id.order_list_main_order);
	    new OrderAsync().execute("1");
	    break;
	case R.id.order_list_main_waiting:
	    setUnderLine(R.id.order_list_main_waiting);
	    new OrderAsync().execute("2");
	    break;
	case R.id.order_list_main_done:
	    setUnderLine(R.id.order_list_main_done);
	    new OrderAsync().execute("3");
	    break;
	default:
	    break;
	}
    }

    private void setUnderLine(int id) {
	switch (id) {
	case R.id.order_list_main_order:
	    underline1.setVisibility(View.VISIBLE);
	    underline2.setVisibility(View.INVISIBLE);
	    underline3.setVisibility(View.INVISIBLE);
	    break;
	case R.id.order_list_main_waiting:
	    underline2.setVisibility(View.VISIBLE);
	    underline1.setVisibility(View.INVISIBLE);
	    underline3.setVisibility(View.INVISIBLE);
	    break;
	case R.id.order_list_main_done:
	    underline3.setVisibility(View.VISIBLE);
	    underline2.setVisibility(View.INVISIBLE);
	    underline1.setVisibility(View.INVISIBLE);
	    break;
	default:
	    break;
	}
    }
}
