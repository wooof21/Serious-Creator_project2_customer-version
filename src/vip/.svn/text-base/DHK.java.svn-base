/**
 * 
 */
package vip;

import org.json.JSONException;
import org.json.JSONObject;

import tools.Config;
import tools.CustomProgressDialog;
import tools.Exit;
import tools.Tools;

import cn.sharesdk.onekeyshare.CustomerLogo;

import com.mkcomingc.R;

import android.app.Activity;
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
public class DHK extends Activity {

    private EditText num;
    private TextView submit;

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.dhk);
	Exit.getInstance().addActivity(this);
	num = (EditText) findViewById(R.id.dhk_num);
	submit = (TextView) findViewById(R.id.dhk_submit);

	submit.setOnClickListener(new OnClickListener() {

	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub
		if (num.getText().length() == 0) {
		    Toast.makeText(DHK.this, "«ÎÃÓ–¥–Ú¡–∫≈£°", Toast.LENGTH_SHORT)
			    .show();
		} else {
		    new AsyncTask<Void, Void, String>() {
			private CustomProgressDialog pd;
			private String msg = "";
			@Override
			protected void onPreExecute() {
			    // TODO Auto-generated method stub
			    super.onPreExecute();
			    pd = CustomProgressDialog.createDialog(DHK.this);
			    pd.show();
			}

			@Override
			protected String doInBackground(Void... params) {
			    // TODO Auto-generated method stub
			    String url = Config.DHK_URL
				    + new Tools().getUserId(DHK.this) + "&tel="
				    + new Tools().getUserName(DHK.this)
				    + "&kh=" + num.getText().toString();
			    Log.e("url", url);
			    String data = new Tools().getURL(url);
			    System.out.println(data);
			    String code = "";
			    
			    try {
				JSONObject job = new JSONObject(data);
				JSONObject result = job.getJSONObject("result");
				code = result.getString("code");
				msg = result.getString("msg");
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
			    if(result.equals("1")){
				Toast.makeText(DHK.this, "∂“ªªÃÂ—Èø®≥…π¶£°", Toast.LENGTH_SHORT).show();
				DHK.this.finish();
			    }else{
				Toast.makeText(DHK.this, msg, Toast.LENGTH_SHORT).show();
			    }
			}
		    }.execute();
		}
	    }
	});
    }
}
