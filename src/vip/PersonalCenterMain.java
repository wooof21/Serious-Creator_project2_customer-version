/**
 * 
 */
package vip;

import java.io.File;
import java.util.HashMap;

import login.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import tools.Config;
import tools.CustomProgressDialog;
import tools.Exit;
import tools.Tools;

import com.mkcomingc.BaseActivity;
import com.mkcomingc.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaCodecInfo.CodecCapabilities;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Liming Chu
 * 
 * @param
 * @return
 */
public class PersonalCenterMain extends BaseActivity implements OnClickListener {

    private ImageView pic;
    private TextView name;
    private TextView phone;
    private TextView balance;
    private TextView recharge;
    private TextView info;
    private TextView coupon;
    private TextView collection;
    private TextView apply;
    private TextView help;
    private TextView quit;
    private DisplayImageOptions options;
    private TextView dial;
    private TextView dhk;
    private String cztb;

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
	setContentView(R.layout.personal_center_main);
	Exit.getInstance().addActivity(this);
	prepareView();

	new AsyncTask<Void, Void, String>() {
	    private CustomProgressDialog pd;

	    @Override
	    protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		pd = CustomProgressDialog.createDialog(PersonalCenterMain.this);
		pd.show();
	    }

	    @Override
	    protected String doInBackground(Void... params) {
		// TODO Auto-generated method stub
		String url = Config.PERSONAL_CENTER_MAIN_URL
			+ new Tools().getUserId(PersonalCenterMain.this);
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
			hashMap.put("nc", job.getString("nc"));
			hashMap.put("dh", job.getString("dh"));
			hashMap.put("ye", job.getString("ye"));
			hashMap.put("pic", job.getString("pic"));
			hashMap.put("cztb", job.getString("cztb"));
			
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

	    @Override
	    protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		pd.dismiss();
		if (result.equals("1")) {

		} else {
		    Toast.makeText(PersonalCenterMain.this, "获取个人资料失败，请重试！",
			    Toast.LENGTH_SHORT).show();
		    PersonalCenterMain.this.finish();
		}
	    }
	}.execute();
    }

    private Handler handler = new Handler() {

	@Override
	public void handleMessage(Message msg) {
	    // TODO Auto-generated method stub
	    super.handleMessage(msg);
	    HashMap<String, String> hashMap = (HashMap<String, String>) msg.obj;
	    ImageLoader.getInstance().displayImage(
		    Config.URL + hashMap.get("pic"), pic, options);
	    name.setText(hashMap.get("nc"));
	    phone.setText(hashMap.get("dh"));
	    balance.setText("￥" + hashMap.get("ye"));
	    if(hashMap.get("cztb").equals("0")){
		
	    }else{
		recharge.setOnClickListener(new OnClickListener() {
		    
		    @Override
		    public void onClick(View v) {
			// TODO Auto-generated method stub
			recharge.setBackgroundResource(R.drawable.tvbg_pink);
			startActivity(new Intent(PersonalCenterMain.this, RechargeCenter.class));
		    }
		});
	    }
	}

    };

    private void prepareView() {
	pic = (ImageView) findViewById(R.id.personal_center_pic);
	name = (TextView) findViewById(R.id.personal_center_name);
	phone = (TextView) findViewById(R.id.personal_center_phone);
	balance = (TextView) findViewById(R.id.personal_center_balance);
	recharge = (TextView) findViewById(R.id.personal_center_recharge);
	info = (TextView) findViewById(R.id.vip_center_my_info);
	coupon = (TextView) findViewById(R.id.vip_center_my_coupon);
	collection = (TextView) findViewById(R.id.vip_center_my_collection);
	apply = (TextView) findViewById(R.id.vip_center_my_apply);
	help = (TextView) findViewById(R.id.vip_center_my_help);
	quit = (TextView) findViewById(R.id.vip_center_my_quit);
	dial = (TextView) findViewById(R.id.vip_center_my_dial);
	dhk = (TextView) findViewById(R.id.vip_center_my_dhk);
	
	dhk.setOnClickListener(this);
	dial.setOnClickListener(this);
	recharge.setOnClickListener(this);
	info.setOnClickListener(this);
	coupon.setOnClickListener(this);
	collection.setOnClickListener(this);
	apply.setOnClickListener(this);
	help.setOnClickListener(this);
	quit.setOnClickListener(this);

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
	case R.id.vip_center_my_dial:
	    Intent intent = new Intent();
	    intent.setAction("android.intent.action.DIAL");
	    intent.setData(Uri.parse("tel:400-107-1377"));
	    startActivity(intent);
	    break;
	case R.id.personal_center_recharge:
	    // startActivity();
	    //startActivity(new Intent(this, RechargeCenter.class));
	    break;
	case R.id.vip_center_my_info:
	    startActivity(new Intent(this, MyRegularInfo.class));
	    break;
	case R.id.vip_center_my_coupon:
	    startActivity(new Intent(this, MyCoupon.class));
	    break;
	case R.id.vip_center_my_collection:
	    startActivity(new Intent(this, MyCollection.class));
	    break;
	case R.id.vip_center_my_apply:
	    startActivity(new Intent(this, MyResume.class));
	    break;
	case R.id.vip_center_my_help:
	    startActivity(new Intent(this, HelpCenter.class));
	    break;
	case R.id.vip_center_my_dhk:
	    startActivity(new Intent(this, DHK.class));
	    break;
	case R.id.vip_center_my_quit:
	    AlertDialog.Builder builder = new AlertDialog.Builder(this);

	    builder.setMessage("是否确定退出账号");
	    builder.setPositiveButton("确定",
		    new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,
				int whichButton) {

			    SharedPreferences sharedPre = PersonalCenterMain.this
				    .getSharedPreferences("config",
					    Context.MODE_PRIVATE);
			    SharedPreferences.Editor editor = sharedPre.edit();

			    editor.remove("username");
			    editor.remove("password");
			    editor.remove("id");
			    editor.clear();
			    editor.commit();

			    // Intent intent = getIntent();
			    // setResult(ResultCode.SUCCESS, intent);
			    startActivity(new Intent(PersonalCenterMain.this,
				    LoginActivity.class));
			    finish();
			}
		    });
	    builder.setNegativeButton("取消",
		    new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
			    // TODO Auto-generated method stub
			    dialog.dismiss();
			}
		    });
	    builder.show();

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
