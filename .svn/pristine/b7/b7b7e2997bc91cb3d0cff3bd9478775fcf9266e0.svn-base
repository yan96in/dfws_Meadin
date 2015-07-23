package com.dfws.shhreader.ui.dialog;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;
/**
 * <h2>Toast</h2>
 * <pre>自定义toast,自定义显示位置，显示次数控制。只有初始化静态方法</pre>
 * @author Eilin.Yang
 * @since 2013-10-22
 * @version v1.0
 */
public class CustomerToast {
    static  Toast toast;
	/**
	 * 显示Toast
	 * @param context 当前上下文
	 * @param msg 显示信息
	 * @param islong 显示时间
	 */
	public static void showMessage(Context context,String msg,boolean islong)
	{		
		if (toast == null) {
			if (islong) {
				toast = Toast.makeText(context,
						msg, Toast.LENGTH_LONG);
			}
			else {
				toast = Toast.makeText(context,
						msg, Toast.LENGTH_SHORT);
			}
			
			toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            //toast.cancel();
            toast.setText(msg);
        }
        toast.show();
		
	}
	
	/**
	 * 显示Toast
	 * @param context 当前上下文
	 * @param msg 显示信息
	 * @param islong 显示时间
	 * @param gravity 显示位置
	 */
	public static void showMessage(Context context,String msg,boolean islong,int gravity)
	{		
		Toast toa=Toast.makeText(context, msg, (islong?Toast.LENGTH_LONG:Toast.LENGTH_SHORT));
		toa.setGravity(gravity, 0, 0);
        toa.show();
	}
	
	/**
	 * 显示toast
	 * @param context 当前上下文
	 * @param msg 显示信息
	 * @param islong 显示时间
	 * @param isCenter 是否显示在中间位置
	 */
	public static void showMessage(Context context,String msg,boolean islong,boolean isCenter)
	{		
		if (toast == null) {
			if (islong) {
				toast = Toast.makeText(context,
						msg, Toast.LENGTH_LONG);
			}
			else {
				toast = Toast.makeText(context,
						msg, Toast.LENGTH_SHORT);
			}
			if (isCenter) {
				toast.setGravity(Gravity.CENTER, 0, 0);
			}
			else {
			}
        } else {
            //toast.cancel();
            toast.setText(msg);
        }
        toast.show();
		
	}
}
