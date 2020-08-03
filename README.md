# Redis

## 简述



### 介绍

> Redis（Remote Dictionary Server )，即远程字典服务

是一个开源的使用ANSI [C语言](https://baike.baidu.com/item/C语言)编写、支持网络、可基于内存亦可持久化的日志型、Key-Value[数据库](https://baike.baidu.com/item/数据库/103728)，并提供多种语言的API。从2010年3月15日起，Redis的开发工作由VMware主持。从2013年5月开始，Redis的开发由Pivotal赞助。



**redis是单线程处理的。因为为了避免多线程导致的cpu上下文切换出现的耗时，直接使用单线程操作效率更高。**



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



## 基本命令

### 

可以在官网查询命令详解：http://www.redis.cn/commands.html

![命令文档](\images\命令文档.PNG)





### 测试联通

```bash
ping
```



### 登陆

```
redis-cli -h 地址 -p 端口 -a 密码
```



### 切换数据库

redis默认有16个数据库，下标从0开始

```
select index
```

**示例：**

```shell
select 1 #切换到数据库 1
```



### 查看DB大小

```shell
dbsize
```



### 查看所有的key

```
keys *
```



### 清空当前库

```
flushdb
```



### 清空所有库

```
flushall
```



### 查看key是否存在

```base
exists key # 1表示存在 0表示不存在
```



### 设置key过期时间

```base
expire key seconds  #单位是秒
```



### 获取key过期时间

```base
ttl key #返回剩余几秒，-2表示已过期
```



### 查看key的数据类型

```bash
type key
```



### 删除key

```bash
del key
```







## 数据类型

### String

#### 命令

##### 追加key值内容

```base
append key value #返回最终value值的总长度
```



##### 获取value值长度

```base
strlen key #返回value值长度
```



##### 原子加1

```base
incr key #返回计算完后的值
```



##### 原子减1

```base
decr key #返回计算完后的值
```



##### 指定原子值累加

```bash
incrby key value #value为指定的累加值
```



##### 指定原子值递减

```bash
desrby key value #value为指定的递减值
```



##### 分割字符串

```bash
getrange key start end #下标从0开始 start为开始下标  end为结束下标 -1表示全部
```



##### 从指定下标开始覆盖字符串

```bash
setrange key offset value # offset为指定下标开始 value为覆盖值
```



##### 设置值并且设置过期时间

```base
setex key seconds value # seconds为过期时间，单位是秒
```



##### 不存在值设置

```base
setnx key value #如果不存在值则设置，存在值就不设置
```

可用于分布式锁当中



##### 批量设值

```base
mset key1 value1 key2 value2 .... 
```



##### 批量获取值

```bash
mget key1 key2 ....
```



##### 不存在值批量设置

```bash
msetnx key1 value1 key2 value2 .... #此命令为原子性操作，其中一个失败则全部失败
```



##### 先获取再设置

```bash
getset key value # 返回的是设置前的值
```



## List



### 命令

大多数list命令都是**l** 开头的



#### 从头部设置值

```bash
lpush key value 
```



#### 从尾部设置值

```bash
rpush key value
```



#### 获取指定范围值

```bash
lrange key start end #下标从0开始 start为开始下标  end为结束下标 -1表示全部
```



#### 从头部获取并移除值

```bash
lpop key 
```



#### 从尾部获取并移除值

```bash
rpop key
```



#### 获取指定下标的值

```bahs
lindex key index # index为下标，下标是从0开始
```



#### 获取list中的个数

```bash
llend key
```



#### 移除指定list值

```bash
lrem key count value # count 为需要移除几个和value相等的值,存在多个value重复的值，则会移除count个value值
```



#### 截取指定范围的list值

```bash
ltrim key start end #截取从start到end之间的值为list的最终值
```

**案例：**

```bash
127.0.0.1:6379> lrange list 0 -1
1) "a3"
2) "a2"
3) "a1"
4) "one"
5) "three"
127.0.0.1:6379> ltrim list 1 2
OK
127.0.0.1:6379> lrange list 0 -1
1) "a2"
2) "a1"
```



#### 替换指定下标的值

```bash
lset key index value #下标值不存在则报错
```



#### 在值的相对位置插入新值

```bash
linsert key before|after value newvalue # before表示在value值之前插入newvalue ，after表示在value之后插入newvalue值
```





## Set

无序集合，并且set中的值是不能重复的



### 命令

set的命令大多数都是**s**开头



#### 批量设置

```bash
sadd key value1 value2 value3.... #不是原子性，允许部分成功，符合条件的可插入
```



#### 获取值

```bash
smembers key
```



#### 判断指定值是否在set中

```bash
sismember key value # value如果存在set中则返回1 ，不存在则返回0
```



#### 获取set中的个数

```bash
scard key
```



#### 移除指定元素

```bash
srem key value
```



#### 随即获取若干个元素

```bash
srandmember key count #随即获取count个元素值
```



#### 随机移除并返回一个元素

```bash
spop key 
```



#### 集合的差集

```bash
sdiff key1 key2....
```



#### 集合的交集

```bash
sinter key1 key2....
```



#### 集合的并集

```bash
sunion key1 key2....
```





## Hash

hash类型是map值，是由key-value键值对组成的集合

### 命令

hash的命令大多数都是**h**开头



#### 设置值

```bash
hset key field value 
```



#### 获取值

```bash
hget key field
```



#### 批量设置

```bash
hmset key field1 value1 field2 value2...  
```



#### 批量获取

```bash
hmget key field1 field2 field3....
```



#### 获取全部值

```bash
hgetall key
```

**示例：**

```bash
127.0.0.1:6379> HGETALL map
1) "key1"
2) "value1"
```



#### 删除指定的元素

```bash
hdel key field
```



#### 获取集合个数

```bash
hlen key
```



#### 判断元素是否存在

```bash
hexists key field #存在返回1，不存在返回0
```



#### 获取集合中的全部field值

```bash
hkeys key
```



#### 获取集合中的value值

```bash
hvals key
```





## Zset

zset叫做有序集合，而set是无序的，zset怎么做到有序的呢？就是zset的每一个成员都有一个分数与之对应，并且分数是可以重复的。



### 命令





#### 设置值

```bash
zadd key score value # score表示分数，用来做排序
```



#### 获取范围值

````bash
zrange key start end
````



#### 正序获取集合

```bash
zrangebyscore key start end #可以使用-inf表示负无穷 +inf表示正无穷
```

**示例1:**

```bash
127.0.0.1:6379> ZRANGEBYSCORE key -inf +inf
1) "one"
2) "kkk"
3) "tow"
```

**示例2:**

```bash
127.0.0.1:6379> ZRANGEBYSCORE key -inf +inf withscores  # 带上withscores后可以显示分数
1) "one"
2) "1"
3) "kkk"
4) "32"
5) "tow"
6) "43"
```



#### 移除元素

```bash
zrem key value 
```



#### 查看元素个数

```bash
zcard key
```



#### 倒序获取集合

```bash
zrevrange key start end 
```





## geospatial

存储经纬度数据



### 命令



#### 设置值

```bash
geoadd key lon lat member 
```

**示例**

```bash
127.0.0.1:6379> geoadd city 113.88 22.55 shenzhen 108.93 34.23 xian
(integer) 2
```



#### 获取经纬度

```bash
geopos key member
```

**示例**

```bash
127.0.0.1:6379> GEOPOS city xian
1) 1) "108.92999857664108"
   2) "34.230001219268523"
```



#### 获取两个空间之间的距离

```bash
geodist key member1 member2 [m|km..] #最后面设置单位，默认是m
```

**示例**

```bash
127.0.0.1:6379> GEODIST city beijing xian km
"914.4648"
```



#### 获取指定地点范围内的集合

```bash
georadius key lon lat radius [m|km...] #lon和lat为指定地点的经纬度,然后在radius范围内进行查询
```

**示例**1

```bash
127.0.0.1:6379> GEORADIUS city 106.51 29.40 1000 km
1) "xian"
```

**示例2**

```bash
127.0.0.1:6379> GEORADIUS city 106.54 29.40 10000 km count 2 # count设置最多返回几个
1) "xian"
2) "shenzhen"
```

**示例3**

```bash
127.0.0.1:6379> GEORADIUS city 106.54 29.40 10000 km withdist  #withdist在返回位置元素的同时， 将位置元素与中心之间的距离也一并返回,单位一致
1) 1) "xian"
   2) "582.7248"
2) 1) "shenzhen"
   2) "1057.3114"
3) 1) "beijing"
   2) "1473.9461"
```

**示例4**

```bash
127.0.0.1:6379> GEORADIUS city 106.54 29.40 10000 km withcoord #withcoord 将位置元素的经度和维度也一并返回
1) 1) "xian"
   2) 1) "108.92999857664108"
      2) "34.230001219268523"
2) 1) "shenzhen"
   2) 1) "113.87999922037125"
      2) "22.55000104759231"
3) 1) "beijing"
   2) 1) "116.38999968767166"
      2) "39.909999566644508"
```





#### 获取指定元素范围内的集合

```bash
georadiusbymember key radius [m|km...]
```

**示例1**

```bash
127.0.0.1:6379> GEORADIUSBYMEMBER city beijing 1000 km withdist #加withdist可以返回对应的距离
1) 1) "beijing"
   2) "0.0000"
2) 1) "xian"
   2) "914.4648"
```



> geo底层其实是set，所以也可以使用set相关的命令来操作geo元素

**示例**

```bash
127.0.0.1:6379> zrange city 0 -1
1) "xian"
2) "shenzhen"
3) "beijing"
```





# 协议





# java

## 基础

### <span id = "hashMap"> 哈希链表</span>





# 操作系统



# 算法

## 数组与字符串

### 两数之和

给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。



**示例:**

```
给定 nums = [2, 7, 11, 15], target = 9

因为 nums[0] + nums[1] = 2 + 7 = 9
所以返回 [0, 1]
```



**解法一：暴力轮询**

```java
    public int[] twoSum(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if ((nums[i] + nums[j]) == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[2];
    }
```



**解法二：哈希表**

```java
    public int[] twoSum(int[] nums, int target) {
       Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.get(nums[i]) != null) {
                return new int[]{i, map.get(nums[i])};
            }
            map.put(target - nums[i], i);
        }
        return new int[2]; 
    }
```

使用哈希表解法性能会比暴力轮询高。原因查看：[哈希链表](#hashMap)



### 寻找两个正序数组的中位数

给定两个大小为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。请你找出这两个正序数组的中位数，并且要求算法的时间复杂度为 O(log(m + n))。你可以假设 nums1 和 nums2 不会同时为空。

 

**示例 1:**

```
nums1 = [1, 3]
nums2 = [2]

则中位数是 2.0
```



**示例 2:**

```
nums1 = [1, 2]
nums2 = [3, 4]

则中位数是 (2 + 3)/2 = 2.5
```



**解读：**

本题的关键点在于：**如何将两个有序数组合并后排序？** 

![202008021159](E:\github\DailyCode\images\202008021159.jpg)





**解法：**

```java
public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        if (m == 0) {
            int index = n / 2;
            if (n % 2 == 0) {
                return (nums2[index - 1] + nums2[index]) / 2.0;
            } else {
                return nums2[index];
            }
        }

        if (n == 0) {
            int index = m / 2;
            if (m % 2 == 0) {
                return (nums1[index - 1] + nums1[index]) / 2.0;
            } else {
                return nums1[index] ;
            }
        }

        //排序两个有序数组
        int count = m + n;
        int i = 0,j = 0,k = 0;
        int[] nums3 = new int[count];
        while (i < m && j < n) {
            if (nums1[i] < nums2[j]) {
                nums3[k++] = nums1[i++];
            } else {
                nums3[k++] = nums2[j++];
            }
        }
        //将剩余的数组nums1倒入nums3
        while (i < m) {
            nums3[k++] = nums1[i++];
        }
        //将剩余的数组nums2倒入nums3
        while (j < n) {
            nums3[k++] = nums2[j++];
        }

        //求数组3的中位数
        int index = count / 2;
        if (count % 2 == 0) {
            return (nums3[index] + nums3[index - 1]) / 2.0;
        } else {
            return nums3[index] ;
        }
    }

```



### 最长回文字符串

给定一个字符串 `s`，找到 `s` 中最长的回文子串。你可以假设 `s` 的最大长度为 1000。

**示例 1：**

```
输入: "babad"
输出: "bab"
注意: "aba" 也是一个有效答案。
```

**示例 2：**

```
输入: "cbbd"
输出: "bb"
```



**解读**：

回文字符串是指倒叙正叙读取都相等的字符串。所以存在两种情况：AAA和ABA 类型的回文字符串。难点在于**如何遍历回文字符串？**

![遍历回文字符串](E:\github\DailyCode\images\遍历回文字符串.png)

**解法：**

```java
 public static String longestPalindrome(String s) {
        int slen = s.length();
        if (slen < 2) {
            return s;
        }

        int[] index = new int[2];
        char[] a = s.toCharArray();
        for (int i = 0; i < slen; i++) {
            //遍历AAA的情况
            int left = i;
            int right = i;
            while (right < slen - 1 && a[i] == a[right + 1]) {
                right++;
            }
            //遍历ABA的情况
            while (left > 0 && right < slen- 1 && a[left - 1] == a[right + 1]) {
                left--;
                right++;
            }

            if (right - left > index[1] - index[0]) {
                index[0] = left;
                index[1] = right;
            }
        }
        return s.substring(index[0], index[1] + 1);
    }
```



