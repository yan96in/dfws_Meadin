package com.dfws.shhreader.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;

import android.os.Environment;
/**
 * 
 * <h2> IO操作<h2>
 * <pre>文件数据的读取，保存 </pre>
 * @author 东方网升Eilin.Yang
 * @since 2013-10-25
 * @version v1.0
 * @modify ""
 */
public class FileAccess {
	
	/**
	 * 从文件读取json字符串
	 * @param path 目录
	 * @param name 文件名称
	 * @return 返回字符串
	 */
	public static String readJsonFromFile(String path,String name){
		if (StringUtils.isEmpty(path)||StringUtils.isEmpty(name)) {
			return null;
		}
		String content="";
		File file=new File(path+name);
		if (!file.exists()) {
			return null;
		}
		InputStream in=null;
		try {
			in=new FileInputStream(file);
			if (in!=null) {
    				InputStreamReader inputreader = new InputStreamReader(in);
    				BufferedReader buffreader = new BufferedReader(inputreader);
    				String line;
				//分行读取
    				while (( line = buffreader.readLine()) != null) {
    					content += line;
    				}				
    			in.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return content;
	}
	
	/**
	 * json写入文件
	 * @param path 目录
	 * @param name 文件名称
	 * @param content json内容
	 * @return true:写入成功;false:写入失败
	 */
	public static boolean writeJsonIntoFile(String path,String name,String content){
		if (StringUtils.isEmpty(path)||StringUtils.isEmpty(name)||StringUtils.isEmpty(content)) {
			return false;
		}
		File file=new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		OutputStreamWriter out=null;
		try {
			out=new OutputStreamWriter(new FileOutputStream(new File(path+name)));
			out.write(content); 
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	/**
	 * 创建文件夹
	 * 
	 * @param dirName
	 */
	public static void MakeDir(String dirName) {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			File destDir = new File(dirName);
			if (!destDir.exists()) {
				destDir.mkdirs();
			}
		}
	}

	/**
	 * 删除此路径名表示的文件或目录。 如果此路径名表示一个目录，则会先删除目录下的内容再将目录删除，所以该操作不是原子性的。
	 * 如果目录中还有目录，则会引发递归动作。
	 * 
	 * @param filePath
	 *            要删除文件或目录的路径。
	 * @return 当且仅当成功删除文件或目录时，返回 true；否则返回 false。
	 */
	public static boolean DeleteFile(String filePath) {
		File file = new File(filePath);
		if (file.listFiles() == null)
			return true;
		else {
			File[] files = file.listFiles();
			for (File deleteFile : files) {
				if (deleteFile.isDirectory())
					DeleteAllFile(deleteFile);
				else
					deleteFile.delete();
			}
		}
		return true;
	}
	/**
	 * 删除全部文件
	 * 
	 * @param file
	 * @return
	 */
	private static boolean DeleteAllFile(File file) {
		File[] files = file.listFiles();
		for (File deleteFile : files) {
			if (deleteFile.isDirectory()) {
				// 如果是文件夹，则递归删除下面的文件后再删除该文件夹
				if (!DeleteAllFile(deleteFile)) {
					// 如果失败则返回
					return false;
				}
			} else {
				if (!deleteFile.delete()) {
					// 如果失败则返回
					return false;
				}
			}
		}
		return file.delete();
	}
	/**
	 * 得到数据库文件路径
	 * @return
	 */
	public static String GetDbFileAbsolutePath(){
//		String dbPath="/data/data/" + Config.APP_PACKAGE_NAME + "/databases/" + Config.DB_FILE_NAME;
//		return dbPath;
		return "";
	}
	/**
	 * 读取文件大小
	 * @param filePath
	 * @return
	 */
	public static long GetFileLength(String filePath){
		File file=new File(filePath);
		return file.length();
	}
	/**
	 * 读取文件夹大小
	 * @param dirPath
	 * @return
	 */
	public static long GetPathLength(String dirPath){
		File dir=new File(dirPath);
		if (dir==null||!dir.exists()) {
			return 0;
		}
		return getDirSize(dir);
	}
	/**
	 * 读取文件夹大小
	 * @param dir
	 * @return
	 */
	public static long getDirSize(File dir) {  
	    if (dir == null) {  
	        return 0;  
	    }  
	    if (!dir.isDirectory()) {  
	        return 0;  
	    }  
	    long dirSize = 0;  
	    File[] files = dir.listFiles();  
	    for (File file : files) {  
	        if (file.isFile()) {  
	            dirSize += file.length();  
	        } else if (file.isDirectory()) {  
	            dirSize += file.length();  
	            dirSize += getDirSize(file); // 如果遇到目录则通过递归调用继续统计  
	        }  
	    }  
	    return dirSize;  
	} 
	/**
	 * 将字长长度转换为KB/MB
	 * @param size
	 * @return
	 */
	public static String GetFileSize(long size){
		int kbSize=(int)size/1024;
		if(kbSize>1024){
			float mbSize=kbSize/1024;
			DecimalFormat formator=new DecimalFormat( "##,###,###.## ");
			return formator.format(mbSize) + "M";
		}
		return kbSize + "K";
	}
	
	/**
	 * 复制文件
	 * @param path1  源文件
	 * @param path2  目标文件
	 * @return
	 */
	public static boolean copyFileTo(String path1,String path2) {
		if (StringUtils.isEmpty(path1)||StringUtils.isEmpty(path2)) {
			return false;
		}
		File f1=new File(path1);
		File f2=new File(path2);
		if (!f1.exists()) {
			return false;
		}
		try {
			copyFileforChannel(f1, f2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * 用管道对接的方式复制文件
	 * @param f1 源文件
	 * @param f2 目标文件
	 * @throws Exception
	 */
	 public static void copyFileforChannel(File f1,File f2) throws Exception{
	        int length=2097152;
	        FileInputStream in=new FileInputStream(f1);
	        FileOutputStream out=new FileOutputStream(f2);
	        FileChannel inC=in.getChannel();
	        FileChannel outC=out.getChannel();
	        ByteBuffer b=null;
	        while(true){
	            if(inC.position()==inC.size()){
	                inC.close();
	                outC.close();
	            }
	            if((inC.size()-inC.position())<length){
	                length=(int)(inC.size()-inC.position());
	            }else
	                length=2097152;
	            b=ByteBuffer.allocateDirect(length);
	            inC.read(b);
	            b.flip();
	            outC.write(b);
	            outC.force(false);
	        }
	    }
}
