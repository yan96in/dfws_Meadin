package com.dfws.shhreader.controllers;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.dfws.shhreader.R;
import com.dfws.shhreader.activity.AppInstance;
import com.dfws.shhreader.configures.FrameConfigure;
import com.dfws.shhreader.configures.NetConfigure;
import com.dfws.shhreader.configures.NewsConfigure;
import com.dfws.shhreader.entity.Comment;
import com.dfws.shhreader.entity.MediaInfo;
import com.dfws.shhreader.entity.Messages;
import com.dfws.shhreader.entity.News;
import com.dfws.shhreader.net.utils.HttpTools;
import com.dfws.shhreader.ui.dialog.CustomerToast;
import com.dfws.shhreader.utils.FileAccess;
import com.dfws.shhreader.utils.MD5Utils;
import com.dfws.shhreader.utils.NetWorkUtils;
import com.dfws.shhreader.utils.StringUtils;

/**
 * <h2>新闻列表控制器</h2>
 * <pre>整个首页的新闻列表控制器,包括图片新闻</pre>
 * @author 东方网升Eilin.Yang
 * @since 2013-10-23
 * @version v1.0
 * @modify ""
 */
public class NewsController extends AbsController {

	/**
	 * 当前上下文
	 */
	private Context context;
	/**
	 * 幻灯片
	 */
	private List<News> slideList;
	/**
	 * 离线下载的图片地址
	 */
	private List<String> imageList;
	/**
	 * 离线下载的缩略图图片地址
	 */
	private List<String> imageThumbList;
	/**
	 * 应用实例
	 */
    private boolean isOffLine=false;
	private AppInstance appInstance;
	public NewsController(Context context) {
		this.context=context;
		appInstance=(AppInstance)context.getApplicationContext();
	}
	/**
	 *<pre>从网络获取新闻列表</pre>
	 * @param pageIndex 页码。默认为1
	 * @param pageSize 每页大小。默认为10
	 * @param since_id 最小的新闻id.返回ID比since_id大的新闻（即比since_id时间晚的微博），默认为0。
	 * @param max_id 最大的新闻id。返回ID小于或等于max_id的新闻，默认为0。
	 * @param column 栏目类型。1：热点、2：图片、3：报告、 4：人物、5：新技术，默认为1。
	 * @param need_slide_news 是否需要幻灯片。是否返回大图幻灯新闻，0：否、1：是，默认为0。
	 * @param show_full_content 是否完整新闻内容，0：否、1：是，默认为0（返回id、title、thumb、digest、ptime）。
	 * @return List<News>
	 */
	public List<News> getNewsListFromNet(int pageIndex
			,int pageSize
			,int since_id
			,int max_id
			,int column
			,int need_slide_news
			,int show_full_content){
		isOffLine=false;
		String strResult ="";
		String name=MD5Utils.MD5(column+"")+".+";
		String path="";
		if (!checkNetStattus(context)) {
			return null;
		}	
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		nv.add(new BasicNameValuePair("page", pageIndex + ""));
		nv.add(new BasicNameValuePair("size", pageSize + ""));
//		nv.add(new BasicNameValuePair("since_id", since_id + ""));
//		nv.add(new BasicNameValuePair("max_id", max_id + ""));
		nv.add(new BasicNameValuePair("column", column + ""));
		nv.add(new BasicNameValuePair("need_slide_news", need_slide_news + ""));
		nv.add(new BasicNameValuePair("show_full_content", show_full_content + ""));
		String request = NetConfigure.news_interface;
		strResult = HttpTools.getJsonString(nv, request);
		ArrayList<News> newsList = null;
		if (!StringUtils.isEmpty(strResult)) {
			JSONObject jObject = null;
			try {
				jObject = new JSONObject(strResult);
				if (!StringUtils.isEmpty(jObject)) {
					if (!jObject.has("code")) {
						setHeader(jObject, column);
						path=getListPath(column, path);
						FileAccess.writeJsonIntoFile(path, name, strResult);
						JSONArray data = jObject.getJSONArray("news");
						if (!StringUtils.isEmpty(data)) {
							int n = data.length();
							newsList = new ArrayList<News>();
							for (int i = 0; i < n; i++) {
								if (show_full_content==1) {//离线下载
									newsList.add(parseAnNewsJson(data.getJSONObject(i),false));
								}
								newsList.add(parseAnNewsJson(data.getJSONObject(i),true));
							}
						}
						if (jObject.has("slide_news")) {//幻灯片
							JSONArray slides = jObject.getJSONArray("slide_news");
							if (!StringUtils.isEmpty(slides)) {
								int ns = slides.length();
								List<News> slidesList = new ArrayList<News>();
								for (int i = 0; i < ns; i++) {
									if (show_full_content==1) {//离线下载
										slidesList.add(parseAnNewsJson(slides.getJSONObject(i),false));
									}else
									slidesList.add(parseAnNewsJson(slides.getJSONObject(i),true));
								}
								setSlideList(slidesList);
							}
						}
					}
					else {
						NetConfigure.error_code=jObject.getString("code");
						NetConfigure.error_msg=jObject.getString("message");
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
				return newsList;
			}
		}
		return newsList;
	}
	
	/**
	 * 
	 *<pre>从本地文件获取新闻列表</pre>
	 * @param column 栏目.1：热点、2：图片、3：报告、 4：人物、5：新技术，默认为1。
	 * @return
	 */
	public List<News> getNewsListFromFile(int column) {
		isOffLine=false;
		String name=MD5Utils.MD5(column+"")+".+";
		String path="";
		path=getListPath(column, path);
		ArrayList<News> newsList = null;
		String strResult =FileAccess.readJsonFromFile(path, name);
		if (!StringUtils.isEmpty(strResult)) {
			JSONObject jObject = null;
			try {
				jObject = new JSONObject(strResult);
				if (!StringUtils.isEmpty(jObject)) {
					if (!jObject.has("code")) {
						setHeader(jObject, column);
						FileAccess.writeJsonIntoFile(path, name, strResult);
						JSONArray data = jObject.getJSONArray("news");
						if (!StringUtils.isEmpty(data)) {
							int n = data.length();
							newsList = new ArrayList<News>();
							for (int i = 0; i < n; i++) {
								newsList.add(parseAnNewsJson(data.getJSONObject(i),true));
							}
						}
						if (jObject.has("slide_news")) {//幻灯片
							JSONArray slides = jObject.getJSONArray("slide_news");
							if (!StringUtils.isEmpty(slides)) {
								int ns = slides.length();
								List<News> slidesList = new ArrayList<News>();
								for (int i = 0; i < ns; i++) {
									slidesList.add(parseAnNewsJson(slides.getJSONObject(i),true));
								}
								setSlideList(slidesList);
							}
						}
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
				return newsList;
			}
		}
		return newsList;
	}
	
	/**
	 * 
	 *<pre>设置请求列表的状态消息
	 *包括记录总数，是否有下一页</pre>
	 * @param jObject 数据对象
	 * @param column 栏目.1：热点、2：图片、3：报告、 4：人物、5：新技术，默认为1。
	 * @throws JSONException
	 */
	private void setHeader(JSONObject jObject,int column) throws JSONException{
		int count=jObject.getInt("counts");
		boolean has_more=jObject.getBoolean("has_more");
		switch (column) {
		case 1://新闻
			NewsConfigure.list_news_count=count;
			NewsConfigure.list_news_has_more=has_more;
			break;
		case 2://图片
			NewsConfigure.list_image_count=count;
			NewsConfigure.list_image_has_more=has_more;
			break;
		case 3://报告
			NewsConfigure.list_report_count=count;
			NewsConfigure.list_report_has_more=has_more;
			break;
		case 4://人物
			NewsConfigure.list_figure_count=count;
			NewsConfigure.list_figure_has_more=has_more;
			break;
		case 5://新技术
			NewsConfigure.list_technique_count=count;
			NewsConfigure.list_technique_has_more=has_more;
			break;
		default:
			NewsConfigure.list_news_count=count;
			NewsConfigure.list_news_has_more=has_more;
			break;
		}
	}
	
	/**
	 * 
	 *<pre>得到文件保存的本地路径</pre>
	 * @param column 栏目名称.1：热点、2：图片、3：报告、 4：人物、5：新技术，默认为1。
	 * @param path 要获取的路径
	 * @return
	 */
	private String getListPath(int column,String path){
		switch (column) {
		case 1:
			path=FrameConfigure.NORMAL_NEWS_OBJECT_LIST_DRC;
			break;
		case 2:
			path=FrameConfigure.NORMAL_IMAGE_OBJECT_LIST_DRC;
			break;
		case 3:
			path=FrameConfigure.NORMAL_REPORT_OBJECT_LIST_DRC;
			break;
		case 4:
			path=FrameConfigure.NORMAL_FIGURE_OBJECT_LIST_DRC;
			break;
		case 5:
			path=FrameConfigure.NORMAL_TECHNIQUE_OBJECT_LIST_DRC;
			break;
		default:
			path=FrameConfigure.NORMAL_NEWS_OBJECT_LIST_DRC;
			break;
		}
		return path;
	}
	
	/**
	 * 
	 *<pre>得到文件保存的本地路径</pre>
	 * @param column 栏目名称.1：热点、2：图片、3：报告、 4：人物、5：新技术，默认为1。
	 * @param path 要获取的路径
	 * @return
	 */
	public String getNewsPath(int column,String path){
		switch (column) {
		case 1:
			path=FrameConfigure.NORMAL_NEWS_OBJECT_NEWS_DRC;
			break;
		case 2:
			path=FrameConfigure.NORMAL_IMAGE_OBJECT_NEWS_DRC;
			break;
		case 3:
			path=FrameConfigure.NORMAL_REPORT_OBJECT_NEWS_DRC;
			break;
		case 4:
			path=FrameConfigure.NORMAL_FIGURE_OBJECT_NEWS_DRC;
			break;
		case 5:
			path=FrameConfigure.NORMAL_TECHNIQUE_OBJECT_NEWS_DRC;
			break;
		default:
			path=FrameConfigure.NORMAL_NEWS_OBJECT_NEWS_DRC;
			break;
		}
		return path;
	}
	
	/**
	 * 
	 *<pre>获取幻灯片</pre>
	 * @return
	 */
	public List<News> getSlideList(){
		return slideList;
	}
	/**
	 * 
	 *<pre>初始化幻灯片</pre>
	 * @param slideList
	 */
	public void setSlideList(List<News> slideList){
		this.slideList=slideList;
	}
	
	/**
	 * 缺URI
	 *<pre>从服务器获取新闻详细</pre>
	 * @param news_id 新闻id
	 * @return
	 */
	public News getNewsFromNet(int news_id,int column){
		if (!checkNetStattus(context)) {
			return null;
		}
		isOffLine=false;
		String strResult="";
		String path="";
		String name=MD5Utils.MD5(news_id+"")+".0";
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		nv.add(new BasicNameValuePair("id", news_id + ""));
		if (!StringUtils.isEmpty(appInstance.pass_token)) {
			nv.add(new BasicNameValuePair("pass_token", appInstance.pass_token));
		}
		String request = NetConfigure.news_detail_interface;
		strResult = HttpTools.getJsonString(nv, request);
		News news=null;
		if (!StringUtils.isEmpty(strResult)) {
			JSONObject jObject=null;
			try {
				jObject=new JSONObject(strResult);
				if (!StringUtils.isEmpty(jObject)) {
					if (!jObject.has("code")) {
						path=getNewsPath(column, path);
						FileAccess.writeJsonIntoFile(path, name, strResult);
						news=parseAnNewsJson(jObject, false);					
					}else {
						NetConfigure.error_code=jObject.getString("code");
						NetConfigure.error_msg=jObject.getString("message");
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return news;
			}
		}
		
		return news;
	}
	
	/**
	 *<pre>从服务器获取新闻评论条数</pre>
	 * @param news_id 新闻id
	 * @return
	 */
	public int getNewsCommentCount(int news_id){
		if (!checkNetStattus(context)) {
			return 0;
		}
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		nv.add(new BasicNameValuePair("news_id", news_id + ""));
		if (!StringUtils.isEmpty(appInstance.pass_token)) {
			nv.add(new BasicNameValuePair("pass_token", appInstance.pass_token));
		}
		int n=0;
		String request = NetConfigure.comments_list_interface;
		String strResult = HttpTools.getJsonString(nv, request);
		if (!StringUtils.isEmpty(strResult)) {
			JSONObject jObject=null;
			try {
				jObject=new JSONObject(strResult);
				if (!StringUtils.isEmpty(jObject)) {
					if (!jObject.has("code")) {
						n=jObject.getInt("counts");				
					}else {
						NetConfigure.error_code=jObject.getString("code");
						NetConfigure.error_msg=jObject.getString("message");
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return n;
			}
		}
		
		return n;
	}
	
	/**
	 * <pre>从服务器获取新闻评论列表</pre>
	 * @param news_id 新闻id
	 * @param pageindex 页码
	 * @param pagesize 每页记录数
	 * @return
	 */
	public List<Comment> getCommentList(int news_id,int pageindex,int pagesize){
		if (!checkNetStattus(context)) {
			return null;
		}
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		nv.add(new BasicNameValuePair("news_id", news_id + ""));
		nv.add(new BasicNameValuePair("page", pageindex + ""));
		nv.add(new BasicNameValuePair("size", pagesize + ""));
		String request = NetConfigure.comments_list_interface;
		String strResult = HttpTools.getJsonString(nv, request);
		List<Comment> comments=null;
		if (!StringUtils.isEmpty(strResult)) {
			JSONObject jObject=null;
			try {
				jObject=new JSONObject(strResult);
				if (!StringUtils.isEmpty(jObject)) {
					if (!jObject.has("code")) {
						NewsConfigure.comments_has_more=jObject.getBoolean("has_more");
						NewsConfigure.comments_count=jObject.getInt("counts");
						JSONArray jArray=jObject.getJSONArray("comments");
						if (!StringUtils.isEmpty(jArray)) {
							int sum=jArray.length();
							comments=new ArrayList<Comment>(sum);
							Comment comment=null;
							for (int i = 0; i < sum; i++) {
								JSONObject object=jArray.getJSONObject(i);
								comment=new Comment();
								String id=object.getString("id");
 								comment.setId(Integer.parseInt(id));
								comment.setIdstr(id);
								comment.setText(object.getString("text"));
								comment.setCreate_time(object.getString("created_time"));
								comment.setNewsId(news_id);
								comment.setUser_name(object.getString("screen_name"));
								comment.setUser_ip(object.getString("source_ip"));
								comment.setSupport(object.getInt("supported_num"));
								comments.add(comment);
							}
							
						}
					}else {
						NetConfigure.error_code=jObject.getString("code");
						NetConfigure.error_msg=jObject.getString("message");
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return comments;
			}
		}
		
		return comments;
	}
	
	/**
	 * <pre>创建单条评论</pre>
	 * @param news_id 新闻id
	 * @param text 评论内容
	 * @return
	 */
	public Comment createComment(int news_id,String text){
		if (!checkNetStattus(context)||StringUtils.isEmpty(text)) {
			return null;
		}
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		if (!StringUtils.isEmpty(appInstance.pass_token)) {
			nv.add(new BasicNameValuePair("pass_token", appInstance.pass_token));
		}
		String user_ip=StringUtils.getIpAddress();
		if (!StringUtils.isEmpty(user_ip)) {
			nv.add(new BasicNameValuePair("user_ip", user_ip));
		}
		nv.add(new BasicNameValuePair("news_id", news_id + ""));
		nv.add(new BasicNameValuePair("text", text));
		String request = NetConfigure.comments_create_interface;
		String strResult = HttpTools.getJsonString(nv, request);
		Comment comment=null;
		if (!StringUtils.isEmpty(strResult)) {
			JSONObject jObject=null;
			try {
				jObject=new JSONObject(strResult);
				if (!StringUtils.isEmpty(jObject)) {
					if (!jObject.has("code")) {
						comment=new Comment();
						String id=jObject.getString("id");
 						comment.setId(Integer.parseInt(id));
						comment.setIdstr(id);
						comment.setText(jObject.getString("text"));
						comment.setCreate_time(jObject.getString("created_time"));
						comment.setNewsId(news_id);
						comment.setUser_name(jObject.getString("screen_name"));
						comment.setUser_ip(jObject.getString("source_ip"));
						comment.setSupport(jObject.getInt("supported_num"));
					}else {
						NetConfigure.error_code=jObject.getString("code");
						NetConfigure.error_msg=jObject.getString("message");
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return comment;
			}
		}
		return comment;
	}
	
	/**
	 * <pre>赞单条评论</pre>
	 * @param news_id 新闻id
	 * @param comment_id 评论id
	 * @return
	 */
	public Comment supportComment(int news_id,String comment_id){
		if (!checkNetStattus(context)) {
			return null;
		}
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		nv.add(new BasicNameValuePair("news_id", news_id + ""));
		nv.add(new BasicNameValuePair("comment_id", comment_id));
		String request = NetConfigure.comments_support_interface;
		String strResult = HttpTools.getJsonString(nv, request);
		Comment comment=null;
		if (!StringUtils.isEmpty(strResult)) {
			JSONObject jObject=null;
			try {
				jObject=new JSONObject(strResult);
				if (!StringUtils.isEmpty(jObject)) {
					if (!jObject.has("code")) {
						comment=new Comment();
						String id=jObject.getString("id");
 						comment.setId(Integer.parseInt(id));
						comment.setIdstr(id);
						comment.setText(jObject.getString("text"));
						comment.setCreate_time(jObject.getString("created_time"));
						comment.setNewsId(news_id);
						comment.setUser_name(jObject.getString("screen_name"));
						comment.setUser_ip(jObject.getString("source_ip"));
						comment.setSupport(jObject.getInt("supported_num"));
					}else {
						NetConfigure.error_code=jObject.getString("code");
						NetConfigure.error_msg=jObject.getString("message");
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return comment;
			}
		}
		return comment;
	}
	
	/**
	 * 
	 *<pre>从本地文件获取新闻详细</pre>
	 * @param news_id 新闻id
	 * @return
	 */
	public News getNewsFromFile(int news_id,int column){
		String strResult="";
		String path="";
		path=getNewsPath(column, path);
		String name=MD5Utils.MD5(news_id+"")+".0";
		strResult=FileAccess.readJsonFromFile(path, name);
		News news=null;
		if (!StringUtils.isEmpty(strResult)) {
			JSONObject jObject=null;
			try {
				jObject=new JSONObject(strResult);
				if (!StringUtils.isEmpty(jObject)) {
					news=parseAnNewsJson(jObject, false);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return news;
			}
		}
		
		return news;
	}
	
	/**
	 * 
	 *<pre>解析新闻</pre>
	 * @param object 数据集
	 * @param isSimple 是否完整解析。true:不完整的，false:完整的
	 * @return 1：普通新闻、2：图片新闻。
	 * @throws JSONException 
	 */
	private News parseAnNewsJson(JSONObject object,boolean isSimple) throws JSONException{
		News news=null;
		if (!StringUtils.isEmpty(object)) {
			news=new News();
			int id=object.getInt("id");
			int type=object.getInt("type");
			news.setId(id);
			news.setIdStr(id+"");
			news.setTitle(object.getString("title"));
			news.setDigest(object.getString("digest"));
			news.setThumb(object.getString("thumb"));
			news.setType(type);
			news.setColumn(object.getInt("column"));
			news.setPotime(object.getString("ptime"));
			if (!isSimple) {
				String content=object.getString("content");
				List<MediaInfo> medias=null;
				news.setSource(object.getString("source"));
				news.setUrl(object.getString("url"));
				news.setComment_count(object.getInt("comment_count"));
				if (object.has("favorited")) {
					news.setCollected(object.getBoolean("favorited"));
				}
				if (object.has("media")) {
					JSONArray array=object.getJSONArray("media");
					if (!StringUtils.isEmpty(array)) {
						int n=array.length();
						medias=new ArrayList<MediaInfo>();
						for (int i = 0; i < n; i++) {
							medias.add(parseAnMediaJson(i,array.getJSONObject(i),id,type));
						}
						news.setMedias(medias);
						content=StringUtils.replaceImgToDefualt(context,content, medias);
 					}
				}
				news.setContent(content);
			}
		}
		return news;
	}	
	
	/**
	 * 
	 *<pre>解析媒体信息</pre>
	 * @param object 媒体数据集
	 * @param news_id 新闻id
	 * @param type 新闻类型
	 * @return MediaInfo
	 * @throws JSONException
	 */
	private MediaInfo parseAnMediaJson(int i,JSONObject object,int news_id,int type) throws JSONException{
		MediaInfo mInfo=null;
		if (!StringUtils.isEmpty(object)) {
			mInfo=new MediaInfo();
			String pixel=object.getString("pixel");
			int width=120;
			int height=90;
			if (!StringUtils.isEmpty(pixel)&&pixel.contains("*")) {
				int index=pixel.indexOf("*");
				String w=pixel.substring(0, index);
				String h=pixel.substring(index+1);
				width=Integer.parseInt(w);
				height=Integer.parseInt(h);
			}
			mInfo.setNews_id(news_id);
			mInfo.setId(news_id+"img"+(i+1));
			mInfo.setRef(object.getString("ref"));
			mInfo.setWidth(width);
			mInfo.setHeight(height);
			String src=object.getString("src");
			mInfo.setSrc(src);
			mInfo.setPath(FrameConfigure.NORMAL_IMG_DRC+MD5Utils.MD5(src));
			if (type==NewsConfigure.COLUMN_IMAGE) {
				mInfo.setAlt(object.getString("alt"));
			}
		}
		return mInfo;
	}
	
	/**
	 * 获取消息
	 * @param page 开始
	 * @param size  多少
	 * @param since_id  推送较早
	 * @param max_id   推送较晚
	 * @return
	 */
	public ArrayList<Messages> getNewsPush(int page,int size,int since_id,int max_id){
		if (!checkNetStattus(context)) {
			return null;
		}
		String strResult="";
		ArrayList<Messages> newsList = null;
		List<NameValuePair> ln=new ArrayList<NameValuePair>();
		ln.add(new BasicNameValuePair("page", page+""));
		ln.add(new BasicNameValuePair("size", size+""));
		ln.add(new BasicNameValuePair("since_id", since_id+""));
		ln.add(new BasicNameValuePair("max_id", max_id+""));
		String request= NetConfigure.news_push_interface;
		strResult = HttpTools.getJsonString(ln, request);
		if (!StringUtils.isEmpty(strResult)) {
			JSONObject  jsonObject=null;
			try {
				jsonObject=new JSONObject(strResult);
				if (!StringUtils.isEmpty(jsonObject)) {
					if (!jsonObject.has("code")) {
    					JSONArray array=jsonObject.getJSONArray("push_list");
    					int n=array.length();
    					newsList=new ArrayList<Messages>(n);
    					for (int i = 0; i < n; i++) {
    						Messages message=new Messages();
    						JSONObject data=array.getJSONObject(i);
    						String push_time=data.getString("push_time");
    						JSONObject newsJson=data.getJSONObject("news");
    						message.setId(newsJson.getInt("id"));
    						message.setColumn(newsJson.getInt("column"));
    						message.setType(newsJson.getInt("type"));
    						message.setTitle(newsJson.getString("title"));
    						message.setPush_time(push_time);
    						message.setPtime(newsJson.getString("pub_date"));
    						message.setSort_time(push_time);
    					    newsList.add(message);
						}
					}else {
						NetConfigure.error_code=jsonObject.getString("code");
						NetConfigure.error_msg=jsonObject.getString("message");
					}
				}				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return newsList;
			}			
		}
		return newsList;	
	}

	/**
	 * 离线下载
	 * @return true：下载完成，fasle：下载失败
	 */
	public boolean offLineDownLoad() {
		isOffLine=true;
		boolean flag=false;
		if (imageList==null) {
			imageList=new ArrayList<String>();
		}else {
			imageList.clear();
		}
		if (imageThumbList==null) {
			imageThumbList=new ArrayList<String>();
		}else {
			imageThumbList.clear();
		}
		for (int i = 1; i < 6; i++) {
			downloadFile(1, 10, 0, 0, i, (i==1?1:0),1);
		}
		flag=true;
		isOffLine=false;
		return flag;
	}
	
	/**
	 * 获取离线下载的图片地址集合
	 * @return 离线下载的图片地址集合
	 */
	public List<String> getImageUrList() {
		return imageList;
	}
	
	/**
	 * 获取离线下载的缩略图图片地址集合
	 * @return 离线下载的缩略图图片地址集合
	 */
	public List<String> getImageThumbUrList() {
		return imageThumbList;
	}
	
	/**
	 *    下载
	 * @param pageIndex
	 * @param pageSize
	 * @param since_id
	 * @param max_id
	 * @param column
	 * @param need_slide_news
	 * @param show_full_content
	 * @return
	 */
		public void downloadFile(
			int pageIndex
			,int pageSize
			,int since_id
			,int max_id
			,int column
			,int need_slide_news
			,int show_full_content){
			
		String strResult ="";
		String name=MD5Utils.MD5(column+"")+".+";
		String path="";
		if (!NetWorkUtils.checkNetWork(context)) {
			return;
		}
		List<NameValuePair> nv = new ArrayList<NameValuePair>();
		nv.add(new BasicNameValuePair("page", pageIndex + ""));
		nv.add(new BasicNameValuePair("size", pageSize + ""));
		nv.add(new BasicNameValuePair("since_id", since_id + ""));
		nv.add(new BasicNameValuePair("max_id", max_id + ""));
		nv.add(new BasicNameValuePair("column", column + ""));
		nv.add(new BasicNameValuePair("need_slide_news", need_slide_news + ""));
		nv.add(new BasicNameValuePair("show_full_content", show_full_content + ""));
		String request = NetConfigure.news_interface;
		strResult = HttpTools.getJsonString(nv, request);
		if (!StringUtils.isEmpty(strResult)) {
			JSONObject jObject = null;
			try {
				jObject = new JSONObject(strResult);
				if (!StringUtils.isEmpty(jObject)) {
					if (!jObject.has("code")) {
						setHeader(jObject, column);
						path=getListPath(column, path);
						FileAccess.writeJsonIntoFile(path, name, strResult);
						JSONArray data = jObject.getJSONArray("news");
						if (!StringUtils.isEmpty(data)) {
							int n = data.length();
							for (int i = 0; i < n; i++) {
								parseAnNewsJsonForOffline(data.getJSONObject(i).toString());							
							}
						}
						if (jObject.has("slide_news")) {//幻灯片
							JSONArray slides = jObject.getJSONArray("slide_news");
							if (!StringUtils.isEmpty(slides)) {
								int ns = slides.length();
								for (int i = 0; i < ns; i++) {						
									parseAnNewsJsonForOffline(slides.getJSONObject(i).toString());
								}
							}
						}
					}
					else {
						NetConfigure.error_code=jObject.getString("code");
						NetConfigure.error_msg=jObject.getString("message");
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
		/**
		 * 
		 *<pre>解析新闻</pre>
		 * @param object 数据集
		 * @param isSimple 是否完整解析。true:不完整的，false:完整的
		 * @return 1：普通新闻、2：图片新闻。
		 * @throws JSONException 
		 */
		private void parseAnNewsJsonForOffline(String obj) throws JSONException{
			JSONObject object=null;
			if (StringUtils.isEmpty(obj)) {
				return;
			}
			object=new JSONObject(obj);
			if (!StringUtils.isEmpty(object)) {
				int id=object.getInt("id");
				int column=object.getInt("column");
				String thumb=object.getString("thumb");
				if (!StringUtils.isEmpty(thumb)) {
					imageThumbList.add(thumb);
				}
				String paths="";
				String name=MD5Utils.MD5(id+"")+".0";
				paths=getNewsPath(column, paths);
				FileAccess.writeJsonIntoFile(paths, name, obj);
				if (object.has("media")) {
					JSONArray array=object.getJSONArray("media");
					if (!StringUtils.isEmpty(array)) {
						int n=array.length();
						for (int i = 0; i < n; i++) {
							parseAnMediaJsonForOffline(array.getJSONObject(i));
						}
 					}
				}
			}
		}
		
		/**
		 * 
		 *<pre>解析媒体信息</pre>
		 * @param object 媒体数据集
		 * @param news_id 新闻id
		 * @param type 新闻类型
		 * @return MediaInfo
		 * @throws JSONException
		 */
		private void parseAnMediaJsonForOffline(JSONObject object) throws JSONException{
			String src=object.getString("src");
			imageList.add(src);
		}
}
