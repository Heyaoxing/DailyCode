package jvm;

import java.util.concurrent.TimeUnit;

public class StackOverFlowErrorCode {


    //Exception in thread "main" java.lang.StackOverflowError
    public static void main(String[] args) throws InterruptedException {
        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
        stackOverFlow();
    }

    private static void stackOverFlow() {
        stackOverFlow();
    }
}
