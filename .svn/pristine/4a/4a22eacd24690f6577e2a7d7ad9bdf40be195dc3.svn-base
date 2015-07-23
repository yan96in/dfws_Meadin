package com.dfws.shhreader.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.AdapterView;
/**
 * viewPager can judge direction
 * @author Eilin.Yang
 *2013-4-7
 */
public class CustomViewPager extends ViewPager {
	/**标签*/
	public static final String TAG="CustomViewPager";
	/**向左滑动*/
	private boolean left = false;
	/**向右滑动*/
    private boolean right = false;
    /**滑动状态*/
    private boolean isScrolling = false;
    /**最后一个标签*/
    private int lastValue = -1;
    /**起始位置*/
	private int sx = 0;
	/**结束位置*/
	private int ex = 0;
    /**回调*/
    private OnPageChangeCallback callback = null;
    private DirectionListener directionListener;

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomViewPager(Context context) {
        super(context);
        init();
    }

    /**
     * init method .
     */
    private void init() {
        setOnPageChangeListener(listener);
    }

    /**
     * listener ,to get move direction .
     */
    public  OnPageChangeListener listener = new OnPageChangeListener() {
        @Override
        public void onPageScrollStateChanged(int arg0) {
        	if(null!=callback){
        		callback.OnPageStateChanged(arg0);
        	}
            if (arg0 == 1) {
                isScrolling = true;
            } else {
                isScrolling = false;
            }
            Log.i(TAG,"onPageScrollStateChanged : arg0:"+ arg0);
            if (arg0 == 2) {
                Log.i(TAG,"onPageScrollStateChanged  direction left:"+ left);
                Log.i(TAG,"onPageScrollStateChanged  direction right:"+ right);
                //notify ....
                if(callback!=null){
                	callback.changeView(left, right);
                }
                right = left = false;
            }

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            if (isScrolling) {
                if (lastValue > arg2) {
                    // 递减，向右侧滑动
                    right = true;
                    left = false;
                } else if (lastValue < arg2) {
                    // 递减，向右侧滑动
                    right = false;
                    left = true;
                } else if (lastValue == arg2) {
                    right = left = false;
                }
            }
        	Log.i(TAG,"onPageScrolled  last :arg0:arg1:arg2  , " + lastValue + ":" + arg0+":"+arg1+":"+arg2);
            lastValue = arg2;
        }

        @Override
        public void onPageSelected(int arg0) {
        	Log.i(TAG,"onPageSelected(int arg0)  arg0:"+arg0);
            if(callback!=null){
            	callback.onCurrentPage(arg0);
            }
        }
    };
    
    /**
     * 得到是否向右侧滑动
     * @return true 为右滑动
     */
    public boolean getMoveRight(){
        return right;
    }
    
    /**
     * 得到是否向左侧滑动
     * @return true 为左做滑动
     */
    public boolean getMoveLeft(){
        return left;
    }
    
    public interface OnPageChangeCallback {
        /**
         * 切换视图 ？决定于left和right 。
         * @param left
         * @param right
         */
        public  void changeView(boolean left,boolean right);
        /**
         * 在当前页
         * @param index 页码
         */
        public  void  onCurrentPage(int index);
        /**
         * <pre>当页面发生改变的时候</pre>
         * @param flag 0:什么都没做，1：滑动中,2:滑动结束
         */
        public void OnPageStateChanged(int flag);
    }
    
    /**
     * set ...
     * @param callback
     */
    public void  setOnPageChangeCallback(OnPageChangeCallback callback){
    	this.callback = callback;
    }
    
    /**已经按下*/
    boolean hasPress=false;
    @Override
	public boolean onTouchEvent(MotionEvent ev) {
    	
			switch (ev.getAction()) {
			case MotionEvent.ACTION_MOVE:
				if (!hasPress) {
					sx = (int) ev.getX();
					hasPress=true;
				}
				Log.d(TAG, "onTouchEvent(MotionEvent ev) ACTION_MOVE sx:ex----"+sx+":"+ex);
				break;
			case MotionEvent.ACTION_DOWN:
				Log.i(TAG, "onTouchEvent(MotionEvent ev)  ACTION_DOWN  sx:ex----"+sx+":"+ex);
				break;
			case MotionEvent.ACTION_UP:
				if(directionListener!=null)
		    		directionListener.ontouch();
				ex = (int) ev.getX();
				Log.i(TAG, "onTouchEvent(MotionEvent ev)  ACTION_UP sx:ex----"+sx+":"+ex);
				if (sx>ex) {//向左滑动
					if(directionListener!=null)
					directionListener.direction(true, false);
				}
				else if (sx<ex) {//向右滑动
					if(directionListener!=null)
					directionListener.direction(false, true);
				}else {
					if(directionListener!=null)
					directionListener.direction(false, false);
				}
				sx=0;
				ex=0;
				hasPress=false;
				break;
			}
		return super.onTouchEvent(ev);
	}
    
    public interface DirectionListener{
    	void direction(boolean left,boolean right);
    	void ontouch();
    }
    
    public void setDirectionListener(DirectionListener listener){
    	this.directionListener=listener;
    }
}
