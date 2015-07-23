package com.dfws.shhreader.controllers;

import android.content.Context;

import com.dfws.shhreader.R;
import com.dfws.shhreader.ui.dialog.CustomerToast;
import com.dfws.shhreader.utils.NetWorkUtils;

/**
 * <h2>父类控制器</h2>
 * <pre>所有控制器的的父类，是一个抽象类
 * 包含网络请求的判断</pre>
 * @author Eilin.Yang
 * @since 2013-10-23
 * @version v1.0
 */
public abstract class AbsController {

	/**
	 * 判断网络连接并提醒
	 * @param context 当前上下文
	 * @return true:online,false:offline
	 */
	protected boolean checkNetStattus(Context context) {
		if (!NetWorkUtils.checkNetWork(context)) {
			CustomerToast.showMessage(context, context.getString(R.string.check_net_status), true, true);
			return false;
		}
		return true;
	}
}