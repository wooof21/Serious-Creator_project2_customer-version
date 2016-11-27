/**
 * 
 */
package vip;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import popup.AddAddressPopUp;

import tools.Config;
import tools.CustomProgressDialog;
import tools.Exit;
import tools.FileUtil;
import tools.Tools;

import com.mkcomingc.BaseActivity;
import com.mkcomingc.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import adapter.AddressListAdapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
public class MyRegularInfo extends BaseActivity implements OnClickListener {

    private ImageView back;
    private ImageView pic;
    private EditText name;
    private EditText phone;
    private TextView add;
    private ListView lv;
    private TextView submit;
    private DisplayImageOptions options;
    private RelativeLayout parent;

    private ArrayList<HashMap<String, String>> list;
    private ArrayList<HashMap<String, String>> areaList;

    private String selectId = "";
    private String path = "", headPath = "";

    private static final int REQUESTCODE_PICK = 0; // 相册选图标记
    private static final int REQUESTCODE_TAKE = 1; // 相机拍照标记
    private static final int REQUESTCODE_CUTTING = 2; // 图片裁切标记
    private ArrayList<File> files;

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
	setContentView(R.layout.my_regular_info);
	Exit.getInstance().addActivity(this);
	prepareView();

	new MainAsync().execute();
    }

    class MainAsync extends AsyncTask<Void, String, String> {
	private CustomProgressDialog pd;

	@Override
	protected void onPreExecute() {
	    // TODO Auto-generated method stub
	    super.onPreExecute();
	    pd = CustomProgressDialog.createDialog(MyRegularInfo.this);
	    pd.show();
	}

	@Override
	protected String doInBackground(Void... params) {
	    // TODO Auto-generated method stub
	    String url = Config.PERSONAL_REGULAR_INFO_URL
		    + new Tools().getUserId(MyRegularInfo.this);
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
		    HashMap<String, String> hashMap = new HashMap<String, String>();
		    hashMap.put("nc", job.getString("nc"));
		    hashMap.put("dh", job.getString("dh"));
		    hashMap.put("tx", job.getString("tx"));

		    Message msg = handler.obtainMessage();
		    msg.what = 1;
		    msg.obj = hashMap;
		    msg.sendToTarget();

		    JSONArray area = job.getJSONArray("sheng");
		    for (int i = 0, j = area.length(); i < j; i++) {
			JSONObject job1 = area.optJSONObject(i);
			HashMap<String, String> areaMap = new HashMap<String, String>();
			areaMap.put("id", job1.getString("id"));
			areaMap.put("classname", job1.getString("classname"));

			areaList.add(areaMap);

		    }

		}
		if (_cydz.equals("1")) {
		    list = new ArrayList<HashMap<String, String>>();
		    JSONArray cydz = job.getJSONArray("cydz");
		    for (int i = 0, j = cydz.length(); i < j; i++) {
			JSONObject job2 = cydz.optJSONObject(i);
			HashMap<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("id", job2.getString("id"));
			hashMap.put("dz", job2.getString("dz"));
			hashMap.put("pic", "un_s");

			list.add(hashMap);
		    }
		    Message msg2 = handler.obtainMessage();
		    msg2.what = 3;
		    msg2.sendToTarget();
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

    private Handler handler = new Handler() {

	private AddressListAdapter adapter;

	@Override
	public void handleMessage(Message msg) {
	    // TODO Auto-generated method stub
	    super.handleMessage(msg);
	    switch (msg.what) {
	    case 1:
		HashMap<String, String> hashMap = (HashMap<String, String>) msg.obj;
		ImageLoader.getInstance().displayImage(
			Config.URL + hashMap.get("tx"), pic, options);
		name.setText(hashMap.get("nc"));
		phone.setText(hashMap.get("dh"));
		break;
	    case 2:

		break;
	    case 3:
		adapter = new AddressListAdapter(list, MyRegularInfo.this);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

		    @Override
		    public void onItemClick(AdapterView<?> parent, View view,
			    int position, long id) {
			// TODO Auto-generated method stub
			for (HashMap<String, String> map : list) {
			    map.put("pic", "un_s");
			}
			list.get(position).put("pic", "s");
			adapter.notifyDataSetChanged();
			selectId = list.get(position).get("id");
		    }
		});
		break;
	    default:
		break;
	    }
	}

    };

    private void prepareView() {
	back = (ImageView) findViewById(R.id.title_back);
	pic = (ImageView) findViewById(R.id.my_regular_info_pic);
	name = (EditText) findViewById(R.id.my_regular_info_name);
	phone = (EditText) findViewById(R.id.my_regular_info_phone);
	add = (TextView) findViewById(R.id.my_regular_info_add);
	lv = (ListView) findViewById(R.id.my_regular_info_lv);
	submit = (TextView) findViewById(R.id.my_regular_info_submit);
	parent = (RelativeLayout) findViewById(R.id.regular_info_parent);

	back.setOnClickListener(this);
	add.setOnClickListener(this);
	submit.setOnClickListener(this);
	pic.setOnClickListener(this);

	preperImageLoader();
	files = new ArrayList<File>();
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
	case R.id.my_regular_info_add:
	    Intent intent = new Intent(this, AddAddressPopUp.class);
	    intent.putExtra("areaMap", areaList);
	    startActivityForResult(intent, 100);
	    break;
	case R.id.my_regular_info_submit:
	    if (validation()) {
		new AsyncTask<Void, Void, String>() {
		    private CustomProgressDialog pd;

		    @Override
		    protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = CustomProgressDialog
				.createDialog(MyRegularInfo.this);
			pd.show();
		    }

		    @Override
		    protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			String name_utf8 = "";
			try {
			    name_utf8 = URLEncoder.encode(name.getText().toString(), "utf-8");
			} catch (UnsupportedEncodingException e1) {
			    // TODO Auto-generated catch block
			    e1.printStackTrace();
			}
			String url = Config.PERSONAL_INFO_POST_URL
				+ new Tools().getUserId(MyRegularInfo.this)
				+ "&nc=" + name_utf8 + "&dh="
				+ phone.getText().toString();
			Log.e("url", url);
			String data = new Tools().getURL(url);
			System.out.println(data);
			
			String code = "0";
			uploadFile(files, url);

			try {
			    JSONObject job = new JSONObject(data);
			    JSONObject result = job.getJSONObject("result");
			    String _code = result.getString("code");
			    if (_code.equals("1")) {
				String addr_set_url = Config.ADDRESS_SET_URL
					+ new Tools()
						.getUserId(MyRegularInfo.this)
					+ "&id=" + selectId;
				Log.e("addr set url", addr_set_url);
				String addr_data = new Tools()
					.getURL(addr_set_url);
				System.out.println(addr_data);
				JSONObject job1 = new JSONObject(addr_data);
				JSONObject result1 = job1
					.getJSONObject("result");
				code = result1.getString("code");
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
			    Toast.makeText(MyRegularInfo.this, "更新资料成功！",
				    Toast.LENGTH_SHORT).show();
			    startActivity(new Intent(MyRegularInfo.this, PersonalCenterMain.class));
			} else {
			    Toast.makeText(MyRegularInfo.this, "更新资料失败，请重试！",
				    Toast.LENGTH_SHORT).show();
			}
		    }

		}.execute();
	    }
	    break;

	case R.id.my_regular_info_pic:
	    new SelectPicPopupWindow(MyRegularInfo.this, parent);
	    break;
	default:
	    break;
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
	case REQUESTCODE_PICK:// 直接从相册获取
	    try {
		startPhotoZoom(data.getData());
	    } catch (NullPointerException e) {
		e.printStackTrace();// 用户点击取消操作
	    }
	    break;
	case REQUESTCODE_TAKE:// 调用相机拍照
	    File temp = new File(path);
	    startPhotoZoom(Uri.fromFile(temp));
	    break;
	case REQUESTCODE_CUTTING:// 取得裁剪后的图片
	    if (data != null) {
		setPicToView(data);
	    }
	    break;
	default:
	    break;
	}
    }
    
    /**
     * 裁剪图片方法实现
     * 
     * @param uri
     */
    private void startPhotoZoom(Uri uri) {
	Intent intent = new Intent("com.android.camera.action.CROP");
	intent.setDataAndType(uri, "image/*");
	// crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
	intent.putExtra("crop", "true");
	// aspectX aspectY 是宽高的比例
	intent.putExtra("aspectX", 1);
	intent.putExtra("aspectY", 1);
	// outputX outputY 是裁剪图片宽高
	intent.putExtra("outputX", 100);
	intent.putExtra("outputY", 100);
	intent.putExtra("return-data", true);
	startActivityForResult(intent, REQUESTCODE_CUTTING);
    }

    /**
     * 保存裁剪之后的图片数据
     * 
     * @param picdata
     */
    private void setPicToView(Intent picdata) {
	Bundle extras = picdata.getExtras();
	if (extras != null) {
	    // 取得SDCard图片路径做显示
	    Bitmap photo = extras.getParcelable("data");
	    Drawable drawable = new BitmapDrawable(null, photo);
	    headPath = FileUtil.saveFile(MyRegularInfo.this, "head.jpg", photo);
	    pic.setImageDrawable(drawable);
	    files.add(new File(headPath));
	}
    }
    
    private boolean validation() {
	if (name.getText().toString().length() == 0) {
	    Toast.makeText(this, "请填写昵称！", Toast.LENGTH_SHORT).show();
	    return false;
	} else if (phone.getText().toString().length() == 0) {
	    Toast.makeText(this, "请填写电话！", Toast.LENGTH_SHORT).show();
	    return false;
	} else if (phone.getText().toString().length() != 11) {
	    Toast.makeText(this, "请填写正确的11位电话！", Toast.LENGTH_SHORT).show();
	    return false;
	} else if (selectId.equals("")) {
	    Toast.makeText(this, "请选择默认地址！", Toast.LENGTH_SHORT).show();
	    return false;
	} else {
	    return true;
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

    class SelectPicPopupWindow extends PopupWindow {

	private Button takePhotoBtn, pickPhotoBtn, cancelBtn;
	private View mMenuView;

	@SuppressLint("InflateParams")
	public SelectPicPopupWindow(Context context, View parent) {
	    super(context);
	    LayoutInflater inflater = (LayoutInflater) context
		    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    mMenuView = inflater.inflate(R.layout.item_popupwindows, null);
	    takePhotoBtn = (Button) mMenuView
		    .findViewById(R.id.item_popupwindows_camera);
	    pickPhotoBtn = (Button) mMenuView
		    .findViewById(R.id.item_popupwindows_Photo);
	    cancelBtn = (Button) mMenuView
		    .findViewById(R.id.item_popupwindows_cancel);
	    // 设置按钮监听
	    cancelBtn.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
		    // TODO Auto-generated method stub
		    dismiss();
		}
	    });
	    pickPhotoBtn.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
		    // TODO Auto-generated method stub
		    Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
		    // 如果朋友们要限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型"
		    pickIntent.setDataAndType(
			    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
			    "image/*");
		    startActivityForResult(pickIntent, REQUESTCODE_PICK);
		    dismiss();
		}
	    });
	    takePhotoBtn.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
		    // TODO Auto-generated method stub
		    photo();
		    dismiss();
		}
	    });

	    // 设置SelectPicPopupWindow的View
	    this.setContentView(mMenuView);
	    // 设置SelectPicPopupWindow弹出窗体的宽
	    this.setWidth(LayoutParams.MATCH_PARENT);
	    // 设置SelectPicPopupWindow弹出窗体的高
	    this.setHeight(LayoutParams.WRAP_CONTENT);
	    // 设置SelectPicPopupWindow弹出窗体可点击
	    this.setFocusable(true);
	    // 设置SelectPicPopupWindow弹出窗体动画效果
	    this.setAnimationStyle(R.style.PopupAnimation);
	    // 实例化一个ColorDrawable颜色为半透明
	    ColorDrawable dw = new ColorDrawable(0x80000000);
	    // 设置SelectPicPopupWindow弹出窗体的背景
	    this.setBackgroundDrawable(dw);
	    showAtLocation(parent, Gravity.BOTTOM, 0, 0);
	    // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
	    mMenuView.setOnTouchListener(new OnTouchListener() {

		@Override
		@SuppressLint("ClickableViewAccessibility")
		public boolean onTouch(View v, MotionEvent event) {

		    int height = mMenuView.findViewById(R.id.ll_popup).getTop();
		    int y = (int) event.getY();
		    if (event.getAction() == MotionEvent.ACTION_UP) {
			if (y < height) {
			    dismiss();
			}
		    }
		    return true;
		}
	    });

	}

    }
    
    

    private void photo() {
	Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	openCameraIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

	StringBuffer sDir = new StringBuffer();
	if (hasSDcard()) {
	    sDir.append(Environment.getExternalStorageDirectory()
		    + "/mkcoming/");
	} else {
	    String dataPath = Environment.getRootDirectory().getPath();
	    sDir.append(dataPath + "/mkcoming/");
	}

	File fileDir = new File(sDir.toString());
	if (!fileDir.exists()) {
	    fileDir.mkdirs();
	}
	File file = new File(fileDir,
		String.valueOf(System.currentTimeMillis()) + ".jpg");

	path = file.getPath();
	Uri imageUri = Uri.fromFile(file);
	openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
	startActivityForResult(openCameraIntent, REQUESTCODE_TAKE);
	
    }

    private static boolean hasSDcard() {
	String status = Environment.getExternalStorageState();
	if (status.equals(Environment.MEDIA_MOUNTED)) {
	    return true;
	} else {
	    return false;
	}
    }
    
    
    /**
     * android上传文件到服务器
     * 
     * @param file
     *            需要上传的文件
     * @param RequestURL
     *            请求的rul
     * @return 返回响应的内容
     */
    public static String uploadFile(ArrayList<File> file, String RequestURL) {
	String result = null;
	String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
	String PREFIX = "--", LINE_END = "\r\n";
	String CONTENT_TYPE = "multipart/form-data"; // 内容类型,
						     // 这个参数来说明我们这传的是文件不是字符串了

	try {

	    URL url = new URL(RequestURL);
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    conn.setReadTimeout(10 * 1000);
	    conn.setConnectTimeout(10 * 1000);
	    conn.setDoInput(true); // 允许输入流
	    conn.setDoOutput(true); // 允许输出流
	    conn.setUseCaches(false); // 不允许使用缓存
	    conn.setRequestMethod("POST"); // 请求方式
	    conn.setRequestProperty("Charset", "utf-8"); // 设置编码
	    conn.setRequestProperty("connection", "keep-alive");
	    conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="
		    + BOUNDARY);

	    StringBuilder sb = new StringBuilder();
	    for (int i = 0, j = file.size(); i < j; i++) {
		sb.append(PREFIX);
		sb.append(BOUNDARY);
		sb.append(LINE_END);

		sb.append("Content-Disposition: form-data; name=\""
			+ "uploadfile" + "\"" + LINE_END);
		sb.append("Content-Type: text/plain; charset=" + "utf-8"
			+ LINE_END);
		sb.append("Content-Transfer-Encoding: 8bit" + LINE_END);
		sb.append(LINE_END);
		sb.append("uploadfile");
		sb.append(LINE_END);
	    }
	    DataOutputStream outStream = new DataOutputStream(
		    conn.getOutputStream());
	    outStream.write(sb.toString().getBytes());
	    if (file != null) {
		for (int i = 0, j = file.size(); i < j; i++) {
		    StringBuffer sb1 = new StringBuffer();
		    sb1.append(PREFIX);
		    sb1.append(BOUNDARY);
		    sb1.append(LINE_END);
		    /**
		     * 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
		     * filename是文件的名字，包含后缀名的 比如:abc.png
		     */

		    sb1.append("Content-Disposition: form-data; name=\"uploadfile\"; filename=\""
			    + file.get(i).getName() + "\"" + LINE_END);
		    sb1.append("Content-Type: application/octet-stream; charset="
			    + "utf-8" + LINE_END);
		    sb1.append(LINE_END);
		    outStream.write(sb1.toString().getBytes());
		    InputStream is = new FileInputStream(file.get(i));
		    byte[] bytes = new byte[1024];
		    int len = 0;
		    while ((len = is.read(bytes)) != -1) {
			outStream.write(bytes, 0, len);
		    }
		    is.close();
		    outStream.write(LINE_END.getBytes());
		}
	    }

	    // 请求结束标志
	    byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
		    .getBytes();
	    outStream.write(end_data);
	    outStream.flush();
	    // 得到响应码
	    int res = conn.getResponseCode();
	    InputStream in = conn.getInputStream();
	    StringBuilder sbResult = new StringBuilder();
	    if (res == 200) {
		int ch;
		while ((ch = in.read()) != -1) {
		    sbResult.append((char) ch);
		}
	    }
	    result = sbResult.toString();
	    outStream.close();
	    conn.disconnect();

	} catch (MalformedURLException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return result;
    }

}
