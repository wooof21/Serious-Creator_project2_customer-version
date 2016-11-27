/**
 * 
 */
package order;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import tools.Config;
import tools.CustomProgressDialog;
import tools.Exit;
import tools.Tools;
import view.MarqueeTV;

import com.mkcomingc.BaseActivity;
import com.mkcomingc.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Liming Chu
 * 
 * @param
 * @return
 */
public class OrderStatusMain extends BaseActivity implements OnClickListener {

    private ImageView back;
    private ImageView pic;
    private TextView name;
    private TextView style;
    private TextView price;
    private TextView timeCount;
    private TextView keep;
    private TextView designerPhone;
    private ImageView dial;
    private TextView phone;
    private MarqueeTV address;
    private TextView dt;
    private TextView remark;
    private TextView coupon;
    private TextView total;
    private TextView timeTitle;
    private TextView serveTime;
    private TextView button;
    private LinearLayout replace;
    private TextView orderId;
    private TextView level;

    private int hr, min, sec;
    private boolean isRun = true;

    
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
	setContentView(R.layout.order_status);
	Exit.getInstance().addActivity(this);
	prepareView();

	new InfoAsync().execute(getIntent().getExtras().getString("id"));
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
	    pd = CustomProgressDialog.createDialog(OrderStatusMain.this);
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
	    String url = Config.ORDER_LIST_DETAIL_URL + params[0] + "&uid="
		    + new Tools().getUserId(OrderStatusMain.this);
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
		    hashMap.put("xm", job.getString("xm"));
		    hashMap.put("fgid", job.getString("fgid"));
		    hashMap.put("zrid", job.getString("zrid"));
		    hashMap.put("jg", job.getString("jg"));
		    hashMap.put("hs", job.getString("hs"));
		    hashMap.put("dh", job.getString("dh"));
		    hashMap.put("yysj", job.getString("yysj"));
		    hashMap.put("dz", job.getString("dz"));
		    hashMap.put("bz", job.getString("bz"));
		    hashMap.put("yhqje", job.getString("yhqje"));
		    hashMap.put("bx_xm", job.getString("bx_xm"));
		    hashMap.put("bx_sfz", job.getString("bx_sfz"));
		    hashMap.put("je", job.getString("je"));
		    hashMap.put("num", job.getString("num"));
		    hashMap.put("zt", job.getString("zt"));
		    hashMap.put("jlfwsc", job.getString("jlfwsc"));
		    hashMap.put("fwsc", job.getString("fwsc"));
		    hashMap.put("yfwsc", job.getString("yfwsc"));
		    hashMap.put("dj", job.getString("dj"));
		    hashMap.put("zxs_dh", job.getString("zxs_dh"));
		    hashMap.put("kssj", job.getString("kssj"));

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
	    if (!result.equals("1")) {
		Toast.makeText(OrderStatusMain.this, "获取订单信息失败，请重试！",
			Toast.LENGTH_SHORT).show();
		finish();
	    }
	}

    }

    private Handler handler = new Handler() {

	@Override
	public void handleMessage(Message msg) {
	    // TODO Auto-generated method stub
	    super.handleMessage(msg);
	    HashMap<String, String> hashMap = (HashMap<String, String>) msg.obj;
	    if(hashMap.get("pic").startsWith("http")){
		ImageLoader.getInstance().displayImage(hashMap.get("pic"), pic, options);
	    }else{
		ImageLoader.getInstance().displayImage(
			    Config.URL + hashMap.get("pic"), pic, options);
	    }
	    
	    name.setText(hashMap.get("xm"));
	    style.setText(hashMap.get("fgid") + "-" + hashMap.get("zrid"));
	    price.setText("￥" + hashMap.get("jg"));
	    level.setText("(" + hashMap.get("dj") + ")");
	    timeCount.setText(hashMap.get("hs"));
	    designerPhone.setText(hashMap.get("zxs_dh"));
	    phone.setText(hashMap.get("dh"));
	    dt.setText(hashMap.get("yysj"));
	    address.setText(hashMap.get("dz"));
	    remark.setText(hashMap.get("bz"));
	    orderId.setText(hashMap.get("num"));
	    coupon.setText(hashMap.get("yhqje"));
	    total.setText(hashMap.get("je") + "元");
	    if (hashMap.get("zt").equals("2")) {
		timeTitle.setText("距离服务时间：");
		serveTime.setText(hashMap.get("jlfwsc"));
		button.setOnClickListener(new OnClickListener() {

		    @Override
		    public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
		    }
		});
	    } else if (hashMap.get("zt").equals("4")) {
		timeTitle.setText("服务时长：");
		serveTime.setText(hashMap.get("fwsc"));
		button.setText("评价");
		button.setBackgroundColor(Color.rgb(255, 155, 0));
		button.setOnClickListener(new OnClickListener() {

		    @Override
		    public void onClick(View v) {
			// TODO Auto-generated method stub

		    }
		});
	    } else {
		long yfwsc = Long.valueOf(hashMap.get("yfwsc"));
		long kssj = Long.valueOf(hashMap.get("kssj"));
		long time = yfwsc - kssj;
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Date d = new Date(time);
		Log.e("yfwsc", sdf.format(d));
		String[] t = sdf.format(d).split(":");
		hr = Integer.valueOf(t[0]);
		min = Integer.valueOf(t[1]);
		sec = Integer.valueOf(t[2]);
		timeTitle.setText("服务时长：");
		serveTime.setText(sdf.format(d));
		startRun();
		button.setOnClickListener(new OnClickListener() {

		    @Override
		    public void onClick(View v) {
			// TODO Auto-generated method stub
			finish();
		    }
		});
	    }
	}

    };

    /**
     * 计时计算
     */
    private void computeTime() {
	sec++;
	if (sec > 59) {
	    min++;
	    sec = 0;
	    if (min > 59) {
		min = 0;
		hr++;
		if (hr > 23) {
		    // 倒计时结束
		    hr = 0;
		}
	    }
	}
    }

    /**
     * 开启计时
     */
    private void startRun() {
	new Thread(new Runnable() {

	    @Override
	    public void run() {
		// TODO Auto-generated method stub
		while (isRun) {
		    try {
			Thread.sleep(1000); // sleep 1000ms
			Message message = Message.obtain();
			message.what = 1;
			timeHandler.sendMessage(message);
		    } catch (Exception e) {
			e.printStackTrace();
		    }
		}
	    }
	}).start();
    }

    private Handler timeHandler = new Handler() {

	@Override
	public void handleMessage(Message msg) {
	    super.handleMessage(msg);
	    switch (msg.what) {
	    case 1:
		computeTime();
		serveTime.setText(hr + " : " + min + " : " + sec);
		if (hr == 23 && min == 59 && sec == 59) {
		    serveTime.setText("服务超时！");
		}
		break;
	    default:
		break;
	    }
	}
    };

    private DisplayImageOptions options;

    private void prepareView() {
	back = (ImageView) findViewById(R.id.title_back);
	pic = (ImageView) findViewById(R.id.order_status_pic);
	name = (TextView) findViewById(R.id.order_status_name);
	style = (TextView) findViewById(R.id.order_status_style);
	price = (TextView) findViewById(R.id.order_status_price);
	timeCount = (TextView) findViewById(R.id.order_status_time_count);
	keep = (TextView) findViewById(R.id.order_status_keep);
	designerPhone = (TextView) findViewById(R.id.order_status_designer_phone);
	dial = (ImageView) findViewById(R.id.order_status_dial);
	phone = (TextView) findViewById(R.id.order_status_phone);
	address = (MarqueeTV) findViewById(R.id.order_status_address);
	dt = (TextView) findViewById(R.id.order_status_dt);
	remark = (TextView) findViewById(R.id.order_status_remark);
	coupon = (TextView) findViewById(R.id.order_status_coupon);
	total = (TextView) findViewById(R.id.order_status_total);
	orderId = (TextView) findViewById(R.id.order_status_orderid);
	level = (TextView) findViewById(R.id.order_status_level);

	timeTitle = (TextView) findViewById(R.id.order_status_time_title);
	serveTime = (TextView) findViewById(R.id.order_status_sever_time);
	button = (TextView) findViewById(R.id.order_status_button);
	replace = (LinearLayout) findViewById(R.id.order_status_time_replace);

	back.setOnClickListener(this);
	dial.setOnClickListener(this);
	button.setOnClickListener(this);

	preperImageLoader();
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
	case R.id.order_status_dial:
	    Intent intent = new Intent();
	    intent.setAction("android.intent.action.DIAL");
	    intent.setData(Uri.parse("tel:" + phone.getText().toString()));
	    startActivity(intent);
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
}
