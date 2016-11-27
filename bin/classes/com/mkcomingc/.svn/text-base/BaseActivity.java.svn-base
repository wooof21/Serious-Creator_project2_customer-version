/**
 * 
 */
package com.mkcomingc;

import com.tencent.stat.StatService;

import android.app.Activity;

/**
 * @author Liming Chu
 * 
 * @param
 * @return
 */
public class BaseActivity extends Activity {

    // 3.1 页面统计
    // 使用下面的函数统计某个页面的访问情况：
    @Override
    protected void onResume() {
	super.onResume();
	// 页面开始 3.1.1 标记一次页面访问的开始
	StatService.onResume(this);
    }

    @Override
    protected void onPause() {
	super.onPause();
	// 页面结束 3.1.2 标记一次页面访问的结束
	StatService.onPause(this);
    }

    /**
     * （注意：onResume和onPause需要成对使用才能正常统计activity，为了统计准确性，建议在每个activity中都调用以上接口，
     * 否则可能会导致MTA上报过多的启动次数，解决办法参考“特殊需求”） *
     */

    /**
     * 3.1.3 通过继承的方式统计页面访问
     * 开发者可以通过app本身的acivity基类，调用MTA的onResume和onPause，并在所有子类中，
     * 重载这2个方法，实现页面统计功能。可参考MtaDemo中的BaseActivity和DrivedActivity代码。另外，MTA
     * SDK中的下面两个类实现了StatService
     * .onResume()和StatService.onPause()的调用，可直接继承以下类并在子类中重载页面统计接口。
     * 
     * com.tencent.stat.EasyActivity //继承自android.app.Activity
     * com.tencent.stat.EasyListActivity //继承自android.app.ListActivity
     * **/
}
