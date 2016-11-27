/**
 * 
 */
package login;

import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import tools.Config;
import tools.CustomProgressDialog;
import tools.Exit;
import tools.Tools;

import com.mkcomingc.BaseActivity;
import com.mkcomingc.R;
import com.tencent.stat.StatService;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Liming Chu
 * 
 * @param
 * @return
 */
public class LoginActivity extends BaseActivity implements OnClickListener {

    private EditText phone;
    private EditText psw;
    private TextView forget;
    private TextView login;
    private TextView register;

    private CustomProgressDialog pd;
    private SharedPreferences sharedPre;
    private Editor editor;

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
	setContentView(R.layout.login);
	Exit.getInstance().addActivity(this);
	prepareView();
    }

    private void prepareView() {
	phone = (EditText) findViewById(R.id.login_phone);
	psw = (EditText) findViewById(R.id.login_psw);
	forget = (TextView) findViewById(R.id.login_gorget);
	login = (TextView) findViewById(R.id.login_login);
	register = (TextView) findViewById(R.id.login_register);

	forget.setOnClickListener(this);
	login.setOnClickListener(this);
	register.setOnClickListener(this);
    }

    private String getPhone() {
	return phone.getText().toString();
    }

    private String getPsw() {
	return psw.getText().toString();
    }

    private boolean validation() {
	if (phone.getText().toString().length() == 0) {
	    Toast.makeText(this, "请填写手机号码！", Toast.LENGTH_SHORT).show();
	    return false;
	} else if(phone.getText().toString().length() == 0){
	    Toast.makeText(this, "请填写正确的手机号码！", Toast.LENGTH_SHORT).show();
	    return false;
	}else if(psw.getText().toString().length() == 0){
	    Toast.makeText(this, "请填写密码！", Toast.LENGTH_SHORT).show();
	    return false;
	} else {
	    return true;
	}
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
	case R.id.login_gorget:
	    startActivity(new Intent(this, ForgetPswActivity.class));
	    break;
	case R.id.login_login:
	    if (!validation()) {
		
	    } else {
		new AsyncTask<Void, Void, String>() {
		    private String msg;
		    /*
		     * (non-Javadoc)
		     * 
		     * @see android.os.AsyncTask#onPreExecute()
		     */
		    @Override
		    protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();//
			pd = CustomProgressDialog
				.createDialog(LoginActivity.this);
			pd.setMessage("正在登陆中...");
			pd.show();
		    }

		    @Override
		    protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			String url = Config.LOGIN_URL + getPhone() + "&pwd="
				+ getPsw() + "&cid="
				+ new Tools().getCID(LoginActivity.this);
			Log.e("url", url);
			String code = "";

			String data = new Tools().getURL(url);
			System.out.println(data);

			try {
			    JSONObject jObject = new JSONObject(data);
			    JSONObject job = jObject.getJSONObject("result");
			    code = job.getString("code");
			    msg = job.getString("msg");
			    if (code.equals("1")) {
				String id = jObject.getJSONObject("data")
					.getString("id");
				saveLoginInfo(getPhone(), getPsw(), id);
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
			    setResult(1000);
			    // 上报登陆成功自定义事件，统计登陆次数和用户数
			    StatService.trackCustomEvent(LoginActivity.this, "login", "true");
			    finish();
			} else {
			    Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
			    setResult(1001);
			    // finish();
			    // 上报登陆失败事件
			    StatService.trackCustomEvent(LoginActivity.this,
				    "login", "false");
			}
		    }

		}.execute();
	    }
	    break;
	case R.id.login_register:
	    startActivity(new Intent(this, RegisterActivity.class));
	    break;
	default:
	    break;
	}
    }

    private void saveLoginInfo(String name, String pswd, String userId) {
	sharedPre = LoginActivity.this.getSharedPreferences("config",
		Context.MODE_PRIVATE);
	editor = sharedPre.edit();
	editor.putString("username", name);
	editor.putString("password", pswd);
	editor.putString("id", userId);
	editor.commit();
    }
}
