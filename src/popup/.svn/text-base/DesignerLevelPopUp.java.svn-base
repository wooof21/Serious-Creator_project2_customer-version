/**
 * 
 */
package popup;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tools.Config;
import tools.CustomProgressDialog;
import tools.Exit;
import tools.Tools;

import com.mkcomingc.BaseActivity;
import com.mkcomingc.R;

import adapter.PopUpLevelListAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * @author Liming Chu
 * 
 * @param
 * @return
 */
public class DesignerLevelPopUp extends BaseActivity {

    private ListView lv;
    private ArrayList<HashMap<String, String>> _list;

    
    /* (non-Javadoc)
     * @see com.mkcomingc.BaseActivity#onResume()
     */
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }
    /* (non-Javadoc)
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
	setContentView(R.layout.popup_level);
	Exit.getInstance().addActivity(this);

	lv = (ListView) findViewById(R.id.popup_level_lv);
	new AsyncTask<Void, Void, String>() {

	    private CustomProgressDialog pd;	

	    @Override
	    protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		pd = CustomProgressDialog.createDialog(DesignerLevelPopUp.this);
		pd.show();
		_list = new ArrayList<HashMap<String,String>>();
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
			for(int i=0,j=jArray.length();i<j;i++){
			    JSONObject job = jArray.optJSONObject(i);
			    HashMap<String, String> hashMap = new HashMap<String, String>();
			    hashMap.put("id", job.getString("id"));
			    hashMap.put("classname", job.getString("classname"));
			    
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
		if(result.equals("1")){
		    PopUpLevelListAdapter adapter = new PopUpLevelListAdapter(_list, DesignerLevelPopUp.this);
		    lv.setAdapter(adapter);
		    lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent,
				View view, int position, long id) {
			    // TODO Auto-generated method stub
			    Intent intent = getIntent();
			    intent.putExtra("id", _list.get(position).get("id"));
			    setResult(100, intent);
			    finish();
			}
		    });
		}else{
		    setResult(101);
		    finish();
		}
	    }

	}.execute();
    }
    
    /* (non-Javadoc)
     * @see android.app.Activity#onBackPressed()
     */
    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        setResult(102);
        finish();
    }
}
