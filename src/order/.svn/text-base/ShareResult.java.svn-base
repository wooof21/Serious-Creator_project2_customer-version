/**
 * 
 */
package order;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import tools.CustomProgressDialog;
import tools.Exit;
import tools.FileUtil;
import tools.Tools;
import vip.MyRegularInfo;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.PlatformListFakeActivity.OnShareButtonClickListener;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

import com.mkcomingc.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
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
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Liming Chu
 * 
 * @param
 * @return
 */
public class ShareResult extends Activity implements OnClickListener {

    private ImageView back, plus, pic;
    private TextView share, nick, comment, name, level;
    private String filepath;
    private LinearLayout parent;
    
    private String path = "", headPath = "";

    private static final int REQUESTCODE_PICK = 0; // 相册选图标记
    private static final int REQUESTCODE_TAKE = 1; // 相机拍照标记
    private static final int REQUESTCODE_CUTTING = 2; // 图片裁切标记
    
    private static String oid;

    
    private void setOid(String oid){
	ShareResult.oid = oid;
    }
    public static String getOid(){
	return oid;
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
	setContentView(R.layout.share_result);
	Exit.getInstance().addActivity(this);
	prepareView();
	oid = getIntent().getExtras().getString("oid");
	setOid(oid);
	
	new AsyncTask<String, Void, String>() {

	    private CustomProgressDialog pd;
	    @Override
	    protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		pd = CustomProgressDialog.createDialog(ShareResult.this);
		pd.show();
	    }

	    @Override
	    protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		pd.dismiss();
	    }

	    @Override
	    protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		String url = tools.Config.SHARE_URL + params[0];
		Log.e("url", url);
		String data = new Tools().getURL(url);
		System.out.println(data);
		String code = "";
		
		try {
		    JSONObject _data = new JSONObject(data);
		    JSONObject result = _data.getJSONObject("result");
		    code = result.getString("code");
		    if(code.equals("1")){
			JSONObject job = _data.getJSONObject("data");
			HashMap<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("pic", job.getString("pic"));
			hashMap.put("yh_xm", job.getString("yh_xm"));
			hashMap.put("zxs_xm", job.getString("zxs_xm"));
			hashMap.put("dj", job.getString("dj"));
			hashMap.put("fxbt", job.getString("fxbt"));
			hashMap.put("pjnr", job.getString("pjnr"));
			
			Message msg = handler.obtainMessage();
			msg.obj = hashMap;
			msg.sendToTarget();
			
		    }
		} catch (JSONException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		
		return null;
	    }
	}.execute(oid);
    }

    private Handler handler = new Handler(){

	@Override
	public void handleMessage(Message msg) {
	    // TODO Auto-generated method stub
	    super.handleMessage(msg);
	    HashMap<String, String> hashMap = (HashMap<String, String>) msg.obj;
	    nick.setText(hashMap.get("yh_xm"));
	    comment.setText(hashMap.get("pjnr"));
	    name.setText(hashMap.get("zxs_xm"));
	    level.setText(hashMap.get("dj"));
	    if(hashMap.get("pic").startsWith("http")){
		ImageLoader.getInstance().displayImage(hashMap.get("pic"), pic, options);
	    }else{
		ImageLoader.getInstance().displayImage(tools.Config.URL + hashMap.get("pic"), pic, options);
	    }
	}
	
    };
    private DisplayImageOptions options;
    private void prepareView() {
	back = (ImageView) findViewById(R.id.title_back);
	share = (TextView) findViewById(R.id.share_result_share);
	plus = (ImageView) findViewById(R.id.share_result_plus);
	nick = (TextView) findViewById(R.id.share_result_nick);
	pic = (ImageView) findViewById(R.id.share_result_pic);
	comment = (TextView) findViewById(R.id.share_result_comment);
	name = (TextView) findViewById(R.id.share_result_name);
	level = (TextView) findViewById(R.id.share_result_level);
	parent = (LinearLayout) findViewById(R.id.share_parent);
	back.setOnClickListener(this);
	share.setOnClickListener(this);
	plus.setOnClickListener(this);

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
	case R.id.share_result_share:
	    Bitmap b = GetandSaveCurrentImage();
	    // plus.setImageBitmap(b);
	    ImageView iv = new ImageView(this);
	    iv.setImageBitmap(b);
	    if(plus.getDrawable() == null){
		Toast.makeText(this, "传张美照吧！", Toast.LENGTH_SHORT).show();
	    }else{
		showShare();
	    }
	    break;
	case R.id.share_result_plus:
	    new SelectPicPopupWindow(ShareResult.this, parent);
	    break;
	default:
	    break;
	}
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
	case REQUESTCODE_PICK:// 直接从相册获取
	    setPicToView(data);
	    break;
	case REQUESTCODE_TAKE:// 调用相机拍照
	    setPicToView(data);
	    break;
	default:
	    break;
	}
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
	    Uri uri= picdata.getData();
	    //Bitmap photo = extras.getParcelable("data");
	    //Drawable drawable = new BitmapDrawable(null, photo);
	    plus.setImageURI(uri);
	}
    }
    
    
    private void showShare() {
	ShareSDK.initSDK(this);
	OnekeyShare oks = new OnekeyShare();
	// 关闭sso授权
	oks.disableSSOWhenAuthorize();
	//
	//
	// // 分享时Notification的图标和文字 2.5.9以后的版本不调用此方法
	// // oks.setNotification(R.drawable.ic_launcher,
	// // getString(R.string.app_name));
	// // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
	// oks.setTitle("测试测试测试");
	//
	// // text是分享文本，所有平台都需要这个字段
	// oks.setText("美咖来了美咖来了美咖来了美咖来了美咖来了美咖来了美咖来了美咖来了美咖来了美咖来了美咖来了美咖来了美咖来了美咖来了");
	// // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
	// oks.setImagePath(filepath);// 确保SDcard下面存在此张图片
	//
	// // url仅在微信（包括好友和朋友圈）中使用
	// oks.setUrl("http://meikall.com/");
	//
	//oks.setSilent(true);
	// 启动分享GUI
	oks.show(this);
	oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {

	    @Override
	    public void onShare(Platform platform, ShareParams paramsToShare) {
		// TODO Auto-generated method stub
		System.out.println("platform ---> " + platform.getName());
		if (platform.getName().equals("WechatMoments")) {
		    shareToMoment();
		}else{
		    shareToFriends();
		}
	    }
	});


    }
    
    class UpdateAsync extends AsyncTask<Void, Void, Void> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected Void doInBackground(Void... params) {
	    // TODO Auto-generated method stub
	    String url = tools.Config.SHARE_SCUESSS_URL + oid
		    + "&uid=" + new Tools().getUserId(ShareResult.this);
	    
	    Log.e("url", url);
	    String data = new Tools().getURL(url);
	    System.out.println(data);
	    return null;
	}

    }

    private void shareToMoment() {
	ShareParams sp = new ShareParams();
	sp.setImagePath(filepath);

	Platform pf = ShareSDK.getPlatform(WechatMoments.NAME);
	pf.setPlatformActionListener(new PlatformActionListener() {

	    @Override
	    public void onError(Platform arg0, int arg1, Throwable arg2) {
		// TODO Auto-generated method stub
		System.err.println("222");
	    }

	    @Override
	    public void onComplete(Platform arg0, int arg1,
		    HashMap<String, Object> arg2) {
		// TODO Auto-generated method stub
		System.err.println("1111");
		new UpdateAsync().execute();
	    }

	    @Override
	    public void onCancel(Platform arg0, int arg1) {
		// TODO Auto-generated method stub
		System.err.println("3333333");
	    }
	});//

	pf.share(sp);
    }
    
    private void shareToFriends() {
	ShareParams sp = new ShareParams();
	sp.setImagePath(filepath);

	Platform pf = ShareSDK.getPlatform(Wechat.NAME);
	pf.setPlatformActionListener(new PlatformActionListener() {

	    @Override
	    public void onError(Platform arg0, int arg1, Throwable arg2) {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public void onComplete(Platform arg0, int arg1,
		    HashMap<String, Object> arg2) {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public void onCancel(Platform arg0, int arg1) {
		// TODO Auto-generated method stub

	    }
	});//

	pf.share(sp);
    }

    /**
     * 获取和保存当前屏幕的截图
     */
    private Bitmap GetandSaveCurrentImage() {
	// 构建Bitmap
	WindowManager windowManager = getWindowManager();
	Display display = windowManager.getDefaultDisplay();
	int w = display.getWidth();
	int h = display.getHeight();
	Bitmap Bmp = Bitmap.createBitmap(w, h, Config.ARGB_8888);
	// 获取屏幕
	View decorview = this.getWindow().getDecorView();
	decorview.setDrawingCacheEnabled(true);
	Bmp = decorview.getDrawingCache();
	// 图片存储路径
	String SavePath = getSDCardPath() + "/Demo/ScreenImages";
	// 保存Bitmap
	try {
	    File path = new File(SavePath);
	    // 文件
	    filepath = SavePath + "/Screen_1.png";
	    File file = new File(filepath);
	    if (!path.exists()) {
		path.mkdirs();
	    }
	    if (!file.exists()) {
		file.createNewFile();
	    }
	    FileOutputStream fos = null;
	    fos = new FileOutputStream(file);
	    if (null != fos) {
		Bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
		fos.flush();
		fos.close();
	    } else {
		Toast.makeText(this, "截屏失败，请重试！", Toast.LENGTH_LONG).show();
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}

	return Bmp;
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
     * 获取SDCard的目录路径功能
     * 
     * @return
     */
    private String getSDCardPath() {
	File sdcardDir = null;
	// 判断SDCard是否存在
	boolean sdcardExist = Environment.getExternalStorageState().equals(
		android.os.Environment.MEDIA_MOUNTED);
	if (sdcardExist) {
	    sdcardDir = Environment.getExternalStorageDirectory();
	}
	return sdcardDir.toString();
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
