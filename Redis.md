### 字符串

#### 原理

##### SDS类型

**String类型使用C语言中的SDS类型进行存储**

```c
struct sdsshdr{
    int len; //字符串长度
    int free; //剩余可用长度
    char buf[]; //字符数组
}
```

图例：

![20200813134439](images\20200813134439.png)



​		SDS遵循c字符串以空字符结尾的惯例，所以char[]数组的最后一个字符保存了空字符 "\0"，并且**这个空字符不计算在SDS的len属性里**。即：某键值设置的字符为：redis，len属性存储的长度为5个字节，但实际存储的内存空间则为6个字节



##### 内存分配策略

1. 空间预分配

   用于修改字符串增长操作。当sds的api对字符串进行修改增加时，除了分配给sds合适大小的空间，同时还会分配未使用的空间赋值给free。未使用空间free长度的分配策略如下：

   - 当sds大小，即len小于1M时，free分配的大小等于len的大小
   - 当len大于等于1M时，free分配到的大小等于1M

   

   例如：

   - 修改后len=20byte，那么free=20byte ，sds实际大小等于20+20+1=41byte (始终预留一个字节用于存储空字符)

   - 修改后len=30M，那么free=1M ，sds实际大小等于30M+30M+1byte
   



​		**通过空间预分配，可以减少连续修改字符串增长导致的内存频繁分配次数。**





2. 惰性空间释放

   用于修改字符串缩短的操作。当sds的api对字符串进行缩减操作时，程序不会立即释放缩短的空间，而是将未使用的空间长度保存在free属性中，用于下次字符增长操作时直接使用，避免了发生内存分配的情况



**通过空间预分配和惰性空间释放，避免了内存泄漏和缓冲区溢出的情况，并且降低了重新分配内存的次数。**



##### 缓冲区溢出与内存泄漏

**缓冲区溢出**：向缓冲区填充的数据位数超过了缓冲区自身的容量限制时，发生的缓冲区溢出覆盖在合法的数据。

例如：有两个字符串"Redis"与"Mongodb"紧挨在内存中存储

![15973054332312](images\15973054332312.png)

当"Redis"字符串的内容被修改为"Redis Cluster"时，假如修改之前没有给s1分配足够的空间，就会导致"Mongodb"的内容被覆盖修改，发生溢出的情况

![15973055905927](images\15973055905927.png)



**内存泄漏**：程序未能正常释放已经不再使用的内存的情况，造成内存保持占用状态，使操作系统不能将内存分配给其他的程序（进程），导致该内存不可用。



##### 二进制安全

​		需要注意的是，**sds类型中的buf数组并不直接保存字符，而是将字符转换成二进制数据存储**，这样做的目的是为了防止客户端保存的值当中存在特殊字符如空字符"\0"，如果直接存储字符，那么程序就会读取到客户端传过来的空字符并且认为这是改字符的结束标志，从而导致异常。通过转换成二进制，不仅能避免特殊字符的问题，还能让redis支持存储任意类型的内容。



### 链表

#### 原理

链表由一个或多个链表节点组成，链表节点的结构如下：

![1598252512240](\images\1598252512240.png)



保存了前后节点的指针地址和一个节点值，前后相连的节点组成双端链表。多个链表节点组成链表接口

![15982535186851](\images\15982535186851.png)

链表保存了头尾节点指针地址，使得访问头尾节点的复杂度变为O(1)

#### 用途

​		链表在redis中除了保存链表键值之外，还用于发布订阅、慢查询、监控器、和保存客户端的状态信息









### 经验总结

#### eureka健康检查redis失败

 **报错信息：**

```shell
{"description":"Remote status from Eureka server","status":"DOWN","discoveryComposite":{"description":"Remote status from Eureka server","status":"DOWN","discoveryClient":{"description":"Spring Cloud Eureka Discovery Client","status":"UP","services":["ib-gateway","fcbox_boxservice_applet_web","fcbox-emp-user","oauth2-service","electronic-file","fcbox-chain-supply","fcbox-game","transformers","consumer-share","fcbox-iom","ib-vendlocker-service","consumer-app","ib-user","es-post-applet-web","fcbox-chain-app","appmgr-web","fcbox-activity-applet","data-center","cms-web","fcbox-invoice","fcbox-chain-job","wd-chain-oauth","push-server-mng","fcbox-chain-dubbo","fcbox-chain-pigeon","fcbox-chain-pc","csc-user-service","tpi-service","device-service","auth-service","huawei-spare","ib-discovery","ib-payment-service","fcbox-rent","wechat-app-core","wechat-app-route","fcbox-sample","fcbox-erm"]},"eureka":{"description":"Remote status from Eureka server","status":"DOWN","applications":{"IB-GATEWAY":4,"FCBOX_BOXSERVICE_APPLET_WEB":6,"FCBOX-EMP-USER":2,"OAUTH2-SERVICE":2,"ELECTRONIC-FILE":2,"FCBOX-CHAIN-SUPPLY":2,"FCBOX-GAME":2,"TRANSFORMERS":2,"CONSUMER-SHARE":2,"FCBOX-IOM":2,"IB-VENDLOCKER-SERVICE":2,"CONSUMER-APP":4,"IB-USER":2,"ES-POST-APPLET-WEB":2,"FCBOX-CHAIN-APP":4,"APPMGR-WEB":6,"FCBOX-ACTIVITY-APPLET":6,"DATA-CENTER":2,"CMS-WEB":2,"FCBOX-INVOICE":4,"FCBOX-CHAIN-JOB":4,"WD-CHAIN-OAUTH":2,"PUSH-SERVER-MNG":2,"FCBOX-CHAIN-DUBBO":2,"FCBOX-CHAIN-PIGEON":2,"FCBOX-CHAIN-PC":4,"CSC-USER-SERVICE":2,"TPI-SERVICE":2,"DEVICE-SERVICE":2,"AUTH-SERVICE":2,"HUAWEI-SPARE":2,"IB-DISCOVERY":2,"IB-PAYMENT-SERVICE":2,"FCBOX-RENT":4,"WECHAT-APP-CORE":4,"WECHAT-APP-ROUTE":8,"FCBOX-SAMPLE":4,"FCBOX-ERM":2}}},"diskSpace":{"status":"UP","total":1073217536000,"free":962524704768,"threshold":10485760},"redis":{"status":"DOWN","error":"org.springframework.data.redis.ClusterStateFailureException: Could not retrieve cluster information. CLUSTER NODES returned with error.\r\n\t- 10.204.13.144:8080 failed: Hostname must not be empty or null.\r\n\t- 10.204.13.143:8080 failed: Hostname must not be empty or null.\r\n\t- 10.204.13.147:8080 failed: Hostname must not be empty or null.\r\n\t- 10.204.13.146:8080 failed: Hostname must not be empty or null.\r\n\t- 10.204.13.148:8080 failed: Hostname must not be empty or null.\r\n\t- 10.204.13.145:8080 failed: Hostname must not be empty or null."},"refreshScope":{"status":"UP"},"hystrix":{"status":"UP"}}
```

 **后果：** eureka健康检查失败，应用客户端不能正常注册到网关，导致应用程序无法提供对外服务能力。



**临时处理：**更换redis集群，重发上线解决



**问题查找：**

1. 找运维登陆redis集群服务器查看集群节点信息,得知集群中存在失效节点

   ```shell
   10.204.13.143:8080> cluster nodes
   62f7e98eec3fcc6a83616fab9c4be2fa04bfb479 10.204.13.147:8080@18080 master - 0 1598338865000 15 connected 10923-16383
   7dc9566c3d9ee65ea00619580de79b0f8feb03e8 :0@0 slave,fail,noaddr 2660c511243ea266e4569250e5abeb0f8cd0b01b 1592315909911 1592315907906 12 disconnected
   29f4a11a31d5c22b5b1f9a3b183417b88e07a076 :0@0 slave,fail,noaddr 87ad4c983d8ba44e647a72ef3154a475d75c6c11 1592315919940 1592315917000 13 disconnected
   4d5b8c43e13e0dfa073472ed62d857287eea6fb4 :0@0 slave,fail,noaddr ff15b2c6075bf1cdbf133a264fc98157d0e9b5cf 1592315915629 1592315913924 11 disconnected
   2660c511243ea266e4569250e5abeb0f8cd0b01b 10.204.13.144:8080@18080 slave 62f7e98eec3fcc6a83616fab9c4be2fa04bfb479 0 1598338865538 15 connected
   bde7bb31d2292eaa53c8c6b36572a55f3bc84464 :0@0 slave,fail,noaddr 87ad4c983d8ba44e647a72ef3154a475d75c6c11 1592315899788 1592315896000 13 disconnected
   e7665411f52bf94f26b2037989e8126f6894e675 10.204.13.148:8080@18080 master - 0 1598338865000 16 connected 0-5460
   94a88ec0f52a8e9eb5f702adc52e6363b823e41f 10.204.13.146:8080@18080 master - 0 1598338862000 14 connected 5461-10922
   d99053b8739ec719615473c22c0b5b1e45a9b0ee :0@0 slave,fail,noaddr 2660c511243ea266e4569250e5abeb0f8cd0b01b 1592315903893 1592315900000 12 disconnected
   ff15b2c6075bf1cdbf133a264fc98157d0e9b5cf 10.204.13.143:8080@18080 myself,slave 94a88ec0f52a8e9eb5f702adc52e6363b823e41f 0 1598338864000 11 connected
   87ad4c983d8ba44e647a72ef3154a475d75c6c11 10.204.13.145:8080@18080 slave e7665411f52bf94f26b2037989e8126f6894e675 0 1598338866540 16 connected
   41b8ea6f4e826878c49e161216c8fcfdc8ec9f6c :0@0 slave,fail,noaddr ff15b2c6075bf1cdbf133a264fc98157d0e9b5cf 1592315894778 1592315890872 11 disconnected
   10.204.13.143:8080> 
   ```

   ​		集群中之所以存在失效节点是因为前段时间运维更换了新的redis集群，但为了方便出问题后切换回旧集群，没有将失效的旧节点下掉，导致昨晚上线的应用连接到了旧集群节点上。

   但我们再去查看替换成功那套redis集群中发现，过了健康检查的redis集群中同样存在失效节点，所以就出现一个问题：**为什么同样都存在失效节点的集群，一个连接失败，一个却连接成功？**

   

