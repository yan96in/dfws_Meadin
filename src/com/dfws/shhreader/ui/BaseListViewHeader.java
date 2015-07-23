package com.dfws.shhreader.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.dfws.shhreader.R;
import com.dfws.shhreader.activity.MainActivity;
import com.dfws.shhreader.activity.NewsDetailActivity;
import com.dfws.shhreader.activity.image.ImageActivitys;
import com.dfws.shhreader.configures.FrameConfigure;
import com.dfws.shhreader.entity.News;
import com.dfws.shhreader.net.utils.NewsImageLoader;
import com.dfws.shhreader.net.utils.NewsImageLoader.Callback;
import com.dfws.shhreader.utils.MD5Utils;
import com.dfws.shhreader.utils.ScreenUtil;

/**
 * <h2>新闻首页ListView的Header部分</h2>
 * 
 * @author Eilin.Yang
 * @version v1.0
 * @since 2013-9-17
 */
public class BaseListViewHeader extends RelativeLayout {

	private Context context;
	/**
	 * 图片展示ViewPager
	 */
	private ViewPager vp_base_list_header_imgbox;
	/**
	 * 当前图片标题
	 */
	private TextView txt_base_list_header_imgtitle;
	/**
	 * 图片索引容器
	 */
	private LinearLayout linear_base_list_header_imgindexbox;
	/**
	 * ViewPager适配器
	 */
	private ImgBoxPagerAdapter adapter;
	/**
	 * 新闻消息列表
	 */
	private List<News> newsList;
	/**
	 * 图片异步加载
	 */
	private NewsImageLoader imageLoader;
	/**
	 * 图片索引
	 */
	private LinearLayout.LayoutParams params;
	/**
	 * 图片
	 */
	private ViewGroup.LayoutParams mParams;
	/**
	 * 页面总数
	 */
	private int pageSize = 0;
	private int current_page = 0;
	/**
	 * 左右滑动
	 */
	private boolean mLeft, mRight;
	private ArrayList<ImageView> imageViews;

	public BaseListViewHeader(Context context, List<News> newsList) {
		this(context, null, newsList);
	}

	public BaseListViewHeader(Context context, AttributeSet attrs,
			List<News> newsList) {
		super(context, attrs);
		this.context = context;
		if (FrameConfigure.reading_type==FrameConfigure.TYPE_NIGHT) {
			LayoutInflater.from(context).inflate(R.layout.night_layout_base_list_header,
					this, true);
		}else {
			LayoutInflater.from(context).inflate(R.layout.layout_base_list_header,
					this, true);
		}
		if (null == newsList) {
			this.newsList = new ArrayList<News>();
		} else {
			this.newsList = newsList;
		}
		imageLoader = new NewsImageLoader(context);
		initView();
		initDatas(newsList);
	}

	private void initView() {
		// TODO Auto-generated method stub
		mParams = new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		params.rightMargin = ScreenUtil.dip2px(context, 5);
		params.width = ScreenUtil.dip2px(context, 3);
		params.height = ScreenUtil.dip2px(context, 3);
		vp_base_list_header_imgbox = (ViewPager) findViewById(R.id.vp_base_list_header_imgbox);
		txt_base_list_header_imgtitle = (TextView) findViewById(R.id.txt_base_list_header_imgtitle);
		linear_base_list_header_imgindexbox = (LinearLayout) findViewById(R.id.linear_base_list_header_imgindexbox);
		 vp_base_list_header_imgbox.setOnPageChangeListener(new
		 OnPageChangeListener() {
			
			 @Override
			 public void onPageSelected(int arg0) {
			 // TODO Auto-generated method stub
				 current_page=arg0;
				 indexSwitch(arg0);
			 }
			
			 @Override
			 public void onPageScrolled(int arg0, float arg1, int arg2) {
			 // TODO Auto-generated method stub
			
			 }
			
			 @Override
			 public void onPageScrollStateChanged(int arg0) {
			 // TODO Auto-generated method stub
//			  MainActivity.mPager.requestDisallowInterceptTouchEvent(true);
			 }
		 });
	}

	/**
	 * 初始化数据
	 * 
	 * @param list
	 *            下拉列表
	 */
	public void initDatas(List<News> list) {
		// TODO Auto-generated method stub
		if (list != null && !list.isEmpty()) {
			if (newsList.size() > 0) {
				newsList.clear();
			}
			newsList.addAll(list);
			int count=newsList.size();
			if (count>5) {
				newsList=newsList.subList(0, 4);
			}
			pageSize = newsList.size();
		}
		initIndex(pageSize);
		vp_base_list_header_imgbox.setAdapter(new ImgBoxPagerAdapter());
	}

	/**
	 * 初始化新闻索引
	 * 
	 * @param size
	 *            新闻总数
	 */
	private void initIndex(int size) {
		linear_base_list_header_imgindexbox.removeAllViews();
		if (size > 0) {
			for (int i = 0; i < size; i++) {
				News news = newsList.get(i);
				if (null != news) {
					ImageView iv = createImageView(news.getId());
					if (i == 0) {
						iv.setImageResource(R.drawable.ic_header_img_index_focus);
						txt_base_list_header_imgtitle.setText(news.getTitle());
					}
					linear_base_list_header_imgindexbox.addView(iv, i, params);
				}
			}
		} else {
			ImageView ivs = createImageView(-1);
			ivs.setImageResource(R.drawable.ic_header_img_index_focus);
			txt_base_list_header_imgtitle.setText(context
					.getString(R.string.news_header_img_def_title));
			linear_base_list_header_imgindexbox.addView(ivs, 0, params);
		}
	}

	/**
	 * 切换索引
	 * 
	 * @param position
	 *            当前位置
	 */
	private void indexSwitch(int position) {
		if (pageSize > 0) {
			for (int i = 0; i < pageSize; i++) {
				ImageView iv = (ImageView) linear_base_list_header_imgindexbox
						.getChildAt(i);
				if (null != iv) {
					iv.setImageResource(R.drawable.ic_header_img_index_normal);
					if (position == i) {
						iv.setImageResource(R.drawable.ic_header_img_index_focus);
						txt_base_list_header_imgtitle.setText(newsList.get(
								position).getTitle());
					}
				}
			}
		}
	}
	
	/**
	 * <pre>加载图片</pre>
	 * @param index 位置
	 */
	private void loadImg(ImageView view,int index){
		 if (pageSize > 0) {
				final News news = newsList.get(index);
				if (news != null) {
					String thumb_path = news.getThumb();
					Bitmap bitmaps=null;
					if (!TextUtils.isEmpty(thumb_path)) {
						String names = MD5Utils.MD5(thumb_path);
						view.setTag(thumb_path);
						view.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								int column=news.getColumn();
								Intent intent=null;
								if (column==2) {
									intent=new Intent(context, ImageActivitys.class);
								}else {
									intent=new Intent(context, NewsDetailActivity.class);
									intent.putExtra("from", 0);
									intent.putExtra("type", column);
								}
								intent.putExtra("news_id", news.getId());
								context.startActivity(intent);
							}
						});
						bitmaps = imageLoader.loadImage("Header", thumb_path,
								names, new Callback() {

									@Override
									public void imageLoaded(String path,
											String names, Bitmap bitmap) {
										// TODO Auto-generated method stub
										if (!TextUtils.isEmpty(path)
												&& null != bitmap) {
											ImageView iv = (ImageView) vp_base_list_header_imgbox
													.findViewWithTag(path);
											if (null != iv) {
												iv.setImageBitmap(bitmap);
											}
										}
									}
								});
						if (null == bitmaps) {
							bitmaps = BitmapFactory.decodeResource(
									getResources(), R.drawable.def_pic_big);
							if (FrameConfigure.reading_type==FrameConfigure.TYPE_NIGHT) {
								bitmaps = BitmapFactory.decodeResource(
										getResources(), R.drawable.pic_load_def_n);
							}
						}
						view.setImageBitmap(bitmaps);
					}
					else {
						bitmaps = BitmapFactory.decodeResource(
								getResources(), R.drawable.def_pic_big);
						if (FrameConfigure.reading_type==FrameConfigure.TYPE_NIGHT) {
							bitmaps = BitmapFactory.decodeResource(
									getResources(), R.drawable.pic_load_def_n);
						}
						view.setImageBitmap(bitmaps);
					}
				}
			}
			view.setScaleType(ScaleType.FIT_XY);
			imageViews.add(view);
	}

	/**
	 * <h2>ListView header ViewPager适配器</h2>
	 * 
	 * @author Eilin.Yang
	 * @since 2013-9-18
	 * @version v1.0
	 */
	private class ImgBoxPagerAdapter extends PagerAdapter {
		public ImgBoxPagerAdapter() {
			if (pageSize > 0) {
				imageViews = new ArrayList<ImageView>(pageSize);
				for (int i=0; i<pageSize;i++) {
					ImageView img = new ImageView(context);
					loadImg(img,i);
				}
			} else {
				imageViews = new ArrayList<ImageView>();
				ImageView defImageView = new ImageView(context);
				defImageView.setImageResource(R.drawable.def_pic_big);
				defImageView.setScaleType(ScaleType.FIT_XY);
				imageViews.add(defImageView);
			}
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return imageViews.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			ImageView view = imageViews.get(position);
			((ViewPager) container).addView(view, mParams);
			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPager) container).removeView(imageViews.get(position));
		}

	}

	/**
	 * 停止掉图片加载的线程
	 */
	public void release() {
		if (imageLoader != null) {
			imageLoader.quit();
		}
	}

	/**
	 * 创建创建索引
	 * 
	 * @param id
	 * @return
	 */
	private ImageView createImageView(int id) {
		ImageView iv = new ImageView(context);
		iv.setImageResource(R.drawable.ic_header_img_index_normal);
		if (id != -1) {
			iv.setId(id);
		}
		return iv;
	}

	float startX;
	float startY;
	// 这里是关键
	public boolean onInterceptTouchEvent(MotionEvent event) {
		int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:// 按下
			startX = event.getX();
			startY = event.getY();
			getParent().requestDisallowInterceptTouchEvent(true);
			break;
		// 滑动，在此对里层viewpager的第一页和最后一页滑动做处理
		case MotionEvent.ACTION_MOVE:
			float deX=event.getX()-startX;
			float deY=event.getY()-startY;
			if (Math.abs(deY)>Math.abs(deX)) {
				getParent().requestDisallowInterceptTouchEvent(false);
			}else {			
				if (startX == event.getX()) {
//					if (0 == vp_base_list_header_imgbox.getCurrentItem()
//							|| vp_base_list_header_imgbox.getCurrentItem() == vp_base_list_header_imgbox
//									.getAdapter().getCount() - 1) {
//						getParent().requestDisallowInterceptTouchEvent(false);
//					}
				}
				// 里层viewpager已经是最后一页，此时继续向右滑（手指从右往左滑）
				else if (startX > event.getX()) {
					if (vp_base_list_header_imgbox.getCurrentItem() == vp_base_list_header_imgbox
							.getAdapter().getCount() - 1) {
						getParent().requestDisallowInterceptTouchEvent(false);
					}
				}
				// 里层viewpager已经是第一页，此时继续向左滑（手指从左往右滑）
//				else if (startX < event.getX()) {
//					if (vp_base_list_header_imgbox.getCurrentItem() == 0) {
//						getParent().requestDisallowInterceptTouchEvent(false);
//					}
//				} 
				else {
					getParent().requestDisallowInterceptTouchEvent(true);
				}
			
			}
			break;
		case MotionEvent.ACTION_UP:// 抬起
		case MotionEvent.ACTION_CANCEL:
			getParent().requestDisallowInterceptTouchEvent(false);
			break;
		}
		return false;
	}
}
