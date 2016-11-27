/**
 * 
 */
package fragments;

import login.LoginActivity;
import order.OrderListMain;
import tools.Tools;
import vip.PersonalCenterMain;

import com.mkcomingc.MainActivity;
import com.mkcomingc.R;

import designers.DesignersList;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 底部4个导航控件fragment
 * 
 * @author Liming Chu
 * @param
 * @return
 */

public class MainBottomFragment extends Fragment implements OnClickListener {

    private LinearLayout ll1;
    private LinearLayout ll2;
    private LinearLayout ll3;
    private LinearLayout ll4;

    /** 底部导航4个按钮 */
    private ImageView iv1;
    private ImageView iv2;
    private ImageView iv3;
    private ImageView iv4;

    private TextView underLine1;
    private TextView underLine2;
    private TextView underLine3;
    private TextView underLine4;
    
    private TextView tv1, tv2, tv3, tv4;

    private String currentActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);

	System.out.println(getActivity().getClass().getName().toString());
	System.out.println(getActivity().getClass().getSimpleName().toString());
	currentActivity = getActivity().getClass().getSimpleName().toString();

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
	    @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
	// TODO Auto-generated method stub

	View view = inflater
		.inflate(R.layout.bottom_fragment, container, false);
	ll1 = (LinearLayout) view.findViewById(R.id.bottom_fragment_ll1);
	ll2 = (LinearLayout) view.findViewById(R.id.bottom_fragment_ll2);
	ll3 = (LinearLayout) view.findViewById(R.id.bottom_fragment_ll3);
	ll4 = (LinearLayout) view.findViewById(R.id.bottom_fragment_ll4);

	iv1 = (ImageView) view.findViewById(R.id.bottom_fragment_iv1);
	iv2 = (ImageView) view.findViewById(R.id.bottom_fragment_iv2);
	iv3 = (ImageView) view.findViewById(R.id.bottom_fragment_iv3);
	iv4 = (ImageView) view.findViewById(R.id.bottom_fragment_iv4);
	
	tv1 = (TextView) view.findViewById(R.id.fragment_tv1);
	tv2 = (TextView) view.findViewById(R.id.fragment_tv2);
	tv3 = (TextView) view.findViewById(R.id.fragment_tv3);
	tv4 = (TextView) view.findViewById(R.id.fragment_tv4);

	underLine1 = (TextView) view
		.findViewById(R.id.bottom_fragment_qiangdang_underline);
	underLine2 = (TextView) view
		.findViewById(R.id.bottom_fragment_yuyue_underline);
	underLine3 = (TextView) view
		.findViewById(R.id.bottom_fragment_zhangdan_underline);
	underLine4 = (TextView) view
		.findViewById(R.id.bottom_fragment_mine_underline);
	String className = getActivity().getClass().getSimpleName().toString();
	if (className.equals("MainActivity")) {
	    ll1.setClickable(false);
	    ll2.setOnClickListener(this);
	    ll3.setOnClickListener(this);
	    ll4.setOnClickListener(this);

	    iv1.setImageResource(R.drawable.meika_sel);
	    iv2.setImageResource(R.drawable.dingdan_un);
	    iv3.setImageResource(R.drawable.zxs_un);
	    iv4.setImageResource(R.drawable.wode_un);
	    tv1.setTextColor(Color.rgb(255, 91, 131));
	    tv2.setTextColor(Color.rgb(10, 11, 19));
	    tv3.setTextColor(Color.rgb(10, 11, 19));
	    tv4.setTextColor(Color.rgb(10, 11, 19));
	} else if (className.equals("OrderListMain")) {
	    ll2.setClickable(false);
	    ll1.setOnClickListener(this);
	    ll3.setOnClickListener(this);
	    ll4.setOnClickListener(this);
	    iv1.setImageResource(R.drawable.meika_un);
	    iv2.setImageResource(R.drawable.dingdan_sel);
	    iv3.setImageResource(R.drawable.zxs_un);
	    iv4.setImageResource(R.drawable.wode_un);
	    tv1.setTextColor(Color.rgb(10, 11, 19));
	    tv2.setTextColor(Color.rgb(255, 91, 131));
	    tv3.setTextColor(Color.rgb(10, 11, 19));
	    tv4.setTextColor(Color.rgb(10, 11, 19));
	} else if (className.equals("DesignersList")) {
	    ll3.setClickable(false);
	    ll2.setOnClickListener(this);
	    ll1.setOnClickListener(this);
	    ll4.setOnClickListener(this);
	    iv1.setImageResource(R.drawable.meika_un);
	    iv2.setImageResource(R.drawable.dingdan_un);
	    iv3.setImageResource(R.drawable.zxs_sel);
	    iv4.setImageResource(R.drawable.wode_un);
	    tv1.setTextColor(Color.rgb(10, 11, 19));
	    tv3.setTextColor(Color.rgb(255, 91, 131));
	    tv2.setTextColor(Color.rgb(10, 11, 19));
	    tv4.setTextColor(Color.rgb(10, 11, 19));
	} else if (className.equals("PersonalCenterMain")) {
	    ll4.setClickable(false);
	    ll2.setOnClickListener(this);
	    ll3.setOnClickListener(this);
	    ll1.setOnClickListener(this);
	    iv1.setImageResource(R.drawable.meika_un);
	    iv2.setImageResource(R.drawable.dingdan_un);
	    iv3.setImageResource(R.drawable.zxs_un);
	    iv4.setImageResource(R.drawable.wode_sel);
	    tv1.setTextColor(Color.rgb(10, 11, 19));
	    tv4.setTextColor(Color.rgb(255, 91, 131));
	    tv2.setTextColor(Color.rgb(10, 11, 19));
	    tv3.setTextColor(Color.rgb(10, 11, 19));
	}
	return view;
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
	case R.id.bottom_fragment_ll1:
	    startActivity(new Intent(getActivity(), MainActivity.class));
	    break;
	case R.id.bottom_fragment_ll2:
	    if(new Tools().isUserLogin(getActivity())){
		startActivity(new Intent(getActivity(), OrderListMain.class));
	    }else{
		startActivity(new Intent(getActivity(), LoginActivity.class));
	    }
	    
	    break;
	case R.id.bottom_fragment_ll3:
	    Intent intent = new Intent(getActivity(), DesignersList.class);
	    intent.putExtra("id", "");
	    intent.putExtra("name", "");
	    intent.putExtra("type", "frag");
	    startActivity(intent);
	    break;
	case R.id.bottom_fragment_ll4:
	    if(new Tools().isUserLogin(getActivity())){
		startActivity(new Intent(getActivity(), PersonalCenterMain.class));
	    }else{
		startActivity(new Intent(getActivity(), LoginActivity.class));
	    }
	    break;
	default:
	    break;
	}
	if (currentActivity.equals("MainActivity")) {

	} else {
	    getActivity().finish();
	}
    }

    private void setBg(int id){
	switch (id) {
	case R.id.bottom_fragment_ll1:
	    iv1.setImageResource(R.drawable.meika_sel);
	    iv2.setImageResource(R.drawable.dingdan_un);
	    iv3.setImageResource(R.drawable.zxs_un);
	    iv4.setImageResource(R.drawable.wode_un);
	    tv1.setTextColor(Color.rgb(255, 91, 131));
	    tv2.setTextColor(Color.rgb(10, 11, 19));
	    tv3.setTextColor(Color.rgb(10, 11, 19));
	    tv4.setTextColor(Color.rgb(10, 11, 19));
	    break;
	case R.id.bottom_fragment_ll2:
	    iv1.setImageResource(R.drawable.meika_un);
	    iv2.setImageResource(R.drawable.dingdan_sel);
	    iv3.setImageResource(R.drawable.zxs_un);
	    iv4.setImageResource(R.drawable.wode_un);
	    tv1.setTextColor(Color.rgb(10, 11, 19));
	    tv2.setTextColor(Color.rgb(255, 25591, 131));
	    tv3.setTextColor(Color.rgb(10, 11, 19));
	    tv4.setTextColor(Color.rgb(10, 11, 19));
	    break;
	case R.id.bottom_fragment_ll3:
	    iv1.setImageResource(R.drawable.meika_un);
	    iv2.setImageResource(R.drawable.dingdan_un);
	    iv3.setImageResource(R.drawable.zxs_sel);
	    iv4.setImageResource(R.drawable.wode_un);
	    tv1.setTextColor(Color.rgb(10, 11, 19));
	    tv3.setTextColor(Color.rgb(255, 25591, 131));
	    tv2.setTextColor(Color.rgb(10, 11, 19));
	    tv4.setTextColor(Color.rgb(10, 11, 19));
	    break;
	case R.id.bottom_fragment_ll4:
	    iv1.setImageResource(R.drawable.meika_un);
	    iv2.setImageResource(R.drawable.dingdan_un);
	    iv3.setImageResource(R.drawable.zxs_un);
	    iv4.setImageResource(R.drawable.wode_sel);
	    tv1.setTextColor(Color.rgb(10, 11, 19));
	    tv4.setTextColor(Color.rgb(255, 25591, 131));
	    tv2.setTextColor(Color.rgb(10, 11, 19));
	    tv3.setTextColor(Color.rgb(10, 11, 19));
	    break;
	default:
	    break;
	}
    }
    @Override
    public void onPause() {
	// TODO Auto-generated method stub
	super.onPause();
    }

}
