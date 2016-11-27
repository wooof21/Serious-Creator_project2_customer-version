/**
 * 
 */
package designers;

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

import tools.Config;
import tools.CustomProgressDialog;
import tools.Exit;
import tools.Tools;
import upload.Bmp;
import upload.ImageManager2;
import upload.PhotoAlbumActivity;
import view.AutoChangeLineLL;

import com.mkcomingc.BaseActivity;
import com.mkcomingc.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import adapter.AddressListAdapter;
import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
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
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RatingBar;
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
public class CommentMain extends BaseActivity implements OnClickListener {

    private ImageView back;
    private ImageView pic;
    private TextView name;
    private TextView style;
    private TextView price;
    private TextView level;
    private TextView time;
    private LinearLayout goodLL;
    private TextView goodTV;
    private ImageView good;
    private LinearLayout mediumLL;
    private TextView mediumTV;
    private ImageView medium;
    private LinearLayout badLL;
    private TextView badTV;
    private ImageView bad;
    private RatingBar rb1;
    private RatingBar rb2;
    private RatingBar rb3;
    private ViewGroup container;
    private HorizontalScrollView hsv;
    private LinearLayout selectedImageLayout;
    private TextView upload;
    private TextView submit;

    private RelativeLayout parent;

    private static final int TAKE_PICTURE = 0x000000;
    private String path = "";

    private TextView containerTv;
    private boolean isFristTime = true;
    /** 标签之间的间距 px */
    final int itemMargins = 30;

    /** 标签的行间距 px */
    final int lineMargins = 10;

    private String[] tag;

    private String oid, zid, rate;

    private ArrayList<String> nrList;

    private ArrayList<File> fList;

    
    
    /*
     * (non-Javadoc)
     * 
     * @see com.mkcomingc.BaseActivity#onResume()
     */
    @Override
    protected void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	if (PhotoAlbumActivity.selectedDataList.size() == 0) {
	    upload.setText("选择图片");
	} else {
	    upload.setText("选择(" + PhotoAlbumActivity.selectedDataList.size()
		    + "/4)");
	}
	
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
	setContentView(R.layout.comment_main);
	Exit.getInstance().addActivity(this);
	prepareView();
	oid = getIntent().getExtras().getString("oid");
	zid = getIntent().getExtras().getString("zid");

	new InfoAsync().execute(oid);
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
	    pd = CustomProgressDialog.createDialog(CommentMain.this);
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
	    String url = Config.COMMENT_MAIN_URL + params[0];
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
		    hashMap.put("zid", _data.getString("zid"));
		    hashMap.put("pic", _data.getString("pic"));
		    hashMap.put("xm", _data.getString("xm"));
		    hashMap.put("zrid", _data.getString("zrid"));
		    hashMap.put("fgid", _data.getString("fgid"));
		    hashMap.put("je", _data.getString("je"));
		    hashMap.put("dj", _data.getString("dj"));
		    hashMap.put("fwsc", _data.getString("fwsc"));

		    Message msg = handler.obtainMessage();
		    msg.what = 1;
		    msg.obj = hashMap;
		    msg.sendToTarget();

		    JSONArray jArray = _data.getJSONArray("biaoqian");
		    tag = new String[jArray.length()];
		    for (int i = 0, j = jArray.length(); i < j; i++) {
			JSONObject job = jArray.optJSONObject(i);
			tag[i] = job.getString("title");
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
		ImageLoader.getInstance().displayImage(
			Config.URL + hashMap.get("pic"), pic, options);
		name.setText(hashMap.get("xm"));
		style.setText(hashMap.get("fgid") + "-" + hashMap.get("zrid"));
		price.setText("￥" + hashMap.get("je"));
		level.setText("(" + hashMap.get("dj") + ")");
		time.setText(hashMap.get("fwsc"));
		break;

	    default:
		break;
	    }
	}

    };

    class PostAsync extends AsyncTask<ArrayList<File>, Void, String> {
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
	    pd = CustomProgressDialog.createDialog(CommentMain.this);
	    pd.show();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected String doInBackground(ArrayList<File>... params) {
	    // TODO Auto-generated method stub
	    String url = Config.COMMENT_MAIN_POST_URL
		    + new Tools().getUserId(CommentMain.this) + getPost();
	    Log.e("url", url);
	    String data = new Tools().getURL(url);
	    System.out.println(data);

	    if(params[0].size() != 0){
		uploadFile(params[0], url);
	    }

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
	    if(result.equals("1")){
		Toast.makeText(CommentMain.this, "评价成功！", Toast.LENGTH_SHORT).show();
		finish();
	    }else{
		Toast.makeText(CommentMain.this, "评价失败，请重试！", Toast.LENGTH_SHORT).show();
	    }
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

		    sb1.append("Content-Disposition: form-data; name=\"uploadfile[]\"; filename=\""
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
	    
	    System.out.println(result);
	    // for(int i = 0,j = file.size();i < j;i++){
	    // OutputStream outputSteam =
	    // conn.getOutputStream();
	    // DataOutputStream dos =
	    // new DataOutputStream(outputSteam);
	    // if(file.get(i) != null){
	    // /**
	    // * 当文件不为空，把文件包装并且上传
	    // */
	    //
	    // StringBuffer sb = new StringBuffer();
	    // sb.append(PREFIX);
	    // sb.append(BOUNDARY);
	    // sb.append(LINE_END);
	    // /**
	    // * 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
	    // * filename是文件的名字，包含后缀名的 比如:abc.png
	    // */
	    //
	    // sb.append("Content-Disposition: form-data; name=\"uploadfile[]\"; filename=\""
	    // + file.get(i).getName()
	    // + "\""
	    // + LINE_END);
	    // sb.append("Content-Type: application/octet-stream; charset="
	    // + "utf-8" + LINE_END);
	    // sb.append(LINE_END);
	    // dos.write(sb.toString().getBytes());
	    // InputStream is =
	    // new FileInputStream(file.get(i));
	    // byte[] bytes = new byte[1024];
	    // int len = 0;
	    // while((len = is.read(bytes)) != -1){
	    // dos.write(bytes, 0, len);
	    // }
	    // is.close();
	    // dos.write(LINE_END.getBytes());
	    // byte[] end_data =
	    // (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
	    // dos.write(end_data);
	    // dos.flush();
	    //
	    // /**
	    // * 获取响应码 200=成功 当响应成功，获取响应的流
	    // */
	    // int res = conn.getResponseCode();
	    // Log.e("upload file", "response code:"
	    // + res);
	    // // if(res==200)
	    // // {
	    // Log.e("upload file", "request success");
	    // InputStream input =
	    // conn.getInputStream();
	    // StringBuffer sb1 = new StringBuffer();
	    // int ss;
	    // while((ss = input.read()) != -1){
	    // sb1.append((char) ss);
	    // }
	    // result = sb1.toString();
	    // Log.e("upload file", "result : "
	    // + result);
	    // }
	    // }

	} catch (MalformedURLException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return result;
    }

    private DisplayImageOptions options;

    private void prepareView() {
	back = (ImageView) findViewById(R.id.title_back);
	pic = (ImageView) findViewById(R.id.comment_main_pic);
	name = (TextView) findViewById(R.id.comment_main_name);
	style = (TextView) findViewById(R.id.comment_main_style);
	price = (TextView) findViewById(R.id.comment_main_price);
	level = (TextView) findViewById(R.id.comment_main_level);
	time = (TextView) findViewById(R.id.comment_main_time_count);
	goodLL = (LinearLayout) findViewById(R.id.comment_main_good_ll);
	goodTV = (TextView) findViewById(R.id.comment_main_good_tv);
	good = (ImageView) findViewById(R.id.comment_main_good_iv);
	mediumLL = (LinearLayout) findViewById(R.id.comment_main_medium_ll);
	mediumTV = (TextView) findViewById(R.id.comment_main_medium_tv);
	medium = (ImageView) findViewById(R.id.comment_main_medium_iv);
	badLL = (LinearLayout) findViewById(R.id.comment_main_bad_ll);
	badTV = (TextView) findViewById(R.id.comment_main_bad_tv);
	bad = (ImageView) findViewById(R.id.comment_main_bad_iv);
	rb1 = (RatingBar) findViewById(R.id.comment_main_rb1);
	rb2 = (RatingBar) findViewById(R.id.comment_main_rb2);
	rb3 = (RatingBar) findViewById(R.id.comment_main_rb3);
	container = (LinearLayout) findViewById(R.id.comment_main_container1);
	selectedImageLayout = (LinearLayout) findViewById(R.id.selected_image_layout);
	hsv = (HorizontalScrollView) findViewById(R.id.scrollview);
	upload = (TextView) findViewById(R.id.comment_main_upload);
	parent = (RelativeLayout) findViewById(R.id.comment_parent);
	submit = (TextView) findViewById(R.id.comment_main_submit);

	back.setOnClickListener(this);
	goodLL.setOnClickListener(this);
	mediumLL.setOnClickListener(this);
	badLL.setOnClickListener(this);
	submit.setOnClickListener(this);
	upload.setOnClickListener(this);

	preperImageLoader();

	rate = "1";
	nrList = new ArrayList<String>();
	fList = new ArrayList<File>();
    }


    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onDestroy()
     */
    @Override
    protected void onDestroy() {
	// TODO Auto-generated method stub
	super.onDestroy();
	PhotoAlbumActivity.selectedDataList.clear();
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
	    PhotoAlbumActivity.selectedDataList.clear();
	    finish();
	    break;
	case R.id.comment_main_good_ll:
	    setBg(R.id.comment_main_good_ll);
	    break;
	case R.id.comment_main_medium_ll:
	    setBg(R.id.comment_main_medium_ll);
	    break;
	case R.id.comment_main_bad_ll:
	    setBg(R.id.comment_main_bad_ll);
	    break;
	case R.id.comment_main_submit:
	    getPost();
	    for (String string : PhotoAlbumActivity.selectedDataList) {
		System.out.println(string);
		File file = new File(string);
		fList.add(file);
	    }
	    if(validation()){
		new PostAsync().execute(fList);
	    }
	    break;
	case R.id.comment_main_upload:
	    new PopupWindowsSel(this, parent);
	    break;
	default:
	    break;
	}
    }

    private boolean validation(){
	if(rb1.getRating() == 0 || rb2.getRating() ==0 || rb3.getRating() == 0){
	    Toast.makeText(this, "请给造型师打分！", Toast.LENGTH_SHORT).show();
	    return false;
	}else if(nrList.size() == 0){
	    Toast.makeText(this, "请给造型师贴几个标签吧！", Toast.LENGTH_SHORT).show();
	    return false;
	}else{
	    return true;
	}
    }
    private String getPost() {
	String nr = "", nr_utf8 = "";
	for (String s : nrList) {
	    nr += s + " ";
	}
	try {
	    nr_utf8 = URLEncoder.encode(nr, "utf-8");
	} catch (UnsupportedEncodingException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	StringBuilder sb = new StringBuilder();
	sb.append("&zid=");
	sb.append(zid);
	sb.append("&oid=");
	sb.append(oid);
	sb.append("&pj=");
	sb.append(rate);
	sb.append("&zy=");
	sb.append("" + rb1.getRating());
	sb.append("&gt=");
	sb.append("" + rb2.getRating());
	sb.append("&ss=");
	sb.append("" + rb3.getRating());
	sb.append("&nr=");
	sb.append(nr_utf8);

	System.out.println(sb.toString());

	return sb.toString();
    }

    private void setBg(int id) {
	switch (id) {
	case R.id.comment_main_good_ll:
	    goodLL.setBackgroundColor(Color.rgb(255, 91, 133));
	    goodTV.setTextColor(Color.rgb(255, 255, 255));
	    good.setImageResource(R.drawable.comment_good_select);
	    mediumLL.setBackgroundColor(Color.rgb(255, 255, 255));
	    mediumTV.setTextColor(Color.rgb(10, 11, 19));
	    medium.setImageResource(R.drawable.comment_medium_unselect);
	    badLL.setBackgroundColor(Color.rgb(255, 255, 255));
	    badTV.setTextColor(Color.rgb(10, 11, 19));
	    bad.setImageResource(R.drawable.comment_bad_unselect);
	    rate = "1";
	    break;
	case R.id.comment_main_medium_ll:
	    goodLL.setBackgroundColor(Color.rgb(255, 255, 255));
	    goodTV.setTextColor(Color.rgb(10, 11, 19));
	    good.setImageResource(R.drawable.comment_good_unselect);
	    mediumLL.setBackgroundColor(Color.rgb(255, 104, 31));
	    mediumTV.setTextColor(Color.rgb(255, 255, 255));
	    medium.setImageResource(R.drawable.comment_medium_select);
	    badLL.setBackgroundColor(Color.rgb(255, 255, 255));
	    badTV.setTextColor(Color.rgb(10, 11, 19));
	    bad.setImageResource(R.drawable.comment_bad_unselect);
	    rate = "2";
	    break;
	case R.id.comment_main_bad_ll:
	    goodLL.setBackgroundColor(Color.rgb(255, 255, 255));
	    goodTV.setTextColor(Color.rgb(10, 11, 19));
	    good.setImageResource(R.drawable.comment_good_unselect);
	    mediumLL.setBackgroundColor(Color.rgb(255, 255, 255));
	    mediumTV.setTextColor(Color.rgb(10, 11, 19));
	    medium.setImageResource(R.drawable.comment_medium_unselect);
	    badLL.setBackgroundColor(Color.rgb(48, 60, 74));
	    badTV.setTextColor(Color.rgb(255, 255, 255));
	    bad.setImageResource(R.drawable.comment_bad_select);
	    rate = "3";
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
	case 1001:
	    if (resultCode == 1000) {
		ArrayList<String> list = data.getExtras().getStringArrayList(
			"dataList");
		initSelectImage(list);
		for (String string : list) {
		    System.out.println(string);
		}
	    }
	    break;
	case TAKE_PICTURE:
	    if (Bmp.drr.size() < 1 && resultCode == -1) {
		ImageView imageView = (ImageView) LayoutInflater.from(this)
			.inflate(R.layout.photoselect_album_choose_item,
				selectedImageLayout, false);
		try {
		    imageView.setImageBitmap(Bmp.revitionImageSize(path));
		    selectedImageLayout.addView(imageView);
		    PhotoAlbumActivity.selectedDataList.add(path);

		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
	    }
	    break;
	default:
	    break;
	}
    }

    private void initSelectImage(ArrayList<String> list) {
	selectedImageLayout.removeAllViews();
	if (list.size() == 0)
	    return;
	System.out.println("size --->" + list);
	for (final String path : list) {
	    System.out.println("path --> " + path);
	    ImageView imageView = (ImageView) LayoutInflater.from(this)
		    .inflate(R.layout.photoselect_album_choose_item,
			    selectedImageLayout, false);
	    selectedImageLayout.addView(imageView);
	    // hashMap.put(path, imageView);
	    ImageManager2.from(this).displayImage(imageView, path,
		    R.drawable.photoselect_bg, 400, 400);
	    // imageView.setOnClickListener(new View.OnClickListener() {
	    //
	    // @Override
	    // public void onClick(View v) {
	    // removePath(path);
	    // }
	    // });
	}
	upload.setText("选择(" + list.size() + "/4)");

    }

    class PopupWindowsSel extends PopupWindow {

	public PopupWindowsSel(Context mContext, View parent) {

	    View view = View
		    .inflate(mContext, R.layout.item_popupwindows, null);
	    view.startAnimation(AnimationUtils.loadAnimation(mContext,
		    R.anim.fade_ins));
	    LinearLayout ll_popup = (LinearLayout) view
		    .findViewById(R.id.ll_popup);
	    ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
		    R.anim.push_bottom_in_2));

	    setWidth(LayoutParams.FILL_PARENT);
	    setHeight(LayoutParams.FILL_PARENT);
	    setBackgroundDrawable(new BitmapDrawable());
	    setFocusable(true);
	    setOutsideTouchable(true);
	    setContentView(view);
	    showAtLocation(parent, Gravity.BOTTOM, 0, 0);
	    update();

	    Button bt1 = (Button) view
		    .findViewById(R.id.item_popupwindows_camera);
	    Button bt2 = (Button) view
		    .findViewById(R.id.item_popupwindows_Photo);
	    Button bt3 = (Button) view
		    .findViewById(R.id.item_popupwindows_cancel);
	    bt1.setOnClickListener(new OnClickListener() {
		public void onClick(View v) {
		    photo();
		    dismiss();
		}
	    });
	    bt2.setOnClickListener(new OnClickListener() {
		public void onClick(View v) {
		    CommentMain.this.startActivityForResult(new Intent(
			    CommentMain.this, PhotoAlbumActivity.class), 1001);
		    dismiss();
		}
	    });
	    bt3.setOnClickListener(new OnClickListener() {
		public void onClick(View v) {
		    dismiss();
		}
	    });

	}

    }

    public void photo() {
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
	startActivityForResult(openCameraIntent, TAKE_PICTURE);

    }

    public static boolean hasSDcard() {
	String status = Environment.getExternalStorageState();
	if (status.equals(Environment.MEDIA_MOUNTED)) {
	    return true;
	} else {
	    return false;
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

    /**
     * 首页热门搜索， 自动换行linearlayout
     * */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
	super.onWindowFocusChanged(hasFocus);
	if (hasFocus && isFristTime) {
	    isFristTime = false;
	    final int containerWidth = container.getMeasuredWidth()
		    - container.getPaddingRight() - container.getPaddingLeft();

	    final LayoutInflater inflater = getLayoutInflater();

	    /** 用来测量字符的宽度 */
	    final Paint paint = new Paint();
	    final TextView textView = (TextView) inflater.inflate(
		    R.layout.container_textview, null);
	    final int itemPadding = textView.getCompoundPaddingLeft()
		    + textView.getCompoundPaddingRight();
	    final LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(
		    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	    tvParams.setMargins(0, 0, itemMargins, 0);

	    paint.setTextSize(textView.getTextSize());

	    LinearLayout layout = new LinearLayout(this);
	    layout.setLayoutParams(new LinearLayout.LayoutParams(
		    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
	    layout.setOrientation(LinearLayout.HORIZONTAL);

	    container.addView(layout);

	    final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
		    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	    params.setMargins(0, lineMargins, 0, 0);

	    /** 一行剩下的空间 **/
	    int remainWidth = containerWidth;

	    for (int i = 0; i < tag.length; ++i) {
		final String text = tag[i];
		final float itemWidth = paint.measureText(text) + itemPadding;
		if (remainWidth > itemWidth) {
		    addItemView(inflater, layout, tvParams, text, i);
		} else {
		    resetTextViewMarginsRight(layout);
		    layout = new LinearLayout(this);
		    layout.setLayoutParams(params);
		    layout.setOrientation(LinearLayout.HORIZONTAL);

		    /** 将前面那一个textview加入新的一行 */
		    addItemView(inflater, layout, tvParams, text, i);
		    container.addView(layout);
		    remainWidth = containerWidth;
		}
		remainWidth = (int) (remainWidth - itemWidth + 0.5f)
			- itemMargins;
	    }
	    resetTextViewMarginsRight(layout);
	}
    }

    /**
     * 将每行最后一个textview的MarginsRight去掉
     * */
    private void resetTextViewMarginsRight(ViewGroup viewGroup) {
	final TextView tempTextView = (TextView) viewGroup.getChildAt(viewGroup
		.getChildCount() - 1);
	tempTextView.setLayoutParams(new LinearLayout.LayoutParams(
		LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
    }

    /**
     * 将view添加到viewgroup中
     * */
    private void addItemView(LayoutInflater inflater, ViewGroup viewGroup,
	    LayoutParams params, String text, int tag) {
	containerTv = (TextView) inflater.inflate(R.layout.container_textview,
		null);
	containerTv.setTag(tag);
	containerTv.setText(text);
	containerTv.setOnClickListener(new OnClickListener() {

	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub
		TextView tv = (TextView) v;
		// Intent intent1 = new Intent(MainActivity.this,
		// SearchActivity.class);
		// intent1.putExtra("tags", tags);
		// startActivity(intent1);
		if (!nrList.contains(tv.getText().toString())) {
		    nrList.add(tv.getText().toString());
		    tv.setTextColor(Color.rgb(255, 100, 148));
		    tv.setBackgroundResource(R.drawable.suqare_frame_pink);
		    for (String string : nrList) {
			System.out.println(string);
		    }
		} else {
		    nrList.remove(tv.getText().toString());
		    tv.setTextColor(Color.rgb(10, 11, 19));
		    tv.setBackgroundResource(R.drawable.square_frame);
		    for (String string : nrList) {
			System.out.println(string);
		    }
		}
	    }
	});
	viewGroup.addView(containerTv, params);
    }
}
