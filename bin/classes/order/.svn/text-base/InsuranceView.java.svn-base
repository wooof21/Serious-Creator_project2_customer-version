/**
 * 
 */
package order;

import tools.Exit;

import com.mkcomingc.BaseActivity;
import com.mkcomingc.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * @author Liming Chu
 * 
 * @param
 * @return
 */
public class InsuranceView extends BaseActivity {

    
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
	setContentView(R.layout.insurance_view);
	Exit.getInstance().addActivity(this);

	findViewById(R.id.title_back).setOnClickListener(new OnClickListener() {

	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub
		finish();
	    }
	});

	findViewById(R.id.i_view_tv1).setOnClickListener(new OnClickListener() {

	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub
		startActivity(new Intent(InsuranceView.this, InsuranceSubView1.class));
	    }
	});

	findViewById(R.id.i_view_tv2).setOnClickListener(new OnClickListener() {

	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub
		startActivity(new Intent(InsuranceView.this, InsuranceSubView2.class));
	    }
	});

	findViewById(R.id.i_view_tv3).setOnClickListener(new OnClickListener() {

	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub
		startActivity(new Intent(InsuranceView.this, InsuranceSubView3.class));
	    }
	});
    
    }

}
