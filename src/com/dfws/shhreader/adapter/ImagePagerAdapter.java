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
import com.dfws.shhreader.entity.News;
import com.dfws.shhreader.net.utils.NewsImageLoader;
import com.dfws.shhreader.net.utils.NewsImageLoader.Callback;
import com.dfws.shhreader.utils.MD5Utils;
/**
 * 
 * @file： ImagePagerAdapter.java
 * @Page： com.dfws.shhreader.adapter
 * @description：  图片  新闻
 * @since： 2013-10-31
 * @author： Administrator
 */
public class ImagePagerAdapter extends BaseAdapter {
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
	public ImagePagerAdapter(Context context,List<News> newsList,ListView mListView) {
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
	 * @return
	 */
	public int getItemObjectId(int position) {
		return newsList.get(position).getId();
	}
	
	/**
	 * 获取到每行对象的id
	 * @param position 位置
	 * @return
	 */
	public int getLastItemObjectId() {
		return newsList.get(newsList.size()-1).getId();
	}
	
	/**
	 * <h2>添加数据</h2>
	 * @param position 添加位置
	 * @param news 数据对象
	 */
	public void addItem(int position,News news) {
		if (position>getCount()||position<0) {
			newsList.add(news);
		}else {
			newsList.add(position, news);
		}
		notifyDataSetChanged();
	}
	
	/**
	 * <h2>添加数据集</h2>
	 * @param position 添加位置,<0则添加到最后
	 * @param list  数据集
	 */
	public void addItems(int position,List<News> list) {
		if (position>getCount()||position<0) {
			newsList.addAll(list);
		}else {
			newsList.addAll(position, list);
		}
		notifyDataSetChanged();
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
		ViewCache viewCache=null;Log.i("Fragment", "ImagePagerAdapter>> getView");
		if (convertView==null) {
			viewCache=new ViewCache();
			if (FrameConfigure.reading_type==FrameConfigure.TYPE_NIGHT) {
				convertView=inflater.inflate(R.layout.night_list_row_1, null);
			}else {
				convertView=inflater.inflate(R.layout.list_row_1, null);
			}
			viewCache.news_list_item_img=(ImageView)convertView.findViewById(R.id.list_imageview);
			viewCache.news_list_item_title=(TextView)convertView.findViewById(R.id.title);		
			convertView.setTag(viewCache);
		}else {
			viewCache=(ViewCache)convertView.getTag();
		}
		News news=newsList.get(position);
		if (news!=null) {
			viewCache.news_list_item_title.setText(news.getTitle());
			//viewCache.news_list_item_time.setText(news.getPotime());
			String img_path=news.getThumb();
			Bitmap bitmap=null;
			if (!TextUtils.isEmpty(img_path)) {
				viewCache.news_list_item_img.setTag(img_path);
				String names=MD5Utils.MD5(img_path);
				bitmap=loader.loadImage("ImagePager",img_path, names, new Callback() {					
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
					if (FrameConfigure.reading_type == FrameConfigure.TYPE_NIGHT) {
						bitmap = BitmapFactory.decodeResource(
								context.getResources(),
								R.drawable.pic_load_def_n);
					} else {
						bitmap = BitmapFactory.decodeResource(
								context.getResources(),
								R.drawable.def_pic_big);
					}
				}
				viewCache.news_list_item_img.setImageBitmap(bitmap);
			}
			else {
				if (FrameConfigure.reading_type == FrameConfigure.TYPE_NIGHT) {
					bitmap = BitmapFactory.decodeResource(
							context.getResources(),
							R.drawable.pic_load_def_n);
				} else {
					bitmap = BitmapFactory.decodeResource(
							context.getResources(),
							R.drawable.def_pic_big);
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
		//private TextView news_list_item_time;
	}
	
	public void release(){
		if (null!=loader) {
			loader.quit();
		}
	}
}