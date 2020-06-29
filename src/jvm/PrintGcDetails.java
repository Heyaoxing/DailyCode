package jvm;


import java.util.concurrent.TimeUnit;

public class PrintGcDetails {

    public static void main(String[] args) throws InterruptedException {

        /**
         * idea 控制台乱码vm参数添加： -Dfile.encoding=UTF-8
         * jps -l 查看windows进程
         * jinfo -flags <pid>  查看vm参数
         * jmap -heap <pid> 查看heap的概要信息、GC使用的算法、heap的配置及JVM堆内存的使用情况.
         */
        Byte[] str=new Byte[1*1024*1024];
        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
        System.out.println("end");
    }
}
