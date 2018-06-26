-- linux 基础命令

       create database testes; 
       drop database testes;
       
       --进入数据库
           mysql -u用户名 -p密码;
        
        
       --导入命令 -d 不包含数据
           mysqldump -u用户名 -p  -d  数据库 > 数据库.sql;
           密码
           
       --导入数据库命令
	      文件目录> mysql -u用户名 -p 数据库 < 数据库.sql
          mysql> source 数据库.sql;
           
       -- 0.0查询表结构
          desc  test;
       -- 0.1创建表
            DROP TABLE IF EXISTS `test`;
			CREATE TABLE `test` (
			  `id` int(11) NOT NULL AUTO_INCREMENT,
			  `name` varchar(1000) DEFAULT NULL 
			  PRIMARY KEY (`id`)
			) ENGINE=InnoDB DEFAULT CHARSET=utf8;
          
          
          
       -- 1.0添加表字段
           alter table test add COLUMN test1 VARCHAR(20) DEFAULT NULL; //增加一个字段，默认为空  
           alter table test add COLUMN test2 VARCHAR(20) NOT NULL;  //增加一个字段，默认不能为空   


       -- 1.1修改某个表的字段名称及指定为空或非空
           alter table 表名称 change 字段原名称 字段新名称 字段类型 DEFAULT NULL  ;
           alter table 表名称 change 字段原名称 字段新名称 字段类型 NOT NULL ;
           
       -- 1.2删除某一字段
           alter table mytable DROP 字段 名 ;      
           
           
           
           
           
           
           
           
           
           
           
           
           
           
           
           
           
           
           
           
           
           