/**
 * 
 */
package adapter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import order.GroupOrderPay;
import order.OrderStatusMain;
import order.ShareResult;

import org.json.JSONException;
import org.json.JSONObject;

import tools.Config;
import tools.CustomProgressDialog;
import tools.Tools;

import com.mkcomingc.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import designers.CommentMain;
import designers.DesignerInfo;
import designers.DesignerReserve;
import designers.DesignerReserve1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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
public class OrderListAdapter extends BaseAdapter {

    private ArrayList<HashMap<String, String>> list;
    private Context context;
    private LayoutInflater lInflater;
    private DisplayImageOptions options;
    private Handler handler;

    public OrderListAdapter(ArrayList<HashMap<String, String>> list,
	    Context context, Handler handler) {
	super();
	this.list = list;
	this.context = context;
	this.handler = handler;
	this.lInflater = LayoutInflater.from(context);
	preperImageLoader();
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.widget.Adapter#getCount()
     */
    @Override
    public int getCount() {
	// TODO Auto-generated method stub
	return list.size();
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.widget.Adapter#getItem(int)
     */
    @Override
    public Object getItem(int position) {
	// TODO Auto-generated method stub
	return list.get(position);
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.widget.Adapter#getItemId(int)
     */
    @Override
    public long getItemId(int position) {
	// TODO Auto-generated method stub
	return position;
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.widget.Adapter#getView(int, android.view.View,
     * android.view.ViewGroup)
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
	// TODO Auto-generated method stub
	ViewHolder vHolder = new ViewHolder();
	if (convertView == null) {
	    convertView = lInflater
		    .inflate(R.layout.order_list_main_item, null);
	}
	vHolder.num = (TextView) convertView
		.findViewById(R.id.order_list_item_num);
	vHolder.ll = (LinearLayout) convertView
		.findViewById(R.id.order_list_item_dt_ll);
	vHolder.rl = (RelativeLayout) convertView
		.findViewById(R.id.order_list_item_bottom_rl);
	vHolder.type = (TextView) convertView
		.findViewById(R.id.order_list_item_type);
	vHolder.status = (TextView) convertView
		.findViewById(R.id.order_list_item_status);
	vHolder.pic = (ImageView) convertView
		.findViewById(R.id.order_list_item_pic);
	vHolder.name = (TextView) convertView
		.findViewById(R.id.order_list_item_name);
	vHolder.phone = (TextView) convertView
		.findViewById(R.id.order_list_item_phone);
	vHolder.price = (TextView) convertView
		.findViewById(R.id.order_list_item_price);
	vHolder.level = (TextView) convertView
		.findViewById(R.id.order_list_item_level);
	vHolder.level.setVisibility(View.GONE);
	vHolder.dt = (TextView) convertView
		.findViewById(R.id.order_list_item_dt);
	vHolder.address = (TextView) convertView
		.findViewById(R.id.order_list_item_address);
	vHolder.time = (TextView) convertView
		.findViewById(R.id.order_list_item_time);
	vHolder.timeTitle = (TextView) convertView
		.findViewById(R.id.order_list_item_time_title);
	vHolder.button1 = (TextView) convertView
		.findViewById(R.id.order_list_item_button1);
	vHolder.button2 = (TextView) convertView
		.findViewById(R.id.order_list_item_button2);
	vHolder.remark = (TextView) convertView.findViewById(R.id.order_list_item_remark);

	final HashMap<String, String> hashMap = list.get(position);

	if (hashMap.get("pic").startsWith("http")) {
	    ImageLoader.getInstance().displayImage(hashMap.get("pic"),
		    vHolder.pic, options);
	} else {
	    ImageLoader.getInstance().displayImage(
		    Config.URL + hashMap.get("pic"), vHolder.pic, options);
	}
	
	vHolder.remark.setText(hashMap.get("bz"));

	if (hashMap.get("lei").equals("3") && hashMap.get("zt").equals("0")) {
	    vHolder.type.setText("团体妆");
	    vHolder.status.setText("提交需求，等待确认");
	    vHolder.num.setText(hashMap.get("num"));

	    vHolder.price.setText("待定");
	    vHolder.phone.setText("暂无");
	    vHolder.dt.setText(hashMap.get("yysj"));
	    vHolder.address.setText(hashMap.get("dz"));
	    vHolder.button1.setVisibility(View.GONE);
	    // vHolder.button2.setText("取消订单");
	    // vHolder.button2.setBackgroundColor(Color.rgb(193, 193, 193));
	    // vHolder.button2.setPadding(8, 8, 8, 8);
	    vHolder.button2.setBackgroundResource(R.drawable.button_qxdd);
	    vHolder.ll.setVisibility(View.VISIBLE);
	    vHolder.timeTitle.setVisibility(View.VISIBLE);
	    vHolder.time.setVisibility(View.VISIBLE);
	    vHolder.timeTitle.setText("距离服务时间");
	    vHolder.time.setText(hashMap.get("jlfwsj"));
	    vHolder.button2.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
		    // TODO Auto-generated method stub
		    new CancelAsync().execute(hashMap.get("id"));
		}
	    });
	} else if (hashMap.get("lei").equals("3")
		&& hashMap.get("zt").equals("1")) {
	    vHolder.type.setText("团体妆");
	    vHolder.status.setText("已确认，等待付款");
	    vHolder.num.setText(hashMap.get("num"));
	    vHolder.price.setText(hashMap.get("je"));
	    vHolder.phone.setText(hashMap.get("dh"));
	    vHolder.dt.setText(hashMap.get("yysj"));
	    vHolder.address.setText(hashMap.get("dz"));
	    vHolder.button1.setVisibility(View.GONE);
	    // vHolder.button2.setText("立即支付");
	    // vHolder.button2.setBackgroundColor(Color.rgb(35, 177, 44));
	    // vHolder.button2.setPadding(8, 8, 8, 8);
	    vHolder.button2.setBackgroundResource(R.drawable.button_ljzf);
	    vHolder.ll.setVisibility(View.VISIBLE);
	    vHolder.timeTitle.setVisibility(View.VISIBLE);
	    vHolder.time.setVisibility(View.VISIBLE);
	    vHolder.timeTitle.setText("距离服务时间");
	    vHolder.time.setText(hashMap.get("jlfwsj"));
	    vHolder.button2.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
		    // TODO Auto-generated method stub
		    // 立即支付
		    Intent intent = new Intent(context, GroupOrderPay.class);
		    intent.putExtra("oid", hashMap.get("id"));
		    context.startActivity(intent);
		}
	    });
	} else {
	    vHolder.num.setText(hashMap.get("num"));
	    vHolder.type.setText(hashMap.get("fgid") + "-"
		    + hashMap.get("zrid"));
	    vHolder.status.setText(new Map().ztMap.get(hashMap.get("zt")));
	    vHolder.name.setText(hashMap.get("xm"));
	    vHolder.phone.setText(hashMap.get("dh"));
	    vHolder.price.setText("￥" + hashMap.get("je"));
	    if (hashMap.get("lei").equals("2")) {
		vHolder.level.setVisibility(View.VISIBLE);
		vHolder.level.setText("(" + hashMap.get("dj") + ")");
	    }
	    vHolder.dt.setText(hashMap.get("yysj"));
	    vHolder.address.setText(hashMap.get("dz"));

	    if (hashMap.get("zt").equals("3")
		    && !hashMap.get("lei").equals("3")) {
		vHolder.button1.setVisibility(View.GONE);
		vHolder.button2.setPadding(20, 8, 20, 8);
		vHolder.time.setText(hashMap.get("fwsc"));
		vHolder.button2.setOnClickListener(new OnClickListener() {

		    @Override
		    public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(context,
				OrderStatusMain.class);
			intent.putExtra("id", hashMap.get("id"));
			intent.putExtra("zt", "3");
			context.startActivity(intent);
		    }
		});
	    } else if (hashMap.get("zt").equals("0")) {
		vHolder.button1.setVisibility(View.GONE);
		// vHolder.button2.setText("取消订单");
		// vHolder.button2.setBackgroundColor(Color.rgb(193, 193, 193));
		// vHolder.button2.setPadding(8, 8, 8, 8);
		vHolder.button2.setBackgroundResource(R.drawable.button_qxdd);
		vHolder.ll.setVisibility(View.GONE);
		vHolder.timeTitle.setVisibility(View.INVISIBLE);
		vHolder.time.setVisibility(View.INVISIBLE);
		vHolder.button2.setOnClickListener(new OnClickListener() {

		    @Override
		    public void onClick(View v) {
			// TODO Auto-generated method stub
			new CancelAsync().execute(hashMap.get("id"));
		    }
		});

	    } else if (hashMap.get("zt").equals("7")) {
		vHolder.timeTitle.setText("距离服务时间：");
		vHolder.timeTitle.setTextColor(Color.rgb(10, 11, 19));
		vHolder.time.setText(hashMap.get("jlfwsj"));
		vHolder.button1.setVisibility(View.VISIBLE);
		// vHolder.button1.setText("追加");
		// vHolder.button1.setBackgroundColor(Color.rgb(35, 177, 44));
		// vHolder.button1.setPadding(20, 8, 20, 8);
		vHolder.button1.setBackgroundResource(R.drawable.button_zj);
		// vHolder.button2.setText("查看");
		// vHolder.button2.setBackgroundColor(Color.rgb(255, 91, 85));
		// vHolder.button2.setPadding(20, 8, 20, 8);
		vHolder.button2.setBackgroundResource(R.drawable.button_ck);

		vHolder.button1.setOnClickListener(new OnClickListener() {

		    @Override
		    public void onClick(View v) {
			// TODO Auto-generated method stub
			Message msg = handler.obtainMessage();
			msg.what = 2;
			msg.obj = hashMap.get("id");
			msg.sendToTarget();

		    }
		});
		vHolder.button2.setOnClickListener(new OnClickListener() {

		    @Override
		    public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(context,
				OrderStatusMain.class);
			intent.putExtra("id", hashMap.get("id"));
			intent.putExtra("zt", "3");
			context.startActivity(intent);

		    }
		});
	    } else if (hashMap.get("zt").equals("2")) {
		vHolder.timeTitle.setText("距离服务时间：");
		vHolder.timeTitle.setTextColor(Color.rgb(10, 11, 19));
		vHolder.time.setText(hashMap.get("jlfwsj"));
		vHolder.button1.setVisibility(View.GONE);
		// vHolder.button2.setText("查看");
		// vHolder.button2.setBackgroundColor(Color.rgb(255, 91, 85));
		// vHolder.button2.setPadding(20, 8, 20, 8);
		vHolder.button2.setBackgroundResource(R.drawable.button_ck);

		vHolder.button2.setOnClickListener(new OnClickListener() {

		    @Override
		    public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(context,
				OrderStatusMain.class);
			intent.putExtra("id", hashMap.get("id"));
			intent.putExtra("zt", "2");
			context.startActivity(intent);
		    }
		});
	    } else if (hashMap.get("zt").equals("4")) {
		vHolder.timeTitle.setText("服务时长：");
		vHolder.timeTitle.setTextColor(Color.rgb(10, 11, 19));
		vHolder.time.setText(hashMap.get("fwsc"));
		vHolder.button1.setVisibility(View.GONE);
		// vHolder.button2.setText("评价");
		// vHolder.button2.setBackgroundColor(Color.rgb(255, 153, 0));
		// vHolder.button2.setPadding(20, 8, 20, 8);
		vHolder.button2.setBackgroundResource(R.drawable.buttong_pj);

		vHolder.button2.setOnClickListener(new OnClickListener() {

		    @Override
		    public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(context, CommentMain.class);
			intent.putExtra("oid", hashMap.get("id"));
			intent.putExtra("zid", hashMap.get("zid"));
			context.startActivity(intent);
		    }
		});
	    } else if (hashMap.get("zt").equals("1")) {
		vHolder.timeTitle.setText("");
		vHolder.timeTitle.setTextColor(Color.rgb(230, 46, 46));
		vHolder.time.setText(hashMap.get("jlqxsj") + "后自动取消");
		vHolder.button1.setVisibility(View.VISIBLE);
		// vHolder.button1.setText("取消订单");
		// vHolder.button1.setBackgroundColor(Color.rgb(193, 193, 193));
		// vHolder.button1.setPadding(8, 8, 8, 8);
		vHolder.button1.setBackgroundResource(R.drawable.button_qxdd);
		// vHolder.button2.setText("立即支付");
		// vHolder.button2.setBackgroundColor(Color.rgb(35, 177, 44));
		// vHolder.button2.setPadding(8, 8, 8, 8);
		vHolder.button2.setBackgroundResource(R.drawable.button_ljzf);
		vHolder.ll.setVisibility(View.GONE);

		vHolder.button1.setOnClickListener(new OnClickListener() {

		    @Override
		    public void onClick(View v) {
			// TODO Auto-generated method stub
			new CancelAsync().execute(hashMap.get("id"));
		    }
		});
		vHolder.button2.setOnClickListener(new OnClickListener() {

		    @Override
		    public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(context,
				DesignerReserve1.class);
			intent.putExtra("zid", hashMap.get("zid"));
			intent.putExtra("zrid", hashMap.get("zrid1"));
			intent.putExtra("name", hashMap.get("fgid") + "-"
				+ hashMap.get("zrid"));
			intent.putExtra("address", hashMap.get("dz"));
			intent.putExtra("time", hashMap.get("yysj"));
			intent.putExtra("oid", hashMap.get("id"));
			intent.putExtra("fgid1", hashMap.get("fgid1"));
			context.startActivity(intent);
		    }
		});
	    } else if (hashMap.get("zt").equals("5")) {
		vHolder.timeTitle.setText("服务时长：");
		vHolder.timeTitle.setTextColor(Color.rgb(10, 11, 19));
		vHolder.time.setText(hashMap.get("fwsc"));
		vHolder.button1.setVisibility(View.VISIBLE);
		// vHolder.button1.setText("再次预约");
		// vHolder.button1.setBackgroundColor(Color.rgb(42, 148, 226));
		// vHolder.button1.setPadding(8, 8, 8, 8);
		vHolder.button1.setBackgroundResource(R.drawable.button_zcyy);
		// vHolder.button2.setText("分享成果");
		// vHolder.button2.setBackgroundColor(Color.rgb(254, 91, 134));
		// vHolder.button2.setPadding(8, 8, 8, 8);
		vHolder.button2.setBackgroundResource(R.drawable.button_fxcg);

		vHolder.button1.setOnClickListener(new OnClickListener() {

		    @Override
		    public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(context, DesignerInfo.class);
			intent.putExtra("zid", hashMap.get("zid"));
			intent.putExtra("zrid", hashMap.get("zrid1"));
			intent.putExtra("name", hashMap.get("fgid") + "-"
				+ hashMap.get("zrid"));
			intent.putExtra("type", "frag");
			intent.putExtra("sqid", "");
			intent.putExtra("sel", "");
			intent.putExtra("address", hashMap.get("dz"));

			context.startActivity(intent);
		    }
		});
		vHolder.button2.setOnClickListener(new OnClickListener() {

		    @Override
		    public void onClick(View v) {
			// TODO Auto-generated method stub
			// 分享
			Intent intent = new Intent(context, ShareResult.class);
			intent.putExtra("oid", hashMap.get("id"));
			context.startActivity(intent);
		    }
		});
	    } else {
		vHolder.rl.setVisibility(View.GONE);
	    }
	}

	return convertView;
    }

    class Map {
	HashMap<String, String> ztMap = new HashMap<String, String>();

	public Map() {
	    ztMap.put("0", "等待接单");
	    ztMap.put("1", "等待付款");
	    ztMap.put("2", "等待服务");
	    ztMap.put("3", "进行中");
	    ztMap.put("4", "待评价");
	    ztMap.put("5", "已互评");
	    ztMap.put("6", "订单已取消, 请重新预定");
	    ztMap.put("7", "追加付款");
	}
    }

    class ViewHolder {
	TextView type;
	TextView status;
	ImageView pic;
	TextView name;
	TextView phone;
	TextView price;
	TextView level;
	TextView dt;
	TextView address;
	TextView timeTitle;
	TextView time;
	TextView button1;
	TextView button2;
	LinearLayout ll;
	RelativeLayout rl;
	TextView num;
	TextView remark;
    }

    class CancelAsync extends AsyncTask<String, Void, String> {
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
	    pd = CustomProgressDialog.createDialog(context);
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
	    String url = Config.ORDER_CANCEL_URL + params[0] + "&uid="
		    + new Tools().getUserId(context);
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
		Toast.makeText(context, "取消订单成功！", Toast.LENGTH_SHORT).show();
		Message msg = handler.obtainMessage();
		msg.what = 1;
		handler.sendMessage(msg);
	    } else {
		Toast.makeText(context, "取消订单失败，请重试！", Toast.LENGTH_SHORT)
			.show();
	    }
	}

    }

    private void preperImageLoader() {

	/******************* 配置ImageLoder ***********************************************/
	File cacheDir = StorageUtils.getOwnCacheDirectory(context,
		"imageloader/Cache");

	ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
		context).denyCacheImageMultipleSizesInMemory()
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
