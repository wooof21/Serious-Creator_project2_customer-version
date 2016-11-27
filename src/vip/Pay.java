/**
 * 
 */
package vip;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

import order.GroupOrderPay;

import org.json.JSONException;
import org.json.JSONObject;

import tools.Config;
import tools.CustomProgressDialog;
import tools.Exit;
import tools.Tools;

import com.alipay.sdk.app.PayTask;
import com.mkcomingc.R;

import alipay.Keys;
import alipay.PayResult;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Liming Chu
 * 
 * @param
 * @return
 */
public class Pay extends Activity {

    private ImageView back;
    private TextView title, get, total, intro, submit;
    private String totalPay, orderNum, alipayOrderInfo;

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.my_recharge_detail);
	Exit.getInstance().addActivity(this);
	prepareView();
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
		pd = CustomProgressDialog.createDialog(Pay.this);
		pd.show();
		orderNum = "N" + System.currentTimeMillis();
	    }

	    @Override
	    protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		String url = Config.RECHARGE_DETAIL_URL + params[0];
		Log.e("url", url);
		String data = new Tools().getURL(url);
		System.out.println(data);
		String code = "";
		try {
		    JSONObject _data = new JSONObject(data);
		    JSONObject result = _data.getJSONObject("result");
		    code = result.getString("code");
		    if (code.equals("1")) {
			JSONObject job = _data.getJSONObject("data");
			HashMap<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("title", job.getString("title"));
			hashMap.put("je", job.getString("je"));
			hashMap.put("contents", job.getString("contents"));
			hashMap.put("zsje", job.getString("zsje"));
			totalPay = job.getString("je");
			
			Message msg = handler.obtainMessage();
			msg.obj = hashMap;
			msg.sendToTarget();
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

		} else {
		    Toast.makeText(Pay.this, "", Toast.LENGTH_SHORT).show();
		    Pay.this.finish();
		}
	    }
	}.execute(getIntent().getExtras().getString("id"));
    }

    private void prepareView() {
	back = (ImageView) findViewById(R.id.title_back);
	title = (TextView) findViewById(R.id.my_recharge_detail_title);
	get = (TextView) findViewById(R.id.my_recharge_detail_amount);
	intro = (TextView) findViewById(R.id.my_recharge_center_intro);
	total = (TextView) findViewById(R.id.my_recharge_center_total);
	submit = (TextView) findViewById(R.id.my_recharge_center_pay);

	back.setOnClickListener(new OnClickListener() {

	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub
		finish();
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
		final String payInfo = alipayOrderInfo
			+ "&sign=\"" + sign + "\"&"
			+ Keys.getSignType();
		System.out.println("payInfo ---> " + payInfo);
		Runnable payRunnable = new Runnable() {

		    @Override
		    public void run() {
			// 构造PayTask 对象
			PayTask alipay = new PayTask(
				Pay.this);
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
	});
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
		    Toast.makeText(Pay.this, "支付成功",
			    Toast.LENGTH_SHORT).show();
		} else {
		    // 判断resultStatus 为非“9000”则代表可能支付失败
		    // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
		    if (TextUtils.equals(resultStatus, "8000")) {
			Toast.makeText(Pay.this, "支付结果确认中",
				Toast.LENGTH_SHORT).show();

		    } else {
			// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
			Toast.makeText(Pay.this, "支付失败",
				Toast.LENGTH_SHORT).show();

		    }
		}
		break;

	    default:
		break;
	    }
	}

    };
    
    private Handler handler = new Handler() {

	@Override
	public void handleMessage(Message msg) {
	    // TODO Auto-generated method stub
	    super.handleMessage(msg);
	    HashMap<String, String> hashMap = (HashMap<String, String>) msg.obj;
	    title.setText(hashMap.get("title"));
	    get.setText(hashMap.get("zsje"));
	    intro.setText(hashMap.get("contents"));
	    total.setText(hashMap.get("je") + "元");
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
	sb.append("\"" + "Android美咖来了充值支付" + "\"");
	sb.append("&body=");
	sb.append("\"" + "美咖来了充值支付" + "\"");
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
}
