# CloudNoteAfter

### 项目介绍

本项目为笔记系统，提供笔记相关的基本功能，如笔记编辑，笔记本分类，笔记分享，共享画板等功能。（后端）

### 相关知识

项目采用前后端分离的方式进行开发。

后端：使用springboot框架进行开发，mysql 为数据库软件，redis 作为 缓存中间件。

前端：使用vue2框架进行开发，搭配element-ui组件库 和 axios请求框架。

### 项目亮点

1、对项目的数据库表结构进行设计。在回收站的设计中仅用单个字段便实现了回收站的功能，节省了大量空间。

2、使用字典树数据结构生成敏感词过滤树，对系统分享和共享的笔记数据进行信息过滤。

3、使用SpringTask定时任务，在每天早上8点定时发送邮件提醒当天有行程计划的用户。

4、使用Redis对验证码和共享笔记数据进行缓存，减少对数据库的访问压力。

5、使用栈数据结构，互相配合的实现了画板的撤销（回退）和恢复（还原）功能。

6、使用WebSocket实现多用户协同编辑（画线）功能，共同编辑，实时同步内容，统计当前在线人数。

7、编写简易消息队列，对非主业务流程进行异步化处理。

8、使用Docker镜像对项目所需要依赖的环境进行部署。

**前端项目链接**

https://gitee.com/kenan_a/cloud-note-front

