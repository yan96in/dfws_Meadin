package com.dfws.shhreader.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.dfws.shhreader.R;
import com.dfws.shhreader.configures.FrameConfigure;
import com.dfws.shhreader.utils.FileAccess;


/**
 * 
 * @file�� DownloadService.java
 * @Page�� com.dfws.shhreader.activity
 * @description�� ����APK ����
 * @since��2013-11-21
 * @author�� Administrator
 */
public class DownloadService extends Service 
{
	private static final int NOTIFY_DOW_ID = 0;
	private static final int NOTIFY_OK_ID = 1;

    private Context mContext; 
    private boolean cancelled;  
    private int progress; 
    private NotificationManager mNotificationManager;  
    private Notification mNotification;  
    private DownloadBinder binder = new DownloadBinder();  
    
    private String serverUrl="" ;	//���ص�ַ
    private int fileSize;		//�ļ���С
    private int readSize;		//�����ض���
    private int downSize;		//���ض���
    private File downFile;		//�����ļ�
    private Intent intent;      //��ͼ
    private String version="";//�汾
    
    private Handler handler = new Handler()
    {  
        public void handleMessage(android.os.Message msg) 
        {  
            switch (msg.what) 
            {  
            	case 0:
            		// ֪ͨ������Ŀ
	    			RemoteViews contentView = mNotification.contentView;
	            	contentView.setTextViewText(R.id.rate, (readSize < 0 ? 0 : readSize) + "b/s   " + msg.arg1 + "%");  
	            	contentView.setProgressBar(R.id.progress, 100, msg.arg1, false);  
	
	            	// ˢ��UI
	            	mNotificationManager.notify(NOTIFY_DOW_ID, mNotification);
            		
                    break;
            	case 1:
            		createNotification(NOTIFY_OK_ID);
            		
                	//�򿪰�װAPK
                	openFile(downFile);
	                break;
            	case 2:
            		mNotificationManager.cancel(NOTIFY_DOW_ID);
            		break;
	        }  
        };  
    };  
    
    //֪ͨ�û�
    private Handler handMessage = new Handler()
    {
    	public void handleMessage(Message msg)
    	{
    		switch(msg.what)
    		{
    			case 0:
    				Toast.makeText(mContext, getString(R.string.download_01), Toast.LENGTH_SHORT).show();
    				break;
    			case 1:
    				Toast.makeText(mContext, getString(R.string.download_02), Toast.LENGTH_SHORT).show();
    				
    				break;    				
    		}
    		
    		handler.sendEmptyMessage(2);
    	}
    };
  
    @Override  
    public void onCreate() 
    {  
        super.onCreate();       
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        cancelled = true;
        mContext=getApplicationContext();
        intent = new Intent();
    }
    
    /* (non-Javadoc)
     * @see android.app.Service#onStartCommand(android.content.Intent, int, int)
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
    	serverUrl=intent.getStringExtra("url");
    	version=intent.getStringExtra("version");
    	return super.onStartCommand(intent, flags, startId);
    }
  
    @Override  
    public IBinder onBind(Intent intent)
    {  
        // downloadBinderʵ��
        return binder;  
    }  
  
    @Override  
    public void onDestroy() 
    {  
        super.onDestroy();  
        cancelled = true; // ȡ������
    }  
      
    /** 
     * ֪ͨ��Ŀ����
     */  
    @SuppressWarnings("deprecation")
	private void createNotification(int notifyId) 
    {
    	switch(notifyId)
    	{
    		case NOTIFY_DOW_ID:
    	        int icon = R.drawable.ic_launcher;  
    	        CharSequence tickerText = getString(R.string.download_05);  
    	        long when = System.currentTimeMillis();  
    	        mNotification = new Notification(icon, tickerText, when);      	  
    	        // �Զ�������֪ͨ��Ŀ
    	        mNotification.flags = Notification.FLAG_ONGOING_EVENT;      	  
    	        RemoteViews contentView = new RemoteViews(mContext.getPackageName(), R.layout.download_notification_layout);  
    	        contentView.setTextViewText(R.id.fileName, "Meadin.apk");      	       
    	        // ����֪ͨ��
    	        mNotification.contentView = contentView;       
    	        
    	        PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);  
    	        //...
    	        mNotification.contentIntent = contentIntent;      	        
    			break;
    		case NOTIFY_OK_ID:
    			int icon2 = R.drawable.ic_launcher; 
    	        CharSequence tickerText2 = "R.string.download_03";  
    	        long when2 = System.currentTimeMillis();  
    	        mNotification = new Notification(icon2, tickerText2, when2);  
    	        
    	        //��װ
    	        mNotification.flags = Notification.FLAG_AUTO_CANCEL;
    	        PendingIntent contentInten2 = PendingIntent.getActivity(mContext, 0, intent, 0);  
    	        mNotification.setLatestEventInfo(mContext, getString(R.string.download_03), getString(R.string.download_04), contentInten2);  
                Toast.makeText(DownloadService.this,getString(R.string.download_03) , Toast.LENGTH_SHORT).show();
                mNotificationManager.cancel(NOTIFY_DOW_ID);
                cancelled = true;
                stopSelf();//ֹͣ
                break;
    	}
    	
    	// �ر�֪ͨ��Ŀ
        mNotificationManager.notify(notifyId, mNotification);  
    }  
  
    /** 
     * ����׼��
     */  
    private void startDownload()
    {  
    	//��ʼ��
    	fileSize = 0;
    	readSize = 0;
    	downSize = 0;
    	progress = 0;
    	
    	InputStream is = null;
    	FileOutputStream fos = null;    	
    	try 
    	{
    		URL myURL = new URL(serverUrl);						//��ȡURL
        	URLConnection conn = myURL.openConnection();		//��ʼ����
            conn.connect();
        	fileSize = conn.getContentLength();					
        	is = conn.getInputStream();   						
        	
        	if (is == null) 
        	{  
        		Log.d("tag","error");
        		throw new RuntimeException("stream is null"); 
        	}
        	FileAccess.MakeDir(FrameConfigure.MAIN_FOLDER+"/apk");
        	downFile = new File(FrameConfigure.MAIN_FOLDER+"/apk/", "meadinreader"+version+".apk");     	
        	//д���ļ���
        	fos = new FileOutputStream(downFile);
        	byte buf[] = new byte[1024 * 1024];
        	while (!cancelled && (readSize = is.read(buf)) > 0) 
        	{   
        		fos.write(buf, 0, readSize);
        		downSize += readSize;
                
        		sendMessage(0);
        	}
        	
        	if(cancelled)
        	{
        		handler.sendEmptyMessage(2);
        		downFile.delete();
        	}
        	else
        	{
        		handler.sendEmptyMessage(1);
        	}
		}
    	catch (MalformedURLException e) 
    	{
			handMessage.sendEmptyMessage(0);
		} 
    	catch (IOException e) 
    	{
			handMessage.sendEmptyMessage(1);
		}  
        catch (Exception e)
        {
			handMessage.sendEmptyMessage(0);
		}
        finally
        {
        	try 
        	{
        		if(null != fos)	fos.close();
				if(null != is)	is.close();
			} 
        	catch (IOException e) 
			{
				e.printStackTrace();
			}
        }
    }
    
    public void sendMessage(int what)
    {
    	int num = (int)((double)downSize / (double)fileSize * 100);
    	    	
    	if(num > progress + 1)
    	{
			progress = num;
			
		    Message msg0 = handler.obtainMessage();  
		    msg0.what = what;  
		    msg0.arg1 = progress;
		    handler.sendMessage(msg0);
    	}
    }
    
	//������
    private void openFile(File f) 
    {
    	Intent intent = new Intent();
    	intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    	intent.setAction(android.content.Intent.ACTION_VIEW);      
    	//����
    	String type = "application/vnd.android.package-archive"; 
    	intent.setDataAndType(Uri.fromFile(f),type);
    	startActivity(intent); 
    }
      /** 
     * DownloadBinder
     * @author user 
     */  
    public class DownloadBinder extends Binder
    {
        /** 
         * ��ʼ����
         */  
        public void start() 
        {  
            cancelled = false;
            new Thread() 
            {  
                public void run() 
                {  
                    createNotification(NOTIFY_DOW_ID);		//����֪ͨ��
                    startDownload();	//������   
                    cancelled = true; 
                };  
            }.start();
        }
        
        /** 
         * ���ݸ�֪ͨ�����������
         *  
         * @return 
         */  
        public int getProgress() 
        {  
            return progress;  
        }   
        /** 
         * ȡ������
         */  
        public void cancel() 
        {  
            cancelled = true;  
        }  
        
        /** 
         * 
         *  
         * @return 
         */  
        public boolean isCancelled() 
        {  
            return cancelled;  
        }
        
    }
}