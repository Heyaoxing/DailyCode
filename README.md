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





# java

## 基础

### <span id = "hashMap"> 哈希链表</span>





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

```tex
nums1 = [1, 3]
nums2 = [2]

则中位数是 2.0
```



**示例 2:**

```tex
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

```tex
输入: "babad"
输出: "bab"
注意: "aba" 也是一个有效答案。
```

**示例 2：**

```tex
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



