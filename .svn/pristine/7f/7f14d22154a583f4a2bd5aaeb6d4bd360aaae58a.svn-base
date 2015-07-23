package com.dfws.shhreader.activity.personalcenter;

import com.dfws.shhreader.configures.FrameConfigure;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * @author Eilin.Yang
 * @since 2013-10-18
 * @version v1.0
 */
public class BaseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (FrameConfigure.reading_type==FrameConfigure.TYPE_NIGHT) {
			setTheme(android.R.style.Theme_Black_NoTitleBar);
		}else {
			setTheme(android.R.style.Theme_Light_NoTitleBar);
		}
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (FrameConfigure.reading_type==FrameConfigure.TYPE_NIGHT) {
			setTheme(android.R.style.Theme_Black_NoTitleBar);
		}else {
			setTheme(android.R.style.Theme_Light_NoTitleBar);
		}
	}

}
