-----------------------------------------------------------------------------
                       移动测试服务器                                  
-----------------------------------------------------------------------------
--  移动测试服务器url: 
 
 
---  服务器配置 
        -- 前置服务器
             -- 前置端口:
             -- 负载均衡接口:
             -- 连接app服务器url
         -- app服务器
             -- 端口:
             -- 连接api的url:
                
         -- api服务器
             -- 端口:
             
  
                       
                       
                       
---  菜单添加功能    
     --   menu表          
     --          004003 微信菜单管理	./equipmentMonitoring/equipmentMonitoring	 fa fa-coffee AMDQ
     --   menurights表          
     --          004003	1	1	AMDQ	0              
              
                       
     -- 删除菜单
            delete from 表名 where 条件
                       
     -- 添加菜单                  
                       
                       
                       

---  移动测试服务器 
	  -- mysql -unetapp -proot@1234;
	  --  use dcdzintelligentstorageapp;
	   
	   ----  导出数据
	  -- 文件目录>   mysqldump -unetapp -p  -d  dcdzintelligentstorageapp > dcdzintelligentstorageapp.sql;
	  --            root@1234
 
	   ----  导入数据
	  -- 文件目录>   mysql -unetapp -p dcdzintelligentstorageapp <  /home/magui_app/netAllAppProject/mysqles/dcdzintelligentstorageapp.sql
	  --           root@1234
      -- mysql>    source /home/magui_app/netAllAppProject/mysqles/dcdzintelligentstorageapp.sql;

 
                       
                       
                       
                       
                       
                       
--- 数据库表添加字段 
          --- 进入数据库 
              use dcdzintelligentstorageapp;
              show  tables;
          --- 数据库 查看表结构
              desc terminal;
          --- 数据库添加字段    
              --  alter table test add COLUMN test1 VARCHAR(20) DEFAULT NULL; //增加一个字段，默认为空  
              --  alter table test add COLUMN test2 VARCHAR(20) NOT NULL;  //增加一个字段，默认不能为空   
              --  alter table test DROP 字段 名 ;      
              alter table terminal add COLUMN rfid varchar(1000) DEFAULT NULL ; 
              
              
               alter table terminal add COLUMN guistatus int(4) DEFAULT  0 ; 
              
              
              
              
              
              
              
              
              
              
              
              
              
 
--- 数据库创建表         
        --  把本地的sql文件整理一下  要清空数据 和注解
     
        /home/magui_app/netAllAppProject/mysqles/wxmenu.sql
        
		DROP TABLE IF EXISTS `wxmenu`;
		CREATE TABLE `wxmenu` (
			  `codes` varchar(20) NOT NULL,
			  `pcodes` varchar(20) DEFAULT NULL,
			  `name` varchar(64) DEFAULT NULL,
			  `company` varchar(64) DEFAULT NULL,
			  `areaCode` varchar(20) DEFAULT NULL,
			  `contentes` text,
			  PRIMARY KEY (`codes`)
		) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信菜单管理';
 
	   文件目录>    
	      mysql -unetapp -p dcdzintelligentstorageapp <  /home/magui_app/netAllAppProject/mysqles/wxmenu.sql
	      root@1234 
		      
		      
		      
		      
		      
		      
		      
 