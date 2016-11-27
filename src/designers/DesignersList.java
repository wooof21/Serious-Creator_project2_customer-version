/**
 * 
 */
package designers;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import popup.AddAddressPopUp;
import popup.DesignerLevelPopUp;

import tools.Config;
import tools.CustomProgressDialog;
import tools.Exit;
import tools.Tools;
import view.PullToRefreshLayout;
import view.PullToRefreshLayout.OnRefreshListener;
import vip.MyRegularInfo;

import com.mkcomingc.BaseActivity;
import com.mkcomingc.R;
import com.xlistview.XListView;

import adapter.DesignerListAdapter;
import adapter.PopUpLevelListAdapter;
import android.R.fraction;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 * @author Liming Chu
 * 
 * @param
 * @return
 */
public class DesignersList extends BaseActivity implements OnClickListener {

    private ImageView back;
    private FrameLayout level;
    private FrameLayout sex;
    private FrameLayout smart;
    private ListView lv;
    private RelativeLayout rl;
    private PullToRefreshLayout ptrl;

    private String zrid, name;
    private ArrayList<HashMap<String, String>> list;
    private DesignerListAdapter adapter;

    private ArrayList<HashMap<String, String>> _list;

    private int page = 1;
    private String _level = "", _sex = "0", _order = "";
    private ListView popup_lv;

    private ArrayList<String> list1, list2, list3;
    private HashMap<String, String> map1, map2, map3;
    private String addrSel, sqId, type, sqSel1, sqSel2, address = "",
	    addr1 = "", addr2 = "", addr3 = "";
    
    private String tvLevel, tvSex, tvOrder;
    private TextView level_tv, sex_tv, smart_tv;
    private View bottom;

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
	setContentView(R.layout.designer_list_main);
	Exit.getInstance().addActivity(this);
	prepareView();
	zrid = getIntent().getExtras().getString("id");
	name = getIntent().getExtras().getString("name");
	type = getIntent().getExtras().getString("type");
	if (type.equals("yy")) {// 点击预约进来
	    bottom.setVisibility(View.GONE);
	    new AddrAsync().execute();
	} else {// 点击导航造型师进来
	    new ListAsync()
		    .execute(page, zrid, _order, _level, _sex, sqId, "1");// 1-refresh
									  // 2-
									  // load
	}
    }

    private void prepareView() {
	back = (ImageView) findViewById(R.id.title_back);
	level = (FrameLayout) findViewById(R.id.designer_list_level);
	sex = (FrameLayout) findViewById(R.id.designer_list_sex);
	smart = (FrameLayout) findViewById(R.id.designer_list_smart);
	ptrl = (PullToRefreshLayout) findViewById(R.id.designer_ptrl);
	lv = (ListView) findViewById(R.id.content_view);
	level_tv = (TextView) findViewById(R.id.designer_list_level_tv);
	sex_tv = (TextView) findViewById(R.id.designer_list_sex_tv);
	smart_tv = (TextView) findViewById(R.id.designer_list_smart_tv);
	bottom = findViewById(R.id.main_bottom_fragment);
	ptrl.setOnRefreshListener(new MyListener());
	ptrl.requestFocus();
	// ptrl.setOnRefreshListener(new OnRefreshListener() {
	//
	// @Override
	// public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
	// // TODO Auto-generated method stub
	// System.out.println("刷新");
	// }
	//
	// @Override
	// public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
	// // TODO Auto-generated method stub
	// System.out.println("加载");
	// }
	// });

	rl = (RelativeLayout) findViewById(R.id.rrrrrrrrrrrrrllllllllllllllllll);
	back.setOnClickListener(this);
	level.setOnClickListener(this);
	sex.setOnClickListener(this);
	smart.setOnClickListener(this);

	/**
	 * addrSel ： 1. 默认， 只选择商圈，无详细地址 2. 选择详细地址
	 * **/
	addrSel = "1";
	sqId = "";

    }

    /**
     * page zrid order=zypj , order=fwpj dj xb 1-male
     * **/
    class ListAsync extends
	    AsyncTask<Object, Void, ArrayList<HashMap<String, String>>> {

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
	    pd = CustomProgressDialog.createDialog(DesignersList.this);
	    pd.show();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected ArrayList<HashMap<String, String>> doInBackground(
		Object... params) {
	    // TODO Auto-generated method stub
	    if (params[6].equals("1")) {
		list = new ArrayList<HashMap<String, String>>();
	    }
	    String url = Config.DESIGNER_LIST_URL + params[0] + "&zrid="
		    + params[1] + "&page_order=" + params[2] + "&page_dj="
		    + params[3] + "&page_xb=" + params[4] + "&city="
		    + params[5];
	    Log.e("url", url);
	    String data = new Tools().getURL(url);
	    System.out.println(data);

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
			hashMap.put("xm", job.getString("xm"));
			hashMap.put("xb", job.getString("xb"));
			hashMap.put("dj", job.getString("dj"));
			hashMap.put("je", job.getString("je"));
			hashMap.put("pic", job.getString("pic"));
			hashMap.put("ds", job.getString("ds"));
			hashMap.put("zypj", job.getString("zypj"));
			hashMap.put("fwpj", job.getString("fwpj"));
			hashMap.put("huodongdj", job.getString("huodongdj"));
			hashMap.put("huodongje", job.getString("huodongje"));

			list.add(hashMap);
		    }

		    Message msg = mHandler.obtainMessage();
		    if (params[6].equals("1")) {
			msg.what = 2;
		    } else {
			msg.what = 1;
		    }
		    msg.sendToTarget();
		}
	    } catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
	    // TODO Auto-generated method stub
	    super.onPostExecute(result);
	    pd.dismiss();

	}
    }

    private Handler mHandler = new Handler() {

	@Override
	public void handleMessage(Message msg) {
	    // TODO Auto-generated method stub
	    super.handleMessage(msg);
	    switch (msg.what) {
	    case 1:
		adapter.notifyDataSetChanged();
		break;
	    case 2:
		adapter = new DesignerListAdapter(list, DesignersList.this);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

		    @Override
		    public void onItemClick(AdapterView<?> parent, View view,
			    int position, long id) {
			// TODO Auto-generated method stub
			if (type.equals("yy")) {

			} else {

			}
			String zid = list.get(position).get("id");
			Intent intent = new Intent(DesignersList.this,
				DesignerInfo.class);
			intent.putExtra("zid", zid);
			intent.putExtra("zrid", zrid);
			intent.putExtra("name", name);
			intent.putExtra("type", type);
			intent.putExtra("sqid", sqId);
			intent.putExtra("sel", addrSel);// addrSel, 地址选择方式，
							// 1位只有商圈，
							// 2位详细地址
			intent.putExtra("address", address);
			startActivity(intent);
		    }
		});
		break;
	    case 100:
		String text = (String) msg.obj;
		level_tv.setText(text);
		break;
	    case 200:
		String text1 = (String) msg.obj;
		sex_tv.setText(text1);
		break;
	    case 300:
		String text2 = (String) msg.obj;
		smart_tv.setText(text2);
		break;
	    default:
		break;
	    }
	}

    };

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
	case R.id.designer_list_level:
	    // startActivityForResult(new Intent(this,
	    // DesignerLevelPopUp.class), 100);
	    new AsyncTask<Void, Void, String>() {

		private CustomProgressDialog pd;

		@Override
		protected void onPreExecute() {
		    // TODO Auto-generated method stub
		    super.onPreExecute();
		    pd = CustomProgressDialog.createDialog(DesignersList.this);
		    pd.show();
		    _list = new ArrayList<HashMap<String, String>>();
		}

		@Override
		protected String doInBackground(Void... params) {
		    // TODO Auto-generated method stub
		    String data = new Tools().getURL(Config.DESIGNER_LEVEL_URL);
		    System.out.println(data);
		    String code = "";
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
				hashMap.put("classname",
					job.getString("classname"));

				_list.add(hashMap);
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
		    if (result.equals("1")) {
			PopUpLevelListAdapter adapter = new PopUpLevelListAdapter(
				_list, DesignersList.this);
			popup_lv.setAdapter(adapter);
		    } else {
			Toast.makeText(DesignersList.this, "获取造型师等级失败，请重试!",
				Toast.LENGTH_SHORT).show();
		    }
		}

	    }.execute();
	    new PopupWindowsLevel(DesignersList.this, lv);
	    break;
	case R.id.designer_list_sex:
	    new PopupWindowsSex(DesignersList.this, lv);
	    break;
	case R.id.designer_list_smart:
	    new PopupWindowsOrder(DesignersList.this, lv);
	    break;
	default:
	    break;
	}
    }

    class PopupWindowsLevel extends PopupWindow {

	public PopupWindowsLevel(Context mContext, View parent) {

	    View view = View.inflate(mContext, R.layout.popup_level, null);
	    view.startAnimation(AnimationUtils.loadAnimation(mContext,
		    R.anim.push_bottom_in_2));
	    popup_lv = (ListView) view.findViewById(R.id.popup_level_lv);
	    // lv.startAnimation(AnimationUtils.loadAnimation(mContext,
	    // R.anim.push_bottom_in_2));

	    setWidth(LayoutParams.FILL_PARENT);
	    setHeight(LayoutParams.FILL_PARENT);
	    // setBackgroundDrawable(new BitmapDrawable());
	    setFocusable(true);
	    setOutsideTouchable(true);
	    setContentView(view);
	    showAtLocation(parent, Gravity.CENTER, 0, 0);
	    update();

	    popup_lv.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view,
			int position, long id) {
		    // TODO Auto-generated method stub
		    _level = _list.get(position).get("id");
		    new ListAsync().execute(page, zrid, _order, _level, _sex,
			    sqId, "1");
		    tvLevel = _list.get(position).get("classname");
		    Message msg = mHandler.obtainMessage();
		    msg.what = 100;
		    msg.obj = tvLevel;
		    msg.sendToTarget();
		    dismiss();
		}
	    });

	}
    }

    class PopupWindowsSex extends PopupWindow {

	public PopupWindowsSex(Context mContext, View parent) {

	    View view = View.inflate(mContext, R.layout.popup_sex, null);
	    view.startAnimation(AnimationUtils.loadAnimation(mContext,
		    R.anim.push_bottom_in_2));
	    TextView all = (TextView) view.findViewById(R.id.popup_sex_all);
	    TextView male = (TextView) view.findViewById(R.id.popup_sex_male);
	    TextView female = (TextView) view
		    .findViewById(R.id.popup_sex_female);
	    // lv.startAnimation(AnimationUtils.loadAnimation(mContext,
	    // R.anim.push_bottom_in_2));

	    setWidth(LayoutParams.FILL_PARENT);
	    setHeight(LayoutParams.FILL_PARENT);
	    // setBackgroundDrawable(new BitmapDrawable());
	    setFocusable(true);
	    setOutsideTouchable(true);
	    setContentView(view);
	    showAtLocation(parent, Gravity.CENTER, 0, 0);
	    update();

	    all.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
		    // TODO Auto-generated method stub
		    _sex = "0";
		    new ListAsync().execute(page, zrid, _order, _level, _sex,
			    sqId, "1");
		    Message msg = mHandler.obtainMessage();
		    msg.what = 200;
		    msg.obj = "全部";
		    msg.sendToTarget();
		    dismiss();
		}
	    });
	    male.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
		    // TODO Auto-generated method stub
		    _sex = "1";
		    new ListAsync().execute(page, zrid, _order, _level, _sex,
			    sqId, "1");
		    Message msg = mHandler.obtainMessage();
		    msg.what = 200;
		    msg.obj = "男";
		    msg.sendToTarget();
		    dismiss();
		}
	    });
	    female.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
		    // TODO Auto-generated method stub
		    _sex = "2";
		    new ListAsync().execute(page, zrid, _order, _level, _sex,
			    sqId, "1");
		    Message msg = mHandler.obtainMessage();
		    msg.what = 200;
		    msg.obj = "女";
		    msg.sendToTarget();
		    dismiss();
		}
	    });

	}

    }

    class PopupWindowsOrder extends PopupWindow {

	public PopupWindowsOrder(Context mContext, View parent) {

	    View view = View.inflate(mContext, R.layout.popup_order, null);
	    view.startAnimation(AnimationUtils.loadAnimation(mContext,
		    R.anim.push_bottom_in_2));
	    TextView count = (TextView) view
		    .findViewById(R.id.popup_order_count);
	    TextView zy = (TextView) view.findViewById(R.id.popup_order_zhiye);
	    TextView fw = (TextView) view.findViewById(R.id.popup_order_fuwu);
	    // lv.startAnimation(AnimationUtils.loadAnimation(mContext,
	    // R.anim.push_bottom_in_2));

	    setWidth(LayoutParams.FILL_PARENT);
	    setHeight(LayoutParams.FILL_PARENT);
	    // setBackgroundDrawable(new BitmapDrawable());
	    setFocusable(true);
	    setOutsideTouchable(true);
	    setContentView(view);
	    showAtLocation(parent, Gravity.CENTER, 0, 0);
	    update();

	    count.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
		    // TODO Auto-generated method stub
		    _order = "jdcs";
		    new ListAsync().execute(page, zrid, _order, _level, _sex,
			    sqId, "1");
		    Message msg = mHandler.obtainMessage();
		    msg.what = 300;
		    msg.obj = "接单次数";
		    msg.sendToTarget();
		    dismiss();
		}
	    });
	    zy.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
		    // TODO Auto-generated method stub
		    _order = "zypj";
		    new ListAsync().execute(page, zrid, _order, _level, _sex,
			    sqId, "1");
		    Message msg = mHandler.obtainMessage();
		    msg.what = 300;
		    msg.obj = "专业评价";
		    msg.sendToTarget();
		    dismiss();
		}
	    });
	    fw.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
		    // TODO Auto-generated method stub
		    _order = "fwpj";
		    new ListAsync().execute(page, zrid, _order, _level, _sex,
			    sqId, "1");
		    Message msg = mHandler.obtainMessage();
		    msg.what = 300;
		    msg.obj = "服务评价";
		    msg.sendToTarget();
		    dismiss();
		}
	    });

	}

    }

    class PopupWindowsAddr extends PopupWindow implements OnClickListener {
	ImageView back, iv1, iv2;
	Spinner spinner1, spinner2, spinner3;
	TextView submit;
	LinearLayout ll;
	public ArrayList<HashMap<String, String>> street;

	public PopupWindowsAddr(Context mContext, View parent) {

	    View view = View.inflate(mContext, R.layout.dialog_choose_addr,
		    null);
	    view.startAnimation(AnimationUtils.loadAnimation(mContext,
		    R.anim.push_bottom_in_2));
	    // lv.startAnimation(AnimationUtils.loadAnimation(mContext,
	    // R.anim.push_bottom_in_2));
	    back = (ImageView) view.findViewById(R.id.title_back);
	    iv1 = (ImageView) view.findViewById(R.id.choose_addr_sel1);
	    iv2 = (ImageView) view.findViewById(R.id.choose_addr_sel2);
	    spinner1 = (Spinner) view.findViewById(R.id.add_addr_spinner1);
	    spinner2 = (Spinner) view.findViewById(R.id.add_addr_spinner2);
	    spinner3 = (Spinner) view.findViewById(R.id.add_addr_spinner3);
	    submit = (TextView) view.findViewById(R.id.add_addr_submit);
	    ll = (LinearLayout) view.findViewById(R.id.choose_addr_sel_ll);
	    back.setOnClickListener(this);
	    iv1.setOnClickListener(this);
	    iv2.setOnClickListener(this);
	    submit.setOnClickListener(this);
	    // spinner1.setOnClickListener(this);
	    // spinner2.setOnClickListener(this);
	    // spinner3.setOnClickListener(this);
	    ll.setOnClickListener(this);

	    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
		    DesignersList.this, R.layout.area_spinner_tv, R.id.text,
		    list1);
	    adapter1.setDropDownViewResource(R.layout.area_spinner_dropdown);
	    spinner1.setAdapter(adapter1);
	    new StreetAsync().execute(map1.get(list1.get(0).toString()));
	    spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
			int position, long id) {
		    // TODO Auto-generated method stub
		    addrSel = "1";
		    iv1.setImageResource(R.drawable.select_pic);
		    iv2.setImageResource(R.drawable.circle_un);
		    System.out.println(list1.get(position).toString());
		    System.out.println(map1.get(list1.get(position).toString()));
		    // spinner2 商圈
		    new StreetAsync().execute(map1.get(list1.get(position)
			    .toString()));
		    // addr1 : 地区地址， 最后地址为addr = addr1 + addr2
		    addr1 = list1.get(position).toString();
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
		    // TODO Auto-generated method stub
		    sqId = sqSel1;
		    Log.e("sqId spinner1 nothing", sqId);
		}
	    });

	    ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(
		    DesignersList.this, R.layout.area_spinner_tv, R.id.text,
		    list3);
	    adapter3.setDropDownViewResource(R.layout.area_spinner_dropdown);
	    spinner3.setAdapter(adapter3);
	    spinner3.setOnItemSelectedListener(new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
			int position, long id) {
		    // TODO Auto-generated method stub
		    addrSel = "2";
		    iv1.setImageResource(R.drawable.circle_un);
		    iv2.setImageResource(R.drawable.select_pic);
		    System.out.println(list3.get(position).toString());
		    sqId = map3.get(list3.get(position).toString());
		    sqSel2 = sqId;
		    // addr3 详细地址
		    addr3 = list3.get(position).toString();
		    Log.e("sqId spinner3 ", sqId);

		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
		    // TODO Auto-generated method stub
		    sqId = sqSel2;
		    Log.e("sqId spinner3 nothing ", sqId);

		}
	    });

	    setWidth(LayoutParams.FILL_PARENT);
	    setHeight(LayoutParams.FILL_PARENT);
	    // setBackgroundDrawable(new BitmapDrawable());
	    setFocusable(true);
	    setOutsideTouchable(true);
	    setContentView(view);
	    showAtLocation(parent, Gravity.CENTER, 0, 0);
	    update();

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
		dismiss();
		break;
	    case R.id.choose_addr_sel1:
		addrSel = "1";
		iv1.setImageResource(R.drawable.select_pic);
		iv2.setImageResource(R.drawable.circle_un);
		sqId = sqSel1;
		address = addr1 + " " + addr2;
		Log.e("sqid sel1", sqId);
		break;
	    case R.id.choose_addr_sel2:
		addrSel = "2";
		iv1.setImageResource(R.drawable.circle_un);
		iv2.setImageResource(R.drawable.select_pic);
		sqId = sqSel2;
		address = addr3;
		Log.e("sqid sel2", sqId);
		break;
	    case R.id.add_addr_submit:
		if (addrSel.equals("1")) {
		    address = addr1 + " " + addr2;
		} else if (addrSel.equals("2")) {
		    address = addr3;
		} else {
		    address = "";
		}
		Log.e("address", address);
		Log.e("+sqId", sqId);
		dismiss();
		new ListAsync().execute(page, zrid, _order, _level, _sex, sqId,
			"1");
		break;
	    case R.id.choose_addr_sel_ll:
		addrSel = "2";
		iv1.setImageResource(R.drawable.circle_un);
		iv2.setImageResource(R.drawable.select_pic);
		sqId = sqSel2;
		address = addr3;
		Log.e("sqid sel2", sqId);
		break;
	    default:
		break;
	    }
	}

	class StreetAsync extends AsyncTask<String, Void, ArrayList<String>> {
	    private CustomProgressDialog pd;

	    @Override
	    protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		pd = CustomProgressDialog.createDialog(DesignersList.this);
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
			sqId = street.get(0).get("id");
			addr2 = street.get(0).get("classname");
			sqSel1 = sqId;
			Log.e("street id default", sqId);
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
			DesignersList.this, R.layout.area_spinner_tv,
			R.id.text, result);
		adapter.setDropDownViewResource(R.layout.area_spinner_dropdown);
		spinner2.setAdapter(adapter);
		spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {

		    @Override
		    public void onItemSelected(AdapterView<?> parent,
			    View view, int position, long id) {
			// TODO Auto-generated method stub
			addrSel = "1";
			iv1.setImageResource(R.drawable.select_pic);
			iv2.setImageResource(R.drawable.circle_un);
			if (street.size() != 0) {
			    System.out.println(street.get(position).get("id"));
			    sqId = street.get(position).get("id");
			    sqSel1 = sqId;
			    addr2 = street.get(position).get("classname");
			    Log.e("street id spinner2", sqId);
			}
		    }

		    @Override
		    public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			sqId = sqSel1;
			Log.e("sqId spinner2 nothing", sqId);
		    }
		});
	    }
	}

	private Handler handler = new Handler() {

	    @Override
	    public void handleMessage(Message msg) {
		// TODO Auto-generated method stub
		super.handleMessage(msg);
		Toast.makeText(DesignersList.this, "无数据！", Toast.LENGTH_SHORT)
			.show();
	    }

	};

    }

    /**
     * 预约进列表第一步 先选地址， 之后按商圈id筛选造型师
     * **/
    class AddrAsync extends AsyncTask<Void, Void, String> {
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
	    pd = CustomProgressDialog.createDialog(DesignersList.this);
	    pd.show();
	    list1 = new ArrayList<String>();
	    list2 = new ArrayList<String>();
	    list3 = new ArrayList<String>();
	    map1 = new HashMap<String, String>();
	    map2 = new HashMap<String, String>();
	    map3 = new HashMap<String, String>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected String doInBackground(Void... params) {
	    // TODO Auto-generated method stub
	    String url = Config.PERSONAL_REGULAR_INFO_URL
		    + new Tools().getUserId(DesignersList.this);
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
		    JSONArray cydz = job.getJSONArray("cydz");
		    for (int i = 0, j = cydz.length(); i < j; i++) {
			JSONObject job1 = cydz.optJSONObject(i);
			list3.add(job1.getString("dz"));
			map3.put(job1.getString("dz"), job1.getString("sqid"));
		    }
		    sqSel2 = map3.get(list3.get(0).toString());
		    addr3 = list3.get(0).toString();
		    JSONArray sheng = job.getJSONArray("sheng");
		    for (int i = 0, j = sheng.length(); i < j; i++) {
			JSONObject job2 = sheng.optJSONObject(i);
			list1.add(job2.getString("classname"));
			map1.put(job2.getString("classname"),
				job2.getString("id"));
		    }
		    addr1 = list1.get(0);

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
		new PopupWindowsAddr(DesignersList.this, lv);
	    } else {
		Toast.makeText(DesignersList.this, "网络不给力啊，请重试！",
			Toast.LENGTH_SHORT).show();
		DesignersList.this.finish();
	    }
	}

    }

    class MyListener implements OnRefreshListener {

	@Override
	public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
	    // 下拉刷新操作
	    new ListAsync().execute("1", zrid, _order, _level, _sex, sqId, "1");
	    new Handler() {
		@Override
		public void handleMessage(Message msg) {
		    // 千万别忘了告诉控件刷新完毕了哦！
		    pullToRefreshLayout
			    .refreshFinish(PullToRefreshLayout.SUCCEED);
		    System.out.println("下来刷新！");

		}
	    }.sendEmptyMessageDelayed(0, 3000);
	}

	@Override
	public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
	    // 加载操作
	    page++;
	    new ListAsync()
		    .execute(page, zrid, _order, _level, _sex, sqId, "2");
	    new Handler() {
		@Override
		public void handleMessage(Message msg) {
		    // 千万别忘了告诉控件加载完毕了哦！
		    Log.e("page", "" + page);
		    pullToRefreshLayout
			    .loadmoreFinish(PullToRefreshLayout.SUCCEED);
		    System.out.println("加载更多！");

		}
	    }.sendEmptyMessageDelayed(0, 3000);
	}

    }
}
