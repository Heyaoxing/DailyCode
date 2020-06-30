package jvm;


import java.util.concurrent.TimeUnit;

public class ThrowableCode {


    /**
     * Throwable 是ERROR 和Exception的父类,包含了常见的程序异常和OOM异常
     * @param args
     */
    public static void main(String[] args) {
        try{
            Byte[] bytes=new Byte[10*1024*1024];
            int a=0;
            System.out.println(10/a);
            TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
        }catch (Exception e){
            System.out.println("Exception "+e);
        }catch (Error e){
            System.out.println("Error "+e);
        }
        catch (Throwable e){
            System.out.println("Throwable "+e);
        }
        System.out.println("end");
    }
}
