# Redis

## 简述



### 介绍

> Redis（Remote Dictionary Server )，即远程字典服务

是一个开源的使用ANSI [C语言](https://baike.baidu.com/item/C语言)编写、支持网络、可基于内存亦可持久化的日志型、Key-Value[数据库](https://baike.baidu.com/item/数据库/103728)，并提供多种语言的API。从2010年3月15日起，Redis的开发工作由VMware主持。从2013年5月开始，Redis的开发由Pivotal赞助。



### 性能

官方测试：读的速度是110000次/s,写的速度是81000次/s 。



### 特性

1. 支持多种类型
2. 支持发布订阅模式
3. 支持集群部署
4. 支持事务
5. ......



### 安装

> 获取方式

1. 官网：https://redis.io/
2. 源码：https://github.com/redis/redis



> **Windows安装**

1. 下载地址 https://github.com/MicrosoftArchive/redis/tags

2. 解压目录

![image-20200801215034805](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20200801215034805.png)

![image-20200801215128008](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20200801215128008.png)



3. 客户端连接

![image-20200801220800608](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20200801220800608.png)



*windows版本的redis已经停更很久，不是redis最新版本*



> **Linux安装**

1. 解压安装

````shell
$ wget http://download.redis.io/releases/redis-6.0.6.tar.gz
$ tar xzf redis-6.0.6.tar.gz
$ cd redis-6.0.6
$ make
````

2. yum命令安装

直接`yum install redis `安装的redis 不是最新版本，需要安装Remi的软件源，官网地址：http://rpms.famillecollet.com/

```shell
yum install -y http://rpms.famillecollet.com/enterprise/remi-release-7.rpm
yum --enablerepo=remi install redis
```

3. 检查版本

``` shell
redis-cli --version
```

![image-20200801230625954](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20200801230625954.png)

4. 启动服务

````shell
systemctl start redis
````



### 性能测试

1. redis-benchmark 测试工具参数列表

|      |           |                                            |           |
| :--- | :-------- | :----------------------------------------- | :-------- |
| 序号 | 选项      | 描述                                       | 默认值    |
| 1    | **-h**    | 指定服务器主机名                           | 127.0.0.1 |
| 2    | **-p**    | 指定服务器端口                             | 6379      |
| 3    | **-s**    | 指定服务器 socket                          |           |
| 4    | **-c**    | 指定并发连接数                             | 50        |
| 5    | **-n**    | 指定请求数                                 | 10000     |
| 6    | **-d**    | 以字节的形式指定 SET/GET 值的数据大小      | 2         |
| 7    | **-k**    | 1=keep alive 0=reconnect                   | 1         |
| 8    | **-r**    | SET/GET/INCR 使用随机 key, SADD 使用随机值 |           |
| 9    | **-P**    | 通过管道传输 <numreq> 请求                 | 1         |
| 10   | **-q**    | 强制退出 redis。仅显示 query/sec 值        |           |
| 11   | **--csv** | 以 CSV 格式输出                            |           |
| 12   | **-l**    | 生成循环，永久执行测试                     |           |
| 13   | **-t**    | 仅运行以逗号分隔的测试命令列表。           |           |
| 14   | **-I**    | Idle 模式。仅打开 N 个 idle 连接并等待。   |           |

2. 测试案例

```shell
redis-benchmark -h 127.0.0.1 -p 6379 -c 50 -n 10000
```

![image-20200801233449337](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20200801233449337.png)



## 配置















# 协议

