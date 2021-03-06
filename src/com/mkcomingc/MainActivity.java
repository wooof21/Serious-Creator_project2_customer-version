package com.mkcomingc;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import login.LoginActivity;
import tools.Config;
import tools.CustomProgressDialog;
import tools.Exit;
import tools.Tools;
import view.MyImgScroll;

import adapter.ADViewPagerAdapter;
import adapter.ViewPagerAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout.LayoutParams;

public class MainActivity extends BaseActivity implements OnClickListener{

	private TextView count;
	private LinearLayout reserve;
	private LinearLayout post;
	private RelativeLayout parent;
	private TextView city;
	private TextView cs;
	private CustomProgressDialog pd;

	// 定义ViewPager对象
	private ViewPager vp, adVP;

	// 定义ViewPager适配器
	private ViewPagerAdapter vpAdapter;
	private ADViewPagerAdapter adAdapter;

	// 定义一个ArrayList来存放View
	private ArrayList<View> views;//

	// private ArrayList<HashMap<String, String>> adList;

	private boolean isExit = false;

	private LinearLayout oval;
	private MyImgScroll isv;
	private ArrayList<View> adList;
	private ExecutorService executorService = Executors.newFixedThreadPool(5);
	private ImageView imageView;
	private Handler rhandler = new Handler();

	/*
	 * (non-Javadoc)
	 * @see com.mkcomingc.BaseActivity#onResume()
	 */
	@Override
	protected void onResume(){
		// TODO Auto-generated method stub
		super.onResume();
	}

	/*
	 * (non-Javadoc)
	 * @see com.mkcomingc.BaseActivity#onPause()
	 */
	@Override
	protected void onPause(){
		// TODO Auto-generated method stub
		super.onPause();

	}

	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onStop()
	 */
	@Override
	protected void onStop(){
		// TODO Auto-generated method stub
		super.onStop();
		isv.stopTimer();
	}

	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed(){
		// TODO Auto-generated method stub
		return;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		prepareView();
		Exit.getInstance().addActivity(this);

		new UpdateAsync().execute();
		if (new Tools().isFristRun(this)){
			SharedPreferences sharedPre = MainActivity.this
					.getSharedPreferences("frist", Context.MODE_PRIVATE);
			Editor editor = sharedPre.edit();
			editor.putString("frist", "0");
			editor.commit();
			Log.e("指导页启动----", "");
			// initView();
			// initData();
			/**********************************************************************************/

			vp.setOnPageChangeListener(new MyOnPageChangeListener());

			// 将要分页显示的View装入数组中
			LayoutInflater mLi = LayoutInflater.from(this);
			View view1 = mLi.inflate(R.layout.view1, null);
			View view2 = mLi.inflate(R.layout.view2, null);
			View view3 = mLi.inflate(R.layout.view3, null);
			View view4 = mLi.inflate(R.layout.view4, null);

			// 每个页面的view数据
			final ArrayList<View> views = new ArrayList<View>();
			views.add(view1);
			views.add(view2);
			views.add(view3);
			views.add(view4);

			// 填充ViewPager的数据适配器
			PagerAdapter pagerAdapter = new PagerAdapter(){

				@Override
				public boolean isViewFromObject(View arg0, Object arg1){
					return arg0 == arg1;
				}

				@Override
				public int getCount(){
					return views.size();
				}

				@Override
				public void destroyItem(View container, int position,
						Object object){
					((ViewPager) container).removeView(views.get(position));
				}

				@Override
				public Object instantiateItem(View container, int position){
					((ViewPager) container).addView(views.get(position));
					return views.get(position);
				}

			};
			vp.setAdapter(pagerAdapter);
			/**********************************************************************************/
		}else{
			vp.setVisibility(View.GONE);

			// init();
		}

	}

	private void init(){
		// if (!new Tools().isUserLogin(this)) {
		// startActivityForResult(new Intent(this, LoginActivity.class), 100);
		// } else {
		// new GrabAsync().execute();
		new NumAsync().execute();
		// }
	}

	class NumAsync extends AsyncTask<Void, Void, Void>{

		/*
		 * (non-Javadoc)
		 * @see android.os.AsyncTask#onPreExecute()
		 */
		@Override
		protected void onPreExecute(){
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = CustomProgressDialog.createDialog(MainActivity.this);
			pd.show();
		}

		/*
		 * (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected Void doInBackground(Void... params){
			// TODO Auto-generated method stub
			String data = new Tools().getURL(Config.USER_MAIN_URL);
			System.out.println(data);

			try{
				JSONObject jObject = new JSONObject(data);
				JSONObject result = jObject.getJSONObject("result");
				String code = result.getString("code");
				if (code.equals("1")){
					JSONObject job = jObject.getJSONObject("data");
					String total = job.getString("zs");
					Message msg = handler.obtainMessage();
					msg.what = 1;
					msg.obj = total;
					msg.sendToTarget();

				}
			}catch(JSONException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		/*
		 * (non-Javadoc)
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(Void result){
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pd.dismiss();
			new AdAsync().execute();
		}

	}

	/********************************** D27 *****************************************/
	public class MyOnPageChangeListener implements OnPageChangeListener{

		@Override
		public void onPageScrollStateChanged(int arg0){
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2){
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageSelected(int arg0){
			// TODO Auto-generated method stub

		}

	}

	public void start(View v){
		vp.setVisibility(View.GONE);
		init();
	}

	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onActivityResult(int, int,
	 * android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode){
			case 100:
				if (resultCode == 1000){

				}else{
					Toast.makeText(MainActivity.this, "登陆失败！",
							Toast.LENGTH_SHORT).show();
				}
			break;

			default:
			break;
		}
	}

	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg){
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch(msg.what){
				case 1:
					String total = (String) msg.obj;
					count.setText(total + "人");
				break;
				case 1234:
				// initDots();
				// adAdapter = new ADViewPagerAdapter(adList,
				// MainActivity.this);
				// adVP.setAdapter(adAdapter);
				// adVP.setCurrentItem(Integer.MAX_VALUE / 2
				// - ((Integer.MAX_VALUE / 2) % adList.size()));
				// msg = new Message();
				// msg.what = 101;
				// handler.sendMessageDelayed(msg, 3000);
				break;
				case 101:
					adVP.setCurrentItem(adVP.getCurrentItem() + 1);
					msg = new Message();
					msg.what = 101;
					handler.sendMessageDelayed(msg, 3000);
				break;
				default:
				break;
			}
		}

	};
	public ArrayList<String> urlList;

	class UpdateAsync extends AsyncTask<Void, Void, Void>{

		/*
		 * (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected Void doInBackground(Void... params){
			// TODO Auto-generated method stub
			String url = Config.UPDATE_CID_URL
					+ new Tools().getUserId(MainActivity.this) + "&cid="
					+ new Tools().getCID(MainActivity.this);
			Log.e("url", url);
			String data = new Tools().getURL(url);
			System.out.println(data);
			return null;
		}

		/*
		 * (non-Javadoc)
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(Void result){
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			new NumAsync().execute();
		}

	}

	private void prepareView(){
		count = (TextView) findViewById(R.id.textView2);
		reserve = (LinearLayout) findViewById(R.id.mian_yuyue);
		post = (LinearLayout) findViewById(R.id.mian_post);
		parent = (RelativeLayout) findViewById(R.id.main_parent);
		city = (TextView) findViewById(R.id.main_city);
		oval = (LinearLayout) findViewById(R.id.vb);
		isv = (MyImgScroll) findViewById(R.id.isv);
		cs = (TextView) findViewById(R.id.main_cs);

		cs.setOnClickListener(this);
		city.setOnClickListener(this);
		reserve.setOnClickListener(this);
		post.setOnClickListener(this);

		vp = (ViewPager) findViewById(R.id.viewpager);

	}

	/*
	 * (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v){
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, StyleSelectListActivity.class);
		switch(v.getId()){
			case R.id.mian_yuyue:
				intent.putExtra("type", "yy");
				startActivity(intent);
			break;
			case R.id.mian_post:
				intent.putExtra("type", "post");
				startActivity(intent);
			break;
			case R.id.main_city:
				new PopupWindows(this, parent);
			break;
			case R.id.main_cs:
				Intent intent1 = new Intent();
				intent1.setAction("android.intent.action.DIAL");
				intent1.setData(Uri.parse("tel:400-107-1377"));
				startActivity(intent1);//
			break;
			default:
			break;
		}
	}

	class AdAsync extends AsyncTask<Void, Void, Void>{
		private CustomProgressDialog pdDialog;

		/*
		 * (non-Javadoc)
		 * @see android.os.AsyncTask#onPreExecute()
		 */
		@Override
		protected void onPreExecute(){
			// TODO Auto-generated method stub
			super.onPreExecute();
			pdDialog = CustomProgressDialog.createDialog(MainActivity.this);
			pdDialog.show();
			adList = new ArrayList<View>();
			urlList = new ArrayList<String>();
		}

		/*
		 * (non-Javadoc)
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected Void doInBackground(Void... params){
			// TODO Auto-generated method stub
			String data = new Tools().getURL(Config.MAIN_AD_URL);
			System.out.println(data);

			try{
				JSONObject jObject = new JSONObject(data);
				JSONObject result = jObject.getJSONObject("result");
				String code = result.getString("code");
				if (code.equals("1")){
					JSONObject _data = jObject.getJSONObject("data");
					JSONArray jArray = _data.getJSONArray("jiaodian");
					for(int i = 0, j = jArray.length(); i < j; i++){
						JSONObject job = jArray.optJSONObject(i);
						HashMap<String, String> hashMap = new HashMap<String, String>();
						hashMap.put("id", job.getString("id"));
						hashMap.put("pic", job.getString("pic"));

						urlList.add(Config.URL + job.getString("pic"));

						// Message msg = handler.obtainMessage();
						// msg.what = 1234;
						// msg.sendToTarget();
					}

					for(final String urlStr : urlList){
						executorService.submit(new Runnable(){

							@Override
							public void run(){
								System.out.println("urlStr --> " + urlStr);
								// TODO
								// Auto-generated
								// method stub

								URL url;
								try{
									url = new URL(urlStr);
									final Drawable drawable = Drawable
											.createFromStream(url.openStream(),
													"src");

									rhandler.post(new Runnable(){

										@Override
										public void run(){
											imageView = new ImageView(
													MainActivity.this);
											// TODO
											// Auto-generated
											// method
											// stub
											imageView
													.setScaleType(ScaleType.CENTER_CROP);
											imageView
													.setImageDrawable(drawable);
											imageView
													.setLayoutParams(new LinearLayout.LayoutParams(
															LayoutParams.FILL_PARENT,
															LayoutParams.FILL_PARENT));
											imageView
													.setOnClickListener(new OnClickListener(){

														@Override
														public void onClick(
																View v){
															// TODO
															// Auto-generated
															// method stub
															// Log.e("index",
															// ""
															// +
															// isv.getCurIndex());
															// Log.e("web url",
															// webList.get(isv
															// .getCurIndex()));
															// Log.e("url list",
															// urlList.get(isv
															// .getCurIndex()));
															// Intent intent =
															// new Intent(
															// MainActivity.this,
															// LvYouWebviewmain.class);
															// intent.putExtra(
															// "url",
															// webList.get(isv
															// .getCurIndex()));
															// startActivity(intent);
														}
													});
											adList.add(imageView);
											System.out
													.println("adList ------> "
															+ adList.size());
											if (adList.size() == urlList.size()){
												System.out.println("2");
												isv.start(
														MainActivity.this,
														adList,
														3000,
														oval,
														R.layout.ad_bottom_item,
														R.id.ad_item_v,
														R.drawable.dot_focused,
														R.drawable.dot_normal);
											}
										}
									});
								}catch(MalformedURLException e1){
									// TODO
									// Auto-generated
									// catch block
									e1.printStackTrace();
								}catch(IOException e){
									// TODO
									// Auto-generated
									// catch block
									e.printStackTrace();
								}

							}

						});

					}

				}
			}catch(JSONException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		/*
		 * (non-Javadoc)
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(Void result){
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pdDialog.dismiss();//
		}

	}

	// /**
	// * 初始化dot
	// */
	// private void initDots() {
	// Log.e("ad size", adList.size()+"");
	// for (int i = 0; i < adList.size(); i++) {
	// View view = new View(this);
	// LayoutParams params = new LayoutParams(8, 8);
	// if (i != 0) {// 第一个点不需要左边距
	// params.leftMargin = 5;
	// }
	// view.setLayoutParams(params);
	// view.setBackgroundResource(R.drawable.selector_dot);
	// dots.addView(view);
	// }
	// }

	// /**
	// * 更新文本
	// */
	// private void updateDots() {
	// int currentPage = adVP.getCurrentItem() % adList.size();
	//
	// for (int i = 0; i < dots.getChildCount(); i++) {
	// dots.getChildAt(i).setEnabled(i == currentPage);// 设置setEnabled为true的话
	// // 在选择器里面就会对应的使用白色颜色
	// }
	// }

	/*
	 * 按俩次back键退出程序
	 */

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK){
			if (!isExit){
				isExit = true;
				Toast.makeText(getApplicationContext(), "再按一次退出程序",
						Toast.LENGTH_SHORT).show();
				new Handler().postDelayed(new Runnable(){
					public void run(){
						isExit = false;
					}
				}, 2000);

				return false;
			}
			Exit.getInstance().exit();
		}

		return super.onKeyDown(keyCode, event);
		// if(keyCode == KeyEvent.KEYCODE_MENU){
		// super.openOptionsMenu(); // 调用这个，就可以弹出菜单
		// }

	}

	class PopupWindows extends PopupWindow{

		public PopupWindows(Context mContext, View parent){

			View view = View.inflate(mContext, R.layout.popup_main, null);
			view.startAnimation(AnimationUtils.loadAnimation(mContext,
					R.anim.push_bottom_in_2));

			ImageView back = (ImageView) view.findViewById(R.id.title_back);
			TextView submit = (TextView) view.findViewById(R.id.main_popup_ok);

			setWidth(LayoutParams.FILL_PARENT);
			setHeight(LayoutParams.FILL_PARENT);
			// setBackgroundDrawable(new BitmapDrawable());
			setFocusable(true);
			setOutsideTouchable(true);
			setContentView(view);
			showAtLocation(parent, Gravity.CENTER, 0, 0);
			update();

			back.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v){
					// TODO Auto-generated method stub
					dismiss();
				}
			});
			submit.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v){
					// TODO Auto-generated method stub
					dismiss();
				}
			});
		}
	}

}
