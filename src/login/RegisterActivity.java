/**
 * 
 */
package login;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import login.ForgetPswActivity.SmsObserver;

import org.json.JSONException;
import org.json.JSONObject;

import tools.Config;
import tools.CustomProgressDialog;
import tools.Exit;
import tools.Tools;

import com.igexin.getuiext.a.l;
import com.mkcomingc.BaseActivity;
import com.mkcomingc.MainActivity;
import com.mkcomingc.R;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.format.Time;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

/**
 * @author Liming Chu
 * 
 * @param
 * @return
 */
public class RegisterActivity extends BaseActivity implements OnClickListener{

	private LinearLayout step1, step2, step3;
	private EditText phone, et, psw, retype;
	private TextView getCode, phone2, phone3, next, submit;
	private GridView gv;

	private Uri SMS_INBOX = Uri.parse("content://sms/");
	private SmsObserver smsObserver;

	private CustomProgressDialog pd;

	private ArrayList<String> list;
	private GvAdapter adapter;
	private int count = 60;

	private Timer timer;
	private TimerTask timerTask;

	private String tel, code, uid;

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
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState){
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		Exit.getInstance().addActivity(this);
		prepareView();

	}

	private void prepareView(){
		step1 = (LinearLayout) findViewById(R.id.register_step1);
		phone = (EditText) findViewById(R.id.register_phone);
		getCode = (TextView) findViewById(R.id.register_getcode);
		step2 = (LinearLayout) findViewById(R.id.register_step2);
		phone2 = (TextView) findViewById(R.id.register_phone2);
		gv = (GridView) findViewById(R.id.register_gv);
		next = (TextView) findViewById(R.id.register_next);
		et = (EditText) findViewById(R.id.register_et);
		step3 = (LinearLayout) findViewById(R.id.register_step3);
		phone3 = (TextView) findViewById(R.id.register_phone3);
		psw = (EditText) findViewById(R.id.register_psw);
		retype = (EditText) findViewById(R.id.register_psw_retype);
		submit = (TextView) findViewById(R.id.register_submit);

		et.requestFocus();

		list = new ArrayList<String>();
		for(int i = 0; i < 6; i++){
			list.add("");
		}
		adapter = new GvAdapter(this);
		gv.setAdapter(adapter);
		gv.setHorizontalSpacing(0);

		gv.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id){
				// TODO Auto-generated method stub
				InputMethodManager m = (InputMethodManager) et.getContext()
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
			}
		});

		et.setInputType(InputType.TYPE_CLASS_NUMBER);
		et.addTextChangedListener(new TextWatcher(){

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count){
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after){
				// TODO Auto-generated method stub
			}

			@Override
			public void afterTextChanged(Editable s){
				// TODO Auto-generated method stub
				Log.e("s", s.toString());
				for(String string : list){
					if (string.equals("")){
						int index = list.indexOf(string);
						Log.e("index", "" + index);
						list.remove(index);
						list.add(index, s.toString());
						break;
					}
				}
				et.getText().clear();
				// adapter = new GvAdapter(RegisterActivity.this);
				// gv.setAdapter(adapter);
				// gv.setHorizontalSpacing(0);
				adapter.notifyDataSetChanged();
			}
		});
		et.setOnKeyListener(new OnKeyListener(){

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event){
				// TODO Auto-generated method stub
				if (keyCode == KeyEvent.KEYCODE_DEL
						&& event.getAction() == KeyEvent.ACTION_DOWN){
					Log.e("back", "back");
					for(int i = 0, j = list.size(); i < j; i++){
						if (!list.get(j - 1).equals("")){
							list.remove(j - 1);
							list.add("");
							break;
						}else{
							if (list.get(i).equals("")){
								list.remove(i - 1);
								list.add(i - 1, "");
								break;
							}
						}
					}
					adapter.notifyDataSetChanged();
				}
				return false;
			}
		});

		if (getCode.getText().toString().contains("����")){
			getCode.setOnClickListener(this);
		}else{
			getCode.setClickable(false);
		}
		next.setOnClickListener(this);
		submit.setOnClickListener(this);
		code = "";
	}

	/*
	 * (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v){
		// TODO Auto-generated method stub
		switch(v.getId()){
			case R.id.register_getcode:
				if (phone.getText().toString().length() == 0){
					Toast.makeText(RegisterActivity.this, "����д�ֻ����룡",
							Toast.LENGTH_SHORT).show();
				}else if (phone.getText().toString().length() != 11){
					Toast.makeText(RegisterActivity.this, "����д��ȷ��11λ�ֻ��ţ�",
							Toast.LENGTH_SHORT).show();
				}else{
					tel = phone.getText().toString();
					new AsyncTask<Void, Void, String>(){

						private CustomProgressDialog pd;

						@Override
						protected void onPreExecute(){
							// TODO Auto-generated method stub
							super.onPreExecute();
							pd = CustomProgressDialog
									.createDialog(RegisterActivity.this);
							pd.show();
						}

						@Override
						protected String doInBackground(Void... params){
							// TODO Auto-generated method stub
							String url = Config.GETCODE_URL
									+ phone.getText().toString();
							Log.e("url", url);
							String data = new Tools().getURL(url);
							System.out.println(data);
							String code = "";
							try{
								JSONObject job = new JSONObject(data);
								JSONObject result = job.getJSONObject("result");
								code = result.getString("code");
								String message = result.getString("msg");

								Message msg = handler.obtainMessage();
								if (code.equals("1")){
									msg.what = 1;
								}else{
									msg.what = 0;
								}
								msg.obj = message;
								msg.sendToTarget();

							}catch(JSONException e){
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							return code;
						}

						@Override
						protected void onPostExecute(String result){
							// TODO Auto-generated method stub
							super.onPostExecute(result);
							pd.dismiss();
							if (result.equals("1")){
								Message msg = handler.obtainMessage();
								msg.what = 100;
								msg.sendToTarget();
							}
						}

					}.execute();
				}
			break;
			case R.id.register_next:
				for(String s : list){
					code += s;
				}
				if (code.length() != 6){
					Toast.makeText(RegisterActivity.this, "����д��֤�룡",
							Toast.LENGTH_SHORT).show();
				}else{
					new AsyncTask<Void, Void, String>(){
						private CustomProgressDialog pd;

						@Override
						protected void onPreExecute(){
							// TODO Auto-generated method stub
							super.onPreExecute();
							pd = CustomProgressDialog
									.createDialog(RegisterActivity.this);
							pd.show();
						}

						@Override
						protected String doInBackground(Void... params){
							// TODO Auto-generated method stub
							String url = Config.VALIDATE_CODE_URL + tel
									+ "&yzm=" + code;
							Log.e("url", url);
							String data = new Tools().getURL(url);
							System.out.println(data);
							String code = "";

							try{
								JSONObject job = new JSONObject(data);
								JSONObject result = job.getJSONObject("result");
								code = result.getString("code");
								String message = result.getString("msg");

								Message msg = handler.obtainMessage();
								if (code.equals("1")){
									msg.what = 6;
								}else{
									msg.what = 0;
								}
								msg.obj = message;
								msg.sendToTarget();

							}catch(JSONException e){
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							return null;
						}

						@Override
						protected void onPostExecute(String result){
							// TODO Auto-generated method stub
							super.onPostExecute(result);
							pd.dismiss();
						}

					}.execute();
				}

			break;

			case R.id.register_submit:
				if (psw.getText().toString().length() == 0){
					Toast.makeText(RegisterActivity.this, "����д���룡",
							Toast.LENGTH_SHORT).show();
				}else if (!(psw.getText().toString().equals(retype.getText()
						.toString()))){
					Toast.makeText(RegisterActivity.this, "������������벻һ�£�",
							Toast.LENGTH_SHORT).show();
				}else{
					new AsyncTask<Void, Void, String>(){
						private CustomProgressDialog pd;

						@Override
						protected void onPreExecute(){
							// TODO Auto-generated method stub
							super.onPreExecute();
							pd = CustomProgressDialog
									.createDialog(RegisterActivity.this);
							pd.show();
						}

						@Override
						protected String doInBackground(Void... params){
							// TODO Auto-generated method stub
							String url = Config.REGISTER_URL + tel + "&pwd="
									+ psw.getText().toString();
							Log.e("url", url);
							String data = new Tools().getURL(url);
							System.out.println(data);
							String code = "";

							try{
								JSONObject job = new JSONObject(data);
								JSONObject result = job.getJSONObject("result");
								code = result.getString("code");
								String message = result.getString("msg");
								uid = result.getString("uid");

							}catch(JSONException e){
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							return code;
						}

						@Override
						protected void onPostExecute(String result){
							// TODO Auto-generated method stub
							super.onPostExecute(result);
							pd.dismiss();
							if (result.equals("1")){
								Toast.makeText(RegisterActivity.this, "ע��ɹ���",
										Toast.LENGTH_SHORT).show();
								saveLoginInfo(tel, psw.getText().toString(),
										uid);
								startActivity(new Intent(RegisterActivity.this,
										MainActivity.class));
								RegisterActivity.this.finish();
							}else{
								Toast.makeText(RegisterActivity.this,
										"ע��ʧ�ܣ������ԣ�", Toast.LENGTH_SHORT).show();
								RegisterActivity.this.finish();
							}
						}

					}.execute();
				}
			break;
			default:
			break;
		}
	}

	class SmsObserver extends ContentObserver{

		public SmsObserver(Context context, Handler handler){
			super(handler);
		}

		@Override
		public void onChange(boolean selfChange){
			super.onChange(selfChange);
			// ÿ�����¶��ŵ���ʱ��ʹ�����ǻ�ȡ����Ϣ�ķ���
			readSMS();
		}
	}

	private void readSMS(){
		ContentResolver cr = getContentResolver();
		String[] projection = new String[] { "body" };// "_id", "address",
		// "person",, "date",
		// "type
		String where = "address = '10690365045280' AND date >  "
				+ (System.currentTimeMillis() - 10 * 60 * 1000);
		Cursor cur = cr.query(SMS_INBOX, projection, where, null, "date desc");
		if (null == cur)
			return;
		if (cur.moveToNext()){
			// String number =
			// cur.getString(cur.getColumnIndex("address"));//�ֻ���
			// String name =
			// cur.getString(cur.getColumnIndex("person"));//��ϵ�������б�
			String body = cur.getString(cur.getColumnIndex("body"));
			System.out.println(body);
			// ��������Ҫ��ȡ�Լ����ŷ�������е���֤��~~
			Pattern pattern = Pattern.compile("[a-zA-Z0-9]{6}");
			Matcher matcher = pattern.matcher(body);
			if (matcher.find()){
				String res = matcher.group().substring(0, 6);
				Message msg = handler.obtainMessage();
				msg.what = 5;
				msg.obj = res;
				handler.sendMessage(msg);
			}
		}
	}

	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg){
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch(msg.what){
				case 0:
					Toast.makeText(RegisterActivity.this, (String) msg.obj,
							Toast.LENGTH_SHORT).show();
				break;
				case 1:
					startCount();
				break;
				case 2:
					getCode.setText("" + count + "s" + " " + "����ط�");
					getCode.setBackgroundColor(Color.GRAY);
					getCode.setClickable(false);
				break;
				case 3:
					getCode.setText("���·���");
					timer.cancel();
					getCode.setBackgroundColor(Color.rgb(239, 84, 131));
					getCode.setClickable(true);
				break;
				case 5:
					String code = (String) msg.obj;
					String code1 = code.substring(0, 1);
					String code2 = code.substring(1, 2);
					String code3 = code.substring(2, 3);
					String code4 = code.substring(3, 4);
					String code5 = code.substring(4, 5);
					String code6 = code.substring(5, 6);
					System.out.println("code ---> " + code);
					System.out.println("code1 ---> " + code1);
					System.out.println("code2 ---> " + code2);
					System.out.println("code3 ---> " + code3);
					System.out.println("code4 ---> " + code4);
					System.out.println("code5 ---> " + code5);
					System.out.println("code6 ---> " + code6);

					list.clear();
					list.add(code1);
					list.add(code2);
					list.add(code3);
					list.add(code4);
					list.add(code5);
					list.add(code6);
					adapter.notifyDataSetChanged();

				break;
				case 6:
					step2.setVisibility(View.GONE);
					step3.setVisibility(View.VISIBLE);
					phone3.setText(phone2.getText().toString());

				break;
				case 100:
					step1.setVisibility(View.GONE);
					step2.setVisibility(View.VISIBLE);
					phone2.setText("+86 " + tel.substring(0, 3) + " "
							+ tel.substring(3, 7) + " " + tel.substring(7, 11));
					smsObserver = new SmsObserver(RegisterActivity.this,
							handler);
					getContentResolver().registerContentObserver(SMS_INBOX,
							true, smsObserver);

					Timer timer = new Timer();
					timer.schedule(new TimerTask(){
						@Override
						public void run(){
							InputMethodManager m = (InputMethodManager) et
									.getContext().getSystemService(
											Context.INPUT_METHOD_SERVICE);
							m.toggleSoftInput(0,
									InputMethodManager.HIDE_NOT_ALWAYS);
						}
					}, 300);
				break;
				default:
				break;
			}
		}

	};
	private SharedPreferences sharedPre;
	private Editor editor;

	class GvAdapter extends BaseAdapter{

		private Context context;
		private LayoutInflater lInflater;

		public GvAdapter(Context context){
			super();
			this.context = context;
			this.lInflater = LayoutInflater.from(context);
		}

		/*
		 * (non-Javadoc)
		 * @see android.widget.Adapter#getCount()
		 */
		@Override
		public int getCount(){
			// TODO Auto-generated method stub
			return 6;
		}

		/*
		 * (non-Javadoc)
		 * @see android.widget.Adapter#getItem(int)
		 */
		@Override
		public Object getItem(int position){
			// TODO Auto-generated method stub
			return list.get(position);
		}

		/*
		 * (non-Javadoc)
		 * @see android.widget.Adapter#getItemId(int)
		 */
		@Override
		public long getItemId(int position){
			// TODO Auto-generated method stub
			return position;
		}

		/*
		 * (non-Javadoc)
		 * @see android.widget.Adapter#getView(int, android.view.View,
		 * android.view.ViewGroup)
		 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent){
			// TODO Auto-generated method stub
			convertView = lInflater.inflate(R.layout.register_gv_item, null);

			TextView et = (TextView) convertView
					.findViewById(R.id.register_item_et);

			et.setText(list.get(position));
			et.setGravity(Gravity.CENTER);

			return convertView;
		}

	}

	private void startCount(){

		timer = new Timer();
		timerTask = new TimerTask(){
			@Override
			public void run(){
				if (count > 0){
					Message msg = handler.obtainMessage();
					msg.what = 2;
					handler.sendMessage(msg);
				}else{

					Message msg = handler.obtainMessage();
					msg.what = 3;
					handler.sendMessage(msg);
				}
				count--;

			}
		};
		timer.schedule(timerTask, 0, 1000);

	}

	private void saveLoginInfo(String name, String pswd, String userId){
		sharedPre = RegisterActivity.this.getSharedPreferences("config",
				Context.MODE_PRIVATE);
		editor = sharedPre.edit();
		editor.putString("username", name);
		editor.putString("password", pswd);
		editor.putString("id", userId);
		editor.commit();
	}

	// private Handler handler = new Handler() {
	//
	// @Override
	// public void handleMessage(Message msg) {
	// // TODO Auto-generated method stub
	// super.handleMessage(msg);
	// switch (msg.what) {
	// case 0:
	// Toast.makeText(RegisterActivity.this, (String) msg.obj,
	// Toast.LENGTH_SHORT).show();
	// break;
	// case 1:
	// startCount();
	// break;
	// case 2:
	// getCode.setText("" + count + "s" + " " + "����ط�");
	// getCode.setBackgroundColor(Color.GRAY);
	// getCode.setClickable(false);
	// break;
	// case 3:
	// getCode.setText("���»�ȡ");
	// timer.cancel();
	// getCode.setBackgroundColor(Color.rgb(239, 84, 131));
	// getCode.setClickable(true);
	// break;
	// case 5:
	// String res = (String) msg.obj;
	// code.setText(res);
	// break;
	// default:
	// break;
	// }
	// }
	//
	// };
	//
	// private boolean validation() {
	// if (phone.getText().toString().length() == 0) {
	// Toast.makeText(this, "����д�ֻ��ţ�", Toast.LENGTH_SHORT).show();
	// return false;
	// } else if (phone.getText().toString().length() != 11) {
	// Toast.makeText(this, "����д��ȷ��11λ�ֻ��ţ�", Toast.LENGTH_SHORT).show();
	// return false;
	// } else if (code.getText().toString().length() == 0) {
	// Toast.makeText(this, "����д��֤�룡", Toast.LENGTH_SHORT).show();
	// return false;
	// } else if (psw.getText().toString().length() == 0) {
	// Toast.makeText(this, "����д���룡", Toast.LENGTH_SHORT).show();
	// return false;
	// } else if (rePsw.getText().toString().length() == 0) {
	// Toast.makeText(this, "��ȷ�����룡", Toast.LENGTH_SHORT).show();
	// return false;
	// } else if (!psw.getText().toString().equals(rePsw.getText().toString()))
	// {
	// Toast.makeText(this, "�����������벻һ�£�", Toast.LENGTH_SHORT).show();
	// return false;
	// } else {
	// return true;
	// }
	// }

}
