			var readmodes;
            
            /**跟踪查看*/
            function checkLogs(switc,tags){
                if (window.newsbean) {
                    window.newsbean.checkLog(switc,tags);
                }
            }

            /**获取标题*/
            function getTitle(){
                var divTitle = document.getElementById("mtitle");
                if (window.newsbean) {
                    divTitle.innerHTML = window.newsbean.getTitle();
                }
            }
            
            /**获取来源*/
            function getSource(){
                var mySource = document.getElementById('msource');
                if (window.newsbean) {
                    mySource.innerHTML = window.newsbean.getSource();
                }
            }
            
            /**获取发布时间*/
            function getPoTime(){
                var myTime = document.getElementById('mptime');
                if (window.newsbean) {
                    myTime.innerHTML = window.newsbean.getPtime();
                }
            }
            
            /**获取正文*/
            function getBody(){
               var divBody = document.getElementById('news_body');
                if (window.newsbean) {
                    divBody.innerHTML = window.newsbean.getContent();
                }
            }
            
            /**显示内容模块*/
            function showMainDiv(){
            	var main_div = document.getElementById('main_div');
            	if(window.newsbean){
            		main_div.style.display="block";
            	}
            }
            
            /**显示加载模块*/
            function showLoadingDiv(){
            	var loading_div = document.getElementById('loading_div');
            	if(window.newsbean){
            		loading_div.style.display="block";
            	}
            }
            
            /**隐藏内容模块*/
            function hideMainDiv(){
            	var main_div = document.getElementById('main_div');
            	if(window.newsbean){
            		main_div.style.display="none";
            	}
            }
            
            /**隐藏加载模块*/
            function hideLoadingDiv(){
            	var loading_div = document.getElementById('loading_div');
            	if(window.newsbean){
            		loading_div.style.display="none";
            	}
            }
            

			/**超大字体*/
			function showSuperBigSize() {
				var divBody = document.getElementById('news_body');
				divBody.style.fontSize = "26px";
				divBody.style.lineHeight = "160%";
			}
			/**大*/
			function showBigSize() {
				var divBody = document.getElementById('news_body');
				divBody.style.fontSize = "20px";
				divBody.style.lineHeight = "160%";
			}
			/**中*/
			function showMidSize() {
				var divBody = document.getElementById('news_body');
				divBody.style.fontSize = "18px";
				divBody.style.lineHeight = "160%";
			}
			/**小*/
			function showSmallSize() {
				var divBody = document.getElementById('news_body');
				divBody.style.fontSize = "16px";
				divBody.style.lineHeight = "160%";
			}
			
			/**显示图片*
			 * 
			 * @param id 图片id
			 * @param path 图片地址
			 */
			function showImage(id,path) {
                checkLogs(3,id);
				var img = document.getElementById(id);
				if(img){
					img.src=path;
                    checkLogs(4,id);
				}
			}
			
			/**显示图片*
			 * 
			 * @param id 图片id
			 * 
			 */
			function setImageIsLoading(id) {
				var img = document.getElementById(id);
				if(img){
					img.src="file:///android_asset/loading_new5.gif";
				}
			}

            /**
             * 
             * 开始加载图片
             * 
             */
            function initImageLoad(){
                if (window.newsbean) {
                    window.newsbean.initImageLoad();
                }
            }

             /**
             * 
             * 重置图片
             * 
             */
            function resetImageLoad(){
                if (window.newsbean) {
                    window.newsbean.resetImageLoad();
                }
            }
            
            /**获取字体大小*/
            function getTextSize() {
            	if (window.newsbean) {
            	    textSize = window.newsbean.getTextSize();
            	    switch(textSize) {
            	    case 3:
            	    	showSuperBigSize();
            	    	break;
            	    case 2:
            	    	showBigSize();
            	    	break;
            	    case 1:
            	    	showMidSize();
            	    	break;
            	    case 0:
            	    	showSmallSize();
            	    	break;
            	    }
            	}
            }
            
            /**图片添加单击事件*/
            function addImgClickListener(){
		        var objs = document.getElementsByTagName("img");
		        for(var i=0;i<objs.length;i++) {
		             (function(i){
		            	 objs[i].onclick=function(){
		            		 window.newsbean.clickImage(i,this.id,this.src);
		            	 }
		            })(i)
		        }
		    }

             /**图片添加单击事件*/
            function swichImageForReadMode(readmode){
                var objs = document.getElementsByTagName("img");
                if (objs) {
                    if (readmode==0) {
                         for(var i=0;i<objs.length;i++) {
                            if (objs[i].src=="file:///android_asset/djx.png") {
                                objs[i].src="file:///android_asset/n_djx.png";
                            }else if (objs[i].src=='file:///android_asset/pic_load_def.png') {
                                objs[i].src=='file:///android_asset/pic_load_def_n.png';
                            }
                            objs[i].style.border="0px solid #000";
                        }
                    }else{
                         for(var i=0;i<objs.length;i++) {
                            if (objs[i].src=="file:///android_asset/n_djx.png") {
                                objs[i].src="file:///android_asset/djx.png";
                            }else if (objs[i].src=='file:///android_asset/pic_load_def_n.png') {
                                objs[i].src=='file:///android_asset/pic_load_def.png';
                            }
                            objs[i].style.border="4px solid #fff";
                        }
                    }
               }
            }

        /**查看原文单击事件*/
            function setLinkToResourceListener(){
                var obj = document.getElementById("resource_link");
                if (obj) {
                      obj.onclick=function(){
                         window.newsbean.linkToResource();
                      }
                }
                
            }
            
            /**设置阅读模式*
             * 
             * @param mode 其他整数：白天模式，0：夜间模式。
             */
            function setReadMode(mode){
            	var vtitle=document.getElementById("mtitle");
            	var vsource=document.getElementById("source_div");
            	var vcontent=document.getElementById("news_body");
            	var vloading=document.getElementById("loading_div");
                var vloading1=document.getElementById("loading_div1");
                var vloading2=document.getElementById("loading_div2");
            	var vlinkto = document.getElementById("resource_link");
                readmodes=mode;
            	if(mode==0){
            		document.body.style.background="#24272c";
            		vtitle.style.color="#bdc0c5";
            		vsource.style.color="#606c78";
            		vcontent.style.color="#606c78";
            		vloading.style.color="#606c78";
                    vloading1.style.color="#606c78";
                    vloading2.style.color="#606c78";
                    vlinkto.style.color="#606c78";
                    swichImageForReadMode(0);
            	}else{
            		document.body.style.background="#f4f4f4";
            		vtitle.style.color="#282828";
            		vsource.style.color="#898989";
            		vcontent.style.color="#353535"
            		vloading.style.color="#353535"
                    vloading1.style.color="#353535"
                    vloading2.style.color="#353535"
                    vlinkto.style.color="#353535";
                    swichImageForReadMode(1);
            	}
            }
           
            /**获取夜间模式*/
            function getReadMode(){
            	if(window.newsbean){
                	var mode =window.newsbean.getReadMode();
                	setReadMode(mode);
            	}
            }
            
            /**初始化数据*/
            function inits(){
                checkLogs(1,"inits_start");
            	hideLoadingDiv();
            	showMainDiv();
                getTitle();
                getSource();
                getPoTime();
                getBody();
                setLinkToResourceListener();
                getTextSize();
                addImgClickListener();
                getReadMode();
                initImageLoad();
                checkLogs(1,"inits_end");
            }

             /**重置*/
            function resets(){
                checkLogs(1,"resets_start");
                hideLoadingDiv();
                showMainDiv();
                getTitle();
                getSource();
                getPoTime();
                getBody();
                setLinkToResourceListener();
                getTextSize();
                addImgClickListener();
                getReadMode();
                resetImageLoad();
                checkLogs(1,"resets_end");
            }
            
			//初始化
						
            window.onload = function(){
            	getReadMode();
            }