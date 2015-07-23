/** 
* Copyright © 2013 www.veryeast.com 
* 
* Licensed under the Apache License, Version 2.0 (the "License"); 
* you may not use this file except in compliance with the License. 
* You may obtain a copy of the License at 
* 
* http://www.apache.org/licenses/LICENSE-2.0 
* 
* Unless required by applicable law or agreed to in writing, software 
* distributed under the License is distributed on an "AS IS" BASIS, 
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
* See the License for the specific language governing permissions and 
* limitations under the License. 
*/
package com.dfws.shhreader.entity;

/**
 * @file： Message.java
 * @Page： com.dfws.shhreader.entity
 * @description： 消息实体
 * @since： 2013-11-13
 * @author： Administrator
 */
public class Messages implements Comparable<Messages>{
   private int id;//新闻ID
   private int column;//类型
   private int type;//列
   private String title;//标题
   private String ptime;//发布时间
   private String push_time;//推送时间
   private String sort_time;//排序时间
   
   public Messages(){
	   
   }
     

public Messages(int id, int column, int type, String title, String ptime,
		String push_time, String sort_time) {
	super();
	this.id = id;
	this.column = column;
	this.type = type;
	this.title = title;
	this.ptime = ptime;
	this.push_time = push_time;
	this.sort_time = sort_time;
}


public String getSort_time() {
	return sort_time;
}


public void setSort_time(String sort_time) {
	this.sort_time = sort_time;
}


public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public int getColumn() {
	return column;
}
public void setColumn(int column) {
	this.column = column;
}
public int getType() {
	return type;
}
public void setType(int type) {
	this.type = type;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public String getPtime() {
	return ptime;
}
public void setPtime(String ptime) {
	this.ptime = ptime;
}
public String getPush_time() {
	return push_time;
}
public void setPush_time(String push_time) {
	this.push_time = push_time;
}
/* (non-Javadoc)
 * @see java.lang.Comparable#compareTo(java.lang.Object)
 */
@Override
public int compareTo(Messages another) {
	// TODO Auto-generated method stub
	return another.sort_time.compareToIgnoreCase(this.sort_time);
}
   
   
}
