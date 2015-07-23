package com.dfws.shhreader.adapter;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.dfws.shhreader.R;
import com.dfws.shhreader.configures.FrameConfigure;
import com.dfws.shhreader.configures.NewsConfigure;
import com.dfws.shhreader.entity.News;
import com.dfws.shhreader.net.utils.NewsImageLoader;
import com.dfws.shhreader.net.utils.NewsImageLoader.Callback;
import com.dfws.shhreader.utils.DateTimeUtils;
import com.dfws.shhreader.utils.MD5Utils;
/**
 * 
 * @file： FigurePagerAdapter.java
 * @Page： com.dfws.shhreader.adapter
 * @description：   新技术
 * @since： 2013-10-31
 * @author： Administrator
 */
public class TechnologyPagerAdapter extends BaseAdapter {
	private Context context;
	/**
	 * 新闻列表
	 */
	private List<News> newsList;
	private LayoutInflater inflater;
	/**
	 * 图片加载器
	 */
	private NewsImageLoader loader;
	/**
	 * 当前listview
	 */
	private ListView mListView;
	/**
	 * <h2>构造函数</h2>
	 * @param context 上下文对象
	 * @param newsList 新闻数据集
	 */
	public TechnologyPagerAdapter(Context context,List<News> newsList,ListView mListView) {
		if (newsList!=null) {
			this.newsList=newsList;
		}else {
			this.newsList=new ArrayList<News>();
		}
		inflater=LayoutInflater.from(context);
		loader=new NewsImageLoader(context);
		this.mListView=mListView;
		this.context=context;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return newsList.size();
	}

	@Override
	public News getItem(int position) {
		// TODO Auto-generated method stub
		if (position<0) {
			return newsList.get(0);
		}
		if (position>newsList.size()-1) {
			return newsList.get(newsList.size()-1);
		}
		return newsList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	/**
	 * 获取到每行对象的id
	 * @param position 位置
	 * @return 当empty时返回-1
	 */
	public int getItemObjectId(int position) {
		if (newsList.size()==0) {
			return -1;
		}
		if (position<0) {
			return getFirstItemObjectId();
		}
		if (position>newsList.size()-1) {
			return getLastItemObjectId();
		}
		return newsList.get(position).getId();
	}
	
	/**
	 * 获取到每行对象的id
	 * @param position 位置
	 * @return 当empty时返回-1
	 */
	public int getLastItemObjectId() {
		if (newsList.size()==0) {
			return -1;
		}
		return newsList.get(newsList.size()-1).getId();
	}
	
	/**
	 * 获取到每行对象的id
	 * @param position 位置
	 * @return 当empty时返回-1
	 */
	public int getFirstItemObjectId() {
		if (newsList.size()==0) {
			return -1;
		}
		return newsList.get(0).getId();
	}
	
	/**
	 * <h2>添加数据</h2>
	 * @param position 添加位置
	 * @param news 数据对象
	 */
	public void addItem(News news) {
		if (news!=null) {
			newsList.add(news);
			notifyDataSetChanged();
		}
	}
	
	/**
	 * <h2>添加数据集</h2>
	 * @param position 添加位置,<0则添加到最后
	 * @param list  数据集
	 */
	public void addItems(List<News> list) {
		if (list!=null) {
			newsList.addAll(list);
			notifyDataSetChanged();
			NewsConfigure.list_news_max_id=getLastItemObjectId();
		}
	}
	
	/**
	 * <h2>清除数据集</h2>
	 * @param position 添加位置,<0则添加到最后
	 * @param list  数据集
	 */
	public void clearItems() {
		if (newsList.size()>0) {
			newsList.clear();
		}
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewCache viewCache=null;Log.i("Fragment", "TechnologyPagerAdapter>> getView");
		if (convertView==null) {
			viewCache=new ViewCache();
			if (FrameConfigure.reading_type==FrameConfigure.TYPE_NIGHT) {
				convertView=inflater.inflate(R.layout.night_layout_news_list_item, null);
			}else {
				convertView=inflater.inflate(R.layout.layout_news_list_item, null);
			}
			viewCache.news_list_item_img=(ImageView)convertView.findViewById(R.id.news_list_item_img);
			viewCache.news_list_item_title=(TextView)convertView.findViewById(R.id.news_list_item_title);
			viewCache.news_list_item_time=(TextView)convertView.findViewById(R.id.news_list_item_time);
			convertView.setTag(viewCache);
		}else {
			viewCache=(ViewCache)convertView.getTag();
		}
		News news=newsList.get(position);
		if (news!=null) {
			viewCache.news_list_item_title.setText(news.getTitle());
			String time=news.getPotime();
			time=DateTimeUtils.getDateFromDatetime(time);
			viewCache.news_list_item_time.setText(time);
			String img_path=news.getThumb();
			Bitmap bitmap=null;
			if (!TextUtils.isEmpty(img_path)) {
				viewCache.news_list_item_img.setTag(img_path);
				String names=MD5Utils.MD5(img_path);
				bitmap=loader.loadImage("TechnologyPagerAdapter",img_path, names, new Callback() {
					
					@Override
					public void imageLoaded(String path, String names, Bitmap bitmap) {
						// TODO Auto-generated method stub
						if (!TextUtils.isEmpty(path)&&bitmap!=null) {
							ImageView iv=(ImageView)mListView.findViewWithTag(path);
							if (iv!=null) {
								iv.setImageBitmap(bitmap);
							}
						}
					}
				});
				if (bitmap==null) {
					if (FrameConfigure.reading_type==FrameConfigure.TYPE_NIGHT) {
						bitmap=BitmapFactory.decodeResource(context.getResources(), R.drawable.def_pic_small_night);
					}else {
						bitmap=BitmapFactory.decodeResource(context.getResources(), R.drawable.def_pic_small_day);
					}
				}
				viewCache.news_list_item_img.setImageBitmap(bitmap);
			}
			else {
				if (FrameConfigure.reading_type==FrameConfigure.TYPE_NIGHT) {
					bitmap=BitmapFactory.decodeResource(context.getResources(), R.drawable.def_pic_small_night);
				}else {
					bitmap=BitmapFactory.decodeResource(context.getResources(), R.drawable.def_pic_small_day);
				}
				viewCache.news_list_item_img.setImageBitmap(bitmap);
			}
		}
		return convertView;
	}

	/**
	 * <h2>item数据缓存</h2>
	 * @author Eilin.Yang
	 * @since 2013-10-17
	 */
	private class ViewCache{
		/**
		 * 新闻图片
		 */
		private ImageView news_list_item_img;
		/**
		 * 新闻标题
		 */
		private TextView news_list_item_title;
		/**
		 * 新闻时间
		 */
		private TextView news_list_item_time;
	}
}