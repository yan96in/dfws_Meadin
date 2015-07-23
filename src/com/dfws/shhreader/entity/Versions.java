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
 * @file： Versions.java
 * @Page： com.dfws.shhreader.entity
 * @description：   版本实体
 * @since： 2013-11-13
 * @author： Administrator
 */
public class Versions  {
	
	private String latest_version;//版本号
	private String update_log;//更新内容
	private String app_url;//地址
		
	public Versions() {
	}
	
	public Versions(String latest_version, String update_log, String app_url) {
		super();
		this.latest_version = latest_version;
		this.update_log = update_log;
		this.app_url = app_url;
	}
	
	
	public String getLatest_version() {
		return latest_version;
	}
	public void setLatest_version(String latest_version) {
		this.latest_version = latest_version;
	}
	public String getUpdate_log() {
		return update_log;
	}
	public void setUpdate_log(String update_log) {
		this.update_log = update_log;
	}
	public String getApp_url() {
		return app_url;
	}
	public void setApp_url(String app_url) {
		this.app_url = app_url;
	}
	
	
	

}
