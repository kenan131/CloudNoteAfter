/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 8.0.24 : Database - cloudnote
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`cloudnote` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `cloudnote`;

/*Table structure for table `book` */

DROP TABLE IF EXISTS `book`;

CREATE TABLE `book` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `text` text COLLATE utf8mb4_general_ci,
  `del` int DEFAULT NULL,
  `share` int DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  `noteId` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `noteId` (`noteId`),
  CONSTRAINT `book_ibfk_1` FOREIGN KEY (`noteId`) REFERENCES `notebook` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `book` */

insert  into `book`(`id`,`name`,`text`,`del`,`share`,`createTime`,`updateTime`,`noteId`) values (1,'英语1','# 一级标题\n\n**粗体**\n\n*斜体*\n\n~~删除线~~\n\n表格\n|column1|column2|column3|\n|-|-|-|\n|content1|content2|content3|\n\n分割线\n------------------------------------\n## 二级标题\n\n1. 列表1\n2. 列表2\n3. 列表3\n### 三级标题\n\n\n#### 四级标题',-1,0,'2023-03-04 00:00:00','2023-03-04 00:00:00',5),(2,'英语2','![image.png](https://cloudnote-1311682597.cos.ap-nanjing.myqcloud.com/image.png){{{width=\"auto\" height=\"auto\"}}}',-1,0,'2023-03-07 00:00:00','2023-03-07 00:00:00',5),(3,'数学1','1、摘要尽量简洁，中文摘要和英文摘要需要相互对应。\n2、图片需要标注图几（图3.1用户登录图），图片的标注需要有序，图片尺寸需要小于页面的百分之50，图片中的文字尽量清晰。图片位置尽量居中。\n3、“如图所示”类文字不应另起一行，紧跟文字段落即可。\n4、一级标题需另起一页，占页面顶部。\n5、小节与小节之间尽量不要有太多空白，需要贴合。\n6、段落中不能出现一个字单独占一行。\n7、用例分析，每张用例图需要用一段文字详细介绍。\n8、业务流程分析，需要画数据流图。\n9、第三章总体设计，需要有系统整体功能图。\n10、功能测试，每张功能模块测试表，最少需要写3个功能测试。\n11、论文中不需要写太多代码。',-1,0,'2023-03-08 00:00:00','2023-03-08 00:00:00',3),(4,'英语3','12\nffff2\n# 2\n# 29\n6\n### 6\n6\n61\n1123',-1,0,'2023-03-08 00:00:00','2023-03-08 00:00:00',5),(5,'英语4','铭感词测试！\n赌博，赌大博。\n你是我的神！。嫖娼，嫖 娼。\n分析！\n',-1,0,'2023-03-08 00:00:00','2023-03-08 00:00:00',5),(6,'测试数据2','                           标题！\n你是地方 ！\n``` java\nint main(){\n\n}\n```\n## df \n## df132\n\n 2\n##  第一章！\n### 2.3\n123\n\n## 2.1\n1\n## 第二章！\n``` java\nint a ;\n```\n',-1,2,'2023-03-08 00:00:00','2023-03-08 00:00:00',5),(7,'5556','测试笔记数据！',-1,0,'2023-03-08 00:00:00','2023-03-08 00:00:00',9),(8,'笔记测试','169854',-1,0,'2023-03-12 00:00:00','2023-03-12 00:00:00',10),(10,'测试','> **91134658开户行考虑结婚dsfesfsd\nsdf\n### dsf \n### sdfsd \nfdsfd\n``` java\nint a;\n\n```',1,0,'2023-03-15 00:00:00','2023-03-15 00:00:00',10),(12,'456测试','## 大师傅似的\n\n**上传文件，测试数据！**\n\n## 士大夫但是\n\n测试数据！\n\n##haha',-1,0,'2023-03-16 00:00:00','2023-03-16 00:00:00',9),(13,'测试笔记111','asdasdasdasdadsadsasddsa',-1,0,'2023-03-17 00:00:00','2023-03-17 00:00:00',12),(14,'Zookeeper安装','## 1.1 下载安装\n\n**1、环境准备**\n\nZooKeeper服务器是用Java创建的，它运行在JVM之上。需要安装JDK 7或更高版本。\n\n**2、上传**\n\n将下载的ZooKeeper放到/opt/ZooKeeper目录下\n\n```shell\n\n#上传zookeeper alt+p\nput f:/setup/apache-zookeeper-3.5.6-bin.tar.gz\n#打开 opt目录\ncd /opt\n#创建zooKeeper目录\nmkdir  zooKeeper\n#将zookeeper安装包移动到 /opt/zooKeeper\nmv apache-zookeeper-3.5.6-bin.tar.gz /opt/zookeeper/\n```\n\n**3、解压**\n\n将tar包解压到/opt/zookeeper目录下\n\n```shell\ntar -zxvf apache-ZooKeeper-3.5.6-bin.tar.gz \n```\n\n## 1.2 配置启动\n\n**1、配置zoo.cfg**\n\n进入到conf目录拷贝一个zoo_sample.cfg并完成配置\n\n```shell\n#进入到conf目录\ncd /opt/zooKeeper/apache-zooKeeper-3.5.6-bin/conf/\n#拷贝\ncp  zoo_sample.cfg  zoo.cfg\n```\n\n\n\n修改zoo.cfg\n\n```shell\n#打开目录\ncd /opt/zooKeeper/\n#创建zooKeeper存储目录\nmkdir  zkdata\n#修改zoo.cfg\nvim /opt/zooKeeper/apache-zooKeeper-3.5.6-bin/conf/zoo.cfg\n```\n\n![1577548250377](images\\1577548250377.png)\n\n修改存储目录：dataDir=/opt/zookeeper/zkdata\n\n**2、启动ZooKeeper**\n\n```shell\ncd /opt/zooKeeper/apache-zooKeeper-3.5.6-bin/bin/\n#启动\n ./zkServer.sh  start\n```\n\n![1577548052037](images\\1577548052037.png)\n\n看到上图表示ZooKeeper成功启动\n\n**3、查看ZooKeeper状态**\n\n```shell\n./zkServer.sh status\n```\n\nzookeeper启动成功。standalone代表zk没有搭建集群，现在是单节点\n\n![1577548175232](images\\1577548175232.png)\n\nzookeeper没有启动\n\n![1577548112773](images\\1577548112773.png)``` language\n```',-1,2,'2023-03-17 00:00:00','2023-03-17 00:00:00',9),(15,'数学2','',-1,0,'2023-03-18 00:00:00','2023-03-18 00:00:00',3),(16,'README',' 3、Java面试必知必会\n\n## 介绍\n\n本仓库主要讲解Java在面试中的高频考点，分别包含Java基础、Java集合、Java多线程与并发编程、Java虚拟机、数据库、计算机基础、框架和中间件等。\n# df\n\n## 3.1 Java基础知识\n\n    1.Java基础知识在面试中的高频考点\n    2.Java集合在面试中的高频考点\n    3.Java多线程与并发编程在面试中的高频考点\n    4.Java虚拟机在面试中的高频考点\n\n## 3.2 数据库知识\n\n### 3.2.1 MySQL高频面试问题\n\n    1. MySQL索引高频面试问题\n    2. MySQL锁的高频面试问题\n    3. MySQL事务的高频面试问题\n\n### 3.2.2 Redis的高频面试问题\n\n## 3.3 设计模式相关\n\n## 3.4 中间件等相关\n\n    1.消息中间件高频面试问题\n    2.Netty高频面试问题\n\n## 3.5 Spring相关框架\n\n    1.Spring高频面试问题\n    2.SpringBoot的高频面试问题\n\n## 3.6 计算机基础知识\n\n    1.计算机网络高频面试问题\n    2.操作系统高频面试问题\n## 特技\n\n1. 使用 Readme\\_XXX.md 来支持不同的语言，例如 Readme\\_en.md, Readme\\_zh.md\n2. Gitee 官方博客 [blog.gitee.com](https://blog.gitee.com)\n3. 你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解 Gitee 上的优秀开源项目\n4. [GVP](https://gitee.com/gvp) 全称是 Gitee 最有价值开源项目，是综合评定出的优秀开源项目\n5. Gitee 官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)\n6. Gitee 封面人物是一档用来展示 Gitee 会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)\n',-1,2,'2023-03-19 00:00:00','2023-03-19 00:00:00',9),(19,'测试笔记','测试笔记数据！',-1,0,'2023-03-28 23:21:11','2023-03-28 23:21:11',13),(23,'README',' 3、Java面试必知必会\n\n## 介绍\n\n本仓库主要讲解Java在面试中的高频考点，分别包含Java基础、Java集合、Java多线程与并发编程、Java虚拟机、数据库、计算机基础、框架和中间件等。\n# df\n\n## 3.1 Java基础知识\n\n    1.Java基础知识在面试中的高频考点\n    2.Java集合在面试中的高频考点\n    3.Java多线程与并发编程在面试中的高频考点\n    4.Java虚拟机在面试中的高频考点\n\n## 3.2 数据库知识\n\n### 3.2.1 MySQL高频面试问题\n\n    1. MySQL索引高频面试问题\n    2. MySQL锁的高频面试问题\n    3. MySQL事务的高频面试问题\n\n### 3.2.2 Redis的高频面试问题\n\n## 3.3 设计模式相关\n\n## 3.4 中间件等相关\n\n    1.消息中间件高频面试问题\n    2.Netty高频面试问题\n\n## 3.5 Spring相关框架\n\n    1.Spring高频面试问题\n    2.SpringBoot的高频面试问题\n\n## 3.6 计算机基础知识\n\n    1.计算机网络高频面试问题\n    2.操作系统高频面试问题\n## 特技\n\n1. 使用 Readme\\_XXX.md 来支持不同的语言，例如 Readme\\_en.md, Readme\\_zh.md\n2. Gitee 官方博客 [blog.gitee.com](https://blog.gitee.com)\n3. 你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解 Gitee 上的优秀开源项目\n4. [GVP](https://gitee.com/gvp) 全称是 Gitee 最有价值开源项目，是综合评定出的优秀开源项目\n5. Gitee 官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)\n6. Gitee 封面人物是一档用来展示 Gitee 会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)\n',-1,0,'2023-04-13 14:54:48','2023-04-13 14:54:48',13),(24,'Zookeeper安装','## 1.1 下载安装\n\n**1、环境准备**\n\nZooKeeper服务器是用Java创建的，它运行在JVM之上。需要安装JDK 7或更高版本。\n\n**2、上传**\n\n将下载的ZooKeeper放到/opt/ZooKeeper目录下\n\n```shell\n\n#上传zookeeper alt+p\nput f:/setup/apache-zookeeper-3.5.6-bin.tar.gz\n#打开 opt目录\ncd /opt\n#创建zooKeeper目录\nmkdir  zooKeeper\n#将zookeeper安装包移动到 /opt/zooKeeper\nmv apache-zookeeper-3.5.6-bin.tar.gz /opt/zookeeper/\n```\n\n**3、解压**\n\n将tar包解压到/opt/zookeeper目录下\n\n```shell\ntar -zxvf apache-ZooKeeper-3.5.6-bin.tar.gz \n```\n\n## 1.2 配置启动\n\n**1、配置zoo.cfg**\n\n进入到conf目录拷贝一个zoo_sample.cfg并完成配置\n\n```shell\n#进入到conf目录\ncd /opt/zooKeeper/apache-zooKeeper-3.5.6-bin/conf/\n#拷贝\ncp  zoo_sample.cfg  zoo.cfg\n```\n\n\n\n修改zoo.cfg\n\n```shell\n#打开目录\ncd /opt/zooKeeper/\n#创建zooKeeper存储目录\nmkdir  zkdata\n#修改zoo.cfg\nvim /opt/zooKeeper/apache-zooKeeper-3.5.6-bin/conf/zoo.cfg\n```\n\n![1577548250377](images\\1577548250377.png)\n\n修改存储目录：dataDir=/opt/zookeeper/zkdata\n\n**2、启动ZooKeeper**\n\n```shell\ncd /opt/zooKeeper/apache-zooKeeper-3.5.6-bin/bin/\n#启动\n ./zkServer.sh  start\n```\n\n![1577548052037](images\\1577548052037.png)\n\n看到上图表示ZooKeeper成功启动\n\n**3、查看ZooKeeper状态**\n\n```shell\n./zkServer.sh status\n```\n\nzookeeper启动成功。standalone代表zk没有搭建集群，现在是单节点\n\n![1577548175232](images\\1577548175232.png)\n\nzookeeper没有启动\n\n![1577548112773](images\\1577548112773.png)``` language\n```',-1,0,'2023-04-13 14:54:58','2023-04-13 14:54:58',13);

/*Table structure for table `notebook` */

DROP TABLE IF EXISTS `notebook`;

CREATE TABLE `notebook` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `userId` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`),
  CONSTRAINT `notebook_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `notebook` */

insert  into `notebook`(`id`,`name`,`userId`) values (3,'数学笔记本',1),(5,'英语笔记本',1),(9,'历史笔记本',1),(10,'政治笔记本',1),(12,'笔记本1',2),(13,'测试笔记！',4),(15,'ddd',7);

/*Table structure for table `notice` */

DROP TABLE IF EXISTS `notice`;

CREATE TABLE `notice` (
  `id` int NOT NULL AUTO_INCREMENT,
  `reader` int DEFAULT NULL,
  `message` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `userId` int DEFAULT NULL,
  `sendTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `notice_fk1` (`userId`),
  CONSTRAINT `notice_fk1` FOREIGN KEY (`userId`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `notice` */

insert  into `notice`(`id`,`reader`,`message`,`userId`,`sendTime`) values (1,1,'测试通知数据1',1,'2023-02-01 09:44:12'),(2,1,'测试数据2',1,'2023-02-01 09:44:12'),(3,1,'恭喜你，共享的笔记已经通过审核了！',2,'2023-02-01 09:44:12'),(4,1,'恭喜你，共享的笔记已经通过审核了！',1,'2023-02-01 09:44:12'),(5,1,'恭喜你，共享的笔记已经通过审核了！',1,'2023-02-01 09:44:12'),(6,1,'123',1,'2023-02-01 09:44:12'),(7,1,'笔记(456测试)已经通过审核了！',1,'2023-03-23 22:32:31'),(8,1,'笔记(3.16)已经通过审核了！',1,'2023-03-23 22:33:10'),(9,1,'测试不通过！',1,'2023-03-25 11:44:21'),(10,1,'笔记(README)共享审核不同了！(有敏感词汇)',1,'2023-03-25 11:49:13'),(11,1,'笔记(README)已经通过审核了！',1,'2023-03-26 20:35:42'),(12,1,'笔记(笔记测试)已经通过审核了！',1,'2023-04-09 23:58:46');

/*Table structure for table `schedule` */

DROP TABLE IF EXISTS `schedule`;

CREATE TABLE `schedule` (
  `id` int NOT NULL AUTO_INCREMENT,
  `plan` varchar(30) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `finishTime` datetime DEFAULT NULL,
  `status` int DEFAULT NULL,
  `userId` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `schedule_fk` (`userId`),
  CONSTRAINT `schedule_fk` FOREIGN KEY (`userId`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `schedule` */

insert  into `schedule`(`id`,`plan`,`createTime`,`finishTime`,`status`,`userId`) values (17,'123','2023-04-03 09:29:35','2023-04-11 09:12:30',1,7),(36,'计划安排1','2023-04-09 15:05:37','2023-04-10 03:05:34',1,1),(37,'测试数据','2023-04-13 15:04:44','2023-04-22 19:42:00',0,1);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `account` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `password` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `email` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `salt` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `is_admin` int DEFAULT NULL,
  `active` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `account` (`account`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `user` */

insert  into `user`(`id`,`account`,`password`,`email`,`salt`,`is_admin`,`active`) values (1,'admin','84a5bc431010168a068a63f0660cd0d4','1020195983@qq.com','4a5a0',1,1),(2,'admin1','84a5bc431010168a068a63f0660cd0d4','10201959813@qq.com','4a5a0',0,1),(3,'sdfdsf','c2de69597953e7ab0fca7d6d25d37ac8','sdf@qq.cm','a32ac',0,0),(4,'admin2','fddab308b93891c5461f49beba385e66','1601358650@qq.com','a3fd0',0,1),(5,'ddd123','a9878c604d2cf2bc8e46e5a98056214a','259174@qq.com','65173',0,0),(7,'ddd1234','f53676b53350b34a63ae9d075ac5e3e0','2591749829@qq.com','f5dfc',0,1),(8,'docmy','8364df073e60052121c125e686c3fd51','942794019@qq.com','2d33d',0,1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
