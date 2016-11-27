package getui.push;

import order.OrderListMain;
import tools.ShowNotification;
import login.LoginActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;

import com.igexin.sdk.PushConsts;
import com.igexin.sdk.PushManager;
import com.mkcomingc.MainActivity;
import com.mkcomingc.R;

public class PushDemoReceiver extends BroadcastReceiver {

    /**
     * 应用未启动, 个推 service已经被唤醒,保存在该时间段内离线消息(此时 GetuiSdkDemoActivity.tLogView ==
     * null)
     */
    public static StringBuilder payloadData = new StringBuilder();

    @Override
    public void onReceive(Context context, Intent intent) {
	Bundle bundle = intent.getExtras();
	Log.d("GetuiSdkDemo", "onReceive() action=" + bundle.getInt("action"));

	switch (bundle.getInt(PushConsts.CMD_ACTION)) {
	case PushConsts.GET_MSG_DATA:
	    // 获取透传数据
	    // String appid = bundle.getString("appid");
	    byte[] payload = bundle.getByteArray("payload");

	    String taskid = bundle.getString("taskid");
	    String messageid = bundle.getString("messageid");

	    // smartPush第三方回执调用接口，actionid范围为90000-90999，可根据业务场景执行
	    boolean result = PushManager.getInstance().sendFeedbackMessage(
		    context, taskid, messageid, 90001);
	    System.out.println("第三方回执接口调用" + (result ? "成功" : "失败"));

	    if (payload != null) {
		String data = new String(payload);
		String status = data.substring(0, 1);
		String text = data.substring(1, data.length());

		Log.d("GetuiSdkDemo", "receiver payload : " + data);
		System.out.println("status ---> " + status);
		System.out.println("text ---> " + text);

		Intent startIntent = new Intent(context, OrderListMain.class);
		startIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
			| Intent.FLAG_ACTIVITY_NEW_TASK);

		if (status.equals("1")) {
		    new ShowNotification().show(context, "您的订单已预约成功！",
			    Integer.valueOf(status), text, startIntent);
		} else if (status.equals("2")) {
		    new ShowNotification().show(context, "您的订单已有造型师抢单！",
			    Integer.valueOf(status), text, startIntent);
		} else {
		    new ShowNotification().show(context, "您有订单距离服务还有一小时！",
			    Integer.valueOf(status), text, startIntent);
		}

		payloadData.append(data);
		payloadData.append("\n");
		//
	    }
	    break;

	case PushConsts.GET_CLIENTID:
	    // 获取ClientID(CID)
	    // 第三方应用需要将CID上传到第三方服务器，并且将当前用户帐号和CID进行关联，以便日后通过用户帐号查找CID进行消息推送
	    String cid = bundle.getString("clientid");//
	    System.out.println("cid --> " + cid);
	    SharedPreferences sharedPre = context.getSharedPreferences(
		    "config", Context.MODE_PRIVATE);
	    Editor editor = sharedPre.edit();
	    editor.putString("cid", cid);
	    editor.commit();
	    break;

	case PushConsts.THIRDPART_FEEDBACK:
	    /*
	     * String appid = bundle.getString("appid"); String taskid =
	     * bundle.getString("taskid"); String actionid =
	     * bundle.getString("actionid"); String result =
	     * bundle.getString("result"); long timestamp =
	     * bundle.getLong("timestamp");
	     * 
	     * Log.d("GetuiSdkDemo", "appid = " + appid); Log.d("GetuiSdkDemo",
	     * "taskid = " + taskid); Log.d("GetuiSdkDemo", "actionid = " +
	     * actionid); Log.d("GetuiSdkDemo", "result = " + result);
	     * Log.d("GetuiSdkDemo", "timestamp = " + timestamp);
	     */
	    break;

	default:
	    break;
	}
    }
}
