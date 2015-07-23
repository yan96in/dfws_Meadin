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
 * @file： Feedback.java
 * @Page： com.dfws.shhreader.entity
 * @description：   反馈
 * @since： 2013-11-13
 * @author： Administrator
 */
public class Feedback {
	
	private String content;//联系方式
	private String device_model;//意见反馈
	private String device_system;//设备硬件型号
	private String app_version;//设备系统
	private String pass_token;//应用版本
	private String push_token;//登录用户的票据
	private String contact;//设备推送码
	/**
	 *   get  and  set	
	 * @return
	 */
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDevice_model() {
		return device_model;
	}
	public void setDevice_model(String device_model) {
		this.device_model = device_model;
	}
	public String getDevice_system() {
		return device_system;
	}
	public void setDevice_system(String device_system) {
		this.device_system = device_system;
	}
	public String getApp_version() {
		return app_version;
	}
	public void setApp_version(String app_version) {
		this.app_version = app_version;
	}
	public String getPass_token() {
		return pass_token;
	}
	public void setPass_token(String pass_token) {
		this.pass_token = pass_token;
	}
	public String getPush_token() {
		return push_token;
	}
	public void setPush_token(String push_token) {
		this.push_token = push_token;
	}
	
	
	public Feedback() {
		super();
		// TODO Auto-generated constructor stub
	}
	


	@Override
	public String toString() {
		return "Feedback [contact=" + contact + ", content=" + content
				+ ", device_model=" + device_model + ", device_system="
				+ device_system + ", app_version=" + app_version
				+ ", pass_token=" + pass_token + ", push_token=" + push_token
				+ "]";
	}
	public Feedback(String contact, String content, String device_model,
			String device_system, String app_version, String pass_token,
			String push_token) {
		super();
		this.contact = contact;
		this.content = content;
		this.device_model = device_model;
		this.device_system = device_system;
		this.app_version = app_version;
		this.pass_token = pass_token;
		this.push_token = push_token;
	}





}
