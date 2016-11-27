/**
 * 
 */
package order;

import com.mkcomingc.BaseActivity;
import com.mkcomingc.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * @author Liming Chu
 * 
 * @param
 * @return
 */
public class PaySuccess extends BaseActivity {

    private TextView total;
    private TextView product;
    private TextView orderNo;
    private TextView time;
    private TextView bank;
    private TextView status;

    
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
	setContentView(R.layout.pay_success);
    }

    private void prepareView() {
	total = (TextView) findViewById(R.id.pay_success_total);
	product = (TextView) findViewById(R.id.pay_success_product);
	orderNo = (TextView) findViewById(R.id.pay_success_order_no);
	time = (TextView) findViewById(R.id.pay_success_time);
	bank = (TextView) findViewById(R.id.pay_success_bank);
	status = (TextView) findViewById(R.id.pay_success_status);
    }
}
